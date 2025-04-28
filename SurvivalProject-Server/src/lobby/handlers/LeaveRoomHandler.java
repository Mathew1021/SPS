package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class LeaveRoomHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x28;
	
	public boolean isDisconnected;
	public LeaveRoomHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}
	
	public LeaveRoomHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		// There are no fields in here
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		lobbyServer.sendRoomMessage(userSession, getResponse(), false);
		lobbyServer.onLeaveRoom(userSession, isDisconnected);
	}

	@Override
	public byte[] getResponse() {
		return getResponse(userSession);
	}
	
	public byte[] getResponse(UserSession userSession) {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.LEAVE_ROOM_RESPONSE);
		output.putInt(0x14, userSession.getUser().getRoomSlot());
		output.putString(0x18, userSession.getUser().getUsername());
		output.putByte(0x25, (byte) 0); // master card
		
		return output.toArray();
	}

	@Override
	public void processMessage() {
		processMessage(userSession);
	}
	
	public void processMessage(UserSession userSession) {
		Room room = lobbyServer.getRoom(userSession.getUser().getRoomIndex());
		room.users[userSession.getUser().getRoomSlot()] = null;
		room.setNumberOfUsers(room.getNumberOfPlayers() - 1);
		room.isAlive[userSession.getUser().getRoomSlot()] = false;
		
		if (userSession.getUser().getRoomTeam() == 10) {
			room.bluePlayersCount--;
		}
		else {
			room.redPlayersCount--;
		}
		userSession.getUser().setInRoom(false);
		userSession.getUser().setInGame(false);
	}
}
