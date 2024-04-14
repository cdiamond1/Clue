package clueGame;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClueGame extends JFrame {
	
	private static Board board = Board.getInstance();
	private static GameControlPanel controlPanel;
	private static GameCardsPanel cardsPanel;
	
	private static final int WINDOW_WIDTH = 820;
	private static final int WINDOW_HEIGHT = 1000;
	
	private final static String SPLASH_TITLE = "Welcome to Clue";
	private final static String SPLASH_CONTENT = "You are Wolfgang Gwawl.\nCan you find the solution\nbefore the computer players?";
	
	private boolean gameOver = false;
	
	public ClueGame() {
		// create display panel object
		JPanel display = new JPanel();
		display.setLayout(new BorderLayout());
		
		// add sub-JPanels
		controlPanel = new GameControlPanel();
		controlPanel.setSize(602, 175);
		controlPanel.setLocation(0, 600);
		display.add(controlPanel);
		
		cardsPanel = new GameCardsPanel();
		cardsPanel.setSize(200,775);
		cardsPanel.setLocation(602,0);
		display.add(cardsPanel);
		
		
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		display.add(board, BorderLayout.CENTER);
		
		add(display);
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame(); // create the panel

		game.setResizable(false); // Players not allowed to change panel size as to not mess with the proportions
		game.setSize(WINDOW_WIDTH, WINDOW_HEIGHT); // size the frame
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		game.setVisible(true); // make it visible
		
		// welcome splash message
		JOptionPane splash = new JOptionPane();
		splash.showMessageDialog(null, SPLASH_CONTENT, SPLASH_TITLE, JOptionPane.INFORMATION_MESSAGE);
		
		// run turns
		int turnCount = 0;
		while (!game.isGameOver()) {
			// do game stuff
			Player currPlayer = board.getPlayerList().get(turnCount);
			
			controlPanel.setTurn(currPlayer, board.roll());
			controlPanel.repaint();
			
			// process NEXT button
			
			turnCount = (turnCount + 1) % board.getPlayerList().size();
		}
	}

	
	// GETTERS & SETTERS
	
	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
}
