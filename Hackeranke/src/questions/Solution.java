package questions;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Solution {
	
	private static String eventsValidation (String [] events) {
		boolean req1, req2, req3 = true;
		String result = null;
		ArrayList master = new ArrayList();
		String [] item = null;
		String [] nextItem = null;

		
		for (int i = 0; i<events.length;i++) {
			item = events[i].split(",");
			master.add(item);
			System.out.println(item[3]);
		}
		

		req1 = requirementValidation1(master);
		req2 = requirementValidation23(master, "sync", "pose","unsync");
		req3 = requirementValidation23(master, "connected", "orientation","disconnected");
		
		if (req1 && req2 && req3)
			result = "GOOD";
		else
			result = "BAD";
	
		
		return result;
	}

	public static boolean requirementValidation23(ArrayList master, String event1, String event2, String event3) {
		boolean result = true;
		String [] item = null;
		boolean eventCheck1 = false;
		boolean eventCheck2 = false;
		boolean eventCheck3 = false;
		int position1 = -1;
		int position2 = -1;
		
		for (int m=0;m<master.size()-1;m++) {
			
			item = (String[]) master.get(m);
			String event = item[3];
			
			if(!eventCheck1) {
				if (event.equals(event1)) {
					eventCheck1 = true;
					position1 = m;
				}
			}
			
			if(!eventCheck2) {
				if (event.equals(event2)) {
					eventCheck2 = true;
					position2 = m;
				}
			}
			
			if(!eventCheck3)
				eventCheck3 = (event.equals(event3));
			
			if (eventCheck3) {
				if (eventCheck1 && eventCheck2) {
					if (position2 < position1) {
						result = false;
					}
				}
				else if (!eventCheck1){
					result = false;
				}
				
			}
				
		}
		
		if (!eventCheck3) {
			
			
			if (eventCheck2)
				result = false;
			else
			if (eventCheck1)
				result = false;
			
		}
		
		return result;
		
	}
	
	public static boolean requirementValidation1(ArrayList master) {
		boolean result = true;

		String [] item = null;
		String [] nextItem = null;
		for (int j=0;j<master.size()-1;j++) {
			item = (String[]) master.get(j);
			nextItem = (String[]) master.get(j+1);
			String t1 = item[1];
			String t2 = nextItem[1];
			boolean checkTimestamp = timestampValidation(t1,t2);
			if (!checkTimestamp) {
				result = false;
				break;
			}
		}
		return result;
	}

	public static boolean timestampValidation(String t1, String t2) {
		boolean req1 = false;
		Long timestamp1 = Long.parseLong(t1);
		Long timestamp2 = Long.parseLong(t2);
		
		Timestamp time1 = new Timestamp(timestamp1);
		Timestamp time2 = new Timestamp(timestamp2);
		
		if (time1.before(time2) || time1.equals(time2)) {
			req1 = true;
		}
		
		return req1;
		
	}
	
	
public static void main(String[] args) throws IOException {
	String [] events = {
			"timestamp,1529957297000,event,start",
			"timestamp,1530032897000,event,sync",
			"timestamp,1530119297000,event,pose",
			"timestamp,1530205697000,event,connected",
			"timestamp,1530292097000,event,orientation",
			"timestamp,1530378497000,event,unsync",
			"timestamp,1530378497000,event,disconnected",
			"timestamp,1530464897000,event,ready"
			
	};
	
	System.out.println(eventsValidation (events));
    
    }
}
