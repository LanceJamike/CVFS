package diskPool;
import java.io.*;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LVProcedures{
public String LVLinearPath="/dev/TargetVG/LinearVG";
public String LVStripePath="/dev/TargetVG/StripeVG";
public String LVLinearName="/dev/TargetVG/LinearVG";
public String LVStripeName="/dev/TargetVG/StripeVG";
public String VGPath = "/dev/TargetVG";
public String LinearLVPath = "/dev/TargetVG/LinearLV";
public String StripedLVPath = "/dev/TargetVG/StripedLV";
public String VGName = "TargetVG";
public String LinearLVName = "LinearLV";
public String StripedLVName = "StripedLV";
public String SharePath="/dev/TargetVG/share";
public static String diskA = "Available Disks";
public static String temp = "Updated Disks";
public static int CopyStatus = 0;
public static int ClearToCopy = 0;
public static int counter = 0;

public void isCopying(){
	this.CopyStatus++;
	System.out.println(this.CopyStatus);
}

public int checkCopyStatus(){
	return CopyStatus;
}

public void resetCopyStatus(){
	this.CopyStatus = 0;
	this.ClearToCopy = 0;
}

public void TriggerCopy(){
	this.ClearToCopy = 1;
}

public void initDiskScan(String DiskList){
	commandExec c1 = new commandExec();
	c1.writeFromCommand("lvmdiskscan", DiskList);	

}

public void ScanDisks() throws IOException{

	FileReader file1 = new FileReader(diskA);
    FileReader file2 = new FileReader(temp);
    BufferedReader reader1 = new BufferedReader(file1);
    BufferedReader reader2 = new BufferedReader(file2);
    
    String line1 = reader1.readLine();
    String line2 = reader2.readLine();
    

HashSet set1 = new HashSet();
HashSet set2 = new HashSet();
HashSet set3 = new HashSet();

    while (line1 != null)
    {
    	line1 = line1.replaceAll("\\[(.+?)\\]", " ").replaceAll("LVM physical volume", " ");
    	set3.add(line1);
    	set1.add(line1);
        line1 = reader1.readLine();
    
        
    }
    while (line2 != null)
    {
    	line2 = line2.replaceAll("\\[(.+?)\\]", " ").replaceAll("LVM physical volume", " ");
    	set2.add(line2);
        line2 = reader2.readLine();
    
    }
    
    set1.removeAll(set2);
    set2.removeAll(set3);
String test = null;
Iterator iterator = set1.iterator();
BufferedWriter results = new BufferedWriter(new FileWriter("remove"));
while(iterator.hasNext())
{
	results.write((String)iterator.next());
	results.newLine();
			
}
Iterator iterator2 = set2.iterator();
BufferedWriter results2 = new BufferedWriter(new FileWriter("add"));
while(iterator2.hasNext())
{
	results2.write((String)iterator2.next());
	results2.newLine();
			
}

results.close();
results2.close();
set1.clear();
set2.clear();
set3.clear();
	}

public void ScanDisks(String update) throws IOException{

	FileReader file1 = new FileReader(temp);
    FileReader file2 = new FileReader(update);
    BufferedReader reader1 = new BufferedReader(file1);
    BufferedReader reader2 = new BufferedReader(file2);
    
    String line1 = reader1.readLine();
    String line2 = reader2.readLine();
    

HashSet set1 = new HashSet();
HashSet set2 = new HashSet();
HashSet set3 = new HashSet();

    while (line1 != null)
    {
    	line1 = line1.replaceAll("\\[(.+?)\\]", " ").replaceAll("LVM physical volume", " ");
    	set3.add(line1);
    	set1.add(line1);
        line1 = reader1.readLine();
    
        
    }
    while (line2 != null)
    {
    	line2 = line2.replaceAll("\\[(.+?)\\]", " ").replaceAll("LVM physical volume", " ");
    	set2.add(line2);
        line2 = reader2.readLine();
    
    }
    
    set1.removeAll(set2);
    set2.removeAll(set3);
String test = null;
Iterator iterator = set1.iterator();
BufferedWriter results = new BufferedWriter(new FileWriter("remove"));
while(iterator.hasNext())
{
	results.write((String)iterator.next());
	results.newLine();
			
}
Iterator iterator2 = set2.iterator();
BufferedWriter results2 = new BufferedWriter(new FileWriter("add"));
while(iterator2.hasNext())
{
	results2.write((String)iterator2.next());
	results2.newLine();
			
}

results.close();
results2.close();
set1.clear();
set2.clear();
set3.clear();
	}
public void InitPV() throws Exception{
	String PVPath = null;
	BufferedReader addDrives = new BufferedReader(new FileReader("add"));
	commandExec c1 = new commandExec();
	Pattern pattern = 
    Pattern.compile("(\\/)(d)(e)(v)(\\/)(s)([a-z])([a-z])(\\*?)");
	while ((PVPath = addDrives.readLine())!= null){
		Matcher match = pattern.matcher(PVPath);
		if(match.find())
		c1.runCommand("pvcreate " + PVPath);
	}
}
public void InitVG() throws Exception{
	String PVPath = null;
	BufferedReader addDrives = new BufferedReader(new FileReader("add"));
	commandExec c1 = new commandExec();
	StringBuilder PVs = new StringBuilder();
	Pattern pattern = 
    Pattern.compile("(\\/)(d)(e)(v)(\\/)(s)([a-z])([a-z])(\\*?)");
	while ((PVPath = addDrives.readLine())!= null){
		Matcher match = pattern.matcher(PVPath);
		if(match.find()){
		PVs.append(PVPath);
		//PVs.append(" ");
		}
	}
	System.out.println("Adding " + PVs + " to TargetVG volume group");
	c1.runCommand("vgcreate " + VGName + " " + PVs);
	
}
public void InitLinearLV() throws Exception{
	//Creates the initial LinearLV
	commandExec c1 = new commandExec();
	c1.runCommand("lvcreate -n LinearLV -l 50%VG /dev/TargetVG" );
}
public void InitStripedLV() throws Exception{
	commandExec c1 = new commandExec();
	c1.runCommand("lvcreate -n StripedLV -i2 -I512 -l 50%FREE /dev/TargetVG" );
}
public void AddToVG() throws Exception{
	//add new PV to the TargetVG
	String PVPath = null;
	BufferedReader addDrives = new BufferedReader(new FileReader("add"));
	commandExec c1 = new commandExec();
	Pattern pattern = 
    Pattern.compile("(\\/)(d)(e)(v)(\\/)(s)([a-z])([a-z])(\\*?)");
	while ((PVPath = addDrives.readLine())!= null){
		Matcher match = pattern.matcher(PVPath);
		if(match.find()){
			System.out.println("Adding " + PVPath + " to TargetVG volume group");
			c1.runCommand("vgextend " + VGName + " " + PVPath);
		}
	}
}
	



public void LinearExtendLV(){
	
	
	commandExec c1 = new commandExec();
	//c1.runCommand("umount /mnt/StripedLV");
	c1.runCommand("umount /mnt/LinearLV");
	//c1.runCommand("lvextend -iL -l+50%FREE " + StripedLVPath );
	c1.runCommand("lvextend -l +100%FREE " + LinearLVPath );
	//c1.runCommand("resize2fs " + StripedLVPath);
	c1.runCommand("resize2fs " + LinearLVPath);
	//c1.runCommand("mount -t ext4 " + StripedLVPath+ " /mnt/StripedLV" );
	c1.runCommand("mount -t ext4 " + LinearLVPath+ " /mnt/LinearLV" );
}
public void StripedExtendLV(){
	commandExec c1 = new commandExec();
	c1.runCommand("umount /mnt/StripedLV");
	//c1.runCommand("lvextend -iL -l+50%FREE " + StripedLVPath );
	//c1.runCommand("resize2fs " + StripedLVPath);
	//c1.runCommand("mount -t ext4 " + StripedLVPath+ " /mnt/StripedLV" );
}

public void MakeFileSystem(){
	commandExec c1 = new commandExec();
	c1.runCommand("mkfs -t ext4 " + StripedLVPath );
	c1.runCommand("mkfs -t ext4 " + LinearLVPath );
	
}
public void Mount(){
	commandExec c1 = new commandExec();
	c1.runCommand("mount -t ext4 " + StripedLVPath+ " /mnt/StripedLV" );
	c1.runCommand("mount -t ext4 " + LinearLVPath+ " /mnt/LinearLV" );
	c1.runCommand("chmod 777 -R /mnt/LinearLV" );
	c1.runCommand("chmod 777 -R /mnt/StripedLV" );
}

public void Share(){
	commandExec c1 = new commandExec();
	c1.runCommand("/usr/local/samba/sbin/smbd" );
	//c1.runCommand("service smbd restart" );
}

public void reduceStripe(){
	
}


public void reduceLinear(){
	
}

public void RemoveFromVG()throws Exception{
	String PVPath = null;
	BufferedReader addDrives = new BufferedReader(new FileReader("remove"));
	commandExec c1 = new commandExec();
	while ((PVPath = addDrives.readLine())!= null){
	//missing: if there is space on drive, pmove it to another 
	c1.runCommand("vgreduce " + VGName + " " + PVPath);
	}
}
public void DeLink(){
	try {
	FileReader file1;
	commandExec c1 = new commandExec();
	file1 = new FileReader("removefiles");
	BufferedReader reader1 = new BufferedReader(file1);
	String line1 = reader1.readLine();
	File f = new File("/mnt/share/" + line1);
	while(line1 != null){
		f.delete();
		System.out.println(f);
		line1 = reader1.readLine();
		f = new File ("/mnt/share/" + line1);
	}
	}catch (IOException x) {
		x.printStackTrace();
	}
}
public void DeLinkStriped(){
	try {
	FileReader file1;
	commandExec c1 = new commandExec();
	file1 = new FileReader("removefilesstriped");
	BufferedReader reader1 = new BufferedReader(file1);
	String line1 = reader1.readLine();
	File f = new File("/mnt/share/" + line1);
	while(line1 != null){
		f.delete();
		System.out.println(f);
		line1 = reader1.readLine();
		f = new File ("/mnt/share/" + line1);
	}
	}catch (IOException x) {
		x.printStackTrace();
	}
}
public void MakeLink(){ 

	FileReader file1;
	try {
		file1 = new FileReader("addfiles");
		
	BufferedReader reader1 = new BufferedReader(file1);
	String line1 = reader1.readLine();

	while(line1 != null){
	if(line1.charAt(0) == '/'){
		Path target = Paths.get(line1);
		
		String targetDirectory = "/mnt/share/";
		
		Path newLink = Paths.get(targetDirectory + target.getFileName()); 
		line1 = reader1.readLine();
		
		try {

			Files.createSymbolicLink(newLink, target);

		} catch (IOException x) {

	   // System.err.println(x);
			 System.out.println("No link update yet");

		} catch (UnsupportedOperationException x) {
			System.err.println(x);

		}
	}
	else{
		String filename = "/mnt/LinearLV/" + line1;  //LAGAY DITO UNG FILE NA GAGAWAN NG LINK

		String targetDirectory = "/mnt/share/"; //ETO UNG DIRECTORY NG SHARE SUPPOSEDLY

	

		Path target = Paths.get(filename);

		Path newLink = Paths.get(targetDirectory + target.getFileName()); 


    	line1 = reader1.readLine();

		try {

			Files.createSymbolicLink(newLink, target);

		} 	catch (IOException x) {

			//System.err.println(x);
			  System.out.println("No link update yet");

	  }	catch (UnsupportedOperationException x) {

		  System.err.println(x);

		}
	}
}
	
	reader1.close();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
	
public long folderSize(File filename) {

	    long length = 0;
	    
	    if(filename.isDirectory()){

	    	for (File file : filename.listFiles()) {
	    		if (file.isFile())
	    			length += file.length();

	    		else
	    			length += folderSize(file);

	    	}
	    }
	    else
	    	length = filename.length();

	    return length;

	}
public void MakeLinkStriped(){ 

	FileReader file1;
	checkerstriped();
	try {
		file1 = new FileReader("filenamesstriped");
	
	BufferedReader reader1 = new BufferedReader(file1);
	String line1 = reader1.readLine();

	while(line1 != null){
	if(line1.charAt(0) == '/'){
		Path target = Paths.get(line1);
		
		String targetDirectory = "/mnt/share/";
		
		Path newLink = Paths.get(targetDirectory + target.getFileName()); 
		line1 = reader1.readLine();
		
		try {

			Files.createSymbolicLink(newLink, target);

		} catch (IOException x) {

	    System.err.println(x);

		} catch (UnsupportedOperationException x) {

			System.err.println(x);

		}
	}
	else{
		String filename = "/mnt/StripedLV/" + line1;  //LAGAY DITO UNG FILE NA GAGAWAN NG LINK

		String targetDirectory = "/mnt/share/"; //ETO UNG DIRECTORY NG SHARE SUPPOSEDLY

	

		Path target = Paths.get(filename);

		Path newLink = Paths.get(targetDirectory + target.getFileName()); 


    	line1 = reader1.readLine();

		try {

			Files.createSymbolicLink(newLink, target);

		} 	catch (IOException x) {

			System.err.println(x);

	  }	catch (UnsupportedOperationException x) {

		  System.err.println(x);

		}
	}
}
	
	reader1.close();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public String checkHash(String hasher){
	FileReader file1;
	String r = null;
	try{
	file1 = new FileReader(hasher);
	BufferedReader reader1 = new BufferedReader(file1);
	String line1 = reader1.readLine();
	MessageDigest md = MessageDigest.getInstance("MD5");
    FileInputStream fis = new FileInputStream(hasher);

    byte[] dataBytes = new byte[1024];

    int nread = 0;
    while ((nread = fis.read(dataBytes)) != -1) {
        md.update(dataBytes, 0, nread);
    };
    byte[] mdbytes = md.digest();
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < mdbytes.length; i++) {
        sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
    }
    System.out.println("Digest(in hex format):: " + sb.toString());
    r = sb.toString();
	
	
}catch (FileNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (NoSuchAlgorithmException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	return(r);
}
public boolean checkfilehash(){
	
	FileReader file1;
	boolean fresult = false;
	try {
		file1 = new FileReader("updatedtransfer");
	BufferedReader reader1 = new BufferedReader(file1);
	String line1 = reader1.readLine();

	while(line1 != null){
	
    String result1 = checkHash("/mnt/LinearLV/"+line1);
    String result2 = checkHash("/mnt/LinearStorage/"+line1);
    line1 = reader1.readLine();

    if (result1 == result2)
    	fresult= true;
	
	}

	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return fresult;
	
	
	
}
public void delcache(){ 

	File dir = new File("/mnt/CVFSCache/");
		for (File file: dir.listFiles()) {
			if(file.isDirectory())				//Kapag ang isang file ay subdirectory
	    		delcache(file);
	        file.delete();
	}
} 

public void delcache(File dir){ 
		
		for (File file: dir.listFiles()) {
			if(file.isDirectory())				//Kapag ang isang file ay subdirectory
	    		delcache(file);
	        file.delete();
		}
} 


public void delDirectory(File dir){ 
		//File shareFileName();
	
		for (File file: dir.listFiles()) {
			if(file.isDirectory())				//Kapag ang isang file ay subdirectory
	    		delcache(file);
	        file.delete();
		}
		//String name = dir.getName();
		delcache(new File("/mnt/share/"));	
		dir.delete();
} 
	
public void delcachestriped(){ 

	File dir = new File("/mnt/StripedStorage");
	for (File file: dir.listFiles()) {
		if(file.isDirectory())				//Kapag ang isang file ay subdirectory
    		delcache(file);
        file.delete();
}
}
public void delcachestripe(File dir){ 

	for (File file: dir.listFiles()) {
		if(file.isDirectory())				//Kapag ang isang file ay subdirectory
    		delcache(file);
        file.delete();
}
} 

public Boolean checkFileifExists(File Source) throws IOException{ 
	File folderLinear = new File("/mnt/LinearLV/");
    File folderStripe = new File("/mnt/StripedLV/");       
    File[] listOfFilesLinear = folderLinear.listFiles();
    File[] listOfFilesStripe = folderStripe.listFiles();
    
  	  Boolean isInLinear = false;
  	  Boolean isInStripe = false;
  	  
  	  for(int j = 0;j<listOfFilesLinear.length;j++){
  		  File fileLinear = listOfFilesLinear[j];
  		  if(Source.getName()==fileLinear.getName()){
  			  isInLinear = true;
  			  break;
  		  }
  	  }
  	  
  	  for(int j = 0;j<listOfFilesStripe.length;j++){
  		  File fileStripe = listOfFilesStripe[j];
  		  if(Source.getName()==fileStripe.getName()){
  			  isInStripe = true;
  			  break;                    			
  		  }
  	  }
  	  
  	  if((isInLinear && isInStripe))
  		  return true;     	  
  	  
  	  return false;
}

public void TransferFiles() throws IOException{ 

	FileReader file1;
	file1 = new FileReader("pendingtransfer");
	String cachedir = "/mnt/CVFSCache";
	BufferedReader reader1 = new BufferedReader(file1);
	String line1 = reader1.readLine();
	CopyFileVisitor transDir= new CopyFileVisitor();
	
		if(this.ClearToCopy==1){
			System.out.println("COPY TRIGGERED!!! ");
			this.resetCopyStatus();
			System.out.println("RESET VALUE " + this.checkCopyStatus() + " " + this.ClearToCopy);
			while(line1 != null){
				File f = new File(line1);
				long folderSizeLinear = folderSize(f)/1024/1024;
			   
				if(f.isDirectory()){
					
					if(folderSizeLinear > 512){
						File Source = new File(f.getAbsolutePath());
						File nDir = new File("/mnt/StripedLV/" + f.getName());
			            
						try {
							transDir.copyFolder(Source, nDir);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
							System.out.println("Updating " + Source.getAbsolutePath());				
						}
					}
					else{
						File Source = new File(f.getAbsolutePath());
						File nDir = new File("/mnt/LinearLV/" + f.getName());
						
						try {
							transDir.copyFolder(Source, nDir);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
							System.out.println("Updating " + Source.getAbsolutePath());
						}
				
					}
				}
				else{
					if(folderSizeLinear > 512){
						File filename = new File(line1);
						Path Source = Paths.get(line1);
						Path nDir = Paths.get("/mnt/StripedLV/");
						try {
							Files.copy(Source, nDir.resolve(Source.getFileName()));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
							System.out.println("Updating " + filename.getAbsolutePath());
						}
					}
					else{
						File filename = new File(line1);
						Path Source = Paths.get(line1);
						Path nDir = Paths.get("/mnt/LinearLV/");
						try {
							Files.copy(Source, nDir.resolve(Source.getFileName()));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
							System.out.println("Updating " + filename.getAbsolutePath());
						}
					}
				}
				line1 = reader1.readLine();		
			}
		//}
		//else{
			//this.CurrentStatus = this.CopyStatus;
		//}
		file1.close();
		reader1.close(); 
	}
	else{
		this.ClearToCopy = 0;
	}
 } 

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

public void checker(){
    File folder = new File("/mnt/LinearLV/");
    File[] listOfFiles = folder.listFiles(); 

    try {
        BufferedWriter out = new BufferedWriter(new FileWriter("filenames"));
        for (File file : listOfFiles) {
            if (file.isFile()) {
                out.write(file.getName());
                out.newLine();
            }
            else{
                out.write(file.getAbsolutePath());
                out.newLine();
            }
        }
        out.close();
    } 
    catch (IOException e){ 
       System.out.println("Exception ");
    }
      
} 
public void checkerstriped(){
    File folder = new File("/mnt/StripedLV/");
    File[] listOfFiles = folder.listFiles(); 

    try {
        BufferedWriter out = new BufferedWriter(new FileWriter("filenamesstriped"));
        for (File file : listOfFiles) {
            if (file.isFile()) {
                out.write(file.getName());
                out.newLine();
            }
            else{
                out.write(file.getAbsolutePath());
                out.newLine();
            }
        }
        out.close();
    } 
    catch (IOException e){ 
       System.out.println("Exception ");
    }
} 
public void checkershare(){
    File folder = new File("/mnt/share/");
    File[] listOfFiles = folder.listFiles(); 

    try {
        BufferedWriter out = new BufferedWriter(new FileWriter("sharefiles"));
        for (File file : listOfFiles) {
            if (file.isFile()) {
                out.write(file.getName());
                out.newLine();
            }
            else if(file.isDirectory()){
                out.write(file.getAbsolutePath());
                out.newLine();
            }
        }
        out.close();
    } 
    catch (IOException e){ 
       System.out.println("Exception ");
    }
      
} 
public static void delete(File file)
    	throws IOException{
 
    	if(file.isDirectory()){
 
    		//directory is empty, then delete it
    		if(file.list().length==0){
 
    		   file.delete();
    		   System.out.println("Directory is deleted : " 
                                                 + file.getAbsolutePath());
 
    		}else{
 
    		   //list all the directory contents
        	   String files[] = file.list();
 
        	   for (String temp : files) {
        	      //construct the file structure
        	      File fileDelete = new File(file, temp);
 
        	      //recursive delete
        	     delete(fileDelete);
        	   }
    		}
    	}
}
public void checker(String update){
    File folder = new File("/mnt/LinearLV/");
    File[] listOfFiles = folder.listFiles(); 

    try {
        BufferedWriter out = new BufferedWriter(new FileWriter("updatedfilenames"));
        for (File file : listOfFiles) {
            if (file.isFile()) {
                out.write(file.getName());
                out.newLine();
            }	
            else{
                out.write(file.getAbsolutePath());
                out.newLine();
            }
        }
        out.close();
    } 
    catch (IOException e){ 
       System.out.println("Exception ");
    }
      
} 
public void checkerstriped(String update){
    File folder = new File("/mnt/StripedLV/");
    File[] listOfFiles = folder.listFiles(); 

    try {
        BufferedWriter out = new BufferedWriter(new FileWriter("updatedfilenamesstriped"));
        for (File file : listOfFiles) {
            if (file.isFile()) {
                out.write(file.getName());
                out.newLine();
            }	
            else{
                out.write(file.getAbsolutePath());
                out.newLine();
            }
        }
        out.close();
    } 
    catch (IOException e){ 
       System.out.println("Exception ");
    }
      
} 
}
