package com.ecsail.connection;

import com.ecsail.BaseApplication;
import com.ecsail.dto.LoginDTO;
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

// https://dentrassi.de/2015/07/13/programmatically-adding-a-host-key-with-jsch/
public class PortForwardingL {

    public static Logger logger = LoggerFactory.getLogger(PortForwardingL.class);
    static String passwd;
    private Session session;
    private JSch jsch = new JSch();
    private static final boolean usePublicKey = true;


    public PortForwardingL(LoginDTO login) {
        BaseApplication.logger.info("Connecting with public key..");
        try {
            jsch.setKnownHosts(login.getKnownHostsFile());
            jsch.addIdentity(login.getPrivateKey());
            HostKeyRepository hkr = jsch.getHostKeyRepository();
            HostKey[] hks = hkr.getHostKey();
            if (hks != null) {
                BaseApplication.logger.info("Host keys exist");
            }
            session = jsch.getSession(login.getSshUser(), login.getHost(), login.getSshPort());
            UserInfo ui = new MyUserInfo();
            session.setUserInfo(ui);
            try {
                session.connect();
            } catch (JSchException e) {
                logger.error(e.getMessage());
            }

            int assingedPort = 0;
            // this prevents exception from filling log if mysql is running locally for testing
            try {
                BaseApplication.logger.info("Attempting to bind SQL port");
                assingedPort = session.setPortForwardingL(login.getLocalSqlPort(), "127.0.0.1", login.getRemoteSqlPort());
            } catch (JSchException e) {
                BaseApplication.logger.error(e.getMessage() + " Check to see if database is running locally");
            }
            BaseApplication.logger.info("localhost:" + assingedPort + " -> " + "127.0.0.1" + ":" + login.getRemoteSqlPort());
//			this.ftp = new Sftp(jsch, session);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class MyUserInfo implements UserInfo {

        public String getPassword() {
            if(usePublicKey) return null;
            // if not using a public key we are using a password
            return passwd;
        }

        public boolean promptYesNo(String str) {
            // change to java fx
            Object[] options = {"yes", "no"};
            int foo = JOptionPane.showOptionDialog(null, str, "Warning", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            return foo == 0;
        }

        public String getPassphrase() {
            return null;
        }

        public boolean promptPassphrase(String message) {
            return false;
        }

        public boolean promptPassword(String message) {
            return false;
        }

        public void showMessage(String message) {
            /// put in a JavaFX message display here.
        }

    }

    public boolean checkSSHConnection() {
        Socket socket;
        try {
            socket = new Socket("localhost", 7);

            if (socket.isConnected()) {
                socket.close();
                return true;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public void closeSession() {
        session.disconnect();
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public JSch getJsch() {
        return jsch;
    }

    public void setJsch(JSch jsch) {
        this.jsch = jsch;
    }

//	public Sftp getFtp() {
//		return ftp;
//	}
//
//	public void setFtp(Sftp ftp) {
//		this.ftp = ftp;
//	}

}