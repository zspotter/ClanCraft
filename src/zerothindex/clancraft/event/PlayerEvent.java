package zerothindex.clancraft.event;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.clan.ClanPlayer;

public class PlayerEvent {

	public void onPlayerChat(ClanPlayer player, String msg) {
		if (player.getChatMode() == ClanPlayer.CHAT_CLAN) {
			if (player.getClan() != null) {
				player.getClan().messageClan("<"+player.getName()+"> (clan) "+msg);
				ClanPlugin.getInstance().log("<"+player.getName()+"> (clan) "+msg);
				return;
			} else {
				player.setChatMode(ClanPlayer.CHAT_PUBLIC);
			}
		}
		if (player.getChatMode() == ClanPlayer.CHAT_ALLY) {
			if (player.getClan() != null) {
				player.getClan().messageAllies("<"+player.getName()+"> (ally) "+msg);
				ClanPlugin.getInstance().log("<"+player.getName()+"> (ally) "+msg);
				return;
			} else {
				player.setChatMode(ClanPlayer.CHAT_PUBLIC);
			}
		}
		ClanPlugin.getInstance().messageAll("<"+player.getName()+"> "+msg);
		ClanPlugin.getInstance().log("<"+player.getName()+"> "+msg);
		
	}

	public void onPlayerJoin(ClanPlayer player) {
		player.logIn();
		
	}

	public void onPlayerQuit(ClanPlayer player) {
		player.logOut();
		
	}

}
