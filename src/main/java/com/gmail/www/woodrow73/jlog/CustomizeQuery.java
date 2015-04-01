package com.gmail.www.woodrow73.jlog;
 
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Wool;
 
public class CustomizeQuery {
        private ItemStack as[] = new ItemStack[45];
        private String defaults;
        private Player p;
        private JLog plugin;
        
        public CustomizeQuery(Player bob, String defaults, JLog jl) {
        	plugin = jl;
        	p=bob;
        	this.defaults=defaults;
            this.createAs();
            Inventory inv = Bukkit.getServer().createInventory(null, 45, "Customize Query");
            inv.setContents(as);
            bob.openInventory(inv);
        }
        
        public void createAs() {
                for(int i = 0; i < as.length; i++) {
                        if (i == 0 || i == 9) {
                                as[i] = new Wool(DyeColor.WHITE).toItemStack(1);
                                ItemMeta im = as[i].getItemMeta();
                                if(i == 0)
                                	im.setDisplayName(ChatColor.GRAY + "From Year: " + String.valueOf(defaults.substring(0, 4)));
                                else
                                	im.setDisplayName(ChatColor.GRAY + "To Year: " + String.valueOf(defaults.substring(0, 4)));
                                as[i].setItemMeta(im);
                        }
                        else if (i == 1 || i == 10) {
                                as[i] = new Wool(DyeColor.WHITE).toItemStack(1);
                                ItemMeta im2 = as[i].getItemMeta();
                                if(i == 1)
                                	im2.setDisplayName(ChatColor.GRAY + "From Month: " + String.valueOf(defaults.substring(4, 6)));
                                else
                                	im2.setDisplayName(ChatColor.GRAY + "To Month: " + String.valueOf(defaults.substring(4, 6)));
                                as[i].setItemMeta(im2);
                        }
                        else if (i == 2 || i == 11) {
                                as[i] = new Wool(DyeColor.WHITE).toItemStack(1);
                                ItemMeta im3 = as[i].getItemMeta();
                                if(i == 2)
                                	im3.setDisplayName(ChatColor.GRAY + "From Day: " + String.valueOf(defaults.substring(6, 8)));
                                else
                                	im3.setDisplayName(ChatColor.GRAY + "To Day: " + String.valueOf(defaults.substring(6, 8)));
                                as[i].setItemMeta(im3);
                        }
                        else if (i == 3 || i == 12) {
                                as[i] = new Wool(DyeColor.WHITE).toItemStack(1);
                                ItemMeta im3 = as[i].getItemMeta();
                                if(i == 3)
                                	im3.setDisplayName(ChatColor.GRAY + "From Hour: " + String.valueOf(defaults.substring(9, 11)));
                                else
                                	im3.setDisplayName(ChatColor.GRAY + "To Hour: " + String.valueOf(defaults.substring(9, 11)));
                                as[i].setItemMeta(im3);
                        }
                        else if (i == 4 || i == 13) {
                                as[i] = new Wool(DyeColor.WHITE).toItemStack(1);
                                ItemMeta im = as[i].getItemMeta();
                                if(i==4)
                                	im.setDisplayName(ChatColor.GRAY + "From Minute: " + String.valueOf(00));
                                else
                                	im.setDisplayName(ChatColor.GRAY + "To Minute: " + String.valueOf(10));
                                as[i].setItemMeta(im);
                        }
                        else if (i == 5 || i == 14) {
                                as[i] = new Wool(DyeColor.WHITE).toItemStack(1);
                                ItemMeta im = as[i].getItemMeta();
                                if(i == 5)
                                	im.setDisplayName(ChatColor.GRAY + "From Second: " + String.valueOf(00));
                                else
                                	im.setDisplayName(ChatColor.GRAY + "To Second: " + String.valueOf(00));
                                as[i].setItemMeta(im);
                        }
                        else if (i == 7 || i == 8) {
                                as[i] = new ItemStack(Material.FISHING_ROD , 1);
                                ItemMeta im = as[i].getItemMeta();
                                if(i == 7)
                                	im.setDisplayName(ChatColor.GRAY + "Big Up Arrow");
                                else
                                	im.setDisplayName(ChatColor.GRAY + "Small Up Arrow");
                                as[i].setItemMeta(im);
                        }
                        else if (i == 16 || i == 17) {
                                as[i] = new ItemStack(Material.COOKED_FISH, 1);
                                ItemMeta im = as[i].getItemMeta();
                                if(i == 16)
                                	im.setDisplayName(ChatColor.GRAY + "Big Down Arrow");
                                else
                                	im.setDisplayName(ChatColor.GRAY + "Small Down Arrow");
                                as[i].setItemMeta(im);
                        }
                        else if(i >= 27 && i < 35) {
                        	int amount = i == 27 ? 10 : (i-27)*25;
                        	as[i] = new ItemStack(Material.BOWL, 1);
                        	ItemMeta im = as[i].getItemMeta();
                        	im.setDisplayName(ChatColor.GRAY + "Limit: " + String.valueOf(amount));
                        	as[i].setItemMeta(im);
                        }
                        else if(i == 35) {
                        	as[i] = new ItemStack(Material.MUSHROOM_SOUP, 1);
                        	ItemMeta im = as[i].getItemMeta();
                        	im.setDisplayName(ChatColor.GRAY + "Limit: " + String.valueOf(200));
                        	as[i].setItemMeta(im);
                        }
                        else if(i == 36) {
                                as[i] = new Wool(DyeColor.RED).toItemStack(1);
                                ItemMeta im = as[i].getItemMeta();
                                im.setDisplayName(ChatColor.GRAY + "Display Players Of Same UUID: false");
                                as[i].setItemMeta(im);
                        }
                        else if(i == 40) {
                        	as[i] = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
                        	SkullMeta sm = (SkullMeta)as[i].getItemMeta();
                        	sm.setDisplayName(ChatColor.GOLD + "execute query");
                        	String head = p.getName();
                        	boolean ccmc = false;
                        	boolean junior = false;
                        	boolean ccmc_2 = false;
                        	for(Player pp : new ArrayList<Player>(plugin.getServer().getOnlinePlayers())) {
                        		if(pp.getName().equals("CrystalCraftMC")) {
                        			ccmc = true;
                        		}
                        		if(pp.getName().equals("x76junior")) {
                        			junior = true;
                        		}
                        		if(pp.getName().equals("CCMC_2"))
                        			ccmc_2 = true;
                        	}
                        	if(ccmc) {
                        		sm.setOwner("CrystalCraftMC");
                        	}
                        	else if(junior) {
                        		sm.setOwner("x76junior");
                        	}
                        	else if(ccmc_2) {
                        		sm.setOwner("CCMC_2");
                        	}
                        	else {
                        		sm.setOwner(p.getName());
                        	}
                        	as[i].setItemMeta(sm);
                        }
                        else if(i == 37) {
                                as[i] = new ItemStack(Material.GOLD_BLOCK, 1);
                                ItemMeta im = as[i].getItemMeta();
                                im.setDisplayName(ChatColor.GRAY + "Retrieve: all");
                                as[i].setItemMeta(im);
                        }
                        else if(i == 43) {
                        	as[i] = new ItemStack(Material.LADDER, 1);
                        	ItemMeta im = as[i].getItemMeta();
                        	im.setDisplayName(ChatColor.GRAY + "Order By: Ascending Time");
                        	as[i].setItemMeta(im);
                        }
                        else if(i == 44) {
                                as[i] = new ItemStack(Material.BOOK, 1);
                                ItemMeta im = as[i].getItemMeta();
                                im.setDisplayName(ChatColor.GRAY + "As Book: true");
                                as[i].setItemMeta(im);
                        }
                }
        }
}