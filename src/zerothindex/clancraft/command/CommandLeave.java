package zerothindex.clancraft.command;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.MessageReceiver;
import zerothindex.clancraft.clan.ClanPlayer;

public class CommandLeave extends CommandBase {

	@Override
	public String getName() {
		return "leave";
	}

	@Override
	public String getDescription() {
		return "leave your faction";
	}

	@Override
	public String getUsage() {
		return "/c leave";
	}

	@Override
	public boolean playerOnly() {
		return true;
	}

	@Override
	public boolean adminOnly() {
		return false;
	}

	@Override
	public boolean handle(MessageReceiver sender, String[] args) {
		ClanPlayer cp = ClanPlugin.getInstance().getClanPlayer(sender);
		if (cp.getClan() == null) {
			sender.message("You aren't part of a clan!");
		} else {
			cp.getClan().kickMember(cp);
		}
		return true;
	}

}
