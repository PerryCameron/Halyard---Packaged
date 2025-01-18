package com.ecsail;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;

public class HalyardPaths {

	public static final String LOG_FILE_DIR = System.getProperty("user.home") + "/.ecsc/logs";
	public static final String DEPOSIT_REPORT_PATH = System.getProperty("user.home") + "/Documents/ECSC/Depost_Reports";
	public static final String RENEWAL_FORM = System.getProperty("user.home") + "/Documents/ECSC/Renewal_Forms";
	public static final String ROSTERS = System.getProperty("user.home") + "/Documents/ECSC/Rosters";
	public static final String DIRECTORIES = System.getProperty("user.home") + "/Documents/ECSC/Directories";
    public static final String ECSC_HOME = System.getProperty("user.home") + "/Documents/ECSC";
	public static final String HOSTS = System.getProperty("user.home") + "/.ecsc/hosts.ecs";
	public static final String SLIP_CHART = System.getProperty("user.home") + "/Documents/ECSC/SlipCharts";
 	public static final String DEFAULT_PHOTO = "/personimg.png";
	public static final String BOAT_LISTS = System.getProperty("user.home") + "/Documents/ECSC/BoatLists";
	
	public static void checkPath(String path) {
		File recordsDir = new File(path);
		if (!recordsDir.exists()) {
			BaseApplication.logger.info("Creating dir: " + path);
		    recordsDir.mkdirs();
		}
	}
	
	public static boolean fileExists(String file) {
		File f = new File(file);
		if(f.exists() && !f.isDirectory()) { 
		    return true;
		}
		return false;
	}

	public static int getYear() {
		return Year.now().getValue();
	}
	
	public static String getDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	public static String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
}
