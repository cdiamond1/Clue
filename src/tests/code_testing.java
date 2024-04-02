package tests;

import java.io.FileNotFoundException;
import java.util.Random;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.ComputerPlayer;

/*
 * Code Testing Class - Used to print out results of methods to figure out what goes wrong
 * 
 * @Author Carson D.
 * 
 * @Date 2/27/2024
 */

public class code_testing {

	private static Board board;
		
	public static void main(String[] args) throws FileNotFoundException {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		
		System.out.println(board.getCell(4, 4).isRoomCenter());
		
		/*
		board.calcTargets(board.getCell(3, 9), 3);
		for(BoardCell C : board.getTargets()) {
			System.out.println(C.getRow() + ", " + C.getColumn());
			System.out.println(C.isRoomCenter());
			System.out.println(C.isDoorway());
			System.out.println();
		}
		*/
		
				
	}
}
