package com.ecsail.repository.implementations;


import com.ecsail.BaseApplication;
import com.ecsail.dto.PersonDTO;
import com.ecsail.dto.PhoneDTO;
import com.ecsail.repository.interfaces.PhoneRepository;
import com.ecsail.repository.rowmappers.PhoneRowMapper;
import org.mariadb.jdbc.Statement;
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


import java.sql.PreparedStatement;
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
    public List<PhoneDTO> getPhoneByPerson(PersonDTO personDTO) {
        String query = "SELECT * FROM phone WHERE p_id = ?";
        return template.query(query, new PhoneRowMapper(), personDTO.getpId());
    }

    @Override
    public String getListedPhoneByType(PersonDTO p, String type) {
        String sql = "SELECT PHONE FROM phone WHERE p_id = ? AND phone_listed = true AND phone_type = ?";
        try {
            return template.queryForObject(sql, String.class, p.getpId(), type);
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
            return template.queryForObject(sql, Boolean.class, p.getpId(), type);
        } catch (DataAccessException e) {
            logger.error("Unable to check if EXISTS: " + e.getMessage());
            return false;
        }
    }
    @Override
    public List<PhoneDTO> getPhoneByPid(int p_id) {
        String sql = "SELECT * FROM phone";
        if (p_id != 0) {
            sql += " WHERE p_id = ?";
            return template.query(sql, new PhoneRowMapper(), p_id);
        } else {
            return template.query(sql, new PhoneRowMapper());
        }
    }
    @Override
    public PhoneDTO insertPhone(PhoneDTO phoneDTO) {
        final String sql = "INSERT INTO phone (P_ID, PHONE, PHONE_TYPE, PHONE_LISTED) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, phoneDTO.getPid());
            ps.setString(2, phoneDTO.getPhoneNumber());
            ps.setString(3, phoneDTO.getPhoneType());
            ps.setBoolean(4, phoneDTO.isIsListed());
            return ps;
        }, keyHolder);

        phoneDTO.setPhone_ID(keyHolder.getKey().intValue());
        return phoneDTO;
    }
    @Override
    public boolean deletePhone(PhoneDTO phone) {
        String sql = "DELETE FROM phone WHERE phone_id = ?";
        try {
            template.update(sql, phone.getPhone_ID());
            return true;
        } catch (DataAccessException e) {
            logger.error("Unable to DELETE: " + e.getMessage());
            return false;
        }
    }
    @Override
    public void deletePhones(int p_id) {
        String sql = "DELETE FROM phone WHERE p_id = ?";
        try {
            template.update(sql, p_id);
        } catch (DataAccessException e) {
            logger.error("Unable to DELETE phones: " + e.getMessage());
            // Handle or rethrow the exception as per your application's requirements
        }
    }

    @Override
    public void updatePhone(String field, int phone_id, Object attribute) {
        String sql = "UPDATE phone SET " + field + " = ? WHERE phone_id = ?";
        try {
            template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                if (attribute instanceof String) {
                    ps.setString(1, (String) attribute);
                } else if (attribute instanceof Boolean) {
                    ps.setBoolean(1, (Boolean) attribute);
                } else {
                    // Handle other types or throw an exception
                    throw new IllegalArgumentException("Unsupported attribute type");
                }
                ps.setInt(2, phone_id);
                return ps;
            });
        } catch (DataAccessException e) {
            logger.error("There was a problem with the UPDATE: " + e.getMessage());
        }
}




}
