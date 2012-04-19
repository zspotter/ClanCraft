package zerothindex.clancraft.command;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.MessageReceiver;
import zerothindex.clancraft.WorldPlayer;
import zerothindex.clancraft.clan.ClanPlayer;

public class CmdDescription extends CommandBase {

	@Override
	public String getName() {
		return "desc";
	}

	@Override
	public String getDescription() {
		return "change the description of your faction";
	}

	@Override
	public String getUsage() {
		return "/c desc <description...>";
	}

	@Override
	public boolean playerOnly() {
		return true;
	}

	@Override
	public boolean handle(MessageReceiver sender, String[] args) {
		if (args.length < 2) return false;
		ClanPlayer cp = ClanPlugin.getInstance().getClanPlayer((WorldPlayer)sender);
		if (cp.getClan() == null) {
			sender.message("<r>You aren't part of a clan.");
			return true;
		}
		if (cp.getRole() != ClanPlayer.ROLE_LEADER) {
			sender.message("<r>You must be a clan leader to do that.");
		}
		String desc = "";
		for (int i = 1; i < args.length; i++) {
			desc += " " + args[i];
		}
		desc = desc.substring(1);
		cp.getClan().setDescription(desc);
		cp.getClan().messageClan("<t>"+cp.getName()+" changed the clan description to: "+desc);
		return true;
	}

}
