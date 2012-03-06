package zerothindex.clancraft.clan;

import java.util.Comparator;

/**
 * Used to sort clans based on size and online players.
 * @author Zachary Potter
 *
 */
public class ClanComparator implements Comparator<Clan> {

	@Override
	public int compare(Clan c1, Clan c2) {
		if (c1.getOnlineSize() > c2.getOnlineSize())
			return 1;
		if (c1.getOnlineSize() < c2.getOnlineSize())
			return -1;
		if (c1.getSize() > c2.getSize())
			return 1;
		if (c1.getSize() < c2.getSize())
			return -1;
		
		return 0;
	}
	
}