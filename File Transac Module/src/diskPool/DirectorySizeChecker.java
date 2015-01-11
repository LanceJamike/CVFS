package diskPool;



import java.io.File;



public class DirectorySizeChecker {

	

	public static long checkfolderSize(File directory) {

	    long length = 0;

	    for (File file : directory.listFiles()) {

	        if (file.isFile())

	            length += file.length();

	        else

	            length += checkfolderSize(file);

	    }

	    return length;

	}

	

	public static void purgeDirectory(File dir) {

	    for (File file: dir.listFiles()) {		//Scan lahat ng files sa isang directory

	    	if(file.isDirectory()){				//Kapag ang isang file ay subdirectory

	    		purgeDirectory(file);			//Apply Recursion and pass the subdirectory to purgeDirectory 

	    	}

	        file.delete();

	    }

	}

	

	public static void main(String[] args) {

		String LVLinearCacheDirectory = "/mnt/CVFSCache/";			//ETO UNG DIRECTORY NG LINEAR CACHE STORAGE

		//String LVStripeCacheDirectory = "C:/Users/arjay2187/Downloads/Video/Boardwalk Empire Season 1-2 Complete 480p.ILPruny/";

		

		File LinearCacheDirectory = new File(LVLinearCacheDirectory);								//Gets the Directory

		//File StripeCacheDirectory = new File(LVStripeCacheDirectory);

		

		long folderSizeLinear = checkfolderSize(LinearCacheDirectory)/1024/1024;					//Computes the size into MegaBytes

		//long folderSizeStripe = folderSize(StripeCacheDirectory)/1024/1024/1024;

	//	System.out.println(folderSizeLinear + "MB");

		/*if(folderSizeLinear > 512)					//If folder size exceeds 512MB LINEAR TO then purge directory

			purgeDirectory(LinearCacheDirectory);

		

		if(folderSizeStripe > 2)

			purgeDirectory(StripeCacheDirectory);*/

		

		System.out.println(folderSizeLinear  + "MB");

//		System.out.println(folderSizeStripe  + "GB");

			

	}



}
