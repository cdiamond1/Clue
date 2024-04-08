package clueGame;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClueGame extends JFrame {
	
	private static final int WINDOW_WIDTH = 820;
	private static final int WINDOW_HEIGHT = 1000;
	
	public ClueGame() {
		// create display panel object
		JPanel display = new JPanel();
		display.setLayout(new BorderLayout());
		
		// add sub-JPanels
		GameControlPanel temp = new GameControlPanel();
		temp.setSize(602, 100);
		temp.setLocation(0, 600);
		display.add(temp);
		
		GameCardsPanel temp2 = new GameCardsPanel();
		temp2.setSize(200,725);
		temp2.setLocation(602,0);
		display.add(temp2);
//		display.add(new GameBoardPanel(), BorderLayout.CENTER);
		
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		display.add(board, BorderLayout.CENTER);
		
		add(display);
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame(); // create the panel

		game.setSize(WINDOW_WIDTH, WINDOW_HEIGHT); // size the frame
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		game.setVisible(true); // make it visible
	}
}
