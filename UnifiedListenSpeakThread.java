import java.net.Socket;

public class UnifiedListenSpeakThread extends Thread{
	boolean loopFlag;
	MyConnection c;
	Client parent;

	public UnifiedListenSpeakThread(Socket s, Client main){
		this.c = new MyConnection(s);	
		this.parent = main;

		loopFlag = true;
	}

	public void run(){
		while(loopFlag){
			String lol = c.getMessage().trim();
			//System.out.println(lol);
			parent.process(lol);
		}
	}

	public void send(String s){
		c.sendMessage(s);
	}

	public void kill(){
		loopFlag = false;
	}
}
