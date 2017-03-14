package yuan.Bus;

import yuan.RegisterImpl.IR;
import yuan.RegisterImpl.MAR;
import yuan.RegisterImpl.MDR;
import yuan.RegisterImpl.PC;

public class CpuBus {
	public static String CpuValue;

	public static void getValue(String str) {
		switch (str) {
		case "PC":
			 CpuValue = PC.PCvalue;
		case "MDR":
			CpuValue = MDR.MDRvalue;
		}
	}
	public static void setValue(String str){
		switch(str){
		case "MAR":
			MAR.MARvalue = CpuValue;
		case "IR":
			IR.IRvalue = CpuValue;
		}
	}
}
