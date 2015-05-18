import java.io.*;
import java.net.*;
import java.util.*;

public class ServerThread extends Thread{
	//Guest guest;
	int index;
	MyConnection c;
	boolean loopFlag;
	Server srvr;
	InetAddress ip;

	public ServerThread(MyConnection c, int place, Server main){
		//this.guest = new Guest(c);
		this.c = c;
		this.srvr = main;
		this.index = place;
		loopFlag = true;

        //ip = c.getSocket().getInetAddress();
		//System.out.println("S: Connected to " + ip);

		//setup client for gui
		this.start();
	}

	public void run(){
		while(loopFlag){
			String s = c.getMessage().trim();
			srvr.process(s, this.index);
		}
	}

	public int getIndex(){
		return this.index;
	}

	public void send(String s){
		/*
		String out="";
		String split[] = s.split("\n");
		for(String pawn: split){
			out = out+pawn+" ";
		}
		c.sendMessage(out);
		*/
		c.sendMessage(s);
	}

	public void kill() throws IOException{
		c.close();
		loopFlag = false;
	}

	public void move(){
		this.index--;
	}
}