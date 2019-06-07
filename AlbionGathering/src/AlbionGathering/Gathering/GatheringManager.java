package AlbionGathering.Gathering;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import AlbionGathering.Main.PlayerStats;

public class GatheringManager 
{
	
	public static final boolean harvestNoGatherable = true;
	public static final int _maxResourceTier = 4;
	public static HashMap<Player, PlayerGathering> playersGatheringMap = new HashMap<>();
	public static HashMap<GatheringType, ItemStack[]> resourceDropMap = new HashMap<>();
	private static HashMap<GatheringType, Material[]> gatheringMap = new HashMap<>();
	private static HashMap<GatheringType, String> TOOLS_MAP = new HashMap<>();
	private static HashMap<EntityType, Integer> gatheringMobMap = new HashMap<>();
	
	
	public static void initPlayerGatheringManager()
	{
		Material[]  ore = new Material[] {Material.IRON_ORE,Material.GOLD_ORE,Material.DIAMOND_ORE,Material.EMERALD_ORE};
		Material[]  stone = new Material[] {Material.COBBLESTONE,Material.GRANITE,Material.ANDESITE,Material.DIORITE};
		Material[]  fiber = new Material[] {Material.PINK_GLAZED_TERRACOTTA,Material.LIGHT_GRAY_GLAZED_TERRACOTTA,Material.GRAY_GLAZED_TERRACOTTA,Material.BLACK_GLAZED_TERRACOTTA};
		Material[]  wood = new Material[] {Material.STRIPPED_OAK_LOG,Material.STRIPPED_BIRCH_LOG,Material.STRIPPED_ACACIA_LOG,Material.STRIPPED_DARK_OAK_LOG};
		Material[]  hide = new Material[] {Material.WHITE_GLAZED_TERRACOTTA,Material.ORANGE_GLAZED_TERRACOTTA,Material.LIGHT_BLUE_TERRACOTTA,Material.YELLOW_GLAZED_TERRACOTTA};
		
		gatheringMap.put(GatheringType.Ore, ore);
		gatheringMap.put(GatheringType.Stone, stone);
		gatheringMap.put(GatheringType.Fibre, fiber);
		gatheringMap.put(GatheringType.Wood, wood);
		gatheringMap.put(GatheringType.Hide, hide);
		
		
		resourceDropMap.put(GatheringType.Ore, new ItemStack[] {new ItemStack(Material.IRON_ORE),new ItemStack(Material.GOLD_ORE),new ItemStack(Material.DIAMOND_ORE),new ItemStack(Material.EMERALD_ORE)});
		resourceDropMap.put(GatheringType.Stone, new ItemStack[] {new ItemStack(Material.COBBLESTONE),new ItemStack(Material.GRANITE),new ItemStack(Material.ANDESITE),new ItemStack(Material.DIORITE)});
		resourceDropMap.put(GatheringType.Fibre, new ItemStack[] {new ItemStack(Material.PINK_GLAZED_TERRACOTTA),new ItemStack(Material.LIGHT_GRAY_GLAZED_TERRACOTTA),new ItemStack(Material.GRAY_GLAZED_TERRACOTTA),new ItemStack(Material.BLACK_GLAZED_TERRACOTTA)});
		resourceDropMap.put(GatheringType.Wood, new ItemStack[] {new ItemStack(Material.STRIPPED_OAK_LOG),new ItemStack(Material.STRIPPED_BIRCH_LOG),new ItemStack(Material.STRIPPED_ACACIA_LOG),new ItemStack(Material.STRIPPED_DARK_OAK_LOG)});
		resourceDropMap.put(GatheringType.Hide, new ItemStack[] {new ItemStack(Material.WHITE_GLAZED_TERRACOTTA),new ItemStack(Material.ORANGE_GLAZED_TERRACOTTA),new ItemStack(Material.LIGHT_BLUE_TERRACOTTA),new ItemStack(Material.YELLOW_GLAZED_TERRACOTTA)});
		
		TOOLS_MAP.put(GatheringType.Ore, "Kilof");
		TOOLS_MAP.put(GatheringType.Stone, "M³ot");
		TOOLS_MAP.put(GatheringType.Fibre, "Sierp");
		TOOLS_MAP.put(GatheringType.Wood, "Siekiera");
		TOOLS_MAP.put(GatheringType.Hide, "Nó¿");
		
		
		gatheringMobMap.put(EntityType.RABBIT, 0);
		gatheringMobMap.put(EntityType.OCELOT, 1);
		gatheringMobMap.put(EntityType.WOLF, 2);
		gatheringMobMap.put(EntityType.POLAR_BEAR, 3);

	}
	
	public static GatheringType getGatheringType(Material mat)
	{

		if(mat.equals(Material.AIR)) return null;
		//return gatheringMap.entrySet().stream().filter( e -> Arrays.asList(e.getValue()).contains(mat)).findFirst().get().getKey();
		Entry<GatheringType, Material[]> d = gatheringMap.entrySet().stream().filter( e -> Arrays.stream(e.getValue()).anyMatch(mat::equals)).findFirst().orElse(null);
		return d != null ? d.getKey() : null;
	}
	
	public static int getResourceTier(GatheringType gt,Material mat)
	{
		return Arrays.asList(gatheringMap.get(gt)).indexOf(mat);
	}
	
	public static int getResourceTier(EntityType e)
	{
		return gatheringMobMap.containsKey(e) ? gatheringMobMap.get(e) : -1;
	}
	
	public static String getItemName(ItemStack item)
	{
		return (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) ? item.getItemMeta().getDisplayName() : "";
	}
	
	public static boolean checkCanGather(PlayerStats ps, Block b,ItemStack item)
	{
		Material mat = b.getType();
		GatheringType gt = getGatheringType(mat);
		if(gt == null) return false;
		int resourceTier = getResourceTier(gt, mat);
		GatheringKey gk = new GatheringKey(gt, resourceTier);
		GatheringStats gs = ps.getStats(gk);
		if(gs == null) return false;
		
		//System.out.println(String.format("Material: %s Gathering Type: %s Resource tier: %s Gathering stats: %s ", mat.toString(),gt.toString(),resourceTier+"",gs.toString()));
		//System.out.println("Tool: " + checkTool(gt,resourceTier,getItemName(item)));
		
		boolean tool = checkTool(gt,resourceTier,getItemName(item));
		boolean level = gs.gatheringLevel >= 1;
		if(!tool) ps.player.sendMessage(ChatColor.GRAY + "Nie masz wymaganego narzêdzia");
		if(!level) ps.player.sendMessage(ChatColor.GRAY + "Nie masz wymaganego poziomu");
		return tool && level;
	}
	public static boolean checkTool(GatheringType gt,int resourceTier,String itemName) 
	{ 
		//System.out.println(String.format("ItemName: %s Contains %b Tier: %d Tier good %b" ,itemName,itemName.contains(TOOLS_MAP.get(gt)),getToolTier(itemName),((getToolTier(itemName) >= resourceTier))));
		return itemName.contains(TOOLS_MAP.get(gt)) && (getToolTier(itemName) >= resourceTier);
	}
	
	public static ItemStack getResourceItemStack(GatheringType gt,int resourceTier,PlayerStats gs)
	{
		ItemStack item = resourceDropMap.get(gt)[resourceTier].clone();
		item.setAmount(GatheringDropCalculator.getDropAmount(gt, resourceTier, gs));
		return resourceDropMap.get(gt)[resourceTier].clone();
	}
	
	public static Material getGahteringMaterial(GatheringType gt,int resourceTier)
	{
		return gatheringMap.get(gt)[resourceTier];
	}
	
	public static int getToolTier(String itemName)
	{
		String namesplit[] = itemName.split(" ");
		if(namesplit.length >= 2)
		{
			switch (namesplit[1]) {
			case "I":
				return 0;
			case "II":
				return 1;
			case "III":
				return 2;
			case "IV":
				return 3;
			default:
				return -1;
			}
		}
		return -1;
	}
}
