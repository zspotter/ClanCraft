package zerothindex.clancraft.bukkit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
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
	
	private static BukkitClanPlugin instance;
	private static ClanPlugin clanPlugin;
	private static WorldGuardPlugin worldGuard;
	
	private HashMap<String,String> tagMap;
	
	/**
	 * Called when the plugin is enabled.
	 */
	public void onEnable() {
		instance = this;
		clanPlugin = new ClanPlugin(this.getDescription().getName());
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new BukkitPlayerListener(this), this);
		
		Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
	    // WorldGuard may not be loaded
	    if (plugin != null && (plugin instanceof WorldGuardPlugin)) {
	    	worldGuard = (WorldGuardPlugin) plugin;
	    } else {
	    	ClanPlugin.getInstance().log("WorldGuard not found! Disabling...");
	    	this.getServer().getPluginManager().disablePlugin(this);
	    }
	    
	    tagMap = new HashMap<String,String>();
	    tagMap.put("<r>", ChatColor.RED.toString());
	    tagMap.put("<g>", ChatColor.GREEN.toString());
	    tagMap.put("<b>", ChatColor.BLUE.toString());
	    tagMap.put("<n>", ChatColor.WHITE.toString());
	    tagMap.put("<t>", ChatColor.GOLD.toString());
	    tagMap.put("<m>", ChatColor.YELLOW.toString());
		
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
	
	public static BukkitClanPlugin getInstance() {
		return instance;
	}
	
	/**
	 * Called when a command is made
	 */
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
    	if(cmd.getName().equalsIgnoreCase("c")){
    		if (sender instanceof Player) 
    			ClanPlugin.getInstance().getCommandManager().handle(new BukkitPlayer((Player)sender), args);
    		else
    			ClanPlugin.getInstance().getCommandManager().handle(new BukkitCommandSender(sender), args);
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
			cp = new ClanPlayer(new BukkitPlayer(p));
			ClanPlugin.getInstance().addClanPlayer(cp);
		}
		return cp;
    }
    
    /**
     * @param str A message to send to a player or console
     * @return a parsed string with color chars
     */
    public static String parseMessage(String str) {
    	String out = "<m>"+str;
    	
    	Iterator<Entry<String,String>> iter = getInstance().tagMap.entrySet().iterator();
    	
    	while(iter.hasNext()) {
    		Entry<String, String> tag = iter.next();
    		out = out.replaceAll(tag.getKey(), tag.getValue());
    	}
    	return out;
    }
    
    /**
     * @param str A message to strip the tags of
     * @return a formatless string
     */
    public static String stripMessage(String str) {
    	String out = str;
    	
    	Iterator<Entry<String,String>> iter = getInstance().tagMap.entrySet().iterator();
    	
    	while(iter.hasNext()) {
    		Entry<String, String> tag = iter.next();
    		out = out.replaceAll(tag.getKey(), "");
    	}
    	return out;
    }

}
