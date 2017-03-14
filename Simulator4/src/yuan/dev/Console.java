package yuan.dev;

import yuan.GUI.GUI;

public class Console {
	public static int ConsoleId;
	public static String Consolevalue;

	public static int getConsoleId() {
		return ConsoleId;
	}

	public static void setConsoleId(int consoleId) {
		ConsoleId = consoleId;
	}

	public String getValue() {
		return Consolevalue;
	}

	public void setValue(String value) {
		Console.Consolevalue = value;
	}

	public void output() {
		GUI.screen.append(Consolevalue);
	}
}
