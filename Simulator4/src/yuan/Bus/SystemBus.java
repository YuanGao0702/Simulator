package yuan.Bus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import yuan.dev.Console;
import yuan.dev.Keyboard;

public class SystemBus{
	//IO device Bus
	Console console = new Console();
	Keyboard keyboard = new Keyboard();
	
	public void setValue(String str,String str1) throws IOException {
		// TODO Auto-generated method stub
		switch (str1) {
		case "console":
			console.setValue(str);
			break;
		case "keyboard":
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String key = br.readLine();
			keyboard.setValue(key);
		}
	}
	public String getValue(String str) {
		// TODO Auto-generated method stub
		String str1="";
		switch(str){
		case "keyboard":
			str1= keyboard.getValue();
			//clear keyboardValue
			//keyboard.KeyboardValue="";
		}
		return str1;
	}

}
