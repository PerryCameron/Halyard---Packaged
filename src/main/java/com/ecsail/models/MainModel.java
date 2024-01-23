package com.ecsail.models;

import com.ecsail.BaseApplication;
import com.ecsail.FileIO;
import com.ecsail.connection.AppConfig;
import com.ecsail.connection.PortForwardingL;
import com.ecsail.connection.Sftp;
import com.ecsail.dto.LoginDTO;
import com.ecsail.enums.Officer;
import com.ecsail.repository.implementations.MembershipRepositoryImpl;
import com.ecsail.repository.interfaces.MembershipRepository;
import com.jcraft.jsch.JSchException;
import javafx.collections.FXCollections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class MainModel {
    private Connection sqlConnection;
    private PortForwardingL sshConnection;
    private Sftp scp;
    private LoginDTO currentLogon;
    private List<LoginDTO> logins;
    private AppConfig appConfig;
    private MembershipRepository membershipRepository;
    
    public static Logger logger = LoggerFactory.getLogger(MainModel.class);


    public MainModel() {
        this.logins = setLogins();
        this.currentLogon = logins.get(0);
    }

    public Boolean connect()  {
        logger.info("Host is " + currentLogon.getHost());
        // create ssh tunnel
        if(currentLogon.isSshForward()) {
            logger.info("SSH tunnel enabled");
            logger.info("Attempting to connect to " + currentLogon.getHost());
            setSshConnection(new PortForwardingL(currentLogon));
            logger.info("Server Alive interval: " + sshConnection.getSession().getServerAliveInterval());
        } else {
            logger.info("SSH connection is not being used");
        }
        // this code calls the connection code
        if(createConnection(currentLogon)) {
            this.membershipRepository = new MembershipRepositoryImpl();
            BaseApplication.activeMemberships =
                    FXCollections.observableArrayList(membershipRepository.getRoster(String.valueOf(Year.now().getValue()), true));
            // gets a list of all the board positions to use throughout the application
            if(BaseApplication.boardPositions == null)
            BaseApplication.boardPositions = Officer.getPositionList();
            this.scp = new Sftp();
        } else {
            logger.error("Can not connect to SQL server");
        }
        return sshConnection.getSession().isConnected();
    };

    protected Boolean createConnection(LoginDTO loginDTO) {
        boolean successful = false;
        try {
            this.appConfig = new AppConfig();
            this.appConfig.createDataSource(loginDTO);
            setSqlConnection(appConfig.getDataSource().getConnection());
            successful = true;
            logger.info("SQL: Datasource active: " + !appConfig.getDataSource().getConnection().isClosed());
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
        return successful;
    }
    public Runnable closeConnections() {
        return () -> {
            try {
                if(!sqlConnection.isClosed()) {
                    logger.info("Attempting to close SQL connection");
                    sqlConnection.close();
                    logger.info("SQL: Datasource active: " + !appConfig.getDataSource().getConnection().isClosed());
                    sqlConnection = null;
                } else logger.info("SQL: No active connection to disconnect from");
            } catch (SQLException e) {
                logger.error("SQL: Can not close connection: " + e.getMessage());
                e.printStackTrace();
            }
            // if ssh is connected then disconnect
            if (sshConnection != null && sshConnection.getSession().isConnected()) {
                try {
                    if(sshConnection.getSession().isConnected()) {
                        logger.info("SSH: Attempting to close ip-tunnel");
                        sshConnection.getSession().delPortForwardingL(3306);
                        sshConnection.getSession().disconnect();
                        logger.info("SSH: ip-tunnel closed");
                    } else logger.info("SSH: There is no active ip-tunnel to disconnect from");
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

    public List<LoginDTO> setLogins() {
        List<LoginDTO> loginDTOS = new ArrayList<>();
        if (FileIO.hostFileExists())
            FileIO.openLoginObjects(loginDTOS);
        else
            // we are starting application for the first time
            loginDTOS.add(new LoginDTO(3306,3306, 22, "", "", "", "",
                    "", "ECSC_SQL",System.getProperty("user.home") + "/.ssh/known_hosts",
                    System.getProperty("user.home") + "/.ssh/id_rsa", false, false));
        return loginDTOS;
    }
}
