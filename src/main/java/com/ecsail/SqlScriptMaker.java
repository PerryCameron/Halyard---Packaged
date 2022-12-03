package com.ecsail;

import com.ecsail.gui.dialogues.Dialogue_DatabaseBackup;
import com.ecsail.jotform.structures.ApiKeyDTO;
import com.ecsail.sql.select.*;
import com.ecsail.structures.*;
import javafx.collections.ObservableList;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class SqlScriptMaker {
//	static Object_TupleCount newTupleCount;
	static ArrayList<DbTableChangesDTO> tableChangesDTOS;
	static ArrayList<DbUpdatesDTO> dbUpdatesDTOS;
	static ArrayList<DbInvoiceDTO> invoiceWidgets;
	static ArrayList<ApiKeyDTO> apis;
	static ArrayList<String> tableCreation = new ArrayList<>();
	static ObservableList<MembershipDTO> memberships;
	static ObservableList<MembershipIdDTO> ids;
	static ObservableList<PersonDTO> people;
	static ObservableList<PhoneDTO> phones;
	static ObservableList<BoatDTO> boats;
	static ObservableList<BoatOwnerDTO> boatowners;
	static ArrayList<SlipDTO> slips;
	static ObservableList<MemoDTO> memos;
	static ObservableList<EmailDTO> email;
	static ObservableList<InvoiceDTO> invoiceDTOS;
	static ObservableList<InvoiceItemDTO> invoiceItemDTOS;
	static ObservableList<OfficerDTO> officers;
	static ObservableList<PaymentDTO> payments;
	static ObservableList<DepositDTO> deposits;
	static ArrayList<WaitListDTO> waitlist;
	static ArrayList<AwardDTO>awards;
	static ArrayList<HashDTO>hash;
	static ArrayList<FeeDTO>fees;
	static ArrayList<IdChangeDTO>idChanges;
	static ArrayList<BoardPositionDTO> positions;
	private static final int ALL = 0;
	public static void createSql() {
		//SqlScriptMaker.newTupleCount = new Object_TupleCount();
//		SqlScriptMaker.newTupleCount =  Halyard.edits;
		BaseApplication.logger.info("Creating SQL script....");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Date date = new Date();
		String stringDate = formatter.format(date);
//		stringDate.replaceAll("\\s+", "");
		BaseApplication.logger.info("1");
		memberships = SqlMembership.getMemberships();
		ids = SqlMembership_Id.getIds();
		people = SqlPerson.getPeople();
		phones = SqlPhone.getPhoneByPid(ALL);
		boats = SqlBoat.getBoats();
		boatowners = SqlBoat.getBoatOwners();
		slips = SqlSlip.getSlips();
		memos = SqlMemos.getMemos(ALL);
		email = SqlEmail.getEmail(ALL);
		invoiceDTOS = SqlInvoice.getAllInvoices();
		invoiceItemDTOS = SqlInvoiceItem.getAllInvoiceItems();
		officers = SqlOfficer.getOfficers();
		payments = SqlPayment.getPayments();
		deposits = SqlDeposit.getDeposits();
		waitlist = SqlWaitList.getWaitLists();
		awards = SqlAward.getAwards();
		hash = SqlHash.getAllHash();
		fees = SqlFee.getAllFees();
		idChanges = SqlIdChange.getAllChangedIds();
		positions = SqlBoardPositions.getPositions();
		apis = SqlApi_key.getAPIKeys();
		invoiceWidgets = SqlDbInvoice.getInvoiceWidgets();
		dbUpdatesDTOS = SqlDbTableChanges.getDbUpdates();
		tableChangesDTOS = SqlDbTableChanges.getDbTableChanges();

		BaseApplication.logger.info("2");
		HalyardPaths.checkPath(HalyardPaths.SQLBACKUP + "/" + BaseApplication.selectedYear);
//		calculateSums();
		BaseApplication.logger.info("opening dialogue");
		new Dialogue_DatabaseBackup();
		String path = HalyardPaths.SQLBACKUP + "/" + HalyardPaths.getYear() + "/ecsc_sql_" + stringDate + ".sql";
		BaseApplication.logger.info("Backed up to: " + path);
		writeToFile(path);
	}

	public static void writeToFile(String filename) {
		try {
			File file = new File(filename);
			FileWriter writer = new FileWriter(file, true);
			// writes the schema from ecsc_create.sql located in resources/database/
			writer.write(writeSchema());
			writer.write("\n\n");
			for (MembershipDTO mem : memberships)
				writer.write(getMembershipString(mem));
			for (MembershipIdDTO mid : ids)
				writer.write(getMembershipIdString(mid));
			for (PersonDTO peo : people)
				writer.write(getPeopleString(peo));
			for (PhoneDTO pho : phones)
				writer.write(getPhoneString(pho));
			for (BoatDTO boa : boats)
				writer.write(getBoatString(boa));
			for (BoatOwnerDTO bos : boatowners)
				writer.write(getBoatOwnerString(bos));
			for (SlipDTO sli : slips)
				writer.write(getSlipString(sli));
			for (MemoDTO mem : memos)
				writer.write(getMemoString(mem));
			for (EmailDTO eml : email)
				writer.write(getEmailString(eml));
			for (InvoiceDTO i : invoiceDTOS)
				writer.write(getInvoiceString(i));
			for (InvoiceItemDTO i : invoiceItemDTOS)
				writer.write(getInvoiceItemString(i));
			for (DepositDTO dep : deposits)
				writer.write(getDepositString(dep));
			for (PaymentDTO obp : payments)
				writer.write(getPaymentString(obp));
			for (OfficerDTO off : officers)
				writer.write(getOfficerString(off));
			for (WaitListDTO wal: waitlist)
				writer.write(getWaitListString(wal));
			for (AwardDTO oa: awards)
				writer.write(getAwardsString(oa));
			for (HashDTO hd: hash)
				writer.write(getHashString(hd));
			for (FeeDTO fe: fees)
				writer.write(getFeeString(fe));
			for (IdChangeDTO idc: idChanges)
				writer.write(getIdChangeString(idc));
			for (BoardPositionDTO b: positions)
				writer.write(getPositionString(b));
			for(ApiKeyDTO a: apis)
				writer.write((getApiString(a)));
			for(DbInvoiceDTO a: invoiceWidgets)
				writer.write(getInvoiceWidgetString(a));
			for(DbUpdatesDTO u: dbUpdatesDTOS)
				writer.write((getDbUpdatesString(u)));
			for(DbTableChangesDTO t: tableChangesDTOS)
				writer.write(getTableChanges(t));

			clearMemory();
			writer.close();
			System.out.println("SQL script file sucessfully made");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void clearMemory() {
		tableCreation.clear();
		memberships.clear();
		ids.clear();
		people.clear();
		phones.clear();
		boats.clear();
		boatowners.clear();
		slips.clear();
		memos.clear();
		email.clear();
		invoiceDTOS.clear();
		invoiceItemDTOS.clear();
		deposits.clear();
		payments.clear();
		officers.clear();
		waitlist.clear();
		awards.clear();
		fees.clear();
		idChanges.clear();
		positions.clear();
		apis.clear();
		invoiceWidgets.clear();
		dbUpdatesDTOS.clear();
		tableChangesDTOS.clear();
	}

	private static String getTableChanges(DbTableChangesDTO t) {
		return "INSERT INTO db_table_changes () VALUES("
				+ t.getId() + ","
				+ t.getDbUpdatesId() + ","
				+ getCorrectString(t.getTableChanged()) + ","
				+ t.getTableInsert() + ","
				+ t.getTableDelete() + ","
				+ t.getTableUpdate() + ","
				+ getCorrectString(t.getChangeDate()) + ","
				+ getCorrectString(t.getChangedBy()) + ");\n";
	}
	private static String getDbUpdatesString(DbUpdatesDTO u) {
		return "INSERT INTO db_updates () VALUES("
				+ u.getId() + ","
				+ getCorrectString(u.getCreationDate()) + ","
				+ u.isClosed() + ","
				+ u.getDbSize() + ");\n";
	}
	private static String getInvoiceString(InvoiceDTO i) {
		return "INSERT INTO invoice () VALUES("
				+ i.getId() + ","
				+ i.getMsId() + ","
				+ i.getYear() + ","
				+ i.getPaid() + ","
				+ i.getTotal() + ","
				+ i.getCredit() + ","
				+ i.getBalance() + ","
				+ i.getBatch() + ","
				+ i.isCommitted() + ","
				+ i.isClosed() + ","
				+ i.isSupplemental() + ","
				+ i.getMaxCredit() + ");\n";
	}

	private static String getInvoiceItemString(InvoiceItemDTO i) {
		return "INSERT INTO invoice_item () VALUES("
				+ i.getId() + ","
				+ i.getInvoiceId() + ","
				+ i.getMsId() + ","
				+ i.getYear() + ","
				+ getCorrectString(i.getItemType()) + ","
				+ i.isMultiplied() + ","
				+ i.isCredit()+ ","
				+ i.getValue() + ","
				+ i.getQty() + ");\n";
	}

	private static String getInvoiceWidgetString(DbInvoiceDTO a) {
		return "INSERT INTO db_invoice () VALUES("
				+ a.getId() + ","
				+ a.getYear() + ","
				+ getCorrectString(a.getFieldName()) + ","
				+ getCorrectString(a.getWidgetType()) + ","
				+ a.getWidth() + ","
				+ a.getOrder() + ","
				+ a.isMultiplied() + ","
				+ a.isPrice_editable() + ","
				+ a.isIs_credit() + ","
				+ a.getMaxQty() + ","
				+ a.isAutoPopulate() + ");\n";
	}
	private static String getApiString(ApiKeyDTO a) {
		return "INSERT INTO api_key () VALUES("
				+ a.getId() + ","
				+ getCorrectString(a.getName()) + ","
				+ getCorrectString(a.getKey()) + ","
				+ getCorrectString(a.getDate()) + ");\n";
	}
	private static String getPositionString(BoardPositionDTO p) {
		return
				"INSERT INTO board_positions () VALUES("
				+ p.id() + ","
				+ getCorrectString(p.position()) + ","
				+ getCorrectString(p.identifier()) + ","
				+ p.order() + ","
				+ p.isOfficer() + ","
				+ p.isChair() + ","
				+ p.isAssist() + ");\n";
	}
	private static String getIdChangeString(IdChangeDTO idc) {
		return
		"INSERT INTO id_change () VALUES("
		+ idc.getChangeId() + ","
		+ idc.getIdYear() + ","
		+ idc.isChanged() + ");\n";
	}

	private static String getFeeString(FeeDTO fe) {
		return
		"INSERT INTO fee () VALUES("
		+ fe.getFeeId() + ","
		+ getCorrectString(fe.getFieldName()) + ","
		+ fe.getFieldValue() + ","
		+ fe.getFieldQuantity() + ","
		+ fe.getFeeYear() + ","
		+ getCorrectString(fe.getDescription())  + ","
		+ getCorrectString(fe.getGroupName()) + ");\n";
	}

	private static String getHashString(HashDTO hd) {
		return
		"INSERT INTO form_msid_hash () VALUES("
		+hd.getHash_id() + ","
		+ hd.getHash() + ","
		+ hd.getMsid() + ");\n";
	}

	private static String getAwardsString(AwardDTO oa) {
		return
		"INSERT INTO awards () VALUES ("
		+ oa.getAwardId() + ","
		+ oa.getPid() + ",'"
		+ oa.getAwardYear() + "','"
		+ oa.getAwardType() + "');\n";

	}

	public static String getWaitListString(WaitListDTO wal) {
		return
		"INSERT INTO wait_list () VALUES ("
		+ wal.getMs_id() + ","
		+ wal.isSlipWait() + "," // stored as integer in database
		+ wal.isKayakWait() + ","
		+ wal.isShedWait() + ","
		+ wal.isWantToSublease() + ","
		+ wal.isWantsRelease() + ","
		+ wal.isWantSlipChange()
		+ ");\n"; //stored as integer in database
	}

	public static String getMembershipIdString(MembershipIdDTO mid) {
		return
		"INSERT INTO membership_id () VALUES ("
		+ mid.getMid() + ","
		+ mid.getFiscal_Year() + "," // stored as integer in database
		+ mid.getMs_id() + ","
		+ mid.getMembership_id() + ","
		+ mid.isRenew() + ","
		+ getCorrectString(mid.getMem_type()) + ","
		+ mid.isSelected() + ","
		+ mid.isLateRenew()
		+ ");\n"; //stored as integer in database
	}

	public static String getDepositString(DepositDTO d) {
		return
				"INSERT INTO deposit () VALUES ("
				+ d.getDeposit_id() + ",'"
				+ d.getDepositDate() + "',"
				+ d.getFiscalYear() + ","
				+ d.getBatch()
				+ ");\n";
	}

	public static String getPaymentString(PaymentDTO pay) {
		return
				"INSERT INTO payment () VALUES ("
				+ pay.getPay_id() + ","
				+ pay.getInvoice_id() + ","
				+ pay.getCheckNumber() + ",'"
				+ pay.getPaymentType() + "','"
				+ pay.getPaymentDate() + "','"
				+ pay.getPaymentAmount() + "',"
				+ pay.getDeposit_id()
				+ ");\n";
	}

	public static String getOfficerString(OfficerDTO off) {
		return
				"INSERT INTO officer () VALUES ("
				+ off.getOfficer_id() + ","
				+ off.getPerson_id() + ","
				+ off.getBoard_year() + ",'"
				+ off.getOfficer_type() + "','"
				+ off.getFiscal_year()
				+ "');\n";
	}

	public static String getEmailString(EmailDTO eml) {
		return
				"INSERT INTO email () VALUES ("
				+ eml.getEmail_id() + ","
				+ eml.getPid() + ","
				+ eml.isIsPrimaryUse() + ",\""
				+ eml.getEmail() + "\","
				+ eml.isIsListed()
				+ ");\n";
	}

	public static String getMemoString(MemoDTO mem) {
		return
				"INSERT INTO memo () VALUES ("
				+ mem.getMemo_id() + ","
				+ mem.getMsid() + ",\""
				+ mem.getMemo_date() + "\",\""
				+ mem.getMemo() + "\","
				+ mem.getInvoice_id()+ ",\""
				+ mem.getCategory() + "\""
				+");\n";
	}

	public static String getSlipString(SlipDTO sli) {
		return
				"INSERT INTO slip () VALUES ("
				+ sli.getSlip_id() + ","
				+ getCorrectString(sli.getMs_id()) + ",\""
				+ sli.getSlipNumber() + "\","
				+ getCorrectString(sli.getSubleased_to()) + ","
				+ getCorrectString(sli.getAltText())
				+ ");\n";
	}

	public static String getBoatOwnerString(BoatOwnerDTO bos) {
		return
				"INSERT INTO boat_owner () VALUES ("
				+ bos.getMsid() + ","
				+ bos.getBoat_id() +");\n";
	}

	public static String getBoatString(BoatDTO boa) {
		return
				"INSERT INTO boat () VALUES ("
				+ boa.getBoat_id() + ","
				+ getCorrectString(boa.getManufacturer()) + ","
				+ getCorrectString(boa.getManufacture_year())  + ","
				+ getCorrectString(boa.getRegistration_num()) + ","
				+ getCorrectString(boa.getModel()) + ","
				+ getCorrectString(boa.getBoat_name()) + ","
				+ getCorrectString(boa.getSail_number()) + ","
				+ boa.isHasTrailer() + ","
				+ getCorrectString(boa.getLength()) + ","
				+ getCorrectString(boa.getWeight()) + ","
				+ getCorrectString(boa.getKeel()) + ","
				+ getCorrectString(boa.getPhrf()) + ","
				+ getCorrectString(boa.getDraft()) + ","
				+ getCorrectString(boa.getBeam()) + ","
				+ getCorrectString(boa.getLwl()) + ","
				+ boa.isAux()
				+ ");\n";
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

	public static String getCorrectString(int example) {   /// overload the method
		String result;
		if(example == 0) {  // if actually null print null
			result = "null";
		}  else {
			result =  String.valueOf(example); // print "string"
		}
		return result;
	}

	public static String getPhoneString(PhoneDTO pho) {
		return
				"INSERT INTO phone () VALUES ("
				+ pho.getPhone_ID() + ","
				+ pho.getPid() + ","
				+ getCorrectString(pho.getPhoneNumber()) + ","
				+ getCorrectString(pho.getPhoneType()) + ","
				+ pho.isIsListed() + ");\n";
	}

	public static String getPeopleString(PersonDTO peo) {
		return
				"INSERT INTO person () VALUES ("
				+ peo.getP_id() + ","
				+ getCorrectString(peo.getMs_id()) + ","
				+ getCorrectString(peo.getMemberType()) + ","
				+ getCorrectString(peo.getFname()) + ","
				+ getCorrectString(peo.getLname()) + ","
				+ getCorrectString(peo.getBirthday()) + ","
				+ getCorrectString(peo.getOccupation()) + ","
				+ getCorrectString(peo.getBusiness()) + ","
				+ peo.isActive() + ","
				+ "null,"
				+ getCorrectString(peo.getNname()) + ","
				+ getCorrectString(peo.getOldMsid()) + ");\n";  // this will be a picture or link to eventually
	}

	public static String getMembershipString(MembershipDTO mem) {  // change back once done

			return "INSERT INTO membership () VALUES ("
					+ mem.getMsid() + ","
					+ mem.getPid() + ","
					+ getCorrectString(mem.getJoinDate()) + ","
					+ getCorrectString(mem.getMemType()) + ","
					+ getCorrectString(mem.getAddress()) + ","
					+ getCorrectString(mem.getCity()) + ","
					+ getCorrectString(mem.getState()) + ","
					+ getCorrectString(mem.getZip()) + ");\n";
	}

	// reads schema from resources and returns a string
	public static String writeSchema() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream io = classloader.getResourceAsStream("database/ecsc_create.sql");
		assert io != null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(io));
		return reader.lines().collect(Collectors.joining(System.lineSeparator()));
	}
}
