package com.ecsail.repository.implementations;

import com.ecsail.BaseApplication;
import com.ecsail.dto.*;
import com.ecsail.repository.rowmappers.*;
import com.ecsail.views.tabs.deposits.InvoiceWithMemberInfoDTO;
import com.ecsail.repository.interfaces.InvoiceRepository;
import org.mariadb.jdbc.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.*;

public class InvoiceRepositoryImpl implements InvoiceRepository {

    public static Logger logger = LoggerFactory.getLogger(InvoiceRepository.class);

    private final JdbcTemplate template;

    public InvoiceRepositoryImpl() {
        this.template = new JdbcTemplate(BaseApplication.getDataSource());
    }

    @Override
    public List<InvoiceDTO> getInvoicesByMsid(int msId) {
        String query = "SELECT * FROM invoice WHERE ms_id=?";
        return template.query(query, new InvoiceRowMapper(), msId);
    }

    @Override
    public List<InvoiceDTO> getAllInvoices() {
        String query = "SELECT * FROM invoice";
        return template.query(query, new InvoiceRowMapper());
    }

    @Override
    public List<InvoiceWithMemberInfoDTO> getInvoicesWithMembershipInfoByDeposit(DepositDTO depositDTO) {
        String query = "select mi.MEMBERSHIP_ID, p.L_NAME, p.F_NAME, i.* from invoice i " +
                "left join person p on i.MS_ID = p.MS_ID " +
                "left join membership_id mi on i.MS_ID = mi.MS_ID " +
                "where i.FISCAL_YEAR=? and mi.FISCAL_YEAR=? and p.MEMBER_TYPE=1 and i.COMMITTED=true and i.batch=?";
        return template.query(query, new InvoiceWithMemberInfoRowMapper(),
                new Object[]{depositDTO.getFiscalYear(), depositDTO.getFiscalYear(), depositDTO.getBatch()});
    }

    @Override
    public List<InvoiceWithMemberInfoDTO> getInvoicesWithMembershipInfoByYear(String year) {
        String query = "select mi.MEMBERSHIP_ID, p.L_NAME, p.F_NAME, i.* from invoice i " +
                "left join person p on i.MS_ID = p.MS_ID " +
                "left join membership_id mi on i.MS_ID = mi.MS_ID " +
                "where i.FISCAL_YEAR=? and mi.FISCAL_YEAR=? and p.MEMBER_TYPE=1 and i.COMMITTED=true";
        return template.query(query, new InvoiceWithMemberInfoRowMapper(), new Object[] {year,year});
    }

    @Override
    public  int getBatchNumber(String year) {
        String query = "SELECT MAX(batch) FROM invoice WHERE committed=true AND fiscal_year=:year";
        Map<String, Object> params = Collections.singletonMap("year", year);
        return Optional.ofNullable(template.queryForObject(query, Integer.class, params)).orElse(0);
    }
    @Override
    public void deletePayment(PaymentDTO p) {
        String sql = "DELETE FROM payment WHERE pay_id = ?";
        try {
            template.update(sql, p.getPay_id());
        } catch (DataAccessException e) {
            logger.error("Unable to DELETE Payment: " + e.getMessage());
        }
    }
    @Override
    public void deletePaymentByInvoiceID(int invoiceId) {
        String sql = "DELETE FROM payment WHERE invoice_id = ?";
        executeDelete(sql, invoiceId);
    }
    @Override
    public void deleteInvoiceItemByInvoiceID(int invoiceId) {
        String sql = "DELETE FROM invoice_item WHERE invoice_id = ?";
        executeDelete(sql, invoiceId);
    }
    @Override
    public void deleteInvoiceByID(int invoiceId) {
        String sql = "DELETE FROM invoice WHERE id = ?";
        executeDelete(sql, invoiceId);
    }
    @Override
    public void deleteAllPaymentsAndInvoicesByMsId(int msId) {
        List<InvoiceDTO> invoices = getInvoicesByMsid(msId);
        invoices.forEach(invoiceDTO -> {
            deletePaymentByInvoiceID(invoiceDTO.getId());
            deleteInvoiceItemByInvoiceID(invoiceDTO.getId());
            deleteInvoiceByID(invoiceDTO.getId());
        });
    }

    private void executeDelete(String sql, int id) {
        try {
            template.update(sql, id);
        } catch (DataAccessException e) {
            logger.error("Unable to DELETE: " + e.getMessage());
        }
    }
    @Override
    public DbInvoiceDTO insertDbInvoice(DbInvoiceDTO d) {
        String sql = """
        INSERT INTO db_invoice (
            FISCAL_YEAR, FIELD_NAME, widget_type, width, 
            Invoice_order, multiplied, price_editable, is_credit, 
            max_qty, auto_populate, is_itemized
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, d.getFiscalYear());
                ps.setString(2, d.getFieldName());
                ps.setString(3, d.getWidgetType());
                ps.setDouble(4, d.getWidth());
                ps.setInt(5, d.getOrder());
                ps.setBoolean(6, d.isMultiplied());
                ps.setBoolean(7, d.isPrice_editable());
                ps.setBoolean(8, d.isCredit());
                ps.setInt(9, d.getMaxQty());
                ps.setBoolean(10, d.isAutoPopulate());
                ps.setBoolean(11, d.isItemized());
                return ps;
            }, keyHolder);

            // Update the ID of the DbInvoiceDTO with the generated key
            d.setId(keyHolder.getKey().intValue());
        } catch (DataAccessException e) {
            logger.error("Unable to create new db_invoice row: " + e.getMessage());
            return null; // or handle as appropriate
        }
        return d;
    }
    @Override
    public PaymentDTO insertPayment(PaymentDTO op) {
        String sql = """
        INSERT INTO payment (
            INVOICE_ID, CHECK_NUMBER, PAYMENT_TYPE, PAYMENT_DATE, 
            AMOUNT, DEPOSIT_ID
        ) VALUES (?, ?, ?, ?, ?, ?)
        """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, op.getInvoice_id());
                ps.setString(2, op.getCheckNumber());
                ps.setString(3, op.getPaymentType());
                ps.setDate(4, Date.valueOf(op.getPaymentDate()));
                ps.setBigDecimal(5, new BigDecimal(op.getPaymentAmount())); // this is line 180
                ps.setInt(6, op.getDeposit_id());
                return ps;
            }, keyHolder);
            op.setPay_id(keyHolder.getKey().intValue());
        } catch (DataAccessException e) {
            logger.error("Unable to create new payment record: " + e.getMessage());
            return null; // or handle as appropriate
        }
        return op;
    }
    @Override
    public InvoiceDTO insertInvoice(InvoiceDTO m) {
        String sql = """
        INSERT INTO invoice (
            MS_ID, FISCAL_YEAR, PAID, TOTAL, CREDIT, 
            BALANCE, BATCH, COMMITTED, CLOSED, SUPPLEMENTAL, MAX_CREDIT
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, m.getMsId());
                ps.setInt(2, m.getYear());
                ps.setBigDecimal(3, new BigDecimal(m.getPaid()));
                ps.setBigDecimal(4, new BigDecimal(m.getTotal()));
                ps.setBigDecimal(5, new BigDecimal(m.getCredit()));
                ps.setBigDecimal(6, new BigDecimal(m.getBalance()));
                ps.setInt(7, m.getBatch());
                ps.setBoolean(8, m.isCommitted());
                ps.setBoolean(9, m.isClosed());
                ps.setBoolean(10, m.isSupplemental());
                ps.setBigDecimal(11, new BigDecimal(m.getMaxCredit()));
                return ps;
            }, keyHolder);
            m.setId(keyHolder.getKey().intValue());
        } catch (DataAccessException e) {
            logger.error("Unable to insert data into invoice row: " + e.getMessage());
            return null; // or handle as appropriate
        }
        return m;
    }
    @Override
    public InvoiceItemDTO insertInvoiceItem(InvoiceItemDTO i) {
        String sql = """
        INSERT INTO invoice_item (
            INVOICE_ID, MS_ID, FISCAL_YEAR, FIELD_NAME, 
            IS_CREDIT, VALUE, QTY
        ) VALUES (?, ?, ?, ?, ?, ?, ?)
        """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, i.getInvoiceId());
                ps.setInt(2, i.getMsId());
                ps.setInt(3, i.getYear());
                ps.setString(4, i.getFieldName());
                ps.setBoolean(5, i.isCredit());
                ps.setBigDecimal(6, new BigDecimal(i.getValue()));
                ps.setInt(7, i.getQty());
                return ps;
            }, keyHolder);
            i.setId(keyHolder.getKey().intValue());
        } catch (DataAccessException e) {
            logger.error("Unable to insert data into invoice_item: " + e.getMessage());
            return null; // or handle as appropriate
        }
        return i;
    }
    @Override
    public FeeDTO insertFee(FeeDTO feeDTO) {
        String sql = """
        INSERT INTO fee (
            FIELD_NAME, FIELD_VALUE, DB_INVOICE_ID, FEE_YEAR, 
            Description, DEFAULT_FEE
        ) VALUES (?, ?, ?, ?, ?, false)
        """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, feeDTO.getFieldName());
                ps.setBigDecimal(2, new BigDecimal(feeDTO.getFieldValue()));
                ps.setInt(3, feeDTO.getDbInvoiceID());
                ps.setInt(4, feeDTO.getFeeYear());
                ps.setString(5, feeDTO.getDescription());
                return ps;
            }, keyHolder);

            feeDTO.setFeeId(keyHolder.getKey().intValue());
            return feeDTO;
        } catch (DataAccessException e) {
            logger.error("Unable to create new fee record: " + e.getMessage());
            return null; // or handle as appropriate
        }
    }

    @Override
    public int updateDeposit(DepositDTO depositDTO) {
        final String sql = """
        UPDATE deposit
        SET DEPOSIT_DATE = ?, FISCAL_YEAR = ?, BATCH = ?
        WHERE DEPOSIT_ID = ?
        """;

        try {
            // The update method returns the number of rows affected by the query
            return template.update(
                    sql,
                    depositDTO.getDepositDate(), // Assuming getDepositDate returns a string in the correct format
                    depositDTO.getFiscalYear(),  // Assuming getFiscalYear returns a string that can be parsed as an integer
                    depositDTO.getBatch(),
                    depositDTO.getDeposit_id()
            );
        } catch (DataAccessException e) {
            logger.error("Error updating deposit: " + e.getMessage());
            return 0; // Or a different error handling strategy, like throwing an exception
        }
    }
    @Override
    public int updatePayment(PaymentDTO paymentDTO) {
        final String sql = """
        UPDATE payment
        SET INVOICE_ID = ?, CHECK_NUMBER = ?, PAYMENT_TYPE = ?, PAYMENT_DATE = ?, 
            AMOUNT = ?, DEPOSIT_ID = ?
        WHERE PAY_ID = ?
        """;
        try {
            return template.update(
                    sql,
                    paymentDTO.getInvoice_id(),
                    paymentDTO.getCheckNumber(),
                    paymentDTO.getPaymentType(),
                    paymentDTO.getPaymentDate(), // Ensure this is in the correct format for your database
                    paymentDTO.getPaymentAmount(), // Convert to BigDecimal if necessary
                    paymentDTO.getDeposit_id(),
                    paymentDTO.getPay_id()
            );
        } catch (DataAccessException e) {
            logger.error("Error updating payment: " + e.getMessage());
            return 0; // Or handle the exception as appropriate
        }
    }
    @Override
    public String getTotalAmount(int invoiceId) {
        final String sql = "SELECT SUM(AMOUNT) FROM payment WHERE INVOICE_ID = ?";
        try {
            BigDecimal totalAmount = template.queryForObject(
                    sql,
                    new Object[]{invoiceId},
                    BigDecimal.class
            );
            // Check if the result is null (i.e., no payments found) and return "0.00" in that case
            return totalAmount != null ? totalAmount.toString() : "0.00";
        } catch (DataAccessException e) {
            logger.error("Unable to retrieve total amount: " + e.getMessage());
            return "0.00"; // Returning default value in case of an error
        }
    }
    @Override
    public List<PaymentDTO> getPaymentsWithInvoiceId(int invoiceId) {
        String query = """
                   SELECT * FROM payment 
                   WHERE invoice_id = ?
                   """;
        try {
            return template.query(query, new PaymentRowMapper(), invoiceId);
        } catch (Exception e) {
            logger.error("Error while retrieving payments", e);
            return new ArrayList<>();
        }
    }
    @Override
    public List<InvoiceItemDTO> getInvoiceItemsByInvoiceId(int invoiceId) {
        String query = "SELECT * FROM invoice_item WHERE invoice_id = ?";
        try {
            return template.query(query, new InvoiceItemRowMapper(), invoiceId);
        } catch (Exception e) {
            logger.error("Error while retrieving invoice items by invoice ID", e);
            return new ArrayList<>();
        }
    }
    @Override
    public Boolean invoiceItemPositionCreditExistsWithValue(int year, int msId) {
        String query = """
                   SELECT EXISTS(
                       SELECT * FROM invoice_item 
                       WHERE FISCAL_YEAR = ? 
                       AND MS_ID = ? 
                       AND field_name = 'Position Credit' 
                       AND VALUE > 0
                   ) AS ITEM_EXISTS
                   """;
        try {
            return template.queryForObject(query, Boolean.class, year, msId);
        } catch (Exception e) {
            logger.error("Unable to check if 'Position Credit' item exists", e);
            return false;
        }
    }
    @Override
    public Boolean membershipHasOfficerForYear(int msid, int year) {
        String query = """
                   SELECT EXISTS(
                       SELECT * FROM officer o 
                       JOIN person p ON p.P_ID = o.P_ID 
                       WHERE o.OFF_YEAR = ? 
                       AND p.MS_ID = ? 
                       AND o.OFF_TYPE != 'BM'
                   ) AS officer_exists
                   """;
        try {
            return template.queryForObject(query, Boolean.class, year, msid);
        } catch (Exception e) {
            logger.error("Unable to check if Officer exists", e);
            return false;
        }
    }
    @Override
    public int updateInvoiceItem(InvoiceItemDTO item) {
        String query = """
                   UPDATE invoice_item 
                   SET VALUE = ?, QTY = ?, INVOICE_ID = ?, MS_ID = ?, FISCAL_YEAR = ?, FIELD_NAME = ?, IS_CREDIT = ?
                   WHERE ID = ?
                   """;
        try {
            return template.update(query,
                    item.getValue(),
                    item.getQty(),
                    item.getInvoiceId(),
                    item.getMsId(),
                    item.getYear(),
                    item.getFieldName(),
                    item.isCredit() ? 1 : 0,
                    item.getId());
        } catch (Exception e) {
            logger.error("There was a problem with updating item " + item.getFieldName(), e);
            return 0;
        }
    }
    @Override
    public void updateInvoice(InvoiceDTO invoice) {
        String query = """
                   UPDATE invoice SET 
                   PAID = ?, 
                   TOTAL = ?, 
                   CREDIT = ?, 
                   BALANCE = ?, 
                   BATCH = ?, 
                   COMMITTED = ?, 
                   CLOSED = ?, 
                   SUPPLEMENTAL = ?, 
                   MAX_CREDIT = ?
                   WHERE ID = ?
                   """;
        try {
            template.update(query,
                    invoice.getPaid(),
                    invoice.getTotal(),
                    invoice.getCredit(),
                    invoice.getBalance(),
                    invoice.getBatch(),
                    invoice.isCommitted() ? 1 : 0,
                    invoice.isClosed() ? 1 : 0,
                    invoice.isSupplemental() ? 1 : 0,
                    invoice.getMaxCredit(),
                    invoice.getId());
        } catch (Exception e) {
            logger.error("There was a problem with updating Invoice ID " + invoice.getId(), e);
        }
    }
    @Override
    public Boolean paymentExists(int invoiceId) {
        String query = "SELECT EXISTS(SELECT * FROM payment WHERE INVOICE_ID = ?)";
        try {
            return template.queryForObject(query, Boolean.class, invoiceId);
        } catch (Exception e) {
            logger.error("Unable to check if payment record EXISTS", e);
            return false;
        }
    }
    @Override
    public List<DbInvoiceDTO> getDbInvoiceByYear(int year) {
        String query = """
                   SELECT * FROM db_invoice 
                   WHERE FISCAL_YEAR = ?
                   """;
        try {
            return template.query(query, new DbInvoiceRowMapper(), year);
        } catch (Exception e) {
            logger.error("Unable to retrieve DbInvoice information for year: " + year, e);
            return new ArrayList<>();
        }
    }
    @Override
    public List<FeeDTO> getFeesFromYear(int year) {
        String query = """
                   SELECT * FROM fee 
                   WHERE fee_year = ?
                   """;
        try {
            return template.query(query, new FeeRowMapper(), year);
        } catch (Exception e) {
            logger.error("Error retrieving fees from year: " + year, e);
            return new ArrayList<>();
        }
    }


}
