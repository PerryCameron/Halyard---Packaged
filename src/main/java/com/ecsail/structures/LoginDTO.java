package com.ecsail.structures;

import java.io.Serializable;

public class LoginDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7434038316336853182L;

	private int localSqlPort;
	private int remoteSqlPort;
	private int sshPort;
	private String host;
	private String user;
	private String passwd;
	private String sshUser;
	private String sshPass;
	private String knownHostsFile;
	private String publicKeyFile;
	private boolean isDefault;
	private boolean sshForward;

	public LoginDTO(int localSqlPort, int remoteSqlPort, int sshPort, String host, String user, String passwd,
					String sshUser, String sshPass, String knownHostsFile, String publicKeyFile, boolean isDefault,
					boolean sshForward) {
		this.localSqlPort = localSqlPort;
		this.remoteSqlPort = remoteSqlPort;
		this.sshPort = sshPort;
		this.host = host;
		this.user = user;
		this.passwd = passwd;
		this.sshUser = sshUser;
		this.sshPass = sshPass;
		this.knownHostsFile = knownHostsFile;
		this.publicKeyFile = publicKeyFile;
		this.isDefault = isDefault;
		this.sshForward = sshForward;
	}

	public int getLocalSqlPort() {
		return localSqlPort;
	}

	public void setLocalSqlPort(int localSqlPort) {
		this.localSqlPort = localSqlPort;
	}

	public int getRemoteSqlPort() {
		return remoteSqlPort;
	}

	public void setRemoteSqlPort(int remoteSqlPort) {
		this.remoteSqlPort = remoteSqlPort;
	}

	public int getSshPort() {
		return sshPort;
	}

	public void setSshPort(int sshPort) {
		this.sshPort = sshPort;
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

	public String getKnownHostsFile() {
		return knownHostsFile;
	}

	public void setKnownHostsFile(String knownHostsFile) {
		this.knownHostsFile = knownHostsFile;
	}

	public String getPublicKeyFile() {
		return publicKeyFile;
	}

	public void setPublicKeyFile(String publicKeyFile) {
		this.publicKeyFile = publicKeyFile;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean aDefault) {
		isDefault = aDefault;
	}

	public boolean isSshForward() {
		return sshForward;
	}

	public void setSshForward(boolean sshForward) {
		this.sshForward = sshForward;
	}
}
