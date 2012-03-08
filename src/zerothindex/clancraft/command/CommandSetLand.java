package zerothindex.clancraft.command;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.MessageReceiver;
import zerothindex.clancraft.WorldPlayer;
import zerothindex.clancraft.bukkit.BukkitWorldPlot;
import zerothindex.clancraft.clan.ClanPlayer;

public class CommandSetLand extends CommandBase {

	@Override
	public String getName() {
		return "setLand";
	}

	@Override
	public String getDescription() {
		return "set the center of your territory to your position";
	}

	@Override
	public String getUsage() {
		return "/c setland";
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
		if (!(sender instanceof WorldPlayer)) return false;
		ClanPlayer cp = ClanPlugin.getInstance().getClanPlayer((WorldPlayer)sender);
		if (cp.getClan() == null) {
			cp.message("You need to belong to a clan claim land.");
			return true;
		}
		if (cp.getRole() != ClanPlayer.ROLE_LEADER) {
			cp.message("You must be a leader of your clan to claim land.");
			return true;
		}
		if (cp.getClan().getPlot() == null) {
			cp.getClan().setPlot(new BukkitWorldPlot(cp.getClan()));
		}
		double[] coords = cp.getCoordinates();
		cp.getClan().getPlot().setCenter(cp.getWorld(), coords[0], coords[1], coords[2]);
		return true;
	}

}
