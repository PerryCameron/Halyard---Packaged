package com.ecsail.repository.implementations;


import com.ecsail.BaseApplication;
import com.ecsail.dto.MembershipIdDTO;
import com.ecsail.repository.interfaces.MembershipIdRepository;
import com.ecsail.repository.rowmappers.MembershipIdRowMapper;
import com.ecsail.views.dialogues.Dialogue_ErrorSQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;


public class MembershipIdRepositoryImpl implements MembershipIdRepository {

    public static Logger logger = LoggerFactory.getLogger(MembershipIdRepository.class);
    private final JdbcTemplate template;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MembershipIdRepositoryImpl() {
        this.template = new JdbcTemplate(BaseApplication.getDataSource());
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(BaseApplication.getDataSource());
    }

    @Override
    public List<MembershipIdDTO> getIds() {
        return null;
    }

    @Override
    public List<MembershipIdDTO> getIds(int msId) {
        String query = "SELECT * FROM membership_id WHERE MS_ID=?";
        return template.query(query, new MembershipIdRowMapper(),msId);
    }

    @Override
    public int getId(int ms_id) {
        String sql = "SELECT membership_id FROM membership_id WHERE ms_id = ?";
        try {
            return template.queryForObject(sql, Integer.class, ms_id);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
            return 0; // Or an appropriate default/error value
        }
    }

    @Override
    public MembershipIdDTO getCurrentId(int msId) {
        String query = "SELECT * FROM membership_id WHERE FISCAL_YEAR=YEAR(CURDATE()) and MS_ID=?";
        return template.queryForObject(query, new MembershipIdRowMapper(),msId);
    }

    @Override
    public int getMembershipIDFromMsid(int msid) {
        String sql = "SELECT membership_id FROM membership_id WHERE ms_id = ? AND fiscal_year = ?";
        try {
            return template.queryForObject(sql, Integer.class, msid, String.valueOf(Year.now().getValue()));
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
            return 0;
        }
    }

    // one in question
    @Override
    public Integer getMsidFromMembershipID(int membership_id) {
        String sql = "SELECT ms_id FROM membership_id WHERE fiscal_year = ? AND membership_id = ?";
        try {
            return template.queryForObject(sql, Integer.class, String.valueOf(Year.now().getValue()), membership_id);
        } catch (EmptyResultDataAccessException e) {
            logger.error(e.getMessage());
            return 0;
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
            return 0;
        }
    }

    @Override
    public MembershipIdDTO getMembershipIdObject(int mid) {
        return null;
    }

    @Override
    public boolean isRenewedByMsidAndYear(int ms_id, String year) {
        return false;
    }

    @Override
    public List<MembershipIdDTO> getAllMembershipIdsByYear(int year) {
        List<MembershipIdDTO> theseIds = new ArrayList<>();
        String sql = "SELECT * FROM membership_id WHERE fiscal_year = ? ORDER BY membership_id";
        try {
            List<MembershipIdDTO> results = template.query(sql, new MembershipIdRowMapper(), year);
            theseIds.addAll(results);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            new Dialogue_ErrorSQL(e, "Unable to retrieve information membership_id info for " + year, "See below for details");
        }
        return theseIds;
    }

    @Override
    public List<MembershipIdDTO> getActiveMembershipIdsByYear(String year) {
        return null;
    }

    @Override
    public int getNonRenewNumber(int year) {
        return 0;
    }

    @Override
    public int getMsidFromYearAndMembershipId(int year, String membershipId) {
        return 0;
    }

    @Override
    public int update(MembershipIdDTO membershipIdDTO) {
        String query = "UPDATE membership_id SET " +
                "FISCAL_YEAR = :fiscalYear, " +
                "MS_ID = :msId, " +
                "MEMBERSHIP_ID = :membershipId, " +
                "RENEW = :renew, " +
                "MEM_TYPE = :memType, " +
                "SELECTED = :selected, " +
                "LATE_RENEW = :lateRenew " +
                "WHERE MID = :mId ";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(membershipIdDTO);
        return namedParameterJdbcTemplate.update(query, namedParameters);
    }

    @Override
    public int delete(MembershipIdDTO membershipIdDTO) {
        String deleteSql = "DELETE FROM membership_id WHERE MID = ?";
        return template.update(deleteSql, membershipIdDTO.getMid());
    }
    @Override
    public void deleteMembershipId(int ms_id) {
        String sql = "DELETE FROM membership_id WHERE ms_id = ?";
        try {
            template.update(sql, ms_id);
        } catch (DataAccessException e) {
            logger.error("Unable to DELETE membership_id: " + e.getMessage());
        }
    }

    @Override
    public MembershipIdDTO insert(MembershipIdDTO membershipIdDTO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO membership_id (FISCAL_YEAR, MS_ID, MEMBERSHIP_ID, RENEW, MEM_TYPE, SELECTED, LATE_RENEW) " +
                "VALUES (:fiscalYear, :msId, :membershipId, :renew, :memType, :selected, :lateRenew)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(membershipIdDTO);
        int affectedRows = namedParameterJdbcTemplate.update(query, namedParameters, keyHolder);
        membershipIdDTO.setMid(keyHolder.getKey().intValue());
        return membershipIdDTO;
    }
    @Override
    public String getMembershipIdByYearAndMsId(String year, int msId) {
        String sql = "SELECT membership_id FROM membership_id WHERE fiscal_year = ? AND ms_id = ?";
        try {
            return template.queryForObject(sql, new Object[]{year, msId}, String.class);
        } catch (EmptyResultDataAccessException e) {
            return "none";
        } catch (DataAccessException e) {
            logger.error("Unable to retrieve information: " + e.getMessage());
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
            return "";
        }
    }
    @Override
    public int getMembershipIdForNewestMembership(int year) {
        String sql = "SELECT MAX(membership_id) FROM membership_id WHERE fiscal_year = ? AND membership_id < 500";
        try {
            Integer result = template.queryForObject(sql, new Object[]{year}, Integer.class);
            return (result != null) ? result : 0;
        } catch (DataAccessException e) {
            logger.error("Unable to retrieve highest membership ID: " + e.getMessage());
            // Handle or rethrow the exception as per your application's requirements
            return 0;
        }
    }

}
