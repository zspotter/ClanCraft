package zerothindex.clancraft.bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import zerothindex.clancraft.ClanPlugin;
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
		for (int i = 0; i < players.size(); i++) {
			members[i] = new SaveStatePlayer(players.get(i));
		}
		
		allies = new Integer[0];
		enemies = new Integer[0];
		allyRequests = new Integer[0];
		invites = new String[0];
		
		allies = clan.getAllies().toArray(allies);
		enemies = clan.getEnemies().toArray(enemies);
		allyRequests = clan.getAllyRequests().toArray(allyRequests);
		invites = clan.getInvites().toArray(invites);
		
		if (clan.getPlot() != null && clan.getPlot().isActive()) {
			world = clan.getPlot().getWorld();
			center = new int[] {clan.getPlot().getX(), clan.getPlot().getZ()};
			if (clan.getPlot().getSpawn() != null) {
				spawn = clan.getPlot().getSpawn();
				spawnDir = clan.getPlot().getSpawnDir();
			}
		}
	}
	
	/**
	 * @return a restored version of what this state represents
	 */
	public Clan toClan() {
		Clan clan = new Clan(clanID, name, description, new HashSet<ClanPlayer>(), 
				new HashSet<ClanPlayer>(), new HashSet<Integer>(), new HashSet<Integer>(), closed, null);
		for (SaveStatePlayer playerState : members) {
			ClanPlayer cp = playerState.toClanPlayer();
			ClanPlugin.getInstance().addClanPlayer(cp);
			clan.addMember(cp);
		}
		for (Integer id : allies) {
			clan.addAlly(id);
		}
		for (Integer id : enemies) {
			clan.addEnemy(id);
		}
		for (Integer id : allyRequests) {
			clan.requestAlly(id);
		}
		for (String str : invites) {
			clan.addInvite(str);
		}
		
		if (world != null) {
			BukkitWorldPlot plot = new BukkitWorldPlot(clan);
			plot.setCenter(world, center[0], center[1]);
			if (spawn != null) {
				plot.setSpawn(world, spawn[0], spawn[1], spawn[2], spawnDir[0], spawnDir[1]);
			}
			clan.setPlot(plot);
		}
		
		return clan;
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
		ClanPlayer player = new ClanPlayer(name, null, -1, rank, chatMode, lastLogin, false);
		return player;
	}
}
