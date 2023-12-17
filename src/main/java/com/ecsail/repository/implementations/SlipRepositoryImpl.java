package com.ecsail.repository.implementations;

import com.ecsail.BaseApplication;
import com.ecsail.dto.MembershipListDTO;
import com.ecsail.dto.SlipDTO;
import com.ecsail.dto.WaitListDTO;
import com.ecsail.repository.interfaces.SlipRepository;
import com.ecsail.repository.rowmappers.SlipRowMapper;
import com.ecsail.repository.rowmappers.WaitListRowMapper;
import com.ecsail.views.dialogues.Dialogue_ErrorSQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class SlipRepositoryImpl implements SlipRepository {

    public static Logger logger = LoggerFactory.getLogger(SlipRepositoryImpl.class);
    private JdbcTemplate template;

    public SlipRepositoryImpl() {
        this.template = new JdbcTemplate(BaseApplication.getDataSource());
    }

    @Override
    public void updateWaitList(int ms_id, String field, Boolean attribute) {
        String sql = "UPDATE wait_list SET " + field + " = ? WHERE ms_id = ?";
        template.update(sql, attribute, ms_id);
    }

    @Override
    public Boolean waitListExists(int ms_id) {
        String sql = "SELECT EXISTS(SELECT * FROM wait_list WHERE ms_id = ?) AS wait_list_exists";
        return template.queryForObject(sql, Boolean.class, ms_id);
    }

    @Override
    public WaitListDTO getWaitList(int ms_id) {
        String sql = "SELECT * FROM wait_list WHERE ms_id = ?";
        try {
            return template.queryForObject(sql, new WaitListRowMapper(), ms_id);
        } catch (EmptyResultDataAccessException e) {
            logger.error(e.getMessage());
            return null;
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
            return null;
        }
    }

    @Override
    public void insertWaitList(WaitListDTO w) {
        String sql = "INSERT INTO wait_list (MS_ID, SLIP_WAIT, KAYAK_RACK_WAIT, SHED_WAIT, WANT_SUBLEASE, WANT_RELEASE, WANT_SLIP_CHANGE) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        template.update(sql,
                w.getMs_id(),
                w.isSlipWait(),
                w.isKayakWait(),
                w.isShedWait(),
                w.isWantToSublease(),
                w.isWantsRelease(),
                w.isWantSlipChange());
    }

    @Override
    public void releaseSlip(MembershipListDTO membership) {
        String sql = "UPDATE slip SET subleased_to = null WHERE ms_id = ?";
        template.update(sql, membership.getMsId());
        membership.setSubLeaser(0);
    }

    @Override
    public void reAssignSlip(int msId, MembershipListDTO membership) {
        String sql = "UPDATE slip SET ms_id = ? WHERE ms_id = ?";
        template.update(sql, msId, membership.getMsId());
        String slip = membership.getSlip();
        membership.setSlip("0");
    }

    @Override
    public void subleaserReleaseSlip(int subleasee) {
        String sql = "UPDATE slip SET subleased_to = null WHERE subleased_to = ?";
        template.update(sql, subleasee);
    }

    @Override
    public Boolean slipRentExists(int subMsid) {
        String sql = "SELECT EXISTS(SELECT * FROM slip WHERE subleased_to = ?)";
        return template.queryForObject(sql, Boolean.class, subMsid);
    }

    @Override
    public void updateSlip(int ms_id, MembershipListDTO membership) {
        String sql = "UPDATE slip SET subleased_to = ? WHERE ms_id = ?";
        template.update(sql, ms_id, membership.getMsId());
        membership.setSubLeaser(ms_id);
    }

    @Override
    public SlipDTO getSlip(int ms_id) {
        String sql = "SELECT * FROM slip WHERE ms_id = ?";
        try {
            return template.queryForObject(sql, new SlipRowMapper(), ms_id);
        } catch (EmptyResultDataAccessException e) {
            logger.error(e.getMessage());
            return null;
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
            return null;
        }
    }

    @Override
    public Boolean slipExists(int ms_id) {
        String sql = "SELECT EXISTS(SELECT * FROM slip WHERE ms_id = ?)";
        return template.queryForObject(sql, Boolean.class, ms_id);
    }

    @Override
    public SlipDTO getSubleasedSlip(int ms_id) {
        String sql = "SELECT * FROM slip WHERE subleased_to = ?";
        try {
            return template.queryForObject(sql, new SlipRowMapper(), ms_id);
        } catch (EmptyResultDataAccessException e) {
            logger.error(e.getMessage());
            return null;
        } catch (DataAccessException e) {
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteWaitList(int msId) {
        String sql = "DELETE FROM wait_list WHERE ms_id = ?";
        try {
            template.update(sql, msId);
        } catch (DataAccessException e) {
            logger.error("Unable to DELETE wait list entry: " + e.getMessage());
        }
    }


}
