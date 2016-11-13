package co.za.oneup.service.objects;

public class Player {
	private String playerID;
	private String playerName;
	private Hand hand; // current player's hand
	//-- add an indicator to say it's my turn
	
	public Player(String playerId, String playName) {
		this.playerID = playerId;
		this.playerName = playName;
	}
	
	public String getPlayerID() {
		return playerID;
	}
	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}
	public Hand getHand() {
		return hand;
	}
	public void setHand(Hand hand) {
		this.hand = hand;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
}
