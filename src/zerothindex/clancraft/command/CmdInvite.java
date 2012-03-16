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
			sender.message("<r>You aren't part of a clan.");
			return true;
		}
		if (cp.getRole() != ClanPlayer.ROLE_LEADER) {
			sender.message("<r>You must be a clan leader to do that.");
		}
		cp.getClan().addInvite(args[1]);
		sender.message("<t>You invited \""+args[1]+"\" to your clan.");
		return true;
	}

}
