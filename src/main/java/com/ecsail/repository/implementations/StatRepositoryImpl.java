package com.ecsail.repository.implementations;



import com.ecsail.dto.StatsDTO;
import com.ecsail.repository.interfaces.StatRepository;
import com.ecsail.repository.rowmappers.StatsRowMapper;
import com.ecsail.repository.rowmappers.StatsSpecialRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class StatRepositoryImpl implements StatRepository {
    private final JdbcTemplate template;

    public StatRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }


    @Override
    public List<StatsDTO> getStatistics(int startYear, int stopYear) {
        String query = "SELECT * FROM stats WHERE fiscal_year > "+(startYear -1)+" AND fiscal_year < " + (stopYear +1);
        return template.query(query, new StatsRowMapper());
    }

    @Override
    public List<StatsDTO> createStatDTO(int year, int statID) {
        return null;
    }

    @Override
    public StatsDTO createStatDTO(int year) {
        String query =
                """
                                SELECT
                                id.fiscal_year AS 'YEAR',
                                COUNT(DISTINCT IF(id.mem_type = 'RM' AND id.RENEW=true,id.membership_id , NULL)) AS 'REGULAR',
                                COUNT(DISTINCT IF(id.mem_type = 'FM' AND id.RENEW=true,id.membership_id , NULL)) AS 'FAMILY',
                                COUNT(DISTINCT IF(id.mem_type = 'SO' AND id.RENEW=true,id.membership_id , NULL)) AS 'SOCIAL',
                                COUNT(DISTINCT IF(id.mem_type = 'LA' AND id.RENEW=true,id.membership_id , NULL)) AS 'LAKE_ASSOCIATES',
                                COUNT(DISTINCT IF(id.mem_type = 'LM' AND id.RENEW=true,id.membership_id , NULL)) AS 'LIFE_MEMBERS',
                                COUNT(DISTINCT IF(id.mem_type = 'SM' AND id.RENEW=true,id.membership_id , NULL)) AS 'STUDENT',
                                COUNT(DISTINCT IF(id.mem_type = 'RF' AND id.RENEW=true,id.membership_id , NULL)) AS 'RACE_FELLOWS',
                                COUNT(DISTINCT IF(YEAR(m.JOIN_DATE)=?,id.membership_id, NULL)) AS 'NEW_MEMBERS',
                                COUNT(DISTINCT IF(id.membership_id >
                                (SELECT membership_id FROM membership_id WHERE fiscal_year=? AND MS_ID=(SELECT MS_ID FROM membership_id WHERE membership_id=(
                                SELECT max(membership_id) FROM membership_id WHERE fiscal_year=(? -1)AND membership_id < 500 AND renew=1)
                                AND fiscal_year=(? -1))) AND id.membership_id < 500 AND YEAR(m.JOIN_DATE)!=?
                                AND (SELECT NOT EXISTS(SELECT mid FROM membership_id WHERE fiscal_year=(? -1) AND RENEW=1 AND MS_ID=id.MS_ID)), id.membership_id, NULL)) AS 'RETURN_MEMBERS',
                                SUM(NOT RENEW) as 'NON_RENEW', SUM(RENEW) as 'ACTIVE_MEMBERSHIPS'
                                FROM membership_id id LEFT JOIN membership m on id.MS_ID=m.MS_ID
                                WHERE fiscal_year=?;
                        """;
        StatsDTO statsDTO = template.queryForObject(query,new StatsSpecialRowMapper(), year, year, year, year, year, year);
            return statsDTO;
    }

//    public StatsDTO createStatDTO(int year) {
//        String query = "SELECT id.fiscal_year AS 'YEAR'," +
//        "COUNT(DISTINCT IF(id.mem_type = 'RM' AND id.RENEW=true,id.membership_id , NULL)) AS 'REGULAR'," +
//        "COUNT(DISTINCT IF(id.mem_type = 'FM' AND id.RENEW=true,id.membership_id , NULL)) AS 'FAMILY'," +
//        "COUNT(DISTINCT IF(id.mem_type = 'SO' AND id.RENEW=true,id.membership_id , NULL)) AS 'SOCIAL'," +
//        "COUNT(DISTINCT IF(id.mem_type = 'LA' AND id.RENEW=true,id.membership_id , NULL)) AS 'LAKE_ASSOCIATES'," +
//        "COUNT(DISTINCT IF(id.mem_type = 'LM' AND id.RENEW=true,id.membership_id , NULL)) AS 'LIFE_MEMBERS'," +
//        "COUNT(DISTINCT IF(id.mem_type = 'SM' AND id.RENEW=true,id.membership_id , NULL)) AS 'STUDENT'," +
//        "COUNT(DISTINCT IF(id.mem_type = 'RF' AND id.RENEW=true,id.membership_id , NULL)) AS 'RACE_FELLOWS'," +
//        "COUNT(DISTINCT IF(YEAR(m.JOIN_DATE)="+year+",id.membership_id, NULL)) AS 'NEW_MEMBERS'," +
//        "COUNT(DISTINCT IF(id.membership_id >" +
//        "(SELECT membership_id FROM membership_id WHERE fiscal_year=? AND MS_ID=(SELECT MS_ID FROM membership_id WHERE membership_id=(" +
//        "SELECT max(membership_id) FROM membership_id WHERE fiscal_year=("+year+" -1)AND membership_id < 500 AND renew=1)" +
//        "AND fiscal_year=("+year+" -1))) AND id.membership_id < 500 AND YEAR(m.JOIN_DATE)!=?" +
//        "AND (SELECT NOT EXISTS(SELECT mid FROM membership_id WHERE fiscal_year=("+year+" -1) AND RENEW=1 AND MS_ID=id.MS_ID)), id.membership_id, NULL)) AS 'RETURN_MEMBERS'," +
//        "SUM(NOT RENEW) as 'NON_RENEW', SUM(RENEW) as 'ACTIVE_MEMBERSHIPS'" +
//        "FROM membership_id id LEFT JOIN membership m on id.MS_ID=m.MS_ID " +
//        "WHERE fiscal_year="+year+";";
//        return template.queryForObject(query,new StatsSpecialRowMapper());
//    }

    @Override
    public int getNumberOfStatYears() {
        return 0;
    }

    @Override
    public int deleteAllStats() {
        String query = "DELETE FROM stats";
        return template.update(query);
    }

    @Override
    public int addStatRecord(StatsDTO s) {
        String query = "INSERT INTO stats (fiscal_year, active_memberships, non_renew, return_members," +
                " new_members, secondary_members, dependants, number_of_boats, family, regular, social, " +
                "lake_associates, life_members, race_fellows, student, deposits, initiation) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return template.update(query, s.getFiscalYear(), s.getActiveMemberships(), s.getNonRenewMemberships(),
                s.getReturnMemberships(), s.getNewMemberships(), s.getSecondaryMembers(), s.getDependants(),
                s.getNumberOfBoats(), s.getFamily(), s.getRegular(), s.getSocial(), s.getLakeAssociates(),
                s.getLifeMembers(), s.getRaceFellows(), s.getStudent(), s.getDeposits(), s.getInitiation());
    }
}
