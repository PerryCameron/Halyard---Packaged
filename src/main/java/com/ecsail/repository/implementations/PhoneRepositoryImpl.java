package com.ecsail.repository.implementations;


import com.ecsail.BaseApplication;
import com.ecsail.dto.PersonDTO;
import com.ecsail.dto.PhoneDTO;
import com.ecsail.repository.interfaces.PhoneRepository;
import com.ecsail.repository.rowmappers.PhoneRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


public class PhoneRepositoryImpl implements PhoneRepository {

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
    public PhoneDTO getListedPhoneByType(PersonDTO p, String phoneType) {
        String query = "SELECT * FROM phone WHERE p_id = ? AND phone_listed = true AND phone_type = ? limit 1";
        try {
            return template.queryForObject(query, new PhoneRowMapper(), p.getP_id(), phoneType);
        } catch (EmptyResultDataAccessException e) {
            return null; // Return null if no phone is found
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

}
