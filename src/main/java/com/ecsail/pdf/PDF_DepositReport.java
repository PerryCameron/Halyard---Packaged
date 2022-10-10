package com.ecsail.pdf;


import com.ecsail.HalyardPaths;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.select.SqlDeposit;
import com.ecsail.sql.select.SqlMemos;
import com.ecsail.sql.select.SqlMoney;
import com.ecsail.structures.*;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DoubleBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.properties.AreaBreakType;
import com.itextpdf.layout.properties.TextAlignment;
import javafx.collections.ObservableList;

import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Comparator;

public class PDF_DepositReport {
	
	private static ObservableList<PaidDuesDTO> paidDuesForDeposit;  // these are the paid dues for a single deposit
	private final DepositDTO currentDeposit;
	private final DefinedFeeDTO currentDefinedFee;
	private DepositSummaryDTO totals;
	String fiscalYear;  // save this because I clear current Deposit
	Boolean includeDollarSigns = false;
	DecimalFormat df = new DecimalFormat("#,###.00");

	static String[] TDRHeaders = {
			"Date",
			"Deposit Number",
			"Fee",
			"Records",
			"Amount"
		};

	public PDF_DepositReport(DepositDTO cd, DefinedFeeDTO cdf, DepositPDFDTO pdfOptions) {
		this.currentDeposit = cd;
		PDF_DepositReport.paidDuesForDeposit = SqlDeposit.getPaidDues(currentDeposit);
		this.currentDefinedFee = cdf;
		System.out.println("PDF_DepositReport being instanciated");
		this.totals = updateTotals();
		this.fiscalYear = currentDeposit.getFiscalYear();
		String dest;

		
		////////////// CHOOSE SORT //////////////
		sortByMembershipId();  ///////  will add more sorts later
		
		//////////////  PREPARE PDF /////////////////////
		

		// Initialize PDF writer
		PdfWriter writer = null;
		// Check to make sure directory exists and if not create it
		HalyardPaths.checkPath(HalyardPaths.DEPOSITREPORTPATH + "/" + currentDeposit.getFiscalYear());
		if (pdfOptions.isSingleDeposit()) { // are we only creating a report of a single deposit
			dest = HalyardPaths.DEPOSITREPORTPATH + "/" + currentDeposit.getFiscalYear() + "/Deposit_Report_" + currentDeposit.getBatch() + "_" + currentDeposit.getFiscalYear() + ".pdf";
		} else { // we are creating a report for the entire year
			dest = HalyardPaths.DEPOSITREPORTPATH + "/" + currentDeposit.getFiscalYear() + "/Deposit_Report_Fiscal_Year_" + currentDeposit.getFiscalYear() + ".pdf";
		}

		try {
			writer = new PdfWriter(dest);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Initialize PDF document

		PdfDocument pdf = new PdfDocument(writer);

		// Initialize document
		Document document = new Document(pdf);
		
//		document.add(addLogoTable(document, ecscLogo));
		
		if (pdfOptions.isSingleDeposit()) { // are we only creating a report of a single deposit
			createDepositTable(currentDeposit.getBatch(), document);
		} else { // we are creating a report for the entire year
			int numberOfBatches = SqlDeposit.getNumberOfDepositBatches(currentDeposit.getFiscalYear()) + 1;
			for (int i = 1; i < numberOfBatches; i++) {
				createDepositTable(i, document);
				document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
			}
		}

		document.setTopMargin(0);
		// Close document
		document.close();
		System.out.println("destination=" + dest);
		File file = new File(dest);
		Desktop desktop = Desktop.getDesktop(); // Gui_Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()
		
		// Open the document
		try {
			desktop.open(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/////////////////////////////   CLASS METHODS  /////////////////////////////////////////
	
	
	private void createDepositTable(int batch, Document document) {
		currentDeposit.clear();
		paidDuesForDeposit.clear();
		SqlDeposit.updateDeposit(fiscalYear, batch, currentDeposit);
		paidDuesForDeposit = SqlDeposit.getPaidDues(currentDeposit);
		/////////// add the details pages ////////////
		document.add(titlePdfTable("Deposit Report"));
		document.add(detailPdfTable());
		/////////// add the summary page ////////////
		totals.clear();
		System.out.println("createDepoitTable() method called");
		totals = updateTotals();
		document.add(headerPdfTable("Summary"));
		document.add(summaryPdfTable());
	}
	
	public Table titlePdfTable(String title) {
		Image ecscLogo = new Image(ImageDataFactory.create(toByteArray(getClass().getResourceAsStream("/EagleCreekLogoForPDF.png"))));
		Table mainTable = new Table(3);
		Cell cell;
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setWidth(200);
		cell.add(new Paragraph(title + " #" + currentDeposit.getBatch())).setFontSize(20);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setWidth(200);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setWidth(90);
		ecscLogo.setMarginLeft(30);
		ecscLogo.scale(0.4f, 0.4f);
		cell.add(ecscLogo);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(new Paragraph(currentDeposit.getDepositDate() + "")).setFontSize(10);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		mainTable.addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		mainTable.addCell(cell);
		return mainTable;
	}

	// makes header row for details of each membership
	private void detailHeaderRow(Table detailTable, PaidDuesDTO dues) {
		// column widths
		int[] width = new int[] { 50, 100, 200, 40, 100 };
		Cell cell;
		for(int i = 0; i < 5; i++) {
			cell = new Cell();
			cell.setBorder(Border.NO_BORDER);
			cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
			cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
			cell.setWidth(width[i]);
			// if it is the first cell put in membership id
			if(i==0)
				cell.add(new Paragraph(dues.getMembershipId() + "")).setFontSize(10);
			if(i==1)
				cell.add(new Paragraph(dues.getL_name() + "")).setFontSize(10);
			detailTable.addCell(cell);
		}
	}

	// this table shows the details for each membership
	private Table detailPdfTable() {
		Table detailTable = new Table(5);
		for(PaidDuesDTO dues: paidDuesForDeposit)  // each membership in Deposit
		{
			detailHeaderRow(detailTable,dues);
			BigDecimal annualDues = new BigDecimal(dues.getDues());
			if (annualDues.compareTo(BigDecimal.ZERO) != 0) addItemRow(detailTable, "Annual Dues", annualDues, 0);
			if (dues.getWinter_storage() != 0)
				addItemRow(detailTable, "Winter Storage Fee", BigDecimal.valueOf(dues.getWinter_storage()).multiply(currentDefinedFee.getWinter_storage()), dues.getWinter_storage());
			//// access ////
			BigDecimal wetSlip = new BigDecimal(dues.getWet_slip());
			if (wetSlip.compareTo(BigDecimal.ZERO) != 0)
				addItemRow(detailTable, "Wet Slip Fee", wetSlip, returnWetSlipNumber(wetSlip));
			if (dues.getBeach() != 0)
				addItemRow(detailTable, "Beach Spot Fee", BigDecimal.valueOf(dues.getBeach()).multiply(currentDefinedFee.getBeach()), dues.getBeach());
			if (dues.getKayac_rack() != 0)
				addItemRow(detailTable, "Kayak Rack Fee", BigDecimal.valueOf(dues.getKayac_rack()).multiply(currentDefinedFee.getKayak_rack()), dues.getKayac_rack());
			if (dues.getKayak_beach_rack() != 0)
				addItemRow(detailTable, "Kayak Beach Rack Fee", BigDecimal.valueOf(dues.getKayak_beach_rack()).multiply(currentDefinedFee.getKayak_beach_rack()), dues.getKayak_beach_rack());
			if (dues.getKayac_shed() != 0)
				addItemRow(detailTable, "Kayak Inside Storage Fee", BigDecimal.valueOf(dues.getKayac_shed()).multiply(currentDefinedFee.getKayak_shed()), dues.getKayac_shed());
			if (dues.getSail_loft() != 0)
				addItemRow(detailTable, "Sail Loft Access Fee", BigDecimal.valueOf(dues.getSail_loft()).multiply(currentDefinedFee.getSail_loft()), dues.getSail_loft());
			if (dues.getSail_school_laser_loft() != 0)
				addItemRow(detailTable, "Sail School Loft Acess", BigDecimal.valueOf(dues.getSail_school_laser_loft()).multiply(currentDefinedFee.getSail_school_laser_loft()), dues.getSail_school_laser_loft());

			//// keys ////
			if (dues.getExtra_key() != 0)
				addItemRow(detailTable, "Extra Gate Key Fee", BigDecimal.valueOf(dues.getExtra_key()).multiply(currentDefinedFee.getMain_gate_key()), dues.getExtra_key());
			if (dues.getSail_school_loft_key() != 0)
				addItemRow(detailTable, "Extra Sail School Loft Key Fee", BigDecimal.valueOf(dues.getSail_school_loft_key()).multiply(currentDefinedFee.getSail_school_loft_key()), dues.getSail_school_loft_key());
			if (dues.getKayac_shed_key() != 0)
				addItemRow(detailTable, "Extra Inside Storage Key", BigDecimal.valueOf(dues.getKayac_shed_key()).multiply(currentDefinedFee.getKayak_shed_key()), dues.getKayac_shed_key());
			if (dues.getSail_loft_key() != 0)
				addItemRow(detailTable, "Extra Sail Loft Key Fee", BigDecimal.valueOf(dues.getSail_loft_key()).multiply(currentDefinedFee.getSail_loft_key()), dues.getSail_loft_key());

			BigDecimal initiation = new BigDecimal(dues.getInitiation());
			BigDecimal ysc = new BigDecimal(dues.getYsc_donation());
			BigDecimal other = new BigDecimal(dues.getOther());
			BigDecimal total = new BigDecimal(dues.getTotal());
			BigDecimal credit = new BigDecimal(dues.getCredit());
			BigDecimal paid = new BigDecimal(dues.getPaid());
			BigDecimal balance = new BigDecimal(dues.getBalance());

			if (initiation.compareTo(BigDecimal.ZERO) != 0) addItemRow(detailTable, "Initiation Fee", initiation, 0);
			if (ysc.compareTo(BigDecimal.ZERO) != 0) addItemRow(detailTable, "Youth Sailing Club Donation", ysc, 0);
			if (other.compareTo(BigDecimal.ZERO) != 0)
				addItemRow(detailTable, "Other", other, 0);
			addItemRow(detailTable, "Total Fees", total, 0);
			if (credit.compareTo(BigDecimal.ZERO) != 0) addCreditRow(detailTable, credit);
			addItemRow(detailTable, "Total Due", total.subtract(credit), 0);
			// prints row of amount paid
			addItemPaidRow(detailTable, paid);
			// will never get used because our treasurer is a nut
			if (balance.compareTo(BigDecimal.ZERO) != 0) {
				addBalanceRow(detailTable, balance, 0);
				addNoteRow(detailTable, "Balance: ",getNote(dues,"B"));
			}
			if (other.compareTo(BigDecimal.ZERO) != 0)
				addNoteRow(detailTable, " ",getNote(dues,"O"));
			if (SqlExists.memoExists(dues.getMoney_id(), "I"))
				addNoteRow(detailTable, " ",getNote(dues,"I"));
		}
		return detailTable;
	}

	private void addNoteRow(Table detailTable, String label, String note) {
		Paragraph p = new Paragraph();
		p.add(new Text("*Note ").setFontColor(ColorConstants.RED));
		p.add(new Text(label));
		p.add(new Text(note));

		Cell cell;
		cell = new Cell(1,2);
		cell.setBorder(Border.NO_BORDER);
		detailTable.addCell(cell);

		cell = new Cell(1,3);
		cell.setBorder(Border.NO_BORDER);
		cell.setTextAlignment(TextAlignment.LEFT);
		cell.add(p).setFontSize(10);
		detailTable.addCell(cell);
	}

	private String getNote(PaidDuesDTO dues, String category) {
		String thisMemo;
		// make sure the memo exists
		if(SqlExists.memoExists(dues.getMoney_id(), category)) {
		thisMemo = SqlMemos.getMemos(dues, category).getMemo();
		} else {
		thisMemo = "No note for this entry";
		}
		return thisMemo;
	}

	int returnWetSlipNumber(BigDecimal numberOf) {
		int result = 0;
		if(numberOf.compareTo(BigDecimal.ZERO) != 0) result = 1;
		return result;
	}

	private void addItemPaidRow(Table detailTable, BigDecimal money) {
		Cell cell;
		for(int i = 0; i < 5; i++) {
			cell = new Cell();
			cell.setBorder(Border.NO_BORDER);
			// start making line above
			if(i > 1) cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
			// place our label
			if(i == 2) cell.add(new Paragraph("Amount Paid")).setFontSize(10);
			// put in amount paid and align right
			if(i ==4) {
				cell.setTextAlignment(TextAlignment.RIGHT);
				cell.add(new Paragraph(addDollarSign() + df.format(money))).setFontSize(10);
			}
			detailTable.addCell(cell);
		}
	}

	private void addBalanceRow(Table detailTable, BigDecimal money, int numberOf) {
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("Balance")).setFontSize(10));
		if(numberOf == 0) {
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));	
		} else {
			detailTable.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER).add(new Paragraph("" + numberOf)).setFontSize(10));	
		}
		detailTable.addCell(new Cell().setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).add(new Paragraph(addDollarSign() + df.format(money))).setFontSize(10));
	}
	
	private void addItemRow(Table detailTable, String label, BigDecimal money, int numberOf) {
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph(label)).setFontSize(10));
		if(numberOf == 0) {
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));	
		} else {
			detailTable.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER).add(new Paragraph("" + numberOf)).setFontSize(10));	
		}
		detailTable.addCell(new Cell().setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).add(new Paragraph(addDollarSign() + df.format(money))).setFontSize(10));
	}
	
	private void addCreditRow(Table detailTable, BigDecimal money) {
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("Credit")).setFontSize(10));
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));
		detailTable.addCell(new Cell().setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).add(new Paragraph("-" + addDollarSign() + money.setScale(2).toPlainString())).setFontSize(10));
	}

	public void summaryPdfTable2() {
		DepositSummaryDTO depositSummaryDTO = SqlMoney.getSumTotalsFromYearAndBatch("2022",2);
		depositSummaryDTO.calculateVariables();
		System.out.println(depositSummaryDTO);

	}


	public Table summaryPdfTable()  {
		Table mainTable = new Table(TDRHeaders.length);
		// mainTable.setKeepTogether(true);
		Cell cell;
		for (String str : TDRHeaders) {
			mainTable.addCell(new Cell().setBackgroundColor(new DeviceCmyk(.5f, .24f, 0, 0.02f))
					// .setWidth(12)
					.add(new Paragraph(str).setFontSize(10)));
		}
		
		mainTable.addCell(new Cell().setWidth(80).add(new Paragraph(currentDeposit.getDepositDate()).setFontSize(10)));
		mainTable.addCell(new Cell().setWidth(100).add(new Paragraph("" + currentDeposit.getBatch()).setFontSize(10)));
		mainTable.addCell(new Cell().setWidth(200));
		mainTable.addCell(new Cell().setWidth(70));
		mainTable.addCell(new Cell().setWidth(40));

		if (totals.getDuesNumber() != 0) {
			addSummaryRow(mainTable, "Annual Dues" , totals.getDuesNumber(), totals.getDues());
		}
		if (totals.getWinter_storageNumber() != 0) {
			addSummaryRow(mainTable, "Winter Storage Fee" , totals.getWinter_storageNumber(), totals.getWinter_storage());
		}
		if (totals.getWet_slip().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Wet slip Fee" , totals.getWet_slipNumber(), totals.getWet_slip());
		}
		if (totals.getBeach().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Beach Spot Fee" , totals.getBeachNumber(), totals.getBeach());
		}
		if (totals.getKayak_rack().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Kayak Rack Fee" , totals.getKayak_rackNumber(), totals.getKayak_rack());
		}
		if (totals.getKayak_beach_rack().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Beach Kayak Rack Fee" , totals.getBeach_kayak_rackNumber(), totals.getKayak_beach_rack());
		}

		if (totals.getKayak_shed().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Inside Kayak Storage Fee" , totals.getKayak_shedNumber(), totals.getKayak_shed());
		}
		if (totals.getSail_loft().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Sail Loft Access Fee" , totals.getSail_loftNumber(), totals.getSail_loft());
		}
		if (totals.getSail_school_laser_loft().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Sail School Loft Access Fee" , totals.getSail_school_laser_loftNumber(), totals.getSail_school_laser_loft());
		}
		if (totals.getInitiation().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Initiation Fee" , totals.getInitiationNumber(), totals.getInitiation());
		}
		///////////////////  KEYS //////////////////////////////
		if (totals.getGate_key().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Extra Gate Key Fee" , totals.getGate_keyNumber(), totals.getGate_key());
		}
		if (totals.getSail_loft_key().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Sail Loft Key Fee" , totals.getSail_loft_keyNumber(), totals.getSail_loft_key());
		}
		if (totals.getSail_school_loft_key().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Sail School Loft Key Fee" , totals.getSail_school_loft_keyNumber(), totals.getSail_school_loft_key());
		}
		if (totals.getKayac_shed_key().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Kayak Shed Key Fee" , totals.getKayac_shed_keyNumber(), totals.getKayac_shed_key());
		}
		if (totals.getYsc_donation().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Youth Sailing Club Donation" , totals.getYsc_donationNumber(), totals.getYsc_donation());
		}
		if (totals.getOther().compareTo(BigDecimal.ZERO) != 0) {
			addSummaryRow(mainTable, "Other" , totals.getOtherNumber(), totals.getOther());
		}
		if (totals.getCredit().compareTo(BigDecimal.ZERO) != 0) {
			// 0 for number of removes count in PDF
			addSummaryRow(mainTable, "Credit" , "", totals.getCredit());
		}

		
		RemoveBorder(mainTable);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setBorderBottom(new DoubleBorder(ColorConstants.BLACK, 2));
		if(totals.getNumberOfRecords() == 1) {
			cell.add(new Paragraph("1 Record").setFontSize(10));
		} else {
			cell.add(new Paragraph(totals.getNumberOfRecords() + " Records").setFontSize(10));
		}
		mainTable.addCell(cell);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setBorderBottom(new DoubleBorder(ColorConstants.BLACK, 2));
		mainTable.addCell(cell);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setBorderBottom(new DoubleBorder(ColorConstants.BLACK, 2));
		cell.add(new Paragraph("Total Funds Received")).setFontSize(10);
		mainTable.addCell(cell);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setBorderBottom(new DoubleBorder(ColorConstants.BLACK, 2));
		mainTable.addCell(cell);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setBorderBottom(new DoubleBorder(ColorConstants.BLACK, 2));
		cell.setTextAlignment(TextAlignment.RIGHT);
		cell.add(new Paragraph(addDollarSign() + df.format(totals.getTotal()))).setFontSize(10);
		mainTable.addCell(cell);
		
		return mainTable;
	}






	private DepositSummaryDTO updateTotals() {
		int numberOfRecordsCounted = 0; // number of records counted
		DepositSummaryDTO total = new DepositSummaryDTO();
		for (PaidDuesDTO paidDuesDTO : paidDuesForDeposit) {

			if (paidDuesDTO.getBeach() != 0) { ///////// BEACH
				total.setBeachNumber(paidDuesDTO.getBeach() + total.getBeachNumber());
				BigDecimal totalBeachDollars = currentDefinedFee.getBeach().multiply(BigDecimal.valueOf(paidDuesDTO.getBeach()));
				total.setBeach(totalBeachDollars.add(total.getBeach()));
			}

			BigDecimal credit = new BigDecimal(paidDuesDTO.getCredit());
			if (credit.compareTo(BigDecimal.ZERO) != 0) {  ////////  CREDIT
				total.setCreditNumber(1 + total.getCreditNumber());
				total.setCredit(credit.add(total.getCredit()));
			}

			BigDecimal dues = new BigDecimal(paidDuesDTO.getDues());
			if (dues.compareTo(BigDecimal.ZERO) != 0) {  ////////  DUES
				total.setDuesNumber(1 + total.getDuesNumber());
				total.setDues(dues.add(total.getDues()));
			}

			if (paidDuesDTO.getExtra_key() != 0) { /////  EXTRA GATE KEY
				// sets total number
				total.setGate_keyNumber(paidDuesDTO.getExtra_key() + total.getGate_keyNumber());
				// does some math
				BigDecimal totalGateKeyDollars = currentDefinedFee.getMain_gate_key().multiply(BigDecimal.valueOf(paidDuesDTO.getExtra_key()));
				// sets total dollars
				total.setGate_key(total.getGate_key().add(totalGateKeyDollars));
			}

			BigDecimal initiation = new BigDecimal(paidDuesDTO.getInitiation());
			if (initiation.compareTo(BigDecimal.ZERO) != 0) {  /////// INITIATION
				total.setInitiationNumber(1 + total.getInitiationNumber());
				total.setInitiation(initiation.add(total.getInitiation()));
			}

			if (paidDuesDTO.getKayac_rack() != 0) {  ///// KAYAK RACK FEE
				System.out.print(paidDuesDTO.getF_name() + " " + paidDuesDTO.getL_name() + " kayak rack: " + paidDuesDTO.getKayac_rack());
				// t = the value in t + amount in this paidDuesDTO
				total.setKayak_rackNumber(paidDuesDTO.getKayac_rack() + total.getKayak_rackNumber());
				// multiplies number of racks x the defined fee for this year
				BigDecimal totalKayakRackDollars = currentDefinedFee.getKayak_rack().multiply(BigDecimal.valueOf(paidDuesDTO.getKayac_rack()));
				// t = the value in t + the amount in the total from line above
				total.setKayak_rack(total.getKayak_rack().add(totalKayakRackDollars));
			}

			if (paidDuesDTO.getKayak_beach_rack() != 0) {  ///// KAYAK RACK FEE
				total.setBeach_kayak_rackNumber(paidDuesDTO.getKayak_beach_rack() + total.getBeach_kayak_rackNumber());
				BigDecimal totalBeachKayakRackDollars = currentDefinedFee.getKayak_beach_rack().multiply(BigDecimal.valueOf(paidDuesDTO.getKayak_beach_rack()));
				total.setKayak_beach_rack(totalBeachKayakRackDollars.add(total.getKayak_beach_rack()));
			}

			if (paidDuesDTO.getKayac_shed() != 0) {   //////// KAYAK SHED ACCESS
				total.setKayak_shedNumber(paidDuesDTO.getKayac_shed() + total.getKayak_shedNumber());
				BigDecimal totalKayakShedDollars = currentDefinedFee.getKayak_shed().multiply(BigDecimal.valueOf(paidDuesDTO.getKayac_shed()));
				total.setKayak_shed(totalKayakShedDollars.add(total.getKayak_shed()));
			}

			if (paidDuesDTO.getKayac_shed_key() != 0) {   ///// KAYAK SHED KEY
				total.setKayac_shed_keyNumber(paidDuesDTO.getKayac_shed_key() + total.getKayac_shed_keyNumber());
				BigDecimal totalKayakShedKeyDollars = currentDefinedFee.getKayak_shed_key().multiply(BigDecimal.valueOf(paidDuesDTO.getKayac_shed_key()));
				total.setKayac_shed_key(totalKayakShedKeyDollars.add(total.getKayac_shed_key()));
			}

			BigDecimal other = new BigDecimal(paidDuesDTO.getOther());
			if (other.compareTo(BigDecimal.ZERO) != 0) {  /////////  OTHER FEE ///////// IN DOLLARS
				total.setOtherNumber(1 + total.getOtherNumber());
				total.setOther(other.add(total.getOther()));
			}
			if (paidDuesDTO.getSail_loft() != 0) {   ////////// SAIL LOFT ACCESS ///////// IN NUMBER OF
				total.setSail_loftNumber(1 + total.getSail_loftNumber());
				total.setSail_loft(currentDefinedFee.getSail_loft().add(total.getSail_loft()));
			}
			if (paidDuesDTO.getSail_loft_key() != 0) {  ///////// SAIL LOFT KEY ///////// IN NUMBER OF
				total.setSail_loft_keyNumber(paidDuesDTO.getSail_loft_key() + total.getSail_loft_keyNumber());
				BigDecimal totalSailLoftKeyDollars = currentDefinedFee.getSail_loft_key().multiply(BigDecimal.valueOf(paidDuesDTO.getSail_loft_key()));
				total.setSail_loft_key(totalSailLoftKeyDollars.add(total.getSail_loft_key()));
			}
			if (paidDuesDTO.getSail_school_laser_loft() != 0) {  ///////// SAIL SCHOOL LOFT ACCESS ///////// IN NUMBER OF
				total.setSail_school_laser_loftNumber(paidDuesDTO.getSail_school_laser_loft() + total.getSail_school_laser_loftNumber());
				BigDecimal totalSailSchoolLoftDollars = currentDefinedFee.getSail_school_laser_loft().multiply(BigDecimal.valueOf(paidDuesDTO.getSail_school_laser_loft()));
				total.setSail_school_laser_loft(totalSailSchoolLoftDollars.add(total.getSail_school_laser_loft()));
			}
			if (paidDuesDTO.getSail_school_loft_key() != 0) {  ////////// SAIL SCHOOL LOFT KEY ///////// IN NUMBER OF
				total.setSail_school_loft_keyNumber(paidDuesDTO.getSail_school_loft_key() + total.getSail_school_loft_keyNumber());
				BigDecimal totalSailSchoolLoftKeyDollars = currentDefinedFee.getSail_school_loft_key().multiply(BigDecimal.valueOf(paidDuesDTO.getSail_school_loft_key()));
				total.setSail_school_loft_key(totalSailSchoolLoftKeyDollars.add(total.getSail_school_loft_key()));
			}

			BigDecimal wetSlip = new BigDecimal(paidDuesDTO.getWet_slip());
			if (wetSlip.compareTo(BigDecimal.ZERO) != 0) {  ////////// WET SLIP FEE ///////// IN DOLLARS
				total.setWet_slipNumber(1 + total.getWet_slipNumber());
				total.setWet_slip(new BigDecimal(paidDuesDTO.getWet_slip()).add(total.getWet_slip()));
			}
			if (paidDuesDTO.getWinter_storage() != 0) {  ////////  WINTER STORAGE FEE ///////// IN NUMBER OF
				total.setWinter_storageNumber(paidDuesDTO.getWinter_storage() + total.getWinter_storageNumber());
				BigDecimal totalWinterStorageDollars = currentDefinedFee.getWinter_storage().multiply(BigDecimal.valueOf(paidDuesDTO.getWinter_storage()));
				total.setWinter_storage(totalWinterStorageDollars.add(total.getWinter_storage()));
			}

			BigDecimal ysc = new BigDecimal(paidDuesDTO.getYsc_donation());
			if (ysc.compareTo(BigDecimal.ZERO) != 0) {  //////// YSC DONATION ///////// IN DOLLARS
				total.setYsc_donationNumber(1 + total.getYsc_donationNumber());
				total.setYsc_donation(new BigDecimal(paidDuesDTO.getYsc_donation()).add(total.getYsc_donation()));
			}

			BigDecimal paid = new BigDecimal(paidDuesDTO.getPaid());
			if (paid.compareTo(BigDecimal.ZERO) != 0) {
				total.setPaid(new BigDecimal(paidDuesDTO.getPaid()).add(total.getPaid()));
			}
			numberOfRecordsCounted++;
		}
		total.setTotal(total.getPaid());  /// sets total to added up payments, kind of redundant but a bug fix and hurts nothing
		total.setNumberOfRecords(numberOfRecordsCounted);
		return total;
	}

	public String addDollarSign() {
		String sign = "";
		if(includeDollarSigns) sign="$";
		return sign;
	}
	
    public static void sortByMembershipId() {
		  paidDuesForDeposit.sort(Comparator.comparingInt(PaidDuesDTO::getMembershipId));
    }
    
	public Table headerPdfTable(String summary) {
		Table mainTable = new Table(1);
		Cell cell;
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(new Paragraph(summary)).setFontSize(20);
		mainTable.addCell(cell);		
		return mainTable;
	}
	
	private <T> void addSummaryRow(Table mainTable, String label, T numberOf, BigDecimal money) {
		mainTable.addCell(new Cell());
		mainTable.addCell(new Cell());
		mainTable.addCell(new Cell().add(new Paragraph(label)).setFontSize(10));
		mainTable.addCell(new Cell().add(new Paragraph(numberOf + "")).setFontSize(10));
		mainTable.addCell(new Cell().add(new Paragraph(addDollarSign() + df.format(money)).setFontSize(10)).setTextAlignment(TextAlignment.RIGHT));
	}
	
	private static void RemoveBorder(Table table)
	{
	    for (IElement iElement : table.getChildren()) {
	        ((Cell)iElement).setBorder(Border.NO_BORDER);
	    }
	}
	
	public static byte[] toByteArray(InputStream in)  { // for taking inputStream and returning byte array
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		// read bytes from the input stream and store them in buffer
		try {
			while ((len = in.read(buffer)) != -1) {
				// write bytes from the buffer into output stream
				os.write(buffer, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return os.toByteArray();
	}
}
