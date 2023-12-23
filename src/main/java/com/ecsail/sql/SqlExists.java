package com.ecsail.sql;


import com.ecsail.BaseApplication;
import com.ecsail.views.dialogues.Dialogue_ErrorSQL;
import com.ecsail.dto.MembershipDTO;
import com.ecsail.dto.PersonDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;

public class SqlExists {
	public static Boolean memberTypeExists(int memberType, int msid) {
		boolean answer = false;
		String query = "SELECT EXISTS(SELECT P_ID FROM person WHERE member_type="+memberType+" AND ms_id="+msid+") as memberTypeExists";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			rs.next();
			answer = rs.getBoolean("memberTypeExists");
		BaseApplication.connect.closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if money record EXISTS","See below for details");
		}
		return answer;
	}
	
	public static Boolean memoExists(int invoice_id, String category) {
		boolean answer = false;
		String query = "SELECT EXISTS(SELECT * FROM memo WHERE CATEGORY='" + category + "' AND invoice_id=" + invoice_id + ") AS memoExists";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			rs.next();
		    answer = rs.getBoolean("memoExists");
		BaseApplication.connect.closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return answer;
	}
	
	public static Boolean invoiceExists(String year, MembershipDTO membership) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM invoice WHERE ms_id=" + membership.getMsId() + " AND fiscal_year=" + year + ") AS invoiceExists";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			while(rs.next()) {
			result = rs.getBoolean("invoiceExists");
			}
		BaseApplication.connect.closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if Invoice Exists","See below for details");
		}
		return result;
	}
	
	
	public static Boolean depositRecordExists(String year, int batch) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM deposit WHERE fiscal_year=" + year + " AND BATCH=" + batch +")";
		  // we must convert here (this is getting crazy!)
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(SELECT * FROM deposit WHERE fiscal_year=" + year + " AND BATCH="+ batch +")");
			}
		BaseApplication.connect.closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}


	public static Boolean currentMembershipIdExists(int ms_id) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM membership_id WHERE fiscal_year=" + Year.now().getValue() + " AND ms_id=" + ms_id + ")";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			while (rs.next()) {
				result = rs.getBoolean(
						"EXISTS(SELECT * FROM membership_id WHERE fiscal_year=" + Year.now().getValue() + " AND ms_id=" + ms_id + ")");
			}
		BaseApplication.connect.closeResultSet(rs);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}

	public static Boolean depositIsUsed(int year, int batch) {
		boolean result = false;
		String query = "select exists(select * from invoice where FISCAL_YEAR="+year+" " +
				"and BATCH="+batch+") AS LATEST_EXISTS";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			while (rs.next()) {
				result = rs.getBoolean(
						"LATEST_EXISTS");
			}
			BaseApplication.connect.closeResultSet(rs);
		}
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}
}
