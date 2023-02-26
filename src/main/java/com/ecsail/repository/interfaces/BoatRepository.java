package com.ecsail.repository.interfaces;

import com.ecsail.dto.BoatDTO;
import com.ecsail.dto.BoatListDTO;
import com.ecsail.dto.BoatOwnerDTO;

import java.util.List;

public interface BoatRepository {

    List<BoatListDTO> getActiveSailBoats();
    List<BoatListDTO> getActiveAuxBoats();
    List<BoatListDTO> getAllSailBoats();
    List<BoatListDTO> getAllAuxBoats();
    List<BoatListDTO> getAllBoatLists();
    List<BoatDTO> getAllBoats();
    List<BoatDTO> getBoatsByMsId(int msId);
    List<BoatDTO> getOnlySailboatsByMsId(int msId);
    List<BoatOwnerDTO> getBoatOwners();
}
