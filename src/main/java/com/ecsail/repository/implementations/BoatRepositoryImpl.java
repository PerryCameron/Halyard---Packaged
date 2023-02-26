package com.ecsail.repository.implementations;

import com.ecsail.BaseApplication;
import com.ecsail.dto.BoatDTO;
import com.ecsail.dto.BoatListDTO;
import com.ecsail.dto.BoatOwnerDTO;
import com.ecsail.repository.interfaces.BoatRepository;
import com.ecsail.repository.rowmappers.BoatListRowMapper;
import com.ecsail.repository.rowmappers.BoatOwnerRowMapper;
import com.ecsail.repository.rowmappers.BoatRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class BoatRepositoryImpl implements BoatRepository {
    private final JdbcTemplate template;

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
        return template.query(query, new BoatListRowMapper());
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
        return template.query(query, new BoatListRowMapper());
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
        return template.query(query, new BoatListRowMapper());
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
        return template.query(query, new BoatListRowMapper());
    }

    @Override
    public List<BoatListDTO> getAllBoatLists() {
        String query = """
                SELECT id.membership_id,id.ms_id, p.l_name, p.f_name,b.*,nb.boat_count FROM boat b 
                LEFT JOIN boat_owner bo on b.BOAT_ID = bo.BOAT_ID 
                LEFT JOIN membership m on bo.MS_ID = m.MS_ID 
                LEFT JOIN (SELECT * FROM membership_id where FISCAL_YEAR=(select year(now()))) id on bo.MS_ID=id.MS_ID 
                LEFT JOIN person p on m.P_ID = p.P_ID 
                LEFT JOIN (select BOAT_ID,count(BOAT_ID) AS boat_count from boat_photos 
                GROUP BY BOAT_ID having count(BOAT_ID) > 0) nb on b.BOAT_ID=nb.BOAT_ID
                """;
        return template.query(query, new BoatListRowMapper());
    }

    @Override
    public List<BoatDTO> getAllBoats() {
        String query = "SELECT * from boat";
        return template.query(query, new BoatRowMapper());
    }

    @Override
    public List<BoatDTO> getBoatsByMsId(int msId) {
        String query = """
                SELECT b.boat_id, bo.ms_id, b.manufacturer, b.manufacture_year, b.registration_num, b.model, 
                b.boat_name, b.sail_number, b.has_trailer, b.length, b.weight, b.keel, b.phrf, b.draft, b.beam, 
                b.lwl, b.aux FROM boat b INNER JOIN boat_owner bo USING (boat_id) WHERE ms_id=?
                """;
        return template.query(query, new BoatRowMapper(), msId);
    }

    @Override
    public List<BoatDTO> getOnlySailboatsByMsId(int msId) {
        String query = """
                Select BOAT_ID, MS_ID, ifnull(MANUFACTURER,'') AS MANUFACTURER, ifnull(MANUFACTURE_YEAR,'') AS
                MANUFACTURE_YEAR, ifnull(REGISTRATION_NUM,'') AS REGISTRATION_NUM, ifnull(MODEL,'') AS MODEL, 
                ifnull(BOAT_NAME,'') AS BOAT_NAME, ifnull(SAIL_NUMBER,'') AS SAIL_NUMBER, HAS_TRAILER, 
                ifnull(LENGTH,'') AS LENGTH, ifnull(WEIGHT,'') AS WEIGHT, KEEL, ifnull(PHRF,'') AS PHRF, ifnull(DRAFT,'') AS DRAFT, 
                ifnull(BEAM,'') AS BEAM, ifnull(LWL,'') AS LWL, AUX from boat 
                INNER JOIN boat_owner USING (boat_id) WHERE ms_id=? and 
                MODEL NOT LIKE 'Kayak' and MODEL NOT LIKE 'Canoe' and MODEL NOT LIKE 'Row Boat' and 
                MODEL NOT LIKE 'Paddle Board'
                                """;
        return template.query(query, new BoatRowMapper(), msId);
    }

    @Override
    public List<BoatOwnerDTO> getBoatOwners() {
        String query = "SELECT * FROM boat_owner";
        return template.query(query, new BoatOwnerRowMapper());
    }
}
