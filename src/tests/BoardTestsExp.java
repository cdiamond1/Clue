package tests;
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

/* BoardTestsExp class - contains our tests
 * 
 * @Author: Carson D.
 * @Author: Charlie D.
 * 
 * @Date: 2/24/2024
 * 
 */

public class BoardTestsExp {

	private TestBoard board;
	
	// called before each test
	@BeforeEach
	public void setup() {
		board = new TestBoard();
	}
	
	/*
	 * Test adjacencies for several different locations
	 * Tests center and edges
	 */
	@Test
	public void testAdjacency() {	// From 'figure 1' in the assignment page might need to change later
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertFalse(testList.contains(board.getCell(2, 2)));
		Assert.assertFalse(testList.contains(board.getCell(3, 3)));
		Assert.assertFalse(testList.contains(board.getCell(3, 0)));
		Assert.assertEquals(2, testList.size());
	}
	
	/*
	 * Test targets with several rolls and start locations
	 */
	@Test
	public void testTargetsNormal() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
	}
	
	/*
	 * Test for targets with a room
	 */
	@Test
	public void testTargetsRoom() {
		TestBoardCell cell = board.getCell(0, 1);
		board.getCell(2, 2).setRoom(true);
		board.getCell(0, 3).setRoom(true);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 0)));
		Assert.assertFalse(targets.contains(board.getCell(2, 2))); // Is a room
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertFalse(targets.contains(board.getCell(0, 3))); // Is a room
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
	}
	
	/*
	 * Test for tagets when an interfering space is occupied
	 */
	@Test
	public void testTargetsOccupied() {
		TestBoardCell cell = board.getCell(0, 0);
		board.getCell(2, 1).setOccupied(true);
		board.getCell(0, 3).setOccupied(true);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertFalse(targets.contains(board.getCell(2, 1))); // Occupied
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertFalse(targets.contains(board.getCell(0, 3))); // Occupied
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
	}
	
	/*
	 * Test targets with occupied room
	 */
	@Test
	public void testTargetsMixed() {
		//	Occupied cells
		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 1).setRoom(true);
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
		Assert.assertFalse(targets.contains(board.getCell(0, 1))); // Blocked by occupied spot
	}
	
	/*
	 * Test roll size
	 */
	@Test
	public void testTargetsRoll() {
		TestBoardCell cell = board.getCell(1, 1);
		board.calcTargets(cell, 1);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertFalse(targets.contains(board.getCell(2, 3))); // Can't get to with 1 move
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertFalse(targets.contains(board.getCell(3, 3))); // Can't get to with 1 move
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
	}
}
