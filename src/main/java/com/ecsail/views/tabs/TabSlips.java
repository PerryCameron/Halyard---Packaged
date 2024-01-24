package com.ecsail.views.tabs;


import com.ecsail.BaseApplication;
import com.ecsail.Launcher;
import com.ecsail.pdf.PDF_SlipChart;
import com.ecsail.repository.implementations.MembershipRepositoryImpl;
import com.ecsail.repository.implementations.SlipRepositoryImpl;
import com.ecsail.repository.interfaces.MembershipRepository;
import com.ecsail.repository.interfaces.SlipRepository;
import com.ecsail.dto.MembershipListDTO;
import com.ecsail.dto.SlipDTO;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;

public class TabSlips extends Tab {

	//private ObservableList<Object_Slip> slips;
	private final ObservableList<MembershipListDTO> slipmemberships;
	private final ObservableList<MembershipListDTO> subleaserMemberships;
	private final MembershipRepository membershipRepository = new MembershipRepositoryImpl();
	private final SlipRepository slipRepository = new SlipRepositoryImpl();
	private final ArrayList<SlipDTO> slipDTOS;
	private final HashMap<String,Text> slipsHash = new HashMap<>();
	public static Logger logger = LoggerFactory.getLogger(TabSlips.class);


	// starting point for each colume x-axis
	private final int[] col = { 20,125,280,385,540,643,800,902 };
	private final int[] row = { 19,45,61,87,103,129,145,171,187,213,229,255,271,297,313,339,355,381,397,423,439,465,481,507 };

	public TabSlips(String text) {
		super(text);
		this.slipmemberships = FXCollections.observableArrayList(membershipRepository.getSlipRoster(String.valueOf(Year.now().getValue())));
		this.subleaserMemberships = FXCollections.observableArrayList();
		// gets all slips
		this.slipDTOS = (ArrayList<SlipDTO>) slipRepository.getSlips();


		Pane screenPane = new Pane();
		Button createPdfButton = new Button("Create PDF");
		VBox vboxGrey = new VBox();  // this is the vbox for organizing all the widgets
		VBox vboxBlue = new VBox();
		VBox vboxPink = new VBox(); // this creates a pink border around the table

		///// LISTENERS //////
		/// this listens for a focus on the slips tab and refreshes data everytime.
		this.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
		    if (newValue) {  // focus Gained
				populateNames();
		    }
		});
		
		createPdfButton.setOnAction((event) -> new PDF_SlipChart(String.valueOf(Year.now().getValue())));
				
		///////////////// ATTRIBUTES /////////////////
		
//		createPdfButton.setId("mediumbuttontext");
		vboxBlue.setId("custom-tap-pane-frame");
		screenPane.setId("slip-fonts");
//		vboxPink.setId("box-pink");
		vboxGrey.setId("custom-tap-pane-frame");
		vboxBlue.setPadding(new Insets(10,10,10,10));
		vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink fram around table
		HBox.setHgrow(vboxGrey, Priority.ALWAYS);
		VBox.setVgrow(vboxGrey, Priority.ALWAYS);
		HBox.setHgrow(vboxPink, Priority.ALWAYS);
		VBox.setVgrow(vboxPink, Priority.ALWAYS);
		createPdfButton.setLayoutX(750);
		createPdfButton.setLayoutY(600);

		///////// ACTIONS ////////
		// assigns Text() objects to a hashmap with the slip number as the key
		assignTextObjectsToHashMapWithSlipNumberAsKey();
		// creates text objects, sets text to slip number and adds coordinates for text object
		createSlipsAsTextObjects();
		// overwrites setText method on Text Objects by putting in the Name of person in slip
		populateNames();
		// sets rotation for f docks
		setRotation();
		//////////////////  SET CONTENT ///////////////
		screenPane.getChildren().add(addDocks(10,10,col[0]));
		screenPane.getChildren().add(addDocks(7,11,col[2]));
		screenPane.getChildren().add(addDocks(11,12,col[4]));
		screenPane.getChildren().add(addDocks(11,11,col[6]));
		screenPane.getChildren().add(createPdfButton);
		// adds all Text() objects to screenPane
		slipsHash.forEach((k,v) -> screenPane.getChildren().add(v));
		vboxGrey.getChildren().add(screenPane);
		vboxBlue.getChildren().add(vboxPink);
		vboxPink.getChildren().add(vboxGrey);
		setContent(vboxBlue);
	}

	private void assignTextObjectsToHashMapWithSlipNumberAsKey() {
		for(SlipDTO s: slipDTOS) {
			slipsHash.put(s.getSlipNumber(),new Text(s.getSlipNumber() + " " + s.getAltText()));
		}
	}

	private void rotate45(int i, int i2, String f03) {
		Rotate rotate;
		rotate = new Rotate();
		rotate.setAngle(314);
		rotate.setPivotX(i);
		rotate.setPivotY(i2);
		slipsHash.get(f03).getTransforms().addAll(rotate);
	}

	// places all Text() objects, in a method so it can be refreshed.
	private Group addDocks(int leftDock, int rightDock, int start) {
	    Group group = new Group();
	    Rotate rotate = new Rotate();
	    Rectangle rect;
	    int dockWidth = 80;
	    int dockHeight = 10;
	    int stemWidth = 20;
	    int gapDistance = 42;

	    // draw left docks
	    int y = 23;
	    for(int i = 0; i < leftDock; i++) {
	    rect = new Rectangle(start,y,dockWidth,dockHeight);  // y position always starts at 23
	    rect.setFill(Color.BLACK);
	    rect.setStroke(Color.BLACK);
	    group.getChildren().add(rect);
	    y+=gapDistance;
	    }

	    // draw right docks 
	    y = 23;  // set back to default start point for right dock
	    for(int i = 0; i < rightDock; i++) {
	    rect = new Rectangle(start + dockWidth + stemWidth,y,dockWidth,dockHeight);  // y position always starts at 23
	    rect.setFill(Color.BLACK);
	    rect.setStroke(Color.BLACK);
	    group.getChildren().add(rect);
	    y+=gapDistance;
	    }

		// draw angled right dock
		int x = 50;
		// increasing final number (752) by 41 adds another dock
		for(int ay = 546; ay < 752; ay+=41) {
			rect = new Rectangle(x + dockWidth + stemWidth, ay, dockWidth, dockHeight);  // y position always starts at 23
			rect.setFill(Color.BLACK);
			rect.setStroke(Color.BLACK);
			rotate.setAngle(250);
			rotate.setPivotX(x);
			rotate.setPivotY(ay);
			rect.getTransforms().addAll(rotate);
			group.getChildren().add(rect);
		}

	    // draw stem
	    rect = new Rectangle(start + dockWidth,23,stemWidth,y - 21);  // y position always starts at 23
	    rect.setFill(Color.BLACK);
	    rect.setStroke(Color.BLACK);
	    group.getChildren().addAll(rect,getOctagon(110.0,470.0,42.0));

	    // draw angled stem
	    x = 130;
	    y = 505;
		// increasing height lengthens dock
	    rect = new Rectangle(x,y,stemWidth,280);  // y position always starts at 23
	    rect.setFill(Color.BLACK);
	    rect.setStroke(Color.BLACK);
	    rotate.setAngle(315);
	    rotate.setPivotX(x); 
	    rotate.setPivotY(y);
	    rect.getTransforms().addAll(rotate);
	    group.getChildren().add(rect);
	    return group;
	}
	
	private Polygon getOctagon(double x, double y, double size) {
		Polygon oct = new Polygon();
		setPolygonSides(oct, x, y, size, 8);
	    return oct;
	}
	
	private static void setPolygonSides(Polygon polygon, double centerX, double centerY, double radius, int sides) {
	    polygon.getPoints().clear();
	    final double angleStep = Math.PI * 2 / sides;
	    double angle = 0; // assumes one point is located directly beneat the center point
	    for (int i = 0; i < sides; i++, angle += angleStep) {
	        polygon.getPoints().addAll(
	                Math.sin(angle) * radius + centerX, // x coordinate of the corner
	                Math.cos(angle) * radius + centerY // y coordinate of the corner
	        );
	    }
	}

	private void createSlipsAsTextObjects() {
		for (SlipDTO mem : slipDTOS) {
			switch (mem.getSlipNumber()) {
				case "D40" -> placeText(col[0], row[0], "D40");
				case "D39" -> placeText(col[1], row[0], "D39");
				case "A35" -> placeText(col[2], row[0], "A35");
				case "A34" -> placeText(col[3], row[0], "A34");
				case "B93" -> placeText(col[4], row[0], "B93");
				case "B94" -> placeText(col[5], row[0], "B94");
				case "C157" -> placeText(col[6], row[0], "C157");
				case "C156" -> placeText(col[7], row[0], "C156");
				case "D38" -> placeText(col[0], row[1], "D38");
				case "D37" -> placeText(col[1], row[1], "D37");
				case "A33" -> placeText(col[2], row[1], "A33");
				case "A32" -> placeText(col[3], row[1], "A32");
				case "B91" -> placeText(col[4], row[1], "B91");
				case "B92" -> placeText(col[5], row[1], "B92");
				case "C155" -> placeText(col[6], row[1], "C155");
				case "C154" -> placeText(col[7], row[1], "C154");
				case "D36" -> placeText(col[0], row[2], "D36");
				case "D35" -> placeText(col[1], row[2], "D35");
				case "A31" -> placeText(col[2], row[2], "A31");
				case "A30" -> placeText(col[3], row[2], "A30");
				case "B89" -> placeText(col[4], row[2], "B89");
				case "B90" -> placeText(col[5], row[2], "B90");
				case "C153" -> placeText(col[6], row[2], "C153");
				case "C152" -> placeText(col[7], row[2], "C152");
				case "D34" -> placeText(col[0], row[3], "D34");
				case "D33" -> placeText(col[1], row[3], "D33");
				case "A29" -> placeText(col[2], row[3], "A29");
				case "A28" -> placeText(col[3], row[3], "A28");
				case "B87" -> placeText(col[4], row[3], "B87");
				case "B88" -> placeText(col[5], row[3], "B88");
				case "C151" -> placeText(col[6], row[3], "C151");
				case "C150" -> placeText(col[7], row[3], "C150");
				case "D32" -> placeText(col[0], row[4], "D32");
				case "D31" -> placeText(col[1], row[4], "D31");
				case "A27" -> placeText(col[2], row[4], "A27");
				case "A26" -> placeText(col[3], row[4], "A26");
				case "B85" -> placeText(col[4], row[4], "B85");
				case "B86" -> placeText(col[5], row[4], "B86");
				case "C149" -> placeText(col[6], row[4], "C149");
				case "C148" -> placeText(col[7], row[4], "C148");
				case "D30" -> placeText(col[0], row[5], "D30");
				case "D29" -> placeText(col[1], row[5], "D29");
				case "A25" -> placeText(col[2], row[5], "A25");
				case "A24" -> placeText(col[3], row[5], "A24");
				case "B83" -> placeText(col[4], row[5], "B83");
				case "B84" -> placeText(col[5], row[5], "B84");
				case "C147" -> placeText(col[6], row[5], "C147");
				case "C146" -> placeText(col[7], row[5], "C146");
				case "D28" ->  // problem starts here?
						placeText(col[0], row[6], "D28");
				case "D27" -> placeText(col[1], row[6], "D27");
				case "A23" -> placeText(col[2], row[6], "A23");
				case "A22" -> placeText(col[3], row[6], "A22");
				case "B81" -> placeText(col[4], row[6], "B81");
				case "B82" -> placeText(col[5], row[6], "B82");
				case "C145" -> placeText(col[6], row[6], "C145");
				case "C144" -> placeText(col[7], row[6], "C144");
				case "D26" -> placeText(col[0], row[7], "D26");
				case "D25" -> placeText(col[1], row[7], "D25");
				case "A21" -> placeText(col[2], row[7], "A21");
				case "A20" -> placeText(col[3], row[7], "A20");
				case "B79" -> placeText(col[4], row[7], "B79");
				case "B80" -> placeText(col[5], row[7], "B80");
				case "C143" -> placeText(col[6], row[7], "C143");
				case "C142" -> placeText(col[7], row[7], "C142");
				case "D24" -> placeText(col[0], row[8], "D24");
				case "D23" -> placeText(col[1], row[8], "D23");
				case "A19" -> placeText(col[2], row[8], "A19");
				case "A18" -> placeText(col[3], row[8], "A18");
				case "B77" -> placeText(col[4], row[8], "B77");
				case "B78" -> placeText(col[5], row[8], "B78");
				case "C141" -> placeText(col[6], row[8], "C141");
				case "C140" -> placeText(col[7], row[8], "C140");
				case "D22" -> placeText(col[0], row[9], "D22");
				case "D21" -> placeText(col[1], row[9], "D21");
				case "A17" -> placeText(col[2], row[9], "A17");
				case "A16" -> placeText(col[3], row[9], "A16");
				case "B75" -> placeText(col[4], row[9], "B75");
				case "B76" -> placeText(col[5], row[9], "B76");
				case "C139" -> placeText(col[6], row[9], "C139");
				case "C138" -> placeText(col[7], row[9], "C138");
				case "D20" -> placeText(col[0], row[10], "D20");
				case "D19" -> placeText(col[1], row[10], "D19");
				case "A15" -> placeText(col[2], row[10], "A15");
				case "A14" -> placeText(col[3], row[10], "A14");
				case "B73" -> placeText(col[4], row[10], "B73");
				case "B74" -> placeText(col[5], row[10], "B74");
				case "C137" -> placeText(col[6], row[10], "C137");
				case "C136" -> placeText(col[7], row[10], "C136");
				case "D18" -> placeText(col[0], row[11], "D18");
				case "D17" -> placeText(col[1], row[11], "D17");
				case "A13" -> placeText(col[2], row[11], "A13");
				case "A12" -> placeText(col[3], row[11], "A12");
				case "B71" -> placeText(col[4], row[11], "B71");
				case "B72" -> placeText(col[5], row[11], "B72");
				case "C135" -> placeText(col[6], row[11], "C135");
				case "C134" -> placeText(col[7], row[11], "C134");
				case "D16" -> placeText(col[0], row[12], "D16");
				case "D15" -> placeText(col[1], row[12], "D15");
				case "A11" -> placeText(col[2], row[12], "A11");
				case "A10" -> placeText(col[3], row[12], "A10");
				case "B69" -> placeText(col[4], row[12], "B69");
				case "B70" -> placeText(col[5], row[12], "B70");
				case "C133" -> placeText(col[6], row[12], "C133");
				case "C132" -> placeText(col[7], row[12], "C132");
				case "D14" -> placeText(col[0], row[13], "D14");
				case "D13" -> placeText(col[1], row[13], "D13");
				case "A09" -> placeText(col[3], row[13], "A09");
				case "B67" -> placeText(col[4], row[13], "B67");
				case "B68" -> placeText(col[5], row[13], "B68");
				case "C131" -> placeText(col[6], row[13], "C131");
				case "C130" -> placeText(col[7], row[13], "C130");
				case "D12" -> placeText(col[0], row[14], "D12");
				case "D11" -> placeText(col[1], row[14], "D11");
				case "A08" -> placeText(col[3], row[14], "A08");
				case "B65" -> placeText(col[4], row[14], "B65");
				case "B66" -> placeText(col[5], row[14], "B66");
				case "C129" -> placeText(col[6], row[14], "C129");
				case "C128" -> placeText(col[7], row[14], "C128");
				case "D10" -> placeText(col[0], row[15], "D10");
				case "D09" -> placeText(col[1], row[15], "D09");
				case "A07" -> placeText(col[3], row[15], "A07");
				case "B63" -> placeText(col[4], row[15], "B63");
				case "B64" -> placeText(col[5], row[15], "B64");
				case "C127" -> placeText(col[6], row[15], "C127");
				case "C126" -> placeText(col[7], row[15], "C126");
				case "D08" -> placeText(col[0], row[16], "D08");
				case "D07" -> placeText(col[1], row[16], "D07");
				case "A06" -> placeText(col[3], row[16], "A06");
				case "B61" -> placeText(col[4], row[16], "B61");
				case "B62" -> placeText(col[5], row[16], "B62");
				case "C125" -> placeText(col[6], row[16], "C125");
				case "C124" -> placeText(col[7], row[16], "C124");
				case "D06" -> placeText(col[0], row[17], "D06");
				case "D05" -> placeText(col[1], row[17], "D05");
				case "A05" -> placeText(col[3], row[17], "A05");
				case "B59" -> placeText(col[4], row[17], "B59");
				case "B60" -> placeText(col[5], row[17], "B60");
				case "C123" -> placeText(col[6], row[17], "C123");
				case "C122" -> placeText(col[7], row[17], "C122");
				case "D04" -> placeText(col[0], row[18], "D04");
				case "D03" -> placeText(col[1], row[18], "D03");
				case "A04" -> placeText(col[3], row[18], "A04");
				case "B57" -> placeText(col[4], row[18], "B57");
				case "B58" -> placeText(col[5], row[18], "B58");
				case "C121" -> placeText(col[6], row[18], "C121");
				case "C120" -> placeText(col[7], row[18], "C120");
				case "D02" -> placeText(col[0], row[19], "D02");
				case "D01" -> placeText(col[1], row[19], "D01");
				case "A03" -> placeText(col[3], row[19], "A03");
				case "B55" -> placeText(col[4], row[19], "B55");
				case "B56" -> placeText(col[5], row[19], "B56");
				case "C119" -> placeText(col[6], row[19], "C119");
				case "C118" -> placeText(col[7], row[19], "C118");
				case "A02" -> placeText(col[3], row[20], "A02");
				case "B53" -> placeText(col[4], row[20], "B53");
				case "B54" -> placeText(col[5], row[20], "B54");
				case "C117" -> placeText(col[6], row[20], "C117");
				case "C116" -> placeText(col[7], row[20], "C116");
				case "A01" -> placeText(col[3], row[21], "A01");
				case "B51" -> placeText(col[4], row[21], "B51");
				case "B52" -> placeText(col[5], row[21], "B52");
				case "C115" -> placeText(col[6], row[21], "C115");
				case "C114" -> placeText(col[7], row[21], "C114");
				case "B50" -> placeText(col[5], row[22], "B50");
				case "B48" -> placeText(col[5], row[23], "B48");

//				col		row
//				176		511
//				194	18	529	18
//				205	11	540	11
//				223	18	558	18
//				234	11	569	11
//				252	18	587	18
//				263	11	598	11
//				281	18	616	18
//				292	11	627	11
//				310	18	645	18
//				321	11	656 11
//				339	18	674 18
				case "F12" -> placeText(176, 511, "F12");
				case "F11" -> placeText(194, 529, "F11");
				case "F10" -> placeText(205, 540, "F10");
				case "F09" -> placeText(223, 558, "F09");
				case "F08" -> placeText(234, 569, "F08");
				case "F07" -> placeText(252, 587, "F07");
				case "F06" -> placeText(263, 598, "F06");
				case "F05" -> placeText(281, 616, "F05");
				case "F04" -> placeText(292, 627, "F04");
				case "F03" -> placeText(310, 645, "F03");
				case "F02" -> placeText(321, 656, "F02");
				case "F01" -> placeText(339, 674, "F01");
				default -> {
				}
			}
		}
	}

	private void setRotation() {
		String[] fDocks = {"F12","F11","F10","F09","F08","F07","F06","F05","F04","F03","F02","F01"};
		for(String s: fDocks) {
			rotate45((int) slipsHash.get(s).getX(), (int) slipsHash.get(s).getY(), s);
		}
	}

	private void placeText(int col, int row, String slip) {
		if(slipsHash.get(slip) == null) {
			logger.info("There is no entry in the hash for slip " + slip);
		} else {
			slipsHash.get(slip).setX(col);
			slipsHash.get(slip).setY(row);
		}
	}

	private void populateNames() {
		for(MembershipListDTO m: slipmemberships) {
			addNameToSlip(m);
		}
	}

	private void addNameToSlip(MembershipListDTO m) {
		if(m.getSubLeaser() != 0) {  /// this slip is subleased
			subleaserMemberships.add(membershipRepository.getMembershipByMsIdAndYear(m.getSubLeaser(), Year.now().getValue()));
			slipsHash.get(m.getSlip()).setText(m.getSlip() + " " + subleaserMemberships.get(subleaserMemberships.size() - 1).getLastName() + " " + subleaserMemberships.get(subleaserMemberships.size() - 1).getFirstName().charAt(0) + ".");
			slipsHash.get(m.getSlip()).setFill(Color.CORNFLOWERBLUE);
		} else { // this slip is owned
			slipsHash.get(m.getSlip()).setText(m.getSlip() + " " + m.getLastName() + " " + m.getFirstName().charAt(0) + ".");
		}
		setMouseListener(slipsHash.get(m.getSlip()), m.getMsId(), m.getSubLeaser());
	}

	private void setMouseListener(Text text, int msid, int submsid) {
		Color color = (Color) text.getFill();
		if (color == Color.CORNFLOWERBLUE) {  // blue if it is a sublease
			text.setOnMouseExited(ex -> text.setFill(Color.CORNFLOWERBLUE));
		} else {
			text.setOnMouseExited(ex -> text.setFill(Color.BLACK));
		}
		text.setOnMouseEntered(en -> text.setFill(Color.RED));
		text.setOnMouseClicked(e -> {
			if (e.getClickCount() == 2) {
				if (color == Color.CORNFLOWERBLUE) {
					// this is a sublease
					Launcher.launchTabFromSlips(submsid);
				} else {
					Launcher.launchTabFromSlips(msid);
				}
			}
		});
	}
}
