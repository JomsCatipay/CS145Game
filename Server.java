import java.net.ServerSocket;
import java.io.*;
import java.util.ArrayList;

public class Server{

	
	//-- Card Lists
		//Question Cards
	ArrayList<QCard> queDeck = new ArrayList<QCard>();
	ArrayList<QCard> queInPlay = new ArrayList<QCard>();
	ArrayList<QCard> queDiscard = new ArrayList<QCard>();
		//Answer Cards
	ArrayList<ACard> ansDeck = new ArrayList<ACard>();
	ArrayList<ACard> ansInPlay = new ArrayList<ACard>();
	ArrayList<ACard> ansDiscard = new ArrayList<ACard>();

	//-- Clientelle shit
	ServerSocket ssocket = new ServerSocket(8888);
	ArrayList<ServerThread> connectDB = new ArrayList<ServerThread>();
	int readyCount=0;

	//-- File names
	String qFile = "qtext.txt";
	String aFile = "atext.txt";

	//-- Server Setup
	public Server() throws IOException{
		System.out.println("S: Starting server...");
		//LOAD ALL DECKS
			// questions
    	BufferedReader reader = new BufferedReader(new FileReader(new File(qFile).getAbsolutePath()));
		String deyum;
		while ((deyum = reader.readLine()) != null) {
			//System.out.println(deyum);
			queDeck.add(new QCard(deyum));
		}
			// answers
    	reader = new BufferedReader(new FileReader(new File(aFile).getAbsolutePath()));
		while ((deyum = reader.readLine()) != null) {
			//System.out.println(deyum);
			ansDeck.add(new ACard(deyum));
		}
		System.out.println("S: " + queDeck.size() + " Question cards and " + ansDeck.size() + " Answer cards loaded.");
		System.out.println("S: ... Server is up");
	}

	public void waitForUser() throws IOException{
		MyConnection s = new MyConnection(ssocket.accept());
		connectDB.add(new ServerThread(s, connectDB.size(), this));
	}

	public void process(String s, int index){

	}



	public static void main(String args[]){
		try{
			//prompt to server setup
			Server bitch = new Server();
			System.out.println("S: Waiting for connections...");

			/*
			bitch.waitForUser();
			while(readyCount<connectDB()){

			}
			//*/
			for(int i=3; i>0; i--){
				System.out.println("Game Starting in " + i);
				Thread.sleep(800);
			}
			//*/
		}
		catch(IOException e){ System.out.println("S: IO Exception"); e.printStackTrace();}
		catch(InterruptedException e) { System.out.println("S: Game started early"); }
	}
}