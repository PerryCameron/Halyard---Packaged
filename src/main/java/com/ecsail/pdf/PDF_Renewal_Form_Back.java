package com.ecsail.pdf;

import com.ecsail.HalyardPaths;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.io.IOException;
import java.time.Year;


public class PDF_Renewal_Form_Back {
	
	public static void Create_Back_Side(Document document) throws IOException {
		int year = Integer.parseInt(String.valueOf(Year.now().getValue()));
		int nextYear = year + 1;
		//PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
		
		if(System.getProperty("os.name").equals("Windows 10"))
		FontProgramFactory.registerFont("c:/windows/fonts/times.ttf", "times");
		else if(System.getProperty("os.name").equals("Mac OS X"))
		FontProgramFactory.registerFont("/Library/Fonts/SF-Compact-Display-Regular.otf", "times");
		PdfFont font = PdfFontFactory.createRegisteredFont("times");
		
		List list = new List().setSymbolIndent(12)
	            .setListSymbol("\u2022")
	            .setFont(font)
	            ;
		
		
		document.add(new Paragraph(new Text("\n\n")));
		
		Table mainTable = new Table(1); 
		mainTable.setWidth(590);
		Paragraph p;
		Cell cell;
		ListItem listItem;
		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
        p = new Paragraph("Eagle Creek Sailing Club, Inc.");
        p.setTextAlignment(TextAlignment.CENTER);
        p.setFontSize(12).setBold();
        p.setFixedLeading(10);
		cell.add(p);
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		cell.setWidth(410);
		mainTable.addCell(cell);

		
		cell = new Cell();
		cell.setBorder(Border.NO_BORDER);
        p = new Paragraph("Annual Dues and Fees Statement Instructions and Information");
        p.setTextAlignment(TextAlignment.CENTER);
        p.setFontSize(12).setBold();
        p.setFixedLeading(10);
		cell.add(p);
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		cell.setWidth(410);
		mainTable.addCell(cell);
		document.add(mainTable);
		document.add(new Paragraph(new Text("\n")));
		
		
		list.add("By submitting this Membership Application you agree to abide by the "
				+ "Eagle Creek Sailing Club Bylaws and the General Rules of the Eagle Creek Sailing Club.");
		
		list.add("This membership year will extend from March 1, "+year+" to February 28, "+nextYear);
		
		/// had to take a few steps to add bold text	
		p = new Paragraph("All forms must be filled out in their entirety and returned to the Membership Chair "
				+ "with all dues and fees ");
		p.add(new Text("postmarked on or before the February 28, "+year+" deadline.").setBold());
		p.add("  If this deadline is missed, the member's name will be placed on inactive status, and the slip, "
				+ "beach spot or assigned boat storage space will be assigned to an active member.");
		listItem = new ListItem();
		listItem.add(p);
		list.add(listItem);
		 
		list.add("Donations for Junior Sailing Program are not tax deductible, per Internal Revenue "
				+ "Code Section 501 (C) (7), nor mandatory.  Donations will have no bearing on your status as a Club Member.");
		list.add("Being legally married 'under the laws of the state of Indiana' is required to hold a Family Membership.");
		list.add("Work Credit Discounts: The Chairperson of the committee of your activity has submitted your work credits "
				+ "to the Membership Chairperson and these credits are indicated on your Annual Dues Statement.  Any "
				+ "questions re: validity, please contact the Chairperson of the Committee");
		list.add("Monies received from you will be applied first to satisfy any outstanding balance, then to the "
				+ "annual dues requirement and finally to any optional fees");
		list.add("Members meeting the dues deadline criteria will be assigned the same space for their boat previously held, "
				+ "unless changed by the Harbormaster for just cause (in which case notification will be given).  "
				+ "If a change has been requested by the member, it will not be considered until the dues deadline has "
				+ "passed and all no-change assignments have been made.  If a request cannot be honored, the member "
				+ "will be given his/her prior space.  New wet slip assignments by the Harbormaster will be based on "
				+ "seniority of membership and wait list status.");
		////  BOLD TEXT HERE
		p = new Paragraph();
		p.add("The wet slip waiting list ");
		p.add(new Text("will be purged ").setBold());
		p.add("of names that do not meet the ");
		p.add(new Text("dues deadline date of February 28, "+year+". ").setBold());
		p.add("Re-instatement to the slip waiting list will occur at the time that full remittance "
				+ "for outstanding fees is received.  Re-instatement to the slip waiting list means that "
				+ "you forfeited your prior position and will be put on the bottom of the slip waiting "
				+ "list based on the date your full remittance is received in full.");
		listItem = new ListItem();
		listItem.add(p);
		list.add(listItem);
		

		list.add("Regular or Family membership status is necessary to receive a dry sail space assignment consideration");
		list.add("All co-owners of boats must hold a current Regular or Family membership in the club");
		////  BOLD TEXT HERE
		p = new Paragraph();
		p.add("Be sure to ");
		p.add(new Text("check one of the 5 choices in the second section of the Wet Slip Rental Agreement. ").setBold());
		p.add("This will indicate to the Harbor Master your plans for the "+year+" season.");
		listItem = new ListItem();
		listItem.add(p);
		list.add(listItem);

		list.add("If dues are not paid by the deadline, you understand that any boat(s)/trailer(s) may be considered "
				+ "abandoned and subject to removal by appropriate authorities. You will be billed all ECSC storage fees that "
				+ "apply from deadline date until property is removed from grounds");
		list.add("No trailer/boat/car should be parked in corral or surrounding areas until 2:00 p.m. on the Sunday following "
				+ "the Hornback Regatta in October");
		////  BOLD TEXT HERE
		p = new Paragraph();
		p.add(new Text("All watercraft such as Sailboats, Row Boats, Kayaks, Canoes, and Paddleboards kept on the property must be registered with membership and have "
				+ "current ECSC stickers by Mother's Day " + year).setBold());
		listItem = new ListItem();
		listItem.add(p);
		list.add(listItem);
		list.add("Only Regular/Family Memberships may keep watercraft on ECSC property");
		list.add("Make all checks payable to \"Eagle Creek Sailing Club\" or �ECSC�");
		
		document.add(list);
		//document.add(list);
		
	//	document.add(new Paragraph("Eagle Creek Sailing Club, Inc").setAlignment(Element.ALIGN_CENTER));
	}
}
