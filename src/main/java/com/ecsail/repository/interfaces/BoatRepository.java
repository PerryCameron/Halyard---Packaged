package com.ecsail.repository.interfaces;

import com.ecsail.dto.BoatDTO;
import com.ecsail.dto.BoatListDTO;
import com.ecsail.dto.BoatOwnerDTO;
import com.ecsail.dto.BoatPhotosDTO;

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

    void deleteBoatPhoto(BoatPhotosDTO bp);
    int deleteBoatOwner(int boat_id, int ms_id);
    BoatPhotosDTO insertBoatImage(BoatPhotosDTO bp);

    void updateBoatImages(BoatPhotosDTO bp);

    void updateBoat(BoatDTO boat);

    BoatDTO insertBoat(BoatDTO boat);
}
