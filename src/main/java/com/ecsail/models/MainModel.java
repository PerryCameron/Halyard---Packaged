package com.ecsail.models;

import com.ecsail.BaseApplication;
import com.ecsail.FileIO;
import com.ecsail.connection.AppConfig;
import com.ecsail.connection.PortForwardingL;
import com.ecsail.connection.Sftp;
import com.ecsail.dto.LoginDTO;
import com.ecsail.enums.Officer;
import com.ecsail.sql.select.SqlMembershipList;
import com.jcraft.jsch.JSchException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainModel {
    private Connection sqlConnection;
    private PortForwardingL sshConnection;
    private Sftp scp;
    private LoginDTO currentLogon;
    private  List<LoginDTO> logins;
    private StringProperty user = new SimpleStringProperty();
    private StringProperty pass = new SimpleStringProperty();
    private StringProperty host = new SimpleStringProperty();
    private StringProperty localSqlPort = new SimpleStringProperty();
    private AppConfig appConfig;

    public MainModel() {
        this.logins = setLogins();
        this.currentLogon = logins.get(0);
    }

    public Boolean connect()  {
        BaseApplication.logger.info("Host is " + host.get());
        String loopback = "127.0.0.1";
        // create ssh tunnel
        if(currentLogon.isSshForward()) {
            BaseApplication.logger.info("SSH tunnel enabled");
            BaseApplication.logger.info("Attempting to connect to " + host.get());
            System.out.println("Time to port forward");
            setSshConnection(new PortForwardingL(currentLogon));
            BaseApplication.logger.info("Server Alive interval: " + sshConnection.getSession().getServerAliveInterval());
        } else {
            BaseApplication.logger.info("SSH connection is not being used");
        }
        if(createConnection(user.get(), pass.get(), loopback, Integer.parseInt(localSqlPort.get()))) {
            BaseApplication.activeMemberships = SqlMembershipList.getRoster(BaseApplication.selectedYear, true);
            // gets a list of all the board positions to use throughout the application
            BaseApplication.boardPositions = Officer.getPositionList();
            this.scp = new Sftp();
        } else {
            BaseApplication.logger.error("Can not connect to SQL server");
        }
        return sshConnection.getSession().isConnected();
    };

    protected Boolean createConnection(String user, String password, String ip, int port) {
        boolean successful = false;
        try {
            this.appConfig = new AppConfig();
            this.appConfig.createDataSource(ip,port,user,password,"ecsailor_ECSC_SQL");
            setSqlConnection(appConfig.getDataSource().getConnection());
            successful = true;
            // Creating a Statement object
        } catch (Exception e) {
            BaseApplication.logger.error(String.valueOf(e));
        }
        return successful;
    }
    public Runnable closeDatabaseConnection() {
        return () -> {
            try {
                sqlConnection.close();
                BaseApplication.logger.info("SQL: Connection closed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // if ssh is connected then disconnect
            if (sshConnection != null && sshConnection.getSession().isConnected()) {
                try {
                    sshConnection.getSession().delPortForwardingL(3306);
                    sshConnection.getSession().disconnect();
                    BaseApplication.logger.info("SSH: port forwarding closed");
                } catch (JSchException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public Connection getSqlConnection() {
        return sqlConnection;
    }

    public void setSqlConnection(Connection sqlConnection) {
        this.sqlConnection = sqlConnection;
    }

    public PortForwardingL getSshConnection() {
        return sshConnection;
    }

    public void setSshConnection(PortForwardingL sshConnection) {
        this.sshConnection = sshConnection;
    }

    public Sftp getScp() {
        return scp;
    }

    public void setScp(Sftp scp) {
        this.scp = scp;
    }

    public LoginDTO getCurrentLogon() {
        return currentLogon;
    }

    public void setCurrentLogon(LoginDTO currentLogon) {
        this.currentLogon = currentLogon;
    }

    public List<LoginDTO> getLogins() {
        return logins;
    }

    public void setLogins(List<LoginDTO> logins) {
        this.logins = logins;
    }

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public String getUser() {
        return user.get();
    }

    public StringProperty userProperty() {
        return user;
    }

    public void setUser(String user) {
        this.user.set(user);
    }

    public String getPass() {
        return pass.get();
    }

    public StringProperty passProperty() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass.set(pass);
    }

    public String getHost() {
        return host.get();
    }

    public StringProperty hostProperty() {
        return host;
    }

    public void setHost(String host) {
        this.host.set(host);
    }

    public String getLocalSqlPort() {
        return localSqlPort.get();
    }

    public StringProperty localSqlPortProperty() {
        return localSqlPort;
    }

    public void setLocalSqlPort(String localSqlPort) {
        this.localSqlPort.set(localSqlPort);
    }

    public List<LoginDTO> setLogins() {
        List<LoginDTO> loginDTOS = new ArrayList<>();
        if (FileIO.hostFileExists())
            FileIO.openLoginObjects(loginDTOS);
        else
            // we are starting application for the first time
            loginDTOS.add(new LoginDTO(3306,3306, 22, "", "", "", "",
                    "", System.getProperty("user.home") + "/.ssh/known_hosts",
                    System.getProperty("user.home") + "/.ssh/id_rsa", false, false));
        return loginDTOS;
    }
}
