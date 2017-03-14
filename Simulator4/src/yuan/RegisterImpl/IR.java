package yuan.RegisterImpl;

import yuan.IRegister.IRegister;

public class IR implements IRegister {
	public static String IRvalue;
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return IRvalue;
	}

	@Override
	public void setValue(String value) {
		// TODO Auto-generated method stub
		IR.IRvalue = value;
	}
	

}
