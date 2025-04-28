package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class Test4377Handler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x44;
	
	public Test4377Handler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}

	@Override
	public void interpretBytes() {
		
	}

	@Override
	public void processMessage() throws SQLException {
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, 0x4377);
		output.putInt(0x14, 0); // card index
		output.putInt(0x18, 1111); // card id
		output.putInt(0x1C, 0);
		output.putInt(0x20, 0);
		output.putInt(0x24, 0);
		output.putInt(0x28, 0);
		output.putInt(0x2C, 60); // premium days
		output.putInt(0x30, 0);
		output.putInt(0x34, 0);
		output.putInt(0x38, 0); // Used in AnyEventWnd2. This window isn't in the images at all.
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
