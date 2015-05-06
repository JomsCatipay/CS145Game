import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.JOptionPane;

public class SpeakingThread extends Thread{
	boolean loopFlag;
	MyConnection c;
	Client parent;
	//ClientGUI screen;
	String out;

	public SpeakingThread(Socket s, Client main){
		this.c = new MyConnection(s);
		this.parent = main;
		this.out = "";

		this.loopFlag = true;

		//this.screen = new ClientGUI(this);
	}

	public void run(){
		synchronized(this){		
			while(loopFlag){
				try{ this.wait(); }
				catch(InterruptedException e) {}

				if(!out.equals("")){
					c.sendMessage(out);
				}
			}
		}
	}

	public void send(String s){
		synchronized(this){
			/*
			this.out="";
			String split[] = s.split("\n");
			for(String pawn: split){
				this.out = this.out+pawn+"~~";
			}
			*/
			this.out = s;
			this.notify();
		}
	}

	public void kill(){ loopFlag = false; }
}