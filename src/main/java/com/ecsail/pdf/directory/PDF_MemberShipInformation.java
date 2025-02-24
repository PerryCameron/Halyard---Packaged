package com.ecsail.pdf.directory;

import com.ecsail.dto.MembershipListDTO;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

public class PDF_MemberShipInformation extends Table {
	PDF_Object_Settings set;
	
	public PDF_MemberShipInformation(int numColumns, MembershipListDTO m, PDF_Object_Settings set) {
		super(numColumns);
		this.set = set;
		Object_MembershipInformation ms = new Object_MembershipInformation(m);

		setWidth(set.getPageSize().getWidth() * 0.9f);  // makes table 90% of page width
		setHorizontalAlignment(HorizontalAlignment.CENTER);
		
		Cell cell = null;
		Paragraph p;
		String slip;
		
		
		if (m.getSlip() != null)
			slip = "Slip: " + m.getSlip();
		else
			slip = "";
		p = new Paragraph("#" + m.getMembershipId() + "  Type: " + m.getMemType() + "    " + slip);
		p.setFontSize(set.getNormalFontSize()).setFixedLeading(set.getFixedLeadingNarrow());
		p.setFontColor(ColorConstants.WHITE);
		cell.setBorder(Border.NO_BORDER).setBorderTop(new SolidBorder(0.5f))
				.setBackgroundColor(set.getMainColor()).setVerticalAlignment(VerticalAlignment.MIDDLE)
				.setWidth(this.getWidth().getValue() * 0.5f).setHorizontalAlignment(HorizontalAlignment.CENTER).add(p);
		addCell(cell);
		
		cell = new Cell();
		p = new Paragraph(ms.getEmergencyPhone());
		p.setFontColor(ColorConstants.WHITE);
		p.setFontSize(set.getNormalFontSize()).setFixedLeading(set.getFixedLeadingNarrow());
		cell.setBorder(Border.NO_BORDER).setBorderTop(new SolidBorder(0.5f))
				.setBackgroundColor(set.getMainColor()).setVerticalAlignment(VerticalAlignment.MIDDLE)
				.setHorizontalAlignment(HorizontalAlignment.RIGHT).add(p);
		addCell(cell);

		cell = new Cell();
		p = new Paragraph(ms.getPrimary().getLastName() + ", " + ms.getPrimary().getFirstName());
		p.setFontSize(set.getNormalFontSize()).setFixedLeading(set.getFixedLeadingNarrow());
		cell
			.setBorder(Border.NO_BORDER).add(p);
		addCell(cell);
		
		cell = new Cell();
		if(ms.getSecondaryExists())
		p = new Paragraph(ms.getSecondary().getLastName() + ", " + ms.getSecondary().getFirstName());
		else
		p = new Paragraph("");
		p.setFontSize(set.getNormalFontSize()).setFixedLeading(set.getFixedLeadingNarrow());
		cell.setBorder(Border.NO_BORDER).add(p);
		addCell(cell);
		/////////// BEGIN PHONE /////
		if(!(ms.getPrimaryPhone().equals("") && ms.getPrimaryPhone().equals(""))) {
		p = new Paragraph(ms.getPrimaryPhone());
		//p.setFont(font);
		p.setFontSize(set.getNormalFontSize());
		p.setFixedLeading((set.getFixedLeadingNarrow()));
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);
		addCell(cell);
		
		p = new Paragraph(ms.getSecondaryPhone());
		//p.setFont(font);
		p.setFontSize(set.getNormalFontSize());
		p.setFixedLeading((set.getFixedLeadingNarrow()));
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);
		addCell(cell);
		}
		///////////END PHONE ////////////////

		p = new Paragraph(ms.getPrimaryEmail()).setFontColor(ColorConstants.BLUE);
		//p.setFont(font);
		p.setFontSize(set.getNormalFontSize());
		p.setFixedLeading((set.getFixedLeadingNarrow()));
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);
		addCell(cell);
		
		p = new Paragraph(ms.getSecondaryEmail()).setFontColor(ColorConstants.BLUE);
		//p.setFont(font);
		p.setFontSize(set.getNormalFontSize());
		p.setFixedLeading((set.getFixedLeadingNarrow()));
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);
		addCell(cell);
		
		p = new Paragraph(m.getAddress() + " " + m.getCity() + " " + m.getState() + " " + m.getZip());
		//p.setFont(font);
		p.setFontSize(set.getNormalFontSize());
		p.setFixedLeading((set.getFixedLeadingNarrow()));
		cell = new Cell(1,2);
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);
		addCell(cell);
		
		if(!ms.getBoats().equals("")) {
		p = new Paragraph(ms.getBoats());
		//p.setFont(font);
		p.setFontSize(set.getNormalFontSize());
		p.setFixedLeading((set.getFixedLeadingNarrow()));
		cell = new Cell(1,2);
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);
		addCell(cell);
		}
		
		if(!ms.getChildren().equals("")) {
		p = new Paragraph(ms.getChildren());
		//p.setFont(font);
		p.setFontSize(set.getNormalFontSize());
		p.setFixedLeading((set.getFixedLeadingNarrow()));
		cell = new Cell(1,2);
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);
		addCell(cell);
		}

	}
}
