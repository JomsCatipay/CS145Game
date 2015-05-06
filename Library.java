import java.util.ArrayList;

public class Library{
	//-- Singleton variable
	private static Library lib = null;

	//-- Libraries
	ArrayList<GridMap> maps = new ArrayList<GridMap>();
	ArrayList<Player> players = new ArrayList<Player>(); //future use
	//Player player = null;

	//-- markers
	int mapMarker=-1;

	//-- Library Setup
	private Library(){}

	//-- Library Methods
		// Static method to get Singleton instance
	public static Library getInstance(){ 
		if(lib==null){
			lib = new Library();
		}
		return lib;
	}
		// GETTERS
	public GridMap getMap(){
		if(mapMarker>-1) return this.maps.get(mapMarker);
		//else System.out.println("Library: No Maps loaded");
		return null;
	}
	/*
	public Player getPlayer(){
		if(player==null) return null;
		return player;
	}
	//*/
	public Player getPlayer(int id){
		if(0<id && id<players.size()) return players.get(id);
		return null;
	}
		// SETTERS
	public void changeMap(int n){
		if(n<0 || n>=maps.size()){
			System.out.println("Library: Map Index out of Bounds");
		}
		else {
			mapMarker = n;
		}
	}
		// ADDERS
	public void createMap(){
		this.maps.add(new GridMap(10,10));
		mapMarker = maps.size()-1;
	}
		// Game Starts
	/*
	public int startGame(){
 		players.add(new Player());

 	}
 		// Game Ends
 	public void endGame(){
 		player = player.kill();
 	}
 	*/

		// FILE Handling
			// Writing
	public void saveMaps(){
		FileHandler.getInstance().saveMaps(maps);
	}
			// Reading
	public void loadMaps(){
		ArrayList<GridMap> temp = FileHandler.getInstance().loadMaps();
		for(GridMap x: temp){ maps.add(x); }
		mapMarker=0;
	}
}