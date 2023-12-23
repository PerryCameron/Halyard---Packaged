package com.ecsail.repository.interfaces;

import com.ecsail.dto.AppSettingsDTO;
import com.ecsail.jotform.structures.ApiKeyDTO;

public interface AppSettingsRepository {
    AppSettingsDTO getSettingFromKey(String key);

    ApiKeyDTO getApiKeyByName(String name);
}
