package com.ecsail.repository.implementations;


import com.ecsail.BaseApplication;
import com.ecsail.dto.EmailDTO;
import com.ecsail.dto.Email_InformationDTO;
import com.ecsail.dto.PersonDTO;
import com.ecsail.repository.interfaces.EmailRepository;
import com.ecsail.repository.rowmappers.EmailInformationRowMapper;
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

import java.sql.PreparedStatement;
import java.time.Year;
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
    public void deleteEmail(int p_id) {
        String sql = "DELETE FROM email WHERE p_id = ?";
        try {
            template.update(sql, p_id);
        } catch (DataAccessException e) {
            logger.error("Unable to DELETE email: " + e.getMessage());
            // Handle or rethrow the exception as per your application's requirements
        }
    }

    @Override
    public boolean deleteEmail(EmailDTO email) {
        String sql = "DELETE FROM email WHERE email_id = ?";
        try {
            template.update(sql, email.getEmail_id());
            return true;
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            new Dialogue_ErrorSQL(e, "Unable to DELETE", "See below for details");
            return false;
        }
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
    @Override
    public List<Email_InformationDTO> getEmailInfo() {
        String sql = "SELECT id.membership_id, m.join_date, p.l_name, p.f_name, email, primary_use " +
                "FROM email e " +
                "INNER JOIN person p ON p.p_id = e.p_id " +
                "INNER JOIN membership m ON m.ms_id = p.ms_id " +
                "INNER JOIN membership_id id ON id.ms_id = m.ms_id " +
                "WHERE id.fiscal_year = ? AND id.renew = true " +
                "ORDER BY id.membership_id";

        return template.query(sql, new EmailInformationRowMapper(), String.valueOf(Year.now().getValue()));
    }

    @Override
    public void updateEmail(int email_id, String email) {
        String sql = "UPDATE email SET email = ? WHERE email_id = ?";
        template.update(sql, email, email_id);
    }
    @Override
    public void updateEmail(String field, int email_id, Boolean attribute) {
        String sql = "UPDATE email SET " + field + " = ? WHERE email_id = ?";
        template.update(sql, attribute, email_id);
    }
    @Override
    public EmailDTO insertEmail(EmailDTO emailDTO) {
        final String sql = "INSERT INTO email (P_ID, PRIMARY_USE, EMAIL, EMAIL_LISTED) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"EMAIL_ID"});
            ps.setInt(1, emailDTO.getPid());
            ps.setBoolean(2, emailDTO.isPrimaryUse());
            ps.setString(3, emailDTO.getEmail());
            ps.setBoolean(4, emailDTO.isIsListed());
            return ps;
        }, keyHolder);

        emailDTO.setEmail_id(keyHolder.getKey().intValue());
        return emailDTO;
    }











}
