package zerothindex.clancraft;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;

import org.bukkit.entity.Player;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import zerothindex.clancraft.clan.ClanManager;
import zerothindex.clancraft.clan.ClanPlayer;
import zerothindex.clancraft.command.CommandManager;

//test
public class ClanPlugin {
	
	private static ClanPlugin instance;
	
	private String name;
	private CommandManager commandManager;
	private ClanManager clanManager;
	
	private HashMap<String, ClanPlayer> players;
	
	/**
	 * Creates an instance of the ClanPlugin!
	 * @param name the name to appear on logs
	 */
	public ClanPlugin(String name) {
		this.name = name;
		commandManager = new CommandManager();
		clanManager = new ClanManager();
		players = new HashMap<String, ClanPlayer>();
		instance = this;
	}
	
	/**
	 * Safely destructs the object
	 */
	public void disable() {		
		Writer writer;
		try {
			writer = new OutputStreamWriter(new FileOutputStream("ClanData.json"));
			Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {

				@Override
				public boolean shouldSkipClass(Class<?> arg0) {
					//System.out.println("sC>"+arg0.getCanonicalName());
					return (arg0 == Player.class);
				}

				@Override
				public boolean shouldSkipField(FieldAttributes arg0) {
					//System.out.println("sF>"+arg0.getName());
					return false;
				}
				
			}).setPrettyPrinting().create();
		    gson.toJson(clanManager, writer);
		    
		    //writer = new OutputStreamWriter(new FileOutputStream("ClanSettings.json"));
		    //gson.toJson(PluginSettings.class, writer);
		    
		    writer.close();
		} catch (FileNotFoundException e) {
			log("SEVERE ERROR: Could not save ClanPlugin, file not found.");
			e.printStackTrace();
		} catch (IOException e) {
			log("SEVERE ERROR: Could not save ClanPlugin, IO error.");
			e.printStackTrace();
		}
	}
	
	public static ClanPlugin getInstance() {
		return instance;
	}
	
	public CommandManager getCommandManager() {
		return commandManager;
	}
	
	public ClanManager getClanManager() {
		return clanManager;
	}
	
	/**
	 * Find the ClanPlayer who's name is some String
	 * @param name the name to identify by
	 * @return a ClanPlayer or NULL
	 */
	public ClanPlayer findClanPlayer(String name) {
		return players.get(name.toLowerCase());
	}
	
	/**
	 * Get or create a ClanPlayer
	 * @param player
	 * @return never Null!
	 */
	public ClanPlayer getClanPlayer(WorldPlayer player) {
		ClanPlayer cp = ClanPlugin.getInstance().findClanPlayer(player.getName());
		if (cp == null) {
			cp = new ClanPlayer(player);
			ClanPlugin.getInstance().addClanPlayer(cp);
			cp.logIn();
		}
		return cp;
	}
	
	/**
	 * Adds a ClanPlayer to the game
	 * @param cp the player
	 * @return false if the name is already taken, true otherwise
	 */
	public boolean addClanPlayer(ClanPlayer cp) {
		String name = cp.getName().toLowerCase();
		if (players.containsKey(name)) {
			return false;
		} else {
			players.put(name, cp);
			return true;
		}
	}
	
	public void messageAll(String string) {
		for (ClanPlayer player : players.values()) {
			player.message(string);
		}
		
	}
	
	public void log(String str) {
		System.out.println("["+name+"] "+str);
	}

}
