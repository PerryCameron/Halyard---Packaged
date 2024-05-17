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

//        String commodoreTitle = "Hi ECSC!";
//		String commodoreParagraph1 = "I would like to thank you for giving me the chance to serve you, the members of this wonderful club. I first got into sailing thanks to my father Bob Hickok. In my youth, I was at first both enamored and terrified of sailing. I guess being so little and being on a Hobie could do that to you. I remember always being terrified of flying a hull or heeling on a monohull. Now that's all I want to do! I was introduced to this beautiful sailing club in 2000. Thanks to Janet Hickok, a new world was opened up to me. I always thought the only sailboat there was had two hulls. Ha ha! Thanks to my dad, Wayne Myers, and many other ECSC members guidance and teachings, I got into sailing and racing with ECSC's then new junior sailing program. I learned a-lot about how to have fun, work hard, and develop perseverance. I also learned that some boats only have one hull.\n";
//		String commodoreParagraph2 = "I am very grateful to ECSC and its members for having such an amazing club and junior sailing program. I will never forget back when the program was new.  The boats and gear were old and worn out, or personally owned by club members. I remember saying to Wayne that the reason why I am so slow is because of my old equipment and boat. Wayne said, “It's not about the equipment, it's about the sailor and experience”. Of course, being so young, I was skeptical and didn't believe him. He addressed my skepticism with, \"You know what, take this new boat and equipment and I'll take the old boat and equipment and let's race.\" I bet you all know what happened! Yes, the experienced skipper beat the rookie skipper even though I was on the new boat. That was a valuable lesson for me and one that I will never forget. Thank you Eagle Creek Sailing Club and our junior program for teaching me such valuable lessons in life and for giving me the opportunity to go from being a junior sailor of ECSC to it's Commodore.\n";
//		String commodoreParagraph3 = "“Twenty years from now, you will be more disappointed by the things you didn’t do than those you did. So, throw off the bowlines. Sail away from safe harbor. Catch the wind in your sails. Explore. Dream. Discover.” – Mark Twain\n";

//        String commodoreTitle = "Hi fellow Eagle Creekers,";
//		String commodoreParagraph1 = "I would like to welcome you to another fantastic season on Eagle Ocean. My first term as Commodore was back in 2010 and I am looking forward to serving the club again. I have been an ECSC member for over 20 years and have served previously as Harbormaster, Racing Chair, Safety and Education Chair, and Junior Coach. As I travel to sailing clubs around the world, I am reminded that it isn’t the size of the body of water, the size of the boats, or the budget of the club that makes sailors prosper, it is the culture and character of the club.  Here at ECSC we have members serving as officers of National Class Associations, cruising to far away ports, educating other sailors at the highest level, and racing on an international level; and yet, these same sailors “come home” to race on a Wednesday night, attend our social events, or just sail the waters of Eagle Creek Reservoir. We have past junior sailors that are pro sailors, class boat builders, riggers, teachers, and coaches; and these young adults recognize ECSC as their home club. \n";
//		String commodoreParagraph2 = "ECSC is one of the jewels of Indianapolis. We are a place to get away and lounge on a lazy summer day, to try your hand on the race course every Wednesday or Sunday, to socialize at our many fantastic club gatherings, to have a family picnic, to learn, to share, to meet new friends, and most importantly, to sail. Our club is a volunteer club that has relied on the talents and involvement of our membership. I encourage you to reach out to our hard-working committee chairs listed on the following page and get involved. Your board has worked hard to plan an exciting year of social events, racing, educational opportunities, youth programs, and facilities improvements. In order to incentivize more volunteer commitment, we have raised our hourly volunteer work credit to $15/hour, so there is no excuse not to join in, get involved, and make new friends.\n";
//		String commodoreParagraph3 = "Sail Fast,";

String commodoreTitle = "Dear ECSC Members,";
String commodoreParagraph1 = "As your Commodore for 2024, I'm excited to steer us into another great year of sailing. We are lucky to have such an amazing lake right in our backyard, and we are even luckier to have such a strong community and organization that allows us to sail on her every year.  \n" +
		"\n" +
		"Some could say that I joined ECSC back in 1992 when I was born (my parents are members), however, I like to think that my membership started in 2018 when I moved back to Indiana to make it my home.  ECSC was one of the things I missed most while living away. I was never more land-locked than when I lived in California (I never sailed that summer). Sailing on the Purdue Sailing Team in college was fun (especially traveling to all the Big 10 Universities) but the team practiced on a small river which left a lot to be desired... While living in Dallas, I got connected into a great Laser sailing community and even raced in some regattas down there but none of it was to the scale of our club here in Indiana. With 275 active memberships (just about 600 total headcount including family members) and a facility that can welcome 300 boats (173 in the water), Eagle Creek Sailing Club is one of the most impressive sailing organizations of its kind.";
String commodoreParagraph2 = "My roles & responsibilities at ECSC started when I was quite young. I was the go-to DJ for social events back in the early 2000's. I helped run the Summer Sailing Camp from 2005 to 2011. As an ECSC Junior Race Team member, I traveled all over the country representing ECSC in youth regattas. But the job I'll never forget is helping my dad label and stamp 250 physical newsletters back in the 90's when the Telltales were still paper and mailed out to everyone. (Child labor??). More recently, I've served as the club's publicity chair for the last 5 years where I had the pleasure of keeping everyone informed of club activities and events. \n" +
		"\n" +
		"Now in 2024 I have the opportunity to serve as ECSC's Commodore.  I'm honored to be in this position, and I'm excited at what we are going to accomplish. A huge thank you to all the Committee heads, assistants, and volunteers that put in long hours to make our club run smoothly every year. You all are the reason we can have a wonderful summer full of sailing!";

//		String commodoreParagraph3 = "A huge thank you to all the Committee heads, assistants, and volunteers that put in long hours to make our club run smoothly every year. You all are the reason we can have a wonderful summer full of sailing!";


		addCell(addVerticalSpace(2));
		addCell(newParagraph(commodoreTitle));
		addCell(addVerticalSpace(1));
		addCell(newParagraph(commodoreParagraph1));
		addCell(addVerticalSpace(1));
		addCell(newParagraph(commodoreParagraph2));
		addCell(addVerticalSpace(1));
//		addCell(newParagraph(commodoreParagraph3));
//		addCell(addVerticalSpace(1));
		addCell(newParagraph("EJ Williams                           2024 ECSC Commodore"));
//		addCell(newParagraph("2024 ECSC Commodore"));
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
