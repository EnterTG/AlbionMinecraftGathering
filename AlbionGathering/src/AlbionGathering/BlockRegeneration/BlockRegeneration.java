package AlbionGathering.BlockRegeneration;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockRegeneration 
{

	public final Block block;
	public final Material previousMaterial;
	public int timeToRespawn;
	
	public BlockRegeneration(Block block, Material priviusMaterial, int timeToRespawn) {
		super();
		this.block = block;
		this.previousMaterial = priviusMaterial;
		this.timeToRespawn = timeToRespawn;
	}
	
	public boolean tick() 
	{
		if(--timeToRespawn<=0) 
		{
			block.setType(previousMaterial);
			return true;
		}
		return false;
	}
	
	
}
