package yuan.RegisterImpl;

import yuan.IRegister.IRegister;

public class MDR implements IRegister {

	public static String MDRvalue;

	public String getValue() {
		return MDRvalue;
	}

	public void setValue(String value) {
		MDR.MDRvalue = value;
	}
}
