package com.ecsail.models;

import com.ecsail.BaseApplication;
import com.ecsail.FileIO;
import com.ecsail.connection.PortForwardingL;
import com.ecsail.connection.Sftp;
import com.ecsail.dto.LoginDTO;
import com.jcraft.jsch.JSchException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainModel {
    private Connection sqlConnection;
    private PortForwardingL sshConnection;
    private Sftp scp;
    private LoginDTO currentLogon;
    private  List<LoginDTO> logins = new ArrayList<>();

    public MainModel() {
        this.logins = setLogins();
        this.currentLogon = logins.get(0);
    }

    public Runnable closeDatabaseConnection() {
        try {
            sqlConnection.close();
            BaseApplication.logger.info("SQL: Connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // if ssh is connected then disconnect
        if(sshConnection != null)
            if(sshConnection.getSession().isConnected()) {
                try {
                    sshConnection.getSession().delPortForwardingL(3306);
                    sshConnection.getSession().disconnect();
                    BaseApplication.logger.info("SSH: port forwarding closed");
                } catch (JSchException e) {
                    e.printStackTrace();
                }
            }
        return null;
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
