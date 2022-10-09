package com.ecsail.structures;

import java.io.Serializable;

public class Object_Login implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7434038316336853182L;
	private String port;
	private String host;
	private String user;
	private String passwd;
	private String sshUser;
	private String sshPass;
	private boolean isDefault;
	private boolean sshForward;
	
	public Object_Login(String port, String host, String user, String passwd, String sshUser, String sshPass, boolean isDefault, boolean sshForward) {
		this.port = port;
		this.host = host;
		this.user = user;
		this.passwd = passwd;
		this.sshUser = sshUser;
		this.sshPass = sshPass;
		this.isDefault = isDefault;
		this.sshForward = sshForward;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getSshUser() {
		return sshUser;
	}

	public void setSshUser(String sshUser) {
		this.sshUser = sshUser;
	}

	public String getSshPass() {
		return sshPass;
	}

	public void setSshPass(String sshPass) {
		this.sshPass = sshPass;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public boolean isSshForward() {
		return sshForward;
	}

	public void setSshForward(boolean sshForward) {
		this.sshForward = sshForward;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Object_Login [port=" + port + ", host=" + host + ", user=" + user + ", passwd=" + passwd + ", sshUser="
				+ sshUser + ", sshPass=" + sshPass + ", isDefault=" + isDefault + ", sshForward=" + sshForward + "]";
	}

}
