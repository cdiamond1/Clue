package tests;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.Set;

import clueGame.Board;
import clueGame.BoardCell;

/*
 * Code Testing Class - Used to print out results of methods to figure out what goes wrong
 * 
 * @Author Carson D.
 * 
 * @Date 2/27/2024
 */

public class code_testing {

	private static Board board = Board.getInstance();;
		
	public static void main(String[] args) throws FileNotFoundException {
		//board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.setConfigFiles("ClueLayout306.csv", "ClueSetup306.txt");
		board.initialize();
				
		board.getCell(15, 7).setOccupied(true);
		board.calcTargets(board.getCell(13, 7), 4);
		board.getCell(15, 7).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		
		System.out.println(targets.size());
		
		for(BoardCell test : targets) {
			System.out.println("Row: " + test.getRow() + ", Column: " + test.getColumn());
			System.out.println("Symbol: " + test.getSymbol());
		}
		
		
		
	}
}
