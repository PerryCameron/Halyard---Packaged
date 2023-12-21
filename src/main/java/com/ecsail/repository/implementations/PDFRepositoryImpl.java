package com.ecsail.repository.implementations;

import com.ecsail.BaseApplication;
import com.ecsail.dto.OfficerWithNameDTO;
import com.ecsail.pdf.directory.PDF_Object_Officer;
import com.ecsail.repository.interfaces.PDFRepository;
import com.ecsail.repository.rowmappers.OfficerWithNameRowMapper;
import com.ecsail.repository.rowmappers.PDFObjectOfficerRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;
import java.util.List;

public class PDFRepositoryImpl implements PDFRepository {

    public static Logger logger = LoggerFactory.getLogger(PDFRepository.class);
    private final JdbcTemplate template;
    public PDFRepositoryImpl() {
        this.template = new JdbcTemplate(BaseApplication.getDataSource());
    }
    @Override
    public List<PDF_Object_Officer> getOfficersByYear(String selectedYear) {
        String sql = """
        SELECT * FROM officer o 
        LEFT JOIN person p ON o.p_id = p.p_id 
        WHERE off_year = ?
        """;
        try {
            return template.query(sql, new Object[]{selectedYear}, new PDFObjectOfficerRowMapper());
        } catch (DataAccessException e) {
            logger.error("Unable to retrieve officer information for year " + selectedYear + ": " + e.getMessage());
            return Collections.emptyList();
        }
    }
    @Override
    public List<OfficerWithNameDTO> getOfficersWithNames(String type) {
        String sql = """
        SELECT f_name, L_NAME, off_year 
        FROM officer o 
        LEFT JOIN person p ON o.p_id = p.p_id 
        WHERE off_type = ?
        """;
        try {
            return template.query(sql, new Object[]{type}, new OfficerWithNameRowMapper());
        } catch (DataAccessException e) {
            logger.error("Unable to retrieve officers with names for type " + type + ": " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
