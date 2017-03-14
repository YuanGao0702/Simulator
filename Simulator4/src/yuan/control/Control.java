package yuan.control;

import yuan.ALU.ALU;
import yuan.Bus.CpuBus;
import yuan.GUI.GUI;
import yuan.ISA.ISA;
import yuan.RAM.RAM;
import yuan.RegisterImpl.IR;
import yuan.RegisterImpl.MAR;
import yuan.RegisterImpl.MDR;
import yuan.RegisterImpl.PC;
import yuan.cache.Cache;
import yuan.decoder.Decoder;
import yuan.instruction.Instruction;

public class Control {
	public static int immediate;
	Instruction inc = new Instruction();
	RAM ram = new RAM();

	public static boolean decode(String str) {
		try {
			String arrays[] = str.split(" ");
			// opcode
			Instruction.opcode = ISA.getISAList(arrays[0]);

			if (arrays[0].equals("AIR") || arrays[0].equals("SIR")) {	//opcode R IMMED
				if (!decodeRF(arrays[1]))	return false;
				if (!decodeImmed(arrays[2]))	return false;
				Instruction.command = Instruction.opcode + Instruction.RF + "000"
						+ Instruction.immed;
				return true;
			}else if(arrays[0].equals("JCC")){		//opcode CC X I ADDR
				if(!decodeCC(arrays[1])) 	return false;
				if(!decodeRX(arrays[2])) 	return false;
				if(!decodeI(arrays[3])) 	return false;
				if(!decodeAddr(arrays[4])) 	return false;
				Instruction.command = Instruction.opcode + Instruction.cc 
						+Instruction.RX + Instruction.I + Instruction.ADDR;
				return true;
			}else if(arrays[0].equals("JMP") || arrays[0].equals("JSR") 
					|| arrays[0].equals("LDX") || arrays[0].equals("STX")){	//opcode X I ADDR
				if(!decodeRX(arrays[1])) 	return false;
				if(!decodeI(arrays[2]))		return false;
				if(!decodeAddr(arrays[3])) return false;
				Instruction.command = Instruction.opcode + "00" +Instruction.RX
						+ Instruction.I + Instruction.ADDR;
			}else if (arrays[0].equals("RFS")){		//opcode Immed
				if(!decodeImmed(arrays[1]))	return false;
				Instruction.command = Instruction.opcode + "00000" +Instruction.immed;
			}else if(arrays[0].equals("IN") || arrays[0].equals("OUT") 
					|| arrays[0].equals("CHK")){	//opcode r devid
				if(!decodeRF(arrays[1]))	return false;
				if(!decodeDevID(arrays[2]))	return false;
				Instruction.command = Instruction.opcode + "00" +Instruction.RF +"000" + Instruction.DevID;
			}else if(arrays[0].equals("MLT") || arrays[0].equals("DVD")){	 	//opcode rx,ry
				if((arrays[1].equals("R0") || arrays[1].equals("R2")) 
						&& (arrays[2].equals("R0") || arrays[2].equals("R2"))){
					if(!decodeRF(arrays[1]))	return false;
					Instruction.command = Instruction.opcode + Instruction.RF; 
					if(!decodeRF(arrays[2])) 	return false;
					Instruction.command = Instruction.command + Instruction.RF + "00000000";
				}else return false;
			}else if(arrays[0].equals("TRR") || arrays[0].equals("AND")		 	//opcode rx,ry
					|| arrays[0].equals("ORR")){
				if(!decodeRF(arrays[1]))	return false;
				Instruction.command = Instruction.opcode + Instruction.RF; 
				if(!decodeRF(arrays[2])) 	return false;
				Instruction.command = Instruction.command + Instruction.RF + "00000000";
			}else if(arrays[0].equals("NOT")){
				if(!decodeRF(arrays[1])) 	return false;
				Instruction.command = Instruction.opcode + Instruction.RF + "0000000000"; 
			}else if (arrays[0].equals("AMR") || arrays[0].equals("SMR")		//opcode R X I ADDR
					|| arrays[0].equals("JZ") || arrays[0].equals("JNE")
					|| arrays[0].equals("SOB") || arrays[0].equals("JGE")
					|| arrays[0].equals("LDR") || arrays[0].equals("STR")
					|| arrays[0].equals("LDA")) {
				if (!decodeRF(arrays[1]))	return false;
				if (!decodeRX(arrays[2]))	return false;
				if (!decodeI(arrays[3]))	return false;
				if (!decodeAddr(arrays[4]))	return false;
				Instruction.command = Instruction.opcode + Instruction.RF
						+ Instruction.RX + Instruction.I + Instruction.ADDR;
				return true;
			}else return false;			
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean decodeRF(String RF){
		switch (RF) {
		case "R0":
			Instruction.RF = "00";
			return  true;
		case "R1":
			Instruction.RF = "01";
			return  true;
		case "R2":
			Instruction.RF = "10";
			return  true;
		case "R3":
			Instruction.RF = "11";
			return  true;
		default:
			return false;
		}
	}
	
	public static boolean decodeImmed(String immed) {
		try {
			immediate = Integer.parseInt(immed);
		} catch (Exception e) {
			return false;
		}
		if (immediate < 0 || immediate > 128) {
			GUI.screen.append("immediate must be <=128 >=0\n");
			return false;
		} else {
			String immedStr = Integer.toBinaryString(immediate);
			int l = immedStr.length();
			for (int i = 0; i < (7 - l); i++) {
				immedStr = "0" + immedStr;
			}
			Instruction.immed = immedStr;
			return true;
		}
	}
	
	public static boolean decodeCC(String cc) {
		int ccInt;
		try {
			 ccInt= Integer.parseInt(cc);
		} catch (Exception e) {
			return false;
		}
		if (ccInt<0 || ccInt>3) {
			GUI.screen.append("cc must be <=3 >=0\n");
			return false;
		} else {
			String ccStr = Integer.toBinaryString(ccInt);
			int l = ccStr.length();
			for (int i = 0; i < (2 - l); i++) {
				ccStr = "0" + ccStr;
			}
			Instruction.cc = ccStr;
			return true;
		}
	}
	
	public static boolean decodeRX(String rx) {
		switch (rx) {
		case "X1":
			Instruction.RX = "01";
			return true;
		case "X2":
			Instruction.RX = "10";
			return true;
		case "X3":
			Instruction.RX = "11";
			return true;
		default:
			return false;
		}
	}
	
	public static boolean decodeI(String indirect) {
		switch (indirect) {
		case "0":
			Instruction.I = "0";
			return true;
		case "1":
			Instruction.I = "1";
			return true;
		default:
			return false;
		}
	}
	
	public static boolean decodeAddr(String addr) {
		int addrInt;
		try {
			addrInt = Integer.parseInt(addr);
		}catch(Exception e){
			return false;
		}
		if (addrInt <= 128 && addrInt >= 0) {
			String addr1 = Integer.toBinaryString(addrInt);
			int l = addr1.length();
			for(int i=0; i<(7-l);i++){
				addr1 = "0"+addr1;
			}
			Instruction.ADDR = addr1;
			return true;
		}else{
			GUI.screen.append("addr must be <=128 >=0\n");
			return false;
		}
	}
	
	public static boolean decodeDevID(String DevID) {
		int DevIDInt;
		String DevIDStr;

		try {
			DevIDInt = Integer.parseInt(DevID);
		} catch (Exception e) {
			return false;
		}
		if (DevIDInt >= 0 && DevIDInt <= 31) {
			DevIDStr = Integer.toBinaryString(DevIDInt);
			int l = DevIDStr.length();
			for (int i = 0; i < (5 - l); i++) {
				DevIDStr = "0" + DevIDStr;
			}
			Instruction.DevID = DevIDStr;
			return true;
		} else {
			GUI.screen.append("DevID must be <=31 >=0\n");
			return false;
		}
	}

	public static boolean execute(String pc) {
		// TODO Auto-generated method stub
		// MAR<-PC
		GUI.console.append("MAR<-PC\n");
		//CpuBus.getValue("PC");
		//CpuBus.setValue("MAR");
		MAR.MARvalue = PC.PCvalue;
		GUI.marField.setText(MAR.MARvalue);

		GUI.console.append("MAR:" + MAR.MARvalue +"\n");
		GUI.console.append("PC:" + PC.PCvalue+"\n");

		// MDR<-c(MAR)
		GUI.console.append("MDR<-c(MAR)\n");
		MDR.MDRvalue = Cache.getValue(Long.parseLong(MAR.MARvalue));
		GUI.mdrField.setText(MDR.MDRvalue);
		
		GUI.console.append("MDR:" + MDR.MDRvalue + "\n");
		GUI.console.append("MAR:" + MAR.MARvalue + "\n");

		// IR<-MDR
		GUI.console.append("IR<-MDR\n");
		CpuBus.getValue("MDR");
		CpuBus.setValue("IR");
		GUI.irField.setText(IR.IRvalue);

		GUI.console.append("IR:" + IR.IRvalue + "\n");
		GUI.console.append("MDR:" + MDR.MDRvalue+"\n");

		if(Decoder.decode(IR.IRvalue)){
			// execute IR
			if(ALU.execute()){
				GUI.refreshRegisterValue();
			}else	return false;
			return true;
		}else return false;
	}

}
