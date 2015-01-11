package diskPool;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateConnection implements Runnable{
	private commandExec getUpdate = new commandExec();
	private LVProcedures updateDrives = new LVProcedures();
	private boolean uFlag = true;
	public String ud = "Recent Disks";
	public static String diskA = "List of Targets";
	public static String temp = "Updated Targets";
	public int count;
	public void run(){
		//naka comment muna ung loop at sleep
		while(isuFlag()==true){
			


		System.out.println("Update again in another minute.");
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Starting rescan");
	try {
		System.out.println("I should be scanning some new iscsitargets here...");
		//getUpdate.runCommand("iscsiadm -m session --rescan");
		InitConfig.ip = null;
		InitConfig.mask = null;
		InitConfig.routeWLAN();
		commandExec c1 = new commandExec();
		//deactivate vgs and any targets first. this is INITIAL.
		
		//Initial lvm disk scan
        LVProcedures initScan = new LVProcedures();
        //Create disk scan different from first disk scan
		//initScan.initDiskScan(ud);
		//Discover IP's around network
        String command = "nmap "+ InitConfig.ip + "/" + InitConfig.mask +" -p T:3260";		        
        //Connect to every IP discovered by nmap
        c1.writeFromCommand(command, "Updated Targets");
        FileReader file1 = new FileReader(diskA);
        FileReader file2 = new FileReader(temp);
        BufferedReader reader1 = new BufferedReader(file1);
        BufferedReader reader2 = new BufferedReader(file2);  
        String line1 = reader1.readLine();
        String line2 = reader2.readLine();
        StringBuilder Ts1 = new StringBuilder();
        StringBuilder Ts2 = new StringBuilder();
        while(line1 != null){
        	line1 = line1.replaceAll("\\(.*?\\)", " ");
        	Ts1.append(line1);
        	line1 = reader1.readLine();
        }
        while(line2 != null){
        	line2 = line2.replaceAll("\\(.*?\\)", " ");
        	Ts2.append(line2);
        	line2 = reader2.readLine();
        }
        if(Ts1.length() != Ts2.length()){
        	c1.runCommand("vgchange -an");
    		//c1.runCommand("iscsiadm -m node -u");
        	c1.writeFromCommand(command, "List of Targets");
        	InitConfig.connectTarget();
        	System.out.println("I should be scanning a new some new drives here..");
        	getUpdate.writeFromCommand("lvmdiskscan", ud);
    		updateDrives.ScanDisks(ud);
    		getUpdate.writeFromCommand("lvmdiskscan", "Updated Disks");
    		updateDrives.InitPV(); 
    		updateDrives.AddToVG();
    		String PVPath = null;
    		int count = 0;
    		BufferedReader addDrives = new BufferedReader(new FileReader("add"));
    		Pattern pattern = 
    	    Pattern.compile("(\\/)(d)(e)(v)(\\/)(s)([a-z])([a-z])(\\*?)");
    		while ((PVPath = addDrives.readLine())!= null){
    			Matcher match = pattern.matcher(PVPath);
    			if(match.find())
    			{
    				count++;
    			}
    
    		}if(count == 1)
    			updateDrives.LinearExtendLV(); 
    		else
    			updateDrives.StripedExtendLV(); 
    		// updateDrives.RemoveFromVG(); 		
        }
			
	} catch (Exception e) {
		e.printStackTrace();
	}
	if(this.isuFlag()==false)
		break;
		}
	}
	public void setuFlag(boolean uFlag) {
		this.uFlag = uFlag;
	}
	public boolean isuFlag() {
		return uFlag;
	}	
}
