package zerothindex.minecraft.clancraft.bukkit;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import zerothindex.minecraft.clancraft.Messageable;
import zerothindex.minecraft.clancraft.clan.ClanManager;
import zerothindex.minecraft.clancraft.clan.ClanPlayer;
import zerothindex.minecraft.clancraft.command.CommandManager;

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
public class ClanPlugin extends JavaPlugin {
	
	private ClanManager clanManager;
	private CommandManager commandManager;
	
	private HashMap<Player, ClanPlayer> players;
	
	private static ClanPlugin instance;
	
	/**
	 * Called when the plugin is enabled.
	 */
	public void onEnable() {
		instance = this;
		//log("Enabling "+this.getDescription().getFullName()+"...");
		
		PluginManager pm = getServer().getPluginManager();
	    //pm.registerEvents(new DEPListener(), this);
		
		players = new HashMap<Player, ClanPlayer>();
		
		clanManager = new ClanManager();
		commandManager = new CommandManager();
		
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
    		commandManager.handle(new MessageableBukkit(sender), args);
    	}
    	return true; 
    }

    public static ClanPlugin getInstance() {
    	return instance;
    }

	/**
	 * Turns a Bukkit Player into a ClanPlayer
	 * @param p the Player
	 * @return a ClanPlayer representation of the player, making a new one if needed
	 */
	public ClanPlayer getClanPlayer(Player p) {
		ClanPlayer cp = players.get(p);
		if (cp != null) return cp;
		cp = new ClanPlayer(new MessageableBukkit(p));
		players.put(p, cp);
		return cp;
	}

	public void log(String str) {
		System.out.println("["+this.getDescription().getName()+"] "+str);
	}

}
