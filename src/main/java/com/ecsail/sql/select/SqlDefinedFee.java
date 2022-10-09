package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.structures.DefinedFeeDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlDefinedFee {
    public static ObservableList<DefinedFeeDTO> getDefinedFees() {
        ObservableList<DefinedFeeDTO> thisDefinedFee = FXCollections.observableArrayList();
        String query = "SELECT * FROM defined_fee";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisDefinedFee.add(new DefinedFeeDTO(
                        rs.getInt("FISCAL_YEAR"),
                        rs.getBigDecimal("DUES_REGULAR"),
                        rs.getBigDecimal("DUES_FAMILY"),
                        rs.getBigDecimal("DUES_LAKE_ASSOCIATE"),
                        rs.getBigDecimal("DUES_SOCIAL"),
                        rs.getBigDecimal("INITIATION"),
                        rs.getBigDecimal("WET_SLIP"),
                        rs.getBigDecimal("BEACH"),
                        rs.getBigDecimal("WINTER_STORAGE"),
                        rs.getBigDecimal("MAIN_GATE_KEY"),
                        rs.getBigDecimal("SAIL_LOFT"),
                        rs.getBigDecimal("SAIL_LOFT_KEY"),
                        rs.getBigDecimal("SAIL_SCHOOL_LASER_LOFT"),
                        rs.getBigDecimal("SAIL_SCHOOL_LOFT_KEY"),
                        rs.getBigDecimal("KAYAK_RACK"),
                        rs.getBigDecimal("KAYAK_BEACH_RACK"),
                        rs.getBigDecimal("KAYAK_SHED"),
                        rs.getBigDecimal("KAYAK_SHED_KEY"),
                        rs.getBigDecimal("WORK_CREDIT")
                ));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
        }
        return thisDefinedFee;
    }

    // to create a single defined fee object and fille it with a selected year
    public static DefinedFeeDTO getDefinedFeeByYear(String year) {
        DefinedFeeDTO definedFee = null;
        String query = "SELECT * FROM defined_fee WHERE fiscal_year=" + year;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                definedFee = new DefinedFeeDTO(
                        rs.getInt("FISCAL_YEAR"),
                        rs.getBigDecimal("DUES_REGULAR"),
                        rs.getBigDecimal("DUES_FAMILY"),
                        rs.getBigDecimal("DUES_LAKE_ASSOCIATE"),
                        rs.getBigDecimal("DUES_SOCIAL"),
                        rs.getBigDecimal("INITIATION"),
                        rs.getBigDecimal("WET_SLIP"),
                        rs.getBigDecimal("BEACH"),
                        rs.getBigDecimal("WINTER_STORAGE"),
                        rs.getBigDecimal("MAIN_GATE_KEY"),
                        rs.getBigDecimal("SAIL_LOFT"),
                        rs.getBigDecimal("SAIL_LOFT_KEY"),
                        rs.getBigDecimal("SAIL_SCHOOL_LASER_LOFT"),
                        rs.getBigDecimal("SAIL_SCHOOL_LOFT_KEY"),
                        rs.getBigDecimal("KAYAK_RACK"),
                        rs.getBigDecimal("KAYAK_BEACH_RACK"),
                        rs.getBigDecimal("KAYAK_SHED"),
                        rs.getBigDecimal("kAYAK_SHED_KEY"),
                        rs.getBigDecimal("WORK_CREDIT"));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
        }
        return definedFee;
    }
}

