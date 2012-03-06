package zerothindex.clancraft.clan;

import zerothindex.clancraft.Messageable;

/**
 * Represents a clan member. It will try not to be a wrapper for Bukkit's
 * Player object.
 * 
 * @author zerothindex
 *
 */
public class ClanPlayer implements Messageable {

	/*
	 * Static variables
	 */
	// Define various chat modes
	public static final int CHAT_PUBLIC = 0;
	public static final int CHAT_CLAN = 1;
	public static final int CHAT_ALLY = 2;
	//Define various roles within a Clan
	public static final int ROLE_NORMAL = 0;
	public static final int ROLE_LEADER = 1;
	
	/*
	 * Private variables
	 */
	private String name;
	private Messageable messageable;
	private Clan clan;
	private int chatMode;
	private long lastLogin;
	private int role;
	private boolean isOnline;
	
	/*
	 * Constructors
	 */
	
	/**
	 * Creates a default instance of a ClanPlayer
	 * @param name the name of the player this object represents
	 */
	public ClanPlayer(Messageable object) {
		this(object, null, ROLE_NORMAL, CHAT_PUBLIC, System.currentTimeMillis());
	}
	
	/**
	 * A complete constructor
	 * @param name the player's name (must match Bukkit Player's name exactly)
	 * @param clan
	 * @param role see ClanPlayer's public static final int variables for values
	 * @param chatMode see ClanPlayer's public static final int variables for values
	 * @param lastLogin the time in milliseconds (System.currentTimeMillis())
	 */
	public ClanPlayer(Messageable object, Clan clan, int role, int chatMode, long lastLogin) {
		this.name = object.getName();
		this.messageable = object;
		this.clan = clan;
		this.chatMode = chatMode;
		this.lastLogin = lastLogin;
		this.role = role;
	}
	
	/*
	 * Methods and functions
	 */
	
	/**
	 * Set the last login time to the current time
	 */
	public void updateLoginTime() {
		lastLogin = System.currentTimeMillis();
	}
	
	/*
	 * Getters and setters
	 */
	
	public String getName() {
		return name;
	}
	
	public Clan getClan() {
		return clan;
	}
	public void setClan(Clan c) {
		clan = c;
	}
	
	public int getRole() {
		return role;
	}
	public void setRole(int r) {
		role = r;
	}
	
	public int getChatMode() {
		return chatMode;
	}
	public void setChatMode(int cmode){
		chatMode = cmode;
	}
	
	public long getLastLogin() {
		return lastLogin;
	}
	
	public boolean isOnline() {
		return isOnline;
	}
	public void logIn() {
		isOnline = true;
		clan.checkIn(this);
	}
	public void logOut() {
		isOnline = false;
		clan.checkOut(this);
	}

	@Override
	public void message(String msg) {
		messageable.message(msg);
		
	}

	@Override
	public Object getObject() {
		return messageable;
	}

	@Override
	public boolean isPlayer() {
		return true;
	}
	
	
}
