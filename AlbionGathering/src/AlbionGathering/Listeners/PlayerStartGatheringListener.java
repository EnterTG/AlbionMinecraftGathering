package AlbionGathering.Listeners;

import java.util.concurrent.Callable;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerDigType;

import AlbionGathering.Gathering.GatheringManager;
import AlbionGathering.Gathering.GatheringSpeedCalculator;
import AlbionGathering.Gathering.GatheringType;
import AlbionGathering.Gathering.PlayerGathering;
import AlbionGathering.Main.Core;
import AlbionGathering.Main.PlayerStats;
import AlbionGathering.Main.PlayersStats;
import PacketWrappers.WrapperPlayClientBlockDig;

public class PlayerStartGatheringListener extends PacketAdapter
{
	public PlayerStartGatheringListener(AdapterParameteters params) {
		super(params);
		// TODO Auto-generated constructor stub
	}

	public PlayerStartGatheringListener(Plugin plugin, Iterable<? extends PacketType> types) {
		super(plugin, types);
		// TODO Auto-generated constructor stub
	}

	public PlayerStartGatheringListener(Plugin plugin, ListenerPriority listenerPriority,
			Iterable<? extends PacketType> types, ListenerOptions... options) {
		super(plugin, listenerPriority, types, options);
		// TODO Auto-generated constructor stub
	}

	public PlayerStartGatheringListener(Plugin plugin, ListenerPriority listenerPriority,
			Iterable<? extends PacketType> types) {
		super(plugin, listenerPriority, types);
		// TODO Auto-generated constructor stub
	}

	public PlayerStartGatheringListener(Plugin plugin, ListenerPriority listenerPriority, PacketType... types) {
		super(plugin, listenerPriority, types);
		// TODO Auto-generated constructor stub
	}

	public PlayerStartGatheringListener(Plugin plugin, PacketType... types) {
		super(plugin, types);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onPacketReceiving(PacketEvent e) 
	{
		WrapperPlayClientBlockDig pac = new WrapperPlayClientBlockDig(e.getPacket());
		Player p = e.getPlayer();
		
		if(pac.getStatus() !=  PlayerDigType.START_DESTROY_BLOCK)
		{
			//System.out.println("Player stop gathering");
			PlayerGathering pg = GatheringManager.playersGatheringMap.get(e.getPlayer());
			if(pg != null) 
			{ 
				pg.onStop();
				Core core = Core.getCore();
				BukkitScheduler bk = core.getServer().getScheduler();
				bk.callSyncMethod(core, new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						p.removePotionEffect(PotionEffectType.SLOW_DIGGING);
						return true;
					}
				});
			}
		}
		else
		{
			Block b = e.getPlayer().getWorld().getBlockAt(pac.getLocation().toLocation(p.getWorld()));
			PlayerStats ps  = PlayersStats.getPlayersStats().getPlayerStats(p);
			ItemStack item = p.getInventory().getItemInMainHand();
			
			if(GatheringManager.checkCanGather(ps, b, item))
			{
				//System.out.println("Player start gathering");
				Material mat = b.getType();
				GatheringType gt = GatheringManager.getGatheringType(mat);
				int resourceTier = GatheringManager.getResourceTier(gt, mat);
				int toolTier = GatheringManager.getToolTier(GatheringManager.getItemName(item));
				int gatheringTime = GatheringSpeedCalculator.getGatheringTime(gt, resourceTier, toolTier, ps);
				
				PlayerGathering pg = new PlayerGathering(e.getPlayer(), b, gatheringTime);
				GatheringManager.playersGatheringMap.put(e.getPlayer(), pg);
				
				Core core = Core.getCore();
				BukkitScheduler bk = core.getServer().getScheduler();
				bk.callSyncMethod(core, new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 100000, 100));
						return true;
					}
					
				});
				pg.taskID = bk.scheduleSyncRepeatingTask(core, pg, 0, 1);
			}
		}
	}

}
