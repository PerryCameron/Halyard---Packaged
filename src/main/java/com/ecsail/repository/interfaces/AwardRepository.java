package com.ecsail.repository.interfaces;


import com.ecsail.dto.AwardDTO;
import com.ecsail.dto.PersonDTO;

import java.util.List;

public interface AwardRepository {
    List<AwardDTO> getAwards(PersonDTO p);
    List<AwardDTO> getAwards();
    int update(AwardDTO o);
    int insert(AwardDTO o);

    AwardDTO insertAward(AwardDTO award);

    int delete(AwardDTO awardDTO);
}
