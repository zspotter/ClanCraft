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
			cp.message("&xYou aren't part of a clan.");
			return true;
		}
		if (cp.getRole() != ClanPlayer.ROLE_LEADER) {
			cp.message("&xYou must be a leader of your clan to declare enemies.");
			return true;
		}
		Clan enemy = ClanPlugin.getInstance().getClanManager().findClan(args[1]);
		if (enemy == null) {
			cp.message("&xClan \""+args[1]+"\" not found.");
			return true;
		}
		cp.getClan().addEnemy(enemy.getClanID());
		enemy.addEnemy(cp.getClan().getClanID());
		cp.getClan().messageClan("&b&mYou are now enemies with &e"+enemy.getName());
		enemy.messageClan("&b&mYou are now enemies with &e"+cp.getClan().getName());
		
		return true;
	}

}
