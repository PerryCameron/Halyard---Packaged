package com.ecsail.repository.implementations;

import com.ecsail.BaseApplication;
import com.ecsail.dto.AppSettingsDTO;
import com.ecsail.dto.BoatDTO;
import com.ecsail.dto.BoatListDTO;
import com.ecsail.dto.BoatOwnerDTO;
import com.ecsail.repository.interfaces.AppSettings;
import com.ecsail.repository.interfaces.BoatRepository;
import com.ecsail.repository.rowmappers.AppSettingsRowMapper;
import com.ecsail.repository.rowmappers.BoatListRowMapper;
import com.ecsail.repository.rowmappers.BoatOwnerRowMapper;
import com.ecsail.repository.rowmappers.BoatRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class AppSettingsImpl implements AppSettings {
    private final JdbcTemplate template;

    public AppSettingsImpl() {
        this.template = new JdbcTemplate(BaseApplication.getDataSource());
    }

    public AppSettingsDTO getSettingFromKey(String key) {
        String sql = "SELECT * FROM app_settings WHERE setting_key = ?";
        try {
            return template.queryForObject(sql, new AppSettingsRowMapper(), key);
        } catch (EmptyResultDataAccessException e) {
            // Handle case where no setting is found for the given key
            return null;
        }
    }


}
