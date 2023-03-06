package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.views.dialogues.Dialogue_ErrorSQL;
import com.ecsail.dto.BoardDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlBoard {
    // select p.P_ID, p.MS_ID, o.O_ID, p.F_NAME, p.L_NAME, o.OFF_YEAR, o.BOARD_YEAR, o.OFF_TYPE  from person p inner join officer o on p.p_id = o.p_id where o.off_year='2020';
    public static ObservableList<BoardDTO> getBoard(String currentYear) {  //p_id
        ObservableList<BoardDTO> thisBoardMember = FXCollections.observableArrayList();
        String query = "SELECT p.p_id, p.ms_id, o.o_id, p.f_name, p.l_name, o.off_year, o.board_year, o.off_type  FROM person p INNER JOIN officer o ON p.p_id = o.p_id WHERE o.off_year='" + currentYear + "'";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisBoardMember.add(new BoardDTO(
                        rs.getInt("p_id"),
                        rs.getInt("ms_id"),
                        rs.getInt("o_id"),
                        rs.getString("f_name"),
                        rs.getString("l_name"),
                         rs.getString("off_year"),
                        rs.getString("board_year"),
                        rs.getString("off_type")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisBoardMember;
    }
}
