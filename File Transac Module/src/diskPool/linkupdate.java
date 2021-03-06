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


public class linkupdate implements Runnable{
	private LVProcedures updateDrives = new LVProcedures();
	private boolean uFlag = true;
	public static String UFN = "updatedfilenames";
	public static String FN = "filenames";

	
	
	public void run(){
		while(isuFlag()==true){
			
	Path SharePath = Paths.get("/mnt/LinearLV/");
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
                  System.out.println("Created: " + event.context().toString());
                  updateDrives.checker(UFN);
                  updateDrives.checkershare();
                 // if(updateDrives.checkfilehash() == true){
                  FileReader file3 = new FileReader(UFN);
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
              		  
              			  //updateDrives.TransferFiles();
              			  updateDrives.checker();
                      
              			  long dirSize = updateDrives.checkfolderSize(new File("/mnt/CVFSCache/"))/1024/1024/1024;
              			  if(dirSize>=5)
              				  updateDrives.delcache();
              			  System.out.println("link complete" );
              		  
              		  
              	
                  //}
                      watchService.close();
              }
              else if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                  System.out.println("Delete: " + event.context().toString());
                  updateDrives.checker(UFN);
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
                      updateDrives.MakeLink();
                      //updateDrives.DeLink();
                      updateDrives.checker();
              }
              else if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                  System.out.println("Modify: " + event.context().toString());
                  updateDrives.checker(UFN);
                  //if(updateDrives.checkfilehash() == true){
                   updateDrives.checkershare();
                   FileReader file3 = new FileReader(UFN);
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

                       updateDrives.TransferFiles();
                       updateDrives.MakeLink();
                       //updateDrives.DeLink();
                       updateDrives.checker();
                       System.out.println("link complete" );
                   //}
                       watchService.close();
              }
          }      
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
