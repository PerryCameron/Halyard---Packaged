package com.ecsail.repository.implementations;

import com.ecsail.BaseApplication;
import com.ecsail.dto.PersonDTO;
import com.ecsail.repository.interfaces.PersonRepository;
import com.ecsail.repository.rowmappers.PersonRowMapper;
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

//    @Override
//    public List<BoatListDTO> getActiveSailBoats() {
//        String query = """
//                SELECT id.membership_id,id.ms_id, p.l_name, p.f_name,b.*,nb.boat_count
//                FROM (SELECT * FROM membership_id WHERE FISCAL_YEAR=year(now()) and RENEW=true) id
//                LEFT JOIN (SELECT * FROM person WHERE MEMBER_TYPE=1) p on id.MS_ID=p.MS_ID
//                INNER JOIN boat_owner bo on id.MS_ID=bo.MS_ID
//                INNER JOIN (SELECT * FROM boat WHERE AUX=false) b on bo.BOAT_ID=b.BOAT_ID
//                LEFT JOIN (SELECT BOAT_ID,count(BOAT_ID) AS boat_count
//                FROM boat_photos group by BOAT_ID having count(BOAT_ID) > 0) nb on b.BOAT_ID=nb.BOAT_ID
//                """;
//        List<BoatListDTO> boatListDTOS =
//                template.query(query, new BoatListRowMapper());
//        return boatListDTOS;
//    }


}
