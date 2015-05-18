import java.io.*;
import java.net.*;
import java.util.*;

public class ListeningThread extends Thread{
	MyConnection c;
	Client parent;

	public ListeningThread(Socket s, Client main){
		this.c = new MyConnection(s);	
		this.parent = main;
	}

	public ListeningThread(MyConnection s, Client main){
		this.c = s;	
		this.parent = main;
	}

	public void run(){
		while(true){
			String lol = c.getMessage().trim();
			System.out.println(lol);
			parent.process(lol);
		}
	}
}