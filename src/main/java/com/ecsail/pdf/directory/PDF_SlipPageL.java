package com.ecsail.pdf.directory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;

public class PDF_SlipPageL extends Table {
	PDF_Object_Settings set;
	
	public PDF_SlipPageL(int numColumns, PDF_Object_Settings set) {
		super(numColumns);
		this.set = set;
		setWidth(set.getPageSize().getWidth() * 0.95f);
		setHorizontalAlignment(HorizontalAlignment.CENTER);
		Cell cell;
		cell = new Cell();
		cell.add(new PDF_Dock(3,"D",10,10,set,true));
		cell.setBorder(Border.NO_BORDER);
		addCell(cell);
		
		cell = new Cell(2,1);
		cell.add(new PDF_Dock(3,"A",6,11,set,true));
		cell.setBorder(Border.NO_BORDER);
		
		Paragraph p;
		p = new Paragraph("\n\n" + set.getSelectedYear() +" Dock Assignments");
		p.setFontColor(set.getMainColor());
		p.setFont(set.getColumnHead());
		p.setTextAlignment(TextAlignment.CENTER);
		cell.add(p);
		
		p = new Paragraph("Sublease in blue");
		p.setFontColor(ColorConstants.BLUE);
		p.setFontSize(set.getSlipFontSize());
		p.setTextAlignment(TextAlignment.CENTER);
		cell.add(p);
		addCell(cell);
		
		cell = new Cell();
		cell.add(new PDF_Dock(3,"F",0,5,set,true));
		cell.setBorder(Border.NO_BORDER);
		addCell(cell);
	}

	
}
