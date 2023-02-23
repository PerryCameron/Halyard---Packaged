package com.ecsail.repository.interfaces;

import com.ecsail.dto.BoatListDTO;

import java.util.List;

public interface BoatRepository {

    List<BoatListDTO> getActiveSailBoats();
    List<BoatListDTO> getActiveAuxBoats();
    List<BoatListDTO> getAllSailBoats();
    List<BoatListDTO> getAllAuxBoats();
    List<BoatListDTO> getAllBoats();
}
