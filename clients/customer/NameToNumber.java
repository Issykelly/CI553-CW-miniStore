package clients.customer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import dbAccess.StockR;
import middle.StockException;

public class NameToNumber extends HashMap<String, String>{
		public NameToNumber() throws StockException {
			StockR db = new StockR();
			String[][] nn = db.nameAndNumber();
			
			if (nn != null) {
	            for (String[] entry : nn) {
	                if (entry.length == 2) {
	                    put(entry[0], entry[1]);
	                }
	            }
	        }
		}
			
		public <T, E> T getNumberByName(Map<T, E> map, E value) {
			for (Entry<T, E> entry : map.entrySet()) {
				String[] keywords = entry.getValue().toString().split(",");
				for (String word : keywords) {
					if (Objects.equals(value,  word)) {
						return entry.getKey();
					}}
				}
			return null;
			}
}
