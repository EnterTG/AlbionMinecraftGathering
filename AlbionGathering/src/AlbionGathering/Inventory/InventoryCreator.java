package AlbionGathering.Inventory;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import AlbionGathering.Gathering.GatheringKey;
import AlbionGathering.Gathering.GatheringManager;
import AlbionGathering.Gathering.GatheringStats;
import AlbionGathering.Gathering.GatheringType;
import AlbionGathering.Main.PlayerStats;
import AlbionGathering.Main.PlayersStats;

public class InventoryCreator {


	public static Inventory getStatsInventory(Player p)
	{
		return getStatsInventory(PlayersStats.getPlayersStats().getPlayerStats(p));
	}
	
	
	public static Inventory getStatsInventory(PlayerStats p)
	{
		Inventory inv = Bukkit.createInventory(null, 36, "Gathering statistics");
		for(GatheringType gt : GatheringType.values())
		{
			
			for(int i = 0 ; i < GatheringManager._maxResourceTier;i++)
			{
				GatheringKey gk = new GatheringKey(gt, i);
				GatheringStats gs= p.getStats(gk);
				inv.setItem(gt.ordinal()*2 + i*9, ItemCreator.createItemStack(ChatColor.GRAY +gt.name() + " gathering",
GatheringManager.getGahteringMaterial(gt, i) , Arrays.asList(ChatColor.GRAY + "Level: " + ChatColor.GOLD + gs.gatheringLevel,ChatColor.GRAY +"Exp: " + ChatColor.GOLD + gs.gatheringExp), i+1));
				
			}
		}
		
		
		return inv;
	}
}
