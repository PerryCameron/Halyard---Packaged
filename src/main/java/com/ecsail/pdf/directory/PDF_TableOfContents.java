package com.ecsail.pdf.directory;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

public class PDF_TableOfContents extends Table {
	//String selectedYear;
	//int normalFontSize = 10;
	//int fixedLeading = 25;
	//float titleBoxHeight = 20;
	PDF_Object_Settings set;
	public PDF_TableOfContents(int numColumns, PDF_Object_Settings set) {
		super(numColumns);
		this.set = set;
		////////////////// Table Properties //////////////////
		setWidth(PageSize.A5.getWidth() * 0.9f);  // makes table 90% of page width
		setHorizontalAlignment(HorizontalAlignment.CENTER);
		
		///////////////// Cells ////////////////////////////
		Cell cell;
		Paragraph p;
		
		addCell(addVerticalSpace(2));
		
		cell = new Cell();
		//cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		cell.setHeight(set.getTitleBoxHeight() + 10);
		cell.setBorder(Border.NO_BORDER);
		p = new Paragraph("Membership Directory");
		p.setFontSize(set.getNormalFontSize() + 8);
		p.setFont(set.getColumnHead());
		p.setFontColor(set.getMainColor());
		p.setFixedLeading(set.getFixedLeading());  // sets spacing between lines of text
		p.setTextAlignment(TextAlignment.CENTER);
		cell.add(p);
		
		addCell(cell);
		addCell(addVerticalSpace(1));
		addCell(addChapter("Commodore Greeting"));
		addCell(addVerticalSpace(1));
		addCell(addChapter("Officers, Committee Chairs, Board Members"));
		addCell(addVerticalSpace(1));
		addCell(addChapter("Membership Information"));
		addCell(addVerticalSpace(1));
		addCell(addChapter("Members - Listed by Number"));
		addCell(addVerticalSpace(1));
		addCell(addChapter("Wet Slip Chart"));
		addCell(addVerticalSpace(1));
		addCell(addChapter("Sportsmanship of the Year Award"));
		addCell(addVerticalSpace(1));
		addCell(addChapter("Past Commodores"));
		addCell(addVerticalSpace(2));
		addAddress();
	}
	
	private Cell addChapter(String heading) {
		Cell cell = new Cell();
		Paragraph p;
		cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		cell.setBorder(Border.NO_BORDER);
		p = new Paragraph(heading);
		p.setFontSize(set.getNormalFontSize() + 4);
		p.setFixedLeading(set.getFixedLeading());  // sets spacing between lines of text
		p.setTextAlignment(TextAlignment.CENTER);
		cell.add(p);
		return cell;
	}
	
	private Cell addVerticalSpace(int space) {
		String carrageReturn = "";
		for(int i = 0; i < space; i++) {
			carrageReturn += "\n";
		}
		Cell cell = new Cell();
		cell.add(new Paragraph(carrageReturn));
		cell.setBorder(Border.NO_BORDER);
		return cell;
	}
	
	private void addAddress() {
		int fixLeadOffset = 15;
		Cell cell; 
		Paragraph p;
		
		cell = new Cell();
		cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		cell.setBorder(Border.NO_BORDER);
		p = new Paragraph("Eagle Creek Sailing Club");
		p.setFontSize(set.getNormalFontSize() + 2);
		p.setFixedLeading(set.getFixedLeading() -fixLeadOffset +3);  // sets spacing between lines of text
		p.setTextAlignment(TextAlignment.CENTER);
		cell.add(p);
		this.addCell(cell);
		
		cell = new Cell();
		cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		cell.setBorder(Border.NO_BORDER);
		p = new Paragraph("8901 W. 46th Street");
		p.setFontSize(set.getNormalFontSize());
		p.setFixedLeading(set.getFixedLeading() - fixLeadOffset);  // sets spacing between lines of text
		p.setTextAlignment(TextAlignment.CENTER);
		cell.add(p);
		this.addCell(cell);
		
		cell = new Cell();
		cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		cell.setBorder(Border.NO_BORDER);
		p = new Paragraph("Indianapolis, IN 46234");
		p.setFontSize(set.getNormalFontSize());
		p.setFixedLeading(set.getFixedLeading() - fixLeadOffset);  // sets spacing between lines of text
		p.setTextAlignment(TextAlignment.CENTER);
		cell.add(p);
		this.addCell(cell);
		
		cell = new Cell();
		cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		cell.setBorder(Border.NO_BORDER);
		p = new Paragraph("Website: ecsail.org");
		p.setFontSize(set.getNormalFontSize());
		p.setFixedLeading(set.getFixedLeading() - fixLeadOffset);  // sets spacing between lines of text
		p.setTextAlignment(TextAlignment.CENTER);
		cell.add(p);
		this.addCell(cell);

	}

}
