package zerothindex.clancraft.command;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.MessageReceiver;
import zerothindex.clancraft.clan.Clan;

public class CmdSetRadius extends CommandBase {

	@Override
	public String getName() {
		return "setradius";
	}

	@Override
	public String getDescription() {
		return "a debug command for setting the radius of a plot";
	}

	@Override
	public String getUsage() {
		return "/c setradius <clan> <radius>";
	}

	@Override
	public boolean playerOnly() {
		return false;
	}

	@Override
	public boolean handle(MessageReceiver sender, String[] args) {
		if (args.length != 3) return false;
		Clan clan = ClanPlugin.getInstance().getClanManager().findClan(args[1]);
		if (clan == null) {
			return false;
		}
		int radius = 0;
		try {
			radius = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			return false;
		}
		clan.getPlot().setRadius(radius);
		sender.message("&mSet "+clan.getName()+"'s radius to "+radius);
		return true;
	}

}
