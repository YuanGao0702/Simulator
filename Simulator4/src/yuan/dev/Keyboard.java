package yuan.dev;

public class Keyboard {
	public static int KeyboardId;
	public static String KeyboardValue="";

	public static int getKeyboardId() {
		return KeyboardId;
	}

	public static void setKeyboardId(int keyboardId) {
		KeyboardId = keyboardId;
	}

	public void setValue(String str) {
		Keyboard.KeyboardValue = str;
	}

	public String getValue() {
		return KeyboardValue;
	}

}
