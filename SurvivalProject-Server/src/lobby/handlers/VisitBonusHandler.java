package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class VisitBonusHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x24;
	
	public VisitBonusHandler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}

	@Override
	public void interpretBytes() {
		
	}

	@Override
	public void processMessage() throws SQLException {
//		visitBonusElementsType = (int) (Math.random() * 4) + 1;
//		visitBonusElements = 0;
//		visitBonusElementsMultiplier = (int) (Math.random() * 4) + 1;
//		visitBonusMoney = 0;
//		visitBonusAvatarMoney = 0;
//		
//		if (userSession.getUser().hasCard(Card.VISIT_BONUS)) {
//			visitBonusElementsMultiplier *= 2;
//			visitBonusMoney *= 2;
//		}
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.VISIT_BONUS_RESPONSE);
		output.putInt(0x14, 1000);
		output.putInt(0x18, 2);
		output.putInt(0x1C, 3);
		output.putInt(0x20, 4);
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
