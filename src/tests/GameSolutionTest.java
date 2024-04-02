
package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameSolutionTest {
	
	private static Board board;
	
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
	public void accusationTest() {
		// strategy: create multiple instances of the correct card and check them against
		// the solution set. Also create false cards that should return false
		Card person = new Card("Wolfgang Gwawl", CardType.PERSON);
		Card weapon = new Card("Knife", CardType.WEAPON);
		Card room = new Card("Shields", CardType.ROOM);
		
		Card person2 = new Card("Wolfgang Gwawl", CardType.PERSON); // same as solution
		Card person3 = new Card("Circe Calypso", CardType.PERSON);  // different than solution
		
		Card room2 = new Card("Shields", CardType.ROOM);
		Card room3 = new Card("Engine room", CardType.ROOM);
		
		Card weapon2 = new Card("Knife", CardType.ROOM);
		Card weapon3 = new Card("Shovel", CardType.ROOM);
		
		Solution testSol = new Solution(room, person, weapon);
		board.setSolution(testSol);
		
		assertTrue(board.checkAccusation(person2.getCardName(), room2.getCardName(), weapon2.getCardName()));
		
		assertFalse(board.checkAccusation(person3.getCardName(), room2.getCardName(), weapon2.getCardName()));
		assertFalse(board.checkAccusation(person2.getCardName(), room3.getCardName(), weapon2.getCardName()));
		assertFalse(board.checkAccusation(person2.getCardName(), room2.getCardName(), weapon3.getCardName()));
	}
	
	@Test
	public void disproveSuggestion1CardTest() {
		Player humanPlayer = new HumanPlayer("human");
		Player compPlayer = new ComputerPlayer("comp");
		
		humanPlayer.updateHand(new Card("Knife", CardType.WEAPON));
		humanPlayer.updateHand(new Card("Shovel", CardType.WEAPON));
		humanPlayer.updateHand(new Card("Shields", CardType.ROOM));
		
		compPlayer.updateHand(new Card("Tadg Helios", CardType.PERSON));
		compPlayer.updateHand(new Card("Navigation", CardType.ROOM));
		compPlayer.updateHand(new Card("Meal Tray", CardType.WEAPON));
		
		assertEquals("Knife", humanPlayer.disproveSuggestion("Navigation", "Circe Calypso", "Knife").getCardName());
		assertEquals("Navigation", compPlayer.disproveSuggestion("Navigation", "Circe Calypso", "Knife").getCardName());
	}
	
	@Test
	public void disproveSuggestion2CardsTest() {
		// Strategy: humanPlayer has two cards that can disprove the suggestion, run
		// disproveSuggestion() 100 times and ensure both results happen
		Player humanPlayer = new HumanPlayer("human");
		
		humanPlayer.updateHand(new Card("Knife", CardType.WEAPON));
		humanPlayer.updateHand(new Card("Shovel", CardType.WEAPON));
		humanPlayer.updateHand(new Card("Shields", CardType.ROOM));
		
		int knifeCount = 0;
		int shieldsCount = 0;
		int otherCount = 0;
		
		for (int i = 0; i < 100; i++) {
			Card disproveCard = humanPlayer.disproveSuggestion("Shields", "Tadg Helios", "Knife");
			
			if (disproveCard.getCardName().equals("Knife")) {
				knifeCount++;
			} else if (disproveCard.getCardName().equals("Shields")) {
				shieldsCount++;
			} else {
				otherCount++;
			}
		}
		
		assertTrue(knifeCount > 0);
		assertTrue(shieldsCount > 0);
		assertEquals(0, otherCount);
	}
	
	@Test
	public void handleSuggestionTest() {
		Card solRoom = new Card("Cargo", CardType.ROOM);
		Card solPerson = new Card("Circe Calypso", CardType.PERSON);
		Card solWeapon = new Card("Space Suit Helmet", CardType.WEAPON);
		Solution solution = new Solution(solRoom, solPerson, solWeapon);
		
		// undo generate solution
		board.addCardtoDeck(board.getSolution().getSolPerson());
		board.addCardtoDeck(board.getSolution().getSolRoom());
		board.addCardtoDeck(board.getSolution().getSolWeapon());
		
		// generate custom solution
		board.setSolution(solution);
		board.removeCardFromDeck(solRoom);
		board.removeCardFromDeck(solPerson);
		board.removeCardFromDeck(solWeapon);
		
		board.deal();
		
		Solution allRight = new Solution(solRoom, solPerson, solWeapon);
		Solution oneWrong = new Solution(solRoom, solPerson, new Card("Shovel", CardType.WEAPON));
		Solution allWrong = new Solution(new Card("Navigation", CardType.ROOM), new Card("Tadg Helios", CardType.PERSON), new Card("Shovel", CardType.WEAPON));
		Player startingPlayer = new HumanPlayer("Glaukos Zal");
		
		assertNull(board.handleSuggestion(allRight, startingPlayer));
		assertEquals(board.handleSuggestion(oneWrong, startingPlayer).getCardName(), "Shovel");
	}
	
	@Test
	public void computerSuggestionTest() {
		ComputerPlayer compPlayer = new ComputerPlayer("comp");
		compPlayer.setPos(16, 4);
		
		compPlayer.updateSeen(new Card("Wolfgang Gwawl", CardType.PERSON));
		compPlayer.updateSeen(new Card("Circe Calypso", CardType.PERSON));
		compPlayer.updateSeen(new Card("Poseidon Alcyone", CardType.PERSON));
		compPlayer.updateSeen(new Card("Tadg Helios", CardType.PERSON));
		compPlayer.updateSeen(new Card("Glaukos Zal", CardType.PERSON));
		
		int atreusCount = 0;
		int knifeCount = 0;
		int shovelCount = 0;
		
		for (int i = 0; i < 1000; i++) {
			Solution currSol = compPlayer.createSuggestion();
			
			if (currSol.getSolPersonName().equals("Atreus Alcides")) atreusCount++;
			if (currSol.getSolWeaponName().equals("Knife")) knifeCount++;
			if (currSol.getSolWeaponName().equals("Shovel")) shovelCount++;
		}
		
		assertEquals("Shields", board.getCell(16, 4).getRoom().getName());
		assertEquals(1000, atreusCount);
		assertTrue((knifeCount + shovelCount) < 1000 && knifeCount != shovelCount);
		
		
	}
	
	
	
	
}
