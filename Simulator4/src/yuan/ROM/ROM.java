package yuan.ROM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ROM {
	public static Map<Long, String> rom = new HashMap<Long, String>();
	public static long address;
	public static long currentADDR = 0;
	public static String value;

	public static void init() {
		File file = new File("ROM.txt");
		BufferedReader reader = null;
		try {

			reader = new BufferedReader(new FileReader(file));
			String content = null;
			int line = 1;
			while ((content = reader.readLine()) != null) {
				ROM.addValue(content);
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}

	}

	public static String getValue(long address) {
		return (String) rom.get(address);
	}

	// add data sequentially,by address ++
	public static void addValue(String value) {
		rom.put(currentADDR, value);
		currentADDR++;
	}

	public static void addValueByADDR(Long addr, String value) {
		rom.remove(addr);
		rom.put(addr, value);
	}

	public void reset() {
		rom.clear();
	}
}
