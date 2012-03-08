package zerothindex.clancraft.command;

import java.util.HashSet;
import java.util.Set;

import zerothindex.clancraft.MessageReceiver;

public class CommandManager {

	private HashSet<CommandBase> commands;
	private CommandBase commandHelp;
	
	public CommandManager() {
		commands = new HashSet<CommandBase>();
		commandHelp = new CommandHelp();
		commands.add(commandHelp);
		
		commands.add(new CommandCreate());
		commands.add(new CommandList());
		commands.add(new CommandChatMode());
		commands.add(new CommandLeave());
		commands.add(new CommandJoin());
		commands.add(new CommandPrivate());
		commands.add(new CommandSetLand());
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
