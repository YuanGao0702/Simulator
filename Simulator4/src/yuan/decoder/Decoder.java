package yuan.decoder;

import yuan.GUI.GUI;
import yuan.instruction.Instruction;

public class Decoder {

	public static boolean decode(String command)
	{
		try{
			Instruction.opcode = command.substring(0, 6);

			switch(Instruction.opcode){
			//Arithmetic and Logical
				case("000100"):
					Instruction.RF = command.substring(6, 8);
					Instruction.RX = command.substring(8, 10);
					Instruction.I = command.substring(10, 11);
					Instruction.ADDR = command.substring(11);
					break;
				case("000101"):
					Instruction.RF = command.substring(6, 8);
					Instruction.RX = command.substring(8, 10);
					Instruction.I = command.substring(10, 11);
					Instruction.ADDR = command.substring(11);
					break;
				case("000110"):
					Instruction.RF = command.substring(6, 8);
					Instruction.immed = command.substring(11);
					break;
				case("000111"):
					Instruction.RF = command.substring(6, 8);
					Instruction.immed = command.substring(11);
					break;
				case("010100"):
					Instruction.rx = command.substring(6,8);
					Instruction.ry = command.substring(8,10);
				case("010101"):
					Instruction.rx = command.substring(6,8);
					Instruction.ry = command.substring(8,10);
				case("010110"):
					Instruction.rx = command.substring(6,8);
					Instruction.ry = command.substring(8,10);
				case("010111"):
					Instruction.rx = command.substring(6,8);
					Instruction.ry = command.substring(8,10);
				case("011000"):
					Instruction.rx = command.substring(6,8);
					Instruction.ry = command.substring(8,10);
				case("011001"):
					Instruction.rx = command.substring(6,8);
				//Transfer
				case("001010"):
					Instruction.RF = command.substring(6, 8);
					Instruction.RX = command.substring(8, 10);
					Instruction.I = command.substring(10, 11);
					Instruction.ADDR = command.substring(11);
					break;
				case("001011"):
					Instruction.RF = command.substring(6, 8);
					Instruction.RX = command.substring(8, 10);
					Instruction.I = command.substring(10, 11);
					Instruction.ADDR = command.substring(11);
					break;
				case("001100"):
					Instruction.cc = command.substring(6, 8);
					Instruction.RX = command.substring(8, 10);
					Instruction.I = command.substring(10, 11);
					Instruction.ADDR = command.substring(11);
					break;
				case("001101"):
					Instruction.RX = command.substring(8, 10);
					Instruction.I = command.substring(10, 11);
					Instruction.ADDR = command.substring(11);
					break;
				case("001110"):
					Instruction.RX = command.substring(8, 10);
					Instruction.I = command.substring(10, 11);
					Instruction.ADDR = command.substring(11);
					break;
				case("001111"):
					Instruction.immed = command.substring(11);
					break;
				case("010000"):
					Instruction.RF = command.substring(6, 8);
					Instruction.RX = command.substring(8, 10);
					Instruction.I = command.substring(10, 11);
					Instruction.ADDR = command.substring(11);
					break;
				case("010001"):
					Instruction.RF = command.substring(6, 8);
					Instruction.RX = command.substring(8, 10);
					Instruction.I = command.substring(10, 11);
					Instruction.ADDR = command.substring(11);
					break;
				case("111101"):
					Instruction.RF = command.substring(8, 10);
					Instruction.DevID = command.substring(13);
					break;	
				case("111110"):
					Instruction.RF = command.substring(8, 10);
					Instruction.DevID = command.substring(13);
					break;	
				case("111111"):
					Instruction.RF = command.substring(8, 10);
					Instruction.DevID = command.substring(13);
					break;	
				//Load and store
				case("000001"):
					Instruction.RF = command.substring(6, 8);
					Instruction.RX = command.substring(8, 10);
					Instruction.I = command.substring(10, 11);
					Instruction.ADDR = command.substring(11);
					break;
				case("000010"):
					Instruction.RF = command.substring(6, 8);
					Instruction.RX = command.substring(8, 10);
					Instruction.I = command.substring(10, 11);
					Instruction.ADDR = command.substring(11);
					break;
				case("000011"):
					Instruction.RF = command.substring(6, 8);
					Instruction.RX = command.substring(8, 10);
					Instruction.I = command.substring(10, 11);
					Instruction.ADDR = command.substring(11);
					break;
				case("101001"):
					Instruction.RX = command.substring(8, 10);
					Instruction.I = command.substring(10, 11);
					Instruction.ADDR = command.substring(11);
					break;
				case("101010"):
					Instruction.RX = command.substring(8, 10);
					Instruction.I = command.substring(10, 11);
					Instruction.ADDR = command.substring(11);
					break;
				default:
					GUI.screen.append("decoder error\n");
					return false;
			}
		}catch(Exception e1)
		{
			//GUI.screen.append("decoder error\n");
			return false;
		}
		return true;
	}
}
