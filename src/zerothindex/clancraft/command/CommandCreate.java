package zerothindex.clancraft.command;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.Messageable;
import zerothindex.clancraft.clan.Clan;
import zerothindex.clancraft.clan.ClanPlayer;

public class CommandCreate extends CommandBase {

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
	public boolean adminOnly() {
		return false;
	}

	@Override
	public boolean handle(Messageable sender, String[] args) {
		if (args.length != 2) {
			return false;
		}
		sender.message("Created the clan \""+args[1]+"\".");
		Clan c = new Clan();
		c.setName(args[1]);
		c.setDescription("Change the defualt description with the \"c desc\" command.");
		
		if (sender.isPlayer()) {
			ClanPlayer cp = ClanPlugin.getInstance().getClanPlayer(sender.getName());
			if (cp == null) {
				cp = new ClanPlayer(sender);
				ClanPlugin.getInstance().addClanPlayer(cp);
			}
			c.addMember(cp);
			cp.setRole(ClanPlayer.ROLE_LEADER);
		}
		
		ClanPlugin.getInstance().getClanManager().addClan(c);
		
		return true;
		
	}

}
