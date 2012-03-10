package zerothindex.clancraft.command;

import java.util.Set;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.MessageReceiver;
import zerothindex.clancraft.PluginSettings;
import zerothindex.clancraft.WorldPlayer;
import zerothindex.clancraft.bukkit.BukkitWorldPlot;
import zerothindex.clancraft.clan.Clan;
import zerothindex.clancraft.clan.ClanPlayer;
import zerothindex.clancraft.clan.ClanPlot;

public class CmdClaim extends CommandBase {

	@Override
	public String getName() {
		return "claim";
	}

	@Override
	public String getDescription() {
		return "set the center of your territory to your position";
	}

	@Override
	public String getUsage() {
		return "/c claim";
	}

	@Override
	public boolean playerOnly() {
		return true;
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
		if (cp.getClan().getSize() < PluginSettings.minimumMemberClaim) {
			cp.message("You need at least "+PluginSettings.minimumMemberClaim+" players to claim land.");
			return true;
		}
		if (cp.getClan().getPlot() == null) {
			cp.getClan().setPlot(new BukkitWorldPlot(cp.getClan()));
		}
		double[] coords = cp.getCoordinates();
		// make sure no clans are within distance to grow into each other
		Set<Clan> clans = ClanPlugin.getInstance().getClanManager().getClans();
		for (Clan clan : clans) {
			ClanPlot plot = clan.getPlot();
			if (plot != null && plot.isActive()
					&& distance(plot.getX(), plot.getZ(), 
								(int)coords[0], (int)coords[2]) > 2*PluginSettings.maximumRadius) {
				cp.message("Your territory's center must be "+(2*PluginSettings.maximumRadius)
						+" blocks away from the closest clan's center.");
				return true;
			}
		}
		
		boolean success = cp.getClan().getPlot().setCenter(cp.getWorld(), coords[0], coords[2]);
		if (success) {
			cp.getClan().messageClan("Your clan has claimed a "
					+cp.getClan().getPlot().getRadius()+" block radius territory.");
		} else {
			cp.message("<r>Error setting territory!");
		}
		return true;
	}
	
	private double distance(int x1, int z1, int x2, int z2) {
		return Math.sqrt(Math.pow((x1-x2),2)+Math.pow((z1-z2),2));
	}

}
