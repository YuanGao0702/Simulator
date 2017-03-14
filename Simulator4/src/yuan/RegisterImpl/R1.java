package yuan.RegisterImpl;

import yuan.IRegister.IRegister;

public class R1 implements IRegister {

	public static String R1value;

	public String getValue() {
		return R1value;
	}

	public void setValue(String value) {
		R1.R1value = value;
	}

}
