package com.ecsail.pdf.directory;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PDF_Cover extends Table {

	PDF_Object_Settings set;
	
	public PDF_Cover(int numColumns, PDF_Object_Settings set) {
		super(numColumns);
		this.set = set;

		////////////////// Table Properties //////////////////
		setWidth(PageSize.A5.getWidth() * 0.9f);  // makes table 90% of page width
		setHorizontalAlignment(HorizontalAlignment.CENTER);
		Image logoImage = getLogoImage(set.getLogoPath());

		logoImage.scaleToFit(this.getWidth().getValue(), this.getWidth().getValue());
		logoImage.setHorizontalAlignment(HorizontalAlignment.CENTER);
		
		addCell(addVerticalSpace(2));
		Cell cell;
		cell = new Cell();
		//cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		cell.setBorder(Border.NO_BORDER);
		cell.add(logoImage);
		
		addCell(cell);
		addCell(addVerticalSpace(2));
		addCell(addChapter("Membership"));
		addCell(addChapter("Directory"));
	}
	
		private Image getLogoImage(String imagePath) {
			Image logoImage = null;
			try {
				logoImage = new Image(ImageDataFactory.create(toByteArray(getClass().getResourceAsStream(imagePath))));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return logoImage;
		}
	
	   private static byte[] toByteArray(InputStream in) throws IOException {
	          //InputStream is = new BufferedInputStream(System.in);
	          ByteArrayOutputStream os = new ByteArrayOutputStream();
	          byte [] buffer = new byte[1024];
	          int len;
	          // read bytes from the input stream and store them in buffer
	            while ((len = in.read(buffer)) != -1) {
	                // write bytes from the buffer into output stream
	                os.write(buffer, 0, len);
	            }
	            return os.toByteArray();
	       }
	   
		private Cell addVerticalSpace(int space) {
			String carrageReturn = "";
			for(int i = 0; i < space; i++) {
				carrageReturn += "\n";
			}
			Cell cell = new Cell();
			cell.add(new Paragraph(carrageReturn));
			cell.setBorder(Border.NO_BORDER);
			return cell;
		}
		
		private Cell addChapter(String heading) {
			Cell cell = new Cell();
			Paragraph p;
			cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
			cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
			cell.setBorder(Border.NO_BORDER);
			p = new Paragraph(heading);
			p.setFontSize(set.getNormalFontSize() + 25);
//			p.setFont(set.getColumnHead());
			p.setFixedLeading(set.getFixedLeading() + 7);  // sets spacing between lines of text
			p.setTextAlignment(TextAlignment.CENTER);
			cell.add(p);
			return cell;
		}
		

}
