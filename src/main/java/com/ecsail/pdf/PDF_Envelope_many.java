package com.ecsail.pdf;

import com.ecsail.HalyardPaths;
import com.ecsail.dto.MembershipDTO;
import com.ecsail.dto.MembershipListDTO;
import com.ecsail.repository.implementations.MembershipRepositoryImpl;
import com.ecsail.repository.interfaces.MembershipRepository;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PDF_Envelope_many {
	public static Logger logger = LoggerFactory.getLogger(PDF_Envelope_many.class);
	MembershipRepository membershipRepository;
	Image ecscLogo = new Image(ImageDataFactory.create(PDF_DepositReport.toByteArray(Objects.requireNonNull(getClass().getResourceAsStream("/EagleCreekLogoForPDF.png")))));
	MembershipDTO membershipChair;
	private final int year;
	private int current_membership_id;
	private List<MembershipListDTO> membershipListDTOS;
	PdfFont font;
	private boolean isOneMembership;

	public PDF_Envelope_many(boolean isCatalog) throws IOException {
		this.year= LocalDate.now().getYear();
		this.membershipRepository = new MembershipRepositoryImpl();
		this.membershipListDTOS = membershipRepository.getAllRoster(Year.now().toString());
		HalyardPaths.checkPath(HalyardPaths.ECSC_HOME);

		this.membershipChair = membershipRepository.getCurrentMembershipChair();

		if(System.getProperty("os.name").equals("Windows 10"))
		FontProgramFactory.registerFont("c:/windows/fonts/times.ttf", "times");
		else if(System.getProperty("os.name").equals("Mac OS X"))
		FontProgramFactory.registerFont("/Library/Fonts/SF-Compact-Display-Regular.otf", "times");
		this.font = PdfFontFactory.createRegisteredFont("times");

		PdfWriter writer = new PdfWriter(HalyardPaths.ECSC_HOME + "_envelopes.pdf");
		// Initialize PDF document
		PdfDocument pdf = new PdfDocument(writer);
		Rectangle envelope;

		Rectangle envelopeSize = isCatalog
				? new Rectangle(648, 432)  // 9" x 6" (catalog envelope)
				: new Rectangle(684, 297);  // 9.5" x 4.125" (landscape)
		Paragraph spacer = new Paragraph().setMarginTop(isCatalog ? 150f : 70f);
		Document doc = new Document(pdf, new PageSize(envelopeSize));
		doc.setMargins(0, 10, 10, 0);
		membershipListDTOS.forEach(membership -> {
				doc.add(createReturnAddress());
				doc.add(spacer);
				doc.add(createAddress(membership));
				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
		});
		doc.close();
		logger.info("destination=" + HalyardPaths.ECSC_HOME + "_envelopes.pdf");
		File file = new File(HalyardPaths.ECSC_HOME + "_envelopes.pdf");
		Desktop desktop = Desktop.getDesktop(); // Gui_Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()
		// Open the document
		try {
			desktop.open(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Table createReturnAddress() {
		Table mainTable = new Table(2);
		mainTable.setWidth(290);
		ecscLogo.scale(0.25f, 0.25f);
		Cell cell = new Cell(3,1);
		cell.setWidth(40);
		cell.setBorder(Border.NO_BORDER);
		cell.setVerticalAlignment(VerticalAlignment.TOP);
		cell.setHorizontalAlignment(HorizontalAlignment.LEFT);
		cell.add(ecscLogo);
		mainTable.addCell(cell);
		mainTable.addCell(newAddressCell(10,10,"ECSC Membership"));
		mainTable.addCell(newAddressCell(10,10,membershipChair.getAddress()));
		mainTable.addCell(newAddressCell(10,10,membershipChair.getCityStateZip()));
		return mainTable;
	}

	private Cell newAddressCell(int fontSize, int fixedLeading, String paragraphContent) {
		Paragraph p;
		Cell cell = new Cell();
		p = new Paragraph(paragraphContent);
		p.setFont(font);
		p.setFontSize(fontSize);
		p.setFixedLeading(fixedLeading);
		cell.setBorder(Border.NO_BORDER);
		cell.add(p);
		return cell;
	}
	
	public Table createAddress(MembershipListDTO membershipListDTO) {
		Table mainTable = new Table(2);
		mainTable.setWidth(590);
		ecscLogo.scale(0.25f, 0.25f);
		
		Cell cell = new Cell(3,1);
		cell.setWidth(260);
		cell.setBorder(Border.NO_BORDER);
		mainTable.addCell(cell);
		mainTable.addCell(newAddressCell(16,14,membershipListDTO.getFirstName()
				+ " " + membershipListDTO.getLastName() + " #" + membershipListDTO.getMembershipId()));
		mainTable.addCell(newAddressCell(16,14,membershipListDTO.getAddress()));
		mainTable.addCell(newAddressCell(16,14,membershipListDTO.getCity() + ", "
				+ membershipListDTO.getState() + " " + membershipListDTO.getZip()));
		return mainTable;
	}
}
