package com.ecsail.repository.interfaces;

import com.ecsail.dto.AppSettingsDTO;
import com.ecsail.jotform.structures.ApiKeyDTO;

public interface AppSettingsRepository {
    AppSettingsDTO getSettingFromKey(String key);

    AppSettingsDTO getSettingByGroup(String group);

    ApiKeyDTO getApiKeyByName(String name);
}
