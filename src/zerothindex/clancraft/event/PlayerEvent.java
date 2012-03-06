package zerothindex.clancraft.event;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.clan.ClanPlayer;

public class PlayerEvent {

	public void onPlayerChat(ClanPlayer player, String msg) {
		String name = (player.getClan() != null)? player.getClan().getName()+" " : "";
		name += player.getName();
		
		if (player.getChatMode() == ClanPlayer.CHAT_CLAN) {
			if (player.getClan() != null) {
				player.getClan().messageClan("<"+name+"> (clan) "+msg);
				ClanPlugin.getInstance().log("<"+name+"> (clan) "+msg);
				return;
			} else {
				player.setChatMode(ClanPlayer.CHAT_PUBLIC);
			}
		}
		if (player.getChatMode() == ClanPlayer.CHAT_ALLY) {
			if (player.getClan() != null) {
				player.getClan().messageAllies("<"+name+"> (ally) "+msg);
				ClanPlugin.getInstance().log("<"+name+"> (ally) "+msg);
				return;
			} else {
				player.setChatMode(ClanPlayer.CHAT_PUBLIC);
			}
		}
		ClanPlugin.getInstance().messageAll("<"+name+"> "+msg);
		ClanPlugin.getInstance().log("<"+name+"> "+msg);
		
	}

	public void onPlayerJoin(ClanPlayer player) {
		player.logIn();
		
	}

	public void onPlayerQuit(ClanPlayer player) {
		player.logOut();
		
	}

}
