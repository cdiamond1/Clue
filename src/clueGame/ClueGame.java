package clueGame;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/* ClueGame class - runs the game. Provides framework to hold
 * all GUI elements
 * 
 * @Author: Carson D.
 * @Author: Charlie D.
 * 
 * @Date: 4/1/2024
 */

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
	private static String winContent = "Congratulations you successfully found the murder! You win :)";
	private final static String LOSE_TITLE = "Uh Oh!";
	private final static String LOSE_CONTENT = "The murder got away! You Lose :(";

	private static int turnCount;
	private static int mouseCellX, mouseCellY;

	private static Player currPlayerSuggest;

	private static boolean gameOver = false;
	private static boolean compWin = false;
	private static JPanel display = new JPanel();

	// constructor: sets up GUI and detects mouse clicks
	public ClueGame() {
		// create display panel object
		display = new JPanel();
		display.setLayout(new BorderLayout());
		display.addMouseListener(new MouseListener() {

			// detect mouse click
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean targetPicked = false;

				// check that mouse click is on board
				if (e.getX() < 600 && e.getY() < 600) {
					mouseCellX = (e.getX() - 2) / (board.getPanelWidth() / board.getNumColumns());
					mouseCellY = (e.getY() - 2) / (board.getPanelHeight() / board.getNumRows());

					// find corresponding cell
					for (BoardCell C : board.getTargets()) {
						if (mouseCellX == C.getColumn() && mouseCellY == C.getRow()) {
							
							// set player position
							board.getPlayerList().get(turnCount).setPos(mouseCellY, mouseCellX);
							board.getCell(board.getPlayerList().get(turnCount).getRow(),
									board.getPlayerList().get(turnCount).getColumn()).setOccupied(true);

							board.repaint();
							board.revalidate();
							display.repaint();
							display.revalidate();

							// get suggestion if in room
							if (board.getCell(board.getPlayerList().get(turnCount).getRow(),
									board.getPlayerList().get(turnCount).getColumn()).isRoomCenter()) {
								
								GuessPanel guessPanel = new GuessPanel(
										board.getCell(board.getPlayerList().get(turnCount).getRow(),
												board.getPlayerList().get(turnCount).getColumn()).getRoom(),
										currPlayerSuggest);
								
								// set suggestion in panel
								if (guessPanel.getSuggestion() != null) {
									controlPanel.setGuess(guessPanel.getSuggestion().getSolRoomName() + "\n"
											+ guessPanel.getSuggestion().getSolPersonName() + "\n"
											+ guessPanel.getSuggestion().getSolWeaponName());
									
									if (guessPanel.getNewCard() != null) {
										controlPanel.setGuessResult(guessPanel.getNewCard().getCardName());
									}
									else {
										for (Player p : board.getPlayerList()) {
											p.setShouldAccuse(true);
										}
									}	
								}
								
								// move computer player when suggested 
								for (Player p : board.getPlayerList()) {
									if (guessPanel.getSuggestion() != null && p.getName() == guessPanel.getSuggestion().getSolPersonName()) {
										board.getCell(p.getRow(), p.getColumn()).setOccupied(false);
										p.setPos(mouseCellY, mouseCellX);
										
										board.repaint();
										board.revalidate();
										display.repaint();
										display.revalidate();
										
										break;
									}
								} 
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
	

	public static int getTurnCount() {
		return turnCount;
	}

	// MAIN
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
					
					int row = board.getPlayerList().get(turnCount).getRow();
					int col = board.getPlayerList().get(turnCount).getColumn();
					
					// randomly pick target
					board.getPlayerList().get(turnCount).selectTarget(roll, row, col);
					
					board.getCell(board.getPlayerList().get(turnCount).getRow(),
							board.getPlayerList().get(turnCount).getColumn()).setOccupied(true);

					board.repaint();
					board.revalidate();
					display.repaint();
					display.revalidate();
					
					// create suggestion if moved into room
					if (board.getCell(board.getPlayerList().get(turnCount).getRow(),
							board.getPlayerList().get(turnCount).getColumn()).isRoomCenter()) {
						
						// Makes suggestion if shouldAccuse is false; otherwise, computer knows the answer
						// and wins
						if (!board.getPlayerList().get(turnCount).shouldAccuse()) {
							tempSol = board.getPlayerList().get(turnCount).createSuggestion();
							controlPanel.setGuess(tempSol.getSolRoomName() + "\n" + tempSol.getSolPersonName() + "\n"
									+ tempSol.getSolWeaponName());
							
							tempCard = board.handleSuggestion(tempSol, currPlayer);
						}
						// computer wins
						else {
							splash = new JOptionPane();
							splash.showMessageDialog(null, "The Computer Wins!", "Computer wins", JOptionPane.INFORMATION_MESSAGE);
							compWin = true;
							gameOver = true;
							
							break;
						}
						
						// tell computer player to accuse next turn
						if (tempCard == null) {
							for (Card c : board.getPlayerList().get(turnCount).getHand()) {
								if (c.getCardName().equals(tempSol.getSolRoomName())) {
									board.getPlayerList().get(turnCount).setShouldAccuse(true);
									
									board.getPlayerList().get(turnCount).createAccusation(tempSol.getSolRoom(), 
											tempSol.getSolPerson(), tempSol.getSolWeapon());
								}
							}
						}
						
						// move player to room where they've been suggested
						for (Player p : board.getPlayerList()) {
							if (p.getName() == tempSol.getSolPersonName()) {
								board.getCell(p.getRow(), p.getColumn()).setOccupied(false);
								
								row = board.getPlayerList().get(turnCount).getRow();
								col = board.getPlayerList().get(turnCount).getColumn();
								p.setPos(row, col);
								
								board.repaint();
								board.revalidate();
								display.repaint();
								display.revalidate();
								
								break;
							}
						}
						
						board.repaint();
						board.revalidate();
						display.repaint();
						display.revalidate();
						
						// set panel
						if (tempCard != null) {
							controlPanel.setGuessResult("Suggestion Disproven!");
						} else {
							controlPanel.setGuessResult("Suggestion Not Disproven!");
						}
					}
				}
			}

			// end game when human player makes accusation
			if (controlPanel.isAccusationMade()) {
				gameOver = true;
			}

			// NEXT button
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
			
			winContent = "Congratulations " + currPlayer.getName() + " has found the murderer! " + currPlayer.getName() + " wins :)";
		}
		
		// check if accusation is correct
		if (controlPanel.isAccusationCorrect()) {
			splash = new JOptionPane();
			splash.showMessageDialog(null, winContent, WIN_TITLE, JOptionPane.INFORMATION_MESSAGE);
		} else {
			if(compWin) {
				splash = new JOptionPane();
				splash.showMessageDialog(null, "A computer player found the murderer! They win", LOSE_TITLE, JOptionPane.INFORMATION_MESSAGE);
			}
			
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