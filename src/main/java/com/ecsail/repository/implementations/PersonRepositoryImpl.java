package com.ecsail.repository.implementations;

import com.ecsail.BaseApplication;
import com.ecsail.dto.PersonDTO;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.repository.interfaces.PersonRepository;
import com.ecsail.repository.rowmappers.PersonRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class PersonRepositoryImpl implements PersonRepository {
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

    public  void deletePerson(PersonDTO p) {
        String query = "DELETE FROM person WHERE p_id = ?";
        try {
            template.update(query, p.getP_id());
        } catch (DataAccessException e) {
            new Dialogue_ErrorSQL(e,"Unable to DELETE","See below for details");
        }
    }



}
