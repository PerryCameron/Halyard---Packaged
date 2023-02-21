package com.ecsail.pdf;

import com.ecsail.HalyardPaths;
import com.ecsail.sql.select.SqlMembershipList;
import com.ecsail.sql.select.SqlMembership_Id;
import com.ecsail.dto.MembershipIdDTO;
import com.ecsail.dto.MembershipListDTO;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.AreaBreakType;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PDF_Envelope {
	Image ecscLogo = new Image(ImageDataFactory.create(PDF_DepositReport.toByteArray(getClass().getResourceAsStream("/EagleCreekLogoForPDF.png"))));
	MembershipListDTO membership;
	private String year;
	private String current_membership_id;
	private static int ms_id;
	private List<MembershipIdDTO> ids = new ArrayList<MembershipIdDTO>();
	PdfFont font;
	private boolean isOneMembership;
	
	public PDF_Envelope(boolean iom, boolean size6x9, String membership_id) throws IOException {
		this.year= HalyardPaths.getYear();
		HalyardPaths.checkPath(HalyardPaths.ECSCHOME);
		this.current_membership_id = membership_id;
		this.isOneMembership = iom;

		if(System.getProperty("os.name").equals("Windows 10"))
		FontProgramFactory.registerFont("c:/windows/fonts/times.ttf", "times");
		else if(System.getProperty("os.name").equals("Mac OS X"))
		FontProgramFactory.registerFont("/Library/Fonts/SF-Compact-Display-Regular.otf", "times");
			
		
		this.font = PdfFontFactory.createRegisteredFont("times");
		// Initialize PDF writer
		
		// Envelope sizeing https://www.paperpapers.com/envelope-size-chart.html
		// No. 6 1/4 (#6-1/4) Envelope inches: 3.5 x 6 (mm: 88.9 x 152.4)
		// 6 inch x 72 points = 432 points (the width)
		// 3.5 inch x 72 points = 252 points (the height)
		
		if(size6x9) {
			create6x9 ();
		} else {
			create4x9 ();
		}
		
		//		p.setFontSize(10);
		//  p.setFixedLeading(10);
		
		

		System.out.println("destination=" + HalyardPaths.ECSCHOME + "_envelopes.pdf");
		File file = new File(HalyardPaths.ECSCHOME + "_envelopes.pdf");
		Desktop desktop = Desktop.getDesktop(); // Gui_Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()

		// Open the document
		try {
			desktop.open(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// #10 Envelope inches: 4.125 x 9.5 (mm: 104.775 x 241.3)
	// 9.5 x 72 points = 684 points (the width)
	// 4.125 x 72 points = 297 points (the height)
	public void create4x9 () throws FileNotFoundException {
		PdfWriter writer = new PdfWriter(HalyardPaths.ECSCHOME + "_envelopes.pdf");
		// Initialize PDF document
		PdfDocument pdf = new PdfDocument(writer);
		Rectangle envelope = new Rectangle(684, 297);
		// Initialize document
		Document doc = new Document(pdf, new PageSize(envelope));
		doc.setTopMargin(0);
		doc.setLeftMargin(0.25f);
		if(isOneMembership) {
			ms_id = SqlMembership_Id.getMsidFromMembershipID(Integer.parseInt(current_membership_id));
			membership = SqlMembershipList.getMembershipFromList(ms_id, year);
		doc.add(createReturnAddress());
		doc.add(new Paragraph(new Text("\n\n\n\n\n")));
		doc.add(createAddress());
		} else {
			ids = SqlMembership_Id.getAllMembershipIdsByYear(year);
			Collections.sort(ids, Comparator.comparing(MembershipIdDTO::getMembership_id));

			for(MembershipIdDTO id: ids) {
				current_membership_id = id.getMembership_id();
				ms_id = SqlMembership_Id.getMsidFromMembershipID(Integer.parseInt(current_membership_id));
				membership = SqlMembershipList.getMembershipFromList(ms_id, year);
				doc.add(createReturnAddress());
				doc.add(new Paragraph(new Text("\n\n\n\n\n")));
				doc.add(createAddress());
				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
			}
		}
		doc.close();
	}
	
	// #1 Catalog inches: 6 x 9 (mm: 152.4 x 228.6)
	// 9 x 72 points = 648 (the width)
	// 6 x 72 points = 432 (the height)
	public void create6x9 () throws FileNotFoundException {
		PdfWriter writer = new PdfWriter(HalyardPaths.ECSCHOME + "_envelopes.pdf");
		// Initialize PDF document
		PdfDocument pdf = new PdfDocument(writer);
		Rectangle envelope = new Rectangle(648, 432);
		// Initialize document
		Document doc = new Document(pdf, new PageSize(envelope));
		doc.setTopMargin(0);
		doc.setLeftMargin(0.25f);
		if(isOneMembership) {
			ms_id = SqlMembership_Id.getMsidFromMembershipID(Integer.parseInt(current_membership_id));
			membership = SqlMembershipList.getMembershipFromList(ms_id, year);
		doc.add(createReturnAddress());
		doc.add(new Paragraph(new Text("\n\n\n\n\n\n\n\n\n")));
		doc.add(createAddress());
		} else {
			ids = SqlMembership_Id.getAllMembershipIdsByYear(year);
			Collections.sort(ids, Comparator.comparing(MembershipIdDTO::getMembership_id));
			for(MembershipIdDTO id: ids) {
				current_membership_id = id.getMembership_id();
				ms_id = SqlMembership_Id.getMsidFromMembershipID(Integer.parseInt(current_membership_id));
				membership = SqlMembershipList.getMembershipFromList(ms_id, year);
				doc.add(createReturnAddress());
				doc.add(new Paragraph(new Text("\n\n\n\n\n")));
				doc.add(createAddress());
				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
			}
		}
		doc.close();
	}
	
	public Table createReturnAddress() {
		Table mainTable = new Table(2);
		mainTable.setWidth(290);
		ecscLogo.scale(0.25f, 0.25f);
		
		Cell cell;
		Paragraph p;

		cell = new Cell(3,1);
		cell.setWidth(40);
		cell.setBorder(Border.NO_BORDER);
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
		cell.add(ecscLogo);
		mainTable.addCell(cell);
		
		p = new Paragraph("ECSC Membership");
		p.setFont(font);
		p.setFontSize(10);
		p.setFixedLeading(10);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("7078 Windridge Way");
		p.setFont(font);
		p.setFontSize(10);
		p.setFixedLeading(10);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph("Brownsburg, IN 46112");
		p.setFont(font);
		p.setFontSize(10);
		p.setFixedLeading(10);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);
		mainTable.addCell(cell);
		
		return mainTable;
	}
	
	public Table createAddress() {
		Table mainTable = new Table(2);
		mainTable.setWidth(590);
		ecscLogo.scale(0.25f, 0.25f);
		
		Cell cell;
		Paragraph p;

		cell = new Cell(3,1);
		cell.setWidth(260);
		cell.setBorder(Border.NO_BORDER);


		mainTable.addCell(cell);
		
		p = new Paragraph(membership.getfName() + " " + membership.getlName() + " #" + current_membership_id);
		p.setFont(font);
		p.setFontSize(16);
		p.setFixedLeading(14);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph(membership.getAddress());
		p.setFont(font);
		p.setFontSize(16);
		p.setFixedLeading(14);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);
		mainTable.addCell(cell);
		
		p = new Paragraph(membership.getCity() + ", " + membership.getState() + " " + membership.getZip());
		p.setFont(font);
		p.setFontSize(16);
		p.setFixedLeading(14);
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);
		mainTable.addCell(cell);
		
		return mainTable;
	}

}
