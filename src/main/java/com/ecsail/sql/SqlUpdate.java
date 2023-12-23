package com.ecsail.sql;

import com.ecsail.BaseApplication;
import com.ecsail.dto.*;
import com.ecsail.views.dialogues.Dialogue_ErrorSQL;


import java.sql.SQLException;

public class SqlUpdate {


	public static void updateAux(String boatId, Boolean value) {
		String query = "UPDATE boat SET aux=" + value + " where BOAT_ID=" + boatId;
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
