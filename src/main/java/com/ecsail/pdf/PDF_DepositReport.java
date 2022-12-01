package com.ecsail.pdf;


import com.ecsail.BaseApplication;
import com.ecsail.HalyardPaths;
import com.ecsail.gui.tabs.deposits.InvoiceWithMemberInfoDTO;
import com.ecsail.gui.tabs.deposits.TabDeposits;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.select.*;
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
import java.util.ArrayList;
import java.util.Comparator;

public class PDF_DepositReport {
	
//	private static ObservableList<PaidDuesDTO> paidDuesForDeposit;  // these are the paid dues for a single deposit
	private final ObservableList<InvoiceItemDTO> invoiceItems;

	private final ArrayList<InvoiceItemDTO> invoiceSummedItems = new ArrayList<>();

	private final ArrayList<String> invoiceItemTypes = new ArrayList<>(); // a list of types of invoice items for a given year
	private final ObservableList<InvoiceWithMemberInfoDTO> invoices;
	private final DepositDTO depositDTO;

	// TODO get rid of DepositSummaryDTO
	String fiscalYear;  // save this because I clear current Deposit
	Boolean includeDollarSigns = false;
//	DecimalFormat df = new DecimalFormat("#,###.00");

	static String[] TDRHeaders = {
			"Date",
			"Deposit Number",
			"Fee",
			"Records",
			"Amount"
		};

	public PDF_DepositReport(TabDeposits td, DepositPDFDTO pdfOptions) {
		this.depositDTO = td.getDepositDTO();
		this.invoices = td.getInvoices();
		this.invoiceItems = SqlInvoiceItem.getAllInvoiceItemsByYearAndBatch(depositDTO);


//		PDF_DepositReport.paidDuesForDeposit = SqlDeposit.getPaidDues(depositDTO);
		BaseApplication.logger.info("Creating Deposit Report "
				+ depositDTO.getBatch() + " for " + depositDTO.getFiscalYear());
//		this.totals = updateTotals();
		this.fiscalYear = depositDTO.getFiscalYear();
		String dest;
		// get our categories
		invoiceItemTypes.addAll(SqlDbInvoice.getInvoiceCategoriesByYear(Integer.parseInt(depositDTO.getFiscalYear())));
		// get our summed items
		for (String type : invoiceItemTypes) {
			invoiceSummedItems.add(SqlInvoiceItem.getInvoiceItemSumByYearAndType(
					Integer.parseInt(depositDTO.getFiscalYear()), type, depositDTO.getBatch()));
		}
		
		////////////// CHOOSE SORT //////////////
//		sortByMembershipId();  ///////  will add more sorts later
		
		//////////////  PREPARE PDF /////////////////////
		

		// Initialize PDF writer
		PdfWriter writer = null;
		// Check to make sure directory exists and if not create it
		HalyardPaths.checkPath(HalyardPaths.DEPOSITREPORTPATH + "/" + depositDTO.getFiscalYear());
		if (pdfOptions.isSingleDeposit()) { // are we only creating a report of a single deposit
			dest = HalyardPaths.DEPOSITREPORTPATH + "/" + depositDTO.getFiscalYear() + "/Deposit_Report_" + depositDTO.getBatch() + "_" + depositDTO.getFiscalYear() + ".pdf";
		} else { // we are creating a report for the entire year
			dest = HalyardPaths.DEPOSITREPORTPATH + "/" + depositDTO.getFiscalYear() + "/Deposit_Report_Fiscal_Year_" + depositDTO.getFiscalYear() + ".pdf";
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
			createDepositTable(depositDTO.getBatch(), document);
		} else { // we are creating a report for the entire year
			int numberOfBatches = SqlDeposit.getNumberOfDepositBatches(depositDTO.getFiscalYear()) + 1;
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
		/////////// add the details pages ////////////
		document.add(titlePdfTable("Deposit Report"));
		document.add(detailPdfTable());
		/////////// add the summary page ////////////
		System.out.println("createDepoitTable() method called");
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
		cell.add(new Paragraph(title + " #" + depositDTO.getBatch())).setFontSize(20);
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
		cell.add(new Paragraph(depositDTO.getDepositDate() + "")).setFontSize(10);
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
	private void detailHeaderRow(Table detailTable, InvoiceWithMemberInfoDTO invoice) {
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
				cell.add(new Paragraph(invoice.getMembershipId() + "")).setFontSize(10);
			if(i==1)
				cell.add(new Paragraph(invoice.getL_name() + "")).setFontSize(10);
			detailTable.addCell(cell);
		}
	}

	// this table shows the details for each membership
	private Table detailPdfTable() {
		Table detailTable = new Table(5);
		for(InvoiceWithMemberInfoDTO i: invoices)  // Scroll through invoices
		{
			detailHeaderRow(detailTable,i);
			for(InvoiceItemDTO ii: invoiceItems) { // Scroll through invoice items
				if(ii.getInvoiceId() == i.getId()) {  // get invoice item that matches this invoice
					if(!ii.getValue().equals("0.00")) {  // if invoice item is not 0.00
						addItemRow(detailTable, ii.getItemType(), ii.getValue(),ii.getQty());
					}
				}
			}
			String totalDue = String.valueOf(new BigDecimal(i.getTotal()).subtract(new BigDecimal(i.getCredit())));
			addItemRow(detailTable, "Total Fees", i.getTotal(), 0);
			addItemRow(detailTable,"Total Credit", i.getCredit(), 0);
			addItemRow(detailTable, "Total Due", totalDue,0);
			addItemPaidRow(detailTable, i);
			if (SqlExists.memoExists(i.getId(), "I"))
				addNoteRow(detailTable, " ",getNote(i,"I"));
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

	private String getNote(InvoiceWithMemberInfoDTO invoice, String category) {
		String thisMemo;
		// make sure the memo exists
		if(SqlExists.memoExists(invoice.getId(), category)) {
		thisMemo = SqlMemos.getMemos(invoice, category).getMemo();
		} else {
		thisMemo = "No note for this entry";
		}
		return thisMemo;
	}

	private void addItemPaidRow(Table detailTable, InvoiceDTO invoiceDTO) {
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
				cell.add(new Paragraph(addDollarSign() + invoiceDTO.getPaid())).setFontSize(10);
			}
			detailTable.addCell(cell);
		}
	}
	
	private void addItemRow(Table detailTable, String label, String money, int numberOf) {
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph(label)).setFontSize(10));
		if(numberOf == 0) {
		detailTable.addCell(new Cell().setBorder(Border.NO_BORDER));	
		} else {
			detailTable.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER).add(new Paragraph("" + numberOf)).setFontSize(10));	
		}
		detailTable.addCell(new Cell().setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).add(new Paragraph(addDollarSign() + money)).setFontSize(10));
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

	public Table summaryPdfTable() {
		Table mainTable = new Table(TDRHeaders.length);
		// mainTable.setKeepTogether(true);
		Cell cell;
		for (String str : TDRHeaders) {
			mainTable.addCell(new Cell().setBackgroundColor(new DeviceCmyk(.5f, .24f, 0, 0.02f))
					// .setWidth(12)
					.add(new Paragraph(str).setFontSize(10)));
		}

		mainTable.addCell(new Cell().setWidth(80).add(new Paragraph(depositDTO.getDepositDate()).setFontSize(10)));
		mainTable.addCell(new Cell().setWidth(100).add(new Paragraph("" + depositDTO.getBatch()).setFontSize(10)));
		mainTable.addCell(new Cell().setWidth(200));
		mainTable.addCell(new Cell().setWidth(70));
		mainTable.addCell(new Cell().setWidth(40));

		for (InvoiceItemDTO item : invoiceSummedItems) {
			if (!item.getValue().equals("0.00"))
				addSummaryRow(mainTable, item);
		}

		RemoveBorder(mainTable);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setBorderBottom(new DoubleBorder(ColorConstants.BLACK, 2));
		if(invoices.size() == 1) {
			cell.add(new Paragraph("1 Record").setFontSize(10));
		} else {
			cell.add(new Paragraph(invoices.size() + " Records").setFontSize(10));
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
		cell.add(new Paragraph("0.00").setFontSize(10));
		mainTable.addCell(cell);

		return mainTable;
	}

	public String addDollarSign() {
		String sign = "";
		if(includeDollarSigns) sign="$";
		return sign;
	}
	
//    public static void sortByMembershipId() {
//		  paidDuesForDeposit.sort(Comparator.comparingInt(PaidDuesDTO::getMembershipId));
//    }
    
	public Table headerPdfTable(String summary) {
		Table mainTable = new Table(1);
		Cell cell;
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(new Paragraph(summary)).setFontSize(20);
		mainTable.addCell(cell);		
		return mainTable;
	}
	
	private void addSummaryRow(Table mainTable, InvoiceItemDTO item) {
		mainTable.addCell(new Cell());
		mainTable.addCell(new Cell());
		mainTable.addCell(new Cell().add(new Paragraph(item.getItemType())).setFontSize(10));
		mainTable.addCell(new Cell().add(new Paragraph(String.valueOf(item.getQty()))).setFontSize(10));
		mainTable.addCell(new Cell().add(new Paragraph(item.getValue()).setFontSize(10)).setTextAlignment(TextAlignment.RIGHT));
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
