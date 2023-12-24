package com.ecsail.pdf;


import com.ecsail.HalyardPaths;
import com.ecsail.dto.StatsDTO;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;


import java.awt.*;
import java.io.*;
import java.time.Year;
import java.util.ArrayList;

//        The default value of a page in iText, if you create a Document object without any parameters,
//        is A4, which is the most common paper size in Europe, Asia, and Latin America. It's
//        specified by the International Standards Organization (ISO) in ISO-216. An A4 document
//        measures 210 mm x 297 mm, or 8.3 in.


public class PDF_MembershipReport {
    public ArrayList<StatsDTO> stats;
    int currentYear;
    int defaultStartYear;
    int defaultNumbOfYears = 20;
    int tableHeight;
    int scaleWidth;
    float scaleBarWidth = 0.25f;
    DeviceCmyk barColor = new DeviceCmyk(.12f, .05f, 0, 0.02f);
    int barCellWidth = 7;
    int spaceCellWidth = 3;
    int cellHeight = 1;

    public PDF_MembershipReport() {
        this.currentYear = Year.now().getValue();
        this.defaultStartYear = currentYear - 20;
//        this.stats = SqlStats.getStatistics(defaultStartYear, defaultStartYear + defaultNumbOfYears);
        this.tableHeight = getTableHeight(getLargestStat());
        this.scaleWidth = tableHeight / 6;

        // Initialize PDF writer
        PdfWriter writer = null;
        // Check to make sure directory exists and if not create it
        HalyardPaths.checkPath(HalyardPaths.BOATLISTS + "/" + String.valueOf(Year.now().getValue()));
        String dest = HalyardPaths.ECSC_HOME + "/MembershipReport_" + HalyardPaths.getDate() + ".pdf";

        try {
            writer = new PdfWriter(dest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf);
        document.add(testTable(document));
//
//        for(MembershipListDTO m: membershipLists) {
//        membershipTable(m, document);
//        }

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

    public Table testTable(Document doc) {
        Table testTable = new Table((stats.size() * 2) + 1);
        Cell cell;
        int count = 0;
        // stacks rows
        for(int i = 0; i < tableHeight; i++ ) {
            // creates padder cell to left
            cell = getPadderCell(i);
            testTable.addCell(cell);
            // creates row
            for (StatsDTO s : stats) {
                // bar cell
                if(i < tableHeight - s.getNewMemberships())
                cell = getBlankBarCell(i);
                else
                cell = getBarCell();
                testTable.addCell(cell);
                // padder cell to right
                cell = getPadderCell(i);
                testTable.addCell(cell);

                count += 10;
            }
        }


        return testTable;
    }

    private Cell getBlankBarCell(int currentHeight) {
        Cell cell;
        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
//            cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
        setScaleLines(currentHeight, cell);
        cell.setWidth(barCellWidth);
        cell.setHeight(cellHeight);
        return cell;
    }

//    @NotNull
    private Cell getBarCell() {
        Cell cell;
        cell = new Cell();
        cell.setPadding(0);
        cell.setBorder(Border.NO_BORDER);
        cell.setBackgroundColor(barColor);
        // corrects for scale guides (somewhat)
        cell.setBorderTop(new SolidBorder(barColor, scaleBarWidth));
        cell.setWidth(barCellWidth);
        cell.setHeight(cellHeight);
        return cell;
    }

//    @NotNull
    private Cell getPadderCell(int currentHeight) {
        Cell cell;
        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
//            cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
//            cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
        setScaleLines(currentHeight, cell);
        cell.setWidth(spaceCellWidth);
        cell.setHeight(cellHeight);
//            cell.add(new Paragraph(" ")).setFontSize(10);
        return cell;
    }

    private void setScaleLines(int currentHeight, Cell cell) {
        if (currentHeight == 0)
            cell.setBorderTop(new SolidBorder(barColor, scaleBarWidth));
        else if(currentHeight % scaleWidth == 0) {
            cell.setBorderTop(new SolidBorder(barColor, scaleBarWidth));
        }
    }

    private int getLargestStat() {
        int largestSize = 0;
        for(StatsDTO stat: stats) {
            if(stat.getNewMemberships() > largestSize)
                largestSize = stat.getNewMemberships();
        }
        return largestSize;
    }

    private int getTableHeight(int largestColumn) {
        return getScaleSize(largestColumn);
    }

    static int getScaleSize(int number) {
        int scaleSize = 0;
        if(number < 50) scaleSize = round (number, 5);
        else if(number < 100) scaleSize = round(number,10);
        else if(number < 500) scaleSize = round(number,50);
        else if(number < 1000) scaleSize = round(number,100);
        return scaleSize;
    }

    static int round(int n, int roundBy)
    {
        // Smaller multiple
        int a = (n / roundBy) * roundBy;
        // Larger multiple
        int b = a + roundBy;
        return b;
    }


//    public void membershipTable(MembershipListDTO ml, Document document) {
//        List<BoatDTO> boats = SqlBoat.getBoats(ml.getMsid());
//        if(boats.size() > 0) {
//            System.out.println("Creating Entry for mebership " + ml.getMsid() + " " + ml.getLname());
//            removeKayaksAndCanoes(boats);
//            System.out.println("-------------------");
////            System.out.println("creating " + ml.getLname());
//            Table detailTable = new Table(6);
//            // mainTable.setKeepTogether(true);
//            Cell cell;
//
//            cell = new Cell();
//            cell.setBorder(Border.NO_BORDER);
//            cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
//            cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
//            cell.setWidth(50);
//            cell.add(new Paragraph(SqlMembership_Id.getMembershipId(String.valueOf(Year.now().getValue()), ml.getMsid()) + "" + "")).setFontSize(10);
//            detailTable.addCell(cell);
//
//            cell = new Cell();
//            cell.setBorder(Border.NO_BORDER);
//            cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
//            cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
//            cell.setWidth(100);
//            cell.add(new Paragraph(ml.getLname() + "")).setFontSize(10);
//            detailTable.addCell(cell);
//
//            cell = new Cell();
//            cell.setBorder(Border.NO_BORDER);
//            cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
//            cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
//            cell.setWidth(100);
//            cell.add(new Paragraph(ml.getFname() + "")).setFontSize(10);
//            detailTable.addCell(cell);
//
//            cell = new Cell();
//            cell.setBorder(Border.NO_BORDER);
//            cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
//            cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
//            cell.setWidth(70);
//            cell.add(new Paragraph(""));
//            detailTable.addCell(cell);
//
//            cell = new Cell();
//            cell.setBorder(Border.NO_BORDER);
//            cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
//            cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
//            cell.setWidth(100);
//            cell.add(new Paragraph(""));
//            detailTable.addCell(cell);
//
//            cell = new Cell();
//            cell.setBorder(Border.NO_BORDER);
//            cell.setBackgroundColor(new DeviceCmyk(.12f, .05f, 0, 0.02f));
//            cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
//            cell.setWidth(60);
//            cell.add(new Paragraph(""));
//            detailTable.addCell(cell);
//
//            for(BoatDTO b: boats) {
//                cell = new Cell();
//                cell.setBorder(Border.NO_BORDER);
//                cell.setWidth(50);
//                cell.add(new Paragraph("")).setFontSize(10);
//                detailTable.addCell(cell);
//
//                cell = new Cell();
//                cell.setBorder(Border.NO_BORDER);
//                cell.add(new Paragraph(b.getManufacturer() + ""));
//                cell.setWidth(100);
//                detailTable.addCell(cell);
//
//                cell = new Cell();
//                cell.setBorder(Border.NO_BORDER);
//                cell.add(new Paragraph(b.getModel() + ""));
//                cell.setWidth(100);
//                detailTable.addCell(cell);
//
//                cell = new Cell();
//                cell.setBorder(Border.NO_BORDER);
//                cell.add(new Paragraph(b.getRegistration_num() + ""));
//                cell.setWidth(70);
//                detailTable.addCell(cell);
//
//                cell = new Cell();
//                cell.setBorder(Border.NO_BORDER);
//                cell.add(new Paragraph(b.getBoat_name() + ""));
//                cell.setWidth(100);
//                detailTable.addCell(cell);
//
//                cell = new Cell();
//                cell.setBorder(Border.NO_BORDER);
//                cell.add(new Paragraph(b.getManufacture_year() + ""));
//                cell.setWidth(60);
//                detailTable.addCell(cell);
//            }
//            document.add(detailTable);
//        }
//    }
//
//    public Table titlePdfTable() {
//        Image ecscLogo = new Image(ImageDataFactory.create(toByteArray(getClass().getResourceAsStream("/EagleCreekLogoForPDF.png"))));
//        Table mainTable = new Table(3);
//        Cell cell;
//        cell = new Cell();
//        cell.setBorder(Border.NO_BORDER);
//        cell.setWidth(200);
//        cell.add(new Paragraph("Membership List with boats"));
//        mainTable.addCell(cell);
//
//        cell = new Cell();
//        cell.setBorder(Border.NO_BORDER);
//        cell.setWidth(200);
//        mainTable.addCell(cell);
//
//        cell = new Cell();
//        cell.setBorder(Border.NO_BORDER);
//        cell.setWidth(90);
//        ecscLogo.setMarginLeft(30);
//        ecscLogo.scale(0.4f, 0.4f);
//        cell.add(ecscLogo);
//        mainTable.addCell(cell);
//
//        cell = new Cell();
//        cell.setBorder(Border.NO_BORDER);
//        cell.add(new Paragraph("As of " + HalyardPaths.getDate() + "")).setFontSize(10);
//        mainTable.addCell(cell);
//
//        cell = new Cell();
//        cell.setBorder(Border.NO_BORDER);
//        mainTable.addCell(cell);
//
//        cell = new Cell();
//        cell.setBorder(Border.NO_BORDER);
//        mainTable.addCell(cell);
//        return mainTable;
//    }

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
