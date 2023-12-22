package com.ecsail.sql;

import com.ecsail.BaseApplication;
import com.ecsail.dto.DepositDTO;
import com.ecsail.dto.FeeDTO;
import com.ecsail.dto.MembershipIdDTO;
import com.ecsail.dto.StatsDTO;
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

	public static void addMembershipId(MembershipIdDTO id) {
		String query = "INSERT INTO membership_id () VALUES (" + id.getMid() + ",'" + id.getFiscalYear() + "','" + id.getMsId()
				+ "'," + id.getMembershipId() + "," + id.isRenew() + ",'" + id.getMemType()+ "'," + id.isSelected() + "," + id.isLateRenew() + ")";
		try {
			BaseApplication.connect.executeQuery(query);
		 } catch (SQLException e) {
			Platform.runLater(() -> new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details"));
		}
	}

	public static void addStatRecord(StatsDTO s) {
		String query = "INSERT INTO stats () VALUES ("
				+ null + "," // autoincrement
				+ s.getFiscalYear() + ","
				+ s.getActiveMemberships() + ","
				+ s.getNonRenewMemberships() + ","
				+ s.getReturnMemberships() + ","
				+ s.getNewMemberships() + ","
				+ s.getSecondaryMembers() + ","
				+ s.getDependants() + ","
				+ s.getNumberOfBoats() + ","
				+ s.getFamily() + ","
				+ s.getRegular() + ","
				+ s.getSocial() + ","
				+ s.getLakeAssociates() + ","
				+ s.getLifeMembers() + ","
				+ s.getRaceFellows() + ","
				+ s.getStudent() + ","
				+ s.getDeposits() + ","
				+ s.getInitiation() + ")";
		try {
			BaseApplication.connect.executeQuery(query);
		 } catch (SQLException e) {
			e.printStackTrace();
		}
	}



}
