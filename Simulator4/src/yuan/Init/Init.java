package yuan.Init;

import yuan.GUI.GUI;
import yuan.IRegister.IRegister;
import yuan.ISA.ISA;
import yuan.RAM.RAM;
import yuan.ROM.ROM;
import yuan.ROM.RomLoader;
import yuan.RegisterImpl.*;
import yuan.dev.CardReader;
import yuan.dev.Console;
import yuan.dev.DeviceList;
import yuan.dev.Keyboard;

public class Init {
	IRegister ir = new IR();
	IRegister mar = new MAR();
	IRegister mdr = new MDR();
	IRegister pc = new PC();
	IRegister r0 = new R0();
	IRegister r1 = new R1();
	IRegister r2 = new R2();
	IRegister r3 = new R3();
	DeviceList devList = new DeviceList();
	Console console = new Console();
	Keyboard keyboard = new Keyboard();
	CardReader cardReader = new CardReader();
	RAM ram = new RAM();
	ISA isa = new ISA();
	X1 x1 = new X1();

	public void reset() {
		R0.R0value = "0";
		R1.R1value = "0";
		R2.R2value = "0";
		R3.R3value = "0";
		X1.X1value = "0";
		X2.X2value = "0";
		X3.X3value = "0";
		CCR.initial();

		ram.reset();
		GUI.console.append("System Initialized!");
		devList.addDevice(console);
		Console.setConsoleId(DeviceList.getCurrentDeviceId() - 1);
		devList.addDevice(keyboard);
		Keyboard.setKeyboardId(DeviceList.getCurrentDeviceId() - 1);
		devList.addDevice(cardReader);
		CardReader.setCardReaderId(DeviceList.getCurrentDeviceId() - 1);
		// lOAD AND STORE
		ISA.addISAList("LDR", "000001");
		ISA.addISAList("STR", "000010");
		ISA.addISAList("LDA", "000011");
		ISA.addISAList("LDX", "101001");
		ISA.addISAList("STX", "101010");
		// Arithmetic and Logical
		ISA.addISAList("AMR", "000100");
		ISA.addISAList("SMR", "000101");
		ISA.addISAList("AIR", "000110");
		ISA.addISAList("SIR", "000111");
		ISA.addISAList("MLT", "010100");
		ISA.addISAList("DVD", "010101");
		ISA.addISAList("TRR", "010110");
		ISA.addISAList("AND", "010111");
		ISA.addISAList("ORR", "011000");
		ISA.addISAList("NOT", "011001");
		// Transfer
		ISA.addISAList("JZ", "001010");
		ISA.addISAList("JNE", "001011");
		ISA.addISAList("JCC", "001100");
		ISA.addISAList("JMP", "001101");
		ISA.addISAList("JSR", "001110");
		ISA.addISAList("RFS", "001111");
		ISA.addISAList("SOB", "010000");
		ISA.addISAList("JGE", "010001");
		// IO
		ISA.addISAList("IN", "111101");
		ISA.addISAList("OUT", "111110");
		ISA.addISAList("CHK", "111111");
		// ROM
		ROM.init();
	}
}
