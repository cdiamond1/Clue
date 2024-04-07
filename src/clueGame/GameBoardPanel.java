package clueGame;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameBoardPanel extends JPanel {
	
	private static Board board;
	
	public GameBoardPanel() {
		JPanel test = new JPanel();
	}

	public static void main(String[] args) {
		GameBoardPanel panel = new GameBoardPanel(); // create the panel
		JFrame frame = new JFrame();
		
		board = Board.getInstance();
		
		frame.setContentPane(board); // put the panel in the frame
		frame.setSize(570, 570); // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
	}
}
