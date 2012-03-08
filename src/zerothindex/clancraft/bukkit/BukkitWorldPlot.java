package zerothindex.clancraft.bukkit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.UnsupportedIntersectionException;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.clan.Clan;
import zerothindex.clancraft.clan.ClanPlayer;
import zerothindex.clancraft.clan.ClanPlot;

/**
 * Manages and protects a plot of land in a Bukkit world using WorldGuard.
 * This is essentially an adapter between the clan plugin and WorldGuard.
 * @author zerothindex
 *
 */
public class BukkitWorldPlot extends ProtectedCuboidRegion implements ClanPlot {

	private Clan clan;
	private int radius;
	private Location spawn;
	private String world;
	private int centerX;
	private int centerZ;
	private boolean active;
	private ClanDomain domain;
	private boolean registered;
	
	public BukkitWorldPlot(Clan c) {
		super(("plot["+c.getName()+"]"), new BlockVector(0,0,0), new BlockVector(0,0,0));
		clan = c;
		radius = 10; // default 0
		spawn = null;
		centerX = 0;
		centerZ = 0;
		world = null;
		active = true; // default false
		domain = new ClanDomain(c);
		registered = false;
		this.setMembers(domain);
		// Set flags for protection
		this.setFlag(DefaultFlag.GREET_MESSAGE, ("Entering "+clan.getName()+" - "+clan.getDescription()));
		this.setFlag(DefaultFlag.FAREWELL_MESSAGE, ("Leaving "+clan.getName()+"."));
	}
	
	@Override
	public boolean setSpawn(String worldName, double x, double y, double z, float yaw, float pitch) {
		World w = BukkitClanPlugin.getInstance().getServer().getWorld(worldName);
		if (w == null) return false;
		spawn = new Location(w, x, y, z, yaw, pitch);
		return true;
	}

	@Override
	public boolean setCenter(String worldName, double x, double z) {
		World w = BukkitClanPlugin.getInstance().getServer().getWorld(worldName);
		if (w == null) return false;
		world = worldName;
		centerX = (int)Math.floor(x);
		centerZ = (int)Math.floor(z);
		active = true;
		if (!registered) {
			BukkitClanPlugin.getWorldGuardPlugin().getGlobalRegionManager()
				.get(Bukkit.getWorld(world)).addRegion(this);
			registered = true;
		}
		return true;
	}

	@Override
	public Clan getClan() {
		return clan;
	}

	@Override
	public void unclaim() {
		BukkitClanPlugin.getWorldGuardPlugin().getGlobalRegionManager()
			.get(Bukkit.getWorld(world)).removeRegion(getId());
		spawn = null;
		world = null;
		centerX = 0;
		centerZ = 0;
		radius = 0;
		active = false;
	}

	@Override
	public void setRadius(int r) {
		radius = r;
	}

	@Override
	public int getRadius() {
		return radius;
	}

	@Override
	public boolean contains(Vector v) {
		if (!active || radius <= 0) return false;
		return (new Vector(v.getBlockX(), 0, v.getBlockZ())).distance(new Vector(centerX, 0, centerZ)) <= radius;
	}
	
	//TODO this
	@Override
	public List<ProtectedRegion> getIntersectingRegions(
			List<ProtectedRegion> arg0) throws UnsupportedIntersectionException {
		return super.getIntersectingRegions(arg0);
	}

	@Override
	public BlockVector getMaximumPoint() {
		return new BlockVector(centerX+radius, Bukkit.getWorld(world).getMaxHeight(), centerZ+radius);
	}

	@Override
	public BlockVector getMinimumPoint() {
		return new BlockVector(centerX-radius, 0, centerZ-radius);
	}

	@Override
	public String getTypeName() {
		return "clanplot-round";
	}

	@Override
	public int volume() {
		return (int) Math.floor(radius * radius * Math.PI * Bukkit.getWorld(world).getMaxHeight());
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
		return (cp.getClan() == clan);
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
		return clan.getOnlineSize();
	}
	
	
}
