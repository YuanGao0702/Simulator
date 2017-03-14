package yuan.ROM;

import yuan.RAM.RAM;
import yuan.control.Control;
import yuan.instruction.Instruction;

public class RomLoader {
	
	RAM ram = new RAM();
	public static void transfer(){
		for(long i = 0;i<ROM.currentADDR;i++){
			Control.decode(ROM.getValue(i));
			RAM.addValue(Instruction.command);
		}
	}
}
