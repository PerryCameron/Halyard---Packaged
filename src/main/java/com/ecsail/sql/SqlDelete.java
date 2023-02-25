package com.ecsail.sql;

import com.ecsail.BaseApplication;
import com.ecsail.gui.dialogues.Dialogue_ErrorSQL;
import com.ecsail.sql.select.SqlInvoice;
import com.ecsail.dto.*;
import javafx.collections.ObservableList;


import java.sql.SQLException;

public class SqlDelete {


	public static void deleteStatistics() {
		String query = "DELETE FROM stats";
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
//			new Dialogue_ErrorSQL(e,"Unable to DELETE","See below for details");
		}
	}

	public static void deleteBoatPhoto(BoatPhotosDTO bp) {
		String query = "DELETE FROM boat_photos WHERE ID=" + bp.getId();
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to DELETE","See below for details");
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

	public static boolean deletePhone(PhoneDTO phone) {
	    boolean noError = false;
		String query = "DELETE FROM phone WHERE phone_id=" + phone.getPhone_ID();
			try {
				BaseApplication.connect.executeQuery(query);
				noError = true;
			} catch (SQLException e) {
				new Dialogue_ErrorSQL(e,"Unable to DELETE","See below for details");
			}
	    return noError;
	}

	public static void deleteBlankMembershipIdRow() {
		String query = "DELETE FROM membership_id WHERE fiscal_year=0 AND membership_id=0";
			try {
				BaseApplication.connect.executeQuery(query);
			} catch (SQLException e) {
				new Dialogue_ErrorSQL(e,"Unable to DELETE Blank Membership ID Row","See below for details");
			}
	}

	public static boolean deleteEmail(EmailDTO email) {
		boolean noError = false;
		String query = "DELETE FROM email WHERE email_id=" + email.getEmail_id();
			try {
				BaseApplication.connect.executeQuery(query);
				noError = true;
			} catch (SQLException e) {
				new Dialogue_ErrorSQL(e,"Unable to DELETE","See below for details");
			}
	    return noError;
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

	public static void deleteInvoiceByID(int id) {
		String query = "DELETE FROM invoice WHERE id=" + id;
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to DELETE","See below for details");
		}
	}

	public static void deleteAllPaymentsAndInvoicesByMsId(int msid) {
		ObservableList<InvoiceDTO> invoices = SqlInvoice.getInvoicesByMsid(msid);
		invoices.forEach(invoiceDTO -> {
			deletePaymentByInvoiceID(invoiceDTO.getId());
			deleteInvoiceItemByInvoiceID(invoiceDTO.getId());
			deleteInvoiceByID(invoiceDTO.getId());
		});
	}

	public static void deleteInvoiceItemByInvoiceID(int id) {
		String query = "DELETE FROM invoice_item WHERE invoice_id=" + id;
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to DELETE","See below for details");
		}
	}

	public static void deletePaymentByInvoiceID(int invoice_id) {
		String query = "DELETE FROM payment WHERE invoice_id=" + invoice_id;
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

	public static void deletePerson(int p_id) {
		String query = "DELETE FROM person WHERE p_id=" + p_id;
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to DELETE","See below for details");
		}
	}

	public static void deleteMembership(int ms_id) {
		String query = "DELETE FROM membership WHERE ms_id=" + ms_id;
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to DELETE","See below for details");
		}
	}

	public static void deleteWaitList(int ms_id) {
		String query = "DELETE FROM waitlist WHERE ms_id=" + ms_id;
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


	public static void deletePayment(PaymentDTO p) {
		String query = "DELETE FROM payment WHERE pay_id=" + p.getPay_id();
		try {
			BaseApplication.connect.executeQuery(query);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to DELETE Payment","See below for details");
		}
	}

	public static boolean deleteBoatOwner(int boat_id, int ms_id) {
		boolean noError = false;
		String query = "DELETE FROM boat_owner WHERE boat_id=" + boat_id + " AND ms_id=" + ms_id;
		try {
			BaseApplication.connect.executeQuery(query);
			noError = true;
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to DELETE Boat Owner","See below for details");
		}
		return noError;
	}
}
