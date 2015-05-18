import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public abstract class Card{

	String value;
	private int xco, yco, width, height;
	private boolean selected;
	private Rectangle2D rect;
	transient private Image img;

	public Card(){
		width = 70;
		height = 100;
	}

	public String getValue(){ return value; }

	public int getX(){ return this.xco; }
	public int getY(){ return this.yco; }
	public void setLoc(int x, int y){
		this.xco = x;
		this.yco = y;
	}

	public void select(){ this.selected = true; }
	public void deselect(){ this.selected = false; }
	public boolean isSelected(){ return this.selected; }
	
	public void paint(Graphics g, JComponent c){
		g.drawImage(img, xco, yco, c);
	}

	public void setImage(int x){
		if(x==0) img = Toolkit.getDefaultToolkit().getImage("images\\acard.png");
		if(x==1) img = Toolkit.getDefaultToolkit().getImage("images\\qcard.png");
	}
	public Image getImage(){ return this.img; }

	public Rectangle2D getRect(){ return this.rect; }
	public void setRect(Rectangle2D rect){ this.rect = rect; }

}