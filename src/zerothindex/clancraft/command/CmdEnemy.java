package zerothindex.clancraft.command;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.MessageReceiver;
import zerothindex.clancraft.WorldPlayer;
import zerothindex.clancraft.clan.Clan;
import zerothindex.clancraft.clan.ClanPlayer;

public class CmdEnemy extends CommandBase {

	@Override
	public String getName() {
		return "enemy";
	}

	@Override
	public String getDescription() {
		return "mark a clan as an enemy";
	}

	@Override
	public String getUsage() {
		return "/c enemy <clan>";
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
			cp.message("You aren't part of a clan.");
			return true;
		}
		if (cp.getRole() != ClanPlayer.ROLE_LEADER) {
			cp.message("You must be a leader of your clan to declare enemies.");
			return true;
		}
		Clan enemy = ClanPlugin.getInstance().getClanManager().findClan(args[1]);
		if (enemy == null) {
			cp.message("Clan \""+args[1]+"\" not found.");
			return true;
		}
		cp.getClan().addEnemy(enemy.getClanID());
		enemy.addEnemy(cp.getClan().getClanID());
		cp.getClan().messageClan("<r> You are now enemies with "+enemy.getName()+".");
		enemy.messageClan("<r> You are now enemies with "+cp.getClan().getName()+".");
		
		return true;
	}

}
