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
	 * @return the Clan id which this plot protects
	 */
	public int getClanID();
	
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

	/**
	 * Recalculate bounds after a player has joined or left
	 */
	public void recalculate();

	/**
	 * @return the center of the plot
	 */
	public int getX();
	
	/**
	 * @return the center of the plot
	 */
	public int getZ();
	
	/**
	 * @return is there land actually claimed?
	 */
	public boolean isActive();
	
	/**
	 * Valid only if isActive() returns true
	 * @return the world this plot is active in
	 */
	public String getWorld();
	
	/**
	 * Is the given point in the plot?
	 * @param world
	 * @param x
	 * @param z
	 * @return
	 */
	public boolean contains(String world, double x, double z);

	/**
	 * @return [x,y,z]
	 */
	public double[] getSpawn();

	/**
	 * @return [yaw, pitch]
	 */
	public float[] getSpawnDir();
}
