package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.ExperienceHelper;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class PickBubbleHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x28;
	
	public PickBubbleHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}

	@Override
	public void interpretBytes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processMessage() throws SQLException {
		
	}

	@Override
	public byte[] getResponse() throws SQLException {
		return null;
	}
	
	public byte[] getResponse(boolean isElement, int elementType) throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.PICK_BUBBLE_RESPONSE);
		
		/*
		 * 0 - gain code
		 * 1 - gain nothing?
		 * 4 - gain element
		 */
		int response = isElement ? 4 : 0;
		
		output.putInt(0x14, userSession.getUser().getRoomSlot());
		output.putInt(0x18, response);
		output.putInt(0x1C, elementType);
		
		
		if (isElement) {
			int elementAmount = ExperienceHelper.getElementCount(false);
			int elementMultiplier = ExperienceHelper.getLuckyMultiplier();
			
			userSession.getUser().setWhiteCard(elementType - 1, userSession.getUser().getWhiteCard(elementType - 1) + elementAmount * elementMultiplier);
			
			output.putInt(0x20, elementAmount);
			output.putInt(0x24, elementMultiplier);
		}
		else {
			int code;
			if (elementType == 0) {
				code = 1000;
			}
			else {
				code = 3000;
			}
			
			userSession.getUser().setPlayerCode(userSession.getUser().getPlayerCode() + code);
			
			output.putInt(0x20, code);
		}
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}
}
