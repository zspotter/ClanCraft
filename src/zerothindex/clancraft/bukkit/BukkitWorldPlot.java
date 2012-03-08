package zerothindex.clancraft.bukkit;

import org.bukkit.Location;
import org.bukkit.World;

import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

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

	private Clan clan;
	private int radius;
	private Location spawn;
	private Location center;
	
	public BukkitWorldPlot(Clan c) {
		clan = c;
		radius = 0;
		spawn = null;
		center = null;
		
	}
	
	@Override
	public boolean setSpawn(String world, double x, double y, double z) {
		World w = BukkitClanPlugin.getInstance().getServer().getWorld(world);
		if (w == null) return false;
		spawn = new Location(w, x, y, z);
		return true;
		
	}

	@Override
	public boolean setCenter(String world, double x, double y, double z) {
		World w = BukkitClanPlugin.getInstance().getServer().getWorld(world);
		if (w == null) return false;
		center = new Location(w, x, y, z);
		return true;
	}

	@Override
	public Clan getClan() {
		return clan;
	}

	@Override
	public void unclaim() {
		spawn = null;
		center = null;
		radius = 0;
	}

	@Override
	public void setRadius(int r) {
		radius = r;
	}

	@Override
	public int getRadius() {
		return radius;
	}



}
