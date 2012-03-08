package zerothindex.clancraft;

/**
 * This interface will represent a player in the game world
 * 
 * @author zerothindex
 *
 */
public interface WorldPlayer extends MessageReceiver {

	/**
	 * @return The name of the world that the player is in
	 */
	public String getWorld();
	
	/**
	 * @return [x,y,z] coords of the player
	 */
	public double[] getCoordinates();
	
	/**
	 * @return [yaw, pitch] of the player
	 */
	public float[] getOrientation();
	
}
