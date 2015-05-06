//package testGame.gui;

//import javax.swing.JFrame;

import javax.swing.JFrame;

public class GUITemplate{
	int FRAME_WIDTH = 300, FRAME_HEIGHT = 300;

	MyCanvas canvas;
	JFrame frame;

	Client client;
	//Library lib;

	public GUITemplate(Client in){
		//lib = Library.getInstance();
		client = in;

		frame = new JFrame();
		frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		frame.addWindowListener(new WindowHandlerTemplate(this) );

		canvas = new MyCanvas(this);

		frame.setContentPane(canvas);
	}

	public void go(){ frame.setVisible(true); }

	public Client getClient(){ return client; }

	public void forceRepaint(){
		canvas.repaint();
		frame.repaint();
	}
}