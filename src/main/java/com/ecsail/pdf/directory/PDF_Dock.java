package com.ecsail.pdf.directory;

import com.ecsail.sql.select.SqlMembershipList;
import com.ecsail.sql.select.SqlSlip;
import com.ecsail.structures.MembershipListDTO;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PDF_Dock extends Table {
    ArrayList<Object_SlipInfo> slips;
    PDF_Object_Settings set;
    int lDocks;
    int rDocks;
    int rowSpot = 0;
    String dock;
    float dockWidth;
    boolean inColor;

    public PDF_Dock(int numColumns, String dock, int lDocks, int rDocks, PDF_Object_Settings set, boolean inColor) {
        super(numColumns);
        this.slips = SqlSlip.getSlipsForDock(dock);
        this.lDocks = lDocks;
        this.rDocks = rDocks;
        this.dock = dock;
        this.set = set;
        this.inColor = inColor;

        setWidth(PageSize.A5.getWidth() * 0.40f);  // makes table 45% of page width
        this.dockWidth = this.getWidth().getValue() * 0.45f;
        setHorizontalAlignment(HorizontalAlignment.CENTER);
        Collections.sort(slips, Comparator.comparing(Object_SlipInfo::getSlipNum).reversed());
        //setBackgroundColor(ColorConstants.ORANGE);

        if (dock.equals("A")) {
            addBlankSpace();
            buildTopRow();
            buildDocksClockWise();
            buildSingleADock(rowSpot);
            buildBlankRowDock();
            buildADockBottom();
            buildBlankRowDock();
            buildBottomRow();
        }
        if (dock.equals("B")) {
            addBlankSpace();
            buildTopRow();
            buildDocksCounterClockWise();
            buildClubDock("Racing");
            buildBlankRowDock();
            buildBottomRow();
        }
        if (dock.equals("C")) {
            addBlankSpace();
            buildTopRow();
            buildDocksClockWise();
            buildBlankRowDock();
            buildBottomRow();
        }
        if (dock.equals("D")) {
            addBlankSpace();
            buildTopRow();
            buildDocksClockWise();
            buildBottomRow();
            addBlankSpace();
        }
        if (dock.equals("F")) {
            buildTopRow();
            buildFDocks();
            buildBottomRow();
        }

    }

    public void buildDocksClockWise() {
        //System.out.println("lDocks=" +lDocks);
        for (int i = 0; i < (4 * lDocks); i += 4) {
            addCell(createLeftDock(i));
            addCell(createCenterDock());
            addCell(createRightDock(i + 1));
            buildBlankRowDock();
            rowSpot = i;
        }
    }

    public void buildDocksCounterClockWise() {
        //System.out.println("lDocks=" +lDocks);
        for (int i = 0; i < (4 * lDocks); i += 4) {
            addCell(createLeftDock(i + 1));
            addCell(createCenterDock());
            addCell(createRightDock(i));
            buildBlankRowDock();
            rowSpot = i;
        }
    }

    public void buildSingleADock(int i) {
        addCell(createLeftADock(i));
        addCell(createCenterDock());
        addCell(createRightADock(i + 1));
    }

    // this is for creating the right dock were the slips change on A dock to one side
    private Cell createRightADock(int element) {
        Cell cell;
        Paragraph p;

        cell = new Cell();
        cell.setWidth(dockWidth);
        cell.setBorder(Border.NO_BORDER);
        cell.setBorderRight(new SolidBorder(1f));
        cell.setBorderBottom(new SolidBorder(1f));
        cell.setBorderTop(new SolidBorder(1f));
        cell.setBackgroundColor(set.getDockColor());
        p = getName(element + 4, 0, true);
        p.setFontSize(set.getSlipFontSize())
                .setTextAlignment(TextAlignment.LEFT)
                .setFixedLeading(set.getFixedLeadingNarrow() - 3);
        cell.add(p);

        p = getName(element + 3, 2, true);
        p.setFontSize(set.getSlipFontSize())
                .setTextAlignment(TextAlignment.LEFT)
                .setFixedLeading(set.getFixedLeadingNarrow() - 3);
        cell.add(p);
        return cell;
    }

    // this is for creating the left dock were the slips change on A dock to one side
    public Cell createLeftADock(int element) {
        Cell cell;
        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        cell.setBorderLeft(new SolidBorder(1f));
        cell.setBorderBottom(new SolidBorder(1f));
        cell.setBorderTop(new SolidBorder(1f));
        cell.setBackgroundColor(set.getDockColor());
        cell.setWidth(dockWidth);
        Paragraph p;
        p = getName(element + 4, 0, false);
        p.setFontSize(set.getSlipFontSize())
                .setTextAlignment(TextAlignment.RIGHT)
                .setBackgroundColor(getStrokeColor())
                .setFixedLeading(set.getFixedLeadingNarrow() - 3);

        cell.add(p);

        p = new Paragraph("spacer");  // just need some text for spacing
        p.setFontSize(set.getNormalFontSize() - 4)
                .setTextAlignment(TextAlignment.RIGHT)
                .setFixedLeading(set.getFixedLeadingNarrow() - 3);
        p.setFontColor(set.getDockColor());
        cell.add(p);

        return cell;
    }

    public void buildADockBottom() {
        //if(lDocks < rDocks) {
        addCell(createRightDockOnly(rowSpot + 7));
        buildBlankRowDock();
        addCell(createRightDockOnly(rowSpot + 9));
        buildBlankRowDock();
        addCell(createRightDockOnly(rowSpot + 11));
        buildBlankRowDock();
        addCell(createRightDockOnly(rowSpot + 13));
        //}
    }

    public void buildFDocks() {
        addCell(createRightDockOnly(rowSpot + 0));
        buildBlankRowDock();
        addCell(createRightDockOnly(rowSpot + 2));
        buildBlankRowDock();
        addCell(createRightDockOnly(rowSpot + 4));
        buildBlankRowDock();
        addCell(createRightDockOnly(rowSpot + 6));
        buildBlankRowDock();
        addCell(createRightDockOnly(rowSpot + 8));
        buildBlankRowDock();
    }


    public Cell createLeftDock(int element) {
        Cell cell;
        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        cell.setBorderLeft(new SolidBorder(1f));
        cell.setBorderBottom(new SolidBorder(1f));
        cell.setBorderTop(new SolidBorder(1f));
        cell.setBackgroundColor(set.getDockColor());
        cell.setWidth(dockWidth);
        Paragraph p;
        p = getName(element, 0, false);
        p.setFontSize(set.getSlipFontSize())
                .setTextAlignment(TextAlignment.RIGHT)
                .setBackgroundColor(getStrokeColor())
                .setFixedLeading(set.getFixedLeadingNarrow() - 3);

        cell.add(p);

        p = getName(element, 2, false);
        p.setFontSize(set.getSlipFontSize())
                .setTextAlignment(TextAlignment.RIGHT)
                .setFixedLeading(set.getFixedLeadingNarrow() - 3);
        cell.add(p);

        return cell;
    }

    private Paragraph getName(int element, int offset, boolean setLeft) {
        String name = slips.get(element + offset).getlName() + ", " + returnInitial(slips.get(element + offset).getfName());

        boolean isSublease = false;
        /// find out if this is a sublease and put their name in if they are.
        if (slips.get(element + offset).getSubleaseMsID() != 0) {
            MembershipListDTO subleaser;
            subleaser = SqlMembershipList.getMembershipList(slips.get(element + offset).getSubleaseMsID(), set.getSelectedYear());
            name = subleaser.getlName() + " " + returnInitial(subleaser.getfName());
            isSublease = true;
        }
        Paragraph p;

        // there is no subleaser
        if (!setLeft) { //dock is on right
            if (!inColor && isSublease) name = "*** " + name;
            p = new Paragraph(name + " " + slips.get(element + offset).getSlipNum());
        } else {            //dock is on left
            if (!inColor && isSublease) name = name + " ***";
            p = new Paragraph(slips.get(element + offset).getSlipNum() + "  " + name);
        }
        if (isSublease && inColor) p.setFontColor(ColorConstants.BLUE);
        return p;
    }

    private String returnInitial(String originalWord) {
        String initial = originalWord.charAt(0) + ".";
        return initial;
    }

    private Cell createRightDock(int element) {
        Cell cell;
        Paragraph p;
        cell = new Cell();
        cell.setWidth(dockWidth);
        cell.setBorder(Border.NO_BORDER);
        cell.setBorderRight(new SolidBorder(1f));
        cell.setBorderBottom(new SolidBorder(1f));
        cell.setBorderTop(new SolidBorder(1f));
        cell.setBackgroundColor(set.getDockColor());
        p = getName(element, 0, true);
        p.setFontSize(set.getSlipFontSize())
                .setTextAlignment(TextAlignment.LEFT)
                .setFixedLeading(set.getFixedLeadingNarrow() - 3);
        cell.add(p);

        p = getName(element, 2, true);
        p.setFontSize(set.getSlipFontSize())
                .setTextAlignment(TextAlignment.LEFT)
                .setFixedLeading(set.getFixedLeadingNarrow() - 3);
        cell.add(p);
        return cell;
    }

    private Cell createRightDockOnly(int element) {

        Cell cell;
        Paragraph p;
        p = new Paragraph("........");
        p.setTextAlignment(TextAlignment.RIGHT);
        p.setFixedLeading(set.getFixedLeadingNarrow() - 3);
        p.setFontColor(ColorConstants.WHITE);
        // make blank left dock
        cell = new Cell();
        cell.setWidth(dockWidth);
        //cell.setBackgroundColor(ColorConstants.BLUE);
        cell.setBorder(Border.NO_BORDER);
        cell.setBorderRight(new SolidBorder(1f));
        cell.add(p);
        addCell(cell);

        // make stem dock
        addCell(createCenterDock());

        cell = new Cell();
        cell.setWidth(dockWidth);
        cell.setBorder(Border.NO_BORDER);
        cell.setBorderRight(new SolidBorder(1f));
        cell.setBorderBottom(new SolidBorder(1f));
        cell.setBorderTop(new SolidBorder(1f));
        cell.setBackgroundColor(set.getDockColor());
        //cell.setBackgroundColor(ColorConstants.BLUE);
        p = getName(element, 0, true);
        p.setFontSize(set.getSlipFontSize())
                .setTextAlignment(TextAlignment.LEFT)
                .setFixedLeading(set.getFixedLeadingNarrow() - 3);
        cell.add(p);
        p = getName(element, 1, true);
        p.setFontSize(set.getSlipFontSize())
                .setTextAlignment(TextAlignment.LEFT)
                .setFixedLeading(set.getFixedLeadingNarrow() - 3);
        cell.add(p);

        return cell;
    }

    private void buildClubDock(String dockName) {

        Cell cell;
        Paragraph p;

        // make blank left dock
        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        cell.setBorderRight(new SolidBorder(1f));
        addCell(cell);

        // make stem dock
        addCell(createCenterDock());

        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        cell.setBorderRight(new SolidBorder(1f));
        cell.setBorderBottom(new SolidBorder(1f));
        cell.setBorderTop(new SolidBorder(1f));
        cell.setBackgroundColor(set.getDockColor());
        p = new Paragraph(dockName);
        p.setFontSize(set.getSlipFontSize())
                .setTextAlignment(TextAlignment.LEFT)
                .setFixedLeading(set.getFixedLeadingNarrow() - 3);
        cell.add(p);

        p = new Paragraph(dockName);
        p.setFontSize(set.getSlipFontSize())
                .setTextAlignment(TextAlignment.LEFT)
                .setFixedLeading(set.getFixedLeadingNarrow() - 3);
        cell.add(p);
        addCell(cell);
    }

    public void buildBlankRowDock() {
        Cell cell;

        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        addCell(cell);

        cell = new Cell();
        //p = new Paragraph(".");
        //cell.add(p);
        cell.setWidth(15f);
        cell.setBorder(Border.NO_BORDER);
        cell.setBorderLeft(new SolidBorder(1f));
        cell.setBorderRight(new SolidBorder(1f));
        cell.setBackgroundColor(set.getDockColor());
        cell.setHeight(10f);
        addCell(cell);

        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        addCell(cell);
    }

    public void addBlankSpace() {
        Cell cell;

        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        addCell(cell);

        cell = new Cell();
        //p = new Paragraph(".");
        //cell.add(p);
        cell.setWidth(15f);
        cell.setBorder(Border.NO_BORDER);
        cell.setHeight(10f);
        addCell(cell);

        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        addCell(cell);
    }

    public void buildBottomRow() {
        Cell cell;

        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        addCell(cell);

        cell = new Cell();
        ;
        Paragraph p = new Paragraph(dock);
        p.setFontSize(set.getSlipFontSize())
                .setTextAlignment(TextAlignment.CENTER);
        cell.setWidth(15f);
        cell.setBorder(Border.NO_BORDER);
        cell.setBorderLeft(new SolidBorder(1f));
        cell.setBorderRight(new SolidBorder(1f));
        cell.setBorderBottom(new SolidBorder(1f));
        cell.setBackgroundColor(set.getDockColor());
        cell.setHeight(10f);
        cell.add(p);
        addCell(cell);

        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        addCell(cell);
    }

    public void buildTopRow() {
        Cell cell;

        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        addCell(cell);

        cell = new Cell();
        //p = new Paragraph(".");
        //cell.add(p);
        cell.setWidth(10f);
        cell.setBorder(Border.NO_BORDER);
        cell.setBorderLeft(new SolidBorder(1f));
        cell.setBorderRight(new SolidBorder(1f));
        cell.setBorderTop(new SolidBorder(1f));
        cell.setBackgroundColor(set.getDockColor());
        cell.setHeight(3f);
        addCell(cell);

        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        addCell(cell);
    }

    public Cell createCenterDock() {
        Cell cell;
        cell = new Cell();
        cell.setBackgroundColor(set.getDockColor());
        cell.setBorder(Border.NO_BORDER);
        cell.setWidth(10f);
        return cell;
    }

}
