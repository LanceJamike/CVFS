<%@ page language="java"
import= "java.io.*,java.util.*" 
contentType="text/html;charset=EUC-KR" session="false" 
%>
<html>
<%			
try 
	        {  	
	    		String line=null;
String[] cmd = {
"/bin/sh",
"-c",
"lvdisplay"
};

			
	            ProcessBuilder builder = new ProcessBuilder(cmd);
		    builder.redirectErrorStream(true);
		    Process process = builder.start();
	            BufferedReader reader=new BufferedReader(new InputStreamReader(process.getInputStream()));
	            while((line=reader.readLine())!=null) 
	            {	
			
	                out.println(line); %> <br><%
	            } 
	                process.waitFor();
	                reader.close();    
	 

    }catch(IOException ie){
        ie.printStackTrace();
    }catch(Exception e){
        e.printStackTrace();
    }
%>
</html>
