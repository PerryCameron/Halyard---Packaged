package com.ecsail.repository.interfaces;

import com.ecsail.dto.DbMembershipListDTO;

import java.util.List;

public interface SettingsRepository {

    List<DbMembershipListDTO> getSearchableListItems();
}
