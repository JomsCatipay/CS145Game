//import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Client{	

    //-- Hand
    ArrayList<ACard> hand = new ArrayList<ACard>();
        //submitted answers
    ArrayList<ACard> submitted = new ArrayList<ACard>();
        //question
    String q;
        //submit answr flag
    boolean goToSubmit;
    boolean questioner;
    String name;
    int score;  // not really

    ArrayList<String> playerNames = new ArrayList<String>();

    MyConnection c;
    ListeningThread lr;
    //SpeakingThread sr;

    Game screen;

    public Client(){
        //*
        score = 0;
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
        String s = JOptionPane.showInputDialog("Enter Your Name:");
        this.name = s;
        c.sendMessage("ready " + s);
    }

    public ACard getCard(int i){
        if(i<hand.size()) return hand.get(i);
        return null;
    }

    public void submit(ACard p){
        if(goToSubmit){
            hand.remove(p);
            c.sendMessage("Submit: " + p.getValue());
            goToSubmit = false;
        }
    }

    public void choose(String s){
        c.sendMessage("Choice: " + s);
        while(submitted.size()>0) submitted.remove(0);
        questioner = false;
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
        if(s.startsWith("Chat: ")){

        }
        else if(s.startsWith("Name: ")){
            playerNames.add(s.substring(s.indexOf(" ")+1));
        }
        else if(s.startsWith("Draw: ") && hand.size()<10){
            hand.add(new ACard(s.substring(s.indexOf(" ")+1)));
        }
        else if(s.startsWith("Que: ")){
            q = s.substring(s.indexOf(" ")+1);
        }
        else if(s.startsWith("Ans: ")){
            submitted.add(new ACard(s.substring(s.indexOf(" ")+1)));
        }
        else if(s.equals("submit now")){
            goToSubmit = true;
        }
        else if(s.equals("you question")){
            questioner = true;
        }
        else if(s.contains("start")){
            screen = new Game(this);
            screen.setVisible(true);
        }
        else if(s.equals("//G")){
            this.readyPrompt();
        }
        else if(s.endsWith("can now pick an answer")){
            if(questioner){
                int len = submitted.size();
                String[] choices = new String[len];
                for(int i=0 ; i<len ; i++){
                    choices[i] = (submitted.remove(0)).getValue();
                }
                screen.chooseAnswer(choices,q);
            }
        }
    }

    public static void main(String args[]) {
            Client lolol = new Client();
    }
}