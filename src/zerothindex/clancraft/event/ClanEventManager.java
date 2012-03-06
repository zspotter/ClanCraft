package zerothindex.clancraft.event;

import zerothindex.clancraft.clan.ClanPlayer;

/**
 * These methods are called when a coresponding event is fired.
 * 
 * @author zerothindex
 *
 */
public class ClanEventManager {

	private PlayerEvent playerEvent;
	
	public ClanEventManager() {
		playerEvent = new PlayerEvent();
	}
	
	/* PLAYER EVENTS */
	public void onPlayerChat(ClanPlayer player, String msg) {
		playerEvent.onPlayerChat(player, msg);
	}
	
	public void onPlayerJoin(ClanPlayer player) {
		playerEvent.onPlayerJoin(player);
	}
	
	public void onPlayerQuit(ClanPlayer player) {
		playerEvent.onPlayerQuit(player);
	}
}
