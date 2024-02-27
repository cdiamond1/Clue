package tests;

import java.util.Set;

import experiment.TestBoard;
import experiment.TestBoardCell;

/*
 * Code Testing Class - Used to print out results of methods to figure out what goes wrong
 * 
 * @Author Carson D.
 * 
 * @Date 2/27/2024
 */

public class code_testing {
	public static void main(String[] args) {
		TestBoard board = new TestBoard();
		TestBoardCell cell = board.getCell(0, 3);
		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 1).setRoom(true);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		for(TestBoardCell e : targets) {
			System.out.println(e.getRow() + ", " + e.getColumn());
		}

	}
}
