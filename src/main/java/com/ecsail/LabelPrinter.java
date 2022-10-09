package com.ecsail;

import javax.print.PrintService;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.print.*;

public class LabelPrinter {
    static String[] labelLines;
    public static void printMembershipLabel(String[] lines) {
        labelLines = lines;

        PrintService[] ps = PrinterJob.lookupPrintServices();
        if (ps.length == 0) {
            throw new IllegalStateException("No Printer found");
        }

        PrintService myService = null;
        for (PrintService printService : ps) {
            if (printService.getName().equals("DYMO LabelWriter 450")) {
                myService = printService;
                break;
            }
        }
        if (myService == null) {
            System.out.println("myService is null");
        }
        PrinterJob pj = PrinterJob.getPrinterJob();
        try {
            pj.setPrintService(myService);
        } catch (PrinterException ex) {
            ex.printStackTrace();
        }

            PageFormat pf = pj.defaultPage();
            Paper paper = pf.getPaper();
            double width = fromCMToPPI(3.5);
            double height = fromCMToPPI(8.8);
            paper.setSize(width, height);
            paper.setImageableArea(
                    fromCMToPPI(0.25),
                    fromCMToPPI(0.5),
                    width - fromCMToPPI(0.35),
                    height - fromCMToPPI(1));
            pf.setOrientation(PageFormat.LANDSCAPE);
            pf.setPaper(paper);
            PageFormat validatePage = pj.validatePage(pf);
            pj.setPrintable(new MyLabelPrintable(), pf);
            try {
                pj.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
    }


    protected static double fromCMToPPI(double cm) {
        return toPPI(cm * 0.393700787);
    }

    protected static double toPPI(double inch) {
        return inch * 72d;
    }


    public static class MyLabelPrintable implements Printable {

        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
            System.out.println(pageIndex);
            int result = NO_SUCH_PAGE;
            if (pageIndex < 1) {
                Graphics2D g2d = (Graphics2D) graphics;
                double width = pageFormat.getImageableWidth();
                double height = pageFormat.getImageableHeight();
                System.out.println("width=" + width + " height=" + height);
                g2d.translate((int) pageFormat.getImageableX(),
                        (int) pageFormat.getImageableY());
                g2d.draw(new Rectangle2D.Double(2, 22, width - 20, height - 23));
                FontMetrics fm = g2d.getFontMetrics();
//                g2d.drawString("Testing", 0, fm.getAscent());
                g2d.drawString("Indianapolis, Indiana", 5, 35);
                g2d.drawString(labelLines[0] + " #" + labelLines[1], 5, 51);
                g2d.drawString("Type "+labelLines[2]+", Expires: " + labelLines[3], 5, 67);
                g2d.drawString("Member: U.S. Sailing ILYA &YCA", 5, 83);
                result = PAGE_EXISTS;
            }
            return result;
        }
    }
}