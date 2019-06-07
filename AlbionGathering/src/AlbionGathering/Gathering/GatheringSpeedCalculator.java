package AlbionGathering.Gathering;

import AlbionGathering.Main.PlayerStats;

public class GatheringSpeedCalculator 
{

	private static final short[][] gatheringSpeeds = new short[][]{
		/*T1*/{100,200,-1,-1},
		/*T2*/{90,100,	200,-1},
		/*T3*/{80,90,100,200},
		/*T4*/{30,80,90,100}}; 


	public static int getGatheringTime(GatheringType gt,int resourceTier,int toolTier, PlayerStats ps)
	{
		int lvl = ps.getStats(new GatheringKey(gt, resourceTier)).gatheringLevel;
		
		return (int) (gatheringSpeeds[toolTier][resourceTier]- (gatheringSpeeds[toolTier][resourceTier] * (0.02*lvl)));
		
	}

}
