package yuan.ALU;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import yuan.GUI.GUI;
import yuan.RAM.RAM;
import yuan.RegisterImpl.CCR;
import yuan.RegisterImpl.PC;
import yuan.RegisterImpl.R0;
import yuan.RegisterImpl.R1;
import yuan.RegisterImpl.R2;
import yuan.RegisterImpl.R3;
import yuan.RegisterImpl.X1;
import yuan.RegisterImpl.X2;
import yuan.RegisterImpl.X3;
import yuan.cache.Cache;
import yuan.dev.Keyboard;
import yuan.instruction.Instruction;

public class ALU {
	
	static int wordPoint = 100;
	static int sentencePoint = 120;

	public static boolean execute() {

		// TODO Auto-generated method stub
		String rf = Instruction.RF;
		String rx = Instruction.RX;
		String i = Instruction.I;
		String addr = Instruction.ADDR;
		String immediate = Instruction.immed;
		String cc = Instruction.cc;
		String devid = Instruction.DevID;
		String regX = Instruction.rx;
		String regY = Instruction.ry;

		switch (Instruction.opcode) {
		case "000100":
			return AMR(rf, rx, i, addr);
		case "000101":
			return SMR(rf, rx, i, addr);
		case "000110":
			return AIR(rf, immediate);
		case "000111":
			return SIR(rf, immediate);
		case "010100":
			return MLT(regX, regY);
		case "010101":
			return DVD(regX, regY);
		case "010110":
			return TRR(regX, regY);
		case "010111":
			return AND(regX, regY);
		case "011000":
			return ORR(regX, regY);
		case "011001":
			return NOT(regX);
		case "001010":
			return JZ(rf, rx, i, addr);
		case "001011":
			return JNE(rf, rx, i, addr);
		case "001100":
			return JCC(cc, rx, i, addr);
		case "001101":
			return JMP(rx, i, addr);
		case "001110":
			return JSR(rx, i, addr);
		case "001111":
			return RFS(immediate);
		case "010000":
			return SOB(rf, rx, i, addr);
		case "010001":
			return JGE(rf, rx, i, addr);
		case "111101":
			return IN(rf, devid);
		case "111110":
			return OUT(rf, devid);
		case "111111":
			// return CHK(rf, devid);
		case "000001":
			return LDR(rf, rx, i, addr);
		case "000010":
			return STR(rf, rx, i, addr);
		case "000011":
			return LDA(rf, rx, i, addr);
		case "101001":
			return LDX(rf, rx, i, addr);
		case "101010":
			return STX(rf, rx, i, addr);
		default:
			return false;
		}
	}

	// Load and Store
	private static boolean LDR(String RF, String RX, String I, String addr) {
		String EA;
		String mData;
		try {
			EA = Long.toBinaryString(getEA(RX, I, addr));
			mData = Cache.getValue(Long.parseLong(EA));
		} catch (Exception e) {
			GUI.screen.append("illegal address\n");
			return false;
		}
		if (mData == null) {
			GUI.screen.append("illegal address\n");
			return false;
		}
		setValueToRegisterById(RF, Integer.toString(binaryToDecimal(mData)));
		// pc++
		PC.PCplusOne();
		return true;
	}

	private static boolean STR(String RF, String RX, String I, String addr) {
		long EALong;
		String EABinary;
		String rfData = getValueFromRegisterById(RF);
		if (rfData.equals("error"))
			return false;

		try {
			EALong = getEA(RX, I, addr);
			EABinary = Long.toBinaryString(EALong);
		} catch (Exception e) {
			GUI.screen.append("illegal address\n");
			return false;
		}
		Cache.addValueByADDR(Long.parseLong(EABinary),
				decimalToBinary(Integer.parseInt(rfData)));
		// pc++
		PC.PCplusOne();
		return true;
	}

	private static boolean LDA(String RF, String RX, String I, String addr) {
		long EALong;
		String rfData = getValueFromRegisterById(RF);
		if (rfData.equals("error"))
			return false;

		try {
			EALong = getEA(RX, I, addr);
		} catch (Exception e) {
			GUI.screen.append("illegal address\n");
			return false;
		}
		setValueToRegisterById(RF, Long.toString(EALong));
		// pc++
		PC.PCplusOne();
		return true;
	}

	private static boolean LDX(String RF, String RX, String I, String addr) {
		String EABinary;
		String mData;
		try {
			EABinary = Long.toBinaryString(getEA(RX, I, addr));
			mData = Cache.getValue(Long.parseLong(EABinary));

		} catch (Exception e) {
			GUI.screen.append("illegal address\n");
			return false;
		}
		if (mData == null) {
			GUI.screen.append("illegal address\n");
			return false;
		}
		setValueToIndexById(RX, Integer.toString(Integer.valueOf(mData, 2)));
		// pc++
		PC.PCplusOne();
		return true;
	}

	private static boolean STX(String RF, String RX, String I, String addr) {
		long EALong;
		String EABinary;
		String rxData = getValueFromIndexById(RX);
		if (rxData.equals("error"))
			return false;

		try {
			EALong = getEA(RX, I, addr);
			EABinary = Long.toBinaryString(EALong);
		} catch (Exception e) {
			GUI.screen.append("illegal address\n");
			return false;
		}
		Cache.addValueByADDR(Long.parseLong(EABinary),
				addZero(Integer.toBinaryString(Integer.parseInt(rxData)), 18));
		// pc++
		PC.PCplusOne();
		return true;
	}

	// transfer
	private static boolean JZ(String RF, String RX, String I, String addr) {
		long EALong;
		String rfData = getValueFromRegisterById(RF);
		if (rfData.equals("error"))
			return false;

		try {
			EALong = getEA(RX, I, addr);
		} catch (Exception e) {
			GUI.screen.append("illegal address\n");
			return false;
		}
		if (rfData.equals("0")) {
			PC.PCvalue = PC.addZero(Long.toBinaryString(EALong), 18);
		} else
			// pc++
			PC.PCplusOne();
		return true;
	}

	private static boolean JNE(String RF, String RX, String I, String addr) {
		long EALong;
		String rfData = getValueFromRegisterById(RF);
		if (rfData.equals("error"))
			return false;

		try {
			EALong = getEA(RX, I, addr);
		} catch (Exception e) {
			GUI.screen.append("illegal address\n");
			return false;
		}
		if (!rfData.equals("0")) {
			PC.PCvalue = PC.addZero(Long.toBinaryString(EALong), 18);
		} else
			PC.PCplusOne();
		return true;
	}

	private static boolean JCC(String CC, String RX, String I, String addr) {
		long EALong;

		try {
			EALong = getEA(RX, I, addr);
		} catch (Exception e) {
			GUI.screen.append("illegal address\n");
			return false;
		}
		int ccrValue = CCR.i[Integer.valueOf(CC, 2)];
		if (ccrValue == 1) {
			PC.PCvalue = PC.addZero(Long.toBinaryString(EALong), 18);
		} else
			PC.PCplusOne();
		return true;
	}

	private static boolean JMP(String RX, String I, String addr) {
		long EALong;

		try {
			EALong = getEA(RX, I, addr);
		} catch (Exception e) {
			GUI.screen.append("illegal address\n");
			return false;
		}
		PC.PCvalue = PC.addZero(Long.toBinaryString(EALong), 18);
		return true;
	}

	private static boolean JSR(String RX, String I, String addr) {
		long EALong;

		try {
			EALong = getEA(RX, I, addr);
		} catch (Exception e) {
			GUI.screen.append("illegal address\n");
			return false;
		}
		R3.R3value = Integer.toString(Integer.valueOf(PC.PCvalue, 2) + 1);
		PC.PCvalue = PC.addZero(Long.toBinaryString(EALong), 18);
		return true;
	}

	private static boolean RFS(String Immed) {
		PC.PCvalue = PC.addZero(
				Integer.toBinaryString(Integer.parseInt(R3.R3value)), 18);
		R0.R0value = Integer.toString(Integer.valueOf(Immed, 2));
		return true;
	}

	private static boolean SOB(String RF, String RX, String I, String addr) {
		long EALong;
		int rValue = Integer.parseInt(getValueFromRegisterById(RF));

		try {
			EALong = getEA(RX, I, addr);
		} catch (Exception e) {
			GUI.screen.append("illegal address\n");
			return false;
		}
		setValueToRegisterById(RF, Integer.toString(rValue - 1));
		if (rValue > 0) {
			PC.PCvalue = PC.addZero(Long.toBinaryString(EALong), 18);
		} else
			// pc++
			PC.PCplusOne();
		return true;
	}

	private static boolean JGE(String RF, String RX, String I, String addr) {
		long EALong;
		int rValue = Integer.parseInt(getValueFromRegisterById(RF));

		try {
			EALong = getEA(RX, I, addr);
		} catch (Exception e) {
			GUI.screen.append("illegal address\n");
			return false;
		}
		if (rValue >= 0) {
			PC.PCvalue = PC.addZero(Long.toBinaryString(EALong), 18);
		} else
			// pc++
			PC.PCplusOne();
		return true;
	}

	// Arithmetic and logic
	private static boolean AMR(String RF, String RX, String I, String addr) {

		String EA;
		String mData;
		int result;
		try {
			EA = Long.toBinaryString(getEA(RX, I, addr));
			mData = Cache.getValue(Long.parseLong(EA));
		} catch (Exception e) {
			GUI.screen.append("illegal address\n");
			return false;
		}
		if (mData == null) {
			GUI.screen.append("illegal address\n");
			return false;
		}
		result = Integer.parseInt(getValueFromRegisterById(RF))
				+ binaryToDecimal(mData);
		if (result > 131071) {
			CCR.i[0] = 1;
			PC.PCplusOne();
			return true;
		} else if (result < -131072) {
			CCR.i[1] = 1;
			PC.PCplusOne();
			return true;
		} else {
			CCR.i[0] = 0;
			CCR.i[1] = 0;
			setValueToRegisterById(RF, Integer.toString(result));
			PC.PCplusOne();
			return true;
		}
	}

	private static boolean SMR(String RF, String RX, String I, String addr) {
		// TODO Auto-generated method stub
		String EA;
		String mData;
		int result;
		try {
			EA = Long.toBinaryString(getEA(RX, I, addr));
			mData = Cache.getValue(Long.parseLong(EA));
		} catch (Exception e) {
			GUI.screen.append("illegal address\n");
			return false;
		}
		if (mData == null) {
			GUI.screen.append("illegal address\n");
			return false;
		}
		result = Integer.parseInt(getValueFromRegisterById(RF))
				- binaryToDecimal(mData);
		if (result > 131071) {
			CCR.i[0] = 1;
			PC.PCplusOne();
			return true;
		} else if (result < -131072) {
			CCR.i[1] = 1;
			PC.PCplusOne();
			return true;
		} else {
			CCR.i[0] = 0;
			CCR.i[1] = 0;
			setValueToRegisterById(RF, Integer.toString(result));
			PC.PCplusOne();
			return true;
		}
	}

	private static boolean SIR(String RF, String immediate2) {
		// TODO Auto-generated method stub
		int result;
		result = Integer.parseInt(getValueFromRegisterById(RF))
				- Integer.valueOf(immediate2, 2);
		if (result > 131071) {
			CCR.i[0] = 1;
			PC.PCplusOne();
			return true;
		} else if (result < -131072) {
			CCR.i[1] = 1;
			PC.PCplusOne();
			return true;
		} else {
			CCR.i[0] = 0;
			CCR.i[1] = 0;
			setValueToRegisterById(RF, Integer.toString(result));
			PC.PCplusOne();
			return true;
		}
	}

	private static boolean AIR(String RF, String immediate2) {
		// TODO Auto-generated method stub
		int result;
		result = Integer.parseInt(getValueFromRegisterById(RF))
				+ Integer.valueOf(immediate2, 2);
		if (result > 131071) {
			CCR.i[0] = 1;
			PC.PCplusOne();
			return true;
		} else if (result < -131072) {
			CCR.i[1] = 1;
			PC.PCplusOne();
			return true;
		} else {
			CCR.i[0] = 0;
			CCR.i[1] = 0;
			setValueToRegisterById(RF, Integer.toString(result));
			PC.PCplusOne();
			return true;
		}
	}

	private static boolean MLT(String regX, String regY) {
		int rxInt = Integer.parseInt(getValueFromRegisterById(regX));
		int ryInt = Integer.parseInt(getValueFromRegisterById(regY));

		long result = rxInt * ryInt;
		String dBinary = "1";
		if (result >= 0) {
			dBinary = addZero(Long.toBinaryString(result), 36);
		} else {
			String dBinaryNegative = Long.toBinaryString(result - 1).substring(
					27);

			for (int i = 2; i <= 36; i++) {
				if (dBinaryNegative.substring(i - 1, i).equals("0"))
					dBinary = dBinary + "1";
				else
					dBinary = dBinary + "0";
			}
		}
		System.out.println(result);
		System.out.println(dBinary);

		switch (regX) {
		case ("00"):
			setValueToRegisterById("00",
					Integer.toString(binaryToDecimal(dBinary.substring(0, 18))));
			setValueToRegisterById(
					"01",
					Integer.toString(binaryToDecimal(dBinary.substring(18, 36))));
			break;
		case ("10"):
			setValueToRegisterById("10",
					Integer.toString(binaryToDecimal(dBinary.substring(0, 18))));
			setValueToRegisterById(
					"11",
					Integer.toString(binaryToDecimal(dBinary.substring(18, 36))));
			break;
		default:
			return false;
		}
		PC.PCplusOne();
		return true;
	}

	private static boolean DVD(String regX, String regY) {
		int x = Integer.parseInt(getValueFromRegisterById(regX));
		int y = Integer.parseInt(getValueFromRegisterById(regY));
		if (y != 0) {

			int quotient = x / y;
			int remainder = x % y;

			setValueToRegisterById(regX, Integer.toString(quotient));
			switch (regX) {
			case ("00"):
				setValueToRegisterById("01", Integer.toString(remainder));
				break;
			case ("10"):
				setValueToRegisterById("11", Integer.toString(remainder));
				break;
			default:
				return false;
			}
			CCR.i[2] = 0;
		} else
			CCR.i[2] = 1;
		// pc++
		PC.PCplusOne();
		return true;
	}

	private static boolean TRR(String regX, String regY) {
		int x = Integer.parseInt(getValueFromRegisterById(regX));
		int y = Integer.parseInt(getValueFromRegisterById(regY));
		if (x == y)
			CCR.i[3] = 1;
		else
			CCR.i[3] = 0;
		// pc++
		PC.PCplusOne();
		return true;
	}

	private static boolean AND(String regX, String regY) {
		int x = Integer.parseInt(getValueFromRegisterById(regX));
		int y = Integer.parseInt(getValueFromRegisterById(regY));
		setValueToRegisterById(regX, Integer.toString(x & y));
		PC.PCplusOne();
		return true;
	}

	private static boolean ORR(String regX, String regY) {
		int x = Integer.parseInt(getValueFromRegisterById(regX));
		int y = Integer.parseInt(getValueFromRegisterById(regY));
		setValueToRegisterById(regX, Integer.toString(x | y));
		PC.PCplusOne();
		return true;
	}

	private static boolean NOT(String regX) {
		int x = Integer.parseInt(getValueFromRegisterById(regX));
		String xStr = Integer.toBinaryString(x);
		String yStr = "";
		for (int i = 0; i < xStr.length(); i++) {
			if (xStr.substring(i, i + 1).equals("0"))
				yStr = yStr + "1";
			else
				yStr = yStr + "0";
		}
		setValueToRegisterById(regX, Integer.toString(Integer.valueOf(yStr, 2)));
		PC.PCplusOne();
		return true;
	}

	// IO
	private static boolean IN(String RF, String devid){
		int devidInt = Integer.valueOf(devid,2);
		//System.out.println("1");
		switch(devidInt){
		case 0:
			GUI.screen.append("Please Input\n");
			
			GUI.rdbtnValue.setEnabled(true);
			GUI.rdbtnValue.setSelected(true);
			while(true){
				//System.out.println("A");
				try {
					Thread.sleep(300);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(!Keyboard.KeyboardValue.equals("")){
					String content = Keyboard.KeyboardValue;
					int len = content.length();
					for(int i=0 ; i<len; i=i+2){
						String str1 = addZero(Integer.toBinaryString((int)content.charAt(i)),9);
						if(i+1 == len){
							RAM.addValueByADDR(Long.parseLong(Integer.toBinaryString(wordPoint)), str1 + "000000000");
							RAM.addValueByADDR(Long.parseLong(Integer.toBinaryString(wordPoint+1)), "000000000000000000");
							wordPoint = wordPoint + 2;
						}else{
							String str2 = addZero(Integer.toBinaryString((int)content.charAt(i+1)),9);
							
							RAM.addValueByADDR(Long.parseLong(Integer.toBinaryString(wordPoint)), str1 + str2);
							wordPoint ++;
						}
						if(i+2 == len){
							RAM.addValueByADDR(Long.parseLong(Integer.toBinaryString(wordPoint)), "000000000000000000");
							wordPoint ++;
						}
					}
					Keyboard.KeyboardValue = "";
					GUI.rdbtnValue.setEnabled(false);
					GUI.rdbtnCommand.setSelected(true);
					break;
				} 
			}
			break;
		case 2:
			File file = new File("passage.txt");
			BufferedReader reader = null;
			try {
				
				reader = new BufferedReader(new FileReader(file));
				String content = null;
				while ((content = reader.readLine()) != null) {
					content = content + "\n";
					int len = content.length();
					for(int i=0 ; i<len; i=i+2){
						String str1 = addZero(Integer.toBinaryString((int)content.charAt(i)),9);
						if(i+1 == len){
							RAM.addValueByADDR(Long.parseLong(Integer.toBinaryString(sentencePoint)), str1 + "000000000");
							RAM.addValueByADDR(Long.parseLong(Integer.toBinaryString(sentencePoint+1)), "000000000000000000");
							sentencePoint = sentencePoint + 2;
						}else{
							String str2 = addZero(Integer.toBinaryString((int)content.charAt(i+1)),9);
							if(str1.equals("000100000")){
								RAM.addValueByADDR(Long.parseLong(Integer.toBinaryString(sentencePoint)), "000000000000100000");
								i --;
								sentencePoint ++;
							}else if(str2.equals("000100000")){
								RAM.addValueByADDR(Long.parseLong(Integer.toBinaryString(sentencePoint)), str1 + "000000000");
								RAM.addValueByADDR(Long.parseLong(Integer.toBinaryString(sentencePoint+1)), "000000000000100000");
								sentencePoint = sentencePoint + 2;
							}else{
								RAM.addValueByADDR(Long.parseLong(Integer.toBinaryString(sentencePoint)), str1 + str2);
								sentencePoint++;
							}
							if(i+2 == len){
								RAM.addValueByADDR(Long.parseLong(Integer.toBinaryString(sentencePoint)), "000000000000000000");
								sentencePoint ++;
							}
						}
					}
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e1) {
					}
				}
			}
			break;
		default:
			return false;
		}
		PC.PCplusOne();
		return true;
	}

	private static boolean OUT(String RF, String devid) {
		int devidInt = Integer.valueOf(devid, 2);
		switch (devidInt) {
		case 1:
			GUI.screen.append(getValueFromRegisterById(RF) + "\n");
			GUI.jBar_screen.setValue(GUI.jBar_screen.getMaximum());
			break;
		case 2:
			String rfValue = addZero(Integer.toBinaryString(Integer.parseInt(getValueFromRegisterById(RF))),18);
			int former = Integer.valueOf(rfValue.substring(0, 9),2);
			int latter = Integer.valueOf(rfValue.substring(9, 18),2);
			
			GUI.screen.append(String.valueOf((char)former));
			GUI.screen.append(String.valueOf((char)latter));
			
			break;
			
		default:
			return false;
		}
		PC.PCplusOne();
		return true;
	}

	private static long getEA(String RX, String I, String addr) {
		long l = 0;
		switch (I) {
		case "0":
			switch (RX) {
			case "01":
				l = Integer.parseInt(X1.X1value) + Long.valueOf(addr, 2);
				break;
			case "10":
				l = Integer.parseInt(X2.X2value) + Long.valueOf(addr, 2);
				break;
			case "11":
				l = Integer.parseInt(X3.X3value) + Long.valueOf(addr, 2);
				break;
			default:
				System.out.println("ALU ERROR");
				break;
			}
			break;
		case "1":
			switch (RX) {
			case "01":
				long l1 = Integer.parseInt(X1.X1value) + Long.valueOf(addr, 2);
				String str1 = Cache.getValue(Long.parseLong(Long
						.toBinaryString(l1)));
				l = Long.valueOf(str1, 2);
				break;
			case "10":
				long l2 = Integer.parseInt(X2.X2value) + Long.valueOf(addr, 2);
				String str2 = Cache.getValue(Long.parseLong(Long
						.toBinaryString(l2)));
				l = Long.valueOf(str2, 2);
				break;
			case "11":
				long l3 = Integer.parseInt(X3.X3value) + Long.valueOf(addr, 2);
				String str3 = Cache.getValue(Long.parseLong(Long
						.toBinaryString(l3)));
				l = Long.valueOf(str3, 2);
				break;
			default:
				System.out.println("ALU ERROR");
				break;
			}
			break;
		default:
			System.out.println("ALU ERROR");
			break;
		}
		return l;
	}

	private static String getValueFromRegisterById(String id) {
		switch (id) {
		case "00":
			return R0.R0value;
		case "01":
			return R1.R1value;
		case "10":
			return R2.R2value;
		case "11":
			return R3.R3value;
		default:
			return "error";
		}
	}

	private static boolean setValueToRegisterById(String id, String value) {
		switch (id) {
		case "00":
			R0.R0value = value;
			return true;
		case "01":
			R1.R1value = value;
			return true;
		case "10":
			R2.R2value = value;
			return true;
		case "11":
			R3.R3value = value;
			return true;
		default:
			return false;
		}
	}

	private static boolean setValueToIndexById(String id, String value) {
		switch (id) {
		case "01":
			X1.X1value = value;
			return true;
		case "10":
			X2.X2value = value;
			return true;
		case "11":
			X3.X3value = value;
			return true;
		default:
			return false;
		}
	}

	private static String getValueFromIndexById(String id) {
		switch (id) {
		case "01":
			return X1.X1value;
		case "10":
			return X2.X2value;
		case "11":
			return X3.X3value;
		default:
			return "error";
		}
	}

	private static String addZero(String binaryString, int length) {
		int l = binaryString.length();
		for (int i = 1; i <= (length - l); i++) {
			binaryString = "0" + binaryString;
		}
		return binaryString;
	}

	private static int binaryToDecimal(String binary) {
		if (binary.substring(0, 1).equals("0")) {
			return Integer.valueOf(binary, 2);
		} else {
			int i = Integer.valueOf(binary.substring(1), 2);
			if (i == 0)
				i = 131072;
			return -i;
		}
	}

	private static String decimalToBinary(int d) {
		if (d >= 0) {
			return addZero(Integer.toBinaryString(d), 18);
		} else {
			String dBinaryNegative = Integer.toBinaryString(d - 1)
					.substring(14);

			String dBinary = "1";
			for (int i = 2; i <= 18; i++) {
				if (dBinaryNegative.substring(i - 1, i).equals("0"))
					dBinary = dBinary + "1";
				else
					dBinary = dBinary + "0";
			}
			return dBinary;
		}
	}
}
