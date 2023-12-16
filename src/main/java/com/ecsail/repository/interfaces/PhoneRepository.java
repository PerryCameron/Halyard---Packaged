package com.ecsail.repository.interfaces;


import com.ecsail.dto.PersonDTO;
import com.ecsail.dto.PhoneDTO;

import java.util.List;

public interface PhoneRepository {
    List<PhoneDTO> getPhoneByPid(int p_id);
    List<PhoneDTO> getPhoneByPerson(PersonDTO p);
    PhoneDTO getListedPhoneByType(PersonDTO p, String type);
    PhoneDTO getPhoneByPersonAndType(int pId, String type);
    int update(PhoneDTO o);
    int delete(PhoneDTO o);
    int insert(PhoneDTO o);
}
