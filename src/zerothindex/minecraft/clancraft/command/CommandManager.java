package zerothindex.minecraft.clancraft.command;

import java.util.HashSet;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import zerothindex.minecraft.clancraft.Messageable;
import zerothindex.minecraft.clancraft.bukkit.ClanPlugin;
import zerothindex.minecraft.clancraft.clan.ClanPlayer;

public class CommandManager {

	private HashSet<CommandBase> commands;
	private CommandBase commandHome;
	
	public CommandManager() {
		commands = new HashSet<CommandBase>();
		commandHome = new CommandHome();
	}
	
	
	public void handle(Messageable sender, String[] args) {
		
		ClanPlugin.getInstance().log(args.toString());
		
		if (args.length == 0) {
			commandHome.handle(sender, args);
			return;
		} 
		
		for (CommandBase cmd : commands) {
			if (cmd.getName().equalsIgnoreCase(args[0])) {
				cmd.handle(sender, args);
				return;
			}
		}
		
		sender.message("Unkown command \""+args[0]+"\"");
	}
}
