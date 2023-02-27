package com.ecsail.repository.implementations;

import com.ecsail.BaseApplication;
import com.ecsail.dto.DepositDTO;
import com.ecsail.dto.InvoiceDTO;
import com.ecsail.gui.tabs.deposits.InvoiceWithMemberInfoDTO;
import com.ecsail.repository.interfaces.InvoiceRepository;
import com.ecsail.repository.rowmappers.InvoiceRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class InvoiceRepositoryImpl implements InvoiceRepository {
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
        return null;
    }

    @Override
    public List<InvoiceWithMemberInfoDTO> getInvoicesWithMembershipInfoByYear(String year) {
        return null;
    }

    @Override
    public int getBatchNumber(String year) {
        return 0;
    }
}
