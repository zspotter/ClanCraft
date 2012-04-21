package zerothindex.clancraft.command;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.MessageReceiver;
import zerothindex.clancraft.WorldPlayer;
import zerothindex.clancraft.clan.ClanPlayer;

public class CmdInvite extends CommandBase {

	@Override
	public String getName() {
		return "invite";
	}

	@Override
	public String getDescription() {
		return "invite a player to join your clan";
	}

	@Override
	public String getUsage() {
		return "/c invite <player>";
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
			return true;
		}
		ClanPlayer invited = ClanPlugin.getInstance().findClanPlayer(args[1]);
		if (invited == null) {
			sender.message("&xCouldn't find \""+args[1]+"\".");
			return true;
		}
		if (invited.getClan().equals(cp.getClan())) {
			sender.message("&xThat player is already part of this clan!");
			return true;
		}
		cp.getClan().addInvite(invited.getName());
		invited.message("&b&mYou have been invited to "+cp.getClan().getName()+".");
		invited.message("&mType \"/c join "+cp.getClan().getName()+"\" to join!");
		sender.message("&mYou invited "+invited.getName()+" to your clan.");
		return true;
	}

}
