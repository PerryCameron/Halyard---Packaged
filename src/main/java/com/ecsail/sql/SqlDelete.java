package com.ecsail.sql;

import com.ecsail.BaseApplication;
import com.ecsail.dto.*;
import com.ecsail.views.dialogues.Dialogue_ErrorSQL;
import com.ecsail.repository.implementations.InvoiceRepositoryImpl;
import com.ecsail.repository.interfaces.InvoiceRepository;

import java.sql.SQLException;
import java.util.List;

public class SqlDelete {


	public static void deleteStatistics() {
		String query1 = "DELETE FROM stats";
		String query2 = "ALTER TABLE stats AUTO_INCREMENT = 1";
		try {
			BaseApplication.connect.executeQuery(query1);
			BaseApplication.connect.executeQuery(query2);
		} catch (SQLException e) {
//			new Dialogue_ErrorSQL(e,"Unable to DELETE","See below for details");
		}
	}
	
	public static void deletePerson(PersonDTO p) {
		String query = "DELETE FROM person WHERE p_id=" + p.getP_id();
			try {
				BaseApplication.connect.executeQuery(query);
			} catch (SQLException e) {
				new Dialogue_ErrorSQL(e,"Unable to DELETE","See below for details");
			}
	}

	public static void deleteFee(FeeDTO f) {
		String query = "DELETE FROM fee WHERE fee_id=" + f.getFeeId();
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to DELETE","See below for details");
		}
	}

	public static void deleteFeesByDbInvoiceId(DbInvoiceDTO dbInvoiceDTO) {
		String query = "DELETE FROM fee WHERE db_invoice_id=" + dbInvoiceDTO.getId();
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to DELETE fees for dbInvoiceID " + dbInvoiceDTO.getId(),
					"See below for details");
		}
	}

	public static void deleteDbInvoice(DbInvoiceDTO dbInvoiceDTO) {
		String query = "DELETE FROM db_invoice WHERE id=" + dbInvoiceDTO.getId();
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to DELETE fees for dbInvoiceID " + dbInvoiceDTO.getId(),
					"See below for details");
		}
	}


	public static void deleteBlankMembershipIdRow() {
		String query = "DELETE FROM membership_id WHERE fiscal_year=0 AND membership_id=0";
			try {
				BaseApplication.connect.executeQuery(query);
			} catch (SQLException e) {
				new Dialogue_ErrorSQL(e,"Unable to DELETE Blank Membership ID Row","See below for details");
			}
	}

	public static boolean deleteMembershipId(MembershipIdDTO mid) {
		boolean noError = false;
		String query = "DELETE FROM membership_id WHERE mid=" + mid.getMid();
			try {
				BaseApplication.connect.executeQuery(query);
				noError = true;
			} catch (SQLException e) {
				new Dialogue_ErrorSQL(e,"Unable to DELETE","See below for details");
			}
	    return noError;
	}

	public static boolean deleteOfficer(OfficerDTO officer) {
		boolean noError = false;
		String query = "DELETE FROM officer WHERE o_id=" + officer.getOfficer_id();
		try {
			BaseApplication.connect.executeQuery(query);
			noError = true;
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to DELETE","See below for details");
		}
		return noError;
	}

	public static boolean deleteAward(AwardDTO a) {
		boolean noError = false;
		String query = "DELETE FROM awards WHERE award_id=" + a.getAwardId();
		try {
			BaseApplication.connect.executeQuery(query);
			noError = true;
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to DELETE","See below for details");
		}
		return noError;
	}

	public static void deleteBoatOwner(int ms_id) {
		String query = "DELETE FROM boat_owner WHERE ms_id=" + ms_id;
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to DELETE","See below for details");
		}
	}

	public static void deleteMemo(MemoDTO memo) {
		String query = "DELETE FROM memo WHERE memo_id=" + memo.getMemo_id();
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to DELETE","See below for details");
		}
	}

	public static void deleteMemos(int ms_id) {
		String query = "DELETE FROM memo WHERE ms_id=" + ms_id;
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to DELETE","See below for details");
		}
	}


	public static void deletePhones(int p_id) {
		String query = "DELETE FROM phone WHERE p_id=" + p_id;
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to DELETE","See below for details");
		}
	}

	public static void deleteEmail(int p_id) {
		String query = "DELETE FROM email WHERE p_id=" + p_id;
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to DELETE","See below for details");
		}
	}

	public static void deleteOfficer(int p_id) {
		String query = "DELETE FROM officer WHERE p_id=" + p_id;
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to DELETE","See below for details");
		}
	}


	public static void deleteWaitList(int ms_id) {
		String query = "DELETE FROM wait_list WHERE ms_id=" + ms_id;
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to DELETE","See below for details");
		}
	}

	public static void deleteMembershipId(int ms_id) {
		String query = "DELETE FROM membership_id WHERE ms_id=" + ms_id;
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to DELETE","See below for details");
		}
	}





}
