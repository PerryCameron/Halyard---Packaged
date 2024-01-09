package com.ecsail.repository.implementations;


import com.ecsail.BaseApplication;
import com.ecsail.dto.StatsDTO;
import com.ecsail.repository.interfaces.StatRepository;
import com.ecsail.repository.rowmappers.StatsRowMapper;
import com.ecsail.repository.rowmappers.StatsSpecialRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class StatRepositoryImpl implements StatRepository {
    public static Logger logger = LoggerFactory.getLogger(StatRepository.class);

    private final JdbcTemplate template;

    public StatRepositoryImpl() {
        this.template = new JdbcTemplate(BaseApplication.getDataSource());
    }


    @Override
    public List<StatsDTO> getStatistics(int startYear, int stopYear) {
        String query = "SELECT * FROM stats WHERE fiscal_year > " + (startYear - 1) + " AND fiscal_year < " + (stopYear + 1);
        return template.query(query, new StatsRowMapper());
    }

    @Override
    public List<StatsDTO> createStatDTO(int year, int statID) {
        return null;
    }

    @Override
    public StatsDTO createStatDTO(int year) {
        final String sql = """
                SELECT id.FISCAL_YEAR AS 'YEAR',
                    COUNT(DISTINCT CASE WHEN id.MEM_TYPE = 'RM' AND id.RENEW = true THEN id.MEMBERSHIP_ID ELSE NULL END) AS 'REGULAR',
                    COUNT(DISTINCT CASE WHEN id.MEM_TYPE = 'FM' AND id.RENEW = true THEN id.MEMBERSHIP_ID ELSE NULL END) AS 'FAMILY',
                    COUNT(DISTINCT CASE WHEN id.MEM_TYPE = 'SO' AND id.RENEW = true THEN id.MEMBERSHIP_ID ELSE NULL END) AS 'SOCIAL',
                    COUNT(DISTINCT CASE WHEN id.MEM_TYPE = 'LA' AND id.RENEW = true THEN id.MEMBERSHIP_ID ELSE NULL END) AS 'LAKE_ASSOCIATES',
                    COUNT(DISTINCT CASE WHEN id.MEM_TYPE = 'LM' AND id.RENEW = true THEN id.MEMBERSHIP_ID ELSE NULL END) AS 'LIFE_MEMBERS',
                    COUNT(DISTINCT CASE WHEN id.MEM_TYPE = 'SM' AND id.RENEW = true THEN id.MEMBERSHIP_ID ELSE NULL END) AS 'STUDENT',
                    COUNT(DISTINCT CASE WHEN id.MEM_TYPE = 'RF' AND id.RENEW = true THEN id.MEMBERSHIP_ID ELSE NULL END) AS 'RACE_FELLOWS',
                    COUNT(DISTINCT CASE WHEN YEAR(m.JOIN_DATE) = ? THEN id.MEMBERSHIP_ID ELSE NULL END) AS 'NEW_MEMBERS',
                    COUNT(DISTINCT CASE
                WHEN id.MEMBERSHIP_ID < 500 AND YEAR(m.JOIN_DATE) != ?
                AND NOT EXISTS (
                    SELECT 1 FROM membership_id mi
                    WHERE mi.FISCAL_YEAR = (? - 1) AND mi.RENEW = 1 AND mi.MS_ID = id.MS_ID
                    )
                    AND id.MEMBERSHIP_ID > (
                    SELECT MAX(mi.MEMBERSHIP_ID)
                    FROM membership_id mi
                    WHERE mi.FISCAL_YEAR = (? - 1) AND mi.MEMBERSHIP_ID < 500 AND mi.RENEW = 1
                    )
                    THEN id.MEMBERSHIP_ID ELSE NULL END
                    ) AS 'RETURN_MEMBERS',
                    SUM(NOT id.RENEW) as 'NON_RENEW',
                    SUM(id.RENEW) as 'ACTIVE_MEMBERSHIPS'
                    FROM membership_id id
                    LEFT JOIN membership m ON id.MS_ID = m.MS_ID
                    WHERE id.FISCAL_YEAR = ?
                    GROUP BY id.FISCAL_YEAR
                        """;
        try {
            return template.queryForObject(sql, new Object[]{year, year, year, year, year}, new StatsSpecialRowMapper());
        } catch (DataAccessException e) {
            logger.error("Error creating stats for year " + year + ": " + e.getMessage());
            return null;
        }
    }


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
    public int insertStat(StatsDTO s) {
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
