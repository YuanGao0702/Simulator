package yuan.RegisterImpl;

import yuan.IRegister.IRegister;

public class R0 implements IRegister {

	public static String R0value;

	public String getValue() {
		return R0value;
	}

	public void setValue(String value) {
		R0.R0value = value;
	}
	
}
