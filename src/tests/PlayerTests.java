package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

public class PlayerTests {
	
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
	
	// Test that 5 computer players and 1 human player load in
	@Test
	public void testPlayerCount() {
		int humanCount = 0;
		int compCount = 0;
		
		for (Player player: board.getPlayerList()) {
			if (player.isHuman()) {
				humanCount++;
			}
			else {
				compCount++;
			}
		}
		
		assertEquals(1, humanCount);
		assertEquals(5, compCount);
	}

	// Test that deck of cards gets created properly:
	// test size and test various cards 
	@Test
	public void testDeck() {
		ArrayList<Card> deck = new ArrayList<Card>();
		Card playerCard = new Card("Circe Calypso", CardType.PERSON);
		Card roomCard = new Card("Shields", CardType.ROOM);
		Card weaponCard = new Card("Banana", CardType.WEAPON);
		
		assertEquals(21, deck.size()); // 9 rooms, 6 people, 6 weapons = 21 cards
		assertTrue(deck.contains(playerCard));
		assertTrue(deck.contains(roomCard));
		assertTrue(deck.contains(weaponCard));	
	}
	
	
	// test that cards are dealt to players and solution properly
	@Test
	public void testSolutionAndHands() {
		board.deal();
		ArrayList<Card> seenCards = new ArrayList<Card>();
		
		// Test that solution isn't null
		assertNotEquals(board.getSolution().getSolRoom(), null);
		assertNotEquals(board.getSolution().getSolPerson(), null);
		assertNotEquals(board.getSolution().getSolWeapon(), null);
		
		for (Player player: board.getPlayerList()) {
			assertEquals(3, player.getHand().size()); // hand size
			
			for (Card card: player.getHand()) {
				// check that solution hand doesn't match any player hands
				assertNotEquals(board.getSolution().getSolRoom(), card);
				assertNotEquals(board.getSolution().getSolPerson(), card);
				assertNotEquals(board.getSolution().getSolWeapon(), card);
				
				// check that cards are unique
				assertFalse(seenCards.contains(card));
				seenCards.add(card);
			}
		}
		
		assertEquals(18, seenCards.size()); // 21 cards - 3 solution cards = 18 dealt cards
	}
	
	
	
	
	
	
	
	
	
	
	
}
