package clueGame;

/* BadConfigFormatException - Creates custom exception for bad config files
 * 
 * @Author Carson D.
 * @Author
 * 
 * @Date 3/1/2024
 * 
 */

public class BadConfigFormatException extends Exception {

	/*
	 * Copied code from C11A-2 Advanced Exceptions for referance
	 * 
	 * protected double negativeBalance;
	 * 
	 * public NegativeBalanceException() { super("Error: negative balance"); }
	 * 
	 * public NegativeBalanceException(double amount) {
	 * super("Amount exceeds balance by " + amount); negativeBalance = amount; }
	 * 
	 * public String toString() { return "Balance of " + negativeBalance +
	 * " not allowed"; }
	 * 
	 * 
	 */
	
	public BadConfigFormatException() { 
		super("Error: bad config file format"); 
	}
	
	public BadConfigFormatException(String message) {
		 super(message); 
	}

}
