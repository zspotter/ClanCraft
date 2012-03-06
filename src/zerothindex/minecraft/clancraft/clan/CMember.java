package zerothindex.minecraft.clancraft.clan;

/**
 * Represents a clan member. This is not a wrapper object for Bukkit's Player,
 * although the saved name will be used to identify connected objects.
 * 
 * @author zerothindex
 *
 */
public class CMember {

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
	private Clan clan;
	private int chatMode;
	private long lastLogin;
	private int role;
	
	/*
	 * Constructors
	 */
	
	/**
	 * Creates a default instance of a CMember
	 * @param name the name of the player this object represents
	 * @param clan the clan this CMember belongs to. Should not be null.
	 */
	public CMember(String name, Clan clan) {
		this(name, clan, ROLE_NORMAL, CHAT_PUBLIC, System.currentTimeMillis());
	}
	
	/**
	 * A complete constructor
	 * @param name the player's name (must match Bukkit Player's name exactly)
	 * @param clan should not ever be null
	 * @param role see CMember's public static final int variables for values
	 * @param chatMode see CMember's public static final int variables for values
	 * @param lastLogin the time in milliseconds (System.currentTimeMillis())
	 */
	public CMember(String name, Clan clan, int role, int chatMode, long lastLogin) {
		this.name = name;
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
	
	
}
