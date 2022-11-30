package com.ecsail;

import com.ecsail.enums.Officer;
import com.ecsail.gui.tabs.welcome.HBoxWelcome;
import com.ecsail.gui.tabs.TabLogin;
import com.ecsail.gui.tabs.welcome.TabWelcome;
import com.ecsail.sql.select.SqlMembershipList;
import com.ecsail.structures.Object_Login;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.sql.*;
import java.util.Objects;

public class ConnectDatabase {

	public static Connection sqlConnection;
	public PortForwardingL sshConnection;
	private double titleBarHeight;
	private Object_Login currentLogon;
	private String port;
	private ObservableList<String> choices = FXCollections.observableArrayList();
	private String exception = "";
	// used in class methods
	CheckBox defaultCheck;
	CheckBox useSshTunnel;
	ComboBox<String> hostName;
	TextField portText;
	TextField hostNameField;
	TextField sshUser;
	TextField sshPass;
	TextField userName;
	TextField passWord;
	public static final String BLUE = "\033[0;34m";    // BLUE

	public static Stage logonStage;
	Stage primaryStage;


	public ConnectDatabase(Stage primaryStage) {
		this.primaryStage = primaryStage;

		if (FileIO.hostFileExists())
			FileIO.openLoginObjects();
		else
			// we are starting application for the first time
			FileIO.logins.add(new Object_Login("", "", "", "", "", "", false, false));
		// our default login will be the first in the array
		this.currentLogon = FileIO.logins.get(0);
		this.port = currentLogon.getPort();
		loadHostsInComboBox();
		// makes it look nice, tab not for anything useful
			BaseApplication.tabPane.getTabs().add(new TabLogin("Log in"));
		displayLogOn(primaryStage);
	}

	public static Connection getSqlConnection() {
		return sqlConnection;
	}

	public PortForwardingL getSshConnection() {
		return sshConnection;
	}


	public void displayLogOn(Stage primaryStage) {
		int width = 500;
		int height = 200;
		logonStage = new Stage();
		logonStage.setTitle("Login");
		Image ecscLogo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ecscloginlogo.png")));
		ImageView logo = new ImageView(ecscLogo);
		VBox vboxBlue = new VBox();
		VBox vboxLeft = new VBox(); // this creates a pink border around the table
		Pane loginPane = new Pane();
		Scene secondScene = new Scene(loginPane, width, height);
		secondScene.getStylesheets().add("css/dark/dark.css");
		
		HBox errorHBox = new HBox();  // for displaying errors above
		HBox vboxUserLabel = new HBox();
		HBox vboxUserText = new HBox();
		HBox vboxPassLabel = new HBox();
		HBox vboxPassText = new HBox();
		HBox vboxHostLabel = new HBox();
		HBox vboxHostText = new HBox();
		HBox vboxHostLabel2 = new HBox();
		HBox vboxHostText2 = new HBox();
		HBox vboxSshUserLabel = new HBox();
		HBox vboxSshUserText = new HBox();
		HBox vboxSshPassLabel = new HBox();
		HBox vboxSshPassText = new HBox();
		HBox vboxPortLabel = new HBox();
		HBox vboxPortText = new HBox();
		HBox infoBox1 = new HBox();
		HBox infoBox2 = new HBox(); 
		HBox infoBox3 = new HBox();
		HBox infoBox4 = new HBox();
		HBox infoBox5 = new HBox();	
		HBox infoBox6 = new HBox();
		HBox infoBox7 = new HBox();	
		HBox infoBox8 = new HBox();
		HBox buttonBox1 = new HBox(); // for login and cancel buttons
		HBox buttonBox2 = new HBox(); // for save and cancel buttons
		HBox buttonBox3 = new HBox(); // for 
		HBox addBox = new HBox();
		HBox mainHBox = new HBox();
		VBox vboxRight = new VBox();
		
		this.hostName = new ComboBox<>(choices);
		this.defaultCheck = new CheckBox("Default Login");
		this.useSshTunnel = new CheckBox("Use ssh tunnel");
		this.portText = new TextField();
		this.hostNameField = new TextField();
		this.sshUser = new TextField();
		this.sshPass = new PasswordField();
		this.userName = new TextField();
		this.passWord = new PasswordField();
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
		Insets fivePad = new Insets(5,5,5,5);
		infoBox1.setPadding(new Insets(20,5,5,5));
		infoBox2.setPadding(fivePad);
		infoBox3.setPadding(fivePad);
		infoBox8.setPadding(new Insets(15,5,25,5));
		buttonBox1.setPadding(new Insets(0,0,0,35));
		buttonBox2.setPadding(new Insets(0,0,0,60));
		vboxRight.setPadding(new Insets(20,20,0,20));
		vboxLeft.setPadding(new Insets(0,0,0,15));
		vboxBlue.setPadding(new Insets(10,10,10,10));
		infoBox5.setPadding(fivePad);
		vboxSshUserLabel.setPadding(fivePad);
		vboxSshUserText.setPadding(fivePad);
		vboxSshPassLabel.setPadding(fivePad);
		vboxSshPassText.setPadding(fivePad);
		vboxPortText.setPadding(new Insets(5,5,5,0));
		vboxPortLabel.setPadding(fivePad);
		
		vboxUserLabel.setAlignment(Pos.CENTER_LEFT);
		vboxPassLabel.setAlignment(Pos.CENTER_LEFT);
		vboxHostLabel.setAlignment(Pos.CENTER_LEFT);
		vboxHostLabel2.setAlignment(Pos.CENTER_LEFT);
		vboxSshUserLabel.setAlignment(Pos.CENTER_LEFT);
		vboxSshPassLabel.setAlignment(Pos.CENTER_LEFT);
		vboxPortLabel.setAlignment(Pos.CENTER_LEFT);
		vboxPortText.setAlignment(Pos.CENTER_LEFT);
		infoBox4.setAlignment(Pos.CENTER_LEFT);
		addBox.setAlignment(Pos.CENTER_LEFT);
		infoBox5.setAlignment(Pos.CENTER);

		userName.setPromptText("Username");
		passWord.setPromptText("Password");

		//Pane secondaryLayout = new Pane();
		Image mainIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ECSClogo4.png")));

		portText.setText("3306");
		vboxUserLabel.setPrefWidth(90);
		vboxPassLabel.setPrefWidth(90);
		vboxHostLabel.setPrefWidth(90);
		vboxHostLabel2.setPrefWidth(90);
		vboxSshUserLabel.setPrefWidth(90);
		vboxSshPassLabel.setPrefWidth(90);
		vboxPortLabel.setPrefWidth(90);
		portText.setPrefWidth(60);
		
		userName.setPrefWidth(200);
		passWord.setPrefWidth(200);
		hostName.setPrefWidth(200);
		hostNameField.setPrefWidth(200);
		sshUser.setPrefWidth(200);
		sshPass.setPrefWidth(200);
		vboxPortText.setPrefWidth(200);
		vboxBlue.setPrefWidth(width);

		buttonBox1.setSpacing(10);
		buttonBox2.setSpacing(10);
		addBox.setSpacing(15);
		vboxPortText.setSpacing(20);	
		logonStage.setAlwaysOnTop(true);
		newConnectText.setFill(Color.CORNFLOWERBLUE);
		editConnectText.setFill(Color.CORNFLOWERBLUE);
		
		if(currentLogon != null) {  // only true if starting for first time
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
			currentLogon = FileIO.logins.get(FileIO.getSelectedHost(options.getValue()));
			populateFields();
        });
		
		// creates screen to add new host
		newConnectText.setOnMouseClicked(e -> {
			if (e.getClickCount() == 1) {
				clearFields();
				infoBox3.getChildren().clear();
				infoBox3.getChildren().addAll(vboxHostLabel2, vboxHostText2);
				infoBox4.getChildren().addAll(vboxPortLabel, vboxPortText);
				infoBox5.getChildren().add(useSshTunnel);
				infoBox6.getChildren().addAll(vboxSshUserLabel,vboxSshUserText);
				infoBox7.getChildren().addAll(vboxSshPassLabel,vboxSshPassText);
				infoBox8.getChildren().clear();
				infoBox8.getChildren().add(buttonBox3);
			}
		});
		
		// edits currently selected host
		editConnectText.setOnMouseClicked(e -> {
			if (e.getClickCount() == 1) {
					//infoBox5.setPadding(new Insets(5,5,5,5));
					infoBox3.getChildren().clear();
					infoBox3.getChildren().addAll(vboxHostLabel2, vboxHostText2);
					infoBox4.getChildren().addAll(vboxPortLabel, vboxPortText);
					infoBox5.getChildren().add(useSshTunnel);
					infoBox6.getChildren().addAll(vboxSshUserLabel,vboxSshUserText);
					infoBox7.getChildren().addAll(vboxSshPassLabel,vboxSshPassText);
					infoBox8.getChildren().clear();
					infoBox8.getChildren().add(buttonBox2);
			}
		});
		
		// takes you back to original screen
		cancelButton2.setOnAction((event) -> {
			populateFields();
			infoBox3.getChildren().clear();
			infoBox3.getChildren().addAll(vboxHostLabel, vboxHostText);
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
		    infoBox3.getChildren().addAll(vboxHostLabel, vboxHostText);
			infoBox4.getChildren().clear();
			infoBox5.getChildren().clear();
			infoBox5.setPadding(Insets.EMPTY);
			infoBox6.getChildren().clear();
			infoBox7.getChildren().clear();
			infoBox8.getChildren().clear();
			infoBox8.getChildren().addAll(addBox, buttonBox1);
		});
		
        loginButton.setOnAction((event) -> {
        		String user = userName.getText();
        		String pass = passWord.getText();
        		String host = hostName.getValue();
        		BaseApplication.logger.info("Host is " + host);
        		String sUser = sshUser.getText();
        		String sPass = sshPass.getText();
        		String loopback = "127.0.0.1";
        		// create ssh tunnel
        		if(currentLogon.isSshForward()) {
        			BaseApplication.logger.info("SSH tunnel enabled");
        			this.sshConnection = new PortForwardingL(host,loopback,3306,3306,sUser,sPass);
//					setServerAliveInterval();
        			BaseApplication.logger.info("Server Alive interval: " + sshConnection.getSession().getServerAliveInterval());
        		} else
        			BaseApplication.logger.info("SSH connection is not being used");
        		// create mysql login
				primaryStage.setTitle("ECSC Membership Database");
        		if(createConnection(user, pass, loopback, port)) {
        		BaseApplication.activeMemberships = SqlMembershipList.getRoster(BaseApplication.selectedYear, true);
				// gets a list of all the board positions to use throughout the application
				BaseApplication.boardPositions = Officer.getPositionList();
        		logonStage.close();
        		} else {
					BaseApplication.logger.error(exception);
        		}
        });
        
        // deletes log on from list
		deleteButton.setOnAction((event) -> {
				int element = FileIO.getSelectedHost(currentLogon.getHost());
				if (element >= 0) {
					FileIO.logins.remove(element);
					FileIO.saveLoginObjects();
					removeHostFromComboBox(hostNameField.getText());
					// should probably set combo box to default here
	            	cancelButton2.fire(); // refresh login back to original
				} else {
					System.out.println("need to build error for removing element");
				}
			});
		
		// saves new login object
        saveButton1.setOnAction((event) -> {
            	FileIO.logins.add(new Object_Login(portText.getText(), hostNameField.getText(), userName.getText(), passWord.getText(), sshUser.getText(),sshPass.getText(), defaultCheck.isSelected(), useSshTunnel.isSelected()));
            	FileIO.saveLoginObjects();
            	choices.add(hostNameField.getText());  // add new host name into combo box
            	hostName.setValue(hostNameField.getText());  // set combo box default to new host name
            	cancelButton2.fire(); // refresh login back to original
            });
        
		// saves changes to existing login object
        saveButton2.setOnAction((event) -> {
			BaseApplication.logger.info(currentLogon.getHost());
        		// get element number
            	int element = FileIO.getSelectedHost(currentLogon.getHost());
            	// save hostname for later
            	String oldHost = currentLogon.getHost();
            	if(element >= 0) {  // the element exists, why wouldn't it exist
            		// change the specific login in the login list
            		FileIO.logins.get(element).setHost(hostNameField.getText());
            		FileIO.logins.get(element).setUser(userName.getText());
            		FileIO.logins.get(element).setPasswd(passWord.getText());
            		FileIO.logins.get(element).setPort(portText.getText());
            		FileIO.logins.get(element).setSshUser(sshUser.getText());
            		FileIO.logins.get(element).setSshPass(sshPass.getText());
            		FileIO.logins.get(element).setDefault(defaultCheck.isSelected());
            		FileIO.logins.get(element).setSshForward(useSshTunnel.isSelected());

            		FileIO.saveLoginObjects();
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
		logonStage.getIcons().add(mainIcon);
		
        vboxUserLabel.getChildren().add(new Label("Username:"));
        vboxPassLabel.getChildren().add(new Label("Password:"));
        vboxHostLabel.getChildren().add(new Label("Hostname:"));
        vboxHostLabel2.getChildren().add(new Label("Hostname:"));
        vboxSshUserLabel.getChildren().add(new Label("ssh user:"));
        vboxSshPassLabel.getChildren().add(new Label("ssh pass:"));
		vboxPortLabel.getChildren().add(new Label("Port:"));
		vboxPortText.getChildren().addAll(portText, defaultCheck);
        vboxUserText.getChildren().add(userName);
        vboxPassText.getChildren().add(passWord);
        vboxHostText.getChildren().add(hostName);
        vboxHostText2.getChildren().add(hostNameField);
        vboxSshUserText.getChildren().add(sshUser);
		vboxSshPassText.getChildren().add(sshPass);
		addBox.getChildren().addAll(newConnectText, editConnectText);
        buttonBox1.getChildren().addAll(loginButton,cancelButton1);
        buttonBox2.getChildren().addAll(saveButton2,deleteButton,cancelButton3);
        buttonBox3.getChildren().addAll(saveButton1,cancelButton2);
        
		infoBox1.getChildren().addAll(vboxUserLabel, vboxUserText);
		infoBox2.getChildren().addAll(vboxPassLabel, vboxPassText);
		infoBox3.getChildren().addAll(vboxHostLabel, vboxHostText);
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

//		BaseApplication.logger.info(HalyardPaths.getOperatingSystem() + " Detected");
		this.titleBarHeight = logonStage.getHeight() - secondScene.getHeight();
		logonStage.setHeight(vboxBlue.getHeight() + titleBarHeight);
		logonStage.setResizable(false);
	}

	///////////////  CLASS METHODS ///////////////////
	private void populateFields() {
		userName.setText(currentLogon.getUser());
		passWord.setText(currentLogon.getPasswd());
		hostName.setValue(currentLogon.getHost());
		hostNameField.setText(currentLogon.getHost());
		sshUser.setText(currentLogon.getSshUser());
		sshPass.setText(currentLogon.getSshPass());
		useSshTunnel.setSelected(currentLogon.isSshForward());
		defaultCheck.setSelected(currentLogon.isDefault());
	}
	
	private void clearFields() {
		userName.setText("");
		passWord.setText("");
		//hostName.setValue("");
		hostNameField.setText("");
		sshUser.setText("");
		sshPass.setText("");
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
		for (Object_Login l : FileIO.logins) {
			this.choices.add(l.getHost());
		}
	}

	protected Boolean createConnection(String user, String password, String ip, String port) {
		boolean successful = false;
		String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
		String DB_URL = "jdbc:mysql://" + ip + ":" + port + "/ECSC_SQL?autoReconnect=true&useSSL=false&serverTimezone=UTC";
		try {
			Class.forName(JDBC_DRIVER);
			BaseApplication.logger.info("Connection: " + DB_URL);
			sqlConnection = DriverManager.getConnection(DB_URL, user, password);
			BaseApplication.tabPane.getTabs()
			.remove(BaseApplication.tabPane.getSelectionModel().getSelectedIndex());
			//vboxGrey.getChildren().add();
			BaseApplication.tabPane.getTabs().add(new TabWelcome(new HBoxWelcome()));
			showStatus();
			successful = true;
			// Creating a Statement object
		} catch (ClassNotFoundException | SQLException e) {
			BaseApplication.logger.error(e.getMessage());
		}
		return successful;
	}
	
	private void setMouseListener(Text text) {
		text.setOnMouseExited(ex -> text.setFill(Color.CORNFLOWERBLUE));
		text.setOnMouseEntered(en -> text.setFill(Color.RED));
	}
	
	private void showStatus() {
		Statement stmt;
		try {
			stmt = sqlConnection.createStatement();
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
		BaseApplication.closeDatabaseConnection();
		primaryStage.setTitle("ECSC Membership Database (not connected)");
	}

	public ResultSet executeSelectQuery(String query) throws SQLException {
//		BaseApplication.logger.info(query);
		Statement stmt = ConnectDatabase.sqlConnection.createStatement();
		if (currentLogon.isSshForward()) {
			if (!sshConnection.getSession().isConnected()) {
				BaseApplication.logger.error("SSH Connection is no longer connected");
				closeConnection();
			}
		}
		return stmt.executeQuery(query);
	}

	public void executeQuery(String query) throws SQLException {
		BaseApplication.logger.info(query);
		Statement stmt = ConnectDatabase.sqlConnection.createStatement();
		if (currentLogon.isSshForward()) {
			if (!sshConnection.getSession().isConnected()) {
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
}
