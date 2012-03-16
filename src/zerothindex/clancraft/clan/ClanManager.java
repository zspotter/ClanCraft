package zerothindex.clancraft.clan;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ClanManager {
	
	private int nextID;
	
	private HashMap<Integer, Clan> clans;
	
	public ClanManager() {
		clans = new HashMap<Integer, Clan>();
		nextID = 0;
	}
	
	public ClanManager(Set<Clan> clans) {
		clans = new HashSet<Clan>(clans);
	}
	
	public Collection<Clan> getClans() {
		return clans.values();
	}
	
	public void addClan(Clan clan) {
		clans.put(clan.getClanID(), clan);
	}
	
	public void removeClan(Clan clan) {
		clans.remove(clan);
		//clan.disband();
	}
	
	/**
	 * Attempts to find the largest clan based on the given name which may be abbreviated.
	 * @param name the name or tag of the clan
	 * @return null or a matching clan
	 */
	public Clan findClan(String str) {
		String name = str.toLowerCase();
		Clan found = null;
		for (Clan clan : clans.values()) {
			if (clan.getName().toLowerCase().startsWith(name)) {
				if (found == null || found.getSize() < clan.getSize()) {
					found = clan;
				}
			}
		}
		return found;
	}
	
	/**
	 * Finds what clan's plot (if any) is at the given coords
	 * @param world
	 * @param x
	 * @param z
	 * @return a Clan or null
	 */
	public Clan getClanAtLocation(String world, double x, double z) {
		for (Clan clan : clans.values()) {
			if (clan.getPlot().isActive()) {
				if (clan.getPlot().contains(world, x, z)) {
					return clan;
				}
			}
		}
		return null;
	}

	/**
	 * @return the next unused clan id
	 */
	public int nextID() {
		return nextID++;
	}

	/**
	 * @param clanID the ID of the clan
	 * @return a clan or null
	 */
	public Clan getClan(int clanID) {
		return clans.get(clanID);
		
	}
	
}
