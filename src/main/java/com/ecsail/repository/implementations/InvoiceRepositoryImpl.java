package com.ecsail.repository.implementations;

import com.ecsail.BaseApplication;
import com.ecsail.dto.*;
import com.ecsail.views.tabs.deposits.InvoiceWithMemberInfoDTO;
import com.ecsail.repository.interfaces.InvoiceRepository;
import com.ecsail.repository.rowmappers.InvoiceRowMapper;
import com.ecsail.repository.rowmappers.InvoiceWithMemberInfoRowMapper;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public List<InvoiceWithMemberInfoDTO> getInvoicesWithMembershipInfoByDeposit(DepositDTO d) {
        String query = "select mi.MEMBERSHIP_ID, p.L_NAME, p.F_NAME, i.* from invoice i " +
                "left join person p on i.MS_ID = p.MS_ID " +
                "left join membership_id mi on i.MS_ID = mi.MS_ID " +
                "where i.FISCAL_YEAR=? and mi.FISCAL_YEAR=? and p.MEMBER_TYPE=1 and i.COMMITTED=true and i.batch=?";
        return template.query(query, new InvoiceWithMemberInfoRowMapper(), new Object[]{d.getFiscalYear(), d.getFiscalYear(), d.getBatch()});
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


}
