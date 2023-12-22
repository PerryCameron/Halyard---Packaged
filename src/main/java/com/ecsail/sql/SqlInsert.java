package com.ecsail.sql;

import com.ecsail.BaseApplication;
import com.ecsail.dto.DepositDTO;

import java.sql.SQLException;

public class SqlInsert {


	public static void addDeposit(DepositDTO d) {
		String query = "INSERT INTO deposit () VALUES (" + d.getDeposit_id() + ",'" + d.getDepositDate() + "','" + d.getFiscalYear() + "'," + d.getBatch() + ");";
		try {
			BaseApplication.connect.executeQuery(query);
		 } catch (SQLException e) {
		e.printStackTrace();
		}
	}


}
