package com.ecsail.dto;

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
	private String sqlUser;
	private String sqlPasswd;
	private String sshUser;
	private String sshPass;
	private String database;
	private String knownHostsFile;
	private String privateKey;
	private boolean isDefault;
	private boolean sshForward;

	public LoginDTO(int localSqlPort, int remoteSqlPort, int sshPort, String host, String sqlUser, String sqlPasswd, String sshUser, String sshPass, String database, String knownHostsFile, String privateKey, boolean isDefault, boolean sshForward) {
		this.localSqlPort = localSqlPort;
		this.remoteSqlPort = remoteSqlPort;
		this.sshPort = sshPort;
		this.host = host;
		this.sqlUser = sqlUser;
		this.sqlPasswd = sqlPasswd;
		this.sshUser = sshUser;
		this.sshPass = sshPass;
		this.database = database;
		this.knownHostsFile = knownHostsFile;
		this.privateKey = privateKey;
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

	public String getSqlUser() {
		return sqlUser;
	}

	public void setSqlUser(String sqlUser) {
		this.sqlUser = sqlUser;
	}

	public String getSqlPasswd() {
		return sqlPasswd;
	}

	public void setSqlPasswd(String sqlPasswd) {
		this.sqlPasswd = sqlPasswd;
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

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getKnownHostsFile() {
		return knownHostsFile;
	}

	public void setKnownHostsFile(String knownHostsFile) {
		this.knownHostsFile = knownHostsFile;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
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

	@Override
	public String toString() {
		return "LoginDTO{" +
				"localSqlPort=" + localSqlPort +
				", remoteSqlPort=" + remoteSqlPort +
				", sshPort=" + sshPort +
				", host='" + host + '\'' +
				", sqlUser='" + sqlUser + '\'' +
				", sqlPasswd='" + sqlPasswd + '\'' +
				", sshUser='" + sshUser + '\'' +
				", sshPass='" + sshPass + '\'' +
				", database='" + database + '\'' +
				", knownHostsFile='" + knownHostsFile + '\'' +
				", privateKey='" + privateKey + '\'' +
				", isDefault=" + isDefault +
				", sshForward=" + sshForward +
				'}';
	}
}
