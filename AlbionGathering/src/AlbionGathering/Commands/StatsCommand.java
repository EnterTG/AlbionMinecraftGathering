package AlbionGathering.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import AlbionGathering.Inventory.InventoryCreator;

public class StatsCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) 
	{
		if(arg0 instanceof Player)
		{
			Player p = (Player)arg0;
			p.openInventory(InventoryCreator.getStatsInventory(p));
		}
		else
			arg0.sendMessage("Sender error");
		return false;
	}


}
