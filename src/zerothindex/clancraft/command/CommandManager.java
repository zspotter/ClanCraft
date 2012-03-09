package zerothindex.clancraft.command;

import java.util.HashSet;
import java.util.Set;

import zerothindex.clancraft.MessageReceiver;

public class CommandManager {

	private HashSet<CommandBase> commands;
	private CommandBase commandHelp;
	
	public CommandManager() {
		commands = new HashSet<CommandBase>();
		commandHelp = new CmdHelp();
		commands.add(commandHelp);
		
		commands.add(new CmdCreate());
		commands.add(new CmdList());
		commands.add(new CmdChatMode());
		commands.add(new CmdLeave());
		commands.add(new CmdJoin());
		commands.add(new CmdPrivate());
		commands.add(new CmdClaim());
	}
	
	
	public void handle(MessageReceiver sender, String[] args) {
		
		if (args.length == 0) {
			commandHelp.handle(sender, args);
			return;
		} 
		
		for (CommandBase cmd : commands) {
			if (cmd.getName().equalsIgnoreCase(args[0])) {
				if (cmd.playerOnly() && !sender.isPlayer()) {
					sender.message("You must be a player to use that command.");
					return;
				}
				if (cmd.handle(sender, args)) {
					return;
				} else {
					sender.message("Usage: "+cmd.getUsage());
					return;
				}
			}
		}
		
		sender.message("Unkown command \""+args[0]+"\"");
	}
	
	public Set<CommandBase> getCommands() {
		return commands;
	}
}
