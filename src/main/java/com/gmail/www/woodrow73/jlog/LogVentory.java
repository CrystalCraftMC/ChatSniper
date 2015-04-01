package com.gmail.www.woodrow73.jlog;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

public class LogVentory implements Listener {
	
	
	public LogVentory(JLog jl) {
		jl.getServer().getPluginManager().registerEvents(this, jl);
	}
	
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		//without the line below, exception will be thrown upon clicking outside of the inventory
		if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
			if(e.getInventory().getName().equals("Customize Query") && e.getInventory().getSize() == 45) {
				e.setCancelled(true);
				Player p = (Player)e.getWhoClicked();
				if(e.getCurrentItem().getType() == Material.FIRE) {
					return;
				}
				int slot = e.getRawSlot();
				if(slot <= 5 || (slot >= 9 && slot <= 14)) {
					for(int i = 0; i < 16; i++) {
						if(e.getInventory().getItem(i) != null && 
								e.getInventory().getItem(i).getType() != Material.AIR) {
							if(e.getInventory().getItem(i).getType() == Material.WOOD_SPADE) {
								ItemMeta im = e.getInventory().getItem(i).getItemMeta();
								ItemStack woolen = new ItemStack(Material.WOOL, 1);
								ItemMeta wmeta = woolen.getItemMeta();
								wmeta.setDisplayName(im.getDisplayName());
								woolen.setItemMeta(wmeta);
								e.getInventory().setItem(i, woolen);
							}
						}
					}
					ItemMeta im = e.getCurrentItem().getItemMeta();
					ItemStack fire = new ItemStack(Material.WOOD_SPADE, 1);
					ItemMeta fireMeta = fire.getItemMeta();
					fireMeta.setDisplayName(im.getDisplayName());
					fire.setItemMeta(fireMeta);
					e.getInventory().setItem(slot, fire);
				}
				else if(slot == 7 || slot == 8 || slot == 16 || slot == 17) {
					int index = Utility.getMaterialIndex(e.getInventory(), Material.WOOD_SPADE);
					if(index != -1) {
						int moveBy = 0;
						switch(slot) {
						case 7:
							moveBy = 5;
							break;
						case 8:
							moveBy = 1;
							break;
						case 16:
							moveBy = -5;
							break;
						case 17:
							moveBy = -1;
							break;
						}
						ItemStack updateFlame = e.getInventory().getItem(index);
						ItemMeta im = updateFlame.getItemMeta();
						String val = "";
						int valInt = 0;
						String analyzeStr = JLog.map.get(p);
						StringBuilder buildStr = new StringBuilder();
						String[] gold = analyzeStr.split(";");
						switch(index) {
						case 0:
							val = im.getDisplayName().substring(13);
							valInt = Integer.parseInt(val);
							valInt += moveBy;
							im.setDisplayName(ChatColor.GRAY + "From Year: " + String.valueOf(valInt));
							updateFlame.setItemMeta(im);
							gold[0] = String.valueOf(valInt);
							for(int i = 0; i < gold.length; i++)
								buildStr.append(gold[i] + ";");
							buildStr.deleteCharAt(buildStr.length()-1);
							JLog.map.remove(p);
							JLog.map.put(p, buildStr.toString());
							break;
						case 9:
							val = im.getDisplayName().substring(11);
							valInt = Integer.parseInt(val);
							valInt += moveBy;
							im.setDisplayName(ChatColor.GRAY + "To Year: " + String.valueOf(valInt));
							updateFlame.setItemMeta(im);
							gold[6] = String.valueOf(valInt);
							for(int i = 0; i < gold.length; i++)
								buildStr.append(gold[i] + ";");
							buildStr.deleteCharAt(buildStr.length()-1);
							JLog.map.remove(p);
							JLog.map.put(p, buildStr.toString());
							break;
						case 1:
							val = im.getDisplayName().substring(14);
							valInt = Integer.parseInt(val);
							valInt += moveBy;
							im.setDisplayName(ChatColor.GRAY + "From Month: " + String.valueOf(valInt));
							updateFlame.setItemMeta(im);
							gold[1] = String.valueOf(valInt);
							for(int i = 0; i < gold.length; i++)
								buildStr.append(gold[i] + ";");
							buildStr.deleteCharAt(buildStr.length()-1);
							JLog.map.remove(p);
							JLog.map.put(p, buildStr.toString());
							break;
						case 10:
							val = im.getDisplayName().substring(12);
							valInt = Integer.parseInt(val);
							valInt += moveBy;
							im.setDisplayName(ChatColor.GRAY + "To Month: " + String.valueOf(valInt));
							updateFlame.setItemMeta(im);
							gold[7] = String.valueOf(valInt);
							for(int i = 0; i < gold.length; i++)
								buildStr.append(gold[i] + ";");
							buildStr.deleteCharAt(buildStr.length()-1);
							JLog.map.remove(p);
							JLog.map.put(p, buildStr.toString());
							break;
						case 2:
							val = im.getDisplayName().substring(12);
							valInt = Integer.parseInt(val);
							valInt += moveBy;
							im.setDisplayName(ChatColor.GRAY + "From Day: " + String.valueOf(valInt));
							updateFlame.setItemMeta(im);
							gold[2] = String.valueOf(valInt);
							for(int i = 0; i < gold.length; i++)
								buildStr.append(gold[i] + ";");
							buildStr.deleteCharAt(buildStr.length()-1);
							JLog.map.remove(p);
							JLog.map.put(p, buildStr.toString());
							break;
						case 11:
							val = im.getDisplayName().substring(10);
							valInt = Integer.parseInt(val);
							valInt += moveBy;
							im.setDisplayName(ChatColor.GRAY + "To Day: " + String.valueOf(valInt));
							updateFlame.setItemMeta(im);
							gold[8] = String.valueOf(valInt);
							for(int i = 0; i < gold.length; i++)
								buildStr.append(gold[i] + ";");
							buildStr.deleteCharAt(buildStr.length()-1);
							JLog.map.remove(p);
							JLog.map.put(p, buildStr.toString());
							break;
						case 3:
							val = im.getDisplayName().substring(13);
							valInt = Integer.parseInt(val);
							valInt += moveBy;
							im.setDisplayName(ChatColor.GRAY + "From Hour: " + String.valueOf(valInt));
							updateFlame.setItemMeta(im);
							gold[3] = String.valueOf(valInt);
							for(int i = 0; i < gold.length; i++)
								buildStr.append(gold[i] + ";");
							buildStr.deleteCharAt(buildStr.length()-1);
							JLog.map.remove(p);
							JLog.map.put(p, buildStr.toString());
							break;
						case 12:
							val = im.getDisplayName().substring(11);
							valInt = Integer.parseInt(val);
							valInt += moveBy;
							im.setDisplayName(ChatColor.GRAY + "To Hour: " + String.valueOf(valInt));
							updateFlame.setItemMeta(im);
							gold[9] = String.valueOf(valInt);
							for(int i = 0; i < gold.length; i++)
								buildStr.append(gold[i] + ";");
							buildStr.deleteCharAt(buildStr.length()-1);
							JLog.map.remove(p);
							JLog.map.put(p, buildStr.toString());
							break;
						case 4:
							val = im.getDisplayName().substring(15);
							valInt = Integer.parseInt(val);
							valInt += moveBy;
							im.setDisplayName(ChatColor.GRAY + "From Minute: " + String.valueOf(valInt));
							updateFlame.setItemMeta(im);
							gold[4] = String.valueOf(valInt);
							for(int i = 0; i < gold.length; i++)
								buildStr.append(gold[i] + ";");
							buildStr.deleteCharAt(buildStr.length()-1);
							JLog.map.remove(p);
							JLog.map.put(p, buildStr.toString());
							break;
						case 13:
							val = im.getDisplayName().substring(13);
							valInt = Integer.parseInt(val);
							valInt += moveBy;
							im.setDisplayName(ChatColor.GRAY + "To Minute: " + String.valueOf(valInt));
							updateFlame.setItemMeta(im);
							gold[10] = String.valueOf(valInt);
							for(int i = 0; i < gold.length; i++)
								buildStr.append(gold[i] + ";");
							buildStr.deleteCharAt(buildStr.length()-1);
							JLog.map.remove(p);
							JLog.map.put(p, buildStr.toString());
							break;
						case 5:
							val = im.getDisplayName().substring(15);
							valInt = Integer.parseInt(val);
							valInt += moveBy;
							im.setDisplayName(ChatColor.GRAY + "From Second: " + String.valueOf(valInt));
							updateFlame.setItemMeta(im);
							gold[5] = String.valueOf(valInt);
							for(int i = 0; i < gold.length; i++)
								buildStr.append(gold[i] + ";");
							buildStr.deleteCharAt(buildStr.length()-1);
							JLog.map.remove(p);
							JLog.map.put(p, buildStr.toString());
							break;
						case 14:
							val = im.getDisplayName().substring(13);
							valInt = Integer.parseInt(val);
							valInt += moveBy;
							im.setDisplayName(ChatColor.GRAY + "To Second: " + String.valueOf(valInt));
							updateFlame.setItemMeta(im);
							gold[11] = String.valueOf(valInt);
							for(int i = 0; i < gold.length; i++)
								buildStr.append(gold[i] + ";");
							buildStr.deleteCharAt(buildStr.length()-1);
							JLog.map.remove(p);
							JLog.map.put(p, buildStr.toString());
							break;
						}
					}
				}
				else if(slot >= 27 && slot < 36) {
					int index = Utility.getMaterialIndex(e.getInventory(), Material.MUSHROOM_SOUP);
					if(index != slot && index != -1) {
						ItemMeta saveMeta = e.getInventory().getItem(index).getItemMeta();
						ItemStack bowl = new ItemStack(Material.BOWL, 1);
						ItemMeta newMeta = bowl.getItemMeta();
						newMeta.setDisplayName(saveMeta.getDisplayName());
						bowl.setItemMeta(newMeta);
						e.getInventory().setItem(index, bowl);
						
						ItemStack mush = new ItemStack(Material.MUSHROOM_SOUP, 1);
						ItemMeta im = mush.getItemMeta();
						int am;
						switch(slot) {
						case 27:
							am = 10;
							break;
						default:
							am = (slot-27)*25;
							break;
						}
						im.setDisplayName(ChatColor.GRAY + "Limit: " + String.valueOf(am));
						mush.setItemMeta(im);
						e.getInventory().setItem(slot, mush);
						String analyzeStr = JLog.map.get(p);
						JLog.map.remove(p);
						StringBuilder buildStr = new StringBuilder();
						String[] gold = analyzeStr.split(";");
						gold[17] = String.valueOf(am);
						for(int i = 0; i < gold.length; i++)
							buildStr.append(gold[i] + ";");
						buildStr.deleteCharAt(buildStr.length()-1);
						JLog.map.put(p, buildStr.toString());
					}
				}
				else if(slot == 36) {
					ItemMeta im = e.getInventory().getItem(slot).getItemMeta();
					boolean val = im.getDisplayName().substring(32).equals("true") ? true : false;
					val = !val;
					e.getInventory().getItem(slot).setItemMeta(im);
					DyeColor newDC = val ? DyeColor.GREEN : DyeColor.RED;
					ItemStack newIS = new Wool(newDC).toItemStack(1);
					ItemMeta im2 = newIS.getItemMeta();
					im2.setDisplayName(ChatColor.GRAY + "Display Players Of Same UUID: " + String.valueOf(val));
					newIS.setItemMeta(im2);
					e.getInventory().setItem(slot, newIS);
					String analyzeStr = JLog.map.get(p);
					StringBuilder buildStr = new StringBuilder();
					String[] gold = analyzeStr.split(";");
					gold[14] = String.valueOf(val);
					for(int i = 0; i < gold.length; i++)
						buildStr.append(gold[i] + ";");
					buildStr.deleteCharAt(buildStr.length()-1);
					JLog.map.remove(p);
					JLog.map.put(p, buildStr.toString());
				}
				else if(slot == 37) {
					ItemMeta oIM = e.getInventory().getItem(37).getItemMeta();
					String val = oIM.getDisplayName().substring(12);
					String newVal = "";
					if(val.equals("all")) {
						ItemStack is = new ItemStack(Material.ICE, 1);
						ItemMeta im = is.getItemMeta();
						im.setDisplayName(ChatColor.GRAY + "Retrieve: messages");
						newVal = "messages";
						is.setItemMeta(im);
						e.getInventory().setItem(slot, is);
					}
					else if(val.equals("messages")) {
						ItemStack is = new ItemStack(Material.REDSTONE_BLOCK, 1);
						ItemMeta im = is.getItemMeta();
						im.setDisplayName(ChatColor.GRAY + "Retrieve: commands");
						newVal = "commands";
						is.setItemMeta(im);
						e.getInventory().setItem(slot, is);
					}
					else if(val.equals("commands")) {
						ItemStack is = new ItemStack(Material.GOLD_BLOCK, 1);
						ItemMeta im = is.getItemMeta();
						im.setDisplayName(ChatColor.GRAY + "Retrieve: all");
						newVal = "all";
						is.setItemMeta(im);
						e.getInventory().setItem(slot, is);
					}
					String analyzeStr = JLog.map.get(p);
					JLog.map.remove(p);
					StringBuilder buildStr = new StringBuilder();
					String[] gold = analyzeStr.split(";");
					gold[16] = String.valueOf(newVal);
					for(int i = 0; i < gold.length; i++)
						buildStr.append(gold[i] + ";");
					buildStr.deleteCharAt(buildStr.length()-1);
					Bukkit.broadcastMessage(buildStr.toString());
					JLog.map.put(p, buildStr.toString());
				}
				else if(slot == 43) {
					ItemMeta oIM = e.getInventory().getItem(43).getItemMeta();
					String val = oIM.getDisplayName().substring(12);
					if(val.charAt(0) == 'A') {
						ItemStack is = new ItemStack(Material.SNOW_BLOCK, 1);
						ItemMeta im = is.getItemMeta();
						im.setDisplayName(ChatColor.GRAY + "Order By: Descending Time");
						is.setItemMeta(im);
						e.getInventory().setItem(43, is);
					}
					else if(val.charAt(0) == 'D') {
						ItemStack is = new ItemStack(Material.LADDER, 1);
						ItemMeta im = is.getItemMeta();
						im.setDisplayName(ChatColor.GRAY + "Order By: Ascending Time");
						is.setItemMeta(im);
						e.getInventory().setItem(43, is);
					}
					String analyzeStr = JLog.map.get(p);
					JLog.map.remove(p);
					String newVal = val.charAt(0) == 'A' ? "DESC" : "ASC";
					String[] gold = analyzeStr.split(";");
					gold[18] = String.valueOf(newVal);
					StringBuilder buildStr = new StringBuilder();
					for(int i = 0; i < gold.length; i++)
						buildStr.append(gold[i] + ";");
					JLog.map.put(p, buildStr.toString());
				}
				else if(slot == 44) {
					ItemMeta oIM = e.getInventory().getItem(44).getItemMeta();
					String val = oIM.getDisplayName().substring(11);
					if(val.equals("true")) {
						ItemStack is = new Wool(DyeColor.RED).toItemStack(1);
						ItemMeta im = is.getItemMeta();
						im.setDisplayName(ChatColor.GRAY + "As Book: false");
						is.setItemMeta(im);
						e.getInventory().setItem(44, is);
					}
					else if(val.equals("false")) {
						ItemStack is = new ItemStack(Material.BOOK, 1);
						ItemMeta im = is.getItemMeta();
						im.setDisplayName(ChatColor.GRAY + "As Book: true");
						is.setItemMeta(im);
						e.getInventory().setItem(44, is);
					}
					String newVal = val.equals("true") ? "false" : "true";
					String analyzeStr = JLog.map.get(p);
					JLog.map.remove(p);
					StringBuilder buildStr = new StringBuilder();
					String[] gold = analyzeStr.split(";");
					gold[15] = String.valueOf(newVal);
					for(int i = 0; i < gold.length; i++)
						buildStr.append(gold[i] + ";");
					buildStr.deleteCharAt(buildStr.length()-1);
					JLog.map.put(p, buildStr.toString());
				}
				else if(slot == 40) {
					p.closeInventory();
					new Query(p);
				}
				//Bukkit.broadcastMessage(JLog.map.get(p));
			}
		}
	}
	
	
}
