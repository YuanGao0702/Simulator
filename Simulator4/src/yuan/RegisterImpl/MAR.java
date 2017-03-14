package yuan.RegisterImpl;

import yuan.IRegister.IRegister;

public class MAR implements IRegister {
	public static String MARvalue;

	public String getValue() {
		return MARvalue;
	}

	public void setValue(String value) {
		MAR.MARvalue = value;
	}

}
