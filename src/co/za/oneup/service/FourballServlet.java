package co.za.oneup.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

import co.za.oneup.service.objects.Game;
import co.za.oneup.service.objects.Player;
import co.za.oneup.service.objects.Team;

@WebServlet(name="FourballServlet", 
			urlPatterns={"/FourballServlet"})
public class FourballServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private List<Game> currentGames = new LinkedList<Game>();	
	private Hashtable<String, String> playerDetails = new Hashtable<String, String>();
	private Gson gson = new Gson();
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws IOException {
		Map<String, String[]> params = request.getParameterMap();
		String operation = "";
		Gson gson = new Gson();
		// determine operation
		for(Map.Entry<String, String[]> current : params.entrySet()) {
			if (current.getKey().equals("OPERATION"))
				operation = current.getValue()[0];
		}
		if (operation == null || "".equals(operation.trim())) { endWithError(response, "No operation provided"); return; }
		
		// registerClient
		if (operation.equals("REGISTERCLIENT")) {
			String playerName = "";
			for(Map.Entry<String, String[]> current : params.entrySet()) {
				if (current.getKey().equals("PLAYERNAME")) {
					playerName = current.getValue()[0];					
				}
			}
			if (playerName == null || "".equals(playerName.trim())) { endWithError(response, "No playerName provided"); return; }
			
			UUID uuid = UUID.randomUUID();
			playerDetails.put(playerName, uuid.toString());
			String[] arr = new String[2];
			arr[0] = uuid.toString();
			arr[1] = gson.toJson(currentGames);
			
			String finalresponse = gson.toJson(arr);
			PrintWriter out = response.getWriter();
			out.println(finalresponse);
			return;
		}
		
		if (operation.equals("CREATEGAME")) {			
			String gameName = "";
			String gameSize = "4"; 
			for(Map.Entry<String, String[]> current : params.entrySet()) {
				if (current.getKey().equals("GAMENAME")) {
					gameName = current.getValue()[0];					
				}
				if (current.getKey().equals("GAMESIZE")) {
					gameSize = current.getValue()[0];					
				}				
			}

			if ("".equals(gameName.trim())) { endWithError(response, "Error - GAMENAME required"); return; }
			if (!StringUtils.isNumeric(gameSize)) { endWithError(response, "Error - Gamesize must be numeric"); return; }

			Game game = new Game();
			game.setGameID(UUID.randomUUID().toString());
			game.setGameName(gameName);
			game.setGameSize(new Short(gameSize));
			
			game.getTeams().add(new Team());
			game.getTeams().add(new Team());
			
			currentGames.add(game);
			
			String finalResponse = gson.toJson(game);
			PrintWriter out = response.getWriter();
			out.println(finalResponse);
			return;			
		}
		
		if (operation.equals("JOINGAME")) {
			String playerName = "";
			String gameId = "";
			String teamId = "";
			for(Map.Entry<String, String[]> current : params.entrySet()) {
				if (current.getKey().equals("PLAYERNAME")) {
					playerName = current.getValue()[0];
				}
				if (current.getKey().equals("GAMEID")) {
					gameId = current.getValue()[0];
				}
				if (current.getKey().equals("TEAMID")) {
					teamId = current.getValue()[0];
				}
			}
			
			if (playerName == null || "".equals(playerName.trim())) { endWithError(response, "Missing player name"); return; }
			if (gameId == null || "".equals(gameId.trim())) { endWithError(response, "Missing game id"); return; }
			if (teamId == null || "".equals(teamId.trim())) { endWithError(response, "Missing team id"); return; }
			
			// find game
			for(Game currentGame : currentGames) {
				if (currentGame.getGameID().equals(gameId)) {
					// found game
					List<Team> teams = currentGame.getTeams();
					boolean foundTeam = false;
					for(Team currentTeam : teams) {
						if (currentTeam.getTeamID().equals(teamId)) {
							foundTeam = true;
							// found team
							if (currentTeam.getPlayers().size() == currentGame.getGameSize()/2) {
								endWithError(response, "Team full");
								return;
							}
							
							List<Player> players = currentTeam.getPlayers();
							Object playerObject = playerDetails.get(playerName);
							if (playerObject == null) {
								endWithError(response, "No player found for playerName : " + playerName);
								return;
							}
							String playerId = (String) playerDetails.get(playerName);
							Player player = new Player(playerId, playerName);
							// assigned player
							players.add(player);
							break;
						}						
					}
					if (!foundTeam) {
						endWithError(response, "No team found for teamid : " + teamId);
						return;
					}
					
					// return game details
					String finalResponse = gson.toJson(currentGame);
					PrintWriter out = response.getWriter();
					out.println(finalResponse);
					return;						
				}
			}
			// no games found
			endWithError(response, "No games found for gameID " + gameId);
			return;
			
		}
		
		if (operation.equals("UPDATEGAME")) {
			// this will do the bulk of in-game logic:  
			// 1.  Players will post their moves
			// 2.  Players will get updates every few seconds (if no update then return a simple response to minimize overhead)
			// 3. $$$s
			
			String gameId = "";
			for(Map.Entry<String, String[]> current : params.entrySet()) {
				if (current.getKey().equals("GAMEID")) {
					gameId = current.getValue()[0];
				}
			}
			
			if (gameId == null || "".equals(gameId.trim())) endWithError(response, "Missing game id");			
			// find game
			for(Game currentGame : currentGames) {
				if (currentGame.getGameID().equals(gameId)) {
					// return game details
					String finalResponse = gson.toJson(currentGame);
					PrintWriter out = response.getWriter();
					out.println(finalResponse);
					return;						
				}
			}
			
			endWithError(response, "No games found for gameID " + gameId);
		}

	}
	
	private void endWithError(HttpServletResponse response, String errorMessage) throws IOException {
		String finalResponse = gson.toJson(errorMessage);
		PrintWriter out = response.getWriter();
		out.println(finalResponse);
		return;				
	}

}
