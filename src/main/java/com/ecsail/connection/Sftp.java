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
import com.jcraft.jsch.*;


public class Sftp {

    public Sftp() {
        try{
            Session session = BaseApplication.connect.sshConnection.getSession();
            Channel channel=session.openChannel("sftp");
            channel.connect();
            ChannelSftp c=(ChannelSftp)channel;


            System.out.println("Channel version" + c.version());
            System.out.println("Remote Working Directory: " + c.pwd());
            System.out.println("Local Working Directory: " + c.lpwd());
            c.cd("/home/ecsc");
            System.out.println("Local Working Directory: " + c.pwd());

//            session.disconnect();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}