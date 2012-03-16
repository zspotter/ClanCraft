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
			sender.message("<t>-- ClanCraft (1/3) --");
			sender.message(" <m>ClanCraft is a PvP and land management plugin.");
			sender.message(" <m>This is a <r>TEST VERSION<m>, and does not represent the final mec"+
					"hanics or state of the plugin.");
			sender.message(" <m>Type \"/c help <page>\" or \"/c help <cmd>\" for help.");
			return true;
		}
		if (page == 2) {
			sender.message("<t>-- ClanCraft (2/3) --");
			sender.message("<t>   Basic Commands:");
			sender.message(" <b>join   <m>- join the specified clan");
			sender.message(" <b>create <m>- create and join a new clan");
			sender.message(" <b>list   <m>- list all clans on the server");
			sender.message(" <b>chat   <m>- change your chat mode");
			sender.message(" <b>leave  <m>- leave your clan");
			sender.message(" <b>help   <m>- view this menu");
			return true;
		}
		if (page == 3) {
			sender.message("<t>-- ClanCraft (3/3) --");
			sender.message("<t>   Clan Commands:");
			sender.message(" <b>invite<m>- invite someone to your clan");
			sender.message(" <b>claim <m>- set the center of your protected land to your position");
			sender.message(" <b>kick  <m>- kick a member from your clan");
			sender.message(" <b>rank  <m>- change the rank of a player in your clan");
			sender.message(" <b>ally  <m>- request an alliance with another clan");
			sender.message(" <b>enemy <m>- declare a clan an enemy");
			return true;
		}	
		if (page == -1) {
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
