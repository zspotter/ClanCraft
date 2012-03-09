package zerothindex.clancraft.bukkit;

import org.bukkit.entity.Player;

import zerothindex.clancraft.WorldPlayer;

/**
 * A wrapper for CommandSenders
 * @author zerothindex
 *
 */
public class BukkitPlayer implements WorldPlayer {
	
	private Player player;
	
	public BukkitPlayer(Player player) {
		this.player = player;
	}

	@Override
	public void message(String msg) {
		player.sendMessage(BukkitClanPlugin.parseMessage(msg));
	}

	@Override
	public Object getObject() {
		return player;
	}

	@Override
	public String getName() {
		return player.getName();
	}

	@Override
	public boolean isPlayer() {
		return true;
	}

	@Override
	public String getWorld() {
		return player.getWorld().getName();
	}

	@Override
	public double[] getCoordinates() {
		return new double[]{
				player.getLocation().getX(),
				player.getLocation().getY(),
				player.getLocation().getZ()};
	}

	@Override
	public float[] getOrientation() {
		return new float[] {
				player.getLocation().getYaw(),
				player.getLocation().getPitch()};
	}
}
	

