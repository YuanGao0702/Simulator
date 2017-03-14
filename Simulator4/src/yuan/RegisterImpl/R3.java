package yuan.RegisterImpl;

import yuan.IRegister.IRegister;

public class R3 implements IRegister {

	public static String R3value;

	public String getValue() {
		return R3value;
	}

	public void setValue(String value) {
		R3.R3value = value;
	}

}
