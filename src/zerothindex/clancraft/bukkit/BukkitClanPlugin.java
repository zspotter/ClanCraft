package zerothindex.clancraft.bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import zerothindex.clancraft.ClanPlugin;

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
		//log("Enabling "+this.getDescription().getFullName()+"...");
		
		PluginManager pm = getServer().getPluginManager();
	    //pm.registerEvents(new DEPListener(), this);
		
		clanPlugin = new ClanPlugin(this.getDescription().getName());
		
	}
	
	/**
	 * Called when the plugin is disabled.
	 */
	public void onDisable() {
		//log("Disabling "+this.getDescription().getFullName()+"...");
	}
	
	/**
	 * Called when a command is made
	 */
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
    	if(cmd.getName().equalsIgnoreCase("c")){
    		getClanPlugin().getCommandManager().handle(new MessageableBukkit(sender), args);
    	}
    	return true; 
    }
    
    public static ClanPlugin getClanPlugin() {
    	return clanPlugin;
    }

}
