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
 * @Date:
 * 
 */

public class BoardTestsExp {

	private TestBoard board;
	
	@BeforeEach
	public void estUp() {
		board = new TestBoard();
	}
	
	@Test
	public void testAdjacency() {	// From 'figure 1' in the assignment page might need to chage later
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertEquals(2, testList.size());
	}
}
