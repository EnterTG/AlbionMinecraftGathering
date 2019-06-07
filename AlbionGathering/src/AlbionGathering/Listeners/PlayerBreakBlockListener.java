package AlbionGathering.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import AlbionGathering.Gathering.GatheringManager;

public class PlayerBreakBlockListener implements Listener
{

	@EventHandler
	public void onPlayerBreakBlockEvent(BlockBreakEvent e)
	{
		if(GatheringManager.getGatheringType(e.getBlock().getType()) == null)
			e.setCancelled(!GatheringManager.harvestNoGatherable);
		else
			e.setCancelled(true);
	}

}
