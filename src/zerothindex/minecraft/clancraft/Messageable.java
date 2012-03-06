package zerothindex.minecraft.clancraft;

/**
 * An interface for something that can be messaged (player, console, etc)
 * 
 * @author zerothindex
 *
 */
public interface Messageable {

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
	
}
