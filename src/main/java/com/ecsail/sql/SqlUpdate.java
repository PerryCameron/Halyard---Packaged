package com.ecsail.sql;

import com.ecsail.BaseApplication;
import com.ecsail.views.dialogues.Dialogue_CustomErrorMessage;
import com.ecsail.views.dialogues.Dialogue_ErrorSQL;
import com.ecsail.dto.*;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;

public class SqlUpdate {
	
	static Alert alert = new Alert(AlertType.ERROR);


	public static void updateBoat(String field, int boat_id, String attribute)  {
		String query = "UPDATE boat SET " + field + "=null WHERE boat_id=" + boat_id;
		String query1 = "UPDATE boat SET " + field + "=\"" + attribute + "\" WHERE boat_id=" + boat_id;
		try {
			if(attribute == null || attribute.equals(""))
				BaseApplication.connect.executeQuery(query);
			else
				BaseApplication.connect.executeQuery(query1);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}

	public static void updateBoat(int boat_id, String fieldName, Boolean hasTrailer) {
		String query = "UPDATE boat SET "+fieldName+"=" + hasTrailer + " WHERE boat_id=" + boat_id;
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}



	public static void updateBoat(int boat_id, String keel) {
		String query = "UPDATE boat SET keel='" + keel + "' WHERE boat_id=" + boat_id;
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}

	public static void updateAddress(MembershipListDTO membership) {
		String query = "UPDATE membership SET address='" + membership.getAddress()
				+ "' WHERE ms_id=" + membership.getMsId();
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}

	public static void updateCity(MembershipListDTO membership) {
		String query = "UPDATE membership SET city='" + membership.getCity()
				+ "' WHERE ms_id=" + membership.getMsId();
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}

	public static void updateState(MembershipListDTO membership) {
		String query = "UPDATE membership SET state='" + membership.getState()
				+ "' WHERE ms_id=" + membership.getMsId();
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}

	public static void updateZipcode(MembershipListDTO membership) {
		String query = "UPDATE membership SET zip='" + membership.getZip()
				+ "' WHERE ms_id=" + membership.getMsId();
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}

	public static void updateMembership(int ms_id, String field, LocalDate date) {
		String query = "UPDATE membership SET " + field + "='" + date + "' WHERE ms_id=" + ms_id;
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}

	public static void updateDeposit(DepositDTO depositDTO) {
		String query = "UPDATE deposit SET DEPOSIT_DATE='" + depositDTO.getDepositDate()
				+ "', FISCAL_YEAR=" + depositDTO.getFiscalYear()
				+ ", BATCH=" + depositDTO.getBatch() + " WHERE deposit_id=" + depositDTO.getDeposit_id();
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the updating this deposit","");
		}
	}

	public static void updateListed(String field, int phone_id, Boolean attribute) {
		String query = "UPDATE phone SET " + field + "=" + attribute + " WHERE phone_id=" + phone_id;
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}

	public static void updatePersonChangeMemberType(PersonDTO person, int newMemType) {
		String query = "UPDATE person SET MEMBER_TYPE=" + newMemType + " WHERE P_ID=" + person.getP_id();
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}



	public static void updateOfficer(String field, int officer_id, String attribute) {
		String query = "UPDATE officer SET " + field + "='" + attribute + "' WHERE o_id=" + officer_id;
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			//new Dialogue_ErrorSQL(e,"There was a problem with the Update","");
			Platform.runLater(() -> {
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Duplicate");
			alert.setContentText("Duplicate entry!");
			alert.showAndWait();
			});
		}
	}

	public static void updateAward(String field, int awardId, String attribute) {
		String query = "UPDATE awards SET " + field + "='" + attribute + "' WHERE award_id=" + awardId;
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			Platform.runLater(() -> {
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Duplicate");
			alert.setContentText("Duplicate entry!");
			alert.showAndWait();
			});
		}
	}






















	public static void updatePayment(int pay_id, String field, String attribute) {
		String query = "UPDATE payment SET " + field + "='" + attribute + "' WHERE pay_id=" + pay_id;
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}

	public static Boolean updateMembershipId(MembershipIdDTO thisId, String field, String attribute) {
		boolean noError = true;
		String query = "UPDATE membership_id SET " + field + "='" + attribute + "' WHERE mid=" + thisId.getMid();
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLIntegrityConstraintViolationException IV) {
			PersonDTO accountHolder = SqlPerson.getPersonFromMembershipID(thisId.getMembershipId(), thisId.getFiscalYear());
			String errorMessage = "The entry for the year " + thisId.getFiscalYear() + " with a membership ID of " + thisId.getMembershipId()
			+ " already exists for another member: " + accountHolder.getFname() + " " + accountHolder.getLname();
			new Dialogue_CustomErrorMessage(errorMessage, "Duplicate Entry");
				noError = false;
		} catch (SQLException e) {
				new Dialogue_ErrorSQL(e, "There was a problem with the UPDATE", "");
		} catch (NullPointerException f) {
				new Dialogue_ErrorSQL(f, "Null pointer for MID="+thisId.getMid()+" membership ID=" + thisId.getMembershipId() + " Fiscal Year=" + thisId.getFiscalYear(), "");
		}
		return noError;
	}

	public static void updateAux(String boatId, Boolean value) {
		String query = "UPDATE boat SET aux=" + value + " where BOAT_ID=" + boatId;
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}

	public static void updateMembershipId(int ms_id, int year, boolean value) {
		String query = "UPDATE membership_id SET renew=" + value + " where fiscal_year='" + year + "' and ms_id=" + ms_id;
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}

	public static void updateMembershipId(int mid, String field, Boolean attribute) {
		String query = "UPDATE membership_id SET " + field + "=" + attribute + " WHERE mid=" + mid;
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}

	public static void updateFeeRecord(FeeDTO feeDTO) {
		String query = "UPDATE fee SET FIELD_NAME='" + feeDTO.getFieldName()
				+ "', FIELD_VALUE=" + feeDTO.getFieldValue()
				+ ", DB_INVOICE_ID=" + feeDTO.getDbInvoiceID()
				+ ", FEE_YEAR=" + feeDTO.getFeeYear()
				+ ", DESCRIPTION='" + feeDTO.getDescription()
				+ "' WHERE FEE_ID=" + feeDTO.getFeeId();
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}

	public static void updateFeeByDescriptionAndFieldName(FeeDTO feeDTO, String s) {
		String query = "UPDATE fee SET DESCRIPTION='" + feeDTO.getDescription()
				+ "' WHERE FIELD_NAME='" + feeDTO.getFieldName() + "' AND DESCRIPTION='" + s + "'";
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with the UPDATE","");
		}
	}

	public static void updateInvoiceItem(InvoiceItemDTO item) {
		String query = "UPDATE invoice_item SET "
				+ "VALUE=" + item.getValue() + ","
				+ "QTY=" + item.getQty()
				+ " WHERE ID=" + item.getId();
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with updating item " + item.getFieldName(),"");
		}
	}

	public static void updateInvoice(InvoiceDTO invoice) {
		String query = "UPDATE invoice SET "
				+ "PAID=" + invoice.getPaid() + ","
				+ "TOTAL=" + invoice.getTotal() + ","
				+ "CREDIT=" + invoice.getCredit() + ","
				+ "BALANCE=" + invoice.getBalance() + ","
				+ "BATCH=" + invoice.getBatch() + ","
				+ "COMMITTED=" + invoice.isCommitted() + ","
				+ "CLOSED=" + invoice.isClosed() + ","
				+ "SUPPLEMENTAL=" + invoice.isSupplemental() + ","
				+ "MAX_CREDIT=" + invoice.getMaxCredit()
				+ " WHERE ID=" + invoice.getId();
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with updating Invoice ID " + invoice.getId(),"");
		}
	}

	public static void updateDbInvoice(DbInvoiceDTO dbInvoiceDTO) {
		String query = "UPDATE db_invoice SET "
				+ "FIELD_NAME='" + dbInvoiceDTO.getFieldName() + "',"
				+ "widget_type='" + dbInvoiceDTO.getWidgetType() + "',"
				+ "width=" + dbInvoiceDTO.getWidth() + ","
				+ "invoice_order=" + dbInvoiceDTO.getOrder() + ","
				+ "multiplied=" + dbInvoiceDTO.isMultiplied() + ","
				+ "price_editable=" + dbInvoiceDTO.isPrice_editable() + ","
				+ "is_credit=" + dbInvoiceDTO.isCredit() + ","
				+ "max_qty=" + dbInvoiceDTO.getMaxQty() + ","
				+ "auto_populate=" + dbInvoiceDTO.isAutoPopulate() + ","
				+ "is_itemized=" + dbInvoiceDTO.isItemized()
				+ " WHERE ID=" + dbInvoiceDTO.getId();
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"There was a problem with updating db_invoice " + dbInvoiceDTO.getId(),"");
		}
	}
}
