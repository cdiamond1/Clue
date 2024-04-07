package clueGame;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClueGame extends JFrame {
	
	private static final int WINDOW_WIDTH = 1000;
	private static final int WINDOW_HEIGHT = 1000;
	
	public ClueGame() {
		
	}
	
	
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame(); // create the panel

		game.setSize(WINDOW_WIDTH, WINDOW_HEIGHT); // size the frame
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		game.setVisible(true); // make it visible
	}
}
