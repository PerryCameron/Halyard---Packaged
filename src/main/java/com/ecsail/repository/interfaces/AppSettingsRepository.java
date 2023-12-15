package com.ecsail.repository.interfaces;

import com.ecsail.dto.AppSettingsDTO;

public interface AppSettingsRepository {
    AppSettingsDTO getSettingFromKey(String key);
}
