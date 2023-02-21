package com.ecsail.pdf.directory;

import com.ecsail.sql.select.SqlOfficer;
import com.ecsail.dto.OfficerWithNameDTO;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class PDF_CommodoreList extends Table {
	ArrayList<OfficerWithNameDTO> commodores = new ArrayList<OfficerWithNameDTO>();
	ArrayList<Table> tables = new ArrayList<Table>(); // Stores the column tables
	PDF_Object_Settings set;
	
	public PDF_CommodoreList(int numColumns, PDF_Object_Settings set) {
		super(numColumns);
		this.set = set;
		this.commodores = SqlOfficer.getOfficersWithNames("CO");
		setWidth(PageSize.A5.getWidth() * 0.8f);  // makes table 90% of page width
		setHorizontalAlignment(HorizontalAlignment.CENTER);
		// sort commodore list by year
		Collections.sort(commodores , Comparator.comparing(OfficerWithNameDTO::getYear));
		// remove the current commodore
		commodores.remove(commodores.size() -1);
		// used to know where we are in iterations
		int count = 0;
		// number of columns set in settings
		int numbColumns = set.getNumberOfCommodoreColumes();
		// divide number of commodores by columns we desire;
		int columnLength = commodores.size() / numbColumns;
		
		int remainder = commodores.size() % numbColumns;
		// if the number of commodores is uneven make the columns the largest size
		if(remainder > 0) columnLength++;
		
		Table columnTable;

			columnTable = new Table(1);
			count = fillColumnTable(columnLength,count,columnTable);
			tables.add(columnTable);

			columnTable = new Table(1);
			count = fillColumnTable(columnLength - remainder, count, columnTable);
			tables.add(columnTable);

		
		Paragraph p;

		Cell cell;
		
		cell = new Cell(1,2);
		p = new Paragraph("\n\n");
		cell.add(p);
		cell.setBorder(Border.NO_BORDER);
		addCell(cell);
		
		
		cell = new Cell(1,2);
		p = new Paragraph("Past Commodores");
		p.setFont(set.getColumnHead());
		p.setFontColor(set.getMainColor());
		p.setFontSize(set.getNormalFontSize() + 10);
		p.setTextAlignment(TextAlignment.CENTER);
		cell.add(p);
		cell.setBorder(Border.NO_BORDER);
		addCell(cell);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(tables.get(0));
		addCell(cell);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(tables.get(1));
		addCell(cell);
		setHorizontalAlignment(HorizontalAlignment.CENTER);
	}
	
	private int fillColumnTable(int columnSize, int count, Table columnTable) {
		for(int i = 0; i < columnSize; i++) {
			columnTable.addCell(createCommodoreEntry(commodores.get(count )));
			count++;
		}
		return count;
	}
	
	// creates a single cell with membership names
	private Cell createCommodoreEntry(OfficerWithNameDTO c) {
		Cell cell;
		Paragraph p;
		cell = new Cell();
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		cell.setBorder(Border.NO_BORDER);
		p = new Paragraph(c.getYear() + "  " + c.getfName() + " " + c.getlName());
		p.setFontSize(set.getNormalFontSize());
		p.setTextAlignment(TextAlignment.LEFT);
		cell.add(p);
		return cell;
	}
	
}
