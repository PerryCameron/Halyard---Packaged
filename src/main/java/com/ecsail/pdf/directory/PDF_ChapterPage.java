package com.ecsail.pdf.directory;

import com.ecsail.HalyardPaths;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;


public class PDF_ChapterPage extends Table {

	PDF_Object_Settings set;
	public PDF_ChapterPage(int numColumns, String chapterText, PDF_Object_Settings set) {
		super(numColumns);
		this.set = set;
		setWidth(PageSize.A5.getWidth() * 0.9f);  // makes table 90% of page width
		setHorizontalAlignment(HorizontalAlignment.CENTER);
		
		///////////////// Cells ////////////////////////////
		Cell cell;
		Paragraph p;
		
		addCell(addVerticalSpace(9));
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		//cell.setHeight(set.getTitleBoxHeight() + 30);
		p = new Paragraph(chapterText);
		p.setFontSize(set.getNormalFontSize() + 18);
		p.setFont(set.getColumnHead());
		p.setFontColor(set.getMainColor());
		p.setTextAlignment(TextAlignment.CENTER);
		cell.add(p);
		
		addCell(cell);
		addCell(addVerticalSpace(3));
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		//cell.setHeight(set.getTitleBoxHeight() + 30);
		p = new Paragraph("Includes paid members as of " + HalyardPaths.getDate());
		p.setFontSize(set.getNormalFontSize());
		//p.setFont(set.getColumnHead());
		//p.setFontColor(set.getMainColor());
		p.setTextAlignment(TextAlignment.CENTER);
		cell.add(p);
		
		addCell(cell);
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		//cell.setHeight(set.getTitleBoxHeight() + 30);

		p = new Paragraph("Please notify the ECSC Membership Chair of any updates: ");
		p.setFontSize(set.getNormalFontSize());
		//p.setFont(set.getColumnHead());
		//p.setFontColor(set.getMainColor());
		p.setTextAlignment(TextAlignment.CENTER);
		cell.add(p);
		
		addCell(cell);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		p = new Paragraph("membership@ecsail.org");
		p.setFontSize(set.getNormalFontSize());
		//p.setFont(set.getColumnHead());
		p.setFontColor(ColorConstants.BLUE);
		p.setTextAlignment(TextAlignment.CENTER);
		cell.add(p);
		
		addCell(cell);
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

}
