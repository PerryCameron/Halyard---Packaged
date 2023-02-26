package com.ecsail.pdf;


import com.ecsail.BaseApplication;
import com.ecsail.HalyardPaths;
import com.ecsail.gui.tabs.deposits.InvoiceWithMemberInfoDTO;
import com.ecsail.gui.tabs.deposits.TabDeposits;
import com.ecsail.repository.implementations.MemoRepositoryImpl;
import com.ecsail.repository.interfaces.MemoRepository;
import com.ecsail.sql.SqlExists;
import com.ecsail.sql.select.SqlDbInvoice;
import com.ecsail.sql.select.SqlDeposit;
import com.ecsail.sql.select.SqlInvoiceItem;
import com.ecsail.dto.*;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DoubleBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.AreaBreakType;
import com.itextpdf.layout.properties.TextAlignment;
import javafx.collections.ObservableList;

import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

public class PDF_DepositReport {
	protected MemoRepository memoRepository = new MemoRepositoryImpl();
	private final ObservableList<InvoiceItemDTO> invoiceItems;
	private final ArrayList<InvoiceItemDTO> invoiceSummedItems = new ArrayList<>();
	private final ObservableList<InvoiceWithMemberInfoDTO> invoices;
	private final DepositDTO depositDTO;
	String fiscalYear;  // save this because I clear current Deposit
	Boolean includeDollarSigns = false;
	static String[] TDRHeaders = {"Date", "Deposit Number", "Fee", "Records", "Amount"};

	public PDF_DepositReport(TabDeposits td, DepositPDFDTO pdfOptions) {
		this.depositDTO = td.getDepositDTO();
		this.invoices = td.getInvoices();
		this.invoiceItems = SqlInvoiceItem.getAllInvoiceItemsByYearAndBatch(depositDTO);
		BaseApplication.logger.info("Creating Deposit Report "
				+ depositDTO.getBatch() + " for " + depositDTO.getFiscalYear());
		this.fiscalYear = depositDTO.getFiscalYear();
		String dest;
		// get our categories
		// a list of types of invoice items for a given year
		ArrayList<String> invoiceItemTypes = new ArrayList<>(
				SqlDbInvoice.getInvoiceCategoriesByYear(Integer.parseInt(depositDTO.getFiscalYear())));
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
			e.printStackTrace();
		}
		// Initialize PDF document
		PdfDocument pdf = new PdfDocument(writer);
		// Initialize document
		Document document = new Document(pdf);
		
//		document.add(addLogoTable(document, ecscLogo));
		
		if (pdfOptions.isSingleDeposit()) { // are we only creating a report of a single deposit
			createDepositTable(document);
		} else { // we are creating a report for the entire year
			int numberOfBatches = SqlDeposit.getNumberOfDepositBatches(depositDTO.getFiscalYear()) + 1;
			for (int i = 1; i < numberOfBatches; i++) {
				createDepositTable(document);
				document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
			}
		}

		document.setTopMargin(0);
		// Close document
		document.close();
		File file = new File(dest);
		Desktop desktop = Desktop.getDesktop(); // Gui_Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()
		// Open the document
		try {
			desktop.open(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////   CLASS METHODS  /////////////////////////////////////////
	private void createDepositTable(Document document) {
		/////////// add the details pages ////////////
		document.add(titlePdfTable("Deposit Report"));
		document.add(detailPdfTable());
		/////////// add the summary page ////////////
		document.add(headerPdfTable("Summary"));
		document.add(summaryPdfTable());
	}
	
	public Table titlePdfTable(String title) {
		Image ecscLogo = new Image(ImageDataFactory.create(toByteArray(Objects.requireNonNull(getClass().getResourceAsStream("/EagleCreekLogoForPDF.png")))));
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
						addItemRow(detailTable, ii.getFieldName(), ii.getValue(),ii.getQty());
					}
				}
			}
			String totalDue = String.valueOf(new BigDecimal(i.getTotal()).subtract(new BigDecimal(i.getCredit())));
			addItemRow(detailTable, "Total Fees", i.getTotal(), 0);
			addItemRow(detailTable,"Total Credit", i.getCredit(), 0);
			addItemRow(detailTable, "Total Due", totalDue,0);
			addItemPaidRow(detailTable, i);
			if (SqlExists.memoExists(i.getId(), "I"))
				addNoteRow(detailTable, " ",getNote(i));
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

	private String getNote(InvoiceWithMemberInfoDTO invoice) {
		String thisMemo;
		// make sure the memo exists
		if(SqlExists.memoExists(invoice.getId(), "I")) {
//		thisMemo = SqlMemos.getMemoByMsId(invoice, "I").getMemo();
			thisMemo = memoRepository.getMemoByMsId(invoice, "I").getMemo();
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

	public Table summaryPdfTable() {
		Table mainTable = new Table(TDRHeaders.length);
		for (String str : TDRHeaders)
			mainTable.addCell(new Cell().setBackgroundColor(new DeviceCmyk(.5f, .24f, 0, 0.02f))
					.add(new Paragraph(str).setFontSize(10)));

		mainTable.addCell(new Cell().setWidth(80).add(new Paragraph(depositDTO.getDepositDate()).setFontSize(10)));
		mainTable.addCell(new Cell().setWidth(100).add(new Paragraph("" + depositDTO.getBatch()).setFontSize(10)));
		mainTable.addCell(new Cell().setWidth(200));
		mainTable.addCell(new Cell().setWidth(70));
		mainTable.addCell(new Cell().setWidth(40));
		for (InvoiceItemDTO item : invoiceSummedItems) {
			sumItemsForCategories(item);

			if (!item.getValue().equals("0.00")) {
				System.out.println(item.getFieldName() + " " + item.getQty() + " " + item.getValue());
				addSummaryRow(mainTable, item);
			}
		}
		RemoveBorder(mainTable);
		DepositTotalDTO depositTotal = SqlDeposit.getTotals(depositDTO, false); // gets count of totals
		for(int i = 0; i < 3; i++) {
			addTotalsFooter(mainTable, depositTotal.getFullLabels()[i],depositTotal.getValues()[i],i == 2);
		}
		return mainTable;
	}

	// TODO this is a temporary hack until I can make a boolean for if it is a category or not
	private void sumItemsForCategories(InvoiceItemDTO item) {
		BigDecimal value = new BigDecimal("0.00");
		int count = 0;
		if(item.getFieldName().equals("Summer Storage")) {
			for(InvoiceItemDTO i: invoiceItems) {
				if(!i.getValue().equals("0.00")) {
					if (i.getFieldName().equals("Beam Over 5 foot")) {
						value = value.add(new BigDecimal(i.getValue()));
						count+= i.getQty();
					}
					if (i.getFieldName().equals("Beam Under 5 foot")) {
						value = value.add(new BigDecimal(i.getValue()));
						count+= i.getQty();
					}
				}
			}

		}
		if(item.getFieldName().equals("Keys")) {
			for(InvoiceItemDTO i: invoiceItems) {
				if(!i.getValue().equals("0.00")) {
					if (i.getFieldName().equals("Gate Key")) {
						value = value.add(new BigDecimal(i.getValue()));
						count+= i.getQty();
					}
					if (i.getFieldName().equals("Sail Loft Key")) {
						value = value.add(new BigDecimal(i.getValue()));
						count+= i.getQty();
					}
					if (i.getFieldName().equals("Kayak Shed Key")) {
						value = value.add(new BigDecimal(i.getValue()));
						count+= i.getQty();
					}
				}
			}
		}
		if(item.getFieldName().equals("Kayak")) {
			for(InvoiceItemDTO i: invoiceItems) {
				if(!i.getValue().equals("0.00")) {
					if (i.getFieldName().equals("Kayak Rack")) {
						value = value.add(new BigDecimal(i.getValue()));
						count+= i.getQty();
					}
					if (i.getFieldName().equals("Kayak Beach Rack")) {
						value = value.add(new BigDecimal(i.getValue()));
						count+= i.getQty();
					}
					if (i.getFieldName().equals("Kayak Shed")) {
						value = value.add(new BigDecimal(i.getValue()));
						count+= i.getQty();
					}
				}
			}
			item.setFieldName("Kayak Storage");  // super hack!!! lol
		}
		BigDecimal test = new BigDecimal("0.00");
		int result = value.compareTo(test);
		if(result == 1) { /// more than 0 dollars
			item.setValue(String.valueOf(value));
			item.setQty(count);
		}
	}

	private String addRecordsLabel() {
		String size = invoices.size()  + " Record";
		if(invoices.size() > 1) return size.concat("s");
		else return size;
	}

	private void addTotalsFooter(Table mainTable, String label, String value, boolean hasBorder) {
		Cell cell;
		for (int i = 0; i < 5; i++) {
			cell = new Cell();
			cell.setBorder(Border.NO_BORDER);
			if (hasBorder) {
				cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
				cell.setBorderBottom(new DoubleBorder(ColorConstants.BLACK, 2));
			}
			if (i == 0 && label.equals("Total Funds Received")) cell
					.add(new Paragraph(addRecordsLabel()).setFontSize(10));
			if (i == 2) cell.add(new Paragraph(label).setFontSize(10));
			if (i == 4) cell.add(new Paragraph(value).setFontSize(10).setTextAlignment(TextAlignment.RIGHT));
			mainTable.addCell(cell);
		}
	}

	public String addDollarSign() {
		String sign = "";
		if(includeDollarSigns) sign="$";
		return sign;
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
	
	private void addSummaryRow(Table mainTable, InvoiceItemDTO item) {
		mainTable.addCell(new Cell());
		mainTable.addCell(new Cell());
		mainTable.addCell(new Cell().add(new Paragraph(item.getFieldName())).setFontSize(10));
		mainTable.addCell(new Cell()
				.add(new Paragraph(String.valueOf(item.getQty()))).setFontSize(10).setTextAlignment(TextAlignment.CENTER));
		mainTable.addCell(new Cell()
				.add(new Paragraph(item.getValue()).setFontSize(10)).setTextAlignment(TextAlignment.RIGHT));
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
