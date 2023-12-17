package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.dto.MembershipListDTO;
import com.ecsail.views.dialogues.Dialogue_ErrorSQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlMembershipList {



    /// this exists in jdbcFormat now
    public static MembershipListDTO getMembershipFromList(int ms_id, String year) {
        MembershipListDTO thisMembership = null;
        String query = "SELECT m.ms_id,m.p_id,id.membership_id,id.fiscal_year,m.join_date,"
                + "id.mem_type,s.SLIP_NUM,p.l_name,p.f_name,s.subleased_to,m.address,m.city,m.state,"
                + "m.zip FROM slip s RIGHT JOIN membership m ON m.ms_id=s.ms_id LEFT JOIN membership_id "
                + "id ON m.ms_id=id.ms_id LEFT JOIN person p ON p.ms_id=m.ms_id WHERE id.fiscal_year='" + year + "' "
                + "AND p.member_type=1 AND m.ms_id=" + ms_id;
        try {

            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                thisMembership = new MembershipListDTO(
                        rs.getInt("ms_id"),
                        rs.getInt("p_id"),
                        rs.getInt("membership_id"),
                        rs.getString("join_date"),
                        rs.getString("mem_type"),
                        rs.getString("SLIP_NUM"),
                        rs.getString("l_name"),
                        rs.getString("f_name"),
                        rs.getInt("subleased_to"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("zip"),
                        rs.getString("fiscal_year"));
                }
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to SELECT roster","See below for details");
        }
        return thisMembership;
    }


}
