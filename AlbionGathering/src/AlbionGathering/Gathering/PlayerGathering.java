package AlbionGathering.Gathering;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import AlbionGathering.Main.Core;
import AlbionGathering.Main.PlayerStats;
import AlbionGathering.Main.PlayersStats;

public class PlayerGathering implements Runnable{

	private final Player player;
	private final Block block;
	private double time;
	private double currentTime;
	BossBar bossBar;
	public int taskID;
	
	private boolean stoped = false;
	
	public PlayerGathering(Player p,Block b,int time) 
	{
		player = p;
		block = b;
		this.time = time; 
		currentTime = time;
		bossBar = Bukkit.createBossBar("Zbieranie", BarColor.WHITE, BarStyle.SOLID);
		bossBar.setProgress(1d);
		bossBar.addPlayer(p);
		bossBar.setVisible(true);
		//System.out.println("Tick to gather: " +currentTime);
	}
	
	public void tick()
	{
		currentTime -= 1;
		//System.out.println("Time: " + time + " Current Time: " + currentTime);
		updateBossBar();
		if(currentTime == 0 ) onMined();
	}
	
	public void updateBossBar()
	{
		//System.out.println("Progress: " + ( ( (currentTime*100) / time ) / 100) ) ;
		bossBar.setProgress(Math.min(1,Math.max(0,((currentTime*100)/time)/100)));
	}
	
	public void onMined()
	{
		Material mat = block.getType();
		GatheringType gt = GatheringManager.getGatheringType(mat);
		if(gt != null)
		{
			//System.out.println("ItemStack: " + GatheringManager.getResourceItemStack(gt, GatheringManager.getResourceTier(gt, mat)));
			int resourceTier =  GatheringManager.getResourceTier(gt, mat);
			PlayerStats ps =PlayersStats.getPlayersStats().getPlayerStats(player);
			HashMap<Integer, ItemStack> drop = player.getInventory().addItem(GatheringManager.getResourceItemStack(gt, resourceTier,ps));
			
			block.setType(Material.AIR);
			drop.entrySet().forEach( e-> player.sendMessage(ChatColor.GRAY + e.getKey().toString() + " zasobów przepad³o."));
			
			player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
			player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 10, 1);
			
			if(gt != GatheringType.Hide) Core.getCore().getBlockManager().addBlock(block, mat, resourceTier);
			GatheringExpCalculator.addExp(ps, gt, resourceTier);
		}
		onStop();
	}

	@Override
	public void run() 
	{
		tick();
	}

	
	public void onStop()
	{
		if(!stoped)
		{
			stoped = true;
			bossBar.removePlayer(player);
			Core core = Core.getCore();
			BukkitScheduler bk = core.getServer().getScheduler();
			bk.cancelTask(taskID);
			GatheringManager.playersGatheringMap.remove(player);
		}
	}

	
	
}
