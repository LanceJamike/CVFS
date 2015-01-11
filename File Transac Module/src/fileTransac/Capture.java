package fileTransac;
//import java.net.InetAddress;
//import java.net.NetworkInterface;

//import jpcap.packet.*;
//import jpcap.*;
import java.util.Scanner;
import java.lang.Object.*;

public class Capture {
	 /*public static void main(String[] args) throws java.io.IOException{
	        
     	//DEVICE INFORMATION RETRIEVAL!
         
     	//Obtain the list of network interfaces
         jpcap.NetworkInterface[] devices = JpcapCaptor.getDeviceList();
         
         //Checks all network interface sa devices array
         for (int i = 0; i < devices.length; i++) {
           //print out its name and description
           System.out.println(i+": "+devices[i].name + "(" + devices[i].description+")");

           //print out its datalink name and description
           System.out.println(" datalink: "+devices[i].datalink_name + "(" + devices[i].datalink_description+")");

           //print out its MAC address
           System.out.print(" MAC address:");
           for (byte b : devices[i].mac_address)
             System.out.print(Integer.toHexString(b&0xff) + ":");
           	System.out.println();
           //print out its IP address, subnet mask and broadcast address
           for (NetworkInterfaceAddress a : devices[i].addresses)
             System.out.println(" address:"+a.address + " " + a.subnet + " "+ a.broadcast);
         }
         	//Get the Device information

         	//CAPTURE PACKETS HERE!!!

             System.out.println("\n \n ");
             System.out.println("Please Enter the Device Name to Capture the Packet");
             Scanner in = new Scanner(System.in);
             int a = in.nextInt();
             if(a <= devices.length)
             {
             int index=a;  // Pili ng device na gagamitin

             //Open ung inteface na napili
             JpcapCaptor captor=JpcapCaptor.openDevice(devices[index], 65535, false, 20);
             captor.setFilter("icmp",true);
             
               //capture a single packet and print it out
               Packet packet = captor.getPacket();	//VARIABLE FOR PACKET CAPTURE
               while(true){
               if(packet!=null){
             	  receivePacket(packet);
             	  //break; //NAKA INFINITE LOOP KAPAG WALANG BREAK PARA MATEST KUNG GANO KADAMI UNG PACKETS CAPTURED
               }
               packet = captor.getPacket();

             }

             }
             else
             System.out.println("Please Enter the correct value");
             
             System.out.println(System.getProperty("java.library.path"));
         }
     
     //METHOD TO SIMPLIFY PRINTING THE PACKET BY JUST USING IP ADDRESSES SOURCE AND DESTINATION
     public static void receivePacket(Packet pkt) {
         IPPacket pac = (IPPacket) pkt;
        System.out.println(pkt.len);
             System.out.println("Src: " + pac.src_ip + " Dest: " + pac.dst_ip);
             ConvertToHex(pkt.header,pkt.data);
             System.out.println();
     }
     
     public static void ConvertToHex(byte bytesHeader[], byte bytesData[]){
         StringBuilder sbHeader = new StringBuilder();
         StringBuilder sbData = new StringBuilder();
         String packet = new String();
         int headerLength =  bytesHeader.length;
         int dataLength = bytesData.length;
         int i=0, j=0;
         
         for (i=0;i<headerLength;i++) 
             sbHeader.append(String.format("%02X", bytesHeader[i]));
         
         for(j=0;j<dataLength;j++)
         	sbData.append(String.format("%02X", bytesData[j]));
        
         packet = sbHeader.toString() + sbData.toString();
         
         RetrieveHex(packet, 1, 15);				//GETS THE SOURCE IP ADDRESS
         System.out.print(".");
         RetrieveHex(packet, 1, 16);
         System.out.print(".");
         RetrieveHex(packet, 2, 1);
         System.out.print(".");
         RetrieveHex(packet, 2, 2);
         
         System.out.println();
         
         for(i=0;i<packet.length();i++){		//PRINTS THE WHOLE PACKET DATA IN HEX FORM
         	if(i%2==0)
         		System.out.print(" ");
         System.out.print(packet.charAt(i));
     	
         if((i+1)%32==0)
           System.out.println();
     	}
 }
     
     //RETRIEVES ANY HEX VALUE FROM PACKET
     public static void RetrieveHex(String packet,int offset, int index){
     	String Hex = new String();
     	StringBuilder sb = new StringBuilder();
     	int i = 32;
     	index = (index * 2) + ((i * offset) - 1);
     	
     	sb.append(packet.charAt(index-1));
     	sb.append(packet.charAt(index));
     	Hex = sb.toString();
     	        	
     	int number = Integer.parseInt(Hex, 16);
     	System.out.print(number);
     }*/
}
