package zerothindex.clancraft.bukkit;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

	@Override
	public boolean isPlayer() {
		return (sender instanceof Player);
	}
	
	

}
