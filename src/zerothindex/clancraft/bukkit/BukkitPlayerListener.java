package zerothindex.clancraft.bukkit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.event.ClanEventManager;

public class BukkitPlayerListener implements Listener {

	private ClanEventManager ev; // event manager
	private BukkitClanPlugin bp; // bukkit plugin
	
	public BukkitPlayerListener(BukkitClanPlugin bukkitPlugin) {
		bp = bukkitPlugin;
		ev = ClanPlugin.getInstance().getClanEventManager();
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e) {
		ev.onPlayerJoin(bp.getClanPlayer(e.getPlayer()));
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		ev.onPlayerQuit(bp.getClanPlayer(e.getPlayer()));
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(PlayerChatEvent e) {
		ev.onPlayerChat(bp.getClanPlayer(e.getPlayer()), e.getMessage());
		e.setCancelled(true);
	}
	
	
}
