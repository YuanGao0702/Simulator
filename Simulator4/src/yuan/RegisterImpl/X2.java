package yuan.RegisterImpl;

import yuan.IRegister.IRegister;

public class X2 implements IRegister {

	public static String X2value;

	public String getValue() {
		return X2value;
	}

	public void setValue(String value) {
		X2.X2value = value;
	}

}
