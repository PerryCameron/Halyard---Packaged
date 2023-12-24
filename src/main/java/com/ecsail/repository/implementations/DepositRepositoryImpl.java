package com.ecsail.repository.implementations;


import com.ecsail.BaseApplication;
import com.ecsail.dto.DepositDTO;
import com.ecsail.dto.DepositTotalDTO;
import com.ecsail.repository.interfaces.DepositRepository;
import com.ecsail.repository.rowmappers.DepositRowMapper;
import com.ecsail.repository.rowmappers.DepositTotalRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;


public class DepositRepositoryImpl implements DepositRepository {


    public static Logger logger = LoggerFactory.getLogger(DepositRepository.class);
    private final JdbcTemplate template;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DepositRepositoryImpl() {
        this.template = new JdbcTemplate(BaseApplication.getDataSource());
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(BaseApplication.getDataSource());
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
    public DepositDTO insertDeposit(DepositDTO d) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO deposit (DEPOSIT_DATE, FISCAL_YEAR, BATCH) VALUES (?, ?, ?);";
        template.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"DEPOSIT_ID"});
            ps.setString(1, d.getDepositDate());
            ps.setString(2, d.getFiscalYear());
            ps.setInt(3, d.getBatch());
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            d.setDeposit_id(keyHolder.getKey().intValue());
        }
        return d;
    }

    @Override
    public Boolean depositIsUsed(int year, int batch) {
        String sql = "SELECT EXISTS(SELECT * FROM invoice WHERE FISCAL_YEAR = ? AND BATCH = ?) AS LATEST_EXISTS";
        try {
            return template.queryForObject(sql, new Object[]{year, batch}, Boolean.class);
        } catch (Exception e) {
            logger.error("Unable to check if EXISTS", e);
            // Handle exception as required
            // For example, showing a dialog or rethrowing as a custom exception
            // new Dialogue_ErrorSQL(e, "Unable to check if EXISTS", "See below for details");
            return false;
        }
    }

    @Override
    public Boolean depositRecordExists(String year, int batch) {
        String sql = "SELECT EXISTS(SELECT * FROM deposit WHERE fiscal_year = ? AND BATCH = ?)";
        try {
            return template.queryForObject(sql, new Object[]{year, batch}, Boolean.class);
        } catch (Exception e) {
            logger.error("Unable to check if EXISTS", e);
            // Handle exception as required
            // For example, showing a dialog or rethrowing as a custom exception
            // new Dialogue_ErrorSQL(e, "Unable to check if EXISTS", "See below for details");
            return false;
        }
    }

    @Override
    public int getNumberOfDepositsForYear(int year) {
        String query = "SELECT COUNT(*) FROM deposit WHERE FISCAL_YEAR = ?";
        try {
            return template.queryForObject(query, new Object[]{year}, Integer.class);
        } catch (Exception e) {
            logger.error("Unable to retrieve information", e);
            return 0; // Return 0 in case of an error
        }
    }
    @Override
    public DepositDTO getDeposit(int year, int batch) {
        String query = """
            SELECT * FROM deposit WHERE fiscal_year = ? AND batch = ?
            """;
        try {
            return template.queryForObject(query, new Object[]{year, batch}, new DepositRowMapper());
        } catch (Exception e) {
            logger.error("Unable to retrieve deposit information", e);
            return null; // Return null in case of failure
        }
    }
    @Override
    public DepositTotalDTO getTotals(DepositDTO d, boolean getAll) {
        String query;
        Object[] params;
        if (getAll) {
            query = "SELECT SUM(TOTAL) AS TOTAL, SUM(CREDIT) AS CREDIT, SUM(PAID) AS PAID FROM invoice WHERE FISCAL_YEAR = ? AND COMMITTED = TRUE";
            params = new Object[]{d.getFiscalYear()};
        } else {
            query = "SELECT SUM(TOTAL) AS TOTAL, SUM(CREDIT) AS CREDIT, SUM(PAID) AS PAID FROM invoice WHERE FISCAL_YEAR = ? AND COMMITTED = TRUE AND BATCH = ?";
            params = new Object[]{d.getFiscalYear(), d.getBatch()};
        }
        try {
            return template.queryForObject(query, params, new DepositTotalRowMapper());
        } catch (Exception e) {
            logger.error("Unable to retrieve information", e);
            return null; // Return null in case of failure
        }
    }
    @Override
    public int getNumberOfDepositBatches(String year) {
        String query = "SELECT MAX(batch) FROM deposit WHERE fiscal_year = ?";
        try {
            Integer maxBatch = template.queryForObject(query, new Object[]{year}, Integer.class);
            return maxBatch != null ? maxBatch : 0;
        } catch (Exception e) {
            logger.error("Unable to retrieve information", e);
            return 0; // Return 0 in case of failure
        }
    }
}
