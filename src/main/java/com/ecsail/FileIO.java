package com.ecsail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.ecsail.structures.LoginDTO;


public class FileIO {

public static List<LoginDTO> logins = new ArrayList<>();
	
	public static void saveLoginObjects() {  // saves user file to disk
		File g = new File(HalyardPaths.HOSTS);

		try	{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(g));
			out.writeObject(logins); 
			out.close();
		} catch (Exception e) {
			BaseApplication.logger.error(e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
		BaseApplication.logger.info(HalyardPaths.HOSTS + " saved");
	}

	public static int getSelectedHost(String hostname) {
		boolean error = true;
		int count = 0;
		int iterate = 0;
		for(LoginDTO login: logins) {
			if(login.getHost().equals(hostname)) {
				count = iterate;
				error = false;  // make sure at least one matches
			}
			iterate++;
		}
		if(error) count = -1;
		return count;
	}
	
	public static void openLoginObjects() {
		File g = new File(HalyardPaths.HOSTS);
		if (g.exists()) {
			try {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(g));
				Object obj = in.readObject();
				ArrayList<?> ar = (ArrayList<?>) obj;
				logins.clear();
				for (Object x : ar) {
				    logins.add((LoginDTO) x);
				}
				in.close();
			} catch (Exception e) {
				BaseApplication.logger.error("Error occurred during reading of " + HalyardPaths.HOSTS);
				BaseApplication.logger.error(e.getMessage());
				e.printStackTrace();
			}			  
		} else {
			BaseApplication.logger.error("No such file exists: " + HalyardPaths.HOSTS);
		}
	}
	
	public static boolean hostFileExists() {
		boolean doesExist = false;
		File g = new File(HalyardPaths.HOSTS);
		if(g.exists())
			doesExist = true;
		return doesExist;
	}
	
	public static int getDefaultLogon() {
		int count = 0;
		int iterate = 0;
		for(LoginDTO login: logins) {
			if(login.isDefault()) {
				count = iterate;
			}
			iterate++;
		}
		return count;
	}

}
