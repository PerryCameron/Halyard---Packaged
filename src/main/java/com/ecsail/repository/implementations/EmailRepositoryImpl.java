package com.ecsail.repository.implementations;


import com.ecsail.BaseApplication;
import com.ecsail.dto.EmailDTO;
import com.ecsail.dto.Email_InformationDTO;
import com.ecsail.dto.PersonDTO;
import com.ecsail.repository.interfaces.EmailRepository;
import com.ecsail.repository.rowmappers.EmailRowMapper;
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

import java.util.List;


public class EmailRepositoryImpl implements EmailRepository {

    private final JdbcTemplate template;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(EmailRepositoryImpl.class);


    public EmailRepositoryImpl() {
        this.template = new JdbcTemplate(BaseApplication.getDataSource());
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(BaseApplication.getDataSource());
    }

    @Override
    public List<Email_InformationDTO> getEmailInfo() {
        return null;
    }

    @Override
    public List<EmailDTO> getEmail(int p_id) {
        String query = "SELECT * FROM email";
        if(p_id != 0)
            query += " WHERE p_id=" + p_id;
        return template.query(query, new EmailRowMapper());
    }

    @Override
    public EmailDTO getPrimaryEmail(PersonDTO person) {
        String query = "select * from email where P_ID=? and PRIMARY_USE=true limit 1;";
        try {
            return template.queryForObject(query, new EmailRowMapper(), person.getP_id());
        } catch (EmptyResultDataAccessException e) {
            return null; // Return null if no email is found
        }
    }

    @Override
    public int update(EmailDTO emailDTO) {
        String query = "UPDATE email SET " +
                "P_ID = :pId, " +
                "PRIMARY_USE = :isPrimaryUse, " +
                "EMAIL = :email, " +
                "EMAIL_LISTED = :isListed " +
                "WHERE EMAIL_ID = :email_id";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(emailDTO);
        return namedParameterJdbcTemplate.update(query, namedParameters);
    }

    @Override
    public int insert(EmailDTO emailDTO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO email (P_ID, PRIMARY_USE, EMAIL, EMAIL_LISTED) " +
                "VALUES (:pId, :isPrimaryUse, :email, :isListed)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(emailDTO);
        int affectedRows = namedParameterJdbcTemplate.update(query, namedParameters, keyHolder);
        emailDTO.setEmail_id(keyHolder.getKey().intValue());
        return affectedRows;
    }

    @Override
    public int delete(EmailDTO emailDTO) {
        String deleteSql = "DELETE FROM email WHERE EMAIL_ID = ?";
        return template.update(deleteSql, emailDTO.getEmail_id());
    }
    @Override
    public boolean emailFromActiveMembershipExists(String email, int year) {
        if (email == null || email.isEmpty()) {
            logger.error("Email " + email + "doesn't exist");
            return false;
        }
        String existsQuery = "SELECT EXISTS(SELECT * FROM email e " +
                "LEFT JOIN person p ON e.P_ID = p.P_ID " +
                "LEFT JOIN membership_id id ON p.MS_ID = id.MS_ID " +
                "WHERE id.FISCAL_YEAR = ? AND e.EMAIL = ?)";
        try {
            Boolean result = template.queryForObject(existsQuery, Boolean.class, year, email);
            return Boolean.TRUE.equals(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
    @Override
    public boolean emailExists(PersonDTO p) {
        String sql = "SELECT EXISTS(SELECT * FROM email WHERE P_ID = ? AND PRIMARY_USE = true)";
        return template.queryForObject(sql, Boolean.class, p.getP_id());
    }
    @Override
    public String getEmail(PersonDTO person) {
        String sql = "SELECT * FROM email WHERE p_id = ? AND primary_use = true";
        try {
            EmailDTO email = template.queryForObject(sql, new EmailRowMapper(), person.getP_id());
            return email != null ? email.getEmail() : "";
        } catch (EmptyResultDataAccessException e) {
            return ""; // No results found
        } catch (DataAccessException e) {
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
            return "";
        }
    }




}
