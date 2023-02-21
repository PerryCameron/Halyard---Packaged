package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.dto.BoardPositionDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlBoardPositions {

    public static ArrayList<BoardPositionDTO> getPositions() {
        ArrayList<BoardPositionDTO> thisBoardPosition = new ArrayList<>();
        String query = "SELECT * FROM board_positions";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisBoardPosition.add(new BoardPositionDTO(
                        rs.getInt("ID"),
                        rs.getString("POSITION"),
                        rs.getString("IDENTIFIER"), // beginning of board term
                        rs.getInt("LIST_ORDER"),
                        rs.getBoolean("IS_OFFICER"),
                        rs.getBoolean("IS_CHAIR"),
                        rs.getBoolean("IS_ASSISTANT_CHAIR")
                        ));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisBoardPosition;
    }

    public static String getByIdentifier(String code) {
        String query = "select position from board_positions where identifier='" + code + "'";
        String position = null;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                       position =  rs.getString("POSITION");
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return position;
    }

    public static String getByName(String name) {
        String query = "select identifier from board_positions where position='" + name + "'";
        String position = null;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                position =  rs.getString("POSITION");
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return position;
    }
}
