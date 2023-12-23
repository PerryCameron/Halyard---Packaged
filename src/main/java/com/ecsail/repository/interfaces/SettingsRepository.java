package com.ecsail.repository.interfaces;

import com.ecsail.dto.DbBoatSettingsDTO;
import com.ecsail.dto.DbRosterSettingsDTO;
import com.ecsail.dto.MembershipListRadioDTO;
import com.ecsail.jotform.structures.ApiKeyDTO;
import com.ecsail.views.tabs.boatlist.BoatListRadioDTO;

import java.util.List;

public interface SettingsRepository {

    List<DbRosterSettingsDTO> getSearchableListItems();
    List<MembershipListRadioDTO> getRadioChoices();
    List<DbBoatSettingsDTO> getBoatSettings();
    List<BoatListRadioDTO> getBoatRadioChoices();

}
