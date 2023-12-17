package com.ecsail.repository.interfaces;


import com.ecsail.dto.EmailDTO;
import com.ecsail.dto.Email_InformationDTO;
import com.ecsail.dto.PersonDTO;

import java.util.List;

public interface EmailRepository {
    List<Email_InformationDTO> getEmailInfo();
    List<EmailDTO> getEmail(int p_id);
    EmailDTO getPrimaryEmail(PersonDTO person);
    int update(EmailDTO o);
    int insert(EmailDTO emailDTO);
    int delete(EmailDTO o);

    boolean emailFromActiveMembershipExists(String email, int year);

    boolean emailExists(PersonDTO p);

    String getEmail(PersonDTO person);

    boolean deleteEmail(EmailDTO email);

    void updateEmail(int email_id, String email);

    void updateEmail(String field, int email_id, Boolean attribute);

    EmailDTO insertEmail(EmailDTO emailDTO);
}