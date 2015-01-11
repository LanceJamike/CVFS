package diskPool;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SambaLink {
	
	public static void purgeDirectory(File dir) {				//METHOD THAT PURGES EVERYTHING IN A DIRECTORY
	    for (File file: dir.listFiles()) {
	        file.delete();
	    }
	}

	public static void makeLink(File[] LVFileList, String shareDirectory){ 
		/*File deleteShare = new File(shareDirectory);		
		purgeDirectory(deleteShare);										//THIS METHOD DELETES ALL FILES IN SHARE DIRECTORY	
		for(int i = 0; i<LVFileList.length;i++){										//THEN UPDATES THE SHARE WITH ALL FILES FOUND IN
			Path newLink = Paths.get(shareDirectory + LVFileList[i].getName()); 		//IN LVFileList
			Path target = Paths.get(LVFileList[i].getAbsolutePath());
			try {
				Files.createSymbolicLink(newLink, target);
			} catch (IOException x) {
				System.err.println(x);
			} catch (UnsupportedOperationException x) {
				System.err.println(x);
			}
		}*/
		
		Path newLink = Paths.get(shareDirectory + "greg.txt"); 		//Eto yung LINK na ilalagay sa as shortcut sa share

		Path target = Paths.get("C:/Users/arjay2187/Desktop/greg.txt"); //Eto yung original file na target ng link kaso wala pa
																		//siya sa actual target.
		try {
			Files.createSymbolicLink(newLink, target);				//Function to create symbolic link
		} catch (IOException x) {
			System.err.println(x);
		} catch (UnsupportedOperationException x) {
			System.err.println(x);
		}
	}
	
	
	public static void main(String[] args) {
		String LVDirectory = "C:/Users/arjay2187/Desktop/";			//ETO UNG DIRECTORY NG LV SUPPOSEDLY
		File[] listOfFiles = new File(LVDirectory).listFiles();		//SCANS LVDirectory for File Lists
		
		String shareDirectory = "C:/Users/arjay2187/Desktop/Share/"; //ETO UNG DIRECTORY NG SHARE SUPPOSEDLY
		makeLink(listOfFiles, shareDirectory);
	}

}