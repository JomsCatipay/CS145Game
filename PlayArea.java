import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class PlayArea extends JPanel{

	private static Card selected;
	private static int xco, yco, inx, iny;
	private static JLabel text;
	private static String labeltext;
	private ACard[] hand;

	private Image ac_img, qc_img, bg_img, qc_bck, ac_bck, selimg;

	private boolean isDragging;

	// public PlayArea(Client self){
	public PlayArea(){
		selected = null;

		readImages();

		addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				xco = e.getX();
				yco = e.getY();
				cardCheck(MouseEvent.MOUSE_RELEASED);
			}
		});
	}

	public void readImages(){
		try{
			ac_img = ImageIO.read(new File("img\\acard.png"));
			qc_img = ImageIO.read(new File("img\\qcard.png"));
			bg_img = ImageIO.read(new File("img\\bg.png"));
			ac_bck = ImageIO.read(new File("img\\aback.png"));
			qc_bck = ImageIO.read(new File("img\\qback.png"));
			selimg = ImageIO.read(new File("img\\sel.png"));
		} catch(Exception e){ e.printStackTrace(); }
	}

	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		g2d.drawImage(bg_img, 0, 0, this);
		
		this.hand = new ACard[10];
		for(int i=0 ; i<10 ; i++){
			String x = "Card #" + (i+1);
			hand[i] = new ACard(x);
		}
		// this.hand = me.hand

		int x = 50;
		for(int i=0 ; i<10 ; i++){
			if(hand[i]!=null){
				g2d.drawImage(ac_img, x, 336, this);
				hand[i].setRect(new Rectangle2D.Double(x,336,70,100));
				if(selected != null){
					if(hand[i].equals(selected)){
						System.out.println(hand[i].getValue() + " is selected");
						g2d.drawImage(selimg, x, 336, this);
					}
				}
			}
			x += 70;
		}
		g2d.drawImage(ac_bck, 309, 92, this);		// answers desk
		g2d.drawImage(qc_bck, 421, 92, this);		// questions desk

	}

	public void cardCheck(int m){
		Card temp = null, hold = null;
		if(m == MouseEvent.MOUSE_RELEASED){
			// check if clicked submitted area
			//
			// else must have selected another card
			selected = null;
			System.out.println("Resetted");
			repaint();

			for(int i=0 ; i<10 ; i++){
				if(hand[i].getRect().contains(xco,yco)){
					labeltext = hand[i].getValue();
					selected = hand[i];
					repaint();
				}
			}
		}
	}

}