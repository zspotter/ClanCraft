package zerothindex.clancraft.command;

import java.util.ArrayList;
import java.util.List;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.MessageReceiver;
import zerothindex.clancraft.clan.Clan;
import zerothindex.clancraft.clan.ClanPlayer;

public class CmdList extends CommandBase {

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
	public boolean handle(MessageReceiver sender, String[] args) {
		sender.message("<t>-- Clan List --");
		List<Clan> clans = new ArrayList<Clan>();
		if (ClanPlugin.getInstance().getClanManager().getClans() != null) {
			clans.addAll(ClanPlugin.getInstance().getClanManager().getClans());
		}
		if (clans.size() == 0){
			sender.message(" <m>There aren't any clans!");
			return true;
		}
		java.util.Collections.sort(clans); // sort by largest online
		Clan myClan = null;
		ClanPlayer player = ClanPlugin.getInstance().findClanPlayer(sender.getName());
		if (player != null) myClan = player.getClan();
		boolean anyPublic = false;
		for (Clan clan : clans) {
			if (!clan.isClosed()) anyPublic = true;
			String color = "";
			if (myClan != null && clan.isAlly(myClan)) {
				color = "<b>";
			} else if (myClan != null && clan.isEnemy(myClan)) {
				color = "<r>";
			} else if (myClan != null && clan.equals(myClan)) {
				color = "<g>";
			}
			sender.message(" "+color+clan.getName()+"<m>"+(!clan.isClosed()? "*" : "")
					+" - "+clan.getOnlineSize()+"/"+clan.getSize()+" online"
					+(clan.getPlot().isActive()? " - radius: "+clan.getPlot().getRadius() : " - no territory"));
		}
		if (anyPublic) {
			sender.message("<m>* - public clan");
		}
		return true;
	}

}
