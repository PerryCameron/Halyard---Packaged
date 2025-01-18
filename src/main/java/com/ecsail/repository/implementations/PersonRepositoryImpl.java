package com.ecsail.repository.implementations;

import com.ecsail.BaseApplication;
import com.ecsail.dto.MembershipDTO;
import com.ecsail.dto.PersonDTO;
import com.ecsail.views.dialogues.Dialogue_ErrorSQL;
import com.ecsail.repository.interfaces.PersonRepository;
import com.ecsail.repository.rowmappers.PersonRowMapper;
import org.mariadb.jdbc.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersonRepositoryImpl implements PersonRepository {

    public static Logger logger = LoggerFactory.getLogger(PersonRepository.class);
    private JdbcTemplate template;
    public PersonRepositoryImpl() {
        this.template = new JdbcTemplate(BaseApplication.getDataSource());
    }

    @Override
    public List<PersonDTO> getActivePeopleByMsId(int msId) {
        String query = "SELECT * FROM person WHERE IS_ACTIVE=true AND ms_id=?";
                List<PersonDTO> personDTOS = template.query(query, new PersonRowMapper(), new Object[]{msId});
        return personDTOS;
    }

    @Override
    public  void deletePerson(PersonDTO p) {
        String query = "DELETE FROM person WHERE p_id = ?";
        try {
            template.update(query, p.getpId());
        } catch (DataAccessException e) {
            new Dialogue_ErrorSQL(e,"Unable to DELETE","See below for details");
        }
    }
    @Override
    public ArrayList<PersonDTO> getDependants(MembershipDTO m) {
        String sql = "SELECT * FROM person WHERE ms_id = ? and member_type = 3 AND IS_ACTIVE = TRUE";
        return new ArrayList<>(template.query(sql, new PersonRowMapper(), m.getMsId()));
    }
    @Override
    public PersonDTO getPerson(int ms_id, int member_type) {
        String sql = "SELECT * FROM person WHERE MS_ID = ? AND member_type = ?";
        try {
            return template.queryForObject(sql, new PersonRowMapper(), ms_id, member_type);
        } catch (EmptyResultDataAccessException e) {
            logger.error("There were no results for getPerson(int ms_id, int member_type)");
            return null;
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
            return null;
        }
    }
    @Override
    public boolean activePersonExists(int ms_id, int member_type) {
        String sql = "SELECT EXISTS(SELECT * FROM person WHERE ms_id = ? AND member_type = ? AND is_active = true)";
        return template.queryForObject(sql, Boolean.class, ms_id, member_type);
    }
    @Override
    public PersonDTO getPersonByPid(int pid) {
        String sql = "SELECT * FROM person WHERE p_id = ?";
        try {
            return template.queryForObject(sql, new PersonRowMapper(), pid);
        } catch (EmptyResultDataAccessException e) {
            logger.error("There were no results for SqlPerson.getPersonByPid(int pid)");
            return null;
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
            return null;
        }
    }
    @Override
    public void deletePerson(int p_id) {
        String sql = "DELETE FROM person WHERE p_id = ?";
        try {
            template.update(sql, p_id);
        } catch (DataAccessException e) {
            logger.error("Unable to DELETE person: " + e.getMessage());
            // Handle or rethrow the exception as per your application's requirements
        }
    }
    @Override
    public List<PersonDTO> getPeople(int ms_id) {
        String sql = "SELECT * FROM person WHERE ms_id = ? AND IS_ACTIVE = true";
        try {
            return template.query(sql, new PersonRowMapper(), ms_id);
        } catch (DataAccessException e) {
            logger.error("Unable to retrieve information: " + e.getMessage());
            return new ArrayList<>(); // Return an empty list in case of an exception
        }
    }
    @Override
    public void updatePerson(PersonDTO p) {
        String sql = """
        UPDATE person 
        SET MEMBER_TYPE = ?, MS_ID = ?, F_NAME = ?, L_NAME = ?, 
            BIRTHDAY = ?, OCCUPATION = ?, BUSINESS = ?, 
            IS_ACTIVE = ?, NICK_NAME = ?, OLD_MSID = ? 
        WHERE p_id = ?
        """;

        try {
            template.update(sql,
                    p.getMemberType(), p.getMsId(), p.getFirstName(), p.getLastName(),
                    p.getBirthday(), p.getOccupation(), p.getBusiness(),
                    p.isActive() ? 1 : 0, p.getNickName(), p.getOldMsid(),
                    p.getpId());
        } catch (DataAccessException e) {
            logger.error("There was a problem with the UPDATE: " + e.getMessage());
        }
    }
    @Override
    public void removePersonFromMembership(PersonDTO p) {
        String sql = "UPDATE person SET MS_ID = null, OLD_MSID = ? WHERE P_ID = ?";
        try {
            template.update(sql, p.getMsId(), p.getpId());
        } catch (DataAccessException e) {
            logger.error("There was a problem with the UPDATE: " + e.getMessage());
        }
    }
    @Override
    public PersonDTO insertPerson(PersonDTO person) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = """
        INSERT INTO person (MS_ID, MEMBER_TYPE, F_NAME, L_NAME, BIRTHDAY, OCCUPATION, BUSINESS, IS_ACTIVE, NICK_NAME, OLD_MSID)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try {
            template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, person.getMsId());
                ps.setInt(2, person.getMemberType());
                ps.setString(3, person.getFirstName());
                ps.setString(4, person.getLastName());
                // Handle birthday conversion with the correctly formatted date string
                if (person.getBirthday() != null && !person.getBirthday().isEmpty()) {
                    ps.setDate(5, java.sql.Date.valueOf(person.getBirthday()));
                } else {
                    ps.setDate(5, null); // Set null if the birthday is null or empty
                }
                ps.setString(6, person.getOccupation());
                ps.setString(7, person.getBusiness());
                ps.setBoolean(8, person.isActive());
                ps.setString(9, person.getNickName());
                ps.setInt(10, person.getOldMsid());
                return ps;
            }, keyHolder);

            Number key = keyHolder.getKey();
            if (key != null) {
                person.setpId(key.intValue()); // Update the DTO with the generated key
            }
        } catch (DataAccessException e) {
            logger.error("Unable to insert into person: " + e.getMessage());
            // Handle or rethrow the exception as per your application's requirements
        }
        return person; // Return the updated DTO
    }

    @Override
    public int getPersonAge(PersonDTO person) {
        String query = "SELECT DATE_FORMAT(FROM_DAYS(DATEDIFF(NOW(),(SELECT birthday FROM person WHERE p_id = ?))), '%Y')+0 AS AGE";
        try {
            return template.queryForObject(
                    query,
                    new Object[]{person.getpId()},
                    Integer.class
            );
        } catch (DataAccessException e) {
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
            return 0; // Handle the exception as needed
        }
    }

    @Override
    public PersonDTO insertUserByMsId(int msId) {
        String sql = """
        INSERT INTO person (
            MS_ID, MEMBER_TYPE, F_NAME, L_NAME, BIRTHDAY, 
            OCCUPATION, BUSINESS, IS_ACTIVE, NICK_NAME, OLD_MSID
        ) VALUES (?, 1, '', '', null, '', '', true, null, null)
        """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, msId);
                return ps;
            }, keyHolder);

            int generatedId = keyHolder.getKey().intValue();
            return new PersonDTO(generatedId, msId, 1, "", "", null, "", "", true, null, 0);
        } catch (DataAccessException e) {
            logger.error("Unable to create new user: " + e.getMessage());
            return null; // or handle as appropriate
        }
    }

    @Override
    public Boolean memberTypeExists(int memberType, int msid) {
        String sql = "SELECT EXISTS(SELECT P_ID FROM person WHERE member_type = ? AND ms_id = ?) as memberTypeExists";
        try {
            return template.queryForObject(
                    sql,
                    new Object[]{memberType, msid},
                    (rs, rowNum) -> rs.getBoolean("memberTypeExists")
            );
        } catch (Exception e) {
            logger.error("Unable to check if member type exists", e);
            return false;
        }
    }

    @Override
    public List<PersonDTO> getAllPersons() {
        String sql = "SELECT * FROM person";
        try {
            return template.query(sql, new PersonRowMapper());
        } catch (Exception e) {
            logger.error("Error fetching all persons", e);
            // Handle exception as required
            // Return an empty list or handle the exception otherwise
            return Collections.emptyList();
        }
    }
}



