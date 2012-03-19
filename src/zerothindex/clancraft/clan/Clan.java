package zerothindex.clancraft.clan;

import java.util.HashSet;
import java.util.Set;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.bukkit.BukkitWorldPlot;
import zerothindex.clancraft.bukkit.SaveStateClan;

/**
 * A Clan has a collection of ClanPlayers and a ClanPlot.
 * 
 * @author zerothindex
 *
 */
public class Clan implements Comparable<Clan>{
	
	private int clanID;
	
	private String name;
	private String description;

	private HashSet<ClanPlayer> members;
	private HashSet<String> invites;
	
	private HashSet<Integer> allies;
	private HashSet<Integer> enemies;
	private HashSet<Integer> allyRequests;
	
	private boolean closed;
	
	transient private ClanPlot plot; // transient - do not serialize
	
	public Clan(String name) {
		this(ClanPlugin.getInstance().getClanManager().nextID(), name, "", new HashSet<ClanPlayer>(), 
				new HashSet<ClanPlayer>(), new HashSet<Integer>(), new HashSet<Integer>(), true, null);
		
	}
	
	public Clan(int clanID, String name, String desc, HashSet<ClanPlayer> members, HashSet<ClanPlayer> online,
			HashSet<Integer> allies, HashSet<Integer> enemies, boolean closed, ClanPlot plot) {
		this.clanID = clanID;
		this.name = name;
		this.description = desc;
		this.members = members;
		this.invites = new HashSet<String>();
		this.allies = allies;
		this.allyRequests = new HashSet<Integer>();
		this.enemies = enemies;
		this.closed = closed;
		if (plot == null) this.plot = new BukkitWorldPlot(this);
		else this.plot = plot;
	}
	
	public void addMember(ClanPlayer newb) {
		if (newb.getClan() != null) {
			newb.getClan().kickMember(newb);
		}
		newb.setClan(this);
		members.add(newb);
		invites.remove(newb.getName());
		newb.message("<m>You have joined "+getName()+".");
		if (plot.isActive()) {
			plot.recalculate();
		}
	}
	
	public void kickMember(ClanPlayer member) {
		if (member.getClan().equals(this)) {
			member.setClan(null);
			member.setRole(ClanPlayer.ROLE_NORMAL);
			members.remove(member);
			member.message("<m>You have left "+getName()+".");
			plot.recalculate();
		}
	}
	
	public void disband() {
		ClanPlugin.getInstance().getClanManager().removeClan(this);
		for (ClanPlayer cp : members) {
			cp.setClan(null);
			cp.setRole(ClanPlayer.ROLE_NORMAL);
			cp.message("<t>Your clan has been disbanded.");
		}
		for (Integer id : allies) {
			ClanPlugin.getInstance().getClanManager().getClan(id).removeAlly(this);
		}
		for (Integer id : enemies) {
			ClanPlugin.getInstance().getClanManager().getClan(id).removeEnemy(this);
		}
		members.clear();
		invites.clear();
		allies.clear();
		enemies.clear();
		closed = true;
		plot.unclaim();
	}

	public boolean isInvited(ClanPlayer player) {
		return invites.contains(player.getName().toLowerCase());
	}

	public void messageClan(String msg) {
		for (ClanPlayer p : members) {
			if (p.isOnline()) p.message(msg);
		}
	}
	
	public void messageAllies(String msg) {
		for (Integer id : allies) {
			ClanPlugin.getInstance().getClanManager().getClan(id).messageClan(msg);
		}
	}
	
	/**
	 * Finds an appropriate color tag to represent the player's relationship to the clan
	 * @param cp 
	 * @return a color tag
	 */
	public String getRelationTag(ClanPlayer cp) {
		if (cp.getClan() == null) return "<n>";
		if (cp.getClan().isEnemy(this)) return "<r>";
		if (cp.getClan().isAlly(this)) return "<b>";
		if (cp.getClan().equals(this)) return "<g>";
		return "<n>";
	}
	
	public int getSize() {
		return members.size();
	}
	public int getOnlineSize() {
		return getOnline().size();
	}
	
	public Set<ClanPlayer> getOnline() {
		HashSet<ClanPlayer> online = new HashSet<ClanPlayer>();
		for (ClanPlayer p : members) {
			if (p.isOnline()) online.add(p);
		}
		return online;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String str) {
		name = str;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String str) {
		description = str;
	}
	
	public void checkIn(ClanPlayer member) {
		//online.add(member);
	}
	public void checkOut(ClanPlayer member) {
		//online.remove(member);
	}
	
	public void addAlly(Integer id) {
		allyRequests.remove(id);
		allies.add(id);
	}
	public void removeAlly(Clan ally) {
		allies.remove(ally);
	}
	public boolean isAlly(Clan ally) {
		return allies.contains(ally.getClanID());
	}
	
	public void addEnemy(Integer id) {
		allyRequests.remove(id);
		enemies.add(id);
	}
	public void removeEnemy(Clan enemy) {
		enemies.remove(enemy.getClanID());
	}
	public boolean isEnemy(Clan enemy) {
		return enemies.contains(enemy.getClanID());
	}
	
	public void addInvite(String player) {
		invites.add(player.toLowerCase());
	}
	public void removeInvite(String player) {
		invites.remove(player.toLowerCase());
	}
	
	public boolean isClosed() {
		return closed;
	}
	public void setClosed(boolean cls) {
		closed = cls;
	}
	
	public HashSet<String> getInvites() {
		return invites;
	}
	
	public Set<Integer> getAllies() {
		return allies;
	}
	public Set<Integer> getEnemies() {
		return enemies;
	}
	public Set<Integer> getAllyRequests() {
		return allyRequests;
	}
	public Set<ClanPlayer> getMembers() {
		return members;
	}
	
	public ClanPlot getPlot() {
		return plot;
	}
	public void setPlot(ClanPlot p) {
		if (plot != null) plot.unclaim();
		plot = p;
	}

	@Override
	/**
	 * Sort by largest online clan
	 */
	public int compareTo(Clan c2) {
		if (this.getOnlineSize() > c2.getOnlineSize())
			return -1;
		if (this.getOnlineSize() < c2.getOnlineSize())
			return 1;
		if (this.getSize() > c2.getSize())
			return -1;
		if (this.getSize() < c2.getSize())
			return 1;
		
		return 0;
	}

	public boolean hasRequestedAlly(Clan clan) {
		return allyRequests.contains(new Integer(clan.getClanID()));
	}

	public void requestAlly(Integer id) {
		allyRequests.add(id);
	}

	/**
	 * @return the integer ID of this clan
	 */
	public int getClanID() {
		return clanID;
	}
	
}
