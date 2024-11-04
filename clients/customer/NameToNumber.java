package clients.customer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NameToNumber extends HashMap<String, String>{
		
		NameToNumber(){
			put("0001", "tv,television,tele,telly");
			put("0002", "radio,dab");
			put("0003", "toaster,toast,bread");
			put("0004", "watch,time");
			put("0005", "camera,picture,video");
			put("0006", "music player,mp3,taylor swift");
			put("0007", "usb drive,usb stick,usb");}
			
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
