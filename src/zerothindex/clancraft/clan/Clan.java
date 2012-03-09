package zerothindex.clancraft.clan;

import java.util.HashSet;
import java.util.Set;

import zerothindex.clancraft.bukkit.BukkitWorldPlot;

/**
 * A Clan has a collection of ClanPlayers and a ClanPlot.
 * 
 * @author zerothindex
 *
 */
public class Clan implements Comparable<Clan>{
	
	private String name;
	private String description;

	private HashSet<ClanPlayer> members;
	private HashSet<ClanPlayer> invites;
	private HashSet<ClanPlayer> online;
	
	private HashSet<Clan> allies;
	private HashSet<Clan> enemies;
	
	private boolean closed;
	
	private ClanPlot plot;
	
	public Clan(String name) {
		this(name, "Default description.", new HashSet<ClanPlayer>(), new HashSet<ClanPlayer>(), 
				new HashSet<ClanPlayer>(), new HashSet<Clan>(), new HashSet<Clan>(), true, null);
		
	}
	
	public Clan(String name, String desc, HashSet<ClanPlayer> members, HashSet<ClanPlayer> invites, HashSet<ClanPlayer> online,
			HashSet<Clan> allies, HashSet<Clan> enemies, boolean closed, ClanPlot plot) {
		this.name = name;
		this.description = desc;
		this.members = members;
		this.invites = invites;
		this.allies = allies;
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
		allies.add(ally);
	}
	public void removeAlly(Clan ally) {
		allies.remove(ally);
	}
	public boolean isAlly(Clan ally) {
		return allies.contains(ally);
	}
	
	public void addEnemy(Clan enemy) {
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
			return 1;
		if (this.getOnlineSize() < c2.getOnlineSize())
			return -1;
		if (this.getSize() > c2.getSize())
			return 1;
		if (this.getSize() < c2.getSize())
			return -1;
		
		return 0;
	}
	
}
