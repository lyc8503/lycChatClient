package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import socket.ClientMain;
import start.ClientStart;

public class LoginFrame {
	public static RegisterFrame registerframe;
	public static int frameheight;
	public static int framewidth;
	static{//设置窗口高度与宽度
		frameheight=180;
		framewidth=330;
	}
	public static JFrame frame;
	public static JTextField serverfield;
	public static JTextField usernamefield;
	public static JPasswordField passwordfield;
	public static JButton submitbutton;
	public static JButton registerbutton;
	public static GridBagLayout layout;
	public static GridBagConstraints constraints;
	public static JCheckBox rememberpassword;
	
	public static JMenuBar menubar;//菜单项
	public static JMenu helpmenu;//帮助菜单
	public static JMenu accountmenu;//账号菜单
	public static JMenuItem aboutmenuitem;//关于
	public static JMenuItem forgetpassword;//忘记密码
	public static JMenuItem changepassword;//更改密码
	@SuppressWarnings("deprecation")
	public LoginFrame(){
		frame=new JFrame("Login");//开始创建新窗口
		
		frame.addWindowListener(new MyWindowsActionLisntener());
		menubar=new JMenuBar();
		helpmenu=new JMenu("帮助");
		accountmenu=new JMenu("账号");
		aboutmenuitem=new JMenuItem("关于lycChat");
		forgetpassword=new JMenuItem("忘记密码");
		changepassword=new JMenuItem("更改密码");
		forgetpassword.addActionListener(new MenuItemActionLisntener());
		aboutmenuitem.addActionListener(new MenuItemActionLisntener());
		changepassword.addActionListener(new MenuItemActionLisntener());
		helpmenu.add(aboutmenuitem);
		accountmenu.add(changepassword);
		accountmenu.add(forgetpassword);
		menubar.add(accountmenu);
		menubar.add(helpmenu);
		try {
			usernamefield=new JTextField(ClientStart.jsonObject.getString("username"));
			passwordfield=new JPasswordField(ClientStart.jsonObject.getString("password"));
			serverfield=new JTextField(ClientStart.jsonObject.getString("serveraddress"));
		} catch (Exception e) {
			usernamefield=new JTextField();
			passwordfield=new JPasswordField();
			serverfield=new JTextField();
			rememberpassword=new JCheckBox("记住账号与密码",false);
		}
		if(serverfield.getText().equals("")&&passwordfield.getText().equals("")&&usernamefield.getText().equals("")){
			rememberpassword=new JCheckBox("记住账号与密码",false);
		}else{
			rememberpassword=new JCheckBox("记住账号与密码",true);
		}
		submitbutton=new JButton("Submit");
		submitbutton.addActionListener(new ButtonActionListener());
		registerbutton=new JButton("Register");
		registerbutton.addActionListener(new ButtonActionListener());
		layout=new GridBagLayout();
		JLabel serverlabel=new JLabel("ServerAddress:");
		JLabel namelabel=new JLabel("Name:");
		JLabel passwordlabel=new JLabel("Password:");
		constraints=new GridBagConstraints();
		frame.setJMenuBar(menubar);
		
		constraints.gridx=1;//开始添加组件
		constraints.gridy=1;
		constraints.fill=GridBagConstraints.BOTH;
		frame.getContentPane().add(serverlabel);
		layout.setConstraints(serverlabel,constraints);

		constraints.gridx=2;
		constraints.gridy=1;
		constraints.ipadx=100;
		frame.getContentPane().add(serverfield);
		layout.setConstraints(serverfield,constraints);
		
		constraints.gridx=1;
		constraints.gridy=2;
		constraints.fill=GridBagConstraints.BOTH;
		frame.getContentPane().add(namelabel);
		layout.setConstraints(namelabel,constraints);

		constraints.gridx=2;
		constraints.gridy=2;
		constraints.ipadx=100;
		frame.getContentPane().add(usernamefield);
		layout.setConstraints(usernamefield,constraints);
		
		constraints.gridx=1;
		constraints.gridy=3;
		frame.getContentPane().add(passwordlabel);
		layout.setConstraints(passwordlabel,constraints);
		
		constraints.gridx=2;
		constraints.gridy=3;
		frame.getContentPane().add(passwordfield);
		layout.setConstraints(passwordfield,constraints);
		
		constraints.gridx=1;
		constraints.gridy=4;
		constraints.gridwidth=2;
		constraints.fill=GridBagConstraints.BOTH;
		frame.getContentPane().add(rememberpassword);
		layout.setConstraints(rememberpassword,constraints);
		
		constraints.gridx=2;
		constraints.gridy=5;
		constraints.gridwidth=1;
		constraints.gridy=50;
		frame.getContentPane().add(submitbutton);
		layout.setConstraints(submitbutton,constraints);
		
		constraints.gridx=1;
		constraints.gridy=5;
		constraints.gridwidth=1;
		constraints.gridy=50;
		frame.getContentPane().add(registerbutton);
		layout.setConstraints(registerbutton,constraints);
		
		frame.setLayout(layout);
		frame.setResizable(false);
		frame.setVisible(true);//将窗口设置在屏幕中间
		frame.setLocation(((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - framewidth)/2,((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()-frameheight)/2);
		frame.setSize(framewidth, frameheight);
	}
}

class MenuItemActionLisntener implements ActionListener{
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()){
		case"关于lycChat":
			JOptionPane.showMessageDialog(null,"作者:lyc8503"+"\n"+"Email:lyc8503@gmail.com"+"\n"+"lycChat版本:Beta 1.0" ,"lycChat",JOptionPane.PLAIN_MESSAGE);
			break;
		case"忘记密码":
			try {
				String serveradd = (String)JOptionPane.showInputDialog(LoginFrame.frame,
				        "请输入服务器地址", "找回密码",
				        JOptionPane.QUESTION_MESSAGE,null, null, "");
				String username = (String)JOptionPane.showInputDialog(LoginFrame.frame,
				        "请输入你的用户名", "找回密码",
				        JOptionPane.QUESTION_MESSAGE,null, null, "");
				ClientMain.forgetpassword(username,serveradd);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case"更改密码":
			String serveradd2 = (String)JOptionPane.showInputDialog(LoginFrame.frame,
			        "请输入服务器地址", "更改密码",
			        JOptionPane.QUESTION_MESSAGE,null, null, "");
			String username2 = (String)JOptionPane.showInputDialog(LoginFrame.frame,
			        "请输入你的用户名", "更改密码",
			        JOptionPane.QUESTION_MESSAGE,null, null, "");
			String oldpassword2 = (String)JOptionPane.showInputDialog(LoginFrame.frame,
			        "请输入你的原密码", "更改密码",
			        JOptionPane.QUESTION_MESSAGE,null, null, "");
			String newpassword2 = (String)JOptionPane.showInputDialog(LoginFrame.frame,
			        "请输入你的新密码", "更改密码",
			        JOptionPane.QUESTION_MESSAGE,null, null, "");
			String repeatpassword2 = (String)JOptionPane.showInputDialog(LoginFrame.frame,
			        "请重复你的新密码", "更改密码",
			        JOptionPane.QUESTION_MESSAGE,null, null, "");
			if(newpassword2.equals(repeatpassword2)){
				if(newpassword2.length()<6){
					JOptionPane.showMessageDialog(LoginFrame.frame,"密码长度至少为6位!" ,"Warning",JOptionPane.PLAIN_MESSAGE);
				}else{
					ClientMain.changepassword(serveradd2, username2, oldpassword2, newpassword2);
				}
			}else{
				JOptionPane.showMessageDialog(LoginFrame.frame,"两次密码不符合!" ,"Warning",JOptionPane.PLAIN_MESSAGE);
			}
			break;
		}
		
	}
}

class ButtonActionListener implements ActionListener{//监听按钮事件
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()){
		case"Submit":
			ClientMain.login(LoginFrame.usernamefield.getText(),LoginFrame.passwordfield.getText(),LoginFrame.serverfield.getText());
			if(LoginFrame.rememberpassword.isSelected()==true){
				ClientStart.jsonObject.put("serveraddress",LoginFrame.serverfield.getText());
				ClientStart.jsonObject.put("username",LoginFrame.usernamefield.getText());
				ClientStart.jsonObject.put("password",LoginFrame.passwordfield.getText());
				FileWriter fWriter;
				try {
					fWriter = new FileWriter("lycChat.json");
					ClientStart.jsonObject.write(fWriter);
					fWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				ClientStart.jsonObject.put("serveraddress","");
				ClientStart.jsonObject.put("username","");
				ClientStart.jsonObject.put("password","");
				FileWriter fWriter;
				try {
					fWriter = new FileWriter("lycChat.json");
					ClientStart.jsonObject.write(fWriter);
					fWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		case"Register":
			LoginFrame.registerframe=new RegisterFrame();
			LoginFrame.frame.setVisible(false);
			break;
		}
	}
}

class MyWindowsActionLisntener implements WindowListener{//窗口事件监听
	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowClosing(WindowEvent arg0) {
		System.out.println("Windows Closing...");
		ClientStart.stop();//当窗口关闭时停止
	}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
}