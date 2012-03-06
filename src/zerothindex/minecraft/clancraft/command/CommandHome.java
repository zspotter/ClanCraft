package zerothindex.minecraft.clancraft.command;

import org.bukkit.command.CommandSender;

import zerothindex.minecraft.clancraft.Messageable;

public class CommandHome extends CommandBase {

	@Override
	public String getName() {
		return "c";
	}

	@Override
	public String getDescription() {
		return "view the help page";
	}

	@Override
	public String getUsage() {
		return "/c";
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
	public void handle(Messageable sender, String[] args) {
		sender.message("-ClanCraft-\nNothing to see here!");
		
	}

	

	

}
