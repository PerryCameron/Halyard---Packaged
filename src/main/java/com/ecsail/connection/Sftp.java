package com.ecsail.connection;

/* -*-mode:java; c-basic-offset:2; indent-tabs-mode:nil -*- */
/**
 * This program will demonstrate the sftp protocol support.
 *   $ CLASSPATH=.:../build javac Sftp.java
 *   $ CLASSPATH=.:../build java Sftp
 * You will be asked username, host and passwd.
 * If everything works fine, you will get a prompt 'sftp>'.
 * 'help' command will show available command.
 * In current implementation, the destination path for 'get' and 'put'
 * commands must be a file, not a directory.
 *
 */

import com.ecsail.BaseApplication;
import com.ecsail.gui.tabs.boatview.AppProgressMonitor;
import com.jcraft.jsch.*;


public class Sftp {
    private ChannelSftp c;
    public Sftp() {
        try{
            Session session = BaseApplication.connect.sshConnection.getSession();
            Channel channel=session.openChannel("sftp");
            channel.connect();
            this.c=(ChannelSftp)channel;
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void changeRemoteDirectory(String path) {
        try {
            c.cd(path);
            System.out.println("Directory is now: " + c.pwd());
        } catch (SftpException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeLocalDirectory(String path) {
        try {
            c.lcd(path);
            System.out.println("Directory is now:" + c.lpwd());
        } catch (SftpException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeGroup(String path, int group) {
        try {
            c.chgrp(group, path);
        } catch (SftpException e) {
            throw new RuntimeException(e);
        }
    }

    public void getFile(String getFrom, String getTo) {
        try {
            SftpProgressMonitor monitor=new AppProgressMonitor();
            int mode=ChannelSftp.OVERWRITE;
            c.get(getFrom, getTo, monitor, mode);
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    public void sendFile(String sendFrom, String sendTo) {
        try {
            SftpProgressMonitor monitor=new AppProgressMonitor();
            int mode=ChannelSftp.OVERWRITE;
            c.put(sendFrom, sendTo, monitor, mode);
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(String path) {
        try {
            c.rm(path);
        } catch (SftpException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeDirectory(String path) {
        try {
            c.mkdir(path);
        } catch (SftpException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteDirectory(String path) {
        try {
            c.rmdir(path);
        } catch (SftpException e) {
            throw new RuntimeException(e);
        }
    }
}