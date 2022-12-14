package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;


import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlSelect {

	public static int getNextAvailablePrimaryKey(String table, String column) {
		return getLastPrimaryKey(table, column) + 1;
	}

	public static int getLastPrimaryKey(String table, String column) {  // example-> "email","email_id"
		int result = 0;
		String query = "SELECT " + column + " FROM " + table + " ORDER BY " + column + " DESC LIMIT 1";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			if(rs.next());
			result = rs.getInt(column);
			BaseApplication.connect.closeResultSet(rs);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
		}
			return result;
	}

	public static int selectIntValueFromTable(String table, String column, String rowId) {
		int result = 0;
		String query = "SELECT " + column + " FROM " + table + " WHERE ID=" + rowId;
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			rs.next();
			result = rs.getInt(column);
			BaseApplication.connect.closeResultSet(rs);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e, "Unable to retrieve information", "See below for details");
		}
		return result;
	}

}
