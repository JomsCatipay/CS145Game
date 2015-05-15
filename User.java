import javax.swing.JOptionPane;

public class User{
	
	public static void main(String args[]){
		System.out.println("Hai");

		ACard hand[] = new ACard[10];
		for(int i=0 ; i<10 ; i++){
			hand[i] = new ACard("Card " + Integer.toString(i));
		}

		for(int i=0 ; i<10 ; i++){
			System.out.println("(" + ((char)('A'+i)) + ") " + hand[i].getValue());
		}

		JOptionPane pane = new JOptionPane();
		pane.setMessageType(JOptionPane.QUESTION_MESSAGE);
		String msg = JOptionPane.showInputDialog("Select card");

		char input = msg.charAt(0);
		int x = Integer.valueOf(input);
		if(x >= 'A' && x <= 'J'){
			System.out.println("You selected " + hand[(x-'A')].getValue());
		}
		else System.out.println("You did not select a valid card");
	}

}