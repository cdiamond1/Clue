package clueGame;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

	private static int turnCount;
	private static int mouseCellX, mouseCellY;
	
	private boolean gameOver = false;
	private static JPanel display = new JPanel();

	public ClueGame() {
		// create display panel object
		display = new JPanel();
		display.setLayout(new BorderLayout());
		display.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getX() < 600 && e.getY() < 600) {
					mouseCellX = (e.getX() - 2) / (board.getPanelWidth() / board.getNumColumns());
					mouseCellY = (e.getY() - 2) / (board.getPanelHeight() / board.getNumRows());
					
					for(BoardCell C : board.getTargets()) {
						if(mouseCellX == C.getColumn() && mouseCellY == C.getRow()) {
							board.getPlayerList().get(turnCount).setPos(mouseCellY, mouseCellX);
							
							board.repaint();
							board.revalidate();
							display.repaint();
							display.revalidate();
							
							controlPanel.nextPressed = true;
						}
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// add sub-JPanels
		controlPanel = new GameControlPanel();
		controlPanel.setSize(602, 175);
		controlPanel.setLocation(0, 600);
		display.add(controlPanel);

		cardsPanel = new GameCardsPanel();
		cardsPanel.setSize(200, 775);
		cardsPanel.setLocation(602, 0);
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
		turnCount = 0;
		int counter = 0;
		while (!game.isGameOver()) {
			// do game stuff
			Player currPlayer = board.getPlayerList().get(turnCount);
			int roll = board.roll();

			if (counter == 0) { // Just to set the first player
				controlPanel.setTurn(currPlayer, roll);
				counter++;

				// Human player stuff
				if (currPlayer.isHuman()) {
					board.calcTargets(board.getCell(currPlayer.getRow(), currPlayer.getColumn()), roll);
					
					for (BoardCell C : board.getTargets()) {
						board.getCell(C.getRow(), C.getColumn()).setTarget(true);
					}
					
					board.repaint();
					board.revalidate();
					display.repaint();
					display.revalidate();
										
				} else { // Computer player stuff
					board.getPlayerList().get(turnCount).selectTarget(roll);

					board.repaint();
					board.revalidate();
					display.repaint();
					display.revalidate();
				}

			}

			if (controlPanel.nextPressed) { // && (!currPlayer.isHuman() && currPlayer.isDone())

				board.repaint();
				board.revalidate();
				display.repaint();
				display.revalidate();

				roll = board.roll();
				controlPanel.setTurn(currPlayer, roll);
				controlPanel.repaint();
				controlPanel.revalidate();

				turnCount = (turnCount + 1) % board.getPlayerList().size();
				counter = 0;
			}
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