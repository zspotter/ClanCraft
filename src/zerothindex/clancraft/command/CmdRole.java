package zerothindex.clancraft.command;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.MessageReceiver;
import zerothindex.clancraft.WorldPlayer;
import zerothindex.clancraft.clan.ClanPlayer;

public class CmdRole extends CommandBase {

	@Override
	public String getName() {
		return "role";
	}

	@Override
	public String getDescription() {
		return "promote or demote a clan member";
	}

	@Override
	public String getUsage() {
		return "/c role <player> <leader|normal>";
	}

	@Override
	public boolean playerOnly() {
		return false;
	}

	@Override
	public boolean handle(MessageReceiver sender, String[] args) {
		if (args.length != 3) return false;
		if (!args[2].startsWith("l") && !args[2].startsWith("n")) return false;
		if (sender instanceof WorldPlayer) {
			ClanPlayer cp = ClanPlugin.getInstance().getClanPlayer((WorldPlayer) sender);
			if (cp.getRole() != ClanPlayer.ROLE_LEADER) {
				cp.message("You must be a clan leader to do that.");
				return true;
			} else {
				ClanPlayer subject = ClanPlugin.getInstance().findClanPlayer(args[1]);
				if (subject == null) {
					cp.message("Player \""+args[1]+"\" not found.");
					return true;
				} else {
					if (cp.getClan() != null && subject.getClan() != null 
							&& cp.getClan().equals(subject.getClan())) {
						subject.setRole( (args[2].startsWith("l")? 
								ClanPlayer.ROLE_LEADER : ClanPlayer.ROLE_NORMAL));
						cp.message("You set "+subject.getName()+"'s role to <t>"+(args[2].startsWith("l")? 
								"leader" : "normal member")+"<m>.");
						subject.message("Your role was set to <t>"+(args[2].startsWith("l")? 
								"leader" : "normal member")+"<m> by "+cp.getName()+".");
						return true;
					} else {
						cp.message("You aren't part of the same clan as "+subject.getName()+".");
						return true;
					}
				}
			}
		} else {
			// assume console command
			ClanPlayer subject = ClanPlugin.getInstance().findClanPlayer(args[1]);
			if (subject == null) {
				sender.message("Player \""+args[1]+"\" not found.");
				return true;
			} else {
				if (subject.getClan() != null) {
					subject.setRole( (args[2].startsWith("l")? 
							ClanPlayer.ROLE_LEADER : ClanPlayer.ROLE_NORMAL));
					sender.message("You set "+subject.getName()+"'s role to <t>"+(args[2].startsWith("l")? 
							"leader" : "normal member")+"<m>.");
					subject.message("Your role was set to <t>"+(args[2].startsWith("l")? 
							"leader" : "normal member")+"<m> by "+sender.getName()+".");
					return true;
				} else {
					sender.message(subject.getName()+" doesn't belong to a clan.");
					return true;
				}
			}
		}
	}

}
