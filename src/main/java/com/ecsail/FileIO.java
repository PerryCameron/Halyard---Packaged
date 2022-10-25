package com.ecsail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.ecsail.structures.Object_Login;
import com.ecsail.structures.Object_TupleCount;


public class FileIO {

public static List<Object_Login> logins = new ArrayList<>();
	
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
	
	public static void saveTupleCountObjects(List<Object_TupleCount> tuples) {  // saves user file to disk
		File g = new File(HalyardPaths.TUPLECOUNTS);
		try	{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(g));
			out.writeObject(tuples);
			out.close();
		} catch (Exception e) {
			BaseApplication.logger.error(e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
		BaseApplication.logger.info(HalyardPaths.TUPLECOUNTS + " saved");
	}

	public static ArrayList<Object_TupleCount> openTupleCountObjects() {
		ArrayList<Object_TupleCount> tuples = new ArrayList<>();
		File g = new File(HalyardPaths.TUPLECOUNTS);
		if (g.exists()) {
			try {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(g));
				Object obj = in.readObject();
				ArrayList<?> ar = (ArrayList<?>) obj;
				for (Object x : ar) {
				    tuples.add((Object_TupleCount) x);
				}
				in.close();
			} catch (Exception e) {
				BaseApplication.logger.error("Error occurred during reading of " + HalyardPaths.TUPLECOUNTS);
				BaseApplication.logger.error(e.getMessage());
				e.printStackTrace();
			}
		} else {
			BaseApplication.logger.error("No such file exists: " + HalyardPaths.TUPLECOUNTS);
		}
		return tuples;
	}
	
	public static Object_TupleCount openTupleCountObject() {
		ArrayList<Object_TupleCount> tuples = new ArrayList<Object_TupleCount>();
		File g = new File(HalyardPaths.TUPLECOUNTS);
		if (g.exists()) {
			try {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(g));
				Object obj = in.readObject();
				ArrayList<?> ar = (ArrayList<?>) obj;
				tuples.clear();
				for (Object x : ar) {
				    tuples.add((Object_TupleCount) x);
				}
				in.close();
			} catch (Exception e) {
				BaseApplication.logger.error("Error occurred during reading of " + HalyardPaths.TUPLECOUNTS);
				BaseApplication.logger.error(e.getMessage());
				e.printStackTrace();
			}
		} else {
			BaseApplication.logger.error("No such file exists: " + HalyardPaths.TUPLECOUNTS);
			BaseApplication.logger.info("Creating file " + HalyardPaths.TUPLECOUNTS);
			List<Object_TupleCount> t = new ArrayList<Object_TupleCount>();
			t.add(new Object_TupleCount());
			saveTupleCountObjects(t);
		}
		return tuples.get(tuples.size() - 1);
	}
	
	public static int getSelectedHost(String hostname) {
		boolean error = true;
		int count = 0;
		int iterate = 0;
		for(Object_Login login: logins) {
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
				    logins.add((Object_Login) x);
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
		for(Object_Login login: logins) {
			if(login.isDefault()) {
				count = iterate;
			}
			iterate++;
		}
		return count;
	}

	// this was used to read schema from a file outsite of source code
//	public static void readFromFile(String filename) {
//		  File file = new File(filename);
//		try {
//			BufferedReader br;
//			br = new BufferedReader(new FileReader(file));
//			String st;
//		  while ((st = br.readLine()) != null) {
//			  tableCreation.add(new String(st));
//		  }
//		  br.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
