package com.ecsail.pdf.directory;

import com.ecsail.sql.select.SqlSportsMan;
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


public class PDF_SportsmanAward extends Table {
	ArrayList<Object_Sportsmen> sportsman = new ArrayList<Object_Sportsmen>();
	ArrayList<Table> tables = new ArrayList<Table>(); // Stores the column tables
	ArrayList<Integer> matched = new ArrayList<Integer>();
	ArrayList<Object_Sportsmen> duplicates = new ArrayList<Object_Sportsmen>();
	PDF_Object_Settings set;
	
	public PDF_SportsmanAward(int numColumns, PDF_Object_Settings set) {
		super(numColumns);
		this.set = set;
		this.sportsman = SqlSportsMan.getSportsManAwardNames();
		Collections.sort(sportsman , Comparator.comparing(Object_Sportsmen::getYear));
		popCoWinners();
		setWidth(PageSize.A5.getWidth() * 0.8f);  // makes table 90% of page width
		setHorizontalAlignment(HorizontalAlignment.CENTER);

		// removes the duplicates by reference
		for(Object_Sportsmen m: duplicates) {
			sportsman.remove(m);
			//System.out.println(m);
		}

		
		
		// sort commodore list by year
		//Collections.sort(sportsman , Comparator.comparing(Object_Sportsmen::getYear));
		// remove the current commodore

		// used to know where we are in iterations
		int count = 0;
		// number of columns set in settings
		int numbColumns = set.getNumberOfCommodoreColumes();
		// divide number of commodores by columns we desire;
		int columnLength = sportsman.size() / numbColumns;
		
		int remainder = sportsman.size() % numbColumns;
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
		p = new Paragraph("Sportsmanship Award");
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
			columnTable.addCell(createSportsManEntry(sportsman.get(count )));
			count++;
		}
		return count;
	}
	
	// creates a single cell with membership names
	private Cell createSportsManEntry(Object_Sportsmen c) {
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
	
	
	// combines dual winners	
	boolean matchedPair = false;
	private void popCoWinners() {
		for (int a = 0; a < sportsman.size(); a++) {
			for (int b = 0; b < sportsman.size(); b++) {
				if(sportsman.get(a) != sportsman.get(b)) {  // no need to compare same object
					if (sportsman.get(a).getYear().equals(sportsman.get(b).getYear())) {
						/////// Found a Match !!  ////////	
						if(matched.size() == 0) {  // make sure we have put in entries first
							matched.add(a);  // put in first number
							//System.out.println("Count A:" + a + " matches " + "Count B:" + b);
							// change first name to be both the first and last name
							sportsman.get(a).setfName(sportsman.get(a).getfName() + " " + sportsman.get(a).getlName() + "/" + sportsman.get(b).getfName() + " " + sportsman.get(b).getlName());
							// change last name to be second person
							sportsman.get(a).setlName("");
						} else { // we have already found a match so now we need to start checking for duplicates
							for(Integer m: matched) {
								if(m == b) matchedPair = true;  // ok we found a duplicate
							}
							if(matchedPair) {
								//System.out.println("we found a duplicate and will remove Count " + a);
								duplicates.add(sportsman.get(a));
								matchedPair = false;  // set the flag back to false				
							} else {
								matched.add(a);
								//System.out.println("Count A:" + a + " matches " + "Count B:" + b);
								// change first name to be both the first and last name
								sportsman.get(a).setfName(sportsman.get(a).getfName() + " " + sportsman.get(a).getlName() + "/" + sportsman.get(b).getfName() + " " + sportsman.get(b).getlName());
								// change last name to be second person
								sportsman.get(a).setlName("");
							}
						}
					}  // end of finding match
				}
			}
		}
	}
	

}
