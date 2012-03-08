package zerothindex.clancraft.bukkit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.domains.PlayerDomain;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import zerothindex.clancraft.ClanPlugin;
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
	private ProtectedCuboidRegion region;
	
	public BukkitWorldPlot(Clan c) {
		clan = c;
		radius = 0;
		spawn = null;
		center = null;
		region = null;
	}
	
	@Override
	public boolean setSpawn(String world, double x, double y, double z, float yaw, float pitch) {
		World w = BukkitClanPlugin.getInstance().getServer().getWorld(world);
		if (w == null) return false;
		spawn = new Location(w, x, y, z, yaw, pitch);
		return true;
		
	}

	@Override
	public boolean setCenter(String world, double x, double y, double z) {
		World w = BukkitClanPlugin.getInstance().getServer().getWorld(world);
		if (w == null) return false;
		center = new Location(w, x, y, z);
		//=====
		region = new ProtectedCuboidRegion(w.getName(), 
				new BlockVector(x-30, 0, z-30), 
				new BlockVector(x+30, w.getMaxHeight(), z+30));
		ClanDomain dom = new ClanDomain(clan);
		region.setMembers(dom);
		BukkitClanPlugin.getWorldGuardPlugin().getGlobalRegionManager().get(center.getWorld()).addRegion(region);
		//=====
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

class ClanDomain extends DefaultDomain {
	
	private Clan clan;
	
	public ClanDomain(Clan c) {
		clan = c;
	}
	
	@Override
	public boolean contains(LocalPlayer player) {
		ClanPlayer cp = ClanPlugin.getInstance().findClanPlayer(player.getName());
		if (cp == null) return false;
		if (cp.getClan() == clan) return true;
		return false;
	}
	
	@Override
	public Set<String> getPlayers() {
		Set<String> players = new HashSet<String>();
		for (ClanPlayer cp : clan.getOnline()) {
			players.add(cp.getName());
		}
		return players;
	}
	
	@Override
	public int size() {
		return clan.getSize();
	}
	
	
}
