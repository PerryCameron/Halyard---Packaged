package com.ecsail.repository.interfaces;

import com.ecsail.dto.DbRosterSettingsDTO;
import com.ecsail.dto.MembershipListRadioDTO;

import java.util.List;

public interface SettingsRepository {

    List<DbRosterSettingsDTO> getSearchableListItems();
    List<MembershipListRadioDTO> getRadioChoices();
}
