package com.ecsail.pdf;

import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;

import java.io.IOException;

public class PDF_Font_Utilities {
	
	public static PdfFont setFont()  {
		FontProgramFactory.registerFont("c:/windows/fonts/times.ttf", "garamond bold");
		try {
			return PdfFontFactory.createRegisteredFont("garamond bold");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
