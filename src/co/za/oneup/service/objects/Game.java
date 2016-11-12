package co.za.oneup.service.objects;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Game {	
	private List<Team> teams; // 2 or 3 team
	private List<Card> dealtCards; // list of cards already dealt
	private Map<String, Integer> scores; // map of teamId to score
	
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