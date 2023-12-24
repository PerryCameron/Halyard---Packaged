package com.ecsail.pdf;


import com.ecsail.BaseApplication;
import com.ecsail.HalyardPaths;
import com.ecsail.repository.implementations.BoatRepositoryImpl;
import com.ecsail.repository.implementations.MembershipIdRepositoryImpl;
import com.ecsail.repository.implementations.MembershipRepositoryImpl;
import com.ecsail.repository.interfaces.BoatRepository;
import com.ecsail.repository.interfaces.MembershipIdRepository;
import com.ecsail.repository.interfaces.MembershipRepository;
import com.ecsail.dto.BoatDTO;
import com.ecsail.dto.MembershipListDTO;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.*;
import java.io.*;
import java.time.Year;
import java.util.List;

public class PDF_BoatReport {
    private ObservableList<MembershipListDTO> membershipLists;
    protected BoatRepository boatRepository = new BoatRepositoryImpl();
    protected MembershipRepository membershipRepository = new MembershipRepositoryImpl();
    protected MembershipIdRepository membershipIdRepository = new MembershipIdRepositoryImpl();

    public PDF_BoatReport() {
        this.membershipLists =
                FXCollections.observableArrayList(membershipRepository.getRoster(String.valueOf(Year.now().getValue()), true));

        // Initialize PDF writer
        PdfWriter writer = null;
        // Check to make sure directory exists and if not create it
        HalyardPaths.checkPath(HalyardPaths.BOATLISTS + "/" + String.valueOf(Year.now().getValue()));
        String dest = HalyardPaths.BOATLISTS+ "/" + Year.now().getValue() + "/BoatList_" + HalyardPaths.getDate() + ".pdf";

        try {
            writer = new PdfWriter(dest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf);
        document.add(titlePdfTable());

        for(MembershipListDTO m: membershipLists) {
        membershipTable(m, document);
        }

        document.close();
        File file = new File(dest);
        Desktop desktop = Desktop.getDesktop(); // Gui_Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()

        // Open the document
        try {
            desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void membershipTable(MembershipListDTO ml, Document document) {
        List<BoatDTO> boats = boatRepository.getOnlySailboatsByMsId(ml.getMsId());
        if(boats.size() > 0) {
            Table detailTable = new Table(6);
            // mainTable.setKeepTogether(true);
            Cell cell;

            cell = new Cell();
            cell.setBorder(Border.NO_BORDER);
            cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
            cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
            cell.setWidth(50);
            cell.add(new Paragraph(membershipIdRepository.getMembershipIdByYearAndMsId(String.valueOf(Year.now().getValue()), ml.getMsId()) + "" + "")).setFontSize(10);
            detailTable.addCell(cell);


            cell = new Cell();
            cell.setBorder(Border.NO_BORDER);
            cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
            cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
            cell.setWidth(100);
            cell.add(new Paragraph(ml.getLastName() + "")).setFontSize(10);
            detailTable.addCell(cell);

            cell = new Cell();
            cell.setBorder(Border.NO_BORDER);
            cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
            cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
            cell.setWidth(100);
            cell.add(new Paragraph(ml.getFirstName() + "")).setFontSize(10);
            detailTable.addCell(cell);

            cell = new Cell();
            cell.setBorder(Border.NO_BORDER);
            cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
            cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
            cell.setWidth(70);
            cell.add(new Paragraph(""));
            detailTable.addCell(cell);

            cell = new Cell();
            cell.setBorder(Border.NO_BORDER);
            cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
            cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
            cell.setWidth(100);
            cell.add(new Paragraph(""));
            detailTable.addCell(cell);

            cell = new Cell();
            cell.setBorder(Border.NO_BORDER);
            cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
            cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
            cell.setWidth(60);
            cell.add(new Paragraph(""));
            detailTable.addCell(cell);

            for(BoatDTO b: boats) {
                cell = new Cell();
                cell.setBorder(Border.NO_BORDER);
                cell.setWidth(50);
                cell.add(new Paragraph("")).setFontSize(10);
                detailTable.addCell(cell);

                cell = new Cell();
                cell.setBorder(Border.NO_BORDER);
                cell.add(new Paragraph(b.getManufacturer() + ""));
                cell.setWidth(100);
                detailTable.addCell(cell);

                cell = new Cell();
                cell.setBorder(Border.NO_BORDER);
                cell.add(new Paragraph(b.getModel() + ""));
                cell.setWidth(100);
                detailTable.addCell(cell);

                cell = new Cell();
                cell.setBorder(Border.NO_BORDER);
                cell.add(new Paragraph(b.getRegistrationNum() + ""));
                cell.setWidth(70);
                detailTable.addCell(cell);

                cell = new Cell();
                cell.setBorder(Border.NO_BORDER);
                cell.add(new Paragraph(b.getBoatName() + ""));
                cell.setWidth(100);
                detailTable.addCell(cell);

                cell = new Cell();
                cell.setBorder(Border.NO_BORDER);
                cell.add(new Paragraph(b.getManufactureYear() + ""));
                cell.setWidth(60);
                detailTable.addCell(cell);
            }
            document.add(detailTable);
        }
    }

    public Table titlePdfTable() {
        com.itextpdf.layout.element.Image ecscLogo = new Image(ImageDataFactory.create(toByteArray(getClass().getResourceAsStream("/EagleCreekLogoForPDF.png"))));
        Table mainTable = new Table(3);
        Cell cell;
        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        cell.setWidth(200);
        cell.add(new Paragraph("Membership List with boats"));
        mainTable.addCell(cell);

        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        cell.setWidth(200);
        mainTable.addCell(cell);

        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        cell.setWidth(90);
        ecscLogo.setMarginLeft(30);
        ecscLogo.scale(0.4f, 0.4f);
        cell.add(ecscLogo);
        mainTable.addCell(cell);

        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        cell.add(new Paragraph("As of " + HalyardPaths.getDate() + "")).setFontSize(10);
        mainTable.addCell(cell);

        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        mainTable.addCell(cell);

        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        mainTable.addCell(cell);
        return mainTable;
    }

    public static byte[] toByteArray(InputStream in)  { // for taking inputStream and returning byte
        // array
        // InputStream is = new BufferedInputStream(System.in);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        // read bytes from the input stream and store them in buffer
        try {
            while ((len = in.read(buffer)) != -1) {
                // write bytes from the buffer into output stream
                os.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os.toByteArray();
    }

}
