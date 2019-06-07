package AlbionGathering.Main;

import java.util.HashMap;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import AlbionGathering.Gathering.GatheringKey;
import AlbionGathering.Gathering.GatheringStats;
import AlbionGathering.Gathering.GatheringType;

public class PlayerStats 
{

	public final Player player;
	private HashMap<GatheringKey,GatheringStats> playerStats;
	
	public PlayerStats(Player p)
	{
		player = p;
	}
	//To lepiej poprawic
	public void save(JavaPlugin jp)
	{
		FileConfiguration config = jp.getConfig();

		
		playerStats.entrySet().forEach( a -> 
		{
			config.set(player.getUniqueId().toString()+"."+a.getKey().toString()+".Exp", a.getValue().gatheringExp);
			config.set(player.getUniqueId().toString()+"."+a.getKey().toString()+".Lvl", a.getValue().gatheringLevel);
		});
		Core.getCore().saveConfig();
	}
	
	public void load(JavaPlugin jp)
	{
		FileConfiguration config = jp.getConfig();
		generateDefaultHashMap();
		if(config.contains(player.getUniqueId().toString()))
		{
			ConfigurationSection cs = config.getConfigurationSection(player.getUniqueId().toString());
			playerStats.entrySet().forEach( a -> {
				ConfigurationSection cs2 =  cs.getConfigurationSection(a.getKey().toString());
				a.getValue().gatheringExp = cs2.getInt("Exp");
				a.getValue().gatheringLevel = cs2.getInt("Lvl");
			}
			);
		}
	}

	public GatheringStats getStats(GatheringKey gk)
	{
		return playerStats.get(gk);
	}
	
	private void generateDefaultHashMap()
	{
		playerStats = new HashMap<>();
		GatheringKey 
		gk = new GatheringKey(GatheringType.Ore, 0);
		playerStats.put(gk, new GatheringStats(0, 1));
		gk = new GatheringKey(GatheringType.Ore, 1);
		playerStats.put(gk, new GatheringStats(0, 0));
		gk = new GatheringKey(GatheringType.Ore, 2);
		playerStats.put(gk, new GatheringStats(0, 0));
		gk = new GatheringKey(GatheringType.Ore, 3);
		playerStats.put(gk, new GatheringStats(0, 0));

		gk = new GatheringKey(GatheringType.Fibre, 0);
		playerStats.put(gk, new GatheringStats(0, 1));
		gk = new GatheringKey(GatheringType.Fibre, 1);
		playerStats.put(gk, new GatheringStats(0, 0));
		gk = new GatheringKey(GatheringType.Fibre, 2);
		playerStats.put(gk, new GatheringStats(0, 0));
		gk = new GatheringKey(GatheringType.Fibre, 3);
		playerStats.put(gk, new GatheringStats(0, 0));
		
		gk = new GatheringKey(GatheringType.Hide, 0);
		playerStats.put(gk, new GatheringStats(0, 1));
		gk = new GatheringKey(GatheringType.Hide, 1);
		playerStats.put(gk, new GatheringStats(0, 0));
		gk = new GatheringKey(GatheringType.Hide, 2);
		playerStats.put(gk, new GatheringStats(0, 0));
		gk = new GatheringKey(GatheringType.Hide, 3);
		playerStats.put(gk, new GatheringStats(0, 0));
		
		gk = new GatheringKey(GatheringType.Stone, 0);
		playerStats.put(gk, new GatheringStats(0, 1));
		gk = new GatheringKey(GatheringType.Stone, 1);
		playerStats.put(gk, new GatheringStats(0, 0));
		gk = new GatheringKey(GatheringType.Stone, 2);
		playerStats.put(gk, new GatheringStats(0, 0));
		gk = new GatheringKey(GatheringType.Stone, 3);
		playerStats.put(gk, new GatheringStats(0, 0));
		
		gk = new GatheringKey(GatheringType.Wood, 0);
		playerStats.put(gk, new GatheringStats(0, 1));
		gk = new GatheringKey(GatheringType.Wood, 1);
		playerStats.put(gk, new GatheringStats(0, 0));
		gk = new GatheringKey(GatheringType.Wood, 2);
		playerStats.put(gk, new GatheringStats(0, 0));
		gk = new GatheringKey(GatheringType.Wood, 3);
		playerStats.put(gk, new GatheringStats(0, 0));
	}
}
