package com.ecsail.repository.implementations;

import com.ecsail.BaseApplication;
import com.ecsail.dto.MembershipDTO;
import com.ecsail.dto.PersonDTO;
import com.ecsail.views.dialogues.Dialogue_ErrorSQL;
import com.ecsail.repository.interfaces.PersonRepository;
import com.ecsail.repository.rowmappers.PersonRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
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
            template.update(query, p.getP_id());
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






}
