package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import socket.ClientMain;

public class RegisterFrame {
	public static int frameheight;
	public static int framewidth;
	static{//设置窗口高度与宽度
		frameheight=250;
		framewidth=380;
	}
	public static JFrame frame;
	public static JTextField serverfield;
	public static JTextField usernamefield;
	public static JTextField emailfield;
	public static JPasswordField passwordfield;
	public static JPasswordField confirmfield;
	public static GridBagLayout layout;
	public static GridBagConstraints constraints;
	public static JButton submitbutton;
	public RegisterFrame(){
		frame=new JFrame("Register");
		layout=new GridBagLayout();
		constraints=new GridBagConstraints();
		frame.addWindowListener(new MyWindowsActionListener());
		frame.setLayout(layout);
		serverfield=new JTextField();
		usernamefield=new JTextField();
		emailfield=new JTextField();
		passwordfield=new JPasswordField();
		confirmfield=new JPasswordField();
		submitbutton=new JButton("Submit");
		submitbutton.addActionListener(new RegisterFrameActionListener());
		JLabel serverlabel=new JLabel("ServerAddress:");
		JLabel namelabel=new JLabel("Name:");
		JLabel emaillabel=new JLabel("Email:");
		JLabel passwordlabel=new JLabel("Password:");
		JLabel confirmlabel=new JLabel("Confirm Password:");
		frame.setLayout(layout);
		
		constraints.gridx=0;//开始添加组件
		constraints.gridy=0;
		constraints.gridheight=1;
		constraints.gridwidth=1;
		constraints.ipadx=100;
		constraints.ipady=10;
		constraints.fill=GridBagConstraints.BOTH;
		frame.getContentPane().add(serverlabel);
		layout.setConstraints(serverlabel,constraints);
		
		constraints.gridx=1;
		constraints.gridy=0;
		constraints.gridheight=1;
		constraints.gridwidth=1;
		constraints.ipadx=100;
		frame.getContentPane().add(serverfield);
		layout.setConstraints(serverfield, constraints);
		
		constraints.gridx=0;
		constraints.gridy=1;
		constraints.gridheight=1;
		constraints.gridwidth=1;
		constraints.ipadx=100;
		frame.getContentPane().add(namelabel);
		layout.setConstraints(namelabel,constraints);
		
		constraints.gridx=1;
		constraints.gridy=1;
		constraints.gridheight=1;
		constraints.gridwidth=1;
		constraints.ipadx=100;
		frame.getContentPane().add(usernamefield);
		layout.setConstraints(usernamefield, constraints);
		
		constraints.gridx=0;
		constraints.gridy=2;
		frame.getContentPane().add(emaillabel);
		layout.setConstraints(emaillabel,constraints);
		
		constraints.gridx=1;
		constraints.gridy=2;
		frame.getContentPane().add(emailfield);
		layout.setConstraints(emailfield, constraints);
		
		constraints.gridx=0;
		constraints.gridy=3;
		frame.getContentPane().add(passwordlabel);
		layout.setConstraints(passwordlabel,constraints);
		
		constraints.gridx=1;
		constraints.gridy=3;
		frame.getContentPane().add(passwordfield);
		layout.setConstraints(passwordfield, constraints);
		
		constraints.gridx=0;
		constraints.gridy=4;
		frame.getContentPane().add(confirmlabel);
		layout.setConstraints(confirmlabel,constraints);
		
		constraints.gridx=1;
		constraints.gridy=4;
		frame.getContentPane().add(confirmfield);
		layout.setConstraints(confirmfield, constraints);
		
		constraints.gridx=0;
		constraints.gridy=5;
		constraints.gridwidth=2;
		frame.getContentPane().add(submitbutton);
		layout.setConstraints(submitbutton, constraints);
		
		

		frame.setResizable(false);
		frame.setVisible(true);//将窗口设置在屏幕中间
		frame.setLocation(((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - framewidth)/2,((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()-frameheight)/2);
		frame.setSize(framewidth, frameheight);
	}
}

class RegisterFrameActionListener implements ActionListener{//用于检测本窗口的按钮行为
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent arg0) {
		if(RegisterFrame.passwordfield.getText().equals(RegisterFrame.confirmfield.getText())){
			if(RegisterFrame.passwordfield.getText().length()<6){
				JOptionPane.showMessageDialog(RegisterFrame.frame,"密码长度至少6位","Hint",JOptionPane.DEFAULT_OPTION);
			}else{
				ClientMain.register(RegisterFrame.usernamefield.getText(),RegisterFrame.emailfield.getText(),RegisterFrame.passwordfield.getText(),RegisterFrame.serverfield.getText());
			}
		}else{
			JOptionPane.showMessageDialog(RegisterFrame.frame,"两次密码不同,请重新输入","Hint",JOptionPane.DEFAULT_OPTION);
		}
	}
}

class MyWindowsActionListener implements WindowListener{
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {
		LoginFrame.frame.setVisible(true);//注册窗口关闭后,恢复登陆窗口
	}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
}