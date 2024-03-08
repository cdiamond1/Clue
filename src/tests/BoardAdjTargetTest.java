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
 * @Author: Charlie Dupras
 * @Author: Carson Diamond
 * 
 * @Date: 3/4/2024
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
	
	// Adjacency tests
	
	// Ensure that open space has 4 adjacent squares
	@Test
	public void onlyWalkways() {
		Set<BoardCell> testList = board.getAdjList(9, 16);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(9, 15)));
		assertTrue(testList.contains(board.getCell(8, 16)));
		assertTrue(testList.contains(board.getCell(9, 17)));
		assertTrue(testList.contains(board.getCell(10, 16)));
	}
	
	// Ensure that player does not move around within room
	@Test
	public void roomNotCenter() {
		Set<BoardCell> testList = board.getAdjList(2, 16);
		assertEquals(0, testList.size());
	}
	
	// Test corner of map
	@Test
	public void edgeOfMap() {
		Set<BoardCell> testList = board.getAdjList(28, 28);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(27, 28)));
		assertTrue(testList.contains(board.getCell(28, 27)));
	}
	
	// test cell next to room
	@Test
	public void besideRoomNoDoor() {
		Set<BoardCell> testList = board.getAdjList(6, 26);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(6, 25)));
		assertTrue(testList.contains(board.getCell(7, 26)));
		assertTrue(testList.contains(board.getCell(6, 27)));
	}
	
	// test room with secret passage
	@Test
	public void secretPassage() {
		Set<BoardCell> testList = board.getAdjList(9, 9);
		assertEquals(3, testList.size()); // 2 doors, 1 passage
		assertTrue(testList.contains(board.getCell(24, 3)));
	}
	
	// Target tests
	
	// Targets along walkways, at various distances
	@Test
	public void testWalkwaysVariedDistance() {
		BoardCell cell = board.getCell(18, 9);
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(16, 10)));
		assertTrue(targets.contains(board.getCell(20, 8)));
		
		board.calcTargets(cell, 5);
		targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(13, 9)));
		assertTrue(targets.contains(board.getCell(19, 3)));
		
		board.calcTargets(cell, 1);
		targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(18, 8)));
		assertFalse(targets.contains(board.getCell(17, 10)));
	}
	
	// Targets that allow the user to enter a room
	@Test
	public void testEnterRoom() {
		BoardCell cell = board.getCell(8, 2);
		board.calcTargets(cell, 2);
		Set<BoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(3, 3)));
	}
	
	// Targets calculated when leaving a room without secret passage
	@Test
	public void testLeavingDoor() {
		BoardCell cell = board.getCell(24, 3);
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(28, 6)));
		assertTrue(targets.contains(board.getCell(25, 5)));
	}
	
	// Targets calculated when leaving a room with secret passage
	@Test
	public void testLeavingSecretPass() {
		BoardCell cell = board.getCell(21, 4);
		board.calcTargets(cell, 1);
		Set<BoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(24, 3)));
		assertTrue(targets.contains(board.getCell(27, 5)));
	}
	
	// Targets that reflect blocking by other players
	@Test
	public void testBlockedByPlayers() {
		BoardCell cell = board.getCell(24, 6);
		board.getCell(22, 6).setOccupied(true);
		board.getCell(23, 5).setOccupied(true);
		board.getCell(25, 6).setOccupied(true);
		board.getCell(27, 7).setOccupied(true);
		board.calcTargets(cell, 2);
		Set<BoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(23, 7)));
		assertTrue(targets.contains(board.getCell(25, 5)));
	}
	
}
