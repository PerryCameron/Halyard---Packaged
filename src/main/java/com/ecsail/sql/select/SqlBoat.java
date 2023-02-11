package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.structures.BoatDTO;
import com.ecsail.structures.BoatListDTO;
import com.ecsail.structures.BoatOwnerDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlBoat {
    // was a list
    public static ObservableList<BoatOwnerDTO> getBoatOwners() {
        ObservableList<BoatOwnerDTO> thisBoatOwner = FXCollections.observableArrayList();
        String query = "SELECT * FROM boat_owner";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisBoatOwner.add(new BoatOwnerDTO(
                        rs.getInt("ms_id"),
                        rs.getInt("boat_id")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisBoatOwner;
    }

    public static ObservableList<BoatDTO> getBoats() {
        ObservableList<BoatDTO> thisBoat = FXCollections.observableArrayList();
        String query = "SELECT * FROM boat";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisBoat.add(new BoatDTO(
                        rs.getInt("boat_id"), 0, // because Object_Boat has a ms-id variable but database does not
                        rs.getString("manufacturer"), // might be the best note I have ever left ^^ lol
                        rs.getString("manufacture_year"),
                        rs.getString("registration_num"),
                        rs.getString("model"),
                        rs.getString("boat_name"),
                        rs.getString("sail_number"),
                        rs.getBoolean("has_trailer"),
                        rs.getString("length"),
                        rs.getString("weight"),
                        rs.getString("keel"),
                        rs.getString("phrf"),
                        rs.getString("draft"),
                        rs.getString("beam"),
                        rs.getString("lwl"),
                        rs.getBoolean("aux")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisBoat;
    }
    // gets all boats and kayak for all time
    public static ObservableList<BoatListDTO> getBoatsWithOwners() {
        ObservableList<BoatListDTO> thisBoat = FXCollections.observableArrayList();
        String query =
                """
                SELECT id.membership_id,id.ms_id, p.l_name, p.f_name,b.*,nb.boat_count
                FROM boat b
                LEFT JOIN boat_owner bo on b.BOAT_ID = bo.BOAT_ID
                LEFT JOIN membership m on bo.MS_ID = m.MS_ID
                LEFT JOIN (SELECT * FROM membership_id where FISCAL_YEAR=(select year(now()))) id on bo.MS_ID=id.MS_ID
                LEFT JOIN person p on m.P_ID = p.P_ID
                LEFT JOIN (select BOAT_ID,count(BOAT_ID) AS boat_count from boat_photos group by BOAT_ID having count(BOAT_ID) > 0) nb on b.BOAT_ID=nb.BOAT_ID;
                """;
        return getBoatListDTOS(thisBoat, query);
    }

    public static ObservableList<BoatListDTO> getActiveSailboats() {
        ObservableList<BoatListDTO> thisBoat = FXCollections.observableArrayList();
        String query =
                """
                SELECT id.membership_id,id.ms_id, p.l_name, p.f_name,b.*,nb.boat_count
                FROM (SELECT * FROM membership_id WHERE FISCAL_YEAR=year(now()) and RENEW=true) id
                LEFT JOIN (SELECT * FROM person WHERE MEMBER_TYPE=1) p on id.MS_ID=p.MS_ID
                INNER JOIN boat_owner bo on id.MS_ID=bo.MS_ID
                INNER JOIN (SELECT * FROM boat WHERE AUX=false) b on bo.BOAT_ID=b.BOAT_ID
                LEFT JOIN (SELECT BOAT_ID,count(BOAT_ID) AS boat_count FROM boat_photos group by BOAT_ID having count(BOAT_ID) > 0) nb on b.BOAT_ID=nb.BOAT_ID;                             
                """;
        return getBoatListDTOS(thisBoat, query);
    }

    private static ObservableList<BoatListDTO> getBoatListDTOS(ObservableList<BoatListDTO> thisBoat, String query) {
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisBoat.add(new BoatListDTO(
                        rs.getInt("boat_id"),
                        rs.getInt("ms_id"),
                        rs.getString("manufacturer"),
                        rs.getString("manufacture_year"),
                        rs.getString("registration_num"),
                        rs.getString("model"),
                        rs.getString("boat_name"),
                        rs.getString("sail_number"),
                        rs.getBoolean("has_trailer"),
                        rs.getString("length"),
                        rs.getString("weight"),
                        rs.getString("keel"),
                        rs.getString("phrf"),
                        rs.getString("draft"),
                        rs.getString("beam"),
                        rs.getString("lwl"),
                        rs.getBoolean("aux"),
                        rs.getInt("membership_id"),
                        rs.getString("l_name"),
                        rs.getString("f_name"),
                        rs.getInt("boat_count")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisBoat;
    }

    public static List<BoatDTO> getOnlySailboats(int ms_id) { // overload but must be separate
        List<BoatDTO> thisBoat = new ArrayList<>();
        String query = "Select BOAT_ID, MS_ID, ifnull(MANUFACTURER,'') AS MANUFACTURER, ifnull(MANUFACTURE_YEAR,'') AS " +
                "MANUFACTURE_YEAR, ifnull(REGISTRATION_NUM,'') AS REGISTRATION_NUM, ifnull(MODEL,'') AS MODEL, " +
                "ifnull(BOAT_NAME,'') AS BOAT_NAME, ifnull(SAIL_NUMBER,'') AS SAIL_NUMBER, HAS_TRAILER, " +
                "ifnull(LENGTH,'') AS LENGTH, ifnull(WEIGHT,'') AS WEIGHT, KEEL, ifnull(PHRF,'') AS PHRF, ifnull(DRAFT,'') AS DRAFT, " +
                "ifnull(BEAM,'') AS BEAM, ifnull(LWL,'') AS LWL, AUX from boat " +
                "INNER JOIN boat_owner USING (boat_id) WHERE ms_id=" + ms_id + " and " +
                "MODEL NOT LIKE 'Kayak' and MODEL NOT LIKE 'Canoe' and MODEL NOT LIKE 'Row Boat' and " +
                "MODEL NOT LIKE 'Paddle Board'";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisBoat.add(new BoatDTO(
                        rs.getInt("boat_id"),
                        rs.getInt("ms_id"),
                        rs.getString("manufacturer"),
                        rs.getString("manufacture_year"),
                        rs.getString("registration_num"),
                        rs.getString("model"),
                        rs.getString("boat_name"),
                        rs.getString("sail_number"),
                        rs.getBoolean("has_trailer"),
                        rs.getString("length"),
                        rs.getString("weight"),
                        rs.getString("keel"),
                        rs.getString("phrf"),
                        rs.getString("draft"),
                        rs.getString("beam"),
                        rs.getString("lwl"),
                        rs.getBoolean("aux")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            BaseApplication.logger.error(e.getMessage());
        }
        BaseApplication.logger.info("Boat size: " + thisBoat.size());
        return thisBoat;
    }
    public static List<BoatDTO> getBoats(int ms_id) { // overload but must be separate
        List<BoatDTO> thisBoat = new ArrayList<>();
        String query = "SELECT b.boat_id, bo.ms_id, b.manufacturer"
                + ", b.manufacture_year, b.registration_num, b.model, b.boat_name, b.sail_number"
                + ", b.has_trailer, b.length, b.weight, b.keel, b.phrf, b.draft, b.beam, b.lwl, b.aux FROM boat b INNER JOIN boat_owner bo USING (boat_id) WHERE ms_id=" + ms_id;
        try {
        ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
            thisBoat.add(new BoatDTO(
                    rs.getInt("boat_id"),
                    rs.getInt("ms_id"),
                    rs.getString("manufacturer"),
                    rs.getString("manufacture_year"),
                    rs.getString("registration_num"),
                    rs.getString("model"),
                    rs.getString("boat_name"),
                    rs.getString("sail_number"),
                    rs.getBoolean("has_trailer"),
                    rs.getString("length"),
                    rs.getString("weight"),
                    rs.getString("keel"),
                    rs.getString("phrf"),
                    rs.getString("draft"),
                    rs.getString("beam"),
                    rs.getString("lwl"),
                    rs.getBoolean("aux")));
        }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisBoat;
    }

    public static BoatDTO getBoatByBoatId(int boat_id) { // overload but must be separate
        BoatDTO thisBoat = null;
        String query = "SELECT * FROM boat b LEFT JOIN boat_owner bo USING (boat_id) WHERE boat_id=" + boat_id;
        try {
        ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
            thisBoat = new BoatDTO(
                    rs.getInt("boat_id"),
                    rs.getInt("ms_id"),
                    rs.getString("manufacturer"),
                    rs.getString("manufacture_year"),
                    rs.getString("registration_num"),
                    rs.getString("model"),
                    rs.getString("boat_name"),
                    rs.getString("sail_number"),
                    rs.getBoolean("has_trailer"),
                    rs.getString("length"),
                    rs.getString("weight"),
                    rs.getString("keel"),
                    rs.getString("phrf"),
                    rs.getString("draft"),
                    rs.getString("beam"),
                    rs.getString("lwl"),
                    rs.getBoolean("aux"));
        }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
//            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
            e.printStackTrace();
        }
        return thisBoat;
    }
}
