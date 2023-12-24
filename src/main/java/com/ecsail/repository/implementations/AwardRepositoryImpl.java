package com.ecsail.repository.implementations;


import com.ecsail.BaseApplication;
import com.ecsail.dto.AwardDTO;
import com.ecsail.dto.PersonDTO;
import com.ecsail.pdf.directory.Object_Sportsmen;
import com.ecsail.repository.interfaces.AwardRepository;
import com.ecsail.repository.rowmappers.AwardsRowMapper;
import org.mariadb.jdbc.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class AwardRepositoryImpl implements AwardRepository {
    private final JdbcTemplate template;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public static Logger logger = LoggerFactory.getLogger(AwardRepository.class);

    public AwardRepositoryImpl() {
        this.template = new JdbcTemplate(BaseApplication.getDataSource());
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(BaseApplication.getDataSource());
    }

    @Override
    public List<AwardDTO> getAwards(PersonDTO p) {
        String query = "SELECT * FROM awards WHERE p_id=" + p.getpId();
        return template.query(query, new AwardsRowMapper());
    }

    @Override
    public List<AwardDTO> getAwards() {
        String query = "SELECT * FROM awards";
        return template.query(query, new AwardsRowMapper());
    }

    @Override
    public int updateAward(AwardDTO awardDTO) {
        String query = "UPDATE awards SET " +
                "P_ID = :pId, " +
                "AWARD_YEAR = :awardYear, " +
                "AWARD_TYPE = :awardType " +
                "WHERE AWARD_ID = :awardId ";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(awardDTO);
        return namedParameterJdbcTemplate.update(query, namedParameters);
    }

    @Override
    public int insert(AwardDTO awardDTO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO awards (P_ID, AWARD_YEAR, AWARD_TYPE) VALUES (:pId, :awardYear, :awardType)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(awardDTO);
        int affectedRows = namedParameterJdbcTemplate.update(query, namedParameters, keyHolder);
        awardDTO.setAwardId(keyHolder.getKey().intValue());
        return affectedRows;
    }

    @Override
    public AwardDTO insertAward(AwardDTO award) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO awards (p_id, award_year, award_type) VALUES (?, ?, ?)";

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, award.getpId());
            ps.setString(2, award.getAwardYear());
            ps.setString(3, award.getAwardType());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            award.setAwardId(keyHolder.getKey().intValue());
        }
        return award;
    }

    @Override
    public int delete(AwardDTO awardDTO) {
        String deleteSql = "DELETE FROM awards WHERE Award_ID = ?";
        return template.update(deleteSql, awardDTO.getAwardId());
    }

    @Override
    public List<Object_Sportsmen> getSportsManAwardNames() {
        String query = "SELECT award_year, f_name, l_name FROM awards a LEFT JOIN person p ON a.p_id = p.p_id";
        try {
            return template.query(query, (rs, rowNum) -> new Object_Sportsmen(
                    rs.getString("award_year"),
                    rs.getString("f_name"),
                    rs.getString("l_name")));
        } catch (DataAccessException e) {
            logger.error("Unable to retrieve information", e);
            return List.of(); // Return an empty list in case of failure
        }
    }
}
