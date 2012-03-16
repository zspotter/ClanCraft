package zerothindex.clancraft.bukkit;

import java.util.HashMap;
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
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.PluginSettings;
import zerothindex.clancraft.clan.Clan;
import zerothindex.clancraft.clan.ClanPlayer;
import zerothindex.clancraft.clan.ClanPlot;

/**
 * Manages and protects a plot of land in a Bukkit world using WorldGuard.
 * This is essentially an adapter between the clanID plugin and WorldGuard.
 * @author zerothindex
 *
 */
public class BukkitWorldPlot extends ProtectedCuboidRegion implements ClanPlot {

	private int clanID;
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
		clanID = c.getClanID();
		radius = 0;
		spawn = null;
		centerX = 0;
		centerZ = 0;
		world = null;
		active = false;
		domain = new ClanDomain(c);
		registered = false;
		this.setMembers(domain);
		// Set flags for protection
		HashMap<Flag<?>, Object> flags = new HashMap<Flag<?>, Object>();
		//flags.put(DefaultFlag.GREET_MESSAGE, ("Entering "+clanID.getName()+" - "+clanID.getDescription()));
		//flags.put(DefaultFlag.FAREWELL_MESSAGE, ("Leaving "+clanID.getName()+"."));
		flags.put(DefaultFlag.CHEST_ACCESS, StateFlag.State.ALLOW);
		flags.put(DefaultFlag.USE, StateFlag.State.ALLOW);
		this.setFlags(flags);
		recalculate();
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
	public int getClanID() {
		return clanID;
	}

	@Override
	public void unclaim() {
		if (registered) {
			BukkitClanPlugin.getWorldGuardPlugin().getGlobalRegionManager()
				.get(Bukkit.getWorld(world)).removeRegion(getId());
		}
		spawn = null;
		world = null;
		centerX = 0;
		centerZ = 0;
		radius = 0;
		active = false;
	}
	
	@Override
	public void recalculate() {
		Clan clan = ClanPlugin.getInstance().getClanManager().getClan(clanID);
		int numPlayers;
		if (clan == null) numPlayers = 0;
		else numPlayers = clan.getSize();
		
		int newRadius = (int) 4*numPlayers+10; //Math.round(8*Math.sqrt(4*numPlayers));
		if (newRadius > PluginSettings.maximumRadius) return; // radius cap
		if (newRadius < radius && active) {
			clan.messageClan("Territory radius decreased to "+newRadius+" blocks.");
		} else if (newRadius > radius && active) {
			clan.messageClan("Territory radius increased to "+newRadius+" blocks.");
		}
		setRadius(newRadius);
		
	}

	@Override
	public void setRadius(int r) {
		radius = r;
		if (radius <= 0) {
			active = false;
		}
	}

	@Override
	public int getRadius() {
		return radius;
	}
	
	@Override
	public boolean contains(String w, double x, double z) {
		if (!active) return false;
		if (!world.equals(w)) return false;
		return contains(new Vector(x,0,z));
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

	@Override
	public int getX() {
		return centerX;
	}

	@Override
	public int getZ() {
		return centerZ;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public String getWorld() {
		if (active) return world;
		return null;
	}

	@Override
	public double[] getSpawn() {
		if (spawn == null || !active) return null;
		return new double[] {spawn.getX(), spawn.getBlockY(), spawn.getBlockZ()};
	}

	@Override
	public float[] getSpawnDir() {
		if (spawn == null || !active) return null;
		return new float[] {spawn.getYaw(), spawn.getPitch()};
	}

}

class ClanDomain extends DefaultDomain {
	
	private int clanID;
	
	public ClanDomain(Clan c) {
		clanID = c.getClanID();
	}
	
	@Override
	public boolean contains(LocalPlayer player) {
		ClanPlayer cp = ClanPlugin.getInstance().findClanPlayer(player.getName());
		if (cp == null) return false;
		return (cp.getClan() == ClanPlugin.getInstance().getClanManager().getClan(clanID));
	}
	
	@Override
	public Set<String> getPlayers() {
		Clan clan = ClanPlugin.getInstance().getClanManager().getClan(clanID);
		Set<String> players = new HashSet<String>();
		for (ClanPlayer cp : clan.getOnline()) {
			players.add(cp.getName());
		}
		return players;
	}
	
	@Override
	public int size() {
		return ClanPlugin.getInstance().getClanManager().getClan(clanID).getOnlineSize();
	}
	
	
}
