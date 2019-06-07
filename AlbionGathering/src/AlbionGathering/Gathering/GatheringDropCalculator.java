package AlbionGathering.Gathering;

import java.util.Random;

import AlbionGathering.Main.PlayerStats;

public class GatheringDropCalculator {

	private static Random random = new Random();
	private static int[] dropsTable = new int[]{3,2,1,1};
	public static int getDropAmount(GatheringType gt,int resourceTier,PlayerStats ps)
	{
		int gatheringLevel = ps.getStats(new GatheringKey(gt, resourceTier)).gatheringLevel;
		return dropsTable[resourceTier] +  random.nextInt(100) <= (int)(gatheringLevel * 0.5) ? dropsTable[resourceTier] : 0;
	}

}
