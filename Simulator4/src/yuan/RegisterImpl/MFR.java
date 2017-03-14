package yuan.RegisterImpl;

import yuan.IRegister.IRegister;

public class MFR implements IRegister {
	public String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
