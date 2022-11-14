package com.ecsail.enums;

import com.ecsail.BaseApplication;
import com.ecsail.sql.select.SqlBoardPositions;
import com.ecsail.structures.BoardPositionDTO;

import java.util.ArrayList;
import java.util.Arrays;

// this used to be an ENUM but changed it to a class and pull info from
// the database and kept it acting like an enum.
public class Officer {

    public static ArrayList<BoardPositionDTO> getPositionList() {
        return SqlBoardPositions.getPositions();
    }

    public static String getByCode(String officerCode) {
        return BaseApplication.boardPositions
                .stream()
                .filter(p -> p.identifier().equals(officerCode))
                .map(p -> p.position()).findFirst().orElse("not found");
    }

    public static String getByName(String name) {
        return BaseApplication.boardPositions
                .stream()
                .filter(p -> p.position().equals(name))
                .map(p -> p.identifier()).findFirst().orElse("not found");
    }


}
