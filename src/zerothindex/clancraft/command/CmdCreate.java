package zerothindex.clancraft.command;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.MessageReceiver;
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
		sender.message("<t>Created the clan \""+args[1]+"\".");
		Clan c = new Clan(args[1]);
		//sender.message("<t>Change the defualt description with the \"c desc\" command.");
		
		if (sender.isPlayer()) {
			ClanPlayer cp = ClanPlugin.getInstance().getClanPlayer((WorldPlayer)sender);
			c.addMember(cp);
			cp.setRole(ClanPlayer.ROLE_LEADER);
		}
		
		ClanPlugin.getInstance().getClanManager().addClan(c);
		ClanPlugin.getInstance().log("New clan \""+c.getName()+"\" created by "+sender.getName());
		return true;
		
	}

}
