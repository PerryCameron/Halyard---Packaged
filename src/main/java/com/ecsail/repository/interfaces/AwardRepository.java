package com.ecsail.repository.interfaces;


import com.ecsail.dto.AwardDTO;
import com.ecsail.dto.PersonDTO;
import com.ecsail.pdf.directory.Object_Sportsmen;

import java.util.List;

public interface AwardRepository {
    List<AwardDTO> getAwards(PersonDTO p);
    List<AwardDTO> getAwards();
    int updateAward(AwardDTO awardDTO);

    int insert(AwardDTO o);

    AwardDTO insertAward(AwardDTO award);

    int delete(AwardDTO awardDTO);

    List<Object_Sportsmen> getSportsManAwardNames();
}
