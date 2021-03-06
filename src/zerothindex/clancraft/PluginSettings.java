package zerothindex.clancraft;

import java.util.Arrays;
import java.util.HashSet;

public class PluginSettings {

	/** Maximum radius for a plot */
	public static int maximumRadius = 75;
	
	/** Minimum number of clan members needed to claim land */
	public static int minimumMemberClaim = 3;
	
	/** Maximum number of characters in a clan name **/
	public static int maximumClanNameLength = 7;
	
	/** Can clan members hurt t each other? */
	public static boolean friendlyFire = false;
	
	/** Blocks that any player can interact with inside of any plot */
	public static HashSet<String> allowUse = new HashSet<String>(Arrays.asList(new String[] 
			{"WOODEN_DOOR", "TRAP_DOOR", 
			"CHEST", "FURNACE", "WORKBENCH", "DISPENSER", "ENCHANTMENT_TABLE", "WOOD_PLATE"}));
}
