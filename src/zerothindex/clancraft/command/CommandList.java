package zerothindex.clancraft.command;

import java.util.ArrayList;
import java.util.List;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.Messageable;
import zerothindex.clancraft.bukkit.BukkitClanPlugin;
import zerothindex.clancraft.clan.Clan;
import zerothindex.clancraft.clan.ClanComparator;

public class CommandList extends CommandBase {

	@Override
	public String getName() {
		return "list";
	}

	@Override
	public String getDescription() {
		return "list all clans";
	}

	@Override
	public String getUsage() {
		return "/c list";
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
		sender.message("-Clan List-");
		List<Clan> clans = new ArrayList<Clan>();
		if (ClanPlugin.getInstance().getClanManager().getClans() != null) {
			clans.addAll(ClanPlugin.getInstance().getClanManager().getClans());
		}
		if (clans.size() == 0){
			sender.message(" There aren't any clans!");
			return true;
		}
		java.util.Collections.sort(clans, new ClanComparator()); // sort by largest online
		for (Clan clan : clans) {
			sender.message(" "+clan.getName()+" - "+clan.getOnlineSize()+"/"+clan.getSize()+" online");
		}
		return true;
	}

}
