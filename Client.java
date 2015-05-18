//import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Client{	

    //-- Hand
    ArrayList<ACard> hand = new ArrayList<ACard>();

    boolean questioner;

    MyConnection c;
    ListeningThread lr;
    //SpeakingThread sr;

    Game screen;

    public Client(){
        //*
        try {
            System.out.println("C: Connecting to server...");
            c = new MyConnection(new Socket("127.0.0.1", 8888));
            System.out.println("C: Connected!");

            this.lr = new ListeningThread(c, this);
            //this.sr = new SpeakingThread(s, this);
        } catch (Exception e) {
            System.out.println("C: Something bad happened :(");
            e.printStackTrace();
        }

        System.out.println("C: Firing up Threads");
        lr.start();
        //sr.start();
        //*/
    }

    public void readyPrompt(){
        String s = JOptionPane.showInputDialog("Are you ready?");
        c.sendMessage(s);
    }

    public ACard getCard(int i){
        if(i<hand.size()) return hand.get(i);
        return null;
    }

    public void process(String s){
        /*
        if(s.startsWith("//")){
            if(s.contains("play")){
                int i = Integer.parseInt()
            }
            else{
                for(ACard pawn: hand){
                    System.out.println(pawn.getValue());
                }
            }
        }
        */
        if(s.startsWith("Card: ") && hand.size()<10){
            hand.add(new ACard(s.substring(s.indexOf(" ")+1)));
        }
        else if(s.equals("you question")){
            questioner = true;
        }
        else if(s.contains("game")){
            screen = new Game(this);
            screen.setVisible(true);
        }
    }

    public static void main(String args[]) {
            Client lolol = new Client();
            lolol.readyPrompt();
    }
}