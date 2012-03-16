package zerothindex.clancraft.command;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.MessageReceiver;
import zerothindex.clancraft.WorldPlayer;
import zerothindex.clancraft.clan.ClanPlayer;

public class CmdSetspawn extends CommandBase {

	@Override
	public String getName() {
		return "setspawn";
	}

	@Override
	public String getDescription() {
		return "set the spawn for your clan to your position";
	}

	@Override
	public String getUsage() {
		return "/c setspawn";
	}

	@Override
	public boolean playerOnly() {
		return true;
	}

	@Override
	public boolean handle(MessageReceiver sender, String[] args) {
		ClanPlayer cp = ClanPlugin.getInstance().findClanPlayer(sender.getName());
		if (cp == null || cp.getClan() == null || !cp.getClan().getPlot().isActive()) {
			cp.message("<m>You need to belong to a clan with claimed land to set a spawn.");
			return true;
		}

		WorldPlayer player = (WorldPlayer)sender;
		String world = player.getWorld();
		double[] coords = player.getCoordinates();
		float[] dir = player.getOrientation();
		if (cp.getClan().getPlot().contains(world, coords[0], coords[2])) {
			cp.getClan().getPlot().setSpawn(world, coords[0], coords[1], coords[2], dir[0], dir[1]);
			sender.message("<t>Set your clan's spawn to your position.");
		} else {
			sender.message("<m>Your spawn must be inside your territory.");
		}
		return true;
	}

}
