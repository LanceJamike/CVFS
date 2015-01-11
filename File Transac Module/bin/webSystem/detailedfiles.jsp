<%@ page language="java"
import= "java.io.*,java.util.*" 
contentType="text/html;charset=EUC-KR" session="false" 
%>
<html>
<%			
try 
	        {  	
	    		String line=null;
String command = "/mnt/share/";
String cmd = "ls";


			ProcessBuilder builder = new ProcessBuilder(cmd,command);
		    builder.redirectErrorStream(true);
		    Process process = builder.start();
	           BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

int i = 0;
String line2 = null;
while( (line = br.readLine())!= null ){
        
    String [] tokens = line.split("\\s+");
    String var_i = tokens[0];
    out.println(line);

			ProcessBuilder builder2 = new ProcessBuilder("stat", command+line);
		    builder2.redirectErrorStream(true);
		    Process process2 = builder2.start();
	           BufferedReader reader = new BufferedReader(new InputStreamReader(process2.getInputStream()));%><br><%
while( (line2 = reader.readLine())!= null 	){
out.println(line2);
i++;%>  <br><%
} %><br><%
}
 br.close();

    }catch(IOException ie){
        ie.printStackTrace();
    }catch(Exception e){
        e.printStackTrace();
    }
%>
</html>
