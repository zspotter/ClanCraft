package zerothindex.clancraft.bukkit;

import org.bukkit.command.CommandSender;

import zerothindex.clancraft.MessageReceiver;

/**
 * A Bukkit console or player
 * 
 * @author zerothindex
 *
 */
public class BukkitCommandSender implements MessageReceiver{

	private CommandSender sender;
	
	public BukkitCommandSender(CommandSender sender) {
		this.sender = sender;
	}
	
	@Override
	public void message(String msg) {
		sender.sendMessage(BukkitClanPlugin.stripMessage(msg));
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
		return false;
	}

}
