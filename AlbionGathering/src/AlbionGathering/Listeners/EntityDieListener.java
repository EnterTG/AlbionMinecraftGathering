package AlbionGathering.Listeners;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.util.Vector;

import AlbionGathering.Gathering.GatheringManager;
import AlbionGathering.Gathering.GatheringType;

public class EntityDieListener implements Listener{

	private static Random random = new Random();
	@EventHandler
	public void onEntityDie(EntityDeathEvent e)
	{
		int resourceTier;
		if( (resourceTier = GatheringManager.getResourceTier(e.getEntityType()))> -1)
		{
			Material mat = GatheringManager.getGahteringMaterial(GatheringType.Hide	, resourceTier);
			spawnResource(mat,e.getEntity().getLocation(),10);
		}
	}

	
	private void spawnResource(Material mat, Location loc,int a)
	{
		Block b = loc.getBlock();
		Material bmat = b.getType();
		if(bmat.equals(Material.AIR) || bmat.equals(Material.SNOW))
			b.setType(mat);
		else
			if(a > 0)
				spawnResource(mat,loc.add(new Vector(random.nextInt(5)-3,0,random.nextInt(5)-3)),a-1);
	}
}
