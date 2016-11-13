package co.za.oneup.service.objects;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Game {	
	private String gameID;
	private String gameName;
	private short gameSize;
	private List<Team> teams; // 2 or 3 team
	private List<Card> dealtCards; // list of cards already dealt -- not needed as the client only needs to use it's card details
	private Map<String, Integer> scores; // map of teamId to score
		
	public short getGameSize() {
		return gameSize;
	}
	public void setGameSize(short gameSize) {
		this.gameSize = gameSize;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getGameID() {
		return gameID;
	}
	public void setGameID(String gameID) {
		this.gameID = gameID;
	}
	public List<Team> getTeams() {
		if (teams == null) teams = new LinkedList<Team>();
		return teams;
	}
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	public List<Card> getDealtCards() {
		if (dealtCards == null) dealtCards = new LinkedList<Card>();
		return dealtCards;
	}
	public void setDealtCards(List<Card> dealtCards) {
		this.dealtCards = dealtCards;
	}
	public Map<String, Integer> getScores() {
		if (scores == null) scores = new HashMap<String, Integer>();
		return scores;
	}
	public void setScores(Map<String, Integer> scores) {
		this.scores = scores;
	}	
	
}
