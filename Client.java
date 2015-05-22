import java.net.*;
import java.util.ArrayList;
import javax.swing.*;

public class Client{	

	ArrayList<ACard> hand = new ArrayList<ACard>();
	ArrayList<ACard> submitted = new ArrayList<ACard>();
	String q;
	boolean goToSubmit;
	boolean questioner;
	String name, chosen, wincon;
	int score;
	ImageIcon ico;
	ArrayList<String> playerNames = new ArrayList<String>();
	int[] points;

	MyConnection c;
	ListeningThread lr;

	Game screen;

	public Client(){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e){
			e.printStackTrace();
		}

		score = 0;
		try {
			System.out.println("C: Connecting to server...");
			String ipv = null;
			while(ipv==null){ ipv = JOptionPane.showInputDialog("Connect to IP at:"); }
			c = new MyConnection(new Socket(ipv, 8888));
			System.out.println("C: Connected!");

			this.lr = new ListeningThread(c, this);
		} catch (Exception e) {
			System.out.println("C: Something bad happened :(");
			e.printStackTrace();
		}

		System.out.println("C: Firing up Threads");
		lr.start();
	}

	public void readyPrompt(){
		String s = null;
		while(s==null){ s = JOptionPane.showInputDialog("Enter Your Name:"); }
		this.name = s;
		//c.sendMessage("ready " + s);
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

	public void sendChat(String s){
		if(s.startsWith("/changeName")){
			this.name = s.substring(s.indexOf(" ")+ 1);
			screen.showChat("Changed Name to " + this.name);
		}
		else if(s.startsWith("/ready")){
			c.sendMessage("ready " + name);
		}
		else c.sendMessage("/chat " + s);
	}
	
    public void close(){
        c.sendMessage("/kill");
        lr.kill();
        screen.setVisible(false);
        System.exit(0);
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
		    screen.showChat(s.substring(s.indexOf(" ")+1));
		}
		else if(s.startsWith("Name: ")){
			for(String asd: playerNames){
				if(s.substring(s.indexOf(" ")+1).equals(asd)) return;
			}
		    playerNames.add(s.substring(s.indexOf(" ")+1));
		    points = new int[playerNames.size()];
		    screen.updateList(playerNames, points);
		}
		else if(s.startsWith("Draw: ") && hand.size()<10){
		    hand.add(new ACard(s.substring(s.indexOf(" ")+1)));
		}
		else if(s.startsWith("Que: ")){
		    q = s.substring(s.indexOf(" ")+1);
		    screen.paintpls();
		}
		else if(s.startsWith("Ans: ")){
		    submitted.add(new ACard(s.substring(s.indexOf(" ")+1)));
		}
		else if(s.equals("submit now")){
		    goToSubmit = true;
		}
		else if(s.equals("you question")){
		    questioner = true;
		    screen.paintpls();
		}
		else if(s.equals("clear")){
		    submitted.clear();
		}
		else if(s.startsWith("Chosen Answer:")){
		    chosen = s.substring(15);
		}
		else if(s.endsWith("can now pick an answer")){
		    int len = submitted.size();
		    if(questioner){
		        String[] choices = new String[len];
		        for(int i=0 ; i<len ; i++){
		            choices[i] = (submitted.remove(0)).getValue();
		        }
		        screen.chooseAnswer(choices,q);
		    }
		    else{
		        String[] choices = new String[len];
		        for(int i=0 ; i<len ; i++){
		            choices[i] = (submitted.remove(0)).getValue();
		        }
		        screen.showAnswers(choices,q);
		    }
		}
		else if(s.equals("start")){
			screen.showGame();
		}
		else if(s.startsWith("wincon ")){
		    wincon = s.substring(7);
		    screen.paintpls();
		}
		else if(s.equals("//G")){
		    this.readyPrompt();
		    screen = new Game(this);
		    screen.setVisible(true);
		    screen.showChat("Send \"/ready\" whenever");
		}
		else if(s.endsWith(" got the point")){
		    String msg = s.substring(0, s.lastIndexOf(" got the point"));
		    if(msg.equals(this.name)) this.score++;
		    screen.showChosen(msg, chosen);
		    chosen = null;

		    points[ playerNames.indexOf(msg) ]++;
		    screen.updateList(playerNames, points);
		}
		else if(s.startsWith("winner ")){
		    String msg = s.substring(7);
		    screen.showWin(msg);
		}
	}

	public static void main(String args[]) {
	        Client lolol = new Client();
	}
}