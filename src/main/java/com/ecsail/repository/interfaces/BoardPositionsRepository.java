package com.ecsail.repository.interfaces;

import com.ecsail.dto.BoardDTO;
import com.ecsail.dto.BoardPositionDTO;

import java.util.List;

public interface BoardPositionsRepository {
    List<BoardPositionDTO> getPositions();

    List<BoardDTO> getBoard(String currentYear);

    String getByIdentifier(String code);

    String getByName(String name);
}
