package com.gmail.www.woodrow73.jlog;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class Utility {

	/**Searches an inventory for an item, and returns it's place
	 * @param inv, the Inventory we're looking at
	 * @param search, the material of whose first instance we're looking for
	 * @return int, the slot value of the first instance of the material
	 */
	public static int getMaterialIndex(Inventory inv, Material search) {
		for(int i = 0; i < inv.getSize(); i++) {
			if(inv.getItem(i) != null && inv.getItem(i).getType() != Material.AIR) {
				if(inv.getItem(i).getType() == search)
					return i;
			}
		}
		return -1;
	}
	
}
