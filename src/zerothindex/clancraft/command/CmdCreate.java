package zerothindex.clancraft.command;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.MessageReceiver;
import zerothindex.clancraft.PluginSettings;
import zerothindex.clancraft.WorldPlayer;
import zerothindex.clancraft.clan.Clan;
import zerothindex.clancraft.clan.ClanPlayer;

public class CmdCreate extends CommandBase {

	@Override
	public String getName() {
		return "create";
	}

	@Override
	public String getDescription() {
		return "creates a new clan";
	}

	@Override
	public String getUsage() {
		return "/c create <name>";
	}

	@Override
	public boolean playerOnly() {
		return false;
	}

	@Override
	public boolean handle(MessageReceiver sender, String[] args) {
		if (args.length != 2) {
			return false;
		}
		ClanPlayer cp = null;
		if (sender instanceof WorldPlayer) {
			cp = ClanPlugin.getInstance().getClanPlayer((WorldPlayer)sender);
		}
		if (cp != null && cp.getClan() != null) {
			sender.message("&xYou are already part of a clan.");
			return true;
		}
		if (args[1].length() > PluginSettings.maximumClanNameLength) {
			sender.message("&xMaximum clan name length is "
					+PluginSettings.maximumClanNameLength+" characters.");
			return true;
		}
		sender.message("&b&mCreated the clan \""+args[1]+"\".");
		Clan c = new Clan(args[1]);
		sender.message("&mChange the defualt description with the \"c desc\" command.");
		
		if (sender.isPlayer()) {
			c.addMember(cp);
			cp.setRole(ClanPlayer.ROLE_LEADER);
		}
		
		ClanPlugin.getInstance().getClanManager().addClan(c);
		ClanPlugin.getInstance().log("New clan \""+c.getName()+"\" created by "+sender.getName());
		return true;
		
	}

}
