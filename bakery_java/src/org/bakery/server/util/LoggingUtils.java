package org.bakery.server.util;

import java.util.Iterator;
import java.util.Map;

public class LoggingUtils {
	
	/**
	 * Dumps whole object of Map<String, String> type to logger, if one of the
	 * parameters has known, as omitable, then no log records will be created at all. 
	 * 
	 * @param map Map<String, String> to dump
	 */		
	public static String dumpStringMapIntoString(Map<String, ? extends Object> map){
		StringBuilder st = new StringBuilder("Logged map: \r\n");
		String result = null;
		try{
			Iterator<String> it = map.keySet().iterator();
			while (it.hasNext()){
				String name = (String) it.next();				

					Object value = map.get(name);
					if (value instanceof String[]){
						
						// logging String array
						st.append("[").append(name).append("]=(");
						String[] arr = (String[]) value;
						for (int i=0; i<arr.length; i++){
							st.append(arr[i]);
							if (i!=arr.length-1) st.append(",");
						}
						st.append(")").append("\r\n");
					} else {
						
						// logging other objects
						st.append("[").append(name).append("]=").append(value).append("\r\n");
					}
				
			}	
			
			result = st.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}
