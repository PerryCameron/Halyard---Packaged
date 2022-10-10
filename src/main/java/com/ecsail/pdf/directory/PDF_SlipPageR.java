package com.ecsail.pdf.directory;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;

public class PDF_SlipPageR extends Table {
	PDF_Object_Settings set;
	
	public PDF_SlipPageR(int numColumns, PDF_Object_Settings set) {
		super(numColumns);
		this.set = set;
		setWidth(set.getPageSize().getWidth() * 0.95f);
		setHorizontalAlignment(HorizontalAlignment.CENTER);
		Cell cell;
		cell = new Cell();
		cell.add(new PDF_Dock(3,"B",11,11,set,true));
		cell.setBorder(Border.NO_BORDER);
		addCell(cell);
		
		cell = new Cell();
		cell.add(new PDF_Dock(3,"C",11,11,set,true));
		cell.setBorder(Border.NO_BORDER);
		addCell(cell);
	}

	
}
