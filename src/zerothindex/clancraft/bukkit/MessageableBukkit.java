package zerothindex.clancraft.bukkit;

import org.bukkit.command.CommandSender;

import zerothindex.clancraft.Messageable;

/**
 * A wrapper for CommandSenders
 * @author zerothindex
 *
 */
public class MessageableBukkit implements Messageable {
	
	private CommandSender sender;
	
	public MessageableBukkit(CommandSender sender) {
		this.sender = sender;
	}

	@Override
	public void message(String msg) {
		sender.sendMessage(msg);
	}

	@Override
	public Object getObject() {
		return sender;
	}

	@Override
	public String getName() {
		return sender.getName();
	}

}
