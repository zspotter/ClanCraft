package zerothindex.clancraft;

import java.util.HashMap;

import zerothindex.clancraft.clan.ClanManager;
import zerothindex.clancraft.clan.ClanPlayer;
import zerothindex.clancraft.command.CommandManager;
import zerothindex.clancraft.event.ClanEventManager;

public class ClanPlugin {
	
	private static ClanPlugin instance;
	
	private String name;
	private CommandManager commandManager;
	private ClanManager clanManager;
	private ClanEventManager clanEventManager;
	
	private HashMap<String, ClanPlayer> players;
	
	/**
	 * Creates an instance of the ClanPlugin!
	 * @param name the name to appear on logs
	 */
	public ClanPlugin(String name) {
		this.name = name;
		commandManager = new CommandManager();
		clanManager = new ClanManager();
		clanEventManager = new ClanEventManager();
		players = new HashMap<String, ClanPlayer>();
		instance = this;
	}
	
	/**
	 * Safely destructs the object
	 */
	public void disable() {		
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
	
	public ClanEventManager getClanEventManager() {
		return clanEventManager;
	}
	
	/**
	 * Find the ClanPlayer who's name is some String
	 * @param name the name to identify by
	 * @return a ClanPlayer or NULL
	 */
	public ClanPlayer getClanPlayer(String name) {
		return players.get(name);
	}
	
	/**
	 * Adds a ClanPlayer to the game
	 * @param cp the player
	 * @return false if the name is already taken, true otherwise
	 */
	public boolean addClanPlayer(ClanPlayer cp) {
		if (players.containsKey(cp.getName())) {
			return false;
		} else {
			players.put(cp.getName(), cp);
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
