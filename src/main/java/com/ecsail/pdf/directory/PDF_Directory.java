package com.ecsail.pdf.directory;


import com.ecsail.HalyardPaths;
import com.ecsail.dto.MembershipListDTO;
import com.ecsail.repository.implementations.MembershipRepositoryImpl;
import com.ecsail.repository.interfaces.MembershipRepository;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.AreaBreakType;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PDF_Directory {

	public static Logger logger = LoggerFactory.getLogger(PDF_Directory.class);
	private final ArrayList<MembershipListDTO> rosters;
	static PDF_Object_Settings set;
	TextArea textArea;
	String message;
	static Document doc;

	public PDF_Directory(String year, TextArea textArea) {
		PDF_Directory.set = new PDF_Object_Settings(year);
		this.textArea = textArea;
		this.message = "";

		MembershipRepository membershipRepository = new MembershipRepositoryImpl();
		this.rosters = (ArrayList<MembershipListDTO>) membershipRepository.getRoster(year, true);
		HalyardPaths.checkPath(HalyardPaths.DIRECTORIES);
		textArea.setText("Creating " + year + " directory");
		
		PdfWriter writer = null;
		try {
			writer = new PdfWriter(HalyardPaths.DIRECTORIES + "/" + Year.now() + "_ECSC_directory.pdf");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Initialize PDF document
		assert writer != null;
		PdfDocument pdf = new PdfDocument(writer);
		//PageSize A5v = new PageSize(PageSize.A5.getWidth(), PageSize.A5.getHeight());
		PDF_Directory.doc = new Document(pdf, new PageSize(set.getPageSize()));
		doc.setLeftMargin(0.5f);
		doc.setRightMargin(0.5f);
		doc.setTopMargin(1f);
		doc.setBottomMargin(0.5f);
		
		rosters.sort(Comparator.comparing(MembershipListDTO::getLastName));
		
		createDirectoryTask();
	}
	
	private void createDirectoryTask() {

	    Task<String> task = new Task<>() {
			@Override
			protected String call() {
				doc.add(new PDF_Cover(1, set));
				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
				textArea.appendText("Created Cover\n");

				doc.add(new PDF_CommodoreMessage(1, set));
				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
				textArea.appendText("Created Commodore Message\n");

				doc.add(new PDF_BoardOfDirectors(1, set));
				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
				textArea.appendText("Created Board of Directors\n");

				doc.add(new PDF_TableOfContents(1, set));
				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
				textArea.appendText("Created Table of Contents\n");

				doc.add(new PDF_ChapterPage(1, "Membership Information", set));
				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
				textArea.appendText("Created Membership Information Chapter Page\n");

				createMemberInfoPages(doc);  // creates info pages
				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
//				 this one below added in if book needs an extra page (should be even number of pages)
//				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

				new PDF_MembersByNumber(set, doc, rosters);


				doc.add(new PDF_SlipPageL(2, set));
				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
				textArea.appendText("Created D and A dock page\n");

				doc.add(new PDF_SlipPageR(2, set));
				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
				textArea.appendText("Created B and C dock page\n");

				doc.add(new PDF_SportsmanAward(2, set));
				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
				textArea.appendText("Created sportsman award page\n");

				doc.add(new PDF_CommodoreList(2, set));
				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
				textArea.appendText("Created directory page\n");
				doc.close();

				logger.info("destination=" + HalyardPaths.DIRECTORIES + "/" + Year.now() + "_ECSC_directory.pdf");
				File file = new File(HalyardPaths.DIRECTORIES + "/" + Year.now() + "_ECSC_directory.pdf");
				Desktop desktop = Desktop.getDesktop(); // Gui_Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()

				// Open the document
				try {
					desktop.open(file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "Directory Successfully Created!";
			}
		};
	    task.setOnScheduled(e -> System.out.println("scheduled"));
	    task.setOnSucceeded(e -> { 
	    	textArea.setText((String) e.getSource().getValue()); 
	    	logger.info("Finished making directory");});
	    task.setOnFailed(e -> System.out.println("This failed" + e.getSource().getMessage()));
	    exec.execute(task);

	}
	
	private final Executor exec = Executors.newCachedThreadPool(runnable -> {
	    Thread t = new Thread(runnable);
	    t.setDaemon(true);
	    return t ;
	});

	private void createMemberInfoPages(Document doc) {
			int count = 0;
			doc.add(new Paragraph("\n"));
			for(MembershipListDTO l: rosters) {
			textArea.appendText("Creating entry for " + l.getFirstName() + " " + l.getLastName() + "\n");
			doc.add(new PDF_MemberShipInformation(2,l,set));
			count++;
			if(count % 6 == 0) {
				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
				textArea.appendText("<----New Page---->");
				if(count < rosters.size()) // prevents adding a return for after this section
					doc.add(new Paragraph("\n")); // I think this is screwing up
			}
			//if(count == 60) break;  // this reduces pages made for testing
		}
	}

}
