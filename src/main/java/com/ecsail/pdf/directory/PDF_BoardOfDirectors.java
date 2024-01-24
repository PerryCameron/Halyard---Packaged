package com.ecsail.pdf.directory;

import com.ecsail.enums.Officer;
import com.ecsail.repository.implementations.PDFRepositoryImpl;
import com.ecsail.repository.interfaces.PDFRepository;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;

import java.util.ArrayList;
import java.util.Comparator;


public class PDF_BoardOfDirectors extends Table {

    PDF_Object_Settings set;
    ArrayList<PDF_Object_Officer> pdfObjectOfficers;

    PDFRepository pdfRepository = new PDFRepositoryImpl();

    public PDF_BoardOfDirectors(int numColumns, PDF_Object_Settings set) {
        super(numColumns);
        this.set = set;
        pdfObjectOfficers = (ArrayList<PDF_Object_Officer>) pdfRepository.getOfficersByYear(set.getSelectedYear());
        pdfObjectOfficers.sort(Comparator.comparing(PDF_Object_Officer::getLastName));
        setWidth(set.getPageSize().getWidth() * 0.9f);  // makes table 90% of page width
        setHorizontalAlignment(HorizontalAlignment.CENTER);
        Cell cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        //cell.add(new Paragraph("\n"));
        cell.add(createOfficersTable());
        //cell.add(new Paragraph("\n"));
        cell.add(createChairmenTable());
        //cell.add(new Paragraph("\n"));
        cell.add(createBODTable());
        addCell(cell);

        cell = new Cell();
        cell.add(new Paragraph("\n\n\n"));
        cell.setBorder(Border.NO_BORDER);
        addCell(cell);

        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        Paragraph p = new Paragraph("©Eagle Creek Sailing club 1969-" + set.getSelectedYear() + " - This directory may not be used for commercial purposes");
        p.setTextAlignment(TextAlignment.CENTER);
        cell.add(p);
        addCell(cell);
    }

    public Table createOfficersTable() {
        Table officerTable = new Table(2);
        officerTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
        officerTable.setWidth(this.getWidth().getValue() * 0.6f);
        //mainTable.setWidth(590);
        Cell cell;
        Paragraph p;
        cell = new Cell(1, 2);
        cell.setBorder(Border.NO_BORDER);
        p = new Paragraph("\n" + set.getSelectedYear() + " Officers");
        p.setFontSize(set.getNormalFontSize() + 4);
        p.setFont(set.getColumnHead());
        p.setTextAlignment(TextAlignment.CENTER);
        p.setFontColor(set.getMainColor());
        cell.add(p);
        officerTable.addCell(cell);

        addOfficerToTable(officerTable, "CO");
        addOfficerToTable(officerTable, "VC");
        addOfficerToTable(officerTable, "PC");
        addOfficerToTable(officerTable, "FM");
        addOfficerToTable(officerTable, "TR");
        addOfficerToTable(officerTable, "SE");
        return officerTable;
    }

    public Table createChairmenTable() {
        Table chairTable = new Table(2);
        chairTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
        chairTable.setWidth(this.getWidth().getValue() * 0.7f);
        Cell cell;
        Paragraph p;
        cell = new Cell(1, 2);
        cell.setBorder(Border.NO_BORDER);
        p = new Paragraph("Committee Chairs");
        p.setFontSize(set.getNormalFontSize() + 4);
        p.setFont(set.getColumnHead());
        p.setFontColor(set.getMainColor());
        p.setTextAlignment(TextAlignment.CENTER);
        cell.add(p);
        chairTable.addCell(cell);

        addOfficerToTable(chairTable, "HM");
        addOfficerToTable(chairTable, "AH");
        addOfficerToTable(chairTable, "MS");
        addOfficerToTable(chairTable, "AM");
        addOfficerToTable(chairTable, "PU");
        addOfficerToTable(chairTable, "RA");

        addOfficerToTable(chairTable, "AR");
//		addOfficerToTable(chairTable, "AR");
        addOfficerToTable(chairTable, "SM");
        addOfficerToTable(chairTable, "JP");
        addOfficerToTable(chairTable, "AJ");
        addOfficerToTable(chairTable, "SO");
        addOfficerToTable(chairTable, "SA");

        return chairTable;
    }

    private Table createBODTable() {

        Table bodTable = new Table(3);
        bodTable.setWidth(this.getWidth().getValue());
        bodTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
        Cell cell;
        Paragraph p;
        cell = new Cell(1, 3);
        cell.setBorder(Border.NO_BORDER);
        p = new Paragraph("Current Board Members");
        p.setFontSize(set.getNormalFontSize() + 4);
        p.setFont(set.getColumnHead());
        p.setFontColor(set.getMainColor());
        p.setTextAlignment(TextAlignment.CENTER);
        cell.add(p);
        bodTable.addCell(cell);

        createBoardMemberTables(bodTable); // will create 3 more cells and put a table in each

        return bodTable;
    }

    private void createBoardMemberTables(Table bodTable) {
        ArrayList<String> currentYearList = new ArrayList<>();
        ArrayList<String> nextYearList = new ArrayList<>();
        ArrayList<String> afterNextYearList = new ArrayList<>();
        int thisYear = Integer.parseInt(set.getSelectedYear());
        int nextYear = thisYear + 1;
        int afterNextYear = thisYear + 2;

        for (PDF_Object_Officer o : pdfObjectOfficers) {
            if (Integer.parseInt(o.getBoardTermEndYear()) == thisYear)
                currentYearList.add(o.firstName + " " + o.lastName);
            if (Integer.parseInt(o.getBoardTermEndYear()) == nextYear)
                nextYearList.add(o.firstName + " " + o.lastName);
            if (Integer.parseInt(o.getBoardTermEndYear()) == afterNextYear)
                afterNextYearList.add(o.firstName + " " + o.lastName);
        }

        Cell cell;

        cell = new Cell();  // make a big cell in previous table to put 3 tables in
        cell.add(createBoardMemberColumn(currentYearList, String.valueOf(thisYear)));
        cell.setBorder(Border.NO_BORDER);
        bodTable.addCell(cell);

        cell = new Cell();
        cell.add(createBoardMemberColumn(nextYearList, String.valueOf(nextYear)));
        cell.setBorder(Border.NO_BORDER);
        bodTable.addCell(cell);

        cell = new Cell();
        cell.add(createBoardMemberColumn(afterNextYearList, String.valueOf(afterNextYear)));
        cell.setBorder(Border.NO_BORDER);
        bodTable.addCell(cell);

    }

    private Table createBoardMemberColumn(ArrayList<String> yearList, String year) {
        Table columnTable = new Table(1);
        Cell cell;
        Paragraph p;
        cell = new Cell();
        p = new Paragraph(year);
        p.setFontSize(12);
        p.setFont(set.getColumnHead());
        p.setFixedLeading(set.getFixedLeading() - 15);  // sets spacing between lines of text
        p.setTextAlignment(TextAlignment.LEFT);
        cell.setBorder(Border.NO_BORDER).add(p);
        columnTable.addCell(cell);

        for (String name : yearList) {
            cell = new Cell();
            p = new Paragraph(name);
            p.setFontSize(set.getNormalFontSize());
            p.setFixedLeading(set.getFixedLeading() - 15);  // sets spacing between lines of text
            cell.setBorder(Border.NO_BORDER).add(p);
            columnTable.addCell(cell);
        }

        return columnTable;
    }

    private void addOfficerToTable(Table mainTable, String type) {
            Cell cell;
            Paragraph p;
            cell = new Cell();
            p = new Paragraph(Officer.getByCode(type) + ":");
            p.setFontSize(set.getNormalFontSize());
            p.setFont(set.getColumnHead());
            p.setFixedLeading(set.getFixedLeading() - 15);  // sets spacing between lines of text
            cell.setBorder(Border.NO_BORDER).add(p).setHorizontalAlignment(HorizontalAlignment.CENTER);
            mainTable.addCell(cell);

            cell = new Cell();
            PDF_Object_Officer o = getOfficer(type);
            if (o == null) p = new Paragraph("none");
            else p = new Paragraph(o.getFirstName() + " " + o.getLastName());
            p.setFontSize(set.getNormalFontSize());
            p.setFixedLeading(set.getFixedLeading() - 15);  // sets spacing between lines of text
            cell.setBorder(Border.NO_BORDER).add(p).setHorizontalAlignment(HorizontalAlignment.CENTER);
            mainTable.addCell(cell);
    }

    public PDF_Object_Officer getOfficer(String type) {
        return pdfObjectOfficers.stream()
                .filter(o -> !o.getOfficerPlaced() && o.getOfficerType().equals(type))
                .findFirst()
                .map(o -> {
                    o.setOfficerPlaced(true);
                    return o;
                })
                .orElse(null);
    }


}
