package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class Test4346Handler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x130;
	
	public Test4346Handler(LobbyServer lobbyServer, UserSession userSession) {
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
		/*
		14-ARRAY
		34
		38
		3C
		40~58
		5C~7C
		9C
		BC-ARRAY
		DC-ARRAY
		FC-ARRAY
		120,124-EXP
		128,12C-MONEY
		 */
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, 0x4346);
		output.putInt(0x14, 1); // result. 1 = win and so on
		output.putInt(0x18, 0);
		output.putInt(0x34, 1);
		output.putInt(0x38, 1); // ko
		
		int[] field3c = new int[8];
		for (int i = 0; i < 8; i++) {
			field3c[i] = 1;
		}
		output.putInts(0x3C, field3c);
		output.putInt(0x5C, 0); // exp and code
		output.putInt(0x7C, 0); // new level
		output.putInt(0x9C, 0);
		output.putInt(0xBC, 1); // elements type. you only get elements in quests/missions
		output.putInt(0xDC, 1); // element amount
		output.putInt(0xFC, 1); // element multiplier
		output.putLong(0x120, 1); // points
		output.putLong(0x128, 1); // code
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
