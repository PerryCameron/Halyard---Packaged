package com.ecsail.repository.interfaces;

import com.ecsail.dto.OfficerWithNameDTO;
import com.ecsail.pdf.directory.PDF_Object_Officer;

import java.util.List;

public interface PDFRepository {
    List<PDF_Object_Officer> getOfficersByYear(String selectedYear);

    List<OfficerWithNameDTO> getOfficersWithNames(String type);
}
