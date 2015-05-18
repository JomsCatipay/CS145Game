import java.net.ServerSocket;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Server{
	//-- CONSTANTS
	static int MIN_PLAYERS=1, MAX_PLAYERS=12;
	
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
	ServerSocket ssocket;
	ArrayList<ServerThread> connectDB = new ArrayList<ServerThread>();
	int readyCount=0;
	WaiterThread waiter;

	//-- File names
	String qFile = "qtext.txt";
	String aFile = "atext.txt";

	//-- Flags
	boolean lobbyFlag;

	//-- Server Setup
	public Server() throws IOException{
		System.out.println("S: Starting server...");

		//SERVER BASICS
		ssocket = new ServerSocket(8888);
		//ssocket.setSoTimeOut(5000);

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

	public void go() throws InterruptedException{
		synchronized(this){
			//Setup Lobby
			waiter = new WaiterThread(this);
			lobbyFlag = true;
			waiter.start();
			this.wait();

			for(int i=3; i>0; i--){
				this.process("start " + i,-1);
				this.shuffleDecks();
				Thread.sleep(800);
			}

			for(int z=0; z<9; z++){
				for(int i=0; i<connectDB.size(); i++){
					this.drawCard(i);
				}
			}

			this.process("game",-1);
		}
	}

	public void shuffleDecks(){
		Collections.shuffle(queDeck);
		Collections.shuffle(ansDeck);
	}

	public void drawCard(int id){
		ACard pawn = ansDeck.remove(0);
		connectDB.get(id).send("Card: "+ pawn.getValue());
		ansInPlay.add(pawn);
	}

	public void showQuestion(){
		//this.process();
	}

	public void waitForUser() throws IOException{
		MyConnection c = new MyConnection(ssocket.accept());
		if(lobbyFlag){
			//System.out.println("S: Connected to "+ c.getSocket().getInetAddress());
			connectDB.add(new ServerThread(c, connectDB.size(), this));
			//this.process("state",-1);
		}
		else{
			c.sendMessage("S: Server already in play. sorry");
		}
	}

	public void process(String s, int index){
		synchronized(this){
			String spread="";

			if(s.equals("ready")){
				readyCount++;
				if( connectDB.size()>=MIN_PLAYERS && connectDB.size()<=MAX_PLAYERS && readyCount==connectDB.size() ){
					lobbyFlag = false;
					this.notify();
				}
				spread = "Server message: " + index + " is ready";
				//this.process("state",-1);
			}
			else if(s.equals("state")){
				System.out.println("total clients, ready: "+ connectDB.size() + ", " + readyCount);
			}
			else if(s.startsWith("start")){
				spread = "Server message: Game Starting in " + s.substring(s.indexOf(" ")+1);
			}
			else if(s.equals("draw")){
				this.drawCard(index);
			}
			else{
				spread = "" + index + ": " + s;
			}

			//spread here
			if(!spread.equals("")){
				System.out.println(spread);
				for(ServerThread pawn: connectDB){
					pawn.send(spread);
				}
			}
		}
	}

	public static void main(String args[]){
		try{
			//prompt to server setup
			Server bitch = new Server();

			//launch game
			bitch.go();			
		}
		catch(IOException e){ System.out.println("S: IO Exception"); e.printStackTrace();}
		catch(InterruptedException e) { System.out.println("S: Game started early"); }
	}
}