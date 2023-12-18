package com.ecsail.repository.interfaces;

import com.ecsail.dto.MembershipDTO;
import com.ecsail.dto.PersonDTO;

import java.util.ArrayList;
import java.util.List;

public interface PersonRepository {

//    List<PersonDTO> getPeople(int ms_id);

    List<PersonDTO> getActivePeopleByMsId(int ms_id);

    void deletePerson(PersonDTO p);

    ArrayList<PersonDTO> getDependants(MembershipDTO m);

    PersonDTO getPerson(int ms_id, int member_type);

    boolean activePersonExists(int ms_id, int member_type);

    PersonDTO getPersonByPid(int pid);

    void deletePerson(int p_id);

    List<PersonDTO> getPeople(int ms_id);

    void updatePerson(PersonDTO p);

    void removePersonFromMembership(PersonDTO p);
//    List<PersonDTO> getActiveAuxBoats();
//    List<PersonDTO> getAllSailBoats();
//    List<PersonDTO> getAllAuxBoats();
//    List<PersonDTO> getAllBoats();
}
