package zerothindex.clancraft.clan;

/**
 * This class holds a land plot and a "home location". It will be extended
 * by "platform specific" classes (AKA Bukkit). The land should be managed
 * and protected by the object independantly from the rest of the game.
 * 
 * @author zerothindex
 *
 */
public interface ClanPlot {
	
	/**
	 * Set the Clan's respawn position to a given point in a given world
	 * @param world the name of the world
	 * @param x coord
	 * @param y coord
	 * @param z coord
	 * @return success
	 */
	public boolean setSpawn(String world, double x, double y, double z, float yaw, float pitch);
	
	/**
	 * Set the Clan's center of territory to a given point in a given world
	 * @param world the name of the world
	 * @param x coord
	 * @param z coord
	 * @return success
	 */
	public boolean setCenter(String world, double x, double z);
	
	/**
	 * @return the Clan which this plot protects
	 */
	public Clan getClan();
	
	/**
	 * Stop protecting and forget the territory
	 */
	public abstract void unclaim();
	
	/**
	 * The radius of a circle to protect around the center
	 * @param r
	 */
	public void setRadius(int r);
	
	/**
	 * @return the radius being protected around the center
	 */
	public int getRadius();
}
