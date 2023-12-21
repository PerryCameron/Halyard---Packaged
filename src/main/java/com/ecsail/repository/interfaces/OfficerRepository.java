package com.ecsail.repository.interfaces;



import com.ecsail.dto.OfficerDTO;
import com.ecsail.dto.OfficerWithNameDTO;
import com.ecsail.dto.PersonDTO;

import java.util.List;

public interface OfficerRepository {
    List<OfficerDTO> getOfficers();
    List<OfficerDTO> getOfficer(String field, int attribute);
    List<OfficerDTO> getOfficer(PersonDTO person);
    List<OfficerWithNameDTO> getOfficersWithNames(String type);
    int update(OfficerDTO o);
    int insert(OfficerDTO o);
    OfficerDTO insertOfficer(OfficerDTO officer);
    void delete(int pId);
    int deleteOfficer(OfficerDTO officerDTO);
}
