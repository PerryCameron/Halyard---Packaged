package com.ecsail.sql;

import com.ecsail.BaseApplication;
import com.ecsail.views.dialogues.Dialogue_ErrorSQL;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.dto.*;
import javafx.application.Platform;

import java.sql.SQLException;

public class SqlInsert {



	public static void addInvoiceRecord(InvoiceDTO m) {
		String query = "INSERT INTO invoice () VALUES ("
				+ m.getId() + "," + m.getMsId() + "," + m.getYear() + ",'" + m.getPaid()
				+ "','" + m.getTotal() + "','" + m.getCredit() + "','" + m.getBalance()
				+ "'," + m.getBatch() + "," + m.isCommitted() + "," + m.isClosed()
				+ "," + m.isSupplemental() + "," + m.getMaxCredit() + ")";
		try {
			BaseApplication.connect.executeQuery(query);
		 } catch (SQLException e) {
			Platform.runLater(() -> new Dialogue_ErrorSQL(e,"Unable to insert data into invoice row","See below for details"));
		}
	}

	public static void addInvoiceItemRecord(InvoiceItemDTO i) {
		int id = SqlSelect.getNextAvailablePrimaryKey("invoice_item","ID");
		String query = "INSERT INTO invoice_item () VALUES ("
				+ id + "," + i.getInvoiceId() + "," + i.getMsId() + "," + i.getYear()
				+ ",'" + i.getFieldName() + "'," + i.isCredit()
				+ ",'" + i.getValue() + "'," + i.getQty() + ")";
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			Platform.runLater(() -> new Dialogue_ErrorSQL(e,"Unable to insert data into invoice row","See below for details"));
		}
	}


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
//			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}
	
	public static void addBoatOwner(int boat_id,int ms_id) {
		String query = "INSERT INTO boat_owner () VALUES (" + ms_id + "," + boat_id +")";
		try {
			BaseApplication.connect.executeQuery(query);
		 } catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}

	public static void addNewFee(FeeDTO feeDTO) {
		String query = "INSERT INTO fee () VALUES (" + feeDTO.getFeeId() + ",'" + feeDTO.getFieldName() + "'," + feeDTO.getFieldValue()
				+ "," + feeDTO.getDbInvoiceID() + "," + feeDTO.getFeeYear() + ",'" + feeDTO.getDescription() + "',false)";
		try {
			BaseApplication.connect.executeQuery(query);
		 } catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}

	public static PersonDTO createUser(int msid) {
		// create a main person for the membership
		int pid = SqlSelect.getNextAvailablePrimaryKey("person","p_id");
		String query = "INSERT INTO person () VALUES (" + pid  +"," + msid + ",1,'','',null,'','',true,null,null,null)";
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new PersonDTO(pid,msid,1,"","",null,"","",true,null,0);
	}
}
