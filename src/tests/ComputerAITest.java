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
import clueGame.Solution;

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
		
		assertTrue(targets.contains(temp));	// Test that the white player selects a random target
		
		CPU = (ComputerPlayer) board.getPlayerList().get(2);
		
		board.calcTargets(CPU.getCurrCell(), 5);
		targets = board.getTargets();
		temp = CPU.selectTarget(5);
		
		assertTrue(targets.contains(temp));		// Test that CPUs prioritizes non-visited rooms
		assertTrue(targets.contains(board.getCell(3, 26)));
		assertEquals(temp.getRow(), 3);
		assertEquals(temp.getColumn(), 26);
		
		CPU = (ComputerPlayer) board.getPlayerList().get(3);
		
		CPU.addVisited(board.getCell(24, 3));
		board.calcTargets(CPU.getCurrCell(), 6);
		targets = board.getTargets();
		temp = CPU.selectTarget(6);
		
		assertTrue(targets.contains(temp));		// Test that if a room is visited the select randomly
		
	}
	
	@Test
	public void createSuggestion() {
		// Suggetsion is pretty much written, just check over it and make sure it works
		ComputerPlayer CPU = new ComputerPlayer("test");
		CPU.setPos(3, 3);									// Setting CPU to the center of Weapons
		Solution testSol = CPU.createSuggestion();
		
		assertEquals(testSol.getSolRoomName(), "Weapons");	// Test that the correct room is picked
		assertFalse(testSol.getSolPerson() == null);		// Test that person and weapon cards are selected
		assertFalse(testSol.getSolWeapon() == null);
		
		CPU = new ComputerPlayer("test");
		CPU.setPos(3, 26);									// Setting CPU to the center of Navigation
		for(Card C : board.getPlayerCardList()) {
			if((C.getCardName().equals("Poseidon Alcyone"))) {	// Setting every other person card to being seen
			} else {
				CPU.updateSeen(C);
			}
		}
		for(Card C : board.getWeaponsList()) {
			if((C.getCardName().equals("Shovel"))) {			// Setting every other weapon card to being seen
			} else {
				CPU.updateSeen(C);
			}
		}
		
		testSol = CPU.createSuggestion();
		
		assertEquals(testSol.getSolRoomName(), "Navigation");				// Test that the correct room is picked
		assertEquals(testSol.getSolPersonName(), "Poseidon Alcyone");		// Test that a specific unseen person and weapon cards are selected
		assertEquals(testSol.getSolWeaponName(), "Shovel");
	}
}
