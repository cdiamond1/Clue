package clueGame;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

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
	private final static String ERROR_TITLE = "Wrong cell choice";
	private final static String ERROR_CONTENT = "You can't move there.\nPick a different cell.";

	private final static String WIN_TITLE = "Congratulations!";
	private final static String WIN_CONTENT = "Congratulations you successfully found the murder! You win :)";
	private final static String LOSE_TITLE = "Uh Oh!";
	private final static String LOSE_CONTENT = "The murder got away! You Lose :(";

	private static int turnCount;
	private static int mouseCellX, mouseCellY;

	private static Player currPlayerSuggest;

	private static boolean gameOver = false;
	private static JPanel display = new JPanel();

	public ClueGame() {
		// create display panel object
		display = new JPanel();
		display.setLayout(new BorderLayout());
		display.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				boolean targetPicked = false;

				if (e.getX() < 600 && e.getY() < 600) {
					mouseCellX = (e.getX() - 2) / (board.getPanelWidth() / board.getNumColumns());
					mouseCellY = (e.getY() - 2) / (board.getPanelHeight() / board.getNumRows());

					for (BoardCell C : board.getTargets()) {
						if (mouseCellX == C.getColumn() && mouseCellY == C.getRow()) {
							board.getPlayerList().get(turnCount).setPos(mouseCellY, mouseCellX);
							board.getCell(board.getPlayerList().get(turnCount).getRow(),
									board.getPlayerList().get(turnCount).getColumn()).setOccupied(true);

							board.repaint();
							board.revalidate();
							display.repaint();
							display.revalidate();

							if (board.getCell(board.getPlayerList().get(turnCount).getRow(),
									board.getPlayerList().get(turnCount).getColumn()).isRoomCenter()) {
								GuessPanel guessPanel = new GuessPanel(
										board.getCell(board.getPlayerList().get(turnCount).getRow(),
												board.getPlayerList().get(turnCount).getColumn()).getRoom(),
										currPlayerSuggest);
								controlPanel.setGuess(guessPanel.getSuggestion().getSolRoomName() + "\n"
										+ guessPanel.getSuggestion().getSolPersonName() + "\n"
										+ guessPanel.getSuggestion().getSolWeaponName());
								controlPanel.setGuessResult(guessPanel.getNewCard().getCardName());
							}

							controlPanel.nextPressed = true;
							targetPicked = true;
						}
					}

					if (!targetPicked) {
						JOptionPane splash = new JOptionPane();
						splash.showMessageDialog(null, ERROR_CONTENT, ERROR_TITLE, JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}

			// unused mouse action method stubs
			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
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
		board.deal();

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
			currPlayerSuggest = currPlayer;
			Card tempCard = null;
			Solution tempSol = null;
			if (counter == 0) { // Just to set the first player
				controlPanel.setTurn(currPlayer, roll);
				counter++;

				// Human player stuff
				if (currPlayer.isHuman()) {
					board.getCell(board.getPlayerList().get(turnCount).getRow(),
							board.getPlayerList().get(turnCount).getColumn()).setOccupied(false);
					board.calcTargets(board.getCell(currPlayer.getRow(), currPlayer.getColumn()), roll);

					cardsPanel.updateHand(board.getPlayerList().get(turnCount).getHand());
					cardsPanel.updateSeen(board.getPlayerList().get(turnCount).getSeen());
					cardsPanel.updateAll();

					// For testing winning accusation
					System.out.println(board.getSolution().getSolPersonName());
					System.out.println(board.getSolution().getSolRoomName());
					System.out.println(board.getSolution().getSolWeaponName());

					for (BoardCell C : board.getTargets()) {
						board.getCell(C.getRow(), C.getColumn()).setTarget(true);
					}

					board.repaint();
					board.revalidate();
					display.repaint();
					display.revalidate();

				} else { // Computer player stuff
					board.getCell(board.getPlayerList().get(turnCount).getRow(),
							board.getPlayerList().get(turnCount).getColumn()).setOccupied(false);
					board.getPlayerList().get(turnCount).selectTarget(roll);
					board.getCell(board.getPlayerList().get(turnCount).getRow(),
							board.getPlayerList().get(turnCount).getColumn()).setOccupied(true);

					board.repaint();
					board.revalidate();
					display.repaint();
					display.revalidate();

					if (board.getCell(board.getPlayerList().get(turnCount).getRow(),
							board.getPlayerList().get(turnCount).getColumn()).isRoomCenter()) {
						tempSol = board.getPlayerList().get(turnCount).createSuggestion();
						controlPanel.setGuess(tempSol.getSolRoomName() + "\n" + tempSol.getSolPersonName() + "\n"
								+ tempSol.getSolWeaponName());
						tempCard = board.handleSuggestion(tempSol, currPlayer);
						if (tempCard != null) {
							controlPanel.setGuessResult("Suggestion Disproven!");
						} else {
							controlPanel.setGuessResult("Suggestion Not Disproven!");
						}
					}
				}
			}

			if (controlPanel.isAccusationMade()) {
				gameOver = true;
			}

			if (controlPanel.nextPressed) {

				board.repaint();
				board.revalidate();
				display.repaint();
				display.revalidate();

				cardsPanel.updateAll();

				roll = board.roll();
				controlPanel.setTurn(currPlayer, roll);
				controlPanel.repaint();
				controlPanel.revalidate();

				turnCount = (turnCount + 1) % board.getPlayerList().size();
				counter = 0;
			}
		}
		if (controlPanel.isAccusationCorrect()) {
			splash = new JOptionPane();
			splash.showMessageDialog(null, WIN_CONTENT, WIN_TITLE, JOptionPane.INFORMATION_MESSAGE);
		} else {
			splash = new JOptionPane();
			splash.showMessageDialog(null, LOSE_CONTENT, LOSE_TITLE, JOptionPane.INFORMATION_MESSAGE);
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