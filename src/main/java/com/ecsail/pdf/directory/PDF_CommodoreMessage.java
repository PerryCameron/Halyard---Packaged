package com.ecsail.pdf.directory;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;

public class PDF_CommodoreMessage extends Table {

	PDF_Object_Settings set;
	public PDF_CommodoreMessage(int numColumns, PDF_Object_Settings set) {
		super(numColumns);
		this.set = set;
		setWidth(set.getPageSize().getWidth() * 0.9f);  // makes table 90% of page width
		setHorizontalAlignment(HorizontalAlignment.CENTER);
		
		///////////////// Cells ////////////////////////////

//		String commodoreTitle = "Hello to the greatest sailing club in America.";
//		String commodoreParagraph1 = "It is truly an honor to serve my ECSC family.  From the first time we entered the property Nancy and I experienced calm tranquility mixed with excitement and wonder.  We always say that it’s a little vacation every time we are at the club.  I was immediately impressed by how helpful people are if you remain humble, inquisitive, and grateful for them.  There are so many sailors that want to share their knowledge, and we are extremely grateful for that. This is truly a remarkable place where people from diverse backgrounds and experience levels join together to share a little slice of heaven.   Whether you be a casual cruiser or a die-hard racer, sail a big cruiser or a small dinghy, are a CEO or are retired, we all find camaraderie at ECSC.  Nancy and I joined with very little sailing experience, but we threw ourselves into the club and sailing with reckless abandon.  Some attempts were more successful than others, but we continued to practice.  At first, we just wanted to cruise but quickly fell in love with racing as well.  Now we just love to be on the water and around our fellow sailors.  Sometimes it’s just great to be at the club and hear halyards slapping on masts on a windy day.";
//		String commodoreParagraph2 = "I encourage everyone to take advantage of the myriad of opportunities at our club from social events to racing and volunteer work parties.  Your board works hard to have plenty to offer all ages and interests.  There are many ways to meet new people, share a few laughs and maybe even learn a thing or two.";
//		String commodoreParagraph3 = "I am optimistic that 2021 will be a much more enjoyable and interactive year for us all.  Hopefully we will be able to catch up with old friends, make new ones, and take advantage of all the magic afforded by our beloved club.  Please respect others as we have made great strides but COVID is not behind us yet.  My biggest requests are that we all share a fat happy smile and greet each other warmly, offer a helping hand without being asked, show grace if you feel offended, and spread some joy.  Remember that for all of us this is a 100% discretionary time and income activity, so let’s keep it safe and enjoyable for all.";

        String commodoreTitle = "Hi ECSC!";
		String commodoreParagraph1 = "I would like to thank you for giving me the chance to serve you, the members of this wonderful club. I first got into sailing thanks to my father Bob Hickok. In my youth, I was at first both enamored and terrified of sailing. I guess being so little and being on a Hobie could do that to you. I remember always being terrified of flying a hull or heeling on a monohull. Now that's all I want to do! I was introduced to this beautiful sailing club in 2000. Thanks to Janet Hickok, a new world was opened up to me. I always thought the only sailboat there was had two hulls. Ha ha! Thanks to my dad, Wayne Myers, and many other ECSC members guidance and teachings, I got into sailing and racing with ECSC's then new junior sailing program. I learned a-lot about how to have fun, work hard, and develop perseverance. I also learned that some boats only have one hull.\n";
		String commodoreParagraph2 = "I am very grateful to ECSC and its members for having such an amazing club and junior sailing program. I will never forget back when the program was new.  The boats and gear were old and worn out, or personally owned by club members. I remember saying to Wayne that the reason why I am so slow is because of my old equipment and boat. Wayne said, “It's not about the equipment, it's about the sailor and experience”. Of course, being so young, I was skeptical and didn't believe him. He addressed my skepticism with, \"You know what, take this new boat and equipment and I'll take the old boat and equipment and let's race.\" I bet you all know what happened! Yes, the experienced skipper beat the rookie skipper even though I was on the new boat. That was a valuable lesson for me and one that I will never forget. Thank you Eagle Creek Sailing Club and our junior program for teaching me such valuable lessons in life and for giving me the opportunity to go from being a junior sailor of ECSC to it's Commodore.\n";
		String commodoreParagraph3 = "“Twenty years from now, you will be more disappointed by the things you didn’t do than those you did. So, throw off the bowlines. Sail away from safe harbor. Catch the wind in your sails. Explore. Dream. Discover.” – Mark Twain\n";

		addCell(addVerticalSpace(2));
		addCell(newParagraph(commodoreTitle));
		addCell(addVerticalSpace(1));
		addCell(newParagraph(commodoreParagraph1));
		addCell(addVerticalSpace(1));
		addCell(newParagraph(commodoreParagraph2));
		addCell(addVerticalSpace(1));
//		addCell(newParagraph(commodoreParagraph3));
//		addCell(addVerticalSpace(1));
		addCell(newParagraph("Ki Hickok"));
		addCell(newParagraph("2022 ECSC Commodore"));
	}
	
	private Cell newParagraph(String text) {
		Paragraph p;
		p = new Paragraph(text);
		p.setFontSize(set.getNormalFontSize());
		Cell cell = new Cell();
		cell.add(p);
		cell.setBorder(Border.NO_BORDER);
		return cell;
	}
	
	private Cell addVerticalSpace(int space) {
		String carrageReturn = "";
		for(int i = 0; i < space; i++) {
			carrageReturn += "\n";
		}
		Cell cell = new Cell();
		Paragraph p = new Paragraph(carrageReturn);
		p.setFixedLeading(7);	
		cell.add(p);
		cell.setBorder(Border.NO_BORDER);
		return cell;
	}

}
