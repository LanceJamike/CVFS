package diskPool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class FileTransfer implements Runnable{
	private LVProcedures updateDrives = new LVProcedures();
	private boolean uFlag = true;
	public static String UFN = "updatedtransfer";
	public static String FN = "transfer";
	public void run(){
		while(isuFlag()==true){
			
	Path SharePath = Paths.get("/mnt/CVFSCache/");
		 WatchService watchService;
		try {
			watchService = FileSystems.getDefault().newWatchService();
		
         SharePath.register(watchService, 
        		 StandardWatchEventKinds.ENTRY_MODIFY,
        		 StandardWatchEventKinds.ENTRY_DELETE,
        		 StandardWatchEventKinds.ENTRY_CREATE);

         WatchKey WKey = watchService.take();
         
         List<WatchEvent<?>> events = WKey.pollEvents();
         for (WatchEvent event : events) {
              if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                  System.out.println("Created: transfer " + event.context().toString());          		  
                 

                  
               
                  File folder = new File("/mnt/CVFSCache/");
                  File[] listOfFiles = folder.listFiles();               
                  try {
                      BufferedWriter out = new BufferedWriter(new FileWriter("pendingtransfer"));
                      
                      for (File file : listOfFiles) { 
                    		  out.write(file.getAbsolutePath());
                    		  out.newLine();
                      }
                      out.close();
                  } 
                  catch (IOException e){ 
                     System.out.println("Exception ");
                  }
                  
                  
              		  //updateDrives.TransferDir();
                      //updateDrives.createTriggerStatus();
                      updateDrives.isCopying();
                      
                  	  
                      //updateDrives.TransferFiles();
                      
                      //updateDrives.MakeLink();
                      //updateDrives.MakeLinkStriped();
                      
                      watchService.close();
                      //break;
              }
              if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                  System.out.println("Deleted: transfer" + event.context().toString());
                  FileReader file3 = new FileReader(UFN);
                  BufferedReader reader3 = new BufferedReader(file3);
                  String line3 = reader3.readLine();
                  FileReader file4 = new FileReader(FN);
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
              Iterator iterator2 = set3.iterator();
              BufferedWriter results2 = new BufferedWriter(new FileWriter("pendingtransfer"));
              while(iterator2.hasNext())
              {
              	results2.write((String)iterator2.next());
              	results2.newLine();
              			
              }

              results2.close();
              set3.clear();
              set4.clear();
              set5.clear();
              
                      //updateDrives.TransferDir();
                      //updateDrives.TransferFiles();
                      watchService.close();
              }
              if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                  //System.out.println("Modified: transfering " + event.context().toString());
            	  
            	  File folder = new File("/mnt/CVFSCache/");
                  File[] listOfFiles = folder.listFiles();               
                  try {
                      BufferedWriter out = new BufferedWriter(new FileWriter("pendingtransfer"));
                      
                      for (File file : listOfFiles) { 
                    		  out.write(file.getAbsolutePath());
                    		  out.newLine();
                      }
                      out.close();
                  } 
                  catch (IOException e){ 
                     System.out.println("Exception ");
                  }
            	  
              //results2.close();
                   //   updateDrives.checker2();
                  	  updateDrives.isCopying();
              	  	 // updateDrives.TransferFiles();
                      watchService.close();
                      System.out.println(updateDrives.checkCopyStatus());
                      break;
              }
          }      
         watchService.close();
} catch (IOException e) {
			
			e.printStackTrace();
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