package zerothindex.clancraft.command;

import zerothindex.clancraft.MessageReceiver;

public abstract class CommandBase {
	 
	public abstract String getName();
	
	public abstract String getDescription();
	
	public abstract String getUsage();
	
	public abstract boolean playerOnly();
	
	public abstract boolean adminOnly();
	
	public abstract boolean handle(MessageReceiver sender, String[] args);
}
