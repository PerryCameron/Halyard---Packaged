package com.ecsail.repository.interfaces;

import com.ecsail.dto.DbMembershipListDTO;
import com.ecsail.dto.MembershipListRadioDTO;

import java.util.List;

public interface SettingsRepository {

    List<DbMembershipListDTO> getSearchableListItems();
    List<MembershipListRadioDTO> getRadioChoices();
}
