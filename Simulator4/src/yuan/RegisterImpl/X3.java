package yuan.RegisterImpl;

import yuan.IRegister.IRegister;

public class X3 implements IRegister {

	public static String X3value;

	public String getValue() {
		return X3value;
	}

	public void setValue(String value) {
		X3.X3value = value;
	}

}
