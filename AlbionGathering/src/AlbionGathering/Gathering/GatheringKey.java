package AlbionGathering.Gathering;

public class GatheringKey implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2490365868432267984L;



	public GatheringKey(GatheringType gatheringType, int gatheringTier) 
	{
		super();
		this.gatheringType = gatheringType;
		this.gatheringTier = gatheringTier;
	}
	public final GatheringType gatheringType;
	public final int gatheringTier;
	


	@Override
	public boolean equals(Object o) 
	{
		if (this == o) return true;
		if (!(o instanceof GatheringKey)) return false;
		GatheringKey key = (GatheringKey) o;
		return gatheringType == key.gatheringType && gatheringTier == key.gatheringTier;
	}

	@Override
	public int hashCode() 
	{
		int result = gatheringType.hashCode();
		result = 31 * result + gatheringTier;
		return result;
	}
	
	@Override
	public String toString()
	{
		return "GatheringType: " + gatheringType + " GatheringTier: " + gatheringTier;
	}
}
