package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

/*
 * BoardAdjTargetTest class. JUnit test that checks adjacency
 * 
 * Author: Charlie Dupras
 * Author:
 */

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}
	
	// Ensure that player does not move around within room
	@Test
	public void testAdjacenciesRooms() {
		
	}
	
	
	// Ensure door locations include their rooms and also additional walkways
	@Test
	public void testAdjacencyDoor() {
		
	}
	
	// Test a variety of walkway scenarios
	@Test
	public void testAdjacencyWalkways() {
		
	}
	
	
	
	
	
	
}
