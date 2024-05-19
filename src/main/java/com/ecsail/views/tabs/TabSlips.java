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
import java.util.Map;

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
				case "A43" -> placeText(col[2], row[0], mem.getSlipNumber());
				case "A44" -> placeText(col[3], row[0], mem.getSlipNumber());
				case "A41" -> placeText(col[2], row[1], mem.getSlipNumber());
				case "A42" -> placeText(col[3], row[1], mem.getSlipNumber());
				case "A39" -> placeText(col[2], row[2], mem.getSlipNumber());
				case "A40" -> placeText(col[3], row[2], mem.getSlipNumber());
				case "A37" -> placeText(col[2], row[3], mem.getSlipNumber());
				case "A38" -> placeText(col[3], row[3], mem.getSlipNumber());
				case "A35" -> placeText(col[2], row[4], mem.getSlipNumber());
				case "A36" -> placeText(col[3], row[4], mem.getSlipNumber());
				case "A33" -> placeText(col[2], row[5], mem.getSlipNumber());
				case "A34" -> placeText(col[3], row[5], mem.getSlipNumber());
				case "A31" -> placeText(col[2], row[6], mem.getSlipNumber());
				case "A32" -> placeText(col[3], row[6], mem.getSlipNumber());
				case "A29" -> placeText(col[2], row[7], mem.getSlipNumber());
				case "A30" -> placeText(col[3], row[7], mem.getSlipNumber());
				case "A27" -> placeText(col[2], row[8], mem.getSlipNumber());
				case "A28" -> placeText(col[3], row[8], mem.getSlipNumber());
				case "A25" -> placeText(col[2], row[9], mem.getSlipNumber());
				case "A26" -> placeText(col[3], row[9], mem.getSlipNumber());
				case "A23" -> placeText(col[2], row[10], mem.getSlipNumber());
				case "A24" -> placeText(col[3], row[10], mem.getSlipNumber());
				case "A21" -> placeText(col[2], row[11], mem.getSlipNumber());
				case "A22" -> placeText(col[3], row[11], mem.getSlipNumber());
				case "A19" -> placeText(col[2], row[12], mem.getSlipNumber());
				case "A20" -> placeText(col[3], row[12], mem.getSlipNumber());
				case "A18" -> placeText(col[3], row[13], mem.getSlipNumber());
				case "A16" -> placeText(col[3], row[14], mem.getSlipNumber());
				case "A14" -> placeText(col[3], row[15], mem.getSlipNumber());
				case "A12" -> placeText(col[3], row[16], mem.getSlipNumber());
				case "A10" -> placeText(col[3], row[17], mem.getSlipNumber());
				case "A08" -> placeText(col[3], row[18], mem.getSlipNumber());
				case "A06" -> placeText(col[3], row[19], mem.getSlipNumber());
				case "A04" -> placeText(col[3], row[20], mem.getSlipNumber());
				case "A02" -> placeText(col[3], row[21], mem.getSlipNumber());
				case "BR2" -> placeText(col[5], row[22], mem.getSlipNumber());
				case "BR1" -> placeText(col[5], row[23], mem.getSlipNumber());
				case "B01" -> placeText(col[4], row[21], mem.getSlipNumber());
				case "B02" -> placeText(col[5], row[21], mem.getSlipNumber());
				case "B03" -> placeText(col[4], row[20], mem.getSlipNumber());
				case "B04" -> placeText(col[5], row[20], mem.getSlipNumber());
				case "B05" -> placeText(col[4], row[19], mem.getSlipNumber());
				case "B06" -> placeText(col[5], row[19], mem.getSlipNumber());
				case "B07" -> placeText(col[4], row[18], mem.getSlipNumber());
				case "B08" -> placeText(col[5], row[18], mem.getSlipNumber());
				case "B09" -> placeText(col[4], row[17], mem.getSlipNumber());
				case "B10" -> placeText(col[5], row[17], mem.getSlipNumber());
				case "B11" -> placeText(col[4], row[16], mem.getSlipNumber());
				case "B12" -> placeText(col[5], row[16], mem.getSlipNumber());
				case "B13" -> placeText(col[4], row[15], mem.getSlipNumber());
				case "B14" -> placeText(col[5], row[15], mem.getSlipNumber());
				case "B15" -> placeText(col[4], row[14], mem.getSlipNumber());
				case "B16" -> placeText(col[5], row[14], mem.getSlipNumber());
				case "B17" -> placeText(col[4], row[13], mem.getSlipNumber());
				case "B18" -> placeText(col[5], row[13], mem.getSlipNumber());
				case "B19" -> placeText(col[4], row[12], mem.getSlipNumber());
				case "B20" -> placeText(col[5], row[12], mem.getSlipNumber());
				case "B21" -> placeText(col[4], row[11], mem.getSlipNumber());
				case "B22" -> placeText(col[5], row[11], mem.getSlipNumber());
				case "B23" -> placeText(col[4], row[10], mem.getSlipNumber());
				case "B24" -> placeText(col[5], row[10], mem.getSlipNumber());
				case "B25" -> placeText(col[4], row[9], mem.getSlipNumber());
				case "B26" -> placeText(col[5], row[9], mem.getSlipNumber());
				case "B27" -> placeText(col[4], row[8], mem.getSlipNumber());
				case "B28" -> placeText(col[5], row[8], mem.getSlipNumber());
				case "B29" -> placeText(col[4], row[7], mem.getSlipNumber());
				case "B30" -> placeText(col[5], row[7], mem.getSlipNumber());
				case "B31" -> placeText(col[4], row[6], mem.getSlipNumber());
				case "B32" -> placeText(col[5], row[6], mem.getSlipNumber());
				case "B33" -> placeText(col[4], row[5], mem.getSlipNumber());
				case "B34" -> placeText(col[5], row[5], mem.getSlipNumber());
				case "B35" -> placeText(col[4], row[4], mem.getSlipNumber());
				case "B36" -> placeText(col[5], row[4], mem.getSlipNumber());
				case "B37" -> placeText(col[4], row[3], mem.getSlipNumber());
				case "B38" -> placeText(col[5], row[3], mem.getSlipNumber());
				case "B39" -> placeText(col[4], row[2], mem.getSlipNumber());
				case "B40" -> placeText(col[5], row[2], mem.getSlipNumber());
				case "B41" -> placeText(col[4], row[1], mem.getSlipNumber());
				case "B42" -> placeText(col[5], row[1], mem.getSlipNumber());
				case "B43" -> placeText(col[4], row[0], mem.getSlipNumber());
				case "B44" -> placeText(col[5], row[0], mem.getSlipNumber());
				case "C43" -> placeText(col[6], row[0], mem.getSlipNumber());
				case "C44" -> placeText(col[7], row[0], mem.getSlipNumber());
				case "C41" -> placeText(col[6], row[1], mem.getSlipNumber());
				case "C42" -> placeText(col[7], row[1], mem.getSlipNumber());
				case "C39" -> placeText(col[6], row[2], mem.getSlipNumber());
				case "C40" -> placeText(col[7], row[2], mem.getSlipNumber());
				case "C37" -> placeText(col[6], row[3], mem.getSlipNumber());
				case "C38" -> placeText(col[7], row[3], mem.getSlipNumber());
				case "C35" -> placeText(col[6], row[4], mem.getSlipNumber());
				case "C36" -> placeText(col[7], row[4], mem.getSlipNumber());
				case "C33" -> placeText(col[6], row[5], mem.getSlipNumber());
				case "C34" -> placeText(col[7], row[5], mem.getSlipNumber());
				case "C31" -> placeText(col[6], row[6], mem.getSlipNumber());
				case "C32" -> placeText(col[7], row[6], mem.getSlipNumber());
				case "C29" -> placeText(col[6], row[7], mem.getSlipNumber());
				case "C30" -> placeText(col[7], row[7], mem.getSlipNumber());
				case "C27" -> placeText(col[6], row[8], mem.getSlipNumber());
				case "C28" -> placeText(col[7], row[8], mem.getSlipNumber());
				case "C25" -> placeText(col[6], row[9], mem.getSlipNumber());
				case "C26" -> placeText(col[7], row[9], mem.getSlipNumber());
				case "C23" -> placeText(col[6], row[10], mem.getSlipNumber());
				case "C24" -> placeText(col[7], row[10], mem.getSlipNumber());
				case "C21" -> placeText(col[6], row[11], mem.getSlipNumber());
				case "C22" -> placeText(col[7], row[11], mem.getSlipNumber());
				case "C19" -> placeText(col[6], row[12], mem.getSlipNumber());
				case "C20" -> placeText(col[7], row[12], mem.getSlipNumber());
				case "C17" -> placeText(col[6], row[13], mem.getSlipNumber());
				case "C18" -> placeText(col[7], row[13], mem.getSlipNumber());
				case "C15" -> placeText(col[6], row[14], mem.getSlipNumber());
				case "C16" -> placeText(col[7], row[14], mem.getSlipNumber());
				case "C13" -> placeText(col[6], row[15], mem.getSlipNumber());
				case "C14" -> placeText(col[7], row[15], mem.getSlipNumber());
				case "C11" -> placeText(col[6], row[16], mem.getSlipNumber());
				case "C12" -> placeText(col[7], row[16], mem.getSlipNumber());
				case "C09" -> placeText(col[6], row[17], mem.getSlipNumber());
				case "C10" -> placeText(col[7], row[17], mem.getSlipNumber());
				case "C07" -> placeText(col[6], row[18], mem.getSlipNumber());
				case "C08" -> placeText(col[7], row[18], mem.getSlipNumber());
				case "C05" -> placeText(col[6], row[19], mem.getSlipNumber());
				case "C06" -> placeText(col[7], row[19], mem.getSlipNumber());
				case "C03" -> placeText(col[6], row[20], mem.getSlipNumber());
				case "C04" -> placeText(col[7], row[20], mem.getSlipNumber());
				case "C01" -> placeText(col[6], row[21], mem.getSlipNumber());
				case "C02" -> placeText(col[7], row[21], mem.getSlipNumber());
				case "D01" -> placeText(col[0], row[19], mem.getSlipNumber());
				case "D02" -> placeText(col[1], row[19], mem.getSlipNumber());
				case "D03" -> placeText(col[0], row[18], mem.getSlipNumber());
				case "D04" -> placeText(col[1], row[18], mem.getSlipNumber());
				case "D05" -> placeText(col[0], row[17], mem.getSlipNumber());
				case "D06" -> placeText(col[1], row[17], mem.getSlipNumber());
				case "D07" -> placeText(col[0], row[16], mem.getSlipNumber());
				case "D08" -> placeText(col[1], row[16], mem.getSlipNumber());
				case "D09" -> placeText(col[0], row[15], mem.getSlipNumber());
				case "D10" -> placeText(col[1], row[15], mem.getSlipNumber());
				case "D11" -> placeText(col[0], row[14], mem.getSlipNumber());
				case "D12" -> placeText(col[1], row[14], mem.getSlipNumber());
				case "D13" -> placeText(col[0], row[13], mem.getSlipNumber());
				case "D14" -> placeText(col[1], row[13], mem.getSlipNumber());
				case "D15" -> placeText(col[0], row[12], mem.getSlipNumber());
				case "D16" -> placeText(col[1], row[12], mem.getSlipNumber());
				case "D17" -> placeText(col[0], row[11], mem.getSlipNumber());
				case "D18" -> placeText(col[1], row[11], mem.getSlipNumber());
				case "D19" -> placeText(col[0], row[10], mem.getSlipNumber());
				case "D20" -> placeText(col[1], row[10], mem.getSlipNumber());
				case "D21" -> placeText(col[0], row[9], mem.getSlipNumber());
				case "D22" -> placeText(col[1], row[9], mem.getSlipNumber());
				case "D23" -> placeText(col[0], row[8], mem.getSlipNumber());
				case "D24" -> placeText(col[1], row[8], mem.getSlipNumber());
				case "D25" -> placeText(col[0], row[7], mem.getSlipNumber());
				case "D26" -> placeText(col[1], row[7], mem.getSlipNumber());
				case "D27" -> placeText(col[0], row[6], mem.getSlipNumber()); // problem starts here?
				case "D28" -> placeText(col[1], row[6], mem.getSlipNumber());
				case "D29" -> placeText(col[0], row[5], mem.getSlipNumber());
				case "D30" -> placeText(col[1], row[5], mem.getSlipNumber());
				case "D31" -> placeText(col[0], row[4], mem.getSlipNumber());
				case "D32" -> placeText(col[1], row[4], mem.getSlipNumber());
				case "D33" -> placeText(col[0], row[3], mem.getSlipNumber());
				case "D34" -> placeText(col[1], row[3], mem.getSlipNumber());
				case "D35" -> placeText(col[0], row[2], mem.getSlipNumber());
				case "D36" -> placeText(col[1], row[2], mem.getSlipNumber());
				case "D37" -> placeText(col[0], row[1], mem.getSlipNumber());
				case "D38" -> placeText(col[1], row[1], mem.getSlipNumber());
				case "D39" -> placeText(col[0], row[0], mem.getSlipNumber());
				case "D40" -> placeText(col[1], row[0], mem.getSlipNumber());
				case "F24" -> placeText(176, 511, mem.getSlipNumber());
				case "F22" -> placeText(194, 529, mem.getSlipNumber());
				case "F20" -> placeText(205, 540, mem.getSlipNumber());
				case "F18" -> placeText(223, 558, mem.getSlipNumber());
				case "F16" -> placeText(234, 569, mem.getSlipNumber());
				case "F14" -> placeText(252, 587, mem.getSlipNumber());
				case "F12" -> placeText(263, 598, mem.getSlipNumber());
				case "F10" -> placeText(281, 616, mem.getSlipNumber());
				case "F08" -> placeText(292, 627, mem.getSlipNumber());
				case "F06" -> placeText(310, 645, mem.getSlipNumber());
				case "F04" -> placeText(321, 656, mem.getSlipNumber());
				case "F02" -> placeText(339, 674, mem.getSlipNumber());
				default -> {
				}
			}
		}
	}

	private void setRotation() {
		String[] fDocks = {"F24","F22","F20","F18","F16","F14","F12","F10","F08","F06","F04","F02"};
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
