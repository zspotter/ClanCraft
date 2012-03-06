package zerothindex.minecraft.clancraft.clan;

import java.util.HashSet;

import zerothindex.minecraft.clancraft.Messageable;

/**
 * A Clan has a collection of CMembers and a ClanPlot.
 * 
 * @author zerothindex
 *
 */
public class Clan {

	private HashSet<ClanPlayer> members;
	private HashSet<ClanPlayer> invites;
	private HashSet<ClanPlayer> online;
	
	private HashSet<Clan> allies;
	private HashSet<Clan> enemies;
	
	private ClanPlot plot;
	
	public Clan() {
		members = new HashSet<ClanPlayer>();
		invites = new HashSet<ClanPlayer>();
		allies = new HashSet<Clan>();
		enemies = new HashSet<Clan>();
		plot = null;
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
		}
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
	
	public boolean isInvited(ClanPlayer player) {
		return invites.contains(player);
	}

	public void messagePlayers(String msg) {
		for (ClanPlayer p : online) {
			p.message(msg);
		}
		
	}
	
}
