import java.util.ArrayList;
import java.io.*;
import java.net.*;

public class Client {

    //ListeningThread lr;
    //SpeakingThread sr;
    UnifiedListenSpeakThread slave;
    GUITemplate screen;
    InputThread in;

    //-- LOCAL COPY OF SHIT
    GridMap map;
    Player player;

    public Client(){
        //*
        try {
            //System.out.println("C: Connecting to server...");
            Socket s = new Socket("127.0.0.1", 8888);
            //System.out.println("C: Connected!");

            this.slave = new UnifiedListenSpeakThread(s, this);
            slave.start();
            //this.sr = new SpeakingThread(s, this);
            //sr.start();
        } catch (Exception e) {
            System.out.println("C: Something bad happened :(");
            e.printStackTrace();
        }
        //*/

        this.in = new InputThread(this);
        in.start();
        this.screen = new GUITemplate(this);
        //*/
        screen.go();
    }

    public GridMap getMap(){ return map; }
    public boolean isPlayerHere(int x, int y){
        if(x==player.getX() && y==player.getY()) return true;
        return false;
    }

    //-for testing pursposes
    /*
    public void send(String s){
        System.out.println(s);
        if(s.equals("/quit")) System.exit(0);        
    }
    //*/

    //-LEGIT
    //*
    public void send(String s){ 
        slave.send(s);
        //if(s.equals("/quit")) cleanup();
    }
    //*/

    /*
    public void process(String s){

    }
    //*/

    /*
    public void cleanup(){
        //lr.kill();
        sr.kill();
        System.exit(0);
    }
    //*/

    //*
    public void process(String s){
        if(s.startsWith("Welcome")){
            // SETUP MAP
            String temp = s.substring(s.indexOf("\n")+1) + "\n" ;
            String width = temp.substring(temp.indexOf("W")+1, temp.indexOf("H"));
            String height = temp.substring(temp.indexOf("H")+1, temp.indexOf("\n"));

            map = new GridMap(Integer.parseInt(width), Integer.parseInt(height));
            System.out.println(width + " " + height);
            temp = temp.substring(temp.indexOf("\n")+1);

            String[] lol = temp.split("\n");
            //System.out.println(lol);
            for(String d: lol){
                String[] split = d.split("-");

                int r = Integer.parseInt(split[0]);
                int c = Integer.parseInt(split[1]);
                int status = Integer.parseInt(split[2]);
                map.changeCell(r,c,status);
            }
            // PLAYER SETUP
            player = new Player();
        }
        else if(s.contains("move ")){
            player.move(s.substring(s.lastIndexOf(" ")+1), map);
        }
    }
    //*/

    public static void main(String args[]) {
            Client buttowski = new Client();
    }
}