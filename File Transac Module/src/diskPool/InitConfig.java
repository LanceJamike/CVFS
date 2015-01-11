package diskPool;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import fileTransac.Capture;

public class InitConfig{
	
	public static boolean filterPorts(String ip, int port, int timeout){
		try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), timeout);
            socket.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
	}
	
	public static void connectTarget() throws FileNotFoundException{
		String properTName = null;
		FileReader listIP = new FileReader("List of Targets");
	    String input = "";
		Scanner scan = new Scanner(listIP);
		while(scan.hasNextLine()){
			input += scan.nextLine();
		}
		scan.close();
		Pattern pattern = 
	        Pattern.compile("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
	    Matcher match = pattern.matcher(input);
	    commandExec c2 = new commandExec();
	    while(match.find()) {
	    	if(filterPorts(match.group(),3260,60)==true){
	    	System.out.println("Target info: ");
	    	properTName = c2.runReturn("iscsiadm -m discovery -t sendtargets -p " + match.group());
	    	Pattern p = Pattern.compile("(i)(q)(n)(\\.)((?:(?:[1]{1}\\d{1}\\d{1}\\d{1})|(?:[2]{1}\\d{3})))(?![\\d])(-)(\\d+)(\\.)((?:[a-z][a-z]+))(\\.)((?:[a-z][a-z]+))(:)((?:[a-z][a-z0-9_]*))"); 
	    	Matcher m = p.matcher(properTName);
	    	if(m.find())
	    	properTName = m.group();
	        c2.runCommand("iscsiadm -m node --targetname " + properTName + " -p " + match.group() + ":3260 -l"); 
	    	System.out.println(match.group() + " is now connected as a target.");
	    	
	    	}
	    	else{
	    	System.out.println(match.group() + " is not a target.");	
	    	}
	    	
	    	}
	}
	
	
	
	public static void routeWLAN() throws SocketException{
		
		
        int x = 0;
        //Print out all interfaces and IP addresses
        for (Enumeration<NetworkInterface> ifaces = 
               NetworkInterface.getNetworkInterfaces();
             ifaces.hasMoreElements(); )
        { 
            NetworkInterface iface = ifaces.nextElement();
            if(iface.getName().equals("eth0")){
                for (Enumeration<InetAddress> addresses = iface.getInetAddresses();
                 addresses.hasMoreElements(); )
            {
                InetAddress address = addresses.nextElement();
                x++;
                if(x == 2){
                     String delims = "/";
                     StringTokenizer st = new StringTokenizer(address.toString(), delims);
                    while (st.hasMoreElements()) {
                        ip = st.nextElement().toString();
                        mask = String.valueOf(iface.getInterfaceAddresses().get(1).getNetworkPrefixLength());
                    }
                    x = 0;
                }
                
            }
            }
            
        }
        
	}    
		    public static void main(String[] args) throws Exception { 
		    	//The program should ask admin if this is an initial start-up or not.
		    	//If yes, create the file system from scratch
		    	//If no, just extend/reduce/edit all the target configurations
		    	ip = null; mask = null;
		        //Look for eth0 dynamically
		        try {
					routeWLAN();
				} catch (SocketException e) {
					
					e.printStackTrace();
			
				}
				commandExec c1 = new commandExec();
				//deactivate vgs and any targets first. this is INITIAL.
				c1.runCommand("vgchange -an");
				c1.runCommand("iscsiadm -m node -u");
				//Initial lvm disk scan
		        LVProcedures initScan = new LVProcedures();
		        //Create disk scan different from first disk scan
				initScan.initDiskScan("Available Disks");
				//Discover IP's around network
		        String command = "nmap "+ ip + "/" + mask +" -p T:3260";		        
		        //Connect to every IP discovered by nmap
		        c1.writeFromCommand(command, "List of Targets");
		    	try {
					connectTarget();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
		        c1.runCommand("cat /proc/partitions");
		      
		        initScan.initDiskScan("Updated Disks");
		        try {
					initScan.ScanDisks();
					//initScan.AddToVG();
				    //initScan.RemoveFromVG();
					
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
		        
				finally{
					LVProcedures Addvg = new LVProcedures();
					Addvg.InitPV();
					Addvg.InitVG();
					Addvg.InitStripedLV();
					Addvg.InitLinearLV();				
					Addvg.MakeFileSystem();
					Addvg.Mount();
					Addvg.Share();
					c1.runCommand("vgchange -ay");
					c1.runCommand("chmod 777 /mnt/LinearLV" );
					c1.runCommand("chmod 777 /mnt/StripedLV" );
				}

		        File addFileText = new File("addfiles");
		        File shareFileText = new File("sharefiles");
		        File pendingFileText = new File("pendingtransfer");
		        File deleteFileText = new File("removefiles");
		        System.out.println(addFileText.delete());	
		        System.out.println(shareFileText.delete());
		        System.out.println(pendingFileText.delete());
		        System.out.println(deleteFileText.delete());
		        new FileWriter("pendingtransfer");
		        new FileWriter("addfiles");
		        new FileWriter("sharefiles");
		        new FileWriter("removefiles");
		        
		        
		    	UpdateConnection uc1 = new UpdateConnection();
		    	linkupdate lu = new linkupdate();
		    	linkupdateshare lus = new linkupdateshare();
		    	linkupdateboth lub = new linkupdateboth();
		        Thread update = new Thread(uc1);
		        Thread lupdateb = new Thread(lub);
		        Thread lupdate = new Thread(lu);
		        Thread lupdates = new Thread(lus);
		        FileTransfer ft = new FileTransfer();
		        Thread fupdate = new Thread(ft);
		        update.start();
		        lupdate.start();
		        //lupdates.start();
		        fupdate.start();
		        lupdateb.start();
		        System.out.println("Updates perform every 1 minute.");	
		        
		       
		         }
		    
public static String ip;
public static String mask;

}