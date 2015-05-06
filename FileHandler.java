import java.io.*;
import java.util.ArrayList;

public class FileHandler{
	//-- Singleton variable
	private static FileHandler secretary = null;

	//-- File List
	String mapFile = "maps.ser";

	//-- Streams
	ObjectOutputStream out;
	ObjectInputStream in;

	//-- Handle Setup
	private FileHandler(){}

	public static FileHandler getInstance(){ 
		if(secretary==null){ secretary = new FileHandler(); }
		return secretary;
	}

	public final void saveMaps(ArrayList<GridMap> list){
		try{ 
			FileOutputStream fo = new FileOutputStream(mapFile); 
			out = new ObjectOutputStream(fo);
			out.writeObject(list);
			out.close();
			fo.close();
		}
		catch(FileNotFoundException e) { System.out.println("FileHandler: Map File not Found."); }
		catch(IOException e) { System.out.println("FileHandler: IO exception"); }
	}

	public final ArrayList<GridMap> loadMaps(){
		try{ 
			ArrayList<GridMap> lol;
			FileInputStream fi = new FileInputStream(mapFile); 
			in = new ObjectInputStream(fi);
			lol = (ArrayList<GridMap>) in.readObject();
			in.close();
			fi.close();
			return lol;
		}
		catch(FileNotFoundException e) { System.out.println("FileHandler: Map File not Found."); }
		catch(IOException e) { System.out.println("FileHandler: IO exception"); }
		catch(ClassNotFoundException e){ System.out.println("FileHandler: Class Not Found exception"); }
		return null;
	}
}