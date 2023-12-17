package com.ecsail.repository.implementations;


import com.ecsail.BaseApplication;
import com.ecsail.dto.OfficerDTO;
import com.ecsail.dto.OfficerWithNameDTO;
import com.ecsail.dto.PersonDTO;
import com.ecsail.repository.interfaces.OfficerRepository;
import com.ecsail.repository.rowmappers.OfficerRowMapper;
import com.ecsail.repository.rowmappers.OfficerWithNamesRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;


public class OfficerRepositoryImpl implements OfficerRepository {
    public static Logger logger = LoggerFactory.getLogger(OfficerRepository.class);
    private final JdbcTemplate template;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public OfficerRepositoryImpl() {
        this.template = new JdbcTemplate(BaseApplication.getDataSource());
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(BaseApplication.getDataSource());
    }

    @Override
    public List<OfficerDTO> getOfficers() {
        String query = "SELECT * FROM officer";
        return template.query(query,new OfficerRowMapper());
    }

    @Override
    public List<OfficerDTO> getOfficer(String field, int attribute) {
        String query = "SELECT * FROM officer WHERE ? = ?";
        return template.query(query,new OfficerRowMapper(),field,attribute);    }

    @Override
    public List<OfficerDTO> getOfficer(PersonDTO person) {
        String query = "SELECT * FROM officer WHERE P_ID = ?";
        return template.query(query,new OfficerRowMapper(), person.getP_id());
    }

    @Override
    public List<OfficerWithNameDTO> getOfficersWithNames(String type) {
        String query = "SELECT f_name,L_NAME,off_year FROM officer o LEFT JOIN person p ON o.p_id=p.p_id WHERE off_type= ?";
        return template.query(query,new OfficerWithNamesRowMapper(), type);
    }

    @Override
    public int update(OfficerDTO officerDTO) {
        String query = "UPDATE officer SET " +
                "P_ID = :pId, " +
                "BOARD_YEAR = :boardYear, " +
                "OFF_TYPE = :officerType, " +
                "OFF_YEAR = :fiscalYear " +
                "WHERE O_ID = :officerId";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(officerDTO);
        return namedParameterJdbcTemplate.update(query, namedParameters);
    }

    @Override
    public int insert(OfficerDTO officerDTO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO officer (P_ID, BOARD_YEAR, OFF_TYPE, OFF_YEAR) " +
                "VALUES (:pId, :boardYear, :officerType, :fiscalYear)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pId", officerDTO.getPerson_id());
        namedParameters.addValue("boardYear", Integer.parseInt(officerDTO.getBoard_year()));
        namedParameters.addValue("officerType", officerDTO.getOfficer_type());
        namedParameters.addValue("fiscalYear", Integer.parseInt(officerDTO.getFiscal_year()));
        int affectedRows = namedParameterJdbcTemplate.update(query, namedParameters, keyHolder);
        officerDTO.setOfficer_id(keyHolder.getKey().intValue());
        return affectedRows;
    }

    @Override
    public int delete(OfficerDTO officerDTO) {
        String deleteSql = "DELETE FROM officer WHERE O_ID = ?";
        return template.update(deleteSql, officerDTO.getOfficer_id());
    }

    @Override
    public void deleteOfficer(int p_id) {
        String sql = "DELETE FROM officer WHERE p_id = ?";
        try {
            template.update(sql, p_id);
        } catch (DataAccessException e) {
            logger.error("Unable to DELETE officer: " + e.getMessage());
        }
    }

}
