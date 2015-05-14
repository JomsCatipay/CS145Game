import java.io.*;
import java.net.*;

public class MyConnection {
	Socket s;
    PrintWriter out;
    BufferedReader in;

	public MyConnection (Socket s){
		this.s = s;
		try{
		    OutputStream os = s.getOutputStream();
		    OutputStreamWriter osw = new OutputStreamWriter(os);
		    out = new PrintWriter(osw);

			InputStream is = s.getInputStream();
	        InputStreamReader isr = new InputStreamReader(is);
			in = new BufferedReader(isr);
		}catch (IOException e){
			System.out.println("Error Setting up");
		}
	}

	public boolean sendMessage(String msg){
        String j[] = msg.split("~~");
        for(String i: j){
	    	out.println(i);
        }
        out.println("~END");
        out.flush();
		return true;
    }

	public String getMessage(){
        String msg = "";
		try{
            while(!msg.contains("~END")){
            	String s = in.readLine();
	        	msg = msg + s + "\n";
	        	//System.out.println("yo");
            }
	    } catch (IOException e){
	    	System.out.println("Error recieving");
	    }
		return msg.substring(0,msg.indexOf("~END"));
		//return msg;
	}

	public Socket getSocket(){ return s; }
	public void close() throws IOException{
		out.close();
		in.close();
	}
}