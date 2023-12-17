package com.ecsail.sql;

import com.ecsail.BaseApplication;
import com.ecsail.views.dialogues.Dialogue_ErrorSQL;
import com.ecsail.sql.select.SqlSelect;
import com.ecsail.dto.*;
import javafx.application.Platform;

import java.sql.SQLException;

public class SqlInsert {
	
	///////////////  CLASS OF STATIC PURE FUNCTIONS /////////////////////////////
	public static void addNewDbInvoice(DbInvoiceDTO d) {
		String query = "INSERT INTO db_invoice () VALUES ("
				+ d.getId() + ","
				+ d.getFiscalYear() + ",'"
				+ d.getFieldName() + "','"
				+ d.getWidgetType() + "',"
				+ d.getWidth() + ","
				+ d.getOrder() + ","
				+ d.isMultiplied() + ","
				+ d.isPrice_editable() + ","
				+ d.isCredit() + ","
				+ d.getMaxQty() + ","
				+ d.isAutoPopulate() + ","
				+ d.isItemized() +")";
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}



	public static boolean addOfficerRecord(int officer_id, int pid , String board_year, String officer, int year) {
		boolean noError = false;
		String query = "INSERT INTO officer () VALUES (" + officer_id + "," + pid + "," + board_year + ",\"" + officer + "\"," + year + ")";
		try {
			BaseApplication.connect.executeQuery(query);
			noError = true;
		 }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
		return noError;  // return true if insert performed without error
	}

	public static int addPaymentRecord(PaymentDTO op) {
		int pay_id = SqlSelect.getNextAvailablePrimaryKey("payment", "pay_id");
		String query = "INSERT INTO payment () VALUES (" + pay_id + ","
				+ op.getInvoice_id() + "," + op.getCheckNumber() + ",'" + op.getPaymentType() + "','"
				+ op.getPaymentDate() + "','" + op.getPaymentAmount() + "','" + op.getDeposit_id() + "')";
		try {
		BaseApplication.connect.executeQuery(query);
		 }
		catch (SQLException e) {
			e.printStackTrace();
		}
		return pay_id;
	}

	public static void addAwardRecord(AwardDTO a) {
		String query = "INSERT INTO awards () VALUES (" + a.getAwardId() + ","
				+ a.getPid() + ",'" + a.getAwardYear() + "','" + a.getAwardType() + "')";
		try {
			BaseApplication.connect.executeQuery(query);
		}
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}


	public static boolean addBoatRecord(BoatDTO b, int msid) {
		boolean noError = false;
		String query = "INSERT INTO boat () VALUES (" + b.getBoatId() + ",null,null,null,null,null,null,true,null,null,null,null,null,null,null,false)";
		String query1 = "INSERT INTO boat_owner () VALUES (" + msid + "," + b.getBoatId() + ")";
		try {
			BaseApplication.connect.executeQuery(query);
			BaseApplication.connect.executeQuery(query1);
			noError = true;
		 }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
		return noError;  // return true if insert performed without error
	}

	public static void addPersonRecord(PersonDTO person) {
		String query = "INSERT INTO person () VALUES ("
				+ person.getP_id() + "," + person.getMs_id() + "," + person.getMemberType() + ",'" + person.getFname()
				+ "','" + person.getLname() + "'," + getCorrectString(person.getBirthday())
				+ ",'" + person.getOccupation() + "','" + person.getBusiness() +"',true,null,'"+person.getNname()+"',"+person.getOldMsid()+")";
		try {

			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
	}

	public static String getCorrectString(String example) {
		String result;
		if(example == null) {  // if actually null print null
			result = "null";
		}  else {
			result = "\"" + example + "\""; // print "string"
		}
		return result;
	}

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

	public static boolean addMembershipIsSucessful(MembershipListDTO nm) {
		boolean updateIsSucessful = false;
		String query = "INSERT INTO membership () VALUES (" + nm.getMsId() + ",'" + nm.getpId() + "','" + nm.getJoinDate() + "','FM','','','IN','')";
		try {
			BaseApplication.connect.executeQuery(query);
			updateIsSucessful = true;
		 } catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
		}
		return updateIsSucessful;
	}

	public static void addMemo(MemoDTO m) {
		String query = "INSERT INTO memo () VALUES (" + m.getMemo_id() + "," + m.getMsid() + ",'" + m.getMemo_date() + "','" + m.getMemo() + "',"
				+ m.getInvoice_id() + ",'" + m.getCategory() + "'," +m.getBoat_id()+ ");";
		try {
			BaseApplication.connect.executeQuery(query);
		 } catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to create new row","See below for details");
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
		String query = "INSERT INTO membership_id () VALUES (" + id.getMid() + ",'" + id.getFiscal_Year() + "','" + id.getMs_id()
				+ "'," + id.getMembership_id() + "," + id.isRenew() + ",'" + id.getMem_type()+ "'," + id.isSelected() + "," + id.isLateRenew() + ")";
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
