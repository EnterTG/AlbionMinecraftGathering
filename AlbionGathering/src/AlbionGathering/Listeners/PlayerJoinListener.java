package AlbionGathering.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;

import AlbionGathering.Main.Core;
import AlbionGathering.Main.PlayerStats;
import AlbionGathering.Main.PlayersStats;

public class PlayerJoinListener implements Listener{

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		PlayerStats ps = new PlayerStats(e.getPlayer());
		PlayersStats.getPlayersStats().addPlayer(ps);
		ps.load(Core.getCore());
		e.getPlayer().removePotionEffect(PotionEffectType.SLOW_DIGGING);
	}

}
