package tests;

import java.io.FileNotFoundException;

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
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
				
		BoardCell cell = board.getCell(18, 9);
		
		System.out.println("Row: " + cell.getRow() + ", Column: " + cell.getColumn());
		System.out.println("Is doorway? " + cell.isDoorway());
		System.out.println("Is room? " + cell.isRoom());
		System.out.println("Is room lable? " + cell.isLabel());
		System.out.println("Room name: " + cell.getRoom().getName());
		System.out.println("Symbol: " + cell.getSymbol());
		
	}
}
