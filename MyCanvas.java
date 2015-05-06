//package testGame.gui;

//import javax.swing.JPanel;

import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;

//import java.awt.geom.*;


class MyCanvas extends JPanel{
	GUITemplate main;

	int width,height;
	//int playerX, playerY;

	public MyCanvas (GUITemplate in) {
		this.main = in;
		//setBackground (Color.GRAY);
		this.width = main.FRAME_WIDTH;
		this.height = main.FRAME_HEIGHT;
		this.setSize(this.width, this.height);
		//canvas.createBufferStrategy(2);
		//canvasBuffer = canvas.getBufferStrategy();
	}

	public void paintComponent (Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		//print grid
		GridMap grid = main.getClient().getMap();

		if(grid!=null){
			int x=20, y=20;
			g2.setColor(new Color(63, 121, 186));

			/*
			if(Library.getInstance().getPlayer()==null){
				playerX = -1;
				playerY = -1;
			}
			else{
				playerX = Library.getInstance().getPlayer().getX();
				playerY = Library.getInstance().getPlayer().getY();
			}
			//*/

			for(int j=0; j<grid.getWidth() ; j++){
				for(int i=0; i<grid.getHeight(); i++){
					int cell = grid.getCell(j,i);
					if(cell==0) g2.fillRect(x,y,10,10);
					if(main.getClient().isPlayerHere(j,i)){
						g2.setColor(new Color(241, 98, 69));					
						g2.fillRect(x,y,10,10);
						g2.setColor(new Color(63, 121, 186));
					}
					y+=20;
				}
				x+=20;
				y=20;
			}
		}
		/*
		g2.drawString ("It is a custom canvas area", this.width/2, this.height/2);
		g2.fillRect(this.width*3/4, this.height*3/4, 40, 40);
		//*/
		main.forceRepaint();
	}
}