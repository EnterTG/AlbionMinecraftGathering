package AlbionGathering.Main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class PlayersStats {

	private static final PlayersStats _playersStats = new PlayersStats();
	
	private PlayersStats()
	{
		
	}
	
	private List<PlayerStats> playersStats = new ArrayList<>();
	
	public void addPlayer(PlayerStats ps)
	{
		playersStats.add(ps);
	}
	public PlayerStats getPlayerStats(Player p)
	{
		return playersStats.parallelStream().filter(ps -> ps.player == p).findFirst().get();
	}
	public void removePlayerStats(Player p)
	{
		playersStats.remove(playersStats.parallelStream().filter(ps -> ps.player == p).findFirst().get());
	}
	
	public static PlayersStats getPlayersStats()
	{
		return _playersStats;
	}
	
	public List<PlayerStats> getPlayersStatsList()
	{
		return playersStats;
	}
}
