package zerothindex.clancraft.bukkit;

import java.util.ArrayList;

import zerothindex.clancraft.clan.Clan;
import zerothindex.clancraft.clan.ClanPlayer;


/**
 * A state class used as an adapter for gson.
 * Each SaveStateClan object will represent an entire clan, its members, and its plot.
 * @author zerothindex
 *
 */
public class SaveStateClan {

	/* general clan info */
	public int clanID;
	public String name;
	public String description;
	public boolean closed;
	
	/* clan members and relashions */	
	public SaveStatePlayer[] members;
	public String[] invites;
	
	public Integer[] allies;
	public Integer[] enemies;
	public Integer[] allyRequests;
	
	/* clan plots */
	public String world;
	public int[] center;// [x,z]
	public double[] spawn; // [x,y,z]
	public float[] spawnDir;//[yaw, pitch]
	
	public SaveStateClan(Clan clan) {
		clanID = clan.getClanID();
		name = clan.getName();
		description = clan.getDescription();
		closed = clan.isClosed();
		
		members = new SaveStatePlayer[clan.getSize()];
		ArrayList<ClanPlayer> players = new ArrayList<ClanPlayer>(clan.getMembers());
		for (int i = 0; i < members.length; i++) {
			members[i] = new SaveStatePlayer(players.get(i));
		}
		
		allies = new Integer[0];
		enemies = new Integer[0];
		allyRequests = new Integer[0];
		
		allies = clan.getAllies().toArray(allies);
		enemies = clan.getEnemies().toArray(enemies);
		allyRequests = clan.getAllyRequests().toArray(allyRequests);
		
		if (clan.getPlot() != null && clan.getPlot().isActive()) {
			world = clan.getPlot().getWorld();
			center = new int[] {clan.getPlot().getX(), clan.getPlot().getZ()};
			if (clan.getPlot().getSpawn() != null) {
				spawn = clan.getPlot().getSpawn();
				spawnDir = clan.getPlot().getSpawnDir();
			}
		}
	}
	
	public Clan toClan() {
		return null;
	}
	
}

class SaveStatePlayer {

	public String name;
	public int chatMode;
	public long lastLogin;
	public int rank;
	
	public SaveStatePlayer(ClanPlayer player) {
		name = player.getName();
		chatMode = player.getChatMode();
		lastLogin = player.getLastLogin();
		rank = player.getRole();
	}
	
	public ClanPlayer toClanPlayer() {
		return null;
	}
}
