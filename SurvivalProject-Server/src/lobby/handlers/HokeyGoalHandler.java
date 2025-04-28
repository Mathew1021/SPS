package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.ExperienceHelper;
import net.Messages;
import net.UserSession;
import net.objects.Room;
import net.objects.User;
import tools.ExtendedByteBuffer;

public class HokeyGoalHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x130;

	protected int goalTeam;
	
	int[] results;
	int[] luckyBonus;
	int[] experienceGained;
	int[] levels;
	int[] unknown;
	int[] elementType;
	int[] elementAmount;
	int[] elementMultiplier;
	
	public HokeyGoalHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}
	
	public HokeyGoalHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		goalTeam = input.getByte(0x14);
	}

	@Override
	public void processMessage() throws SQLException {
		Room room = lobbyServer.getRoom(userSession.getUser().getRoomIndex());
		
		int winningTeam = (goalTeam == 0) ? 10 : 20;
		
		/*
		 * 1 - scored a goal
		 * 2 - nothing
		 * 3 - draw?
		 * 4 - leave the game
		 */
		results = new int[8];
		luckyBonus = new int[8];
		experienceGained = new int[8];
		levels = new int[8];
		unknown = new int[8];
		elementType = new int[8];
		elementAmount = new int[8];
		elementMultiplier = new int[8];
		
		// Add a kill to the one who scored the goal
		userSession.getUser().setPlayerKOs(userSession.getUser().getPlayerKOs() + 1);
		
		for (int i = 0; i < 8; i++) {
			results[i] = -1;
			luckyBonus[i] = ExperienceHelper.getLuckyMultiplier();
			UserSession currentSession = room.getUserSession(i);
			
			if (room.getUserSession(i) != null) {
				results[i] = 2;
				
				if (currentSession.getUser().getRoomTeam() == winningTeam) {
					// Change exp and code
					experienceGained[i] = 100;
					currentSession.getUser().setPlayerExperience(currentSession.getUser().getPlayerExperience() + experienceGained[i] * luckyBonus[i]);
					currentSession.getUser().setPlayerCode(currentSession.getUser().getPlayerExperience() + experienceGained[i] * luckyBonus[i]);
					
					// Change level
					levels[i] = ExperienceHelper.getLevel(userSession.getUser().getPlayerExperience());
					currentSession.getUser().setPlayerLevel(levels[i]);
					
					// Add elements
					elementType[i] = (int) (Math.random() * 4) + 1;
					elementAmount[i] = ExperienceHelper.getElementCount(false);
					elementMultiplier[i] = luckyBonus[i];
					currentSession.getUser().setWhiteCard(elementType[i] - 1, currentSession.getUser().getWhiteCard(elementType[i] - 1) + elementAmount[i] * elementMultiplier[i]);
				}
				else {
					currentSession.getUser().setPlayerDowns(currentSession.getUser().getPlayerDowns() + 1);
				}
				
				User.saveUser(currentSession.getUser());
			}
		}
		
		// Change the scores
		if (winningTeam == 10) {
			room.blueScore++;
		}
		else {
			room.redScore++;
		}
		
		// If someone wins
		if (room.blueScore == 3 || room.redScore == 3) {
			for (int i = 0; i < 8; i++) {
				results[i] = -1;
				
				UserSession currentSession = room.getUserSession(i);
				
				if (currentSession != null) {
					if (currentSession.getUser().getRoomTeam() == winningTeam) {
						results[i] = 1;
						currentSession.getUser().setPlayerWins(currentSession.getUser().getPlayerWins() + 1);
						User.saveUser(currentSession.getUser());
					}
					else {
						results[i] = 2;
						currentSession.getUser().setPlayerLoses(currentSession.getUser().getPlayerLoses() + 1);
						User.saveUser(currentSession.getUser());
					}
				}
			}
		}
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.HOKEY_GOAL_RESPONSE);
		
		Room room = lobbyServer.getRoom(userSession.getUser().getRoomIndex());
		output.putInts(0x14, results);
		output.putInt(0x34, room.blueScore); // blue goals
		output.putInt(0x38, room.redScore); // red goals
		output.putInts(0x3C, luckyBonus);
		output.putInts(0x5C, experienceGained);
		output.putInts(0x7C, levels);
		output.putInts(0x9C, unknown);
		output.putInts(0xBC, elementType);
		output.putInts(0xDC, elementAmount);
		output.putInts(0xFC, elementMultiplier);
		output.putLong(0x120, userSession.getUser().getPlayerExperience()); // exp
		output.putLong(0x128, userSession.getUser().getPlayerCode()); // code
		return output.toArray();
	}
	
	public byte[] getResponse(int goal) throws SQLException {
		goalTeam = goal;
		processMessage();
		
		return getResponse();
	}

	@Override
	public void afterSend() throws IOException {
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
		Room room = lobbyServer.getRoom(userSession.getUser().getRoomIndex());
		
		if (room.blueScore == 3 || room.redScore == 3) {
			room.blueScore = 0;
			room.redScore = 0;
		}
	}
}
