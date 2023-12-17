package com.ecsail.repository.implementations;

import com.ecsail.BaseApplication;
import com.ecsail.dto.DepositDTO;
import com.ecsail.dto.InvoiceDTO;
import com.ecsail.dto.PaymentDTO;
import com.ecsail.views.tabs.deposits.InvoiceWithMemberInfoDTO;
import com.ecsail.repository.interfaces.InvoiceRepository;
import com.ecsail.repository.rowmappers.InvoiceRowMapper;
import com.ecsail.repository.rowmappers.InvoiceWithMemberInfoRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

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


}
