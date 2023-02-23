package com.ecsail.repository.implementations;

import com.ecsail.BaseApplication;
import com.ecsail.dto.BoatListDTO;
import com.ecsail.repository.interfaces.BoatRepository;
import com.ecsail.repository.rowmappers.BoatListRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class BoatRepositoryImpl implements BoatRepository {
    private JdbcTemplate template;

    public BoatRepositoryImpl() {
        this.template = new JdbcTemplate(BaseApplication.getDataSource());
    }

    @Override
    public List<BoatListDTO> getActiveSailBoats() {
        String query = """
                SELECT id.membership_id,id.ms_id, p.l_name, p.f_name,b.*,nb.boat_count 
                FROM (SELECT * FROM membership_id WHERE FISCAL_YEAR=year(now()) and RENEW=true) id 
                LEFT JOIN (SELECT * FROM person WHERE MEMBER_TYPE=1) p on id.MS_ID=p.MS_ID 
                INNER JOIN boat_owner bo on id.MS_ID=bo.MS_ID 
                INNER JOIN (SELECT * FROM boat WHERE AUX=false) b on bo.BOAT_ID=b.BOAT_ID 
                LEFT JOIN (SELECT BOAT_ID,count(BOAT_ID) AS boat_count 
                FROM boat_photos group by BOAT_ID having count(BOAT_ID) > 0) nb on b.BOAT_ID=nb.BOAT_ID
                """;
        List<BoatListDTO> boatListDTOS =
                template.query(query, new BoatListRowMapper());
        return boatListDTOS;
    }

    @Override
    public List<BoatListDTO> getActiveAuxBoats() {
        String query = """
                SELECT id.membership_id,id.ms_id, p.l_name, p.f_name,b.*,nb.boat_count 
                FROM (SELECT * FROM membership_id WHERE FISCAL_YEAR=year(now()) and RENEW=true) id 
                LEFT JOIN (SELECT * FROM person WHERE MEMBER_TYPE=1) p on id.MS_ID=p.MS_ID 
                INNER JOIN boat_owner bo on id.MS_ID=bo.MS_ID 
                INNER JOIN (SELECT * FROM boat WHERE AUX=true) b on bo.BOAT_ID=b.BOAT_ID 
                LEFT JOIN (SELECT BOAT_ID,count(BOAT_ID) AS boat_count 
                FROM boat_photos group by BOAT_ID having count(BOAT_ID) > 0) nb on b.BOAT_ID=nb.BOAT_ID;
                """;
        List<BoatListDTO> boatListDTOS =
                template.query(query, new BoatListRowMapper());
        return boatListDTOS;
    }

    @Override
    public List<BoatListDTO> getAllSailBoats() {
        String query = """
                SELECT id.membership_id,id.ms_id, p.l_name, p.f_name,b.*,nb.boat_count 
                FROM (select * from boat where AUX=false) b 
                LEFT JOIN boat_owner bo on b.BOAT_ID = bo.BOAT_ID 
                LEFT JOIN membership m on bo.MS_ID = m.MS_ID 
                LEFT JOIN (SELECT * FROM membership_id where FISCAL_YEAR=(select year(now()))) id on bo.MS_ID=id.MS_ID 
                LEFT JOIN person p on m.P_ID = p.P_ID 
                LEFT JOIN (select BOAT_ID,count(BOAT_ID) 
                AS boat_count from boat_photos group by BOAT_ID having count(BOAT_ID) > 0) nb 
                ON b.BOAT_ID=nb.BOAT_ID;
                """;
        List<BoatListDTO> boatListDTOS =
                template.query(query, new BoatListRowMapper());
        return boatListDTOS;
    }

    @Override
    public List<BoatListDTO> getAllAuxBoats() {
        String query = """
                SELECT id.membership_id,id.ms_id, p.l_name, p.f_name,b.*,nb.boat_count 
                FROM (select * from boat where AUX=true) b 
                LEFT JOIN boat_owner bo on b.BOAT_ID = bo.BOAT_ID 
                LEFT JOIN membership m on bo.MS_ID = m.MS_ID 
                LEFT JOIN (SELECT * FROM membership_id where FISCAL_YEAR=(select year(now()))) id on bo.MS_ID=id.MS_ID 
                LEFT JOIN person p on m.P_ID = p.P_ID LEFT JOIN (select BOAT_ID,count(BOAT_ID) 
                AS boat_count from boat_photos group by BOAT_ID having count(BOAT_ID) > 0) nb on b.BOAT_ID=nb.BOAT_ID;
                """;
        List<BoatListDTO> boatListDTOS =
                template.query(query, new BoatListRowMapper());
        return boatListDTOS;
    }

    @Override
    public List<BoatListDTO> getAllBoats() {
        String query = """
                SELECT id.membership_id,id.ms_id, p.l_name, p.f_name,b.*,nb.boat_count FROM boat b 
                LEFT JOIN boat_owner bo on b.BOAT_ID = bo.BOAT_ID 
                LEFT JOIN membership m on bo.MS_ID = m.MS_ID 
                LEFT JOIN (SELECT * FROM membership_id where FISCAL_YEAR=(select year(now()))) id on bo.MS_ID=id.MS_ID 
                LEFT JOIN person p on m.P_ID = p.P_ID 
                LEFT JOIN (select BOAT_ID,count(BOAT_ID) AS boat_count from boat_photos 
                GROUP BY BOAT_ID having count(BOAT_ID) > 0) nb on b.BOAT_ID=nb.BOAT_ID
                """;
        List<BoatListDTO> boatListDTOS =
                template.query(query, new BoatListRowMapper());
        return boatListDTOS;
    }
}
