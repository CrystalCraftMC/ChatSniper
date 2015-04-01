package com.gmail.www.woodrow73.jlog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Logger implements Runnable {
	ArrayList<String[]> al = new ArrayList<String[]>();
	private Connection c;
	private PreparedStatement ps;
	private Thread t1;
	public Logger(ArrayList<String[]> al) {
		this.al = al;
		t1 = new Thread(this);
		t1.start();
	}
	
	public synchronized void run() {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://" + JLog.ip + "/" + JLog.db,
					JLog.username, JLog.password);
			PreparedStatement pre = c.prepareStatement("SHOW TABLES Like 'CrystalLog';");
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
		}catch(ClassNotFoundException e) { e.printStackTrace();
		}catch(SQLException e) { e.printStackTrace(); }
		/*String stm = "INSERT INTO ChatLog (Year, Month, Day, Hour,"
			+ " Minute, Second, Name, Message, UUID, isCommand) " +
			"VALUES (" + stms.get(i)[0] + ", " + stms.get(i)[1] + ", " +
			stms.get(i)[2] + ", " + stms.get(i)[3] + ", " +
			stms.get(i)[4] + ", " + stms.get(i)[5] + ", \'" +
			stms.get(i)[6] + "\', \'" + stms.get(i)[7] + "\', \'" +
			stms.get(i)[8] + "\', " + stms.get(i)[9] + ");";*/
		
		for(int i = 0; i < al.size(); i++) {
			String timeStamp = al.get(i)[0] + "-" + al.get(i)[1] + "-" +
					al.get(i)[2] + " " + al.get(i)[3] + ":" + al.get(i)[4] + ":" + al.get(i)[5];
			al.get(i)[9] = al.get(i)[9].equals("true") ? String.valueOf(1) : String.valueOf(0);
			String stm = "INSERT INTO CrystalLog (Time, Name, Message, UUID, isCommand) " +
			"VALUES (\'" + timeStamp + "\', \'" +
			al.get(i)[6] + "\', \'" + al.get(i)[7] + "\', \'" +
			al.get(i)[8] + "\', " + al.get(i)[9] + ");";
			try{
				ps = c.prepareStatement(stm);
				ps.executeUpdate();
			}catch(SQLException e) { e.printStackTrace(); }
		}
		try{
			ps.close();
			c.close();
			t1.join();
		}catch(InterruptedException e) { e.printStackTrace();
		}catch(SQLException e) { e.printStackTrace(); }
	}
}
