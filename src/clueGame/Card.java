package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	
	public Card(String cardName, CardType type) {
		super();
		this.cardName = cardName;
		this.type = type;
	}
	
	public String getCardName() {
		return cardName;
	}
	
	public CardType getCardType() {
		return type;
	}
	
	public boolean equals(Card Target) {
		if(this.cardName.equals(Target.getCardName())) {
			return true;
		}
		return false;
	}
}
