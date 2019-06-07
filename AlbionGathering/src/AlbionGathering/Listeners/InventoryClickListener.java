package AlbionGathering.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import AlbionGathering.Inventory.InventoryCreator;

public class InventoryClickListener implements Listener
{
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e)
	{
		if(e.getInventory().getTitle().equals(InventoryCreator.INV_NAME))
			e.setCancelled(true);
	}
}
