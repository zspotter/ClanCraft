package zerothindex.clancraft.bukkit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.clan.ClanPlayer;

public class BukkitPlayerListener implements Listener {

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e) {
		ClanPlayer cp = ClanPlugin.getInstance().getClanPlayer(e.getPlayer().getName());
		if (cp == null) {
			cp = new ClanPlayer(new MessageableBukkit(e.getPlayer()));
			ClanPlugin.getInstance().addClanPlayer(cp);
		}
		cp.logIn();
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		ClanPlayer cp = ClanPlugin.getInstance().getClanPlayer(e.getPlayer().getName());
		cp.logOut();
	}
	
	
}
