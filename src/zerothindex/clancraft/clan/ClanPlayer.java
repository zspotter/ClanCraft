package zerothindex.clancraft.clan;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.WorldPlayer;

/**
 * Represents a clan member. It will try not to be a wrapper for Bukkit's
 * Player object.
 * 
 * @author zerothindex
 *
 */
public class ClanPlayer implements WorldPlayer {

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
	private WorldPlayer player;
	private int clanID;
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
	public ClanPlayer(WorldPlayer object) {
		this(object, -1, ROLE_NORMAL, CHAT_PUBLIC, System.currentTimeMillis(), false);
	}
	
	/**
	 * A complete constructor
	 * @param name the player's name (must match Bukkit Player's name exactly)
	 * @param clan
	 * @param role see ClanPlayer's public static final int variables for values
	 * @param chatMode see ClanPlayer's public static final int variables for values
	 * @param lastLogin the time in milliseconds (System.currentTimeMillis())
	 */
	public ClanPlayer(WorldPlayer object, int clanID, int role, int chatMode, long lastLogin, boolean isOnline) {
		this.name = object.getName();
		this.player = object;
		this.clanID = clanID;
		this.chatMode = chatMode;
		this.lastLogin = lastLogin;
		this.role = role;
		this.isOnline = isOnline;
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
		return ClanPlugin.getInstance().getClanManager().getClan(clanID);
	}
	public void setClan(Clan c) {
		if (c == null) clanID = -1;
		else clanID = c.getClanID();
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
		if (clanID != -1) getClan().checkIn(this);
	}
	public void logOut() {
		isOnline = false;
		if (clanID != -1) getClan().checkOut(this);
	}

	@Override
	public void message(String msg) {
		player.message(msg);
		
	}

	@Override
	public Object getObject() {
		return player;
	}

	@Override
	public boolean isPlayer() {
		return true;
	}

	@Override
	public String getWorld() {
		return player.getWorld();
	}

	@Override
	public double[] getCoordinates() {
		return player.getCoordinates();
	}

	@Override
	public float[] getOrientation() {
		return player.getOrientation();
	}
	
	
}
