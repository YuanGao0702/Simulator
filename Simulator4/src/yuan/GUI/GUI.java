package yuan.GUI;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.JScrollPane;

import java.awt.SystemColor;

import javax.swing.JRadioButton;
import javax.swing.JList;

import yuan.RAM.RAM;
import yuan.ROM.RomLoader;
import yuan.RegisterImpl.*;
import yuan.cache.Cache;
import yuan.control.Control;
import yuan.dev.Keyboard;
import yuan.instruction.Instruction;

public class GUI extends JFrame{
	
	class t extends Thread{
		public t(){
			
		}
		
		public void run(){
			GUI.btnSingleStep.setEnabled(false);
			Control.execute(PC.PCvalue);
		//	refreshCacheScreen(Cache.cache);
			GUI.btnSingleStep.setEnabled(true);
		}
	}
	
	class t2 extends Thread{
		public t2(){
	
		}
		
		public void run(){
			while(true){
				if(!Control.execute(PC.PCvalue)){
					GUI.screen.append("Program End\n");
					btnSingleStep.setEnabled(true);
					break;
				}
			//	refreshCacheScreen(Cache.cache);
			}
		}
	}

	private JPanel contentpanel;
	public static JTextField pcField;
	public static JTextField marField;
	public static JTextField mdrField;
	public static JTextField irField;
	public static JTextField ADDRField;
	public static JTextArea console;
	public static JTextArea screen;
	public static JTextArea cmdRec;
	public static JTextField dataField;
	public static JTextField pcFieldD;
	public static JTextField r0FieldD;
	public static JTextField r1FieldD;
	public static JTextField r2FieldD;
	public static JTextField r3FieldD;
	public static JTextField x1FieldD;
	public static JTextField x2FieldD;
	public static JTextField x3FieldD;
	public static JTextField ADDRFieldD;
	public static JTextField dataFieldD;
	public static JButton btnSingleStep;
	public static JRadioButton  rdbtnValue;
	public static JRadioButton  rdbtnCommand;
	
	public static JScrollBar jBar_screen;

	
	static long countInstruction = 0; //record how many instruction has been stored
	private JTextField keyboard_1;
	public static JTextArea cache;
	
	public void runGUI() {
	try {
		GUI frame = new GUI();
		frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Create the frame.
	 */
	public GUI() {
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 600);
		contentpanel = new JPanel();
		contentpanel.setForeground(Color.BLACK);
		contentpanel.setBorder(new MatteBorder(0, 1, 1, 1, (Color) Color.GRAY));
		setContentPane(contentpanel);
		contentpanel.setLayout(null);
		
		JPanel panel_keyboard_1 = new JPanel();
		panel_keyboard_1.setLayout(null);
		panel_keyboard_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 128, 128)));
		panel_keyboard_1.setBounds(312, 341, 338, 127);
		contentpanel.add(panel_keyboard_1);
		
		JLabel lblKeyboard_1 = new JLabel("Keyboard");
		lblKeyboard_1.setBounds(6, 6, 66, 16);
		panel_keyboard_1.add(lblKeyboard_1);
		
		keyboard_1 = new JTextField();
		keyboard_1.setColumns(10);
		keyboard_1.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(128, 128, 128)));
		keyboard_1.setBackground(SystemColor.window);
		keyboard_1.setBounds(16, 45, 268, 28);
		panel_keyboard_1.add(keyboard_1);
		
		rdbtnValue = new JRadioButton("Value");
		rdbtnValue.setEnabled(false);
		rdbtnValue.setBounds(153, 2, 66, 23);
		panel_keyboard_1.add(rdbtnValue);
		
		rdbtnCommand = new JRadioButton("Command");
		rdbtnCommand.setSelected(true);
		rdbtnCommand.setBounds(222, 2, 96, 23);
		panel_keyboard_1.add(rdbtnCommand);
		
		ButtonGroup g = new ButtonGroup();
		g.add(rdbtnValue);
		g.add(rdbtnCommand);
		
		JPanel panel_register = new JPanel();
		panel_register.setBorder(new MatteBorder(0, 1, 1, 0, (Color) Color.GRAY));
		panel_register.setBounds(0, 0, 312, 407);
		contentpanel.add(panel_register);
		panel_register.setLayout(null);
		
		JLabel lblRegisterValues = new JLabel("Register");
		lblRegisterValues.setBounds(6, 60, 96, 16);
		panel_register.add(lblRegisterValues);
		
		JLabel lblPc = new JLabel("PC");
		lblPc.setBounds(17, 95, 16, 16);
		panel_register.add(lblPc);
		
		pcField = new JTextField();
		pcField.setEnabled(false);
		pcField.setEditable(false);
		pcField.setText("0");
		pcField.setBounds(50, 89, 181, 28);
		pcField.setBackground(Color.WHITE);
		panel_register.add(pcField);
		pcField.setColumns(10);
		
		JLabel lblMar = new JLabel("MAR");
		lblMar.setBounds(17, 127, 28, 16);
		panel_register.add(lblMar);
		
		marField = new JTextField();
		marField.setText("000000000000000000");
		marField.setEnabled(false);
		marField.setEditable(false);
		marField.setBounds(50, 121, 242, 28);
		marField.setColumns(10);
		panel_register.add(marField);
		
		JLabel lblMdr = new JLabel("MDR");
		lblMdr.setBounds(17, 159, 29, 16);
		panel_register.add(lblMdr);
		
		mdrField = new JTextField();
		mdrField.setText("000000000000000000");
		mdrField.setEnabled(false);
		mdrField.setEditable(false);
		mdrField.setBounds(50, 153, 242, 28);
		mdrField.setColumns(10);
		panel_register.add(mdrField);
		
		JLabel lblIr = new JLabel("IR");
		lblIr.setBounds(17, 191, 12, 16);
		panel_register.add(lblIr);
		
		irField = new JTextField();
		irField.setText("000000000000000000");
		irField.setEnabled(false);
		irField.setEditable(false);
		irField.setBounds(50, 185, 242, 28);
		irField.setColumns(10);
		panel_register.add(irField);
		
		JLabel lblR = new JLabel("R0");
		lblR.setBounds(17, 225, 16, 16);
		panel_register.add(lblR);
		
		JLabel lblR_1 = new JLabel("R1");
		lblR_1.setBounds(168, 227, 16, 16);
		panel_register.add(lblR_1);
		
		JLabel lblR_2 = new JLabel("R2");
		lblR_2.setBounds(17, 253, 16, 16);
		panel_register.add(lblR_2);
		
		JLabel lblR_3 = new JLabel("R3");
		lblR_3.setBounds(168, 255, 16, 16);
		panel_register.add(lblR_3);
		
		JLabel lblX1 = new JLabel("X1");
		lblX1.setBounds(17, 289, 16, 16);
		panel_register.add(lblX1);
		
		JLabel lblX_2 = new JLabel("X2");
		lblX_2.setBounds(168, 289, 16, 16);
		panel_register.add(lblX_2);
		
		JLabel lblX_3 = new JLabel("X3");
		lblX_3.setBounds(17, 319, 16, 16);
		panel_register.add(lblX_3);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(6, 327, 1, 16);
		panel_register.add(textArea);
		
		pcFieldD = new JTextField();
		pcFieldD.setText("0");
		pcFieldD.setBounds(236, 89, 56, 28);
		panel_register.add(pcFieldD);
		pcFieldD.setColumns(10);
		
		r0FieldD = new JTextField();
		r0FieldD.setText("0");
		r0FieldD.setColumns(10);
		r0FieldD.setBounds(50, 219, 96, 28);
		panel_register.add(r0FieldD);
		
		r1FieldD = new JTextField();
		r1FieldD.setText("0");
		r1FieldD.setColumns(10);
		r1FieldD.setBounds(196, 219, 96, 28);
		panel_register.add(r1FieldD);
		
		r2FieldD = new JTextField();
		r2FieldD.setText("0");
		r2FieldD.setColumns(10);
		r2FieldD.setBounds(50, 251, 96, 28);
		panel_register.add(r2FieldD);
		
		r3FieldD = new JTextField();
		r3FieldD.setText("0");
		r3FieldD.setColumns(10);
		r3FieldD.setBounds(196, 251, 96, 28);
		panel_register.add(r3FieldD);
		
		x1FieldD = new JTextField();
		x1FieldD.setText("0");
		x1FieldD.setColumns(10);
		x1FieldD.setBounds(50, 283, 96, 28);
		panel_register.add(x1FieldD);
		
		x2FieldD = new JTextField();
		x2FieldD.setText("0");
		x2FieldD.setColumns(10);
		x2FieldD.setBounds(196, 283, 96, 28);
		panel_register.add(x2FieldD);
		
		x3FieldD = new JTextField();
		x3FieldD.setText("0");
		x3FieldD.setColumns(10);
		x3FieldD.setBounds(50, 315, 96, 28);
		panel_register.add(x3FieldD);
		
		JButton btnNewButton = new JButton("Load");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int flag=1;
				long buffer=0;
				try {
					Integer.parseInt(pcFieldD.getText());
					Long.parseLong(r0FieldD.getText());
					Long.parseLong(r1FieldD.getText());
					Long.parseLong(r2FieldD.getText());
					Long.parseLong(r3FieldD.getText());
					Long.parseLong(x1FieldD.getText());
					Long.parseLong(x2FieldD.getText());
					Long.parseLong(x3FieldD.getText());
					
			    } catch (Exception e1) {
			    	screen.append("Value must be number\n");
			    	flag=0;
			     }
				if(flag==1){
					buffer = Integer.parseInt(pcFieldD.getText());
					if(buffer>2048 || buffer<0)
					{
				    	screen.append("PC must be <=2048 && >=0\n");
				    	flag = 0;
					}
					buffer = Long.parseLong(r0FieldD.getText());
					if(buffer>Math.pow(2,17) || buffer<Math.pow(-2,17))
					{
						screen.append("R0 must be <=2^17 && >=-2^17\n");
				    	flag = 0;
					}
					buffer = Long.parseLong(r1FieldD.getText());
					if(buffer>Math.pow(2,17) || buffer<Math.pow(-2,17))
					{
						screen.append("R1 must be <=2^17 && >=-2^17\n");
				    	flag = 0;
					}
					buffer = Long.parseLong(r2FieldD.getText());
					if(buffer>Math.pow(2,17) || buffer<Math.pow(-2,17))
					{
						screen.append("R2 must be <=2^17 && >=-2^17\n");
				    	flag = 0;
					}
					buffer = Long.parseLong(r3FieldD.getText());
					if(buffer>Math.pow(2,17) || buffer<Math.pow(-2,17))
					{
						screen.append("R3 must be <=2^17 && >=-2^17\n");
				    	flag = 0;
					}
					buffer = Long.parseLong(x1FieldD.getText());
					if(buffer>Math.pow(2,12) || buffer<-Math.pow(2,12))
					{
						screen.append("X1 must be <=2^12\n");
				    	flag = 0;
					}
					buffer = Long.parseLong(x2FieldD.getText());
					if(buffer>Math.pow(2,12) || buffer<-Math.pow(2,12))
					{
						screen.append("X2 must be <=2^12\n");
				    	flag = 0;
					}
					buffer = Long.parseLong(x3FieldD.getText());
					if(buffer>Math.pow(2,12) || buffer<-Math.pow(2,12))
					{
						screen.append("X3 must be <=2^12\n");
				    	flag = 0;
					}
					if(buffer!= 0 && buffer != 1){
						screen.append("CCR0 must be 0||1\n");
						flag = 0;
					}
					if(buffer!= 0 && buffer != 1){
						screen.append("CCR1 must be 0||1\n");
						flag = 0;
					}			
					if(buffer!= 0 && buffer != 1){
						screen.append("CCR2 must be 0||1\n");
						flag = 0;
					}
					if(buffer!= 0 && buffer != 1){
						screen.append("CCR3 must be 0||1\n");
						flag = 0;
					}
				}
				if(flag == 1){
					PC.PCvalue =addZero(Integer.toBinaryString(Integer.parseInt(pcFieldD.getText())),18);
					pcField.setText(PC.PCvalue);
					R0.R0value = r0FieldD.getText();
					R1.R1value = r1FieldD.getText();
					R2.R2value = r2FieldD.getText();
					R3.R3value = r3FieldD.getText();
					X1.X1value = x1FieldD.getText();
					X2.X2value = x2FieldD.getText();
					X3.X3value = x3FieldD.getText();
					screen.append("Load success\n");
				}
			}
		});
		btnNewButton.setBounds(196, 314, 84, 29);
		panel_register.add(btnNewButton);
		
		
		
		JPanel panel_console = new JPanel();
		panel_console.setBorder(new MatteBorder(0, 1, 1, 1, (Color) Color.GRAY));
		panel_console.setBounds(649, 0, 251, 578);
		contentpanel.add(panel_console);
		panel_console.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new MatteBorder(0, 1, 1, 1, (Color) Color.GRAY));
		scrollPane.setBounds(0, 20, 251, 558);
		panel_console.add(scrollPane);
		
		console = new JTextArea();
		console.setEditable(false);
		console.setBackground(UIManager.getColor("Button.background"));
		console.setMargin(new Insets(15,15,15,15));
		scrollPane.setViewportView(console);
		
		JLabel lblConsole = new JLabel("Console");
		lblConsole.setBounds(10, 6, 119, 16);
		panel_console.add(lblConsole);
		
		JPanel panel_control = new JPanel();
		panel_control.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 128, 128)));
		panel_control.setBounds(312, 467, 338, 111);
		contentpanel.add(panel_control);
		panel_control.setLayout(null);
		
		JPanel panel_screen = new JPanel();
		panel_screen.setBorder(new MatteBorder(0, 1, 1, 0, (Color) new Color(128, 128, 128)));
		panel_screen.setBounds(312, 0, 338, 215);
		contentpanel.add(panel_screen);
		panel_screen.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(0, 20, 338, 191);
		scrollPane_2.setBorder(new MatteBorder(0, 1, 0, 1, (Color) new Color(128, 128, 128)));
		panel_screen.add(scrollPane_2);
	
		jBar_screen = scrollPane_2.getVerticalScrollBar();
		jBar_screen.setValue(jBar_screen.getMaximum());
		
		screen = new JTextArea();
		screen.setEditable(false);
		screen.setBackground(UIManager.getColor("Button.background"));
		scrollPane_2.setViewportView(screen);
		screen.setMargin(new Insets(15,15,15,15));
		
		JLabel lblScreen = new JLabel("Screen");
		lblScreen.setBounds(6, 6, 116, 16);
		panel_screen.add(lblScreen);
		
		JPanel panel_cmdRec = new JPanel();
		panel_cmdRec.setBorder(new MatteBorder(1, 1, 0, 0, (Color) new Color(128, 128, 128)));
		panel_cmdRec.setBounds(312, 213, 338, 127);
		contentpanel.add(panel_cmdRec);
		panel_cmdRec.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 20, 338, 108);
		scrollPane_1.setBorder(new MatteBorder(0, 1, 0, 1, (Color) new Color(128, 128, 128)));
		panel_cmdRec.add(scrollPane_1);
		
		cmdRec = new JTextArea();
		cmdRec.setEditable(false);
		cmdRec.setBackground(UIManager.getColor("Button.background"));
		scrollPane_1.setViewportView(cmdRec);
		cmdRec.setMargin(new Insets(15,15,15,15));
		
		JLabel label = new JLabel("Command Record");
		label.setBounds(6, 6, 116, 16);
		panel_cmdRec.add(label);
		
		btnSingleStep = new JButton("Single Step");
		btnSingleStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new t().start();			
			}
		});
		btnSingleStep.setBounds(120, 52, 117, 29);
		panel_control.add(btnSingleStep);
		
		JButton btnLoader = new JButton("Load Program");
		btnLoader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RomLoader.transfer();
				PC.PCvalue = addZero("0",18);
			}
		});
		btnLoader.setBounds(6, 52, 117, 29);
		panel_control.add(btnLoader);
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSingleStep.setEnabled(false);
				new t2().start();
			}
		});
		btnRun.setBounds(234, 52, 81, 29);
		panel_control.add(btnRun);
		
		JLabel lblControl = new JLabel("Control");
		lblControl.setBounds(6, 6, 61, 16);
		panel_control.add(lblControl);
		
		JLabel lblMemory = new JLabel("Memory");
		lblMemory.setBounds(10, 411, 96, 16);
		contentpanel.add(lblMemory);
		
		ADDRField = new JTextField();
		ADDRField.setEnabled(false);
		ADDRField.setText("000000000000000000");
		ADDRField.setBounds(52, 450, 161, 28);
		contentpanel.add(ADDRField);
		ADDRField.setEditable(false);
		ADDRField.setColumns(10);
		
		ADDRFieldD = new JTextField();
		ADDRFieldD.setBounds(216, 450, 76, 28);
		contentpanel.add(ADDRFieldD);
		ADDRFieldD.setText("0");
		ADDRFieldD.setColumns(10);
		
		JLabel lblAddr = new JLabel("addr");
		lblAddr.setBounds(17, 456, 61, 16);
		contentpanel.add(lblAddr);
		
		dataField = new JTextField();
		dataField.setEnabled(false);
		dataField.setText("000000000000000000");
		dataField.setEditable(false);
		dataField.setBounds(52, 485, 161, 28);
		contentpanel.add(dataField);
		dataField.setColumns(10);
		
		dataFieldD = new JTextField();
		dataFieldD.setBounds(216, 485, 76, 28);
		contentpanel.add(dataFieldD);
		dataFieldD.setText("0");
		dataFieldD.setColumns(10);
		
		JLabel lblcontentM = new JLabel("data");
		lblcontentM.setBounds(17, 491, 37, 16);
		contentpanel.add(lblcontentM);
		
		JButton btnRAMRead = new JButton("Query");
		btnRAMRead.setBounds(84, 533, 84, 29);
		contentpanel.add(btnRAMRead);
		
		JButton btnRAMLoad = new JButton("Load");
		btnRAMLoad.setBounds(197, 533, 84, 29);
		contentpanel.add(btnRAMLoad);
		
		JLabel lblcache = new JLabel("Cache");
		lblcache.setBounds(912, 6, 119, 16);
		contentpanel.add(lblcache);
		
		cache = new JTextArea();
		cache.setEditable(false);
		cache.setBackground(UIManager.getColor("Button.background"));
		cache.setMargin(new Insets(15,15,15,15));
		
		cache.setBounds(902, 22, 298, 556);
		contentpanel.add(cache);
		
		btnRAMLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int flag=1;
				long buffer = 0;
				try{
					Long.parseLong(ADDRFieldD.getText());
					Long.parseLong(dataFieldD.getText());
				}catch(Exception e1){
					flag=0;
					screen.append("Value must be VALID\n");
				}
				if(flag==1){	
					buffer = Long.parseLong(ADDRFieldD.getText());
					if(buffer<0 || buffer>2048){
						flag=0;
						screen.append("address must be <=2048 >=0\n");
					}
					buffer = Long.parseLong(dataFieldD.getText());
					if(buffer>Math.pow(2,17)-1 || buffer<Math.pow(-2,17)-1){
						flag=0;
						screen.append("data must be <=2^17-1 >=-2^17\n");
					}
					if(flag==1){
						long addrBinary = Long.parseLong(Long.toBinaryString(Long.parseLong(ADDRFieldD.getText())));
						int dataI = Integer.parseInt(dataFieldD.getText());
						if(dataI >= 0){
							String dataBinary = addZero(Integer.toBinaryString(dataI),18);
							Cache.addValueByADDR(addrBinary,dataBinary);
							dataField.setText(dataBinary);
						}else{
							String dataBinaryNegative = Integer.toBinaryString(dataI - 1).substring(14);
							
							System.out.println(dataBinaryNegative);
							
							String dataBinary="1";
							for(int i=2; i<=18; i++){
								if(dataBinaryNegative.substring(i-1, i).equals("0"))
									dataBinary = dataBinary + "1";
								else dataBinary = dataBinary + "0";
							}
							Cache.addValueByADDR(addrBinary,dataBinary);
							dataField.setText(dataBinary);
						} 
						ADDRField.setText(addZero(Long.toString(addrBinary),18));
						screen.append("Load success\n");
					}
				}
			}
		});
		btnRAMRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int flag=1;
				long buffer = 0;
				try{
					Long.parseLong(ADDRFieldD.getText());
				}catch(Exception e1){
					flag=0;
					screen.append("Value must be number\n");
				}
				if(flag==1){	
					buffer = Long.parseLong(ADDRFieldD.getText());
					if(buffer<0 || buffer>2048){
						flag=0;
						screen.append("address must be <=2048 >=0\n");
					}
					if(flag==1){
						long addrBinary = Long.parseLong(Long.toBinaryString(Long.parseLong(ADDRFieldD.getText())));
						//READ INSTRUCTION INTO MEMORY
						dataField.setText(RAM.getValue(addrBinary));
						if(RAM.getValue(addrBinary) == null){
							dataFieldD.setText("");
						}else{
							dataFieldD.setText(Integer.toString(Integer.valueOf(RAM.getValue(addrBinary),2)));
							ADDRField.setText(addZero(Long.toString(addrBinary),18));
							screen.append("Read success\n");
						}
					}
				}
			}
		});
		
		//listen the enter,put it to value
		keyboard_1.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e){
				int k = e.getKeyCode();
				if (k == KeyEvent.VK_ENTER) {
					if (!keyboard_1.getText().equals("\n")) {
						if(rdbtnValue.isSelected())
							Keyboard.KeyboardValue = keyboard_1.getText();
						else{
							if(Control.decode(keyboard_1.getText())){
								Cache.addValueByADDR(Long.parseLong(Long.toBinaryString(countInstruction)),Instruction.command ); //store cmd into memory
								//System.out.println(Control.decode(keyboard.getText()));
								cmdRec.append(keyboard_1.getText() + "   stored at "+ countInstruction + "\n");
								countInstruction = countInstruction + 1;
							}else{
								screen.append("command error\n");
							}
						}
					}
					keyboard_1.setText("");
				}
			}
		});	
	}


	private static String addZero(String binaryString, int length) {
			int l=binaryString.length();
			for(int i=1;i<=(length-l);i++)
			{
				binaryString="0"+binaryString;
			}
			return binaryString;
	}
	
	public static void refreshCacheScreen(String[][] cacheArray){
		for(int i=1;i<=cacheArray.length;i++){
			cache.setText("");
			cache.append("Id"+ " " + "Signal" + "Used Bit" + "Data Value" + "\n");
			try{
				cache.append("i"+ " " + cacheArray[i][0] + " " + cacheArray[i][1] + " " + cacheArray[i][2] + "\n");
			}catch(Exception e){}
		}
	}
		

	
	public static void refreshRegisterValue(){
		pcFieldD.setText(Integer.toString(Integer.valueOf(PC.PCvalue,2)));
		pcField.setText(PC.PCvalue);
		r0FieldD.setText(R0.R0value);
		r1FieldD.setText(R1.R1value);
		r2FieldD.setText(R2.R2value);
		r3FieldD.setText(R3.R3value);
		x1FieldD.setText(X1.X1value);
		x2FieldD.setText(X2.X2value);
		x3FieldD.setText(X3.X3value);
	}
}
