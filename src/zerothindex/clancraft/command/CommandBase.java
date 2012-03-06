package zerothindex.clancraft.command;

import zerothindex.clancraft.Messageable;

public abstract class CommandBase {
	 
	public abstract String getName();
	
	public abstract String getDescription();
	
	public abstract String getUsage();
	
	public abstract boolean playerOnly();
	
	public abstract boolean adminOnly();
	
	public abstract boolean handle(Messageable sender, String[] args);
}
