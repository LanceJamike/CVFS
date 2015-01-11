package diskPool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class linkupdateboth implements Runnable{
	private LVProcedures updateDrives = new LVProcedures();
	private boolean uFlag = true;
	public static String UFN2 = "updatedfilenamesstriped";
	public static String FN2 = "filenamesstriped";
	public static String UFN1 = "updatedfilenamesstriped";
	public static String FN1 = "filenamesstriped";
	public static int CopyStatus = -1;
	
	public void run(){
		while(isuFlag()==true){
			try
			{
				  Thread.sleep(5000);
				  updateDrives.checker("updatedfilenames");
                  updateDrives.checkershare();
                  updateDrives.checker();
                  updateDrives.checkerstriped();
                  
                  String [] files1 = new String [100];
                  String [] files2 = new String [100];
                  
                  FileReader file1 = new FileReader("filenames");
                  BufferedReader reader1 = new BufferedReader(file1);
                  files1[0] = reader1.readLine();
                  FileReader file2 = new FileReader("filenamesstriped");
                  BufferedReader reader2 = new BufferedReader(file2);
                  files2[0] = reader2.readLine();
                  int i = 0;
                  int k = 0;
                  String a = null; 
                  while(files2[i] != null)
                  {
                	  while(files1[k]!=null){
                		  if (files1[k].replaceAll("/mnt/LinearLV/", " ").equals(files2[i].replaceAll("/mnt/StripedLV/", " ")))
                		  {
                			  a=files1[k];
                			  File b = new File(a);
                			  updateDrives.delDirectory(b);
                			  k++;
                		  }
                		  else {
                			  System.out.println(files1[k].replaceAll("/mnt/LinearLV/", " "));
                    		  System.out.println(files2[i].replaceAll("/mnt/StripedLV/", " "));
                    		  k++;  
                		  }
                	  }
                  i++;
                  }                  
                  
                  
                  
                 // if(updateDrives.checkfilehash() == true){
                  FileReader file3 = new FileReader("updatedfilenames");
                  BufferedReader reader3 = new BufferedReader(file3);
                  String line3 = reader3.readLine();
                  FileReader file4 = new FileReader("sharefiles");
                  BufferedReader reader4 = new BufferedReader(file4);
                  String line4 = reader4.readLine();
                  HashSet set4 = new HashSet();
                  HashSet set5 = new HashSet();
                  HashSet set3 = new HashSet();
                  while(line3 != null){
                  	  //     	line3 = line3.replaceAll("\\(.*?\\)", " ");
                  	        	set3.add(line3);
                  	        	line3 = reader3.readLine();
                  	        }
                  while(line4 != null){
            //      	line3 = line3.replaceAll("\\(.*?\\)", " ");
                  	set4.add(line4);
                  	set5.add(line4);
                  	line4 = reader4.readLine();
                  }
                  set4.removeAll(set3);
                  set3.removeAll(set5);
              String test = null;
              Iterator iterator = set4.iterator();
              BufferedWriter results = new BufferedWriter(new FileWriter("removefiles"));
              while(iterator.hasNext())
              {
              	results.write((String)iterator.next());
              	results.newLine();
              			
              }
              Iterator iterator2 = set3.iterator();
              BufferedWriter results2 = new BufferedWriter(new FileWriter("addfiles"));
              while(iterator2.hasNext())
              {
              	results2.write((String)iterator2.next());
              	results2.newLine();
              			
              }

              results.close();
              results2.close();
              set3.clear();
              set4.clear();
              set5.clear();
              
              
                      //updateDrives.MakeLink();
                      //updateDrives.DeLink();
              int Status = updateDrives.checkCopyStatus();
              if(Status == this.CopyStatus){
            	  	  if(Status != 0){
            	  		  
            	  		  updateDrives.TriggerCopy();
            	  		  updateDrives.TransferFiles();
            	  		  updateDrives.checker();
                      
            	  		  long dirSize = updateDrives.checkfolderSize(new File("/mnt/CVFSCache/"))/1024/1024/1024;
            	  		  if(dirSize>=5)
            	  			  updateDrives.delcache();
            	  		  //updateDrives.TransferDir();
            	  		  //updateDrives.delcachestriped();
            	  		  //updateDrives.delcache();
            	  		  updateDrives.MakeLink();
            	  		  updateDrives.MakeLinkStriped();
            	  		  updateDrives.DeLinkStriped();
            	  		  updateDrives.DeLink();
                 
            	  		  System.out.println("link complete" );
            	  	  }
			}
              else{
            	  this.CopyStatus = Status; 
              }
                          
} catch (Exception e) {
	e.printStackTrace();
}
     	if(this.isuFlag()==false)
     		break;} 
		
	}
	public void setuFlag(boolean uFlag) {
		this.uFlag = uFlag;
	}
	public boolean isuFlag() {
		return uFlag;
	}
}
