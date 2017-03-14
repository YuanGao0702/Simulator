package yuan.RAM;

import java.util.HashMap;
import java.util.Map;

public class RAM {
	public static Map<Long,String> ram = new HashMap<Long,String>();
	public static long address;
	public static long currentADDR =0;
	public static String value;
	
	
	public static String getValue(long address){
		return (String) ram.get(address);
	}
	
	public static void addValue(String value){
		ram.put(Long.valueOf(Long.toBinaryString(currentADDR)),value);
		currentADDR++;
	}
	public static void addValueByADDR(Long addr,String value){
		ram.remove(addr);
		ram.put(addr, value);
	}
	public void reset(){
		ram.clear();
	}
}
