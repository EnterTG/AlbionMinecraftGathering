package AlbionGathering.Gathering;

import AlbionGathering.Main.PlayerStats;

public class GatheringExpCalculator {
	
	public static final long[][][] _expTable = new long[GatheringType.values().length][GatheringManager._maxResourceTier][100];
	
	public static void initGatheringExpCalculator()
	{
		//Equasion for functions
		//(t^2*(150*l))+(l*1000)
		for(int i = 1 ; i < 100 ;i++)
		{
			for(int a = 0 ; a < GatheringManager._maxResourceTier;a++)
			{
				long l = (((a+1)*(a+1)) * (150*i)) * (i*1000);
				for(GatheringType gt : GatheringType.values())
				{
					_expTable[gt.ordinal()][a][i] = l;
					//System.out.println(String.format("Gathering type: %s i: %d a: %d l: %d", gt.toString(),i,a,l));
				}
			}
		}
		
		for(int a = 0 ; a < GatheringManager._maxResourceTier;a++)
		{
			long l = (a+1)*35000;
			for(GatheringType gt : GatheringType.values())
			{
				_expTable[gt.ordinal()][a][0] = l;
			}
		}
	}
	
	public static short getExp(GatheringType gt,int resourceTier)
	{
		return (short) (Math.pow(resourceTier+2, 3)+50);
	}
	public static void addExp(PlayerStats ps,GatheringType gt,int resourceTier)
	{
		GatheringKey gk = new GatheringKey(gt, resourceTier);
		GatheringStats gs = ps.getStats(gk);
		gs.gatheringExp += getExp(gt,resourceTier);
		checkExp(ps,gt,resourceTier);
		for(int i = resourceTier+1 ; i < GatheringManager._maxResourceTier;i++)
		{
			GatheringKey gk2 = new GatheringKey(gt, i);
			GatheringStats gs2 = ps.getStats(gk2);
			if(gs2.gatheringLevel < 1)
			{
				gs2.gatheringExp += getExp(gt,i);
				checkExp(ps,gt,i);
				break;
			}
		}
	}
	
	public static void checkExp(PlayerStats ps)
	{
		for(GatheringType gt : GatheringType.values())
			for(int i = 0 ;  i < GatheringManager._maxResourceTier;i++)
			{
				checkExp(ps,gt,i);
			}
	}
	public static void checkExp(PlayerStats ps,GatheringType gt)
	{
		for(int i = 0 ;  i < GatheringManager._maxResourceTier;i++)
		{
			checkExp(ps,gt,i);
		}
	}

	public static void checkExp(PlayerStats ps,GatheringType gt,int resourceTier)
	{
		GatheringStats gs = ps.getStats(new GatheringKey(gt, resourceTier));
		//System.out.println("Gl: " + gs.gatheringLevel +" Exp need: "+ _expTable[gt.ordinal()][resourceTier][gs.gatheringLevel]+ " Gathering type: " + gt.toString());
		if(gs.gatheringExp >= _expTable[gt.ordinal()][resourceTier][gs.gatheringLevel] && gs.gatheringLevel < 100)
		{
			gs.gatheringExp = 0;
			gs.gatheringLevel += 1;
		}
	}
}
