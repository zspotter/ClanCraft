package zerothindex.clancraft.bukkit;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class EntityListener implements Listener {
	
	@EventHandler
	public void onEntityPrime(ExplosionPrimeEvent e) {
		// maybe figure out a way for the server to not calculate destroyed blocks after this
		// without setting the radius to 0. Player damage and entity things are dealt this way.
	}
	
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		/* The flow of block destruction:
		 * 
		 *  SOLID ORE* > OBSIDIAN* > LAPIS BLOCK > SMOOTH BRICK > COBBLESTONE (and everything else) > AIR
		 *  
		 *  Iron doors don't break unless the block beneath them does.
		 *  Stone/cobble has a 30% chance of turning to gravel if not directly adjacent with tnt
		 *  * only degrades when directly adjacent with tnt
		 *  
		 *  Block checking algorithm:
		 *   - degrade critical hit spots (directly adjacent to tnt faces)
		 *   - degrade edge blocks^1
		 *   - degrade corner blocks^2
		 *   
		 *  ^1 Degrade only if at least one of two laterally adjacent blocks is air or being destroyed
		 *  ^2 Degrade only if at least one of three adjacent blocks is or or being destroyed 
		 *  
		 */
		if (event.isCancelled()) return;
		// if explosion isn't from tnt, dont do block damage
		if (!(event.getEntity() instanceof TNTPrimed)) {
			event.setCancelled(true);
			return;
		}
		event.blockList().clear(); // don't destroy blocks, we'll do that manually.

		int[][] critCoords = {	// crit hit coords
							{0,0,0}, {-1,0,0}, {0,-1,0}, {0,0,-1}, {1,0,0}, {0,1,0}, {0,0,1}};
		int[][] edgeCoords = {	// edge blocks
							{0,-1,-1}, {0,-1,1}, {0,1,-1}, {0,1,1},
							{-1,0,-1}, {-1,0,1}, {1,0,-1}, {1,0,1},
							{-1,-1,0}, {1,-1,0}, {1,1,0}, {-1,1,0}};
		int[][] cornerCoords={	// corner blocks
							{-1,-1,-1},{-1,-1,1},{-1,1,1},{-1,1,-1},
							{1,1,1},{1,1,-1},{1,-1,-1},{1,-1,1}};
		
		
		// Go through all block coords and degrade/destroy
		Block center = event.getLocation().getBlock();
		int x,y,z;
		
		// crit blocks
		for (int i = 0; i < critCoords.length; ++i) {
			x = critCoords[i][0];
			y = critCoords[i][1];
			z = critCoords[i][2];
			
			boolean destroy = degrade(center.getRelative(x,y,z), true);
			if (destroy) {
				event.blockList().add(center.getRelative(x,y,z));
			}
		}
		// edge blocks
		for (int i = 0; i < edgeCoords.length; ++i) {
			x = edgeCoords[i][0];
			y = edgeCoords[i][1];
			z = edgeCoords[i][2];
			
			int[][] adj = edgeAdjacents(edgeCoords[i]);
			
			boolean degrade = event.blockList().contains(center.getRelative(adj[0][0],adj[0][1],adj[0][2]))
					|| event.blockList().contains(center.getRelative(adj[1][0],adj[1][1],adj[1][2]));
			
			if (degrade) {
				boolean destroy = degrade(center.getRelative(x,y,z), false);
				if (destroy) {
					event.blockList().add(center.getRelative(x,y,z));
				}
			}
		}
		// corner blocks
		for (int i = 0; i < cornerCoords.length; ++i) {
			x = cornerCoords[i][0];
			y = cornerCoords[i][1];
			z = cornerCoords[i][2];
			
			int[][] adj = cornerAdjacents(cornerCoords[i]);
			boolean degrade = event.blockList().contains(center.getRelative(adj[0][0],adj[0][1],adj[0][2]))
								|| event.blockList().contains(center.getRelative(adj[1][0],adj[1][1],adj[1][2]))
								|| event.blockList().contains(center.getRelative(adj[2][0],adj[2][1],adj[2][2]));
			
			if (degrade) {
				boolean destroy = degrade(center.getRelative(x,y,z), false);
				if (destroy) event.blockList().add(center.getRelative(x,y,z));
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
	
	/**
	 * Degrades the given block. If block should instead be destroyed, make no change and return true.
	 * @param block to degrade
	 * @param critHit is the block adjacent to a tnt face?
	 * @return true if block should be destroyed, else false
	 */
	private boolean degrade(Block block, boolean critHit) {
		//type of current block
		Material type = block.getType();		
		
		if (type == Material.DIAMOND_BLOCK || type == Material.GOLD_BLOCK 
				|| type == Material.IRON_BLOCK) {
			if (critHit) {
				block.setType(Material.OBSIDIAN);
			}
			
		} else if (type == Material.OBSIDIAN) {
			if (critHit) {
				block.setType(Material.LAPIS_BLOCK);
			}
			
		} else if (type == Material.LAPIS_BLOCK) {
			block.setType(Material.SMOOTH_BRICK);
			
		} else if (type == Material.SMOOTH_BRICK) {
			block.setType(Material.COBBLESTONE);
			
		} else if ( !critHit && (type == Material.COBBLESTONE || type == Material.STONE) 
					&& (Math.random() < 0.2) ) {
				block.setType(Material.GRAVEL);
				
		// every other type of block gets destroyed (except bedrock and iron door)
		} else if (type != Material.BEDROCK && type != Material.IRON_DOOR_BLOCK){  
			return true;
		}
		return false;
	}
	
	// find adjacent blocks to an edge
	private int[][] edgeAdjacents(int[] coords) {
		int[][] adjacents = new int[2][3];
		
		// time for magic HA HA HA
		if (coords[0] == 0) {
			adjacents[0] = new int[] {0,coords[1],0};
			adjacents[1] = new int[] {0,0,coords[2]};
		} else if (coords[1] == 0) {
			adjacents[0] = new int[] {coords[0],0,0};
			adjacents[1] = new int[] {0,0,coords[2]};
		} else if (coords[2] == 0) {
			adjacents[0] = new int[] {coords[0],0,0};
			adjacents[1] = new int[] {0,coords[1],0};
		} else {
			return null;
		}
		
		return adjacents;
	}
	
	private int[][] cornerAdjacents(int[] coords) {
		int[][] adjacents = new int[3][3];
		adjacents[0] = new int[] {0, coords[1], coords[2]};
		adjacents[1] = new int[] {coords[0], 0, coords[2]};
		adjacents[2] = new int[] {coords[0], coords[1], 0};
		return adjacents;
	}
	
	// returns true if this type will not let an explosion pass through it
	private boolean isSolid(int typeID) {
		return (typeID < 256) && 
				!(typeID==6 || typeID==8 || typeID==9 || typeID==10 || typeID==11 || typeID==26
				  || typeID==27 || typeID==28 || typeID==30 || typeID==31 || typeID==32 || typeID==34
				  || typeID==37 || typeID==38 || typeID==39 || typeID==40 || typeID==50 || typeID==51
				  || typeID==55 || typeID==59 || typeID==63 || typeID==65 || typeID==66 || typeID==68
				  || typeID==69 || typeID==70 || typeID==72 || typeID==75 || typeID==76 || typeID==77
				  || typeID==78 || typeID==83 || typeID==85 || typeID==93 || typeID==94 || typeID==0);
	}
}
