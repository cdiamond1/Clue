package tests;

import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Set;

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
		
		
		ComputerPlayer CPU = (ComputerPlayer) board.getPlayerList().get(4);
		System.out.println(CPU.getRow() + ", " + CPU.getColumn());
		/*
		board.calcTargets(CPU.getCurrCell(), 1);
		Set<BoardCell> targets = board.getTargets();
		BoardCell temp = CPU.selectTarget(1);
		
		System.out.println(temp.getRow() + ", " + temp.getColumn());
		System.out.println(targets.contains(temp));
		*/		
	}
}
