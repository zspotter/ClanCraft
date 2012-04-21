package zerothindex.clancraft.command;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.MessageReceiver;
import zerothindex.clancraft.WorldPlayer;
import zerothindex.clancraft.clan.ClanPlayer;

public class CmdKick extends CommandBase {

	@Override
	public String getName() {
		return "kick";
	}

	@Override
	public String getDescription() {
		return "kick a player from your faction";
	}

	@Override
	public String getUsage() {
		return "/c kick <player>";
	}

	@Override
	public boolean playerOnly() {
		return true;
	}

	@Override
	public boolean handle(MessageReceiver sender, String[] args) {
		if (args.length != 2) return false;
		ClanPlayer cp = ClanPlugin.getInstance().getClanPlayer((WorldPlayer)sender);
		if (cp.getClan() == null) {
			sender.message("&xYou aren't part of a clan.");
			return true;
		}
		if (cp.getRole() != ClanPlayer.ROLE_LEADER) {
			sender.message("&xYou must be a clan leader to do that.");
		}
		ClanPlayer kicked = ClanPlugin.getInstance().findClanPlayer(args[1]);
		if (kicked == null || kicked.getClan() != cp.getClan()) {
			sender.message("&xPlayer \""+args[1]+"\" isn't part of your clan.");
			return true;
		}
		cp.getClan().kickMember(kicked);
		sender.message("&mYou kicked \""+kicked.getName()+"\" from your clan.");
		return true;
	}

}
