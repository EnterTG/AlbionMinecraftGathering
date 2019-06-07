package AlbionGathering.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import AlbionGathering.Main.Core;
import AlbionGathering.Main.PlayersStats;

public class PlayerExitListener implements Listener{


	@EventHandler
	public void onPLayerLeaveEvent(PlayerQuitEvent e)
	{
		PlayersStats.getPlayersStats().getPlayerStats(e.getPlayer()).save(Core.getCore());
		PlayersStats.getPlayersStats().removePlayerStats(e.getPlayer());
	}
}
