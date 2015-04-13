/*
 * Copyright 2015 CrystalCraftMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gmail.www.woodrow73.jlog;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Alex Woodward (Jwood)
 */
public class JLog extends JavaPlugin implements Listener {
	
	/**Holds the current list of commands/chat logs to upload to the database (unloads at 21)*/
	public static ArrayList<String[]> queue = new ArrayList<String[]>();
	
	/**Holds the list of players with permissions to make db queries && /queryperms add|remove pname command*/
	public ArrayList<String> permsList = new ArrayList<String>();
	
	/**Holds map of players to current queries*/
	public static Map<Player, String> map = new HashMap<Player, String>();
	
	/**Holds username for database*/
	public static String username;
	
	/**Holds password for database*/
	public static String password;
	
	/**Holds ip for database*/
	public static String ip;
	
	/**Holds database name*/
	public static String db;
	
	public static JLog plugin;
	
	//String to query format:  
	//year;month;day;hour;minute;second;year;month;day;hour;minute;second;pname;keyword;uuidsimilarshow;asbook;retrieve;limit;asc
	
	public void onEnable() {
		plugin = this;
		this.getServer().getPluginManager().registerEvents(this, this);
		this.initializePermsList();
		this.initializeDBInfo();
		new LogVentory(this);
	}
	public void onDisable() {
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player)sender;
			if(hasPerms(p)) {
				if(label.equalsIgnoreCase("query") && args.length == 2) {
					
					for(Player ps : map.keySet()) {
						if(ps.getName().equals(p.getName()))
							map.remove(p);
					}
					
					String currentDate = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
					StringBuilder sb = new StringBuilder();
					sb.append(currentDate.substring(0, 4) + ";" + currentDate.substring(4, 6) +
							";" + currentDate.substring(6, 8) + ";" + "14;00;00;" +
							currentDate.substring(0, 4) + ";" + currentDate.substring(4, 6) +
							";" + currentDate.substring(6, 8) + ";" + "14;10;00;" + args[0] + ";" +
							args[1] + ";false;true;all;200;ASC");
					map.put(p, sb.toString());
					new CustomizeQuery(p, currentDate, this);
					return true;
				}
				else if(label.equalsIgnoreCase("queryperms") && (args.length == 2 || args.length == 0)) {
					if(args.length == 0) {
						if(permsList.size() == 0) {
							p.sendMessage(ChatColor.BLUE + "Noone is currently in the permission list for JLog. " +
									"\nHere is the other way to use this command:");
							return false;
						}
						p.sendMessage(ChatColor.BLUE + "Here is a list of all people with JLog Perms:");
						for(String names : permsList)
							p.sendMessage(ChatColor.GOLD + names);
						return true;
					}
					if(args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {
						boolean isAdd = args[0].equalsIgnoreCase("add") ? true : false;
						boolean existsInList = false;
						for(String names : permsList) {
							if(args[1].equals(names))
								existsInList = true;
						}
						if(isAdd) {
							if(existsInList) {
								p.sendMessage(ChatColor.RED + "Error; " + ChatColor.AQUA + args[1] +
										ChatColor.RED + " already exists in the perms list.");
								return true;
							}
							permsList.add(args[1]);
							updatePermsList();
							return true;
						}
						else {
							if(!existsInList) {
								p.sendMessage(ChatColor.RED + "Error; " + ChatColor.AQUA + args[1] + ChatColor.RED +
										" does not exist in the permslist.  Here is the existing perms list:");
								for(String names : permsList)
									p.sendMessage(ChatColor.GOLD + names);
								return true;
							}
							else {
								permsList.remove(args[1]);
								p.sendMessage(ChatColor.GREEN + "Player " + ChatColor.BLUE + args[1] + ChatColor.GREEN +
										" successfully removed from permslist.");
								return true;
							}
						}
					}
					else {
						p.sendMessage(ChatColor.RED + "Error; your first argument was not \'add\' or \'remove\'");
						return false;
					}
				}
				else
					return false;
			}
			else {
				p.sendMessage(ChatColor.RED + "Error; you do not have permission for this command.");
				return true;
			}
		}
		else {
			return false;
		}
	}
	
	@EventHandler
	public void logChat(AsyncPlayerChatEvent e) {
		new SuperLog(e.getPlayer(), e.getMessage(), false);
	}
	
	
	@EventHandler
	public void logCommands(PlayerCommandPreprocessEvent e) {
		new SuperLog(e.getPlayer(), e.getMessage(), true);
	}
	
	/**Tests whether a player is an op or has perms
	 * @param p the player we're testing
	 * @return true if player has perms (op or is in permsList)
	 */
	public boolean hasPerms(Player p) {
		if(p.isOp())
			return true;
		for(String name : permsList) {
			if(p.getName().equals(name))
				return true;
		}
		return false;
	}
	
	/**Will update the permslist file*/
	public void updatePermsList() {
		File file = new File("JLogFiles\\perms.ser");
		if(file.exists())
			file.delete();
		if(!(new File("JLogFiles").exists()))
			new File("JLogFiles").mkdir();
		try{
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(permsList);
			oos.close();
			fos.close();
		}catch(IOException e) { e.printStackTrace(); }
	}
	
	/**Will initialize the permslist file*/
	public void initializePermsList() {
		File file = new File("JLogFiles\\perms.ser");
		if(file.exists()) {
			try{
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				permsList = (ArrayList<String>)ois.readObject();
				ois.close();
				fis.close();
			}catch(IOException e) { e.printStackTrace();
			}catch(ClassNotFoundException e) { e.printStackTrace(); }
		}
	}
	
	/**Initializes the database connection data*/
	public void initializeDBInfo() {
		File file = new File("JLogFiles\\DatabaseLogin.txt");
		if(file.exists()) {
			try{
				Scanner in = new Scanner(file);
				String u = in.nextLine();
				String p = in.nextLine();
				String i = in.nextLine();
				String d = in.nextLine();
				in.close();
				int cut1 = u.indexOf(":");
				int cut2 = p.indexOf(":");
				int cut3 = i.indexOf(":");
				int cut4 = d.indexOf(":");
				username = u.substring(cut1+1);
				username = username.trim();
				password = p.substring(cut2+1);
				password = password.trim();
				ip = i.substring(cut3+1);
				ip = ip.trim();
				db = d.substring(cut4+1);
				db = db.trim();
				this.getLogger().info("Database Info: " + username + "**" + password + "**" + ip + "**" + db);
			}catch(IOException e) { e.printStackTrace(); }
		}
		else {
			if(!new File("JLogFiles").exists())
				new File("JLogFiles").mkdir();
			try{
				PrintWriter pw = new PrintWriter(file);
				pw.println("Username: ");
				pw.flush();
				pw.println("Password: ");
				pw.flush();
				pw.println("IP: ");
				pw.flush();
				pw.println("Database Name: ");
				pw.flush();
				pw.close();
			}catch(IOException e) { e.printStackTrace(); }
		}
	}
}
