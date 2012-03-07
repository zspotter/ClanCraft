package zerothindex.clancraft.bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.clan.ClanPlayer;

/**
 * The "main" class of the Bukkit plugin. Because this plugin aims to convert
 * to the official server API when it is available, we will try to keep all
 * Bukkit interaction as separate from the core of the plugin as possible.
 * 
 * This class will try to be THE ONLY WAY that Bukkit and Clan classes will 
 * interact with each other!
 * 
 * @author zerothindex
 *
 */
public class BukkitClanPlugin extends JavaPlugin {
	
	private static ClanPlugin clanPlugin;
	private static WorldGuardPlugin worldGuard;
	
	/**
	 * Called when the plugin is enabled.
	 */
	public void onEnable() {
		clanPlugin = new ClanPlugin(this.getDescription().getName());
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new BukkitPlayerListener(this), this);
		
		Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
	    // WorldGuard may not be loaded
	    if (plugin != null && (plugin instanceof WorldGuardPlugin)) {
	    	worldGuard = (WorldGuardPlugin) plugin;
	    } else {
	    	ClanPlugin.getInstance().log("WorldGuard not found! Disabling ClanCraft...");
	    	this.getServer().getPluginManager().disablePlugin(this);
	    }
		
	}
	
	/**
	 * Called when the plugin is disabled.
	 */
	public void onDisable() {
		clanPlugin.disable();
	}
	
	/**
	 * @return world guard
	 */
	public static WorldGuardPlugin getWorldGuardPlugin() {
		return worldGuard;
	}
	
	/**
	 * Called when a command is made
	 */
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
    	if(cmd.getName().equalsIgnoreCase("c")){
    		ClanPlugin.getInstance().getCommandManager().handle(new MessageableBukkit(sender), args);
    	}
    	return true; 
    }
    
    /**
     * Gets an existing or creates a new ClanPlayer for the given Player
     * @param p a Player
     * @return a ClanPlayer
     */
    public ClanPlayer getClanPlayer(Player p) {
    	ClanPlayer cp = ClanPlugin.getInstance().findClanPlayer(p.getName());
		if (cp == null) {
			cp = new ClanPlayer(new MessageableBukkit(p));
			ClanPlugin.getInstance().addClanPlayer(cp);
		}
		return cp;
    }

}
