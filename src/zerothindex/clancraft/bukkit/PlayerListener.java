package zerothindex.clancraft.bukkit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.clan.ClanPlayer;

public class PlayerListener implements Listener {

	private BukkitClanPlugin bp; // bukkit plugin
	
	public PlayerListener(BukkitClanPlugin bukkitPlugin) {
		bp = bukkitPlugin;
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e) {
		bp.getClanPlayer(e.getPlayer()).logIn();
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		bp.getClanPlayer(e.getPlayer()).logOut();
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerChat(PlayerChatEvent e) {
		if (e.isCancelled()) return;
		e.setCancelled(true);
		// [for future use, may want to consider not canceling the event]
		ClanPlayer player = bp.getClanPlayer(e.getPlayer());
		String msg = e.getMessage();
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
	
	
	
	
}
