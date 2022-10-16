package com.ecsail;

import com.jcraft.jsch.*;

import javax.swing.*;
import java.io.IOException;
import java.net.BindException;
import java.net.Socket;
import java.net.UnknownHostException;

// https://dentrassi.de/2015/07/13/programmatically-adding-a-host-key-with-jsch/
public class PortForwardingL {
	static String passwd;
	private Session session;
	private JSch jsch = new JSch();
//	private Sftp ftp;

	public PortForwardingL(String host, String rhost, int lport, int rport, String user, String password) { // int
																											// lport;
		PortForwardingL.passwd = password;

		try {

			jsch.setKnownHosts(System.getProperty("user.home") + "/.ssh/known_hosts");
			HostKeyRepository hkr = jsch.getHostKeyRepository();
			HostKey[] hks = hkr.getHostKey();
			if (hks != null) {
				BaseApplication.logger.info("Host keys exist");
				// This will print out the keys
//				for (int i = 0; i < hks.length; i++) {
//					HostKey hk = hks[i];
//					System.out.println(hk.getHost() + " " + hk.getType() + " " + hk.getFingerPrint(jsch));
//				}
//				System.out.println("");
			}

			session = jsch.getSession(user, host, 22);
			UserInfo ui = new MyUserInfo();
			session.setUserInfo(ui);
			session.connect();
			int assingedPort = session.setPortForwardingL(lport, rhost, rport);
			BaseApplication.logger.info("localhost:" + assingedPort + " -> " + rhost + ":" + rport);
//			this.ftp = new Sftp(jsch, session);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class MyUserInfo implements UserInfo {

		public String getPassword() {
			return passwd;
		}

		public boolean promptYesNo(String str) {
			// change to java fx
			Object[] options = { "yes", "no" };
			int foo = JOptionPane.showOptionDialog(null, str, "Warning", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE, null, options, options[0]);
			return foo == 0;
		}

		public String getPassphrase() {
			return null;
		}

		public boolean promptPassphrase(String message) {
			return true;
		}

		public boolean promptPassword(String message) {
			return true;
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
		} catch (UnknownHostException e) {
			// nothing special
		} catch (IOException e) {
			// nothing special
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