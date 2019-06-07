package AlbionGathering.Gathering;

public class GatheringStats implements java.io.Serializable {

	/*
	 * 
	 */
	private static final long serialVersionUID = -7293084682586058167L;
	
	public int gatheringLevel; 
	public long gatheringExp;;
	public GatheringStats(long gexp,int glv) 
	{

		gatheringLevel = glv;
		gatheringExp = gexp;
	}

	@Override
	public String toString() 
	{
		return "Lvl: "+ gatheringLevel + " Exp: " + gatheringExp;
	}
}
