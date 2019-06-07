package AlbionGathering.Main;

import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;

import AlbionGathering.BlockRegeneration.BlockRegenerationManager;
import AlbionGathering.Commands.StatsCommand;
import AlbionGathering.Gathering.GatheringExpCalculator;
import AlbionGathering.Gathering.GatheringManager;
import AlbionGathering.Listeners.EntityDieListener;
import AlbionGathering.Listeners.InventoryClickListener;
import AlbionGathering.Listeners.PlayerBreakBlockListener;
import AlbionGathering.Listeners.PlayerJoinListener;
import AlbionGathering.Listeners.PlayerStartGatheringListener;

public class Core extends JavaPlugin{

	private ProtocolManager protocolManager;
	private static Core core;
	private static BlockRegenerationManager brm = new BlockRegenerationManager();
	
	public Core()
	{
		
	}
	
	@Override
	public void onEnable()
	{
		core = this;
		GatheringManager.initPlayerGatheringManager();
		GatheringExpCalculator.initGatheringExpCalculator();
		
		this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerBreakBlockListener(), this);
		this.getServer().getPluginManager().registerEvents(new EntityDieListener(), this);
		this.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
		protocolManager = ProtocolLibrary.getProtocolManager();
		protocolManager.addPacketListener(new PlayerStartGatheringListener(this, ListenerPriority.NORMAL,PacketType.Play.Client.BLOCK_DIG));
		
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, brm, 0, 20);
		
		this.getCommand("stats").setExecutor(new StatsCommand());
	}
	
	@Override
	public void onDisable()
	{
		PlayersStats.getPlayersStats().getPlayersStatsList().parallelStream().forEach( d -> d.save(this));
	}
	
	public static Core getCore()
	{
		return core;
	}
	
	public  BlockRegenerationManager getBlockManager()
	{
		return brm;
	}
}
