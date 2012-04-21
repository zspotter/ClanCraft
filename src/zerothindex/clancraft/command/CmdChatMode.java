package zerothindex.clancraft.command;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.MessageReceiver;
import zerothindex.clancraft.WorldPlayer;
import zerothindex.clancraft.clan.ClanPlayer;

public class CmdChatMode extends CommandBase {

	@Override
	public String getName() {
		return "chat";
	}

	@Override
	public String getDescription() {
		return "changes your chat mode";
	}

	@Override
	public String getUsage() {
		return "/c chat OR /c chat <public|clan|ally>";
	}

	@Override
	public boolean playerOnly() {
		return true;
	}

	@Override
	public boolean handle(MessageReceiver sender, String[] args) {
		if (args.length > 2) return false;
		ClanPlayer cp = ClanPlugin.getInstance().getClanPlayer((WorldPlayer)sender);
		String mode;
		if (args.length == 2) {
			mode = args[1];
		} else {
			if (cp.getClan() != null) {
				if (cp.getChatMode() == ClanPlayer.CHAT_ALLY)
					mode = "p";
				else if (cp.getChatMode() == ClanPlayer.CHAT_CLAN) 
					mode = "a";
				else mode = "c";
			} else {
				return true;
			}
		}
	
		if (mode.startsWith("p")) {
			cp.setChatMode(ClanPlayer.CHAT_PUBLIC);
			cp.message("&mChat mode: &nPUBLIC.");
		} else if (cp.getClan() == null) {
			cp.message("&xYou don't have a clan to chat with!");
			cp.setChatMode(ClanPlayer.CHAT_PUBLIC);
		} else if (mode.startsWith("c")) {
			cp.setChatMode(ClanPlayer.CHAT_CLAN);
			cp.message("&mChat mode: &fCLAN");
		} else if (mode.startsWith("a")) {
			cp.setChatMode(ClanPlayer.CHAT_ALLY);
			cp.message("&mChat mode: &aALLY");
		} else {
			return false;
		}
		
		return true;
	}

}
