package zerothindex.clancraft.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.PluginSettings;
import zerothindex.clancraft.clan.Clan;
import zerothindex.clancraft.clan.ClanPlayer;
import zerothindex.clancraft.clan.ClanPlot;

public class PlayerListener implements Listener {

	private BukkitClanPlugin bp; // bukkit plugin
	
	public PlayerListener(BukkitClanPlugin bukkitPlugin) {
		bp = bukkitPlugin;
	}
	
	/**
	 * Prevents non-clan players from interacting with blocks in a plot
	 */
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getClickedBlock() == null) return;
		//if interacting with tnt in hand in enemy land, make explosion
		//If interacting with TNT in enemy territory, allow + auto-ignite!
		ClanPlayer cp = bp.getClanPlayer(e.getPlayer());
		Block adjacent = e.getClickedBlock().getRelative(e.getBlockFace());
		Clan clan = ClanPlugin.getInstance().getClanManager()
				.getClanAtLocation(adjacent.getWorld().getName(), (int)adjacent.getX(), (int)adjacent.getZ());
		
		if (clan == null) return;
		if (e.isCancelled()) return;
		Block clicked = e.getClickedBlock();
		if (cp == null) {
			e.setCancelled(true);
			return;
		} 
		if (cp.getClan() != null && clan.equals(cp.getClan())) {
			// its the player's own clan
			return;
		}
		if (PluginSettings.allowUse.contains(clicked.getType().toString())) {
			return;
		} else {
			e.setCancelled(true);
		}
		
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) 
				&& (e.getItem() != null) 
				&& (e.getItem().getType() == Material.TNT)) {
			//find out if block was placed in enemy territory
			//determine if TNT should auto ignite
			if (clan.isEnemy(cp.getClan())) {
				//remove 1 TNT from player inventory because the event was cancelled
				ItemStack tntStack = e.getItem();
				if (tntStack.getAmount() <= 1) {
					//Can't set amount to 0 for some reason...
					e.getPlayer().setItemInHand(null);
				} else {
					//Deduct 1 TNT from hand
					tntStack.setAmount(tntStack.getAmount() -1);
				}
				
				//Never actually place a block in enemy land... Just spawn a lit TNT entity
				Location location = new Location(adjacent.getWorld(), adjacent.getX() + 0.5D, adjacent.getY() + 0.5D, adjacent.getZ() + 0.5D);
	            TNTPrimed tnt = adjacent.getWorld().spawn(location, TNTPrimed.class);
	            ClanPlugin.getInstance().log("Let ["+cp.getClan().getName()+"] "+cp.getName()+" place TNT in territory of "+clan.getName());
	            
	            return;
			}
		}
	}
	
	/**
	 * Messages players when they enter or exit claimed land.
	 */
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (e.isCancelled()) return;
		Location to = e.getTo();
		Location from = e.getFrom();
		// only perform plot enter/exit checks if player has moved whole block
		if (to.getBlockX() == from.getBlockX()
				&& to.getBlockZ() == from.getBlockZ()
				&& to.getBlock().getWorld().equals(from.getBlock().getWorld())) return;
		
		// perform enter exit checks
		Clan entering = ClanPlugin.getInstance().getClanManager()
				.getClanAtLocation(to.getWorld().getName(), (int)to.getX(), (int)to.getZ());
		Clan exiting = ClanPlugin.getInstance().getClanManager()
				.getClanAtLocation(from.getWorld().getName(), (int)from.getX(), (int)from.getZ());
		// not entering or exiting
		if (entering == null && exiting == null) return;
		if (entering != null && exiting != null && entering.equals(exiting)) return;
		
		ClanPlayer cp = ClanPlugin.getInstance().findClanPlayer(e.getPlayer().getName());
		if (cp == null) {
			ClanPlugin.getInstance().log("Could not find ClanPlayer \""+
					e.getPlayer().getName()+"\" while tracking movement! Kicking player.");
			e.getPlayer().kickPlayer("Internal error. Please reconnect.");
			e.setCancelled(true);
			return;
		}
		
		// entering a plot
		if (entering != null) {
			cp.message("<m>Entering "+entering.getRelationTag(cp)+entering.getName()+
					(entering.getDescription().equals("")? "" : " - "+entering.getDescription()));
		// exiting plot
		} else if (exiting != null) {
			cp.message("<m>Leaving "+exiting.getRelationTag(cp)+exiting.getName());
		}
		
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		ClanPlayer cp = bp.getClanPlayer(e.getPlayer());
		if (cp.getClan() != null && cp.getClan().getPlot().getSpawn() != null) {
			ClanPlot p = cp.getClan().getPlot();
			double[] coords = p.getSpawn();
			float[] dir = p.getSpawnDir();
			e.setRespawnLocation(new Location(Bukkit.getWorld(p.getWorld()), coords[0], coords[1], coords[2], 
					dir[0], dir[1]));
		}
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e) {
		ClanPlayer cp = bp.getClanPlayer(e.getPlayer());
		cp.logIn(new BukkitPlayer(e.getPlayer()));
		Clan entering = ClanPlugin.getInstance().getClanManager()
				.getClanAtLocation(e.getPlayer().getLocation().getWorld().getName(), 
						(int)e.getPlayer().getLocation().getX(), 
						(int)e.getPlayer().getLocation().getZ());
		if (entering == null) return;
		cp.message("<m>Entering "+entering.getRelationTag(cp)+entering.getName()+
				(entering.getDescription().equals("")? "" : " - "+entering.getDescription()));
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
		String msg = BukkitClanPlugin.stripMessage(e.getMessage()); //so players cant use color tags in chat
		String name = "";
		String str;
		if (player.getClan() == null) {
			name = player.getName();
			player.setChatMode(ClanPlayer.CHAT_PUBLIC);
		} else {
			name = player.getClan().getName()+" "+player.getName();
		}
		if (player.getChatMode() == ClanPlayer.CHAT_CLAN) {
			str = "<<g>"+name+"<n>> <g>(clan) <n>"+msg;
			player.getClan().messageClan(str);
		} else if (player.getChatMode() == ClanPlayer.CHAT_ALLY) {
			str = "<<g>"+name+"<n>> <b>(ally) <n>"+msg;
			player.getClan().messageClan(str);
			str = "<<b>"+name+"<n>> <b>(ally) <n>"+msg;
			player.getClan().messageAllies(str);
		} else {
			for (Player p : Bukkit.getOnlinePlayers()) {
				String tag = "";
				if (player.getClan() != null) {
					ClanPlayer target = ClanPlugin.getInstance().findClanPlayer(p.getName());
					if (target != null) 
						tag = player.getClan().getRelationTag(target);
				}
				p.sendMessage(BukkitClanPlugin.parseMessage("<"+tag+name+"<n>> "+msg));
			}
			str = "<"+name+"> "+msg;
		}
		//log the message
		System.out.println(BukkitClanPlugin.stripMessage(str));
	}
	
	
	
	
}
