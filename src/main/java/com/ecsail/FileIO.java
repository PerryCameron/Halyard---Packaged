package com.ecsail;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.ecsail.dto.LoginDTO;


public class FileIO {
	private static final String OUTPUT_FORMAT = "Path: %-30s -> File Extension: %s";
	private static final String WINDOWS_FILE_SEPARATOR = "\\";
	private static final String UNIX_FILE_SEPARATOR = "/";
	private static final String FILE_EXTENSION = ".";


	
	public static void saveLoginObjects(List<LoginDTO> logins) {  // saves user file to disk
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

	public static int getSelectedHost(String hostname, List<LoginDTO> logins) {
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
	
	public static void openLoginObjects(List<LoginDTO> logins) {
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
	
	public static int getDefaultLogon(List<LoginDTO> logins) {
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

	public static void deleteFile(String path) {
		File fileToDelete = new File(path);
		if (fileToDelete.delete()) {
			BaseApplication.logger.info("Deleted the file: " + fileToDelete.getName());
		} else {
			BaseApplication.logger.info("Failed to delete the file: " + fileToDelete.getName());
		}
	}

	public static void copyFile(File srcFile, File destFile) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(srcFile);
			os = new FileOutputStream(destFile);
			byte[] buffer = new byte[8192];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				is.close();
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static String getFileExtension(String fileName) {

		if (fileName == null) {
			throw new IllegalArgumentException("fileName must not be null!");
		}

		String extension = "";

		int indexOfLastExtension = fileName.lastIndexOf(FILE_EXTENSION);

		// check last file separator, windows and unix
		int lastSeparatorPosWindows = fileName.lastIndexOf(WINDOWS_FILE_SEPARATOR);
		int lastSeparatorPosUnix = fileName.lastIndexOf(UNIX_FILE_SEPARATOR);

		// takes the greater of the two values, which mean last file separator
		int indexOflastSeparator = Math.max(lastSeparatorPosWindows, lastSeparatorPosUnix);

		// make sure the file extension appear after the last file separator
		if (indexOfLastExtension > indexOflastSeparator) {
			extension = fileName.substring(indexOfLastExtension + 1);
		}

		return extension.toLowerCase();
	}

}
