package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.gui.tabs.deposits.InvoiceWithMemberInfoDTO;
import com.ecsail.dto.Memo2DTO;
import com.ecsail.dto.MemoDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlMemos {




    public static ObservableList<Memo2DTO> getAllMemosForTabNotes(String year, String category) {
        ObservableList<Memo2DTO> theseMemos = FXCollections.observableArrayList();
        String query = "SELECT * FROM memo "
                + "LEFT JOIN membership_id id on memo.ms_id=id.ms_id "
                + "WHERE YEAR(memo_date)='"+year+"' and id.fiscal_year='"+year+"' and memo.CATEGORY IN("+category+")";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            while (rs.next()) {
                theseMemos.add(new Memo2DTO( // why do I keep gettin a nullpointer exception here?
                        rs.getString("MEMBERSHIP_ID"),
                        rs.getInt("MEMO_ID"),
                        rs.getInt("MS_ID"),
                        rs.getString("MEMO_DATE"),
                        rs.getString("MEMO"),
                        rs.getInt("INVOICE_ID"),
                        rs.getString("CATEGORY")));
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return theseMemos;
    }

    public static MemoDTO getMemoByMsId(InvoiceWithMemberInfoDTO invoice, String category) {
        String query = "SELECT * FROM memo WHERE INVOICE_ID=" + invoice.getId() + " and category='" + category + "'";
        MemoDTO thisMemo = null;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            rs.next();
                thisMemo = new MemoDTO( // why do I keep gettin a nullpointer exception here?
                        rs.getInt("MEMO_ID"),
                        rs.getInt("MS_ID"),
                        rs.getString("MEMO_DATE"),
                        rs.getString("MEMO"),
                        rs.getInt("INVOICE_ID"),
                        rs.getString("CATEGORY"),
                        rs.getInt("boat_id"));
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thisMemo;
    }
}
