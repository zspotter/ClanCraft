package zerothindex.clancraft.command;

import zerothindex.clancraft.ClanPlugin;
import zerothindex.clancraft.MessageReceiver;
import zerothindex.clancraft.clan.ClanPlayer;

public class CommandChatMode extends CommandBase {

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
		return "/c chat <public|clan|ally>";
	}

	@Override
	public boolean playerOnly() {
		return true;
	}

	@Override
	public boolean adminOnly() {
		return false;
	}

	@Override
	public boolean handle(MessageReceiver sender, String[] args) {
		if (args.length != 2) return false;
		ClanPlayer cp = ClanPlugin.getInstance().getClanPlayer(sender);
		if (args[1].startsWith("p")) {
			cp.setChatMode(ClanPlayer.CHAT_PUBLIC);
			cp.message("Chat mode: public");
		} else if (cp.getClan() == null) {
			cp.message("You don't have a clan to chat with!");
			cp.setChatMode(ClanPlayer.CHAT_PUBLIC);
		} else if (args[1].startsWith("c")) {
			cp.setChatMode(ClanPlayer.CHAT_CLAN);
			cp.message("Chat mode: clan");
		} else if (args[1].startsWith("a")) {
			cp.setChatMode(ClanPlayer.CHAT_ALLY);
			cp.message("Chat mode: ally");
		} else {
			return false;
		}
		
		return true;
	}

}
