package zerothindex.clancraft.bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

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
	
	/**
	 * Called when the plugin is enabled.
	 */
	public void onEnable() {
		clanPlugin = new ClanPlugin(this.getDescription().getName());
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new BukkitPlayerListener(this), this);
		
	}
	
	/**
	 * Called when the plugin is disabled.
	 */
	public void onDisable() {
		clanPlugin.disable();
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
