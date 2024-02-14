package com.ecsail.repository.implementations;

import com.ecsail.BaseApplication;
import com.ecsail.dto.Memo2DTO;
import com.ecsail.dto.MemoDTO;
import com.ecsail.repository.interfaces.MemoRepository;
import com.ecsail.repository.rowmappers.Memo2RowMapper;
import com.ecsail.repository.rowmappers.MemoRowMapper;
import com.ecsail.views.tabs.deposits.InvoiceWithMemberInfoDTO;
import org.mariadb.jdbc.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;

public class MemoRepositoryImpl implements MemoRepository {

    public static Logger logger = LoggerFactory.getLogger(MemoRepository.class);
    private final JdbcTemplate template;

    public MemoRepositoryImpl() {
        this.template = new JdbcTemplate(BaseApplication.getDataSource());
    }

    @Override
    public List<MemoDTO> getMemosByMsId(int msId) {
        String query = "SELECT * FROM memo";
        if (msId != 0) query += " WHERE ms_id=?";
        return template.query(query, new MemoRowMapper(), msId);
    }

    @Override
    public List<MemoDTO> getMemosByBoatId(int boatId) {
        String query = "SELECT * FROM memo WHERE boat_id=?";
        return template.query(query, new MemoRowMapper(), boatId);
    }

    @Override
    public MemoDTO getMemoByInvoiceIdAndCategory(InvoiceWithMemberInfoDTO invoice, String category) {
                String query = "SELECT * FROM memo WHERE INVOICE_ID=" + invoice.getId() + " AND category='" + category + "' limit 1";
        return template.queryForObject(query, new MemoRowMapper());
    }

    @Override
    public List<Memo2DTO> getAllMemosForTabNotes(String year, String category) {
        String query = "SELECT * FROM memo "
                + "LEFT JOIN membership_id id on memo.ms_id=id.ms_id "
                + "WHERE YEAR(memo_date)='"+year+"' and id.fiscal_year='"+year+"' and memo.CATEGORY IN("+category+")";
        return template.query(query, new Memo2RowMapper());
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

    @Override
    public MemoDTO insertMemo(MemoDTO m) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO memo (ms_id, memo_date, memo, invoice_id, category, boat_id) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            template.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        ps.setInt(1, m.getMsid());
                        ps.setDate(2, java.sql.Date.valueOf(m.getMemo_date()));
                        ps.setString(3, m.getMemo());
                        // Handle potential null values for invoice_id and boat_id
                        if (m.getInvoice_id() != 0) ps.setInt(4, m.getInvoice_id());
                        else ps.setObject(4, null, Types.INTEGER); // Set to NULL if the value is 0
                        // 5
                        ps.setString(5, m.getCategory());
                        // 6
                        if (m.getBoat_id() != 0) ps.setInt(6, m.getBoat_id());
                        else ps.setObject(6, null, Types.INTEGER); // Set to NULL if the value is 0
                        return ps;
                    }, keyHolder);
            Number key = keyHolder.getKey();
            if (key != null) {
                m.setMemo_id(key.intValue());
            }
        } catch (DataAccessException e) {
            logger.error("Unable to create new memo row: " + e.getMessage());
        }
        return m;
    }

    @Override
    public void updateMemo(int memoId, String field, String attribute) {
        String sql = "UPDATE memo SET " + field + " = ? WHERE memo_id = ?";
        try {
            template.update(sql, attribute, memoId);
        } catch (DataAccessException e) {
            logger.error("There was a problem with the UPDATE: " + e.getMessage());
        }
    }
    @Override
    public Boolean memoExists(int invoiceId, String category) {
        String sql = "SELECT EXISTS(SELECT * FROM memo WHERE CATEGORY = ? AND invoice_id = ?) AS memoExists";
        try {
            return template.queryForObject(sql, new Object[]{category, invoiceId}, Boolean.class);
        } catch (Exception e) {
            logger.error("Unable to check if memo exists", e);
            // Handle exception as required
            // For example, showing a dialog or rethrowing as a custom exception
            // new Dialogue_ErrorSQL(e, "Unable to check if memo exists", "See below for details");
            return false;
        }
    }
}
