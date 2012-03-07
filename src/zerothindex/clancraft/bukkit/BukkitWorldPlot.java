package zerothindex.clancraft.bukkit;

import zerothindex.clancraft.clan.Clan;
import zerothindex.clancraft.clan.ClanPlayer;
import zerothindex.clancraft.clan.ClanPlot;

/**
 * Manages and protects a plot of land in a Bukkit world using WorldEdit.
 * This is essentially an adapter between the clan plugin and WorldEdit.
 * @author zerothindex
 *
 */
public class BukkitWorldPlot extends ClanPlot {

	@Override
	public void setSpawn(String world, double x, double y, double z) {
		
	}

	@Override
	public void setCenter(String world, double x, double y, double z) {
		
	}

	@Override
	public Clan getClan() {
		return null;
	}

	@Override
	public void unclaim() {
		
	}

	@Override
	public void setRadius(int r) {
		
	}

	@Override
	public int getRadius() {
		return 0;
	}



}
