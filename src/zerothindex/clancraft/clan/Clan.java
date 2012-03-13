package zerothindex.clancraft.clan;

import java.util.HashSet;
import java.util.Set;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.bukkit.BukkitWorldPlot;

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
	private HashSet<ClanPlayer> invites;
	private HashSet<ClanPlayer> online;
	
	private HashSet<Clan> allies;
	private HashSet<Clan> enemies;
	private HashSet<Clan> allyRequests;
	
	private boolean closed;
	
	private ClanPlot plot;
	
	public Clan(String name) {
		this(ClanPlugin.getInstance().getClanManager().nextID(), name, "", new HashSet<ClanPlayer>(), 
				new HashSet<ClanPlayer>(), new HashSet<Clan>(), new HashSet<Clan>(), true, null);
		
	}
	
	public Clan(int clanID, String name, String desc, HashSet<ClanPlayer> members, HashSet<ClanPlayer> online,
			HashSet<Clan> allies, HashSet<Clan> enemies, boolean closed, ClanPlot plot) {
		this.clanID = clanID;
		this.name = name;
		this.description = desc;
		this.members = members;
		this.invites = new HashSet<ClanPlayer>();
		this.allies = allies;
		this.allyRequests = new HashSet<Clan>();
		this.enemies = enemies;
		this.online = new HashSet<ClanPlayer>();
		this.closed = closed;
		if (plot == null) this.plot = new BukkitWorldPlot(this);
		else this.plot = plot;
	}
	
	public void addMember(ClanPlayer newb) {
		newb.setClan(this);
		members.add(newb);
		invites.remove(newb);
		if (newb.isOnline()) online.add(newb);
		newb.message("You have joined "+getName()+".");
		plot.recalculate();
	}
	
	public void kickMember(ClanPlayer member) {
		if (member.getClan().equals(this)) {
			member.setClan(null);
			member.setRole(ClanPlayer.ROLE_NORMAL);
			members.remove(member);
			online.remove(member);
			member.message("You have left "+getName()+".");
			plot.recalculate();
		}
	}
	
	public void disband() {
		for (ClanPlayer cp : members) {
			cp.setClan(null);
			cp.setRole(ClanPlayer.ROLE_NORMAL);
			cp.message("Your faction has been disbanded.");
		}
		members = null;
		invites = null;
		online = null;
		allies = null;
		enemies = null;
		closed = true;
		plot.unclaim();
	}

	public boolean isInvited(ClanPlayer player) {
		return invites.contains(player);
	}

	public void messageClan(String msg) {
		for (ClanPlayer p : online) {
			p.message(msg);
		}
	}
	
	public void messageAllies(String msg) {
		for (ClanPlayer p : online) {
			p.message(msg);
		}
		for (Clan a : allies) {
			a.messageClan(msg);
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
		return online.size();
	}
	
	public Set<ClanPlayer> getOnline() {
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
		online.add(member);
	}
	public void checkOut(ClanPlayer member) {
		online.remove(member);
	}
	
	public void addAlly(Clan ally) {
		allyRequests.remove(ally);
		allies.add(ally);
	}
	public void removeAlly(Clan ally) {
		allies.remove(ally);
	}
	public boolean isAlly(Clan ally) {
		return allies.contains(ally);
	}
	
	public void addEnemy(Clan enemy) {
		allyRequests.remove(enemy);
		enemies.add(enemy);
	}
	public void removeEnemy(Clan enemy) {
		enemies.remove(enemy);
	}
	public boolean isEnemy(Clan enemy) {
		return enemies.contains(enemy);
	}
	
	public void addInvite(ClanPlayer player) {
		invites.add(player);
	}
	public void removeInvite(ClanPlayer player) {
		invites.remove(player);
	}
	
	public boolean isClosed() {
		return closed;
	}
	public void setClosed(boolean cls) {
		closed = cls;
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
		return allyRequests.contains(clan);
	}

	public void requestAlly(Clan ally) {
		allyRequests.add(ally);
	}

	/**
	 * @return the integer ID of this clan
	 */
	public int getClanID() {
		return clanID;
	}
	
}
