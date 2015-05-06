import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server{
	//-- Singleton variable
	private static Server server = null;

	//-- LIBRARIES
	ArrayList<GridMap> mapDB = new ArrayList<GridMap>();
	ArrayList<Player> playerDB = new ArrayList<Player>();
	ArrayList<ServerThread> connectDB = new ArrayList<ServerThread>();

	//-- markers
	int mapmarker=-1;

	//-- Server Setup
	public Server(){
		mapDB = FileHandler.getInstance().loadMaps();

		//prompt to choose active map
	    BufferedReader readr = new BufferedReader(new InputStreamReader(System.in));
	    do{
			System.out.println("Enter map_id to be used: ");
		    try{ mapmarker = Integer.parseInt(readr.readLine()); }
		    catch(IOException e){ e.printStackTrace(); }    	
	    } while(mapmarker<0 || mapmarker>mapDB.size()-1 );


	}

	//-- GET INSTANCE
	public static Server getInstance(){ 
		if(server==null){ server = new Server(); }
		return server;
	}

	//-- Processing client input
	public void process(String s, int srvrId){
		String spread = "";

		if(s.startsWith("move ") ){
			spread = srvrId + ": " + s;
		}

		if(!spread.equals("")){
			//*
			for(ServerThread c : connectDB){
				c.send(spread);
			}
			//*/
			System.out.println(spread);
		}
	}

	public void add(MyConnection c){
		ServerThread s = new ServerThread(c, connectDB.size(), this);

		//-- CLIENT DATA SETUP
		String init = "Welcome";
		GridMap mainMap = mapDB.get(mapmarker);
		init = init + "~~" + "W" + mainMap.getWidth() + "H" + mainMap.getHeight();
		for(int i=0; i<mainMap.getWidth(); i++){
			for(int j=0; j<mainMap.getHeight(); j++){
				init = init + "~~" + i + "-" + j + "-" + mainMap.getCell(i,j);
			}
		}

		connectDB.add(s);
		playerDB.add(new Player());
		
		s.start();
		s.send(init);

		//this.process("Server Message: " + namesDB.get(namesDB.size()-1) + " has connected",-1);
	}

	/*
	public void remove(int id){
		String name = namesDB.remove(id);

		for(int i=id+1; i<connectDB.size();i++) connectDB.get(i).move();

		connectDB.remove(id).kill();
		statusDB.remove(id);

		//this.process("Server Message: " + name + " has disconnected",-1);
	}
	//*/

	public static void main(String[] args){
		System.out.println("S: Starting server...");
		Server.getInstance();

		try{
			ServerSocket ssocket = new ServerSocket(8888);
			System.out.println("S: Waiting for connections...");

			while(true){
				MyConnection s = new MyConnection(ssocket.accept());
				//setup DBs
				Server.getInstance().add(s);
			}
		}catch(Exception e){
            System.out.println("S: Something bad happened :(");
            e.printStackTrace();
		}
	}
}
