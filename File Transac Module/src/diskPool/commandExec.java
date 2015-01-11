package diskPool;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class commandExec {
	public String runReturn(String commandName){
	    try 
	        {   //Run command
	    		String line=null;
	            Process p=Runtime.getRuntime().exec(commandName); 
	            //Read output from terminal to console
	            BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream()));
	            //There is sure to be only 1 line to be outputted from this command.	           
	            line=reader.readLine();
	            System.out.println(line); 
	            p.waitFor();
	            reader.close();
	            return line;    
	        } 
	        catch(IOException e1) {System.err.println(e1);
	        return null;} 
	        catch (InterruptedException ex) {
	                Logger.getLogger(commandExec.class.getName()).log(Level.SEVERE, null, ex);
	                return null;
	        }
	        }
	public void runCommand(String commandName){
	    try 
	        {   //Run command
	    		String line=null;
	            Process p=Runtime.getRuntime().exec(commandName); 
	            //Read output from terminal to console
	            BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream()));
	           
	            while((line=reader.readLine())!=null) 
	            {	//Print it to java console 
	                System.out.println(line); 
	            } 
	            
	                p.waitFor();
	                reader.close();
	        } 
	        catch(IOException e1) {System.err.println(e1);} 
	        catch (InterruptedException ex) {
	                Logger.getLogger(commandExec.class.getName()).log(Level.SEVERE, null, ex);
	            }
	      
	        }
	public void writeFromCommand(String commandName, String filename){
		try 
        {   //Run command
            Process p=Runtime.getRuntime().exec(commandName); 
            //Read output from terminal to console
            BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            String line=null; 
            while((line=reader.readLine())!=null) 
            {	//Print it to java console first, then write to a text file 
                writer.write(line);
                writer.newLine();
            } 
            
                p.waitFor();
                reader.close();
                writer.close();
        } 
        catch(IOException e1) {System.err.println(e1);} 
        catch (InterruptedException ex) {
                Logger.getLogger(commandExec.class.getName()).log(Level.SEVERE, null, ex);
            }
      
	}
}