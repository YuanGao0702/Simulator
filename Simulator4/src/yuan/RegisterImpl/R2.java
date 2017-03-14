package yuan.RegisterImpl;

import yuan.IRegister.IRegister;

public class R2 implements IRegister {

	public static String R2value;

	public String getValue() {
		return R2value;
	}

	public void setValue(String value) {
		R2.R2value = value;
	}
}
