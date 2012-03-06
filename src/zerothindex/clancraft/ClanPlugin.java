package zerothindex.clancraft;

import zerothindex.clancraft.clan.ClanManager;
import zerothindex.clancraft.command.CommandManager;

public class ClanPlugin {
	
	private static ClanPlugin instance;
	
	private String name;
	private CommandManager commandManager;
	private ClanManager clanManager;
	
	/**
	 * Creates an instance of the ClanPlugin!
	 * @param name the name to appear on logs
	 */
	public ClanPlugin(String name) {
		this.name = name;
		commandManager = new CommandManager();
		clanManager = new ClanManager();
		instance = this;
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
	
	public void log(String str) {
		System.out.println("["+name+"] "+str);
	}

}
