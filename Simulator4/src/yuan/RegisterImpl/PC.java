package yuan.RegisterImpl;

import yuan.IRegister.IRegister;

public class PC implements IRegister {

	public static String PCvalue= "000000000000000000";

	public String getValue() {
		return PCvalue;
	}

	public void setValue(String value) {
		PC.PCvalue = value;
	}
	
	public static void PCplusOne(){
		int value = Integer.valueOf(PCvalue, 2);
		value = value + 1;
		PCvalue = addZero(Integer.toBinaryString(value),18);
		//GUI.pcField.setText(PCvalue);
		//GUI.pcFieldD.setText(Integer.toString(Integer.valueOf(PCvalue, 2)));
	}
	
	public static String addZero(String binaryString, int length) {
		int l=binaryString.length();
		for(int i=1;i<=(length-l);i++)
		{
			binaryString="0"+binaryString;
		}
		return binaryString;
	}
}
