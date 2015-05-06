import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class ButtonHandlerTemplate extends KeyAdapter{
	// VK_*letter* = letter is pressed

	public ButtonHandlerTemplate(){}

	//unused
	public void keyTyped(KeyEvent arg0){}
    
    public void keyReleased(KeyEvent key) {
		//sample code
		//if(key.getKeyCode() == KeyEvent.VK_A) change left flag to false;
    }
	
	public void keyPressed(KeyEvent key) {
		//sample code
		//if(key.getKeyCode() == KeyEvent.VK_A) change left flag to true;
    }
}