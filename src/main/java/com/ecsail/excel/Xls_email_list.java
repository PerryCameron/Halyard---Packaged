package com.ecsail.excel;

import com.ecsail.HalyardPaths;
import com.ecsail.repository.implementations.EmailRepositoryImpl;
import com.ecsail.repository.interfaces.EmailRepository;
import com.ecsail.dto.Email_InformationDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Xls_email_list {

    private static EmailRepository emailRepository = new EmailRepositoryImpl();
	private static ObservableList<Email_InformationDTO> emailInformationDTOS =
            FXCollections.observableArrayList(emailRepository.getEmailInfo());
    private static final Logger logger = LoggerFactory.getLogger(Xls_email_list.class);


    public static void createSpreadSheet() {
		logger.info("Creating email list..");
		String[] columns = {"Membership ID", "Join Date", "Last Name", "First Name", "Email","Primary Email"};

        // Create a Workbook
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

        /* CreationHelper helps us create instances of various things like DataFormat, 
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
       // CreationHelper createHelper = workbook.getCreationHelper();

        // Create a Sheet
        Sheet sheet = workbook.createSheet("Employee");

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        
        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        
     // Create a Row
        Row headerRow = sheet.createRow(0);
        
        // Create cells
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
        
        int rowNum = 1;
        for(Email_InformationDTO e: emailInformationDTOS) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(e.getMembershipId());
            row.createCell(1).setCellValue(e.getJoinDate());
            row.createCell(2).setCellValue(e.getLname());
            row.createCell(3).setCellValue(e.getFname());
            row.createCell(4).setCellValue(e.getEmail());
            row.createCell(5).setCellValue(e.isPrimary());            
        }
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        HalyardPaths.checkPath(HalyardPaths.ECSC_HOME);
        try (
                FileOutputStream fileOut = new FileOutputStream(HalyardPaths.ECSC_HOME + "/Email_List.xlsx");
        ) {
            workbook.write(fileOut);
            logger.info("Email list successfully written to " + HalyardPaths.ECSC_HOME  +"/Email_List.xlsx");
        } catch (FileNotFoundException e) {
            logger.error("File not found: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("IO Exception: " + e.getMessage());
            e.printStackTrace();
        }
	}
}
