package com.ecsail.repository.implementations;

import com.ecsail.BaseApplication;
import com.ecsail.dto.DbBoatSettingsDTO;
import com.ecsail.dto.DbRosterSettingsDTO;
import com.ecsail.dto.MembershipListRadioDTO;
import com.ecsail.jotform.structures.JotFormSettingsDTO;
import com.ecsail.repository.rowmappers.*;
import com.ecsail.views.tabs.boatlist.BoatListRadioDTO;
import com.ecsail.repository.interfaces.SettingsRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SettingsRepositoryImpl implements SettingsRepository {

    private JdbcTemplate template;

    public SettingsRepositoryImpl() {
        this.template = new JdbcTemplate(BaseApplication.getDataSource());
    }

    @Override
    public List<DbRosterSettingsDTO> getSearchableListItems() {
        String query = "SELECT * FROM db_roster_settings";
        List<DbRosterSettingsDTO> dbMembershipListDTOS
                = template.query(query, new DbRosterSettingsRowMapper());
        return dbMembershipListDTOS;
    }

    @Override
    public List<MembershipListRadioDTO> getRadioChoices() {
        String query = "SELECT * FROM db_roster_radio_selection";
        List<MembershipListRadioDTO> dbMembershipListDTOS
                = template.query(query, new DbMembershipListRadioRowMapper());
        return dbMembershipListDTOS;
    }

    @Override
    public List<DbBoatSettingsDTO> getBoatSettings() {
        String query = "SELECT * from db_boat_list_settings";
        List<DbBoatSettingsDTO> dbBoatSettingsDTOS
                = template.query(query,new DbBoatSettingsRowMapper());
        return dbBoatSettingsDTOS;
    }

    @Override
    public List<BoatListRadioDTO> getBoatRadioChoices() {
        String query = "select * from db_boat_list_radio_selection";
        List<BoatListRadioDTO> dbBoatSettingsDTOS
                = template.query(query,new BoatListRadioRowMapper());
        return dbBoatSettingsDTOS;
    }

    @Override
    public List<JotFormSettingsDTO> getJotFormSettings(long id) {
        String query = "select * from jotform_settings where form_number= ?";
        List<JotFormSettingsDTO> jotFormSettingsDTOS = template.query(query, new JotFormsSettingsRowMapper(), id);
        return jotFormSettingsDTOS;
    }
}
