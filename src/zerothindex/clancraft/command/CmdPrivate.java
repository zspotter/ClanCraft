package zerothindex.clancraft.command;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.MessageReceiver;
import zerothindex.clancraft.WorldPlayer;
import zerothindex.clancraft.clan.ClanPlayer;

public class CmdPrivate extends CommandBase {

	@Override
	public String getName() {
		return "private";
	}

	@Override
	public String getDescription() {
		return "make your faction invite only or public";
	}

	@Override
	public String getUsage() {
		return "/c private <yes|no>";
	}

	@Override
	public boolean playerOnly() {
		return true;
	}

	@Override
	public boolean handle(MessageReceiver sender, String[] args) {
		if (args.length != 2) return false;
		boolean closed = true;
		if (args[1].toLowerCase().startsWith("y")) {
			closed = true;
		} else if (args[1].toLowerCase().startsWith("n")) {
			closed = false;
		} else {
			return false;
		}
		ClanPlayer cp = ClanPlugin.getInstance().getClanPlayer((WorldPlayer)sender);
		if (cp.getClan() == null) {
			sender.message("You aren't part of a clan!");
			return true;
		}
		if (cp.getRole() != ClanPlayer.ROLE_LEADER) {
			sender.message("You must be a leader of your clan to do that.");
			return true;
		}
		if (closed == cp.getClan().isClosed()) {
			sender.message("Your clan is already "+ (closed? "private" : "public")+".");
			return true;
		}
		cp.getClan().setClosed(closed);
		cp.getClan().messageClan("Your clan is now "+(closed? "private" : "public")+".");
		return true;
		
	}

}
