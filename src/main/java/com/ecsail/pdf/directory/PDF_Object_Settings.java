package com.ecsail.pdf.directory;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.Style;

import java.io.IOException;

// No. 6 1/4 (#6-1/4) Envelope inches: 3.5 x 6 (mm: 88.9 x 152.4)
// 6 inch x 72 points = 432 points (the width)
// 3.5 inch x 72 points = 252 points (the height)

public class PDF_Object_Settings {

	private String logoPath;
	private String selectedYear;
	private int normalFontSize;
	private int slipFontSize;
	private int fixedLeading;
	private int fixedLeadingNarrow;
	private float titleBoxHeight;
	private PdfFont columnHead;
	private DeviceCmyk mainColor;
	private Color dockColor;
	private Style emailColor;
	private int numberOfRowsByNumber; // this is the number of columns in MembersByNumber
	private int numberOfCommodoreColumes; // this is the number of rows in Commodore Page
	private float width;
	private float height;
	private Rectangle pageSize;
	
	public PDF_Object_Settings(String selectedYear) {
		super();
		this.logoPath = "/Stickers/2024.png";
		this.selectedYear = selectedYear;
		this.normalFontSize = 10;
		this.slipFontSize = 6;
		this.fixedLeading = 25;
		this.fixedLeadingNarrow = 10;
		this.titleBoxHeight =20;
		this.columnHead = constructFontHeading();
//		this.mainColor = new DeviceCmyk(.93f, 0, 0.7f, 0.62f);  // green color in document 2021
//		this.mainColor = new DeviceCmyk(0f, .24f, .60f, 0f);  // yellow color in document 2022
//		this.mainColor = new DeviceCmyk(0.52f, .34f, 0f, 0f);  // blue color in document 2023
		this.mainColor = new DeviceCmyk(0.18f, .5f, 0f, 0.24f);  // blue color in document 2024
		this.dockColor  = new DeviceRgb(237, 237, 237);
		this.emailColor = new Style().setFontColor(ColorConstants.BLUE);
		this.numberOfRowsByNumber = 28; // this needs to go up to 29 when you get more memberships
		this.numberOfCommodoreColumes = 2;
		this.width = 5.5f;
		this.height = 8.5f;
		this.pageSize = getSizeOfRectangle();
	}
	
	private com.itextpdf.kernel.geom.Rectangle getSizeOfRectangle() {
		float widthPoints = 72 * this.width;
		float heightPoints = 72 * this.height;
		Rectangle sheet = new Rectangle(widthPoints, heightPoints);
		return sheet;
	}

	private PdfFont constructFontHeading() {
		PdfFont pdfFont = null;

		try {
			FontProgram fontProgram = FontProgramFactory.createFont(StandardFonts.HELVETICA);
			// updating library forced me to remove the true below to work
			// pdfFont = PdfFontFactory.createFont(fontProgram, PdfEncodings.WINANSI, true);
			pdfFont = PdfFontFactory.createFont(fontProgram, PdfEncodings.WINANSI);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return pdfFont;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(String selectedYear) {
		this.selectedYear = selectedYear;
	}

	public int getNormalFontSize() {
		return normalFontSize;
	}

	public void setNormalFontSize(int normalFontSize) {
		this.normalFontSize = normalFontSize;
	}

	public int getFixedLeading() {
		return fixedLeading;
	}

	public void setFixedLeading(int fixedLeading) {
		this.fixedLeading = fixedLeading;
	}

	public int getFixedLeadingNarrow() {
		return fixedLeadingNarrow;
	}

	public void setFixedLeadingNarrow(int fixedLeadingNarrow) {
		this.fixedLeadingNarrow = fixedLeadingNarrow;
	}

	public float getTitleBoxHeight() {
		return titleBoxHeight;
	}

	public void setTitleBoxHeight(float titleBoxHeight) {
		this.titleBoxHeight = titleBoxHeight;
	}

	public PdfFont getColumnHead() {
		return columnHead;
	}

	public void setColumnHead(PdfFont columnHead) {
		this.columnHead = columnHead;
	}

	public DeviceCmyk getMainColor() {
		return mainColor;
	}

	public void setMainColor(DeviceCmyk mainColor) {
		this.mainColor = mainColor;
	}

	public Style getEmailColor() {
		return emailColor;
	}

	public void setEmailColor(Style emailColor) {
		this.emailColor = emailColor;
	}

	public int getNumberOfRowsByNumber() {
		return numberOfRowsByNumber;
	}

	public void setNumberOfRowsByNumber(int numberOfRowsByNumber) {
		this.numberOfRowsByNumber = numberOfRowsByNumber;
	}

	public int getNumberOfCommodoreColumes() {
		return numberOfCommodoreColumes;
	}

	public void setNumberOfCommodoreColumes(int numberOfCommodoreColumes) {
		this.numberOfCommodoreColumes = numberOfCommodoreColumes;
	}

	public Color getDockColor() {
		return dockColor;
	}

	public void setDockColor(Color dockColor) {
		this.dockColor = dockColor;
	}

	public int getSlipFontSize() {
		return slipFontSize;
	}

	public void setSlipFontSize(int slipFontSize) {
		this.slipFontSize = slipFontSize;
	}

	public com.itextpdf.kernel.geom.Rectangle getPageSize() {
		return pageSize;
	}

	public void setPageSize(Rectangle pageSize) {
		this.pageSize = pageSize;
	}	
	
}
