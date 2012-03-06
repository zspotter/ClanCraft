package zerothindex.clancraft.clan;

import java.util.HashSet;

/**
 * A Clan has a collection of CMembers and a ClanPlot.
 * 
 * @author zerothindex
 *
 */
public class Clan {
	
	private String name;
	private String description;

	private HashSet<ClanPlayer> members;
	private HashSet<ClanPlayer> invites;
	private HashSet<ClanPlayer> online;
	
	private HashSet<Clan> allies;
	private HashSet<Clan> enemies;
	
	private boolean closed;
	
	private ClanPlot plot;
	
	public Clan() {
		this("New Clan", "Default description.", new HashSet<ClanPlayer>(), new HashSet<ClanPlayer>(), 
				new HashSet<ClanPlayer>(), new HashSet<Clan>(), new HashSet<Clan>(), true, new ClanPlot());
		
	}
	
	public Clan(String name, String desc, HashSet<ClanPlayer> members, HashSet<ClanPlayer> invites, HashSet<ClanPlayer> online,
			HashSet<Clan> allies, HashSet<Clan> enemies, boolean closed, ClanPlot plot) {
		this.name = name;
		this.description = desc;
		this.members = members;
		this.invites = invites;
		this.allies = allies;
		this.enemies = enemies;
		this.closed = closed;
		this.plot = plot;
	}
	
	public void addMember(ClanPlayer newb) {
		newb.setClan(this);
		members.add(newb);
		invites.remove(newb);
	}
	
	public void kickMember(ClanPlayer member) {
		if (member.getClan().equals(this)) {
			member.setClan(null);
			members.remove(member);
			online.remove(member);
		}
	}
	
	public void disband() {
		for (ClanPlayer cp : members) {
			cp.setClan(null);
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

	public void messagePlayers(String msg) {
		for (ClanPlayer p : online) {
			p.message(msg);
		}
	}
	
	public int getSize() {
		return members.size();
	}
	public int getOnlineSize() {
		return online.size();
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
	
	public void addEnemy(Clan enemy) {
		allies.add(enemy);
	}
	public void removeEnemy(Clan enemy) {
		allies.remove(enemy);
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
	
}