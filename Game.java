import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.ArrayList;

public class Game extends JFrame{
	
	private JPanel pane;
	private JTextArea window;
	private JTextArea list;
	private JTextArea chat;
	private JButton sbutton;
	private PlayArea area;
	private JScrollPane areaS, listS, chatS;
	private Client client;
	private ImageIcon ico;

	public Game(Client client){
		this.client = client;

		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			Image x = ImageIO.read(new File("img\\ico.png"));
			ico = new ImageIcon(x);
		} catch(Exception e){
			e.printStackTrace();
		}

		Font ft = new Font("Calibri", Font.PLAIN, 12);

		window = new JTextArea(7, 60);
		window.setEditable(false);
		window.setWrapStyleWord(true);
		window.setLineWrap(true);
		window.setFont(ft);

		areaS = new JScrollPane(window);
		areaS.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		list = new JTextArea(7, 15);
		list.setEditable(false);
		list.setFont(ft);
		listS = new JScrollPane(list);
		listS.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		chat = new JTextArea(4,10);
		chat.setFont(ft);
		chatS = new JScrollPane(chat);
		chatS.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		addWindowListener( new WindowAdapter() {
			public void windowOpened( WindowEvent e ){
				chat.requestFocus();
			}
			public void windowClosing(WindowEvent e){
				// conn.sendMessage("/quit");
			}
		}); 

		pane = new JPanel();
		pane.setSize(800,300);
		pane.setLayout(new GridBagLayout());
		GridBagConstraints gb = new GridBagConstraints();
		gb.gridx = 0;
		gb.gridy = 0;
		gb.insets = new Insets(5,5,5,5);

		pane.add(new JLabel("Chat Window"), gb);
		gb.gridx = 6;
		pane.add(new JLabel("Online Clients"), gb);

		gb.gridx = 0;
		gb.gridy++;
		gb.gridwidth = 2;
		gb.fill = GridBagConstraints.BOTH;
		pane.add(areaS, gb);
		gb.gridx = 6;
		gb.gridwidth = 2;
		pane.add(listS, gb);

		gb.gridx = 0;
		gb.gridy = 3;
		gb.gridheight = 1;
		gb.gridwidth = 6;
		gb.fill = GridBagConstraints.HORIZONTAL;
		pane.add(chatS, gb);

		sbutton = new JButton("Send");
		sbutton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				String msg = chat.getText();
				chat.setText("");
				// conn.sendMessage(msg);
				client.sendChat(msg);
				chat.requestFocus();
			}
		});
		gb.gridx = 6;
		gb.gridwidth = 2;
		gb.gridheight = 2;
		pane.add(sbutton, gb);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLayout(new BorderLayout());

		area = new PlayArea(client);
		area.setSize(800, 400);
		area.setVisible(true);
		area.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(area);
		add(pane, BorderLayout.PAGE_END);

		setSize(800,700);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Cards Againt Humanity OnLAN.");
	}

	public void showChat(String s){
		window.append(s+"\n");
		window.setCaretPosition(window.getDocument().getLength());
	}

	public void updateList(ArrayList<String> a, int[] b){
		String out = "";
		for(int i=0; i<a.size(); i++){
			out = out + a.get(i) + ": " + b[i] + "\n";
		}
		list.setText(out);
	}

	public void chooseAnswer(String[] choices, String question){
		Object[] answers = (Object[]) choices;
		String s = (String) JOptionPane.showInputDialog(this, question, "Choose an answer", JOptionPane.QUESTION_MESSAGE, ico, answers, answers[0]);

		if ((s != null) && (s.length() > 0)) {
			System.out.println("You have chosen: " + s);
			client.choose(s);
		}

	}

	public void showAnswers(String[] choices, String question){			// show answers to non-questioner
		String label = "\"" + question + "\"\n";
		for(int i=0 ; i<choices.length ; i++){
			label += "\n" + choices[i];
		}
		JOptionPane.showMessageDialog(this, label, "And the choices are...", JOptionPane.INFORMATION_MESSAGE, ico);
	}

	public void showChosen(String winner, String ans){			// show winner to round
		String label = winner + " won that round!\nTheir answer was:\n";
		label += "\"" + ans + "\"";
		JOptionPane.showMessageDialog(this, label, "And the point goes to...", JOptionPane.INFORMATION_MESSAGE, ico);
	}

	public void paintpls(){ area.repaint(); }

}