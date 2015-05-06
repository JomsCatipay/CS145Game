import java.io.*;
import java.net.*;

public class InputThread extends Thread{
    BufferedReader readr = new BufferedReader(new InputStreamReader(System.in));

	boolean loopFlag, editFlag;
	Client main;

	public InputThread(Client in){
		this.main = in;
		loopFlag = true;
		editFlag = true;
	}

	public void run(){
		while(loopFlag){
			try{
				String s = readr.readLine();
				main.send(s);
			} catch(IOException e){ e.printStackTrace(); }
		}
	}

    //*
    public void process(String s){
        /*
        if(s.startsWith("new")){
            Library.getInstance().createMap();
        }
        else if(s.startsWith("changeCell ")){
            if(!editFlag){
                System.out.println("cannot edit map when in game");
                return;
            }
            String pawn = s.substring(s.indexOf(' ')+1);
            //System.out.println(pawn);
            int x = Integer.parseInt(pawn.substring(0,pawn.indexOf(" ")));
            pawn = pawn.substring(pawn.indexOf(" ")+1);
            //System.out.println(pawn);
            int y = Integer.parseInt(pawn.substring(0,pawn.indexOf(" ")));
            pawn = pawn.substring(pawn.indexOf(" ")+1);
            //System.out.println(pawn);
            int state = Integer.parseInt(pawn);
            Library.getInstance().getMap().changeCell(x,y,state);
        }
        else if(s.startsWith("save")){
            Library.getInstance().saveMaps();
        }
        else if(s.startsWith("load")){
            Library.getInstance().loadMaps();
        }
        else if(s.equals("start game")){
            player = Library.getInstance().startGame();
            editFlag = false;
        }
        else if(s.startsWith("get_map ")){
            Library.getInstance().changeMap(Integer.parseInt(s.substring(s.indexOf(" ")+1)));
        }
        else if(s.equals("end game")){
            Library.getInstance().endGame();
            editFlag = true;
        }
        else if(s.startsWith("move")){
            Library.getInstance().getPlayer().move(s.substring(s.indexOf(" ")+1));
        }
        else if(s.equals("/kill")){
            loopFlag = false;
            System.exit(0);
        }
        //*/

        
    }
    //*/
}