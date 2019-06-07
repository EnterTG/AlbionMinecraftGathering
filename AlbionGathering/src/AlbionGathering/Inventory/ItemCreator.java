package AlbionGathering.Inventory;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemCreator {


	public static ItemStack createItemStack(String name,Material mat,List<String> lore,int amount)
	{
		ItemStack item =  new ItemStack(mat,amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
}
