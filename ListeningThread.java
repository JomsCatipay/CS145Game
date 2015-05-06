import java.io.*;
import java.net.*;
import java.util.*;

public class ListeningThread extends Thread{
	boolean loopFlag;
	MyConnection c;
	Client parent;

	public ListeningThread(Socket s, Client main){
		this.c = new MyConnection(s);	
		this.parent = main;

		loopFlag = true;
	}

	public void run(){
		while(loopFlag){
			//System.out.println(c.getMessage().trim());
			parent.process(c.getMessage().trim());
		}
	}

	public void kill(){
		loopFlag = false;
	}
}