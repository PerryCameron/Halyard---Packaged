package com.ecsail.pdf;

import com.ecsail.HalyardPaths;
import com.ecsail.views.common.SaveFileChooser;
import com.ecsail.pdf.directory.PDF_Dock;
import com.ecsail.pdf.directory.PDF_Object_Settings;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PDF_SlipChart {

	private PDF_Object_Settings set;

	public PDF_SlipChart(String year) {
		this.set = new PDF_Object_Settings(year);
		File fileToOpen;
		fileToOpen = createChart();
		if (fileToOpen != null) {  // if we didn't cancel
			Desktop desktop = Desktop.getDesktop(); // Gui_Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()
			// Open the document
			try {
				desktop.open(fileToOpen);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public File createChart() {
		HalyardPaths.checkPath(HalyardPaths.SLIP_CHART);
		File file = new SaveFileChooser(HalyardPaths.SLIP_CHART + "/", "SlipChart_" + HalyardPaths.getDate() + ".pdf", "PDF files",
				"*.pdf").getFile();
		if (file != null) {
			PdfWriter writer = null;
			try {
				writer = new PdfWriter(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			// Initialize PDF document
			PdfDocument pdf = new PdfDocument(writer);
			Rectangle envelope = new Rectangle(PageSize.A4.getHeight(), PageSize.A4.getWidth());
			Document doc = new Document(pdf, new PageSize(envelope));
			doc.setLeftMargin(0.5f);
			doc.setRightMargin(0.5f);
			doc.setTopMargin(1f);
			doc.setBottomMargin(0.5f);
			Table mainTable = new Table(4);
			mainTable.setWidth(PageSize.A4.getHeight() * 0.95f); // actually sets the width
			mainTable.setHorizontalAlignment(HorizontalAlignment.CENTER);

			Cell cell;
			cell = new Cell();
			cell.add(new PDF_Dock(3, "A", 10, 10, set, false));
			cell.setBorder(Border.NO_BORDER);
			mainTable.addCell(cell);

			cell = new Cell(2, 1);
			cell.add(new PDF_Dock(3, "B", 6, 11, set, false));
			cell.setBorder(Border.NO_BORDER);
			mainTable.addCell(cell);

			cell = new Cell(2, 1);
			cell.add(new PDF_Dock(3, "C", 11, 11, set, false));
			cell.setBorder(Border.NO_BORDER);
			mainTable.addCell(cell);

			/////// LEGEND /////////
			Paragraph p;
			p = new Paragraph("\n\n" + set.getSelectedYear() + " Dock Assignments");
			p.setFontColor(set.getMainColor());
			p.setFont(set.getColumnHead());
			p.setTextAlignment(TextAlignment.CENTER);
			cell.add(p);

			p = new Paragraph("Sublease ***");
			// p.setFontColor(ColorConstants.BLUE);
			p.setFontSize(set.getSlipFontSize());
			p.setTextAlignment(TextAlignment.CENTER);
			cell.add(p);

			//////// LEGEND END //////

			cell = new Cell(2, 1);
			cell.add(new PDF_Dock(3, "D", 11, 11, set, false));
			cell.setBorder(Border.NO_BORDER);
			mainTable.addCell(cell);

			cell = new Cell();
			cell.add(new PDF_Dock(3, "F", 0, 5, set, false));
			cell.setBorder(Border.NO_BORDER);
			mainTable.addCell(cell);

			doc.add(mainTable);
			doc.close();
		}
		return file;
	}
}
