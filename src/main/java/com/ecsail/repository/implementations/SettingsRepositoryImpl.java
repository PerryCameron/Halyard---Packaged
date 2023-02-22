package com.ecsail.repository.implementations;

import com.ecsail.BaseApplication;
import com.ecsail.dto.DbMembershipListDTO;
import com.ecsail.repository.interfaces.SettingsRepository;
import com.ecsail.repository.rowmappers.DbMembershipListRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SettingsRepositoryImpl implements SettingsRepository {

    private JdbcTemplate template;

    public SettingsRepositoryImpl() {
        this.template = new JdbcTemplate(BaseApplication.getDataSource());
    }

    @Override
    public List<DbMembershipListDTO> getSearchableListItems() {
        String query = "SELECT * FROM db_membership_list";
        List<DbMembershipListDTO> dbMembershipListDTOS
                = template.query(query, new DbMembershipListRowMapper());
        return dbMembershipListDTOS;
    }
}
