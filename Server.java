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
	ArrayList<QCard> quePlayed = new ArrayList<QCard>();
		//Answer Cards
	ArrayList<ACard> ansDeck = new ArrayList<ACard>();

	//-- Clientelle shit
	ServerSocket ssocket;
		//lobby shit
	WaiterThread waiter;
	ArrayList<ServerThread> lobby = new ArrayList<ServerThread>();
	//int readyCount=0;
		//game shit
	ArrayList<ServerThread> activePlayers = new ArrayList<ServerThread>();
	ArrayList<String> playerNames = new ArrayList<String>();
	ACard[] submitted;
	ServerThread questioner;

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

			submitted = new ACard[activePlayers.size()];

			for(int i=3; i>0; i--){
				this.spread("Server message: Game Starting in " + i);
				this.shuffleDecks();
				Thread.sleep(800);
			}

			for(int z=0; z<10; z++){
				for(int i=0; i<activePlayers.size(); i++){
					this.drawCard(i);
				}
			}

			this.spread("start");
			//questioner.send("you question");
			//*
			//while(true){
				// show the question
				this.showQuestion();

				// everyone aside from questioner is allowed to submit
				int nope = activePlayers.indexOf(questioner);
				for(int i=0; i<activePlayers.size(); i++){
					if(i != nope) activePlayers.get(i).send("submit now");
				}

				this.wait();

				this.spread("Server message: Everyone has submitted");

				for(ACard pawn: submitted){
					if(pawn!=null) this.spread("Ans: " + pawn.getValue());
				}

				this.spread("Server message: "+ playerNames.get(activePlayers.indexOf(questioner)) + " can now pick an answer");
			//}
			//*/
		}
	}

	public void shuffleDecks(){
		Collections.shuffle(queDeck);
		Collections.shuffle(ansDeck);
	}

	public void drawCard(int id){
		ACard pawn = ansDeck.remove(0);
		activePlayers.get(id).send("Draw: "+ pawn.getValue());
		//ansInPlay.add(pawn);
	}

	public void showQuestion(){
		QCard pawn = queDeck.remove(0);
		this.spread("Que: " + pawn.getValue());
	}

	public void waitForUser() throws IOException{
		MyConnection c = new MyConnection(ssocket.accept());
		if(lobbyFlag){
			//System.out.println("S: Connected to "+ c.getSocket().getInetAddress());
			lobby.add(new ServerThread(c, lobby.size(), this));
			//this.process("state",-1);
			c.sendMessage("//G");
		}
		else{
			c.sendMessage("S: Server already in play. sorry");
			c.close();
		}
	}

	public void process(String s, int index){
		synchronized(this){
			if(s.startsWith("ready")){
				//remove ready player from lobby
				for(int i=index+1; i<lobby.size(); i++){ lobby.get(i).move(i-1); }
				questioner = lobby.remove(index);
				//add ready player to active
				questioner.move(activePlayers.size());
				activePlayers.add(questioner);
				//brief him on names
				for(String lolz : playerNames){
					questioner.send("Name: " + lolz);
				}
				//spread word of his name
				playerNames.add(s.substring(s.indexOf(" ")+1));
				this.spread("Name: " + playerNames.get(playerNames.size()-1));
				this.spread("Server message: " + playerNames.get(playerNames.size()-1) + " is ready");
				//this.process("state",-1);

				if( activePlayers.size()>=MIN_PLAYERS && activePlayers.size()<=MAX_PLAYERS && lobby.size()==0){
					lobbyFlag = false;
					this.notify();
				}
			}
			/*
			else if(s.equals("state")){
				System.out.println("total clients, ready: "+ activePlayers.size() + ", " + readyCount);
			}
			//*/
			else if(s.equals("draw")){
				this.drawCard(index);
			}
			else if(s.startsWith("Submit: ")){
				submitted[index] = new ACard(s.substring(s.indexOf(" ")+1));
				boolean flag=true;
				for(int i=0; i<activePlayers.size(); i++){
					if(submitted[i]==null && i!=activePlayers.indexOf(questioner)){
						flag = false;
						break;
					}
				}
				this.drawCard(index);
				if(flag){ this.notify(); }
			}
			else if(index>=0 && !s.startsWith("/")){  // normal chat
				this.spread("" + index + ": " + s);
			}

			//spread here
			System.out.println(s+" ~~ " + index);
		}
	}

	public void spread(String spread){
		System.out.println(spread);
		for(ServerThread pawn: activePlayers){
			pawn.send(spread);
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