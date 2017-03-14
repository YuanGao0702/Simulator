package yuan.RegisterImpl;

import yuan.IRegister.IRegister;

public class X1 implements IRegister {

	public static String X1value;

	public String getValue() {
		return X1value;
	}

	public void setValue(String value) {
		X1.X1value = value;
	}

}
