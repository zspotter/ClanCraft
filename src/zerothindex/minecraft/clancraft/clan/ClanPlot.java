package zerothindex.minecraft.clancraft.clan;

import org.bukkit.Location;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

/**
 * This class holds a land plot and a "home location". Because this class
 * is very specific to the game world, it will interact with Bukkit specifics.
 * 
 * @author zerothindex
 *
 */
public class ClanPlot {

	// a Location to spawn at
	private Location spawn;
	// WorldGuard's Region 
	private ProtectedRegion plot;
	// radius of the plot
	private int radius;
	
	public ClanPlot() {
		spawn = null;
		plot = null;
		radius = -1;
	}
	
	public void setSpawn(Location s) {
		spawn = s;
	}
	
	public void setPlot(ProtectedRegion p) {
		plot = p;
	}
	public ProtectedRegion getPlot() {
		return plot;
	}
	
	public void setRadius(int r) {

	}
}
