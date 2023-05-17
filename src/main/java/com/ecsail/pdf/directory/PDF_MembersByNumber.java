package com.ecsail.pdf.directory;

import com.ecsail.dto.MembershipListDTO;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.AreaBreakType;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PDF_MembersByNumber {
	ObservableList<MembershipListDTO> rosters;
	PDF_Object_Settings set;
	ArrayList<Table> tables = new ArrayList<Table>(); // Stores the column tables
	ArrayList<Object_StoreMemberPosition> position;
	
	public PDF_MembersByNumber(PDF_Object_Settings set, Document doc, ObservableList<MembershipListDTO> rosters) {
		this.set = set;
		this.rosters = rosters;
		this.position = new ArrayList<Object_StoreMemberPosition>();
		Collections.sort(this.rosters , Comparator.comparing(MembershipListDTO::getMembershipId));
		
		// used to know where we are in iterations
		int count = 0;
		// determines number of people we will put in a column
		int columnLength = this.set.getNumberOfRowsByNumber();
		// determines number of people left that won't perfectly fill a column, the straglers
		int remainder = rosters.size() % columnLength;
		// determines the total number of columns we have not including one needed for any straglers
		int numberOfColumnTables = rosters.size() / columnLength;
		// if we have straglers then we need to make one more column for them
		if(remainder > 0) numberOfColumnTables++;
		// 5 columns make a page, so we are going to create the number of pages not including any straglers
		int numberOfPdfPages = numberOfColumnTables / 5;
		// if there are any straggler columns then we will create another page
		if(numberOfColumnTables % 5 > 0) numberOfPdfPages++;
		
		
		Table columnTable;
		/// creates many column tables
		while(count < rosters.size() - remainder) {
			columnTable = new Table(1);
			count = fillColumnTable(columnLength,count,columnTable);
			tables.add(columnTable);
		}
		/// creates a column table with the remainder
		while(count < rosters.size()) {
			columnTable = new Table(1);
			count = fillColumnTable(remainder, count, columnTable);
			tables.add(columnTable);
		}
		
		// creates each PDF page adds content to page
		Paragraph p;
		Table table;
		Cell cell;

		for(int i = 0; i < numberOfPdfPages * 5; i+=5 ) {
		doc.add(new Paragraph("\n"));
		table = new Table(1);
		cell = new Cell();
		p = new Paragraph("Memberships " + position.get(i).getPersonStart() + " through " + position.get(i+4).getPersonEnd());
		p.setFont(set.getColumnHead());
		p.setFontColor(set.getMainColor());
		p.setFontSize(set.getNormalFontSize() + 10);
		cell.add(p);
		cell.setBorder(Border.NO_BORDER);
		table.addCell(cell);
		table.setHorizontalAlignment(HorizontalAlignment.CENTER);
		doc.add(table);
		doc.add(createPage(i));
		doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
		}
	}
	
	// Creates table with 5 columns
	private Table createPage(int start) {
		Table listTable = new Table(5);
		listTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
		for(int i = 0; i < 5; i++) {
			listTable.addCell(tables.get(i + start));
		}
		return listTable;
	}
	
	// Creates a table and fills with cells with member names
	private int fillColumnTable(int columnSize, int count, Table columnTable) {
		Object_StoreMemberPosition tc = null;
		for(int i = 0; i < columnSize; i++) {
			columnTable.addCell(createMembershipEntry(rosters.get(count )));
			
			if(i == 0) 
				tc = new Object_StoreMemberPosition(rosters.get(count ).getMembershipId(),0);

			if(i == columnSize - 1)
				tc.setPersonEnd(rosters.get(count ).getMembershipId());
			count++;
		}
		position.add(tc);
		return count;
	}
	
	// creates a single cell with membership names
	private Cell createMembershipEntry(MembershipListDTO m) {
		Cell cell;
		Paragraph p;
		cell = new Cell();
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		cell.setBorder(Border.NO_BORDER);
		p = new Paragraph(m.getMembershipId() + "  " + m.getLastName());
		p.setFontSize(set.getNormalFontSize() - 2);
		p.setTextAlignment(TextAlignment.LEFT);
		cell.add(p);
		return cell;
	}
}