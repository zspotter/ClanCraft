package zerothindex.minecraft.clancraft.command;

import java.util.HashSet;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import zerothindex.minecraft.clancraft.Messageable;
import zerothindex.minecraft.clancraft.clan.ClanPlayer;

public class CommandManager {

	private HashSet<CommandBase> commands;
	private CommandBase commandHome;
	
	public CommandManager() {
		commands = new HashSet<CommandBase>();
		commandHome = new CommandHome();
	}
	
	
	public void handle(Messageable sender, String[] args) {
		if (args.length == 1 || args.length == 0) {
			commandHome.handle(sender, args);
			return;
		} 
		
		for (CommandBase cmd : commands) {
			if (cmd.getName().equalsIgnoreCase(args[2])) {
				cmd.handle(sender, args);
				return;
			}
		}
		
		sender.message("Unkown command \""+args[2]+"\"");
	}
}
