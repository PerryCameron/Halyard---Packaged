package com.ecsail.repository.implementations;

import com.ecsail.BaseApplication;
import com.ecsail.dto.Memo2DTO;
import com.ecsail.dto.MemoDTO;
import com.ecsail.gui.tabs.deposits.InvoiceWithMemberInfoDTO;
import com.ecsail.repository.interfaces.MemoRepository;
import com.ecsail.repository.rowmappers.Memo2RowMapper;
import com.ecsail.repository.rowmappers.MemoRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;

public class MemoRepositoryImpl implements MemoRepository {
    private JdbcTemplate template;

    public MemoRepositoryImpl() {
        this.template = new JdbcTemplate(BaseApplication.getDataSource());
    }

    @Override
    public List<MemoDTO> getMemosByMsId(int msId) {
        String query = "SELECT * FROM memo";
        if (msId != 0) query += " WHERE ms_id=?";
        List<MemoDTO> memoDTOs =
                template.query(query, new MemoRowMapper(), new Object[]{msId});
        return memoDTOs;
    }

    @Override
    public List<MemoDTO> getMemosByBoatId(int boatId) {
        String query = "SELECT * FROM memo WHERE boat_id=?";
        List<MemoDTO> memoDTOs =
                template.query(query, new MemoRowMapper(), new Object[]{boatId});
        return memoDTOs;
    }

    @Override
    public MemoDTO getMemoByMsId(InvoiceWithMemberInfoDTO invoice, String category) {
        String query = "SELECT * FROM memo WHERE INVOICE_ID=:invoiceId AND category=:category";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("invoiceId", invoice.getId())
                .addValue("category", category);
        MemoDTO memoDTO =
                template.queryForObject(query, new MemoRowMapper(), params);
        return memoDTO;
    }

//    @Override
//    public List<Memo2DTO> getAllMemosForTabNotes(String year, String category) {
//        String fiscalYear = year;
//        String query = "SELECT * FROM memo "
//                + "LEFT JOIN membership_id id on memo.ms_id=id.ms_id "
//                + "WHERE YEAR(memo_date)=:year and id.fiscal_year=:fiscalYear and memo.CATEGORY IN(:category)";
//        SqlParameterSource namedParameters = new MapSqlParameterSource()
//                .addValue("year", year)
//                .addValue("fiscalYear", fiscalYear)
//                .addValue("category", category);
//        List<Memo2DTO> memoDTOs =
//                template.query(query, new Memo2RowMapper(), namedParameters);
//        return memoDTOs;
//    }

    @Override
    public List<Memo2DTO> getAllMemosForTabNotes(String year, String category) {
        String query = "SELECT * FROM memo "
                + "LEFT JOIN membership_id id on memo.ms_id=id.ms_id "
                + "WHERE YEAR(memo_date)='"+year+"' and id.fiscal_year='"+year+"' and memo.CATEGORY IN("+category+")";
        List<Memo2DTO> memoDTOs =
                template.query(query, new Memo2RowMapper());
        return memoDTOs;
    }
}
