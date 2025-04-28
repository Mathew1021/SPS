package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class SetSlotOrderHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x38;

	public SetSlotOrderHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}

	@Override
	public void interpretBytes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		int[] order = new int[8];
		
		for (int i = 0; i < order.length; i++) {
			order[i] = i;
		}
		
		return getResponse(order);
	}
	
	public byte[] getResponse(int[] blueOrder, int[] redOrder) {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.SPAWN_RESPONSE);
		
//		for (int i = 0; i < 8; i++) {
//			output.putInt(0x14 + 4 * i, -1);
//		}
//		output.putInt(0x14, 0);
//		output.putInt(0x24, 1);
		for (int i = 0; i < 4; i++) {
			output.putInt(0x14 + 4 * i, redOrder[i]);
			output.putInt(0x24 + 4 * i, blueOrder[i]);
		}
		
		output.putInt(0x34, 1);
		
		return output.toArray();
	}
	
	public byte[] getResponse(int[] order) {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.SPAWN_RESPONSE);
		output.putInts(0x14, order);
		output.putInt(0x34, 0);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}

}
