package zerothindex.clancraft.command;

import java.util.ArrayList;
import java.util.List;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.MessageReceiver;
import zerothindex.clancraft.WorldPlayer;
import zerothindex.clancraft.clan.Clan;
import zerothindex.clancraft.clan.ClanPlayer;

public class CmdList extends CommandBase {

	@Override
	public String getName() {
		return "list";
	}

	@Override
	public String getDescription() {
		return "list all clans or view a specific clan's stats";
	}

	@Override
	public String getUsage() {
		return "/c list [clan]";
	}

	@Override
	public boolean playerOnly() {
		return false;
	}

	@Override
	public boolean handle(MessageReceiver sender, String[] args) {
		// list specific clan stats
		if (args.length == 2) {
			Clan clan = ClanPlugin.getInstance().getClanManager().findClan(args[1]);
			if (clan == null) {
				sender.message("<m>Clan \""+args[1]+"\" not found.");
				return true;
			}
			String color = "";
			if (sender instanceof WorldPlayer) {
				ClanPlayer cp = ClanPlugin.getInstance().getClanPlayer((WorldPlayer)sender);
				clan.getRelationTag(cp);
			}
			sender.message("<t>--- Clan: "+color+clan.getName()+" <t>---");
			sender.message("<t>'"+clan.getDescription()+"'");
			sender.message("  <t>Online: "+clan.getOnlineSize()+"/"+clan.getSize());
			if (clan.isClosed()) {
				sender.message("  <t>Invite only.");
			} else {
				sender.message("  <t>Public clan.");
			}
			String players = "";
			for (ClanPlayer member : clan.getMembers()) {
				players += ", "+member.getName();
				if (member.getRole() == ClanPlayer.ROLE_LEADER) {
					players += "*";
				}
			}
			players = players.substring(2);
			sender.message("  <t>Members: "+color+players);
			sender.message("  <m>* - clan leader");
			return true;
		}
		
		// list all clans
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
