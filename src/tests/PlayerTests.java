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
		
		ArrayList<Player> playerList = new ArrayList<Player>();
		playerList = board.getPlayerList();
		
		for (int i = 0; i < playerList.size(); i++) {
			if (playerList.get(i).isHuman()) {
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
		int weaponCardCount = 0;
		int roomCardCount = 0;
		int playerCardCount = 0;
		
		deck = board.getDeck();
		
		for (Card card: deck) {
			switch (card.getCardType()) {
			case WEAPON:
				weaponCardCount++;
				break;
			case ROOM:
				roomCardCount++;
				break;
			case PERSON:
				playerCardCount++;
				break;
			}
		}
		
		assertEquals(18, deck.size());
		assertEquals(5, weaponCardCount); // 6 cards - 1 solution card
		assertEquals(8, roomCardCount);	  // 9 cards - 1 solution card
		assertEquals(5, playerCardCount);
		
		
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
