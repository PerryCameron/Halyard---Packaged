package com.ecsail.sql.select;



import com.ecsail.BaseApplication;
import com.ecsail.dto.HashDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlHash {

    public static HashDTO getHashFromMsid(int msid) {
        HashDTO hashDTO = new HashDTO();
        String query = "SELECT * FROM msid_hash WHERE ms_id=" + msid;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                        hashDTO.setHash_id(rs.getInt("HASH_ID"));
                        hashDTO.setHash(rs.getLong("hash"));
                        hashDTO.setMsid(rs.getInt("ms_id"));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hashDTO;
    }

    public static ArrayList<HashDTO> getAllHash() {
        ArrayList<HashDTO> hashDTOList = new ArrayList<>();
        String query = "SELECT * FROM msid_hash";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                hashDTOList.add(new HashDTO(
                rs.getInt("HASH_ID"),
                rs.getLong("hash"),
                rs.getInt("ms_id")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hashDTOList;
    }

    public static HashDTO getMsidFromHash(String hash) {
        HashDTO hashDTO = new HashDTO();
        String query = "SELECT * FROM msid_hash WHERE hash=" + hash;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                hashDTO.setHash_id(rs.getInt("HASH_ID"));
                hashDTO.setHash(rs.getLong("hash"));
                hashDTO.setMsid(rs.getInt("ms_id"));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(hashDTO);
        return hashDTO;
    }
}
