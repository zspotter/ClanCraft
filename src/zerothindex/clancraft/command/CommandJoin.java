package zerothindex.clancraft.command;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.MessageReceiver;
import zerothindex.clancraft.clan.Clan;
import zerothindex.clancraft.clan.ClanPlayer;

public class CommandJoin extends CommandBase{

	@Override
	public String getName() {
		return "join";
	}

	@Override
	public String getDescription() {
		return "join the given clan";
	}

	@Override
	public String getUsage() {
		return "/c join <clan>";
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
		if (args.length != 2) return false;
		Clan c = ClanPlugin.getInstance().getClanManager().findClan(args[1]);
		if (c == null) {
			sender.message("Clan \""+args[1]+"\" not found.");
			return true;
		}
		ClanPlayer cp = ClanPlugin.getInstance().getClanPlayer(sender);
		if (!c.isClosed() || c.isInvited(cp)) {
			c.addMember(cp);
		} else {
			sender.message("That clan is invite only.");
		}
		return true;
	}

}
