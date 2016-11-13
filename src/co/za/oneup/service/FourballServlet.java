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
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws IOException{
		Map<String, String[]> params = request.getParameterMap();
		String operation = "";
		Gson gson = new Gson();
		// determine operation
		for(Map.Entry<String, String[]> current : params.entrySet()) {
			if (current.getKey().equals("OPERATION"))
				operation = current.getValue()[0];
		}
		
		// registerClient
		if (operation.equals("REGISTERCLIENT")) {
			String playerName = "";
			for(Map.Entry<String, String[]> current : params.entrySet()) {
				if (current.getKey().equals("PLAYERNAME")) {
					playerName = current.getValue()[0];					
				}
			}
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
			for(Map.Entry<String, String[]> current : params.entrySet()) {
				if (current.getKey().equals("GAMENAME")) {
					gameName = current.getValue()[0];					
				}
			}			
			Game game = new Game();
			game.setGameID(UUID.randomUUID().toString());
			game.setGameName(gameName);
			
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
			
			// find game
			for(Game currentGame : currentGames) {
				if (currentGame.getGameID().equals(gameId)) {
					// found game
					List<Team> teams = currentGame.getTeams();
					for(Team currentTeam : teams) {
						if (currentTeam.getTeamID().equals(teamId)) {
							// found team
							List<Player> players = currentTeam.getPlayers();
							String playerId = (String) playerDetails.get(playerName);
							Player player = new Player(playerId, playerName);
							// assigned player
							players.add(player);
							break;
						}
					}
					
					// return game details
					String finalResponse = gson.toJson(currentGame);
					PrintWriter out = response.getWriter();
					out.println(finalResponse);
					return;						
				}
			}
			
		}
		
		if (operation.equals("UPDATEGAME")) {
			// this will do the bulk of in-game logic:  
			// 1.  Players will post their moves
			// 2.  Players will get updates every few seconds (if no update then return a simple response to minimize overhead)
		}

	}

}
