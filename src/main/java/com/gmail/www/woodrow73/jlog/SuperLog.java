package com.gmail.www.woodrow73.jlog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.bukkit.entity.Player;

public class SuperLog {
	public SuperLog(Player p, String msg, boolean isCommand) {
		if(p != null) {
			String dateLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			String father[] = new String[10];
			father[0] = (dateLog.substring(0, 4));
			father[1] = (dateLog.substring(4, 6));
			father[2] = (dateLog.substring(6, 8));
			father[3] = (dateLog.substring(9, 11));
			father[4] = (dateLog.substring(11, 13));
			father[5] = (dateLog.substring(13));
			father[6] = (p.getName());
			father[7] = (msg);
			father[8] = (p.getUniqueId().toString());
			String cmd = isCommand ? "true" : "false";
			father[9] = (cmd);
			JLog.queue.add(father);
			if(JLog.queue.size() > 20) {
				ArrayList<String[]> al = new ArrayList<String[]>();
				for(int i = 0; i < JLog.queue.size(); i++) {
					String toAdd[] = new String[10];
					for(int ii = 0; ii < 10; ii++) {
						toAdd[ii] = JLog.queue.get(i)[ii];
					}
					al.add(toAdd);
				}
				new Logger(al);
				JLog.queue.clear();
			}
		}
	}
}
