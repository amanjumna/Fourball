package co.za.oneup.service.objects;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Team {
	private String teamID;
	private List<Player> players; // 2 or 3 players
	
	public Team() {
		teamID = UUID.randomUUID().toString();
	}
	
	public String getTeamID() {
		return teamID;
	}
	public void setTeamID(String teamID) {
		this.teamID = teamID;
	}
	public List<Player> getPlayers() {
		if (players == null) players = new LinkedList<Player>();
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
}
