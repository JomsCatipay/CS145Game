import java.io.IOException;

public class WaiterThread extends Thread{
	Server main;

	public WaiterThread(Server in){
		this.main = in;
	}

	public void run(){
		System.out.println("S: Waiting for connections...");
		try{
			while(true){
				main.waitForUser();
			}
		}
		catch(IOException e){ System.out.println("S: IO Exception"); e.printStackTrace();}
	}
}