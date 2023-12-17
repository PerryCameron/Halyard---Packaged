package com.ecsail.repository.implementations;


import com.ecsail.BaseApplication;
import com.ecsail.dto.PersonDTO;
import com.ecsail.dto.PhoneDTO;
import com.ecsail.repository.interfaces.PhoneRepository;
import com.ecsail.repository.rowmappers.PhoneRowMapper;
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


import java.util.List;


public class PhoneRepositoryImpl implements PhoneRepository {

    public static Logger logger = LoggerFactory.getLogger(PhoneRepository.class);
    private final JdbcTemplate template;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public PhoneRepositoryImpl() {
        this.template = new JdbcTemplate(BaseApplication.getDataSource());
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(BaseApplication.getDataSource());
    }

    @Override
    public List<PhoneDTO> getPhoneByPid(int pId) {
        String query = "SELECT * FROM phone Where P_ID = ?";
        return template.query(query, new PhoneRowMapper(), pId);
    }

    @Override
    public List<PhoneDTO> getPhoneByPerson(PersonDTO personDTO) {
        String query = "SELECT * FROM phone WHERE p_id = ?";
        return template.query(query, new PhoneRowMapper(), personDTO.getP_id());
    }

    @Override
    public String getListedPhoneByType(PersonDTO p, String type) {
        String sql = "SELECT PHONE FROM phone WHERE p_id = ? AND phone_listed = true AND phone_type = ?";
        try {
            return template.queryForObject(sql, String.class, p.getP_id(), type);
        } catch (EmptyResultDataAccessException e) {
            logger.error("No listed phone found for the specified type: " + e.getMessage());
            return "";
        } catch (DataAccessException e) {
            logger.error("Unable to retrieve information: " + e.getMessage());
            return "";
        }
    }

    @Override
    public PhoneDTO getPhoneByPersonAndType(int pId, String type) {
        String query = "SELECT * FROM phone WHERE p_id = ? AND phone_type = ? LIMIT 1";
        try {
            return template.queryForObject(query, new PhoneRowMapper(), pId, type);
        } catch (EmptyResultDataAccessException e) {
            return null; // Return null if no phone is found
        }
    }
    @Override
    public int update(PhoneDTO phoneDTO) {
        String query = "UPDATE phone SET " +
                "P_ID = :pId," +
                "PHONE = :phone, " +
                "PHONE_TYPE = :phoneType, " +
                "PHONE_LISTED = :phoneListed " +
                "WHERE PHONE_ID = :phoneId";
        System.out.println("Phone ID is " + phoneDTO.getPhone_ID());
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(phoneDTO);
        return namedParameterJdbcTemplate.update(query, namedParameters);
    }

    @Override
    public int delete(PhoneDTO phoneDTO) {
        String deleteSql = "DELETE FROM phone WHERE PHONE_ID = ?";
        return template.update(deleteSql, phoneDTO.getPhone_ID());
    }

    @Override
    public int insert(PhoneDTO phoneDTO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO phone (P_ID, PHONE, PHONE_TYPE, PHONE_LISTED) " +
                "VALUES (:pId, :phone, :phoneType, :phoneListed)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(phoneDTO);
        int affectedRows = namedParameterJdbcTemplate.update(query, namedParameters, keyHolder);
        phoneDTO.setPhone_ID(keyHolder.getKey().intValue());
        return affectedRows;
    }
    @Override
    public boolean listedPhoneOfTypeExists(PersonDTO p, String type) {
        String sql = "SELECT EXISTS(SELECT * FROM phone WHERE P_ID = ? AND PHONE_LISTED = true AND PHONE_TYPE = ?)";
        try {
            return template.queryForObject(sql, Boolean.class, p.getP_id(), type);
        } catch (DataAccessException e) {
            logger.error("Unable to check if EXISTS: " + e.getMessage());
            return false;
        }
    }


}
