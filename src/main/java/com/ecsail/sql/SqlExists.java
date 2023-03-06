package com.ecsail.sql;


import com.ecsail.BaseApplication;
import com.ecsail.views.dialogues.Dialogue_ErrorSQL;
import com.ecsail.dto.MembershipDTO;
import com.ecsail.dto.PersonDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlExists {


	public static Boolean membershipHasOfficerForYear(int msid, int year) {
		boolean answer = false;
		String query = "SELECT EXISTS(" +
				"SELECT * FROM officer o " +
				"JOIN person p ON p.P_ID=o.P_ID " +
				"WHERE o.OFF_YEAR=" +year+ " " +
				"AND p.MS_ID=" + msid + " " +
				"AND o.OFF_TYPE != 'BM'" +
				") AS officer_exists";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			rs.next();
			answer = rs.getBoolean("officer_exists");
			BaseApplication.connect.closeResultSet(rs);
		}
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if Officer EXISTS","See below for details");
		}
		return answer;
	}

	// this may be a duplicate, for instance it doesn't need int pid, and why the inner join
	// this is used on BoxAddPerson only
	public static Boolean dbTableChangeRowExists(int id, String table) {
		boolean answer = false;
		String query = "select exists(select * from db_table_changes where db_updates_id="
				+id+" and table_changed='"+table+"') AS table_exists";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			rs.next();
			answer = rs.getBoolean("table_exists");
			BaseApplication.connect.closeResultSet(rs);
		}
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");

		}
		return answer;
	}

	public static Boolean personExists(int type, int ms_id) {
		boolean answer = false;
		String query = "SELECT EXISTS(SELECT * FROM person INNER JOIN membership ON person.ms_id = membership.ms_id WHERE membership.ms_id ="
				+ ms_id + " AND person.member_type=" + type + ") AS person_exists";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			rs.next();
		    answer = rs.getBoolean("person_exists");
		BaseApplication.connect.closeResultSet(rs);
		}
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return answer;
	}
	
	public static Boolean paymentExists(int invoice_id) {
		boolean answer = false;
		String query = "SELECT EXISTS(SELECT * FROM payment WHERE INVOICE_ID=" + invoice_id + ")";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			rs.next();
		    answer = rs.getBoolean("EXISTS(SELECT * FROM payment WHERE INVOICE_ID=" + invoice_id + ")");
		BaseApplication.connect.closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if money record EXISTS","See below for details");
		}
		return answer;
	}

	public static Boolean memberTypeExists(int memberType, int msid) {
		boolean answer = false;
		String query = "SELECT EXISTS(SELECT P_ID FROM person WHERE member_type="+memberType+" AND ms_id="+msid+") as memberTypeExists";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			rs.next();
			answer = rs.getBoolean("memberTypeExists");
		BaseApplication.connect.closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if money record EXISTS","See below for details");
		}
		return answer;
	}
	
	public static Boolean paymentsExistForMembership(int ms_id) {
		boolean answer = false;
		String query = "SELECT EXISTS(SELECT * FROM money WHERE ms_id=" + ms_id + ") AS payment_exists";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			rs.next();
		    answer = rs.getBoolean("payment_exists");
		BaseApplication.connect.closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if money record EXISTS","See below for details");
		}
		return answer;
	}
	
	public static Boolean memoExists(int invoice_id, String category) {
		boolean answer = false;
		String query = "SELECT EXISTS(SELECT * FROM memo WHERE CATEGORY='" + category + "' AND invoice_id=" + invoice_id + ") AS memoExists";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			rs.next();
		    answer = rs.getBoolean("memoExists");
		BaseApplication.connect.closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return answer;
	}
	
	public static boolean memberShipExists(int ms_id) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM membership WHERE ms_id='" + ms_id + "')";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(SELECT * FROM membership WHERE ms_id='" + ms_id + "')");
			}
		BaseApplication.connect.closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to verify if a membership EXISTS","See below for details");
		}
		return result;
	}
	
	public static boolean membershipIdBlankRowExists(String msid) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM membership_id WHERE fiscal_year=0 AND MEMBERSHIP_ID=0 AND ms_id!="+msid+") AS new_tuple";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			while(rs.next()) {
			result = rs.getBoolean("new_tuple");
			}
		BaseApplication.connect.closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if a blank membership_id row EXISTS","See below for details");
		}
		return result;
	}

	public static boolean activePersonExists(int ms_id, int member_type) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM person WHERE ms_id=" + ms_id + " AND member_type=" + member_type + " AND is_active=true)";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(SELECT * FROM person WHERE ms_id=" + ms_id + " AND member_type=" + member_type + " AND is_active=true)");
			}
		BaseApplication.connect.closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}

	public static boolean emailExists(PersonDTO p) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM email WHERE P_ID=" + p.getP_id() + " AND PRIMARY_USE=true) AS email_exists";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			while(rs.next()) {
			result = rs.getBoolean("email_exists");
			}
		BaseApplication.connect.closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}
	
	public static boolean listedPhoneOfTypeExists(PersonDTO p, String type) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM phone WHERE P_ID=" + p.getP_id() + " AND PHONE_LISTED=true AND PHONE_TYPE='" + type + "') AS phone_exists";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			while(rs.next()) {
			result = rs.getBoolean("phone_exists");
			}
		BaseApplication.connect.closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}

	public static boolean phoneOfTypeExists(String pid, String type) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM phone WHERE P_ID=" + pid + " AND PHONE_TYPE='" + type + "') AS phone_exists";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			while(rs.next()) {
				result = rs.getBoolean("phone_exists");
			}
		BaseApplication.connect.closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}

	//select exists(select * from person where MS_ID=229 and MEMBER_TYPE=2);
	public static Boolean slipExists(int ms_id) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM slip WHERE ms_id=" + ms_id + ")";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(SELECT * FROM slip WHERE ms_id=" + ms_id + ")");
			}
		BaseApplication.connect.closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}
	
	public static Boolean slipRentExists(int subMsid) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM slip WHERE subleased_to='" + subMsid + "')";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			while (rs.next()) {
				result = rs.getBoolean("EXISTS(SELECT * FROM slip WHERE subleased_to='" + subMsid + "')");
			}
		BaseApplication.connect.closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}
	
	public static Boolean invoiceExists(String year, MembershipDTO membership) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM invoice WHERE ms_id=" + membership.getMsId() + " AND fiscal_year=" + year + ") AS invoiceExists";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			while(rs.next()) {
			result = rs.getBoolean("invoiceExists");
			}
		BaseApplication.connect.closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if Invoice Exists","See below for details");
		}
		return result;
	}
	
	
	public static Boolean depositRecordExists(String year, int batch) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM deposit WHERE fiscal_year=" + year + " AND BATCH=" + batch +")";
		  // we must convert here (this is getting crazy!)
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			while(rs.next()) {
			result = rs.getBoolean("EXISTS(SELECT * FROM deposit WHERE fiscal_year=" + year + " AND BATCH="+ batch +")");
			}
		BaseApplication.connect.closeResultSet(rs); }
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}


	public static Boolean currentMembershipIdExists(int ms_id) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM membership_id WHERE fiscal_year=" + BaseApplication.selectedYear + " AND ms_id=" + ms_id + ")";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			while (rs.next()) {
				result = rs.getBoolean(
						"EXISTS(SELECT * FROM membership_id WHERE fiscal_year=" + BaseApplication.selectedYear + " AND ms_id=" + ms_id + ")");
			}
		BaseApplication.connect.closeResultSet(rs);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}
	
	public static Boolean waitListExists(int ms_id) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM wait_list WHERE ms_id="+ms_id+") AS wait_list_exists";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			while (rs.next()) {
				result = rs.getBoolean(
						"wait_list_exists");
			}
		BaseApplication.connect.closeResultSet(rs);
		} catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}

	public static Boolean personExistsByType(String msid, String type) {
		boolean result = false;
		String query = "SELECT EXISTS(SELECT * FROM person WHERE ms_id=" + msid + " AND member_type=" + type + ") AS person_exist";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			while (rs.next()) {
				result = rs.getBoolean(
						"person_exist");
			}
			BaseApplication.connect.closeResultSet(rs);
		}
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}

	public static Boolean depositIsUsed(int year, int batch) {
		boolean result = false;
		String query = "select exists(select * from invoice where FISCAL_YEAR="+year+" " +
				"and BATCH="+batch+") AS LATEST_EXISTS";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			while (rs.next()) {
				result = rs.getBoolean(
						"LATEST_EXISTS");
			}
			BaseApplication.connect.closeResultSet(rs);
		}
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}

	public static Boolean invoiceItemPositionCreditExists(int year, int msId) {
		boolean result = false;
		String query = "select exists(select * from invoice_item where FISCAL_YEAR="+year+" " +
				"and MS_ID="+msId+" and field_name='Position Credit') AS ITEM_EXISTS";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			while (rs.next()) {
				result = rs.getBoolean(
						"ITEM_EXISTS");
			}
			BaseApplication.connect.closeResultSet(rs);
		}
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}

	public static Boolean invoiceItemPositionCreditExistsWithValue(int year, int msId) {
		boolean result = false;
		String query = "select exists(select * from invoice_item where FISCAL_YEAR="+year+" " +
				"and MS_ID="+msId+" and field_name='Position Credit' and VALUE > 0) AS ITEM_EXISTS";
		try {
			ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
			while (rs.next()) {
				result = rs.getBoolean(
						"ITEM_EXISTS");
			}
			BaseApplication.connect.closeResultSet(rs);
		}
		catch (SQLException e) {
			new Dialogue_ErrorSQL(e,"Unable to check if EXISTS","See below for details");
		}
		return result;
	}

}
