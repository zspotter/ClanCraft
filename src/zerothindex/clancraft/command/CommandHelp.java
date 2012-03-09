package zerothindex.clancraft.command;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.MessageReceiver;

public class CommandHelp extends CommandBase {

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public String getDescription() {
		return "view the help page";
	}

	@Override
	public String getUsage() {
		return "/c";
	}

	@Override
	public boolean playerOnly() {
		return false;
	}

	@Override
	public boolean adminOnly() {
		return false;
	}

	@Override
	public boolean handle(MessageReceiver sender, String[] args) {
		if (args.length != 2) {
			sender.message("<t>-- ClanCraft --");
			sender.message(" (Type \"/help <command>\" for more information.)");
			for (CommandBase cmd : ClanPlugin.getInstance().getCommandManager().getCommands()) {
				sender.message(" <b>"+cmd.getName()+" <m>- "+cmd.getDescription());
			}
			return true;
		} else {
			for (CommandBase cmd : ClanPlugin.getInstance().getCommandManager().getCommands()) {
				if (args[1].equalsIgnoreCase(cmd.getName())) {
					sender.message("<t>-- ClanCraft --");
					sender.message(" <b>"+cmd.getName()+" <m>- "+cmd.getDescription());
					sender.message(" "+cmd.getUsage());
					return true;
				}
			}
		}
		
		return false;
		
	}

	

	

}
