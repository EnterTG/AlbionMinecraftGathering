package AlbionGathering.BlockRegeneration;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockRegenerationManager implements Runnable
{
	private ConcurrentLinkedQueue<BlockRegeneration > blocks = new ConcurrentLinkedQueue<>();	
	private final int[] timers = new int[] {5,10,15,25};
	
	public void addBlock(Block b, Material mat,int resourceTier)
	{
		BlockRegeneration br = new BlockRegeneration(b, mat, timers[resourceTier]);
		blocks.add(br);
	}
	@Override
	public void run() 
	{
		blocks.forEach(b -> {if(b.tick()) blocks.remove(b);} );
	}
}
