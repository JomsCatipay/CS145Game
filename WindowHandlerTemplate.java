import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowHandlerTemplate extends WindowAdapter{
	GUITemplate screen;

	public WindowHandlerTemplate(GUITemplate screen){
		this.screen = screen;
	}		

	public void windowClosing(WindowEvent e) {
		screen.frame.setVisible(false);
		System.exit(0);
	}

	
}
