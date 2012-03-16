package zerothindex.clancraft.command;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.MessageReceiver;
import zerothindex.clancraft.WorldPlayer;
import zerothindex.clancraft.clan.Clan;
import zerothindex.clancraft.clan.ClanPlayer;

public class CmdAlly extends CommandBase {

	@Override
	public String getName() {
		return "ally";
	}

	@Override
	public String getDescription() {
		return "request a clan to form an alliance";
	}

	@Override
	public String getUsage() {
		return "/c ally <clan>";
	}

	@Override
	public boolean playerOnly() {
		return true;
	}

	@Override
	public boolean handle(MessageReceiver sender, String[] args) {
		if (!(sender instanceof WorldPlayer)) return false;
		if (args.length != 2) return false;
		ClanPlayer cp = ClanPlugin.getInstance().getClanPlayer((WorldPlayer)sender);
		if (cp.getClan() == null) {
			cp.message("<m>You aren't part of a clan.");
			return true;
		}
		if (cp.getRole() != ClanPlayer.ROLE_LEADER) {
			cp.message("<m>You must be a leader of your clan to declare allies.");
			return true;
		}
		Clan ally = ClanPlugin.getInstance().getClanManager().findClan(args[1]);
		if (ally == null) {
			cp.message("<m>Clan \""+args[1]+"\" not found.");
			return true;
		}
		if (ally.hasRequestedAlly(cp.getClan())) {
			cp.getClan().addAlly(ally.getClanID());
			ally.addAlly(cp.getClan().getClanID());
			cp.getClan().messageClan("<b>You are now allies with "+ally.getName()+".");
			ally.messageClan("<b>You are now allies with "+cp.getClan().getName()+".");
			return true;
		} else {
			cp.getClan().requestAlly(ally.getClanID());
			cp.message("<m>You have requested an alliance with "+ally.getName()
					+". To complete the process, they must request an alliance with you.");
			ally.messageClan("<b>Clan "+cp.getClan().getName()+" has requested an alliance. " +
					"Type \"<m>/c ally "+cp.getClan().getName()+"<m>\" to accept.");
			return true;
		}
		
		
	}

}
