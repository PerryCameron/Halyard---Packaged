package com.ecsail.repository.implementations;

import com.ecsail.BaseApplication;
import com.ecsail.dto.Memo2DTO;
import com.ecsail.dto.MemoDTO;
import com.ecsail.repository.interfaces.MemoRepository;
import com.ecsail.repository.rowmappers.Memo2RowMapper;
import com.ecsail.repository.rowmappers.MemoRowMapper;
import com.ecsail.views.tabs.deposits.InvoiceWithMemberInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class MemoRepositoryImpl implements MemoRepository {

    public static Logger logger = LoggerFactory.getLogger(MemoRepository.class);
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
    public MemoDTO getMemoByInvoiceIdAndCategory(InvoiceWithMemberInfoDTO invoice, String category) {
                String query = "SELECT * FROM memo WHERE INVOICE_ID=" + invoice.getId() + " AND category='" + category + "'";
        MemoDTO memoDTO = template.queryForObject(query, new MemoRowMapper());
        return memoDTO;
    }

    @Override
    public List<Memo2DTO> getAllMemosForTabNotes(String year, String category) {
        String query = "SELECT * FROM memo "
                + "LEFT JOIN membership_id id on memo.ms_id=id.ms_id "
                + "WHERE YEAR(memo_date)='"+year+"' and id.fiscal_year='"+year+"' and memo.CATEGORY IN("+category+")";
        List<Memo2DTO> memoDTOs =
                template.query(query, new Memo2RowMapper());
        return memoDTOs;
    }
    @Override
    public void deleteMemo(MemoDTO memo) {
        String sql = "DELETE FROM memo WHERE memo_id = ?";
        try {
            template.update(sql, memo.getMemo_id());
        } catch (DataAccessException e) {
            logger.error("Unable to DELETE memo: " + e.getMessage());
        }
    }
    @Override
    public void deleteMemos(int msId) {
        String sql = "DELETE FROM memo WHERE ms_id = ?";
        try {
            template.update(sql, msId);
        } catch (DataAccessException e) {
            logger.error("Unable to DELETE memos: " + e.getMessage());
        }
    }
}
