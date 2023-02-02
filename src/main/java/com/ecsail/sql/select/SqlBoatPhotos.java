package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.structures.BoatPhotosDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlBoatPhotos {
    public static ArrayList<BoatPhotosDTO> getImagesByBoatId(int boat_id) {
        ArrayList<BoatPhotosDTO> hashDTOList = new ArrayList<>();
        String query = "select * from boat_photos where BOAT_ID=" + boat_id;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                hashDTOList.add(new BoatPhotosDTO(
                        rs.getInt("ID"),
                        rs.getInt("BOAT_ID"),
                        rs.getString("upload_date"),
                        rs.getString("filename"),
                        rs.getString("path"),
                        rs.getBoolean("default_image")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hashDTOList;
    }
}
