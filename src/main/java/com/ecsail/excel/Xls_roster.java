package com.ecsail.excel;

import com.ecsail.HalyardPaths;
import com.ecsail.gui.common.SaveFileChooser;
import com.ecsail.sql.select.SqlEmail;
import com.ecsail.sql.select.SqlMembership_Id;
import com.ecsail.sql.select.SqlPhone;
import com.ecsail.dto.EmailDTO;
import com.ecsail.dto.MembershipListDTO;
import com.ecsail.dto.PhoneDTO;
import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Xls_roster {
//	private RosterSelectDTO printChoices;
	
//	public Xls_roster(ObservableList<MembershipListDTO> rosters, RosterSelectDTO printChoices) {
//		this.printChoices = printChoices;
//		ArrayList<String> headers = getHeaders();
//		System.out.println("Creating Roster..");
//		//String[] columnHeads = {"Membership ID", "Last Name", "First Name", "Join Date", "Street Address","City","State","Zip"};
//
//        // Create a Workbook
//        Workbook workBook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
//
//        /* CreationHelper helps us create instances of various things like DataFormat,
//           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
//        // CreationHelper createHelper = workbook.getCreationHelper();
//
//        // Create a Sheet
//        Sheet sheet = workBook.createSheet(printChoices.getYear() + " Roster");
//
//        // Create a Font for styling header cells
//        Font headerFont = workBook.createFont();
//        headerFont.setBold(true);
//        headerFont.setFontHeightInPoints((short) 12);
//        headerFont.setColor(IndexedColors.BLACK.getIndex());
//
//        // Create a CellStyle with the font
//        CellStyle headerCellStyle = workBook.createCellStyle();
//        headerCellStyle.setFont(headerFont);
//
//        // Create a Row
//        Row headerRow = sheet.createRow(0);
//
//        // Create the header of the sheet
//        for(int i = 0; i < headers.size(); i++) {
//            Cell cell = headerRow.createCell(i);
//            cell.setCellValue(headers.get(i));
//            cell.setCellStyle(headerCellStyle);
//        }
//
//        // prints the main body of information
//        int rowNum = 1;
//        for(MembershipListDTO m: rosters) {
//            createRow(sheet,rowNum,m);
//            rowNum++;
//        }
//
//		// makes the columns nice widths for the data
//		for (int i = 0; i < headers.size(); i++) {
//			sheet.autoSizeColumn(i);
//		}
//
//		File file = new SaveFileChooser(HalyardPaths.ROSTERS + "/", getFileName(), "Excel Files", "*.xlsx").getFile();
//
//		if (file != null) {
//			FileOutputStream fileOut = getFileOutPutStream(file);
//			writeToWorkbook(workBook, fileOut);
//			closeFileStream(fileOut);
//			closeWorkBook(workBook);
//		}
//
//	}
//
//	private void closeWorkBook(Workbook workBook) {
//		try {
//			workBook.close();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	}
//
//	//////////////////////////////  CLASS METHODS /////////////////////////////
//
//	private void closeFileStream(FileOutputStream fileOut) {
//		try {
//			fileOut.close();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	}
//
//	private void writeToWorkbook(Workbook workbook, FileOutputStream fileOut) {
//		try {
//			workbook.write(fileOut);
//		} catch (IOException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
//	}
//
//	private FileOutputStream getFileOutPutStream(File file) {
//		FileOutputStream fileOut = null;
//		try {
//			fileOut = new FileOutputStream(file);
//			System.out.println("Creating " + file);
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		return fileOut;
//	}
//
//	private String getFileName() {
//		HalyardPaths.checkPath(HalyardPaths.ROSTERS);
//		String fileName = printChoices.getYear();
//		if(printChoices.isActive()) {
//			fileName += " Active";
//		} else if (printChoices.isNonRenew()) {
//			fileName += " Non-renew";
//		} else if (printChoices.isNewMembers()) {
//			fileName += " New_Member";
//		} else if (printChoices.isSlipwait()) {
//			fileName += " Slip_Waiting_List";
//		} else if (printChoices.isSlip()) {
//			fileName += " Slip_Owners_List";
//		} else if (printChoices.isActiveAndInactive()) {
//			fileName += " Active_And_Inactive";
//		} else if (printChoices.isAll()) {
//			fileName += " ActiveAndInactive";
//		} else {  // is both new and re-returning members
//			fileName += " New Member";
//		}
//		System.out.println("Filename " + fileName + " is selected");
//		return fileName += " Roster.xlsx";
//	}
//
//	private Row createRow(Sheet sheet, int rowNum, MembershipListDTO m) {
//		Row row = sheet.createRow(rowNum);
//		int cellNumber = 0;
//		if(printChoices.isMembership_id()) {
//			row.createCell(cellNumber).setCellValue(m.getMembershipId());
//			cellNumber++;
//		}
//		if(printChoices.isLastName()) {
//			row.createCell(cellNumber).setCellValue(m.getlName());
//			cellNumber++;
//		}
//		if(printChoices.isFirstName()) {
//			row.createCell(cellNumber).setCellValue(m.getfName());
//			cellNumber++;
//		}
//		if(printChoices.isJoinDate()) {
//			row.createCell(cellNumber).setCellValue(m.getJoinDate());
//			cellNumber++;
//		}
//		if(printChoices.isStreetAddress()) {
//			row.createCell(cellNumber).setCellValue(m.getAddress());
//			cellNumber++;
//		}
//		if(printChoices.isCity()) {
//			row.createCell(cellNumber).setCellValue(m.getCity());
//			cellNumber++;
//		}
//		if(printChoices.isState()) {
//			row.createCell(cellNumber).setCellValue(m.getState());
//			cellNumber++;
//		}
//		if(printChoices.isZip()) {
//			row.createCell(cellNumber).setCellValue(m.getZip());
//			cellNumber++;
//		}
//		if(printChoices.isMemtype()) {
//			row.createCell(cellNumber).setCellValue(m.getMemType());
//			cellNumber++;
//		}
//		if(printChoices.isSlip()) {
//			row.createCell(cellNumber).setCellValue(m.getSlip());
//			cellNumber++;
//		}
//		if(printChoices.isPhone()) {
//			row.createCell(cellNumber).setCellValue(getPhone(m.getpId()));
//			cellNumber++;
//		}
//		if(printChoices.isEmail()) {
//			row.createCell(cellNumber).setCellValue(getEmail(m.getpId()));
//			cellNumber++;
//		}
//		if(printChoices.isSubleasedto()) {
//			row.createCell(cellNumber).setCellValue(getSubleaser(m));
//			cellNumber++;
//		}
//        return row;
//	}
//
//	private ArrayList<String> getHeaders() {
//		ArrayList<String> headers = new ArrayList<String>();
//		if(printChoices.isMembership_id()) {
//			headers.add("Memebership ID");
//		}
//		if(printChoices.isLastName()) {
//			headers.add("Last Name");
//		}
//		if(printChoices.isFirstName()) {
//			headers.add("First Name");
//		}
//		if(printChoices.isJoinDate()) {
//			headers.add("Join Date");
//		}
//		if(printChoices.isStreetAddress()) {
//			headers.add("Street Address");
//		}
//		if(printChoices.isCity()) {
//			headers.add("City");
//		}
//		if(printChoices.isState()) {
//			headers.add("State");
//		}
//		if(printChoices.isZip()) {
//			headers.add("Zip");
//		}
//		if(printChoices.isMemtype()) {
//			headers.add("Type");
//		}
//		if(printChoices.isSlip()) {
//			headers.add("Slip");
//		}
//		if(printChoices.isPhone()) {
//			headers.add("Phone");
//		}
//		if(printChoices.isEmail()) {
//			headers.add("Email");
//		}
//		if(printChoices.isSubleasedto()) {
//			headers.add("Subleased To");
//		}
//		return headers;
//	}
//
//	private String getPhone(int p_id) {
//		String phoneString = "";
//		ObservableList<PhoneDTO> phones = SqlPhone.getPhoneByPid(p_id);
//		if (phones != null) {
//			for (PhoneDTO p : phones) {
//				if (p.getPhoneType().equals("C")) {  // we prefer a cell phone
//					phoneString = p.getPhoneNumber();
//					break;
//				} else if (p.getPhoneType().contentEquals("H")) { // if home phone is all that is available we go with it
//					phoneString = p.getPhoneNumber();
//				}
//			}
//		}
//		return phoneString;
//	}
//
//	private String getEmail(int p_id) {
//		String emailString = "";
//		ObservableList<EmailDTO> email = SqlEmail.getEmail(p_id);
//		if (email != null) {
//			for (EmailDTO e: email) {
//				if(e.isPrimaryUse()) {
//					emailString = e.getEmail();
//					break;
//				} else {
//					emailString = e.getEmail();
//				}
//
//			}
//		}
//		return emailString;
//	}
//
//	private String getSubleaser(MembershipListDTO owner) {
//		String subleaseString = "";
//		if(owner.getSubleaser() != 0) {
//			subleaseString = SqlMembership_Id.getId(owner.getSubleaser());
//		}
//		return subleaseString;
//	}
}



















