package com.ecsail.sql;

import com.ecsail.BaseApplication;
import com.ecsail.dto.DepositDTO;
import com.ecsail.dto.MembershipIdDTO;
import com.ecsail.views.dialogues.Dialogue_ErrorSQL;
import javafx.application.Platform;

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
