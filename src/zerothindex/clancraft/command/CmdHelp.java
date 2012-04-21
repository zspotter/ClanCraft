package zerothindex.clancraft.command;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.MessageReceiver;

public class CmdHelp extends CommandBase {

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
	public boolean handle(MessageReceiver sender, String[] args) {
		int page = 0;
		try {
			page = Integer.parseInt(args[1]);
		} catch (Exception e) {
			page = -1;
		}
		if (args.length != 2 || (page == 1)) {
			sender.message("&t-- ClanCraft (1/3) --");
			sender.message(" &nClanCraft is a PvP and land management plugin.");
			sender.message(" &nThis is a &xTEST VERSION&r&n, and does not represent the final");
			sender.message(" &nmechanics or state of the plugin.");
			sender.message(" &aType \"/c help <page>\" or \"/c help <cmd>\" for help.");
			return true;
		}
		if (page == 2) {
			sender.message("&t-- ClanCraft (2/3) --");
			sender.message("&tBasic Commands:");
			sender.message(" &ajoin &m- join the specified clan");
			sender.message(" &acreate &m- create and join a new clan");
			sender.message(" &alist &m- list all clans on the server");
			sender.message(" &achat &m- change your chat mode");
			sender.message(" &aleave &m- leave your clan");
			sender.message(" &ahelp &m- view this menu");
			return true;
		}
		if (page == 3) {
			sender.message("&t-- ClanCraft (3/3) --");
			sender.message("&tClan Commands:");
			sender.message(" &ainvite &m- invite someone to your clan");
			sender.message(" &aclaim &m- set the center of your protected land to your position");
			sender.message(" &asetspawn &m- set your clan's spawn to your position");
			sender.message(" &akick &m- kick a member from your clan");
			sender.message(" &arank &m- change the rank of a player in your clan");
			sender.message(" &aally &m- request an alliance with another clan");
			sender.message(" &aenemy &m- declare a clan an enemy");
			return true;
		}	
		if (page == -1) {
			for (CommandBase cmd : ClanPlugin.getInstance().getCommandManager().getCommands()) {
				if (args[1].equalsIgnoreCase(cmd.getName())) {
					sender.message("&t-- ClanCraft --");
					sender.message(" &a"+cmd.getName()+" &m- "+cmd.getDescription());
					sender.message(" &mUsage: "+cmd.getUsage());
					return true;
				}
			}
		}
		
		return false;
		
	}

	

	

}
