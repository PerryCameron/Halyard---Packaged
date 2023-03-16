package com.ecsail.connection;

import com.ecsail.BaseApplication;
import com.ecsail.DataBase;
import com.ecsail.FileIO;
import com.ecsail.dto.LoginDTO;
import com.ecsail.models.MainModel;
import com.ecsail.views.tabs.TabLogin;
import com.ecsail.views.tabs.welcome.HBoxWelcome;
import com.ecsail.views.tabs.welcome.TabWelcome;
import com.ecsail.widgetfx.HBoxWidgets;
import com.ecsail.widgetfx.InsetWidget;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class ConnectDatabase {

	private MainModel mainModel = new MainModel();
	private double titleBarHeight;
	private int localSqlPort;
	private ObservableList<String> choices = FXCollections.observableArrayList();
	// used in class methods
	private CheckBox defaultCheck;
	private CheckBox useSshTunnel;
	private ComboBox<String> hostName;
	private TextField localSqlPortText;
	private TextField hostNameField;
	private TextField sshUser;
	private TextField knownHost;

	private TextField publicKey;
	private TextField userName;
	private TextField passWord;
	public static final String BLUE = "\033[0;34m";    // BLUE

	public static Stage logonStage;
	Stage primaryStage;



	RotateTransition rotateTransition;


	public ConnectDatabase(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.localSqlPort = mainModel.getCurrentLogon().getLocalSqlPort();
		loadHostsInComboBox();
		// makes it look nice, tab not for anything useful
			BaseApplication.tabPane.getTabs().add(new TabLogin("Log in"));
		displayLogOn(primaryStage);
	}

	public Connection getSqlConnection() {
		return mainModel.getSqlConnection();
	}

	public PortForwardingL getSshConnection() {
		return mainModel.getSshConnection();
	}


	public void displayLogOn(Stage primaryStage) {
		int width = 500;
		int height = 200;
		logonStage = new Stage();
		logonStage.setTitle("Login");
		Image loginLogo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ships_wheel.png")));
		ImageView logo = new ImageView(loginLogo);
		rotateTransition = new RotateTransition(Duration.seconds(5), logo);
		rotateTransition.setByAngle(360);
		rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
		rotateTransition.setInterpolator(Interpolator.LINEAR);

		VBox vboxBlue = new VBox();
		VBox vboxLeft = new VBox(); // this creates a pink border around the table
		Pane loginPane = new Pane();
		Scene secondScene = new Scene(loginPane, width, height);
		secondScene.getStylesheets().add("css/dark/dark.css");
		
		HBox errorHBox = new HBox();  // for displaying errors above
		HBox hboxUserLabel = HBoxWidgets.createHBox(Pos.CENTER_LEFT, 90);
		HBox hboxUserText = new HBox();
		HBox hboxPassLabel = HBoxWidgets.createHBox(Pos.CENTER_LEFT, 90);
		HBox hboxPassText = new HBox();
		HBox hboxHostLabel = HBoxWidgets.createHBox(Pos.CENTER_LEFT, 90);
		HBox hboxHostText = new HBox();
		HBox hboxHostLabel2 = HBoxWidgets.createHBox(Pos.CENTER_LEFT, 90);
		HBox hboxHostText2 = new HBox();
		HBox hboxSshUserLabel = HBoxWidgets.createHBox(Pos.CENTER_LEFT, 90, InsetWidget.getCommonInsets(5));
		HBox hboxSshPassLabel = HBoxWidgets.createHBox(Pos.CENTER_LEFT, 90, InsetWidget.getCommonInsets(5));
		HBox hboxPortLabel = HBoxWidgets.createHBox(Pos.CENTER_LEFT, 90, InsetWidget.getCommonInsets(5));
		HBox hboxSshUserText = HBoxWidgets.createHBox(InsetWidget.getCommonInsets(5));
		HBox hboxSshPassText = HBoxWidgets.createHBox(InsetWidget.getCommonInsets(5));
		HBox hboxPortText = HBoxWidgets.createHBox(Pos.CENTER_LEFT,200,new Insets(5,5,5,0),20);
		HBox infoBox1 = new HBox();
		HBox infoBox2 = HBoxWidgets.createHBox(InsetWidget.getCommonInsets(5));
		HBox infoBox3 = HBoxWidgets.createHBox(InsetWidget.getCommonInsets(5));
		HBox infoBox4 = new HBox();
		HBox infoBox5 = HBoxWidgets.createHBox(Pos.CENTER,InsetWidget.getCommonInsets(5));
		HBox infoBox6 = new HBox();
		HBox infoBox7 = new HBox();	
		HBox infoBox8 = new HBox();
		HBox buttonBox1 = HBoxWidgets.createHBox(InsetWidget.getLeftInset(35.0),10);
		HBox buttonBox2 = HBoxWidgets.createHBox(InsetWidget.getLeftInset(60.0),10);
		HBox buttonBox3 = new HBox();
		HBox addBox = new HBox();
		HBox mainHBox = new HBox();
		VBox vboxRight = new VBox();

		infoBox1.setPadding(new Insets(20,5,5,5));

		infoBox8.setPadding(new Insets(15,5,25,5));
		vboxRight.setPadding(new Insets(20,20,0,20));
		vboxLeft.setPadding(new Insets(0,0,0,15));
		vboxBlue.setPadding(new Insets(10,10,10,10));

		infoBox4.setAlignment(Pos.CENTER_LEFT);
		addBox.setAlignment(Pos.CENTER_LEFT);

		this.hostName = new ComboBox<>(choices);
		this.defaultCheck = new CheckBox("Default Login");

		this.useSshTunnel = new CheckBox("Use ssh tunnel");
		this.localSqlPortText = new TextField();
		localSqlPortText.textProperty().bindBidirectional(mainModel.localSqlPortProperty());
		this.hostNameField = new TextField();
		hostNameField.textProperty().bindBidirectional(mainModel.hostProperty());
		this.sshUser = new TextField();
		this.knownHost = new TextField();
		this.userName = new TextField();
		userName.textProperty().bindBidirectional(mainModel.userProperty());
		this.passWord = new PasswordField();
		passWord.textProperty().bindBidirectional(mainModel.passProperty());
		Button loginButton = new Button("Login");
		Button cancelButton1 = new Button("Cancel");
		Button cancelButton2 = new Button("Cancel");
		Button cancelButton3 = new Button("Cancel");
		Button saveButton1 = new Button("Save");
		Button saveButton2 = new Button("Save");
		Button deleteButton = new Button("Delete");
		Text newConnectText = new Text("New");
		Text editConnectText = new Text("Edit");
		
		///////////////////// ATTRIBUTES //////////////////////////
		
		/*  // for testing
		infoBox8.setStyle("-fx-background-color: #c5c7c1;");  // gray
		infoBox1.setStyle("-fx-background-color: #4d6955;");  //green
		infoBox2.setStyle("-fx-background-color: #feffab;");  // yellow
		infoBox3.setStyle("-fx-background-color: #e83115;");  // red
		infoBox4.setStyle("-fx-background-color: #201ac9;");  // blue
		infoBox5.setStyle("-fx-background-color: #e83115;");  // purple
		infoBox6.setStyle("-fx-background-color: #15e8e4;");  // light blue
		infoBox7.setStyle("-fx-background-color: #e89715;");  // orange
		*/
//		Insets fivePad = new Insets(5,5,5,5);



		userName.setPromptText("Username");
		passWord.setPromptText("Password");

		//Pane secondaryLayout = new Pane();
//		Image mainIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ECSClogo4.png")));

		localSqlPortText.setText("3306");
		localSqlPortText.setPrefWidth(60);
		
		userName.setPrefWidth(200);
		passWord.setPrefWidth(200);
		hostName.setPrefWidth(200);
		hostNameField.setPrefWidth(200);
		sshUser.setPrefWidth(200);
		knownHost.setPrefWidth(200);

		vboxBlue.setPrefWidth(width);

		buttonBox2.setSpacing(10);
		addBox.setSpacing(15);

		logonStage.setAlwaysOnTop(true);
		newConnectText.setFill(Color.CORNFLOWERBLUE);
		editConnectText.setFill(Color.CORNFLOWERBLUE);
		
		if(mainModel.getCurrentLogon() != null) {  // only true if starting for first time
			populateFields();
		}
		mainHBox.setId("box-pink");
		vboxBlue.setId("box-frame-dark");
		
		Platform.runLater(() -> userName.requestFocus());
		
		///////////////////// LISTENERS //////////////////////////  
		
		vboxBlue.heightProperty().addListener((obs, oldVal, newVal) -> logonStage.setHeight((double)newVal + titleBarHeight));
		
		setMouseListener(newConnectText);
		
		setMouseListener(editConnectText);
		
		// when host name combo box changes
		hostName.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			mainModel.setCurrentLogon(mainModel.getLogins().get(FileIO.getSelectedHost(options.getValue(),mainModel.getLogins())));
			populateFields();
        });
		
		// creates screen to add new host
		newConnectText.setOnMouseClicked(e -> {
			if (e.getClickCount() == 1) {
				clearFields();
				infoBox3.getChildren().clear();
				infoBox3.getChildren().addAll(hboxHostLabel2, hboxHostText2);
				infoBox4.getChildren().addAll(hboxPortLabel, hboxPortText);
				infoBox5.getChildren().add(useSshTunnel);
				infoBox6.getChildren().addAll(hboxSshUserLabel,hboxSshUserText);
				infoBox7.getChildren().addAll(hboxSshPassLabel,hboxSshPassText);
				infoBox8.getChildren().clear();
				infoBox8.getChildren().add(buttonBox3);
			}
		});
		
		// edits currently selected host
		editConnectText.setOnMouseClicked(e -> {
			if (e.getClickCount() == 1) {
					//infoBox5.setPadding(new Insets(5,5,5,5));
					infoBox3.getChildren().clear();
					infoBox3.getChildren().addAll(hboxHostLabel2, hboxHostText2);
					infoBox4.getChildren().addAll(hboxPortLabel, hboxPortText);
					infoBox5.getChildren().add(useSshTunnel);
					infoBox6.getChildren().addAll(hboxSshUserLabel,hboxSshUserText);
					infoBox7.getChildren().addAll(hboxSshPassLabel,hboxSshPassText);
					infoBox8.getChildren().clear();
					infoBox8.getChildren().add(buttonBox2);
			}
		});
		
		// takes you back to original screen
		cancelButton2.setOnAction((event) -> {
			populateFields();
			infoBox3.getChildren().clear();
			infoBox3.getChildren().addAll(hboxHostLabel, hboxHostText);
			infoBox4.getChildren().clear();
			infoBox5.getChildren().clear();
			infoBox5.setPadding(Insets.EMPTY);
			infoBox6.getChildren().clear();
			infoBox7.getChildren().clear();
			infoBox8.getChildren().clear();
			infoBox8.getChildren().addAll(addBox, buttonBox1);
		});
		
		/// duplicated cancelButton 3 for simplicity
		cancelButton3.setOnAction((event) -> {
			infoBox3.getChildren().clear();
		    infoBox3.getChildren().addAll(hboxHostLabel, hboxHostText);
			infoBox4.getChildren().clear();
			infoBox5.getChildren().clear();
			infoBox5.setPadding(Insets.EMPTY);
			infoBox6.getChildren().clear();
			infoBox7.getChildren().clear();
			infoBox8.getChildren().clear();
			infoBox8.getChildren().addAll(addBox, buttonBox1);
		});

		loginButton.setOnAction((event) -> {
			System.out.println("Starting thread");
			rotateTransition.play();
			connectToServer();
        });

		// deletes log on from list
		deleteButton.setOnAction((event) -> {
				int element = FileIO.getSelectedHost(mainModel.getCurrentLogon().getHost(), mainModel.getLogins());
				if (element >= 0) {
					mainModel.getLogins().remove(element);
					FileIO.saveLoginObjects(mainModel.getLogins());
					removeHostFromComboBox(hostNameField.getText());
					// should probably set combo box to default here
	            	cancelButton2.fire(); // refresh login back to original
				} else {
					System.out.println("need to build error for removing element");
				}
			});
		
		// saves new login object
        saveButton1.setOnAction((event) -> {
            	mainModel.getLogins().add(new LoginDTO(Integer.parseInt(localSqlPortText.getText()),
						3306,22, hostNameField.getText(), mainModel.getUser(),
						passWord.getText(), sshUser.getText(),knownHost.getText(),
						System.getProperty("user.home") + "/.ssh/known_hosts" ,
						System.getProperty("user.home") + "/.ssh/id_rsa",
						defaultCheck.isSelected(), useSshTunnel.isSelected()));
            	FileIO.saveLoginObjects(mainModel.getLogins());
            	choices.add(hostNameField.getText());  // add new host name into combo box
            	hostName.setValue(hostNameField.getText());  // set combo box default to new host name
            	cancelButton2.fire(); // refresh login back to original
            });
        
		// saves changes to existing login object
        saveButton2.setOnAction((event) -> {
			BaseApplication.logger.info(mainModel.getCurrentLogon().getHost());
        		// get element number
            	int element = FileIO.getSelectedHost(mainModel.getCurrentLogon().getHost(), mainModel.getLogins());
            	// save hostname for later
            	String oldHost = mainModel.getCurrentLogon().getHost();
            	if(element >= 0) {  // the element exists, why wouldn't it exist
            		// change the specific login in the login list
            		mainModel.getLogins().get(element).setHost(hostNameField.getText());
					mainModel.getLogins().get(element).setUser(mainModel.getUser());
					mainModel.getLogins().get(element).setPasswd(passWord.getText());
					mainModel.getLogins().get(element).setLocalSqlPort(Integer.parseInt(localSqlPortText.getText()));
					mainModel.getLogins().get(element).setSshUser(sshUser.getText());
					mainModel.getLogins().get(element).setSshPass(knownHost.getText());
					mainModel.getLogins().get(element).setDefault(defaultCheck.isSelected());
					mainModel.getLogins().get(element).setSshForward(useSshTunnel.isSelected());
            		FileIO.saveLoginObjects(mainModel.getLogins());
            		updateHostInComboBox(oldHost, hostNameField.getText());
            		hostName.setValue(hostNameField.getText());
            		cancelButton2.fire(); // refresh login back to original
            	} else {
            		System.out.println("need to build error for non matching host here");
            	}
            });
        
        // exits program 
        cancelButton1.setOnAction((event) -> System.exit(0));

        /////////////// SET CONTENT /////////////////////
		Image mainIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/title_bar_icon.png")));
		logonStage.getIcons().add(mainIcon);
		
        hboxUserLabel.getChildren().add(new Label("Username:"));
        hboxPassLabel.getChildren().add(new Label("Password:"));
        hboxHostLabel.getChildren().add(new Label("Hostname:"));
        hboxHostLabel2.getChildren().add(new Label("Hostname:"));
        hboxSshUserLabel.getChildren().add(new Label("ssh user:"));
        hboxSshPassLabel.getChildren().add(new Label("known_hosts:"));
		hboxPortLabel.getChildren().add(new Label("SQL Port:"));
		hboxPortText.getChildren().addAll(localSqlPortText, defaultCheck);
        hboxUserText.getChildren().add(userName);
        hboxPassText.getChildren().add(passWord);
        hboxHostText.getChildren().add(hostName);
        hboxHostText2.getChildren().add(hostNameField);
        hboxSshUserText.getChildren().add(sshUser);
		hboxSshPassText.getChildren().add(knownHost);
		addBox.getChildren().addAll(newConnectText, editConnectText);
        buttonBox1.getChildren().addAll(loginButton,cancelButton1);
        buttonBox2.getChildren().addAll(saveButton2,deleteButton,cancelButton3);
        buttonBox3.getChildren().addAll(saveButton1,cancelButton2);
        
		infoBox1.getChildren().addAll(hboxUserLabel, hboxUserText);
		infoBox2.getChildren().addAll(hboxPassLabel, hboxPassText);
		infoBox3.getChildren().addAll(hboxHostLabel, hboxHostText);
        infoBox8.getChildren().addAll(addBox, buttonBox1);
        
        vboxLeft.getChildren().addAll(errorHBox,infoBox1, infoBox2, infoBox3, infoBox4, infoBox5, infoBox6, infoBox7, infoBox8);
        vboxRight.getChildren().addAll(logo);
        
		logonStage.setX(primaryStage.getX() + 260);
		logonStage.setY(primaryStage.getY() + 300);
		mainHBox.getChildren().addAll(vboxLeft, vboxRight);
		vboxBlue.getChildren().add(mainHBox);
		loginPane.getChildren().add(vboxBlue);
		logonStage.setScene(secondScene);
		logonStage.show();
		this.titleBarHeight = logonStage.getHeight() - secondScene.getHeight();
		logonStage.setHeight(vboxBlue.getHeight() + titleBarHeight);
		logonStage.setResizable(false);
	}

	///////////////  CLASS METHODS ///////////////////

	private void connectToServer() {
		Task<Boolean> connectTask = new Task<Boolean>() {
			@Override
			protected Boolean call() throws Exception {
				// Perform database connection here
				return mainModel.connect();
			}
		};
		connectTask.setOnSucceeded(event -> {
			boolean connectionSuccessful = connectTask.getValue();
			if (connectionSuccessful) {
				rotateTransition.stop();
				primaryStage.setTitle("Halyard");
				BaseApplication.tabPane.getTabs().remove(BaseApplication.tabPane.getSelectionModel().getSelectedIndex());
				BaseApplication.tabPane.getTabs().add(new TabWelcome(new HBoxWelcome()));
				showStatus();
				logonStage.close();
				// Code to execute after the task completes successfully
			} else {
				// Handle the case where the connection fails
			}
		});
		connectTask.setOnFailed(event -> {
			BaseApplication.logger.error(connectTask.getException().getMessage());
		});
		Thread thread = new Thread(connectTask);
		thread.start();
	}

	private void populateFields() {
		userName.setText(mainModel.getCurrentLogon().getUser());
		passWord.setText(mainModel.getCurrentLogon().getPasswd());
		hostName.setValue(mainModel.getCurrentLogon().getHost());
		hostNameField.setText(mainModel.getCurrentLogon().getHost());
		sshUser.setText(mainModel.getCurrentLogon().getSshUser());
		knownHost.setText(mainModel.getCurrentLogon().getKnownHostsFile());
		useSshTunnel.setSelected(mainModel.getCurrentLogon().isSshForward());
		defaultCheck.setSelected(mainModel.getCurrentLogon().isDefault());
	}
	
	private void clearFields() {
		userName.setText("");
		passWord.setText("");
		//hostName.setValue("");
		hostNameField.setText("");
		sshUser.setText("");
		knownHost.setText("");
		useSshTunnel.setSelected(true);
		defaultCheck.setSelected(false); // this needs to check other logons first
	}

	private void updateHostInComboBox(String host, String newHost) {
		int count = 0;
		int choiceWanted = 0;
		for(String ho: choices) {
			if(host.equals(ho)) {
				choiceWanted = count;
			}
			count++;
		}
		choices.set(choiceWanted, newHost);
	}
	
	private void removeHostFromComboBox(String host) {
		int count = 0;
		int choiceWanted = 0;
		for(String ho: choices) {
			if(host.equals(ho)) {
				choiceWanted = count;
			}
			count++;
		}
		choices.remove(choiceWanted);
	}
	
	private void loadHostsInComboBox() {
		for (LoginDTO l : mainModel.getLogins()) {
			this.choices.add(l.getHost());
		}
	}

	private void setMouseListener(Text text) {
		text.setOnMouseExited(ex -> text.setFill(Color.CORNFLOWERBLUE));
		text.setOnMouseEntered(en -> text.setFill(Color.RED));
	}
	
	private void showStatus() {
		Statement stmt;
		try {
			stmt = mainModel.getSqlConnection().createStatement();
			ResultSet rs = stmt.executeQuery("select @@hostname;");
		while (rs.next()) {
			BaseApplication.logger.info("Connected to " + rs.getString(1));
			BaseApplication.statusLabel.setText("(Connected) " + rs.getString(1));
		}
		stmt.close();
      } catch (SQLException e) {
			e.printStackTrace();
		}
//		 Retrieving the data
	}

	private void closeConnection() {
		getMainModel().closeDatabaseConnection();
		primaryStage.setTitle("Halyard (not connected)");
	}

	public ResultSet executeSelectQuery(String query) throws SQLException {
//		BaseApplication.logger.info(query);
		Statement stmt = mainModel.getSqlConnection().createStatement();
		if (mainModel.getCurrentLogon().isSshForward()) {
			if (!mainModel.getSshConnection().getSession().isConnected()) {
				BaseApplication.logger.error("SSH Connection is no longer connected");
				closeConnection();
			}
		}
		return stmt.executeQuery(query);
	}

	public void executeQuery(String query) throws SQLException {
		if(!query.startsWith("UPDATE db_table_changes")) // lets remove noise
		System.out.println(query);
//		BaseApplication.logger.info(query);
		Statement stmt = mainModel.getSqlConnection().createStatement();
		if (mainModel.getCurrentLogon().isSshForward()) {
			if (!mainModel.getSshConnection().getSession().isConnected()) {
				BaseApplication.logger.error("SSH Connection is no longer connected");
				closeConnection();
			}
		}
		stmt.execute(query);
		stmt.close();
		DataBase.recordChange(query);
	}

	public void closeResultSet(ResultSet rs) throws SQLException {
		if (rs.getStatement() != null) {
			rs.getStatement().close();
		}
		rs.close();
	}

	public Sftp getScp() {
		return mainModel.getScp();
	}

	public MainModel getMainModel() {
		return mainModel;
	}
}
