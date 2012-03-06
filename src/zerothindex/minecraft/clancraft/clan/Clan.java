package zerothindex.minecraft.clancraft.clan;

import java.util.ArrayList;

/**
 * A Clan has a collection of CMembers and a CPlot.
 * 
 * @author zerothindex
 *
 */
public class Clan {

	private ArrayList<CMember> members;
	private ArrayList<CMember> invites;
	
	private ArrayList<Clan> allies;
	private ArrayList<Clan> enemies;
	
	private CPlot territory;
	
}
