package zerothindex.clancraft;

/**
 * An interface for something that can be messaged (player, console, etc)
 * 
 * @author zerothindex
 *
 */
public interface MessageReceiver {

	/**
	 * Send a message to this object
	 * @param msg
	 */
	public void message(String msg);
	
	/**
	 * Get the underlying object
	 * @return potentially a Player or a CommandSender
	 */
	public Object getObject();
	
	/**
	 * @return the name of this object.
	 */
	public String getName();
	
	/**
	 * @return true if this is an in-game player
	 */
	public boolean isPlayer();
	
}
