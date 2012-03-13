package zerothindex.clancraft.clan;

import java.util.HashSet;
import java.util.Set;

public class ClanManager {
	
	private HashSet<Clan> clans;
	
	public ClanManager() {
		clans = new HashSet<Clan>();
	}
	
	public ClanManager(Set<Clan> clans) {
		clans = new HashSet<Clan>(clans);
	}
	
	public HashSet<Clan> getClans() {
		return clans;
	}
	
	public void addClan(Clan clan) {
		clans.add(clan);
	}
	
	public void disbandClan(Clan clan) {
		clans.remove(clan);
		clan.disband();
	}
	
	/**
	 * Attempts to find the largest clan based on the given name which may be abbreviated.
	 * @param name the name or tag of the clan
	 * @return null or a matching clan
	 */
	public Clan findClan(String str) {
		String name = str.toLowerCase();
		Clan found = null;
		for (Clan clan : clans) {
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
		for (Clan clan : clans) {
			if (clan.getPlot().isActive()) {
				if (clan.getPlot().contains(world, x, z)) {
					return clan;
				}
			}
		}
		return null;
	}
	
}
