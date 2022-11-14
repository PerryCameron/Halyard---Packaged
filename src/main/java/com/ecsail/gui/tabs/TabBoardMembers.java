package com.ecsail.gui.tabs;

import com.ecsail.BaseApplication;
import com.ecsail.Launcher;
import com.ecsail.enums.Officer;
import com.ecsail.sql.select.SqlBoard;
import com.ecsail.sql.select.SqlBoardPositions;
import com.ecsail.structures.BoardDTO;
import com.ecsail.structures.BoardPositionDTO;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;


public class TabBoardMembers extends Tab {

	private VBox boardMembersVBox1 = new VBox();
	private VBox boardMembersVBox2 = new VBox();
	private VBox boardMembersVBox3 = new VBox();
	private VBox committeeVBox1 = new VBox();  // titles
	private VBox committeeVBox2 = new VBox();
	private VBox officerVBox1 = new VBox();  // titles
	private VBox officerVBox2 = new VBox();
	private ObservableList<BoardDTO> board;
	private String selectedYear;
	private String currentYear;
	private Text year;

	
	public TabBoardMembers(String text) {
		super(text);
		this.selectedYear = new SimpleDateFormat("yyyy").format(new Date());  // let's start at the current year
		this.board =  SqlBoard.getBoard(selectedYear);
		this.currentYear = selectedYear;  // save the current year for later
		this.year = new Text(selectedYear + " Officers");
		// gets a list of board position data to use throughout app.

		
	VBox vboxLeft = new VBox();  // this is the vbox for organizing all the widgets
	HBox vboxBlue = new HBox();
	VBox vboxPink = new VBox(); // this creates a pink border around the table
	HBox officersTitleHBox = new HBox();
	HBox officersHBox = new HBox();
	HBox committeeTitleHBox = new HBox();
	HBox committeeHBox = new HBox();
	HBox boardMembersTitleBox = new HBox();
	HBox boardMembersHBox = new HBox();

		//Label year = new Label(selectedYear + " Officers");
	Text chairs = new Text("Committee Chairs");
	Text board = new Text("Board of Directors");
	chairs.getStyleClass().add("title-text-bod");
	board.getStyleClass().add("title-text-bod");
	year.getStyleClass().add("title-text-bod");

	Image stickerImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Stickers/" + selectedYear + ".png")));
	//Image slipImage = new Image(getClass().getResourceAsStream("/Stickers/" + filename + ".png"), 600, 600, false, false);
	ImageView imageView = new ImageView(stickerImage);
	imageView.setFitWidth(300);
	imageView.setFitHeight(300);
	imageView.setPreserveRatio(true);

	var comboBox = new ComboBox<Integer>();
	for(int i = Integer.parseInt(currentYear) + 1; i > 1969; i--) {
		comboBox.getItems().add(i);
	}

		comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {

			  selectedYear = newValue.toString();
			  refreshBoardList();
			  clearBoard(officerVBox1, officerVBox2, committeeVBox1, committeeVBox2,boardMembersVBox1,boardMembersVBox2,boardMembersVBox3);
			  addOfficers();
			  addChairmen();
			  addBoard(boardMembersVBox1,boardMembersVBox2,boardMembersVBox3);
			  Image newImage;
			  try {
				  newImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Stickers/" + selectedYear + ".png")));
			  } catch (NullPointerException e) {
				  BaseApplication.logger.error("Couldn't locate /Stickers/" + selectedYear + ".png" );
				  newImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Stickers/unknown.png")));
			  }
			  imageView.setImage(newImage);

		});
	
	boardMembersVBox1.setPrefWidth(150);
	boardMembersVBox2.setPrefWidth(150);
	boardMembersVBox3.setPrefWidth(150);
	comboBox.setPrefWidth(150);
	comboBox.setId("bigcombo-box");

	vboxLeft.setAlignment(Pos.TOP_CENTER);


	vboxLeft.setPadding(new Insets(15,0,0,40));
	boardMembersHBox.setPadding(new Insets(0,0,0,100));
	vboxBlue.setPadding(new Insets(10,10,10,10));
	vboxPink.setPadding(new Insets(3,3,3,3)); // spacing to make pink frame around table
	
	officerVBox1.getStyleClass().add("labels");
	committeeVBox1.getStyleClass().add("labels");
	officersHBox.getStyleClass().add("board-general");
	committeeHBox.getStyleClass().add("board-general");
	boardMembersHBox.getStyleClass().add("board-general");
	officersTitleHBox.getStyleClass().add("title-bod-box");
	committeeTitleHBox.getStyleClass().add("title-bod-box");
	boardMembersTitleBox.getStyleClass().add("title-bod-box");

	vboxLeft.setSpacing(40);
	officersHBox.setSpacing(120);
	committeeHBox.setSpacing(30);
	boardMembersHBox.setSpacing(40);
	officersTitleHBox.setPadding(new Insets(10,0,0,0));
	officersTitleHBox.setSpacing(190);
	officerVBox1.setSpacing(4);
	officerVBox2.setSpacing(4);
	committeeVBox1.setSpacing(4);
	committeeVBox2.setSpacing(4);
	boardMembersVBox1.setSpacing(4);
	boardMembersVBox2.setSpacing(4);
	boardMembersVBox3.setSpacing(4);
	vboxPink.setSpacing(10);

	VBox.setVgrow(vboxPink, Priority.ALWAYS);
	HBox.setHgrow(vboxPink,Priority.ALWAYS);

	vboxPink.setId("custom-tap-pane-frame");
	vboxLeft.setId("custom-tap-pane-frame");

	comboBox.getSelectionModel().select(1);

	officersHBox.getChildren().addAll(officerVBox1,officerVBox2);
	committeeHBox.getChildren().addAll(committeeVBox1,committeeVBox2);
	boardMembersHBox.getChildren().addAll(boardMembersVBox1,boardMembersVBox2,boardMembersVBox3);

	officersTitleHBox.getChildren().addAll(year);
	committeeTitleHBox.getChildren().addAll(chairs);
	boardMembersTitleBox.getChildren().addAll(board);
	vboxLeft.getChildren().addAll(comboBox, imageView);
	vboxPink.getChildren().addAll(officersTitleHBox, officersHBox, committeeTitleHBox, committeeHBox, boardMembersTitleBox,boardMembersHBox);
	//Pane screenPane = new Pane();
//	vboxBlue.setId("box-blue");
	//screenPane.setId("slip-fonts");



	vboxBlue.getChildren().addAll(vboxLeft,vboxPink);
	setContent(vboxBlue);
	}
	
	private void refreshBoardList() {
		year.setText(selectedYear + " Officers");
		board.clear();
		board =  SqlBoard.getBoard(selectedYear);
	}
	
	private void clearBoard(VBox officerVBox1, VBox officerVBox2, VBox committeeVBox1, VBox committeeVBox2,VBox boardMembersVBox1,VBox boardMembersVBox2,VBox boardMembersVBox3) {
		officerVBox1.getChildren().clear();
		officerVBox2.getChildren().clear();
		committeeVBox1.getChildren().clear();
		committeeVBox2.getChildren().clear();
		boardMembersVBox1.getChildren().clear();
		boardMembersVBox2.getChildren().clear();
		boardMembersVBox3.getChildren().clear();
	}
	
	private void addBoard(VBox boardMembersVBox1,VBox boardMembersVBox2,VBox boardMembersVBox3) {
		getBoard(selectedYear, boardMembersVBox1);  /// add a listener here
		getBoard(incrementSelectedYear(1), boardMembersVBox2);
		getBoard(incrementSelectedYear(2), boardMembersVBox3);	
	}
	
	private String incrementSelectedYear(int increment) {
		return (Integer.parseInt(selectedYear) + increment) + "";
	}


	private void addOfficers() {
		BaseApplication.boardPositions.stream()
				.filter(p -> p.isOfficer())
				.map(offType -> new Pair(offType.identifier(), getOfficer(offType.identifier())))
				.filter(pair -> !pair.value.equals(""))
				.forEach(pair -> {
					Text officerTitle = new Text(Officer.getByCode(pair.key.toString()));
					officerTitle.getStyleClass().add("bod-position-titles-text");
					officerVBox1.getChildren().add(officerTitle); // this is our labels
					Text officerName = new Text(pair.value.toString());
					officerName.getStyleClass().add("bod-names-text");
					officerVBox2.getChildren()
							.add(setMouseListener(officerName, getOfficerMSID(pair.key.toString())));
				});

//		Arrays.stream(Officer.values()).limit(7)
//				.filter(offTypes -> !offTypes.getText().equals("Board Member"))
//				.map(offTypes -> new Pair(offTypes.getCode(), getOfficer(offTypes.getCode())))
//				.filter(pair -> !pair.value.equals(""))
//				.forEach(pair -> {
//						Text officerTitle = new Text(Officer.getNameByCode(pair.key.toString()));
//						officerTitle.getStyleClass().add("bod-position-titles-text");
//						officerVBox1.getChildren().add(officerTitle); // this is our labels
//						Text officerName = new Text(pair.value.toString());
//						officerName.getStyleClass().add("bod-names-text");
//						officerVBox2.getChildren()
//								.add(setMouseListener(officerName, getOfficerMSID(pair.key.toString())));
//				});
	}

	private void addChairmen() {
		BaseApplication.boardPositions.stream()
						.filter(p -> p.isChair() || p.isAssist())
				.map(offType -> new Pair<>(offType.identifier(), getOfficer(offType.identifier())))
				.filter(pair -> !pair.value.equals(""))
				.forEach(pair -> {
					Text committeeTitle = new Text(Officer.getByCode(pair.key));
					committeeTitle.getStyleClass().add("bod-position-titles-text");
					committeeVBox1.getChildren().add(committeeTitle); // this is our labels
					Text chairmanName = new Text(pair.value);
					chairmanName.getStyleClass().add("bod-names-text");
					committeeVBox2.getChildren()
							.add(setMouseListener(chairmanName, getOfficerMSID(pair.key)));
				});

//		Arrays.stream(Officer.values()).skip(7)
//				.filter(offTypes -> !offTypes.getText().equals("Board Member"))
//				.map(offTypes -> new Pair<>(offTypes.getCode(), getOfficer(offTypes.getCode())))
//				.filter(pair -> !pair.value.equals(""))
//				.forEach(pair -> {
//					Text committeeTitle = new Text(Officer.getNameByCode(pair.key));
//					committeeTitle.getStyleClass().add("bod-position-titles-text");
//					committeeVBox1.getChildren().add(committeeTitle); // this is our labels
//					Text chairmanName = new Text(pair.value);
//					chairmanName.getStyleClass().add("bod-names-text");
//					committeeVBox2.getChildren()
//							.add(setMouseListener(chairmanName, getOfficerMSID(pair.key)));
//				});
	}

	/**
	 * Takes an officer type and streams through the board list to find correct officer
	 * then returns their first and last name as one string
	 * @param offType
	 * @return
	 */
	private String getOfficer(String offType) {
		return board.stream().filter(o -> o.getOfficer_type().equals(offType))
				.map(o -> String.join(" ", o.getFname(), o.getLname()))
				.findFirst().orElse("");
	}

	private int getOfficerMSID(String offType) {
		return board.stream().filter(bm -> offType.equals(bm.getOfficer_type()))
				.map(BoardDTO::getMs_id)
				.findFirst().orElse(0);
	}
	
	private void getBoard(String year, VBox fillHBox) {
		Text yearText = new Text(year);
		yearText.getStyleClass().add("year-bod-text");
		fillHBox.getChildren().add(yearText);
		board.stream().filter(bm -> bm.getBoard_year().equals(year))
				.forEach(bm -> {
					Text boardMember = new Text((bm.getFname() + " " + bm.getLname()));
					boardMember.getStyleClass().add("bod-names-text");
					fillHBox.getChildren()
							.add(setMouseListener(boardMember, bm.getMs_id()));
				});
	}
	
	private Text setMouseListener(Text text, int msid) {
		Color color = (Color) text.getFill();
		if(color == Color.CORNFLOWERBLUE) {
			text.setOnMouseExited(ex -> text.setFill(Color.CORNFLOWERBLUE));
		} else {
			text.setOnMouseExited(ex -> text.setFill(Color.BLACK));
		}
		text.setOnMouseEntered(en -> text.setFill(Color.RED));

		text.setOnMouseClicked(e -> {
			if (e.getClickCount() == 2)  {
				Launcher.createMembershipTabForBOD(msid, selectedYear);
			}
		});
		return text;
	}

	static class Pair<T1, T2> {
		private final T1 key;
		private final T2 value;

		public Pair(T1 first, T2 second) {
			this.key = first;
			this.value = second;
		}

		public T1 getKey() {
			return key;
		}

		public T2 getValue() {
			return value;
		}
	}
}
