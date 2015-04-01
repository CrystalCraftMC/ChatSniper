package com.gmail.www.woodrow73.jlog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;


public class Query implements Runnable {
	private Player p;
	private String gold[];
	private Connection c;
	private PreparedStatement ps;
	private ResultSet rs;
	private Thread t1;
	
	
	public Query(Player p) {
		this.p = p;
		gold = JLog.map.get(p).split(";");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://" + JLog.ip + "/" + JLog.db,
					JLog.username, JLog.password);
			
			PreparedStatement pre = c.prepareStatement("SHOW TABLES LIKE 'CrystalLog';");
			ResultSet rs = pre.executeQuery();
			
			boolean exists = false;
			if(rs.next()) {
				exists = true;
			}
			rs.close();
			pre.close();
			if(!exists) {
				pre = c.prepareStatement("CREATE TABLE CrystalLog(Time TimeStamp, Name Text(255),"
						+ " Message Text(255), UUID Text(255), isCommand boolean);");
				pre.execute();
				pre.close();
			}
		}catch(SQLException e) { e.printStackTrace();
		}catch(ClassNotFoundException e) { e.printStackTrace(); }
		t1 = new Thread(this);
		t1.start();
	}
	
	public void run() {
		try{
			p.sendMessage(ChatColor.DARK_PURPLE + "Query Executing");
			String keyStr = "";
			String nameStr = "";
			String cmdStr = "";
			String startTime = "";
			String endTime = "";
			startTime = gold[0] + "-" + gold[1] + "-" + gold[2] + " " + gold[3] + ":" +
					gold[4] + ":" + gold[5];
			endTime =  gold[6] + "-" + gold[7] + "-" + gold[8] + " " + gold[9] + ":" +
					gold[10] + ":" + gold[11];
			StringBuilder query = new StringBuilder("SELECT Time, Name, Message FROM CrystalLog WHERE " +
					"Time > \'" + startTime + "\' AND Time < \'" + endTime + "\'");
			
			if(gold[16].equals("messages")) {
				cmdStr = " AND isCommand = \'false\'";
			}
			else if(gold[16].equals("commands")) {
				cmdStr = " AND isCommand = \'true\'";
			}
			
			if(!gold[13].equals("null")) {
				keyStr = " AND Message Like(\'%" + gold[13] + "%\')";
			}
			
			if(!gold[12].equals("all")) {
				nameStr = " AND Name LIKE(\'%" + gold[12] + "%\')";
				if(gold[14].equals("true")) {
					ps = c.prepareStatement("SELECT UUID FROM CrystalLog WHERE " +
							"Name LIKE(\'%" + gold[12] + "%\') LIMIT 1");
					rs = ps.executeQuery();
					nameStr = " AND UUID LIKE(\'" + rs.getString("UUID") + "\')";
					ps.close();
					rs.close();
				}
			}
			
			query.append(cmdStr);
			query.append(keyStr);
			query.append(nameStr);
			query.append(" ORDER BY Time " + gold[18]);
			query.append(" LIMIT " + gold[17]);
			
			ps = c.prepareStatement(query.toString());
			rs = ps.executeQuery();
			
			ArrayList<String[]> output = new ArrayList<String[]>();
			int accumulator = 0;
			while(rs.next()) {
				output.add(new String[3]);
				output.get(accumulator)[0] = rs.getTimestamp("Time").toString();
				int dotLocation = output.get(accumulator)[0].indexOf(".");
				if(dotLocation != -1)
					output.get(accumulator)[0] = output.get(accumulator)[0].substring(0, dotLocation);
				output.get(accumulator)[1] = rs.getString("Name");
				output.get(accumulator)[2] = rs.getString("Message");
				
				accumulator++;
			}
			
			if(gold[15].equals("false")) {
				for(int i = 0; i < output.size(); i++) {
					final String time = output.get(i)[0];
					final String name = output.get(i)[1];
					final String message = output.get(i)[2];
					JLog.plugin.getServer().getScheduler().scheduleSyncDelayedTask(JLog.plugin, new Runnable() {
						public void run() {
							p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.LIGHT_PURPLE + time +
									ChatColor.DARK_GRAY + "]" + ChatColor.BLUE + name + ChatColor.DARK_GRAY +
									": " + ChatColor.GOLD + message);
						}
					}, (long)(i*10+20));
				}
			}
			else {
				ItemStack book[] = new ItemStack[4];
				BookMeta[] bm = new BookMeta[4];
				for(int i = 0; i < 4; i++) {
					book[i] = new ItemStack(Material.WRITTEN_BOOK, 1);
					bm[i] = (BookMeta)book[i].getItemMeta();
					bm[i].setAuthor("Jwood9198");
					bm[i].setTitle("LogBook " + String.valueOf(i+1));
					
				}
				boolean firstB = false, secondB = false, thirdB = false, fourthB = false;
				for(int i = 0; i < output.size(); i++) {
					if(i < 50) {
						bm[0].addPage("["+output.get(i)[0] + "]" + output.get(i)[1] + ": " +
								output.get(i)[2]);
						firstB = true;
					}
					else if(i < 100) {
						bm[1].addPage("["+output.get(i)[0] + "]" + output.get(i)[1] + ": " +
								output.get(i)[2]);
						secondB = true;
					}
					else if(i < 150) {
						bm[2].addPage("["+output.get(i)[0] + "]" + output.get(i)[1] + ": " +
								output.get(i)[2]);
						thirdB = true;
					}
					else {
						bm[3].addPage("["+output.get(i)[0] + "]" + output.get(i)[1] + ": " +
								output.get(i)[2]);
						fourthB = true;
					}
				}
				for(int i = 0; i < 4; i++) {
					book[i].setItemMeta(bm[i]);
					if((i == 0 && firstB) || (i == 1 && secondB) ||
							(i == 2 && thirdB) || (i == 3 && fourthB)) {
						p.getInventory().addItem(book[i]);
					}
				}
			}
			
			ps.close();
			rs.close();
			c.close();
			t1.join();
		}catch(SQLException e) { e.printStackTrace();
		}catch(InterruptedException e) { e.printStackTrace(); }
	}
}
