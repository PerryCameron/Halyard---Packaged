package com.ecsail.repository.implementations;

import com.ecsail.BaseApplication;
import com.ecsail.dto.AppSettingsDTO;
import com.ecsail.jotform.structures.ApiKeyDTO;
import com.ecsail.repository.interfaces.AppSettingsRepository;
import com.ecsail.repository.rowmappers.ApiKeyRowMapper;
import com.ecsail.repository.rowmappers.AppSettingsRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class AppSettingsRepositoryImpl implements AppSettingsRepository {

    public static Logger logger = LoggerFactory.getLogger(AppSettingsRepository.class);
    private final JdbcTemplate template;

    public AppSettingsRepositoryImpl() {
        this.template = new JdbcTemplate(BaseApplication.getDataSource());
    }
    @Override
    public AppSettingsDTO getSettingFromKey(String key) {
        String sql = "SELECT * FROM app_settings WHERE setting_key = ?";
        try {
            return template.queryForObject(sql, new AppSettingsRowMapper(), key);
        } catch (EmptyResultDataAccessException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
    @Override
    public AppSettingsDTO getSettingByGroup(String group) {
        String sql = "SELECT * FROM app_settings WHERE group_name = ?";
        try {
            return template.queryForObject(sql, new AppSettingsRowMapper(), group);
        } catch (EmptyResultDataAccessException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
    @Override
    public ApiKeyDTO getApiKeyByName(String name) {
        String query = "SELECT * FROM api_key WHERE NAME = ?";
        try {
            return template.queryForObject(query, new Object[]{name}, new ApiKeyRowMapper());
        } catch (Exception e) {
            logger.error("Unable to retrieve API key information", e);
            return null; // Return null in case of failure
        }
    }

}
