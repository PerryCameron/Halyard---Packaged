package com.ecsail.enums;

import com.ecsail.BaseApplication;
import com.ecsail.sql.select.SqlBoardPositions;
import com.ecsail.dto.BoardPositionDTO;

import java.util.ArrayList;

/**
 * this used to be an ENUM but changed it to a class and pull info from
 * the database and kept it acting somewhat like an enum. This gives the
 * versatility to add, delete, and edit positions, and also allowed for
 * clearer code by enabling more columns of information
 */

public class Officer {
    /**
     * This is the most appropriate location to create an Array list of board positions
     * @return ArrayList<BoardPositionDTO>
     */
    public static ArrayList<BoardPositionDTO> getPositionList() {
        return SqlBoardPositions.getPositions();
    }

    /**
     * Will return the full name of the position, when give a two character code.
     * This feature is for reducing database storage size needed
     * @param officerCode - two character code
     * @return String - full name of position
     */
    public static String getByCode(String officerCode) {
        return BaseApplication.boardPositions
                .stream()
                .filter(p -> p.identifier().equals(officerCode))
                .map(p -> p.position()).findFirst().orElse("not found");
    }

    /**
     * Well return a two character code when given the full name of a position
     * @param name String the full name of a position
     * @return String the two character code that represents given position name
     */
    public static String getByName(String name) {
        return BaseApplication.boardPositions
                .stream()
                .filter(p -> p.position().equals(name))
                .map(p -> p.identifier()).findFirst().orElse("not found");
    }

}
