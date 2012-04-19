package zerothindex.clancraft.bukkit;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class EntityListener implements Listener {
	
	@EventHandler
	public void onEntityPrime(ExplosionPrimeEvent e) {
		e.setRadius(0f); //all explosions are manually calculated
	}
	
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		/* The flow of block destruction:
		 * 
		 *  OBSIDIAN* > LAPIS BLOCK > SMOOTH BRICK > COBBLESTONE (and everything else) > AIR
		 *  
		 *  Iron doors don't break unless the block beneath them does.
		 *  Stone/cobble has a 30% chance of turning to gravel is not directly aligned with tnt
		 *  * Obsidian only degrades when directly aligned with tnt
		 *  
		 */
		if (event.isCancelled()) return;
		// if explosion isn't from tnt, dont do block damage
		if (!(event.getEntity() instanceof TNTPrimed)) {
			event.setCancelled(true);
		}
		event.blockList().clear(); // don't destroy blocks, we'll do that manually.

		// Go through a list of adjacent blocks and add one's to be destroyed to the event.blockList
		Block center = event.getLocation().getBlock();
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				for (int z = -1; z <= 1; z++) {
					Material type = center.getRelative(x,y,z).getType(); //type of current block
					boolean critHit = (x == 0 || y == 0 || z == 0); //is block directly aligned with center?
					
					if        (critHit && type == Material.OBSIDIAN) {
						center.getRelative(x,y,z).setType(Material.LAPIS_BLOCK);
						
					} else if (type == Material.LAPIS_BLOCK) {
						center.getRelative(x,y,z).setType(Material.SMOOTH_BRICK);
						
					} else if (type == Material.SMOOTH_BRICK) {
						center.getRelative(x,y,z).setType(Material.COBBLESTONE);
						
					} else if ( !critHit && (type == Material.COBBLESTONE || type == Material.STONE) 
								&& (Math.random() < 0.3) ) {
							center.getRelative(x,y,z).setType(Material.GRAVEL);
							
							// every other type of block gets destroyed (except bedrock and iron door)
					} else if (type != Material.BEDROCK && type != Material.IRON_DOOR_BLOCK){  
						event.blockList().add(center.getRelative(x,y,z));
					}
				}
			}
		}
	}
	
	// to prevent mobs from triggering stone plates (everywhere, but especially in territories)
	@EventHandler
	public void onEntityInteract(EntityInteractEvent e) {
		if (e.getBlock().getType().equals(Material.STONE_PLATE)) {
			e.setCancelled(true);
		}
	}
}
