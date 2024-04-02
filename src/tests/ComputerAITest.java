package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;

public class ComputerAITest {

	private static Board board;
	private ComputerPlayer CPU;
	private Card CPUCard;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
		
	}
	
	@Test
	public void selectTargets() {
		CPU = (ComputerPlayer) board.getPlayerList().get(1);
		CPUCard = board.getPlayerCardList().get(1);
		
		board.calcTargets(CPU.getCurrCell(), 1);
		Set<BoardCell> targets = board.getTargets();
		BoardCell temp = CPU.selectTarget(1);
		
		assertTrue(targets.contains(temp));
	}
	
	@Test
	public void createSuggestion() {
		
	}
}
