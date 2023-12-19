package com.ecsail.repository.implementations;

import com.ecsail.BaseApplication;
import com.ecsail.dto.MembershipDTO;
import com.ecsail.repository.interfaces.MembershipRepository;
import com.ecsail.repository.rowmappers.MembershipListRowMapper;
import com.ecsail.dto.MembershipListDTO;
import com.ecsail.repository.rowmappers.MembershipRowMapper;
import com.ecsail.views.dialogues.Dialogue_ErrorSQL;
import org.mariadb.jdbc.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class MembershipRepositoryImpl implements MembershipRepository {

    public static Logger logger = LoggerFactory.getLogger(MembershipRepository.class);
    private JdbcTemplate template;
    public MembershipRepositoryImpl() {
        this.template = new JdbcTemplate(BaseApplication.getDataSource());
    }

    @Override
    public List<MembershipListDTO> getActiveRoster(String selectedYear) {
        String query = """
                SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,id.fiscal_year,m.join_date,id.mem_type, 
                s.SLIP_NUM,p.l_name,p.f_name,s.subleased_to,m.address,m.city,m.state,m.zip 
                FROM (select * from membership_id where FISCAL_YEAR=? and RENEW=1) id 
                INNER JOIN membership m on m.MS_ID = id.MS_ID 
                LEFT JOIN (select * from person where MEMBER_TYPE=1) p on m.MS_ID= p.MS_ID 
                LEFT JOIN slip s on m.MS_ID = s.MS_ID;
                """;
        List<MembershipListDTO> membershipListDTOS
                = template.query(query, new MembershipListRowMapper(), new Object[] {selectedYear});
        return membershipListDTOS;
    }

    @Override
    public List<MembershipListDTO> getInActiveRoster(String selectedYear) {
        String query = """
                SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,id.fiscal_year,m.join_date,id.mem_type, 
                s.SLIP_NUM,p.l_name,p.f_name,s.subleased_to,m.address,m.city,m.state,m.zip 
                FROM (select * from membership_id where FISCAL_YEAR=? and RENEW=0) id 
                INNER JOIN membership m on m.MS_ID = id.MS_ID 
                LEFT JOIN (select * from person where MEMBER_TYPE=1) p on m.MS_ID= p.MS_ID  
                LEFT JOIN slip s on m.MS_ID = s.MS_ID;
                """;
        List<MembershipListDTO> membershipListDTOS
                = template.query(query, new MembershipListRowMapper(), new Object[]{selectedYear});
        return membershipListDTOS;
    }

    @Override
    public List<MembershipListDTO> getAllRoster(String selectedYear) {
        String query = """
                SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,id.fiscal_year,m.join_date,id.mem_type,
                s.SLIP_NUM,p.l_name,p.f_name,s.subleased_to,m.address,m.city,m.state,m.zip 
                FROM (select * from membership_id where FISCAL_YEAR=?) id 
                INNER JOIN membership m on m.MS_ID = id.MS_ID 
                LEFT JOIN (select * from person where MEMBER_TYPE=1) p on m.MS_ID= p.MS_ID 
                LEFT JOIN slip s on m.MS_ID = s.MS_ID;
                """;
        List<MembershipListDTO> membershipListDTOS
                = template.query(query, new MembershipListRowMapper(), new Object[]{selectedYear});
        return membershipListDTOS;
    }

    @Override
    public List<MembershipListDTO> getNewMemberRoster(String selectedYear) {
        String query = """
                SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,id.fiscal_year,m.join_date,id.mem_type,s.SLIP_NUM,p.l_name,p.f_name,s.subleased_to,m.address,m.city,m.state,m.zip
                FROM (select * from membership where YEAR(JOIN_DATE)=?) m
                INNER JOIN (select * from membership_id where FISCAL_YEAR=? and RENEW=1) id ON m.ms_id=id.ms_id
                INNER JOIN (select * from person where MEMBER_TYPE=1) p ON m.p_id=p.p_id
                LEFT JOIN slip s on m.MS_ID = s.MS_ID
                                """;
        List<MembershipListDTO> membershipListDTOS
                = template.query(query, new MembershipListRowMapper(), new Object[]{selectedYear,selectedYear});
        return membershipListDTOS;
    }

    @Override
    public List<MembershipListDTO> getReturnMemberRoster(String selectedYear) {
        String query = """
                SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,m.join_date,id.mem_type,s.SLIP_NUM,p.l_name,
                       p.f_name,s.subleased_to,m.address,m.city,m.state,m.zip
                FROM membership_id id
                         LEFT JOIN membership m ON id.ms_id = m.ms_id
                         LEFT JOIN person p ON p.p_id = m.p_id
                         LEFT JOIN slip s ON s.ms_id = m.ms_id
                WHERE fiscal_year = ?
                  AND id.membership_id >
                      (SELECT membership_id
                       FROM membership_id
                       WHERE fiscal_year = ?
                         AND ms_id = (SELECT ms_id
                                      FROM membership_id
                                      WHERE membership_id = (SELECT MAX(membership_id)
                                                             FROM membership_id
                                                             WHERE fiscal_year = (? - 1)
                                                               AND membership_id < 500
                                                               AND renew = 1)
                                        AND fiscal_year = (? - 1)))
                  AND id.membership_id < 500
                  AND YEAR(m.join_date) != ?
                  AND (SELECT NOT EXISTS(SELECT mid
                                         FROM membership_id
                                         WHERE fiscal_year = (? - 1)
                                           AND renew = 1
                                           AND ms_id = id.ms_id));
                                """;
        List<MembershipListDTO> membershipListDTOS
                = template.query(query, new MembershipListRowMapper(), new Object[]{selectedYear,selectedYear,
                selectedYear,selectedYear,selectedYear,selectedYear});
        return membershipListDTOS;
    }

    @Override
    public List<MembershipListDTO> getSlipWaitList(String selectedYear) {
        String query = """
                SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,id.fiscal_year,m.join_date,id.mem_type,
                s.SLIP_NUM,p.l_name,p.f_name,s.subleased_to,m.address,m.city,m.state,m.zip
                FROM (SELECT * from wait_list where SLIP_WAIT=true) wl
                INNER JOIN (select * from membership_id where FISCAL_YEAR=? and RENEW=1) id on id.MS_ID=wl.MS_ID
                INNER JOIN membership m on m.MS_ID=wl.MS_ID
                LEFT JOIN (select * from person where MEMBER_TYPE=1) p on m.MS_ID= p.MS_ID
                LEFT JOIN slip s on m.MS_ID = s.MS_ID;
                """;
        List<MembershipListDTO> membershipListDTOS
                = template.query(query, new MembershipListRowMapper(), new Object[]{selectedYear});
        return membershipListDTOS;
    }
    @Override
    public MembershipListDTO getMembershipByMsIdAndYear(int msId, int year) {
        String sql = """
                SELECT m.ms_id, m.p_id, id.membership_id, id.fiscal_year, m.join_date, id.mem_type, s.SLIP_NUM, 
                p.l_name, p.f_name, s.subleased_to, m.address, m.city, m.state, m.zip 
                FROM slip s 
                RIGHT JOIN membership m ON m.ms_id = s.ms_id 
                LEFT JOIN membership_id id ON m.ms_id = id.ms_id 
                LEFT JOIN person p ON p.ms_id = m.ms_id 
                WHERE id.fiscal_year = ? AND p.member_type = 1 AND m.ms_id = ?
                """;
        try {
            return template.queryForObject(sql, new MembershipListRowMapper(), year, msId);
        } catch (EmptyResultDataAccessException e) {
            logger.error(e.getMessage());
            return null;
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            new Dialogue_ErrorSQL(e, "Unable to SELECT roster", "See below for details");
            return null;
        }
    }
    @Override
    public MembershipListDTO getMembershipListByIdAndYear(int membershipId, int year) {
        String sql = "SELECT m.ms_id, m.p_id, mid.membership_id, mid.fiscal_year, m.join_date, " +
                "mid.mem_type, s.SLIP_NUM, p.l_name, p.f_name, s.subleased_to, m.address, " +
                "m.city, m.state, m.zip " +
                "FROM membership m " +
                "LEFT JOIN person p ON m.ms_id = p.ms_id AND p.member_type = 1 " +
                "LEFT JOIN membership_id mid ON m.ms_id = mid.ms_id AND mid.fiscal_year = ? " +
                "LEFT JOIN slip s ON m.ms_id = s.ms_id " +
                "WHERE mid.fiscal_year = ? AND mid.membership_id = ? " +
                "LIMIT 1";
        return template.queryForObject(sql, new MembershipListRowMapper(), year, year, membershipId);
    }
    @Override
    public MembershipDTO getCurrentMembershipChair() {
        String sql = """
                SELECT m.* FROM membership m 
                JOIN app_settings a ON m.MS_ID = CAST(a.setting_value AS UNSIGNED) 
                WHERE a.setting_key = 'current_membership_chair'
                """;
        return template.queryForObject(sql, new MembershipRowMapper());
    }
    @Override
    public boolean memberShipExists(int msId) {
        String sql = "SELECT EXISTS(SELECT * FROM membership WHERE ms_id = ?)";
        return template.queryForObject(sql, Boolean.class, msId);
    }
    @Override
    public MembershipListDTO getMembershipByMsIdAndYear(int msId, String year) {
        String sql = """
                SELECT m.ms_id, m.p_id, id.membership_id, id.fiscal_year, m.join_date,
                id.mem_type, s.SLIP_NUM, p.l_name, p.f_name, s.subleased_to, m.address, m.city, m.state,
                m.zip FROM slip s RIGHT JOIN membership m ON m.ms_id = s.ms_id
                LEFT JOIN membership_id id ON m.ms_id = id.ms_id
                LEFT JOIN person p ON p.ms_id = m.ms_id 
                WHERE id.fiscal_year = ? AND p.member_type = 1 AND m.ms_id = ?
                """;
        try {
            return template.queryForObject(sql, new MembershipListRowMapper(), year, msId);
        } catch (EmptyResultDataAccessException e) {
            logger.error(e.getMessage());
            return null; // or handle appropriately
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            new Dialogue_ErrorSQL(e, "Unable to SELECT roster", "See below for details");
            return null; // or handle appropriately
        }
    }

    @Override
    public List<MembershipListDTO> getRoster(String year, boolean isActive) {
        String sql = """
                SELECT m.ms_id, m.p_id, id.membership_id, id.fiscal_year, m.join_date, id.mem_type, 
                s.SLIP_NUM, p.l_name, p.f_name, s.subleased_to, m.address, m.city, m.state, m.zip 
                FROM slip s
                RIGHT JOIN membership m ON m.ms_id = s.ms_id 
                LEFT JOIN membership_id id ON m.ms_id = id.ms_id
                LEFT JOIN person p ON p.ms_id = m.ms_id
                WHERE id.fiscal_year = ? AND p.member_type = 1 AND id.renew = ?
                ORDER BY id.membership_id
                """;
        try {
            return template.query(sql, new MembershipListRowMapper(), year, isActive);
        } catch (DataAccessException e) {
            logger.error("Unable to SELECT roster: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    @Override
    public List<MembershipListDTO> getRosterOfSlipOwners() {
        String sql = """
                 SELECT m.ms_id, m.p_id, id.membership_id, id.fiscal_year, m.join_date, id.mem_type, s.SLIP_NUM, 
                 p.l_name, p.f_name, s.subleased_to, m.address, m.city, m.state, m.zip 
                 FROM slip s 
                 INNER JOIN membership m ON s.ms_id = m.ms_id 
                 LEFT JOIN membership_id id ON m.ms_id = id.ms_id 
                 LEFT JOIN person p ON p.ms_id = m.ms_id 
                 WHERE p.member_type = 1 AND id.fiscal_year = YEAR(NOW())
                 """;
        try {
            return template.query(sql, new MembershipListRowMapper());
        } catch (DataAccessException e) {
            logger.error("Unable to SELECT roster: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    @Override
    public List<MembershipListDTO> getRosterOfSubleasedSlips() {
        String sql = """
                 SELECT m.ms_id, m.p_id, id.membership_id, id.fiscal_year, m.join_date, id.mem_type, 
                 s.SLIP_NUM, p.l_name, p.f_name, s.subleased_to, m.address, m.city, m.state, m.zip 
                 FROM slip s 
                 INNER JOIN membership m ON s.ms_id = m.ms_id 
                 LEFT JOIN membership_id id ON m.ms_id = id.ms_id 
                 LEFT JOIN person p ON p.ms_id = s.subleased_to 
                 WHERE subleased_to IS NOT NULL AND p.member_type = 1 AND id.fiscal_year = YEAR(NOW())
                 """;
        try {
            return template.query(sql, new MembershipListRowMapper());
        } catch (DataAccessException e) {
            logger.error("Unable to SELECT roster: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    @Override
    public MembershipListDTO getMembershipFromListWithoutMembershipId(int msId) {
        String sql = """
            SELECT m.MS_ID, m.P_ID, id.MEMBERSHIP_ID, id.FISCAL_YEAR, m.JOIN_DATE, 
                   id.MEM_TYPE, s.SLIP_NUM, p.L_NAME, p.F_NAME, s.SUBLEASED_TO, 
                   m.address, m.city, m.state, m.zip 
            FROM slip s 
            RIGHT JOIN membership m ON m.MS_ID = s.MS_ID 
            LEFT JOIN membership_id id ON m.MS_ID = id.MS_ID 
            LEFT JOIN person p ON p.MS_ID = m.MS_ID 
            WHERE p.MEMBER_TYPE = 1 
            AND id.fiscal_year = (
                CASE 
                    WHEN EXISTS (
                        SELECT 1 FROM membership_id 
                        WHERE FISCAL_YEAR = YEAR(NOW()) AND MS_ID = ?
                    )
                    THEN YEAR(NOW())
                    ELSE (SELECT MAX(FISCAL_YEAR) FROM membership_id WHERE MS_ID = ?)
                END
            )
            AND m.MS_ID = ?
                 """;
        try {
            return template.queryForObject(sql, new MembershipListRowMapper(), msId, msId, msId);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Unable to SELECT roster: " + e.getMessage());
            return null;
        } catch (DataAccessException e) {
            logger.error("Data Access Exception: " + e.getMessage());
            return null;
        }
    }
    @Override
    public List<MembershipListDTO> getSlipRoster(String year) {
        String sql = """
                 SELECT m.ms_id, m.p_id, id.membership_id, id.fiscal_year, m.join_date, id.mem_type, 
                 s.SLIP_NUM, p.l_name, p.f_name, s.subleased_to, m.address, m.city, m.state, m.zip 
                 FROM slip s 
                 INNER JOIN membership m ON s.ms_id = m.ms_id 
                 INNER JOIN membership_id id ON id.ms_id = m.ms_id 
                 INNER JOIN person p ON p.p_id = m.p_id 
                 WHERE id.fiscal_year = ?
                 """;
        try {
            return template.query(sql, new MembershipListRowMapper(), year);
        } catch (DataAccessException e) {
            logger.error("Unable to SELECT roster: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    @Override
    public MembershipListDTO getMembershipByMembershipId(String membership_id) {
        String sql = """
                 SELECT m.ms_id, m.p_id, id.membership_id, id.fiscal_year, m.join_date, 
                 id.mem_type, COALESCE(s.SLIP_NUM, '') as SLIP_NUM, p.l_name, p.f_name, 
                 COALESCE(s.subleased_to, 0) as subleased_to, m.address, m.city, m.state, m.zip 
                 FROM membership m 
                 LEFT JOIN person p ON m.p_id = p.p_id 
                 LEFT JOIN membership_id id ON m.ms_id = id.ms_id 
                 LEFT JOIN slip s ON m.ms_id = s.ms_id 
                 WHERE id.fiscal_year = Year(Now()) AND id.membership_id = ?
                 """;
        try {
            return template.queryForObject(sql, new MembershipListRowMapper(), membership_id);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Unable to SELECT roster: " + e.getMessage());
            return null;
        } catch (DataAccessException e) {
            logger.error("Data Access Exception: " + e.getMessage());
            return null;
        }
    }
    @Override
    public List<MembershipListDTO> getBoatOwnerRoster(int boat_id) {
        String sql = """
                 SELECT m.ms_id, m.p_id, id.membership_id, id.fiscal_year, m.join_date, 
                 id.mem_type, COALESCE(s.SLIP_NUM, '') as SLIP_NUM, p.l_name, p.f_name, 
                 COALESCE(s.subleased_to, 0) as subleased_to, m.address, m.city, m.state, m.zip 
                 FROM boat_owner bo 
                 LEFT JOIN membership m ON bo.ms_id = m.ms_id 
                 LEFT JOIN membership_id id ON m.ms_id = id.ms_id 
                 LEFT JOIN person p ON m.p_id = p.p_id 
                 LEFT JOIN slip s ON m.ms_id = s.ms_id 
                 WHERE bo.BOAT_ID = ? AND id.fiscal_year = Year(Now())
                 """;
        try {
            return template.query(sql, new MembershipListRowMapper(), boat_id);
        } catch (DataAccessException e) {
            logger.error("Unable to SELECT boat owner roster: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    @Override
    public void deleteMembership(int ms_id) {
        String sql = "DELETE FROM membership WHERE ms_id = ?";
        try {
            template.update(sql, ms_id);
        } catch (DataAccessException e) {
            logger.error("Unable to DELETE membership: " + e.getMessage());
        }
    }

    @Override
    public void deleteFormMsIdHash(int ms_id) {
        String sql = "DELETE FROM form_msid_hash WHERE ms_id = ?";
        try {
            template.update(sql, ms_id);
        } catch (DataAccessException e) {
            logger.error("Unable to DELETE form_msid_hash row: " + e.getMessage());
        }
    }
    @Override
    public MembershipListDTO insertMembership(MembershipListDTO nm) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = """
        INSERT INTO membership (p_id, join_date, mem_type, address, city, state, zip)
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try {
            template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, nm.getpId());
                ps.setDate(2, nm.getJoinDate() != null ? java.sql.Date.valueOf(nm.getJoinDate()) : null);
                ps.setString(3, nm.getMemType());
                ps.setString(4, nm.getAddress());
                ps.setString(5, nm.getCity());
                ps.setString(6, nm.getState());
                ps.setString(7, nm.getZip());
                return ps;
            }, keyHolder);

            Number key = keyHolder.getKey();
            if (key != null) {
                nm.setMsId(key.intValue()); // Update the DTO with the generated key
            }
        } catch (DataAccessException e) {
            logger.error("Unable to insert into membership: " + e.getMessage());
        }
        return nm; // Return the updated DTO
    }
    @Override
    public void updateMembership(MembershipListDTO dto) {
        String sql = "UPDATE membership SET P_ID = ?, JOIN_DATE = ?, MEM_TYPE = ?, ADDRESS = ?, CITY = ?, STATE = ?, ZIP = ? WHERE MS_ID = ?";
        try {
            template.update(sql, dto.getpId(), dto.getJoinDate(), dto.getMemType(), dto.getAddress(), dto.getCity(), dto.getState(), dto.getZip(), dto.getMsId());
        } catch (DataAccessException e) {
            logger.error("Error in updateMembership: " + e.getMessage());
        }
    }


}
