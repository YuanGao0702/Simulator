package yuan.Boot;

import java.io.IOException;

import yuan.Bus.SystemBus;
import yuan.GUI.GUI;
import yuan.Init.Init;
import yuan.dev.Console;

public class Boot {

	public static void main(String[] args) throws IOException {
		GUI frame = new GUI();
		frame.runGUI();
		// declare
		Init init = new Init();
		SystemBus sysBus = new SystemBus();
		Console console = new Console();
		// Register.RAM.Device reset
		init.reset();
		// Menu and receive Input
		//while (true) {
			console.output();
	}
}
