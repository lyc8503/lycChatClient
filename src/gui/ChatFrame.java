package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import socket.ClientMain;
import socket.MessageManager;
import start.ClientStart;

public class ChatFrame {
	public static JFrame frame;
	
	public static JMenuBar menubar;//�˵�
	public static JMenu helpmenu;//�����˵�
	public static JMenuItem aboutmenuitem;//���ڲ˵�
	public static JMenu chat;//��������
	public static JMenuItem setfont;
	public static JMenuItem savemessages;
	public static JMenu account;//�˺�
	public static JMenuItem logout;
	public static JMenu privatechat;//˽�Ĳ˵�
	public static JMenuItem privatechatlist;
	public static JMenuItem newprivatechat;
	public static JMenuItem ignoreprivatechat;
	
	
	public static JTextField messagefield;
	public static JButton sendbutton;
	public static JTextArea showmessagearea;
	public static JScrollPane scrollpane;
	public static JPopupMenu popupMenu;
	public static JMenuItem cleartext;
	
	public static GridBagLayout layout;
	public static GridBagConstraints constraints;
	public ChatFrame(){//Ⱥ�Ĵ���
		frame=new JFrame("Public");
		
		menubar=new JMenuBar();//�˵���
		privatechat=new JMenu("˽��");
		privatechatlist=new JMenuItem("˽���б�");
		newprivatechat=new JMenuItem("����˽��");
		ignoreprivatechat=new JMenuItem("�ر�˽����ʾ");
		privatechatlist.addActionListener(new MenuActionListener());
		newprivatechat.addActionListener(new MenuActionListener());
		ignoreprivatechat.addActionListener(new MenuActionListener());
		privatechat.add(privatechatlist);
		privatechat.add(newprivatechat);
		privatechat.add(ignoreprivatechat);
		menubar.add(privatechat);
		setfont=new JMenuItem("��������");
		setfont.addActionListener(new MenuActionListener());
		savemessages=new JMenuItem("���������¼");
		savemessages.addActionListener(new MenuActionListener());
		chat=new JMenu("��������");
		chat.add(setfont);
		chat.add(savemessages);
		menubar.add(chat);
		logout=new JMenuItem("�˳���½");
		logout.addActionListener(new MenuActionListener());
		account=new JMenu("�˺�");
		account.add(logout);
		menubar.add(account);
		helpmenu=new JMenu("����");
		aboutmenuitem=new JMenuItem("����lycChat");
		menubar.add(helpmenu);
		helpmenu.add(aboutmenuitem);
		aboutmenuitem.addActionListener(new MenuActionListener());
		frame.setJMenuBar(menubar);
		
		messagefield=new JTextField("Type message here!");//������
		sendbutton=new JButton("Send");
		sendbutton.addActionListener(new SendButtonActionListener());
		showmessagearea=new JTextArea("Welcome to use lycChat!");
		showmessagearea.setEditable(false);
		layout=new GridBagLayout();
		constraints=new GridBagConstraints();
		
		popupMenu=new JPopupMenu();
		cleartext=new JMenuItem("��������¼");
		cleartext.addActionListener(new MenuActionListener());
		popupMenu.add(cleartext);
		showmessagearea.setComponentPopupMenu(popupMenu);
		
		constraints.fill=GridBagConstraints.BOTH;
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridheight=1;
		constraints.gridwidth=2;
		constraints.weightx=1;
		constraints.weighty=0.9;
		//��ȡ�����ļ�
		try{
			Font font=new Font((String)ClientStart.jsonObject.get("font"), 0, (int) ClientStart.jsonObject.get("size"));
			showmessagearea.setFont(font);
		}catch (Exception e) {
			e.printStackTrace();
		}
		scrollpane=new JScrollPane(showmessagearea);
		layout.setConstraints(scrollpane, constraints);
		frame.getContentPane().add(scrollpane);
		
		constraints.gridx=0;
		constraints.gridy=1;
		constraints.weightx=0.8;
		constraints.weighty=0.1;
		constraints.gridheight=1;
		constraints.gridwidth=1;
		layout.setConstraints(messagefield, constraints);
		frame.getContentPane().add(messagefield);
		messagefield.addActionListener(new SendButtonActionListener());
		
		constraints.gridx=1;
		constraints.gridy=1;
		constraints.weightx=0.2;
		constraints.weighty=0.1;
		constraints.gridheight=1;
		constraints.gridwidth=1;
		layout.setConstraints(sendbutton, constraints);
		frame.getContentPane().add(sendbutton);
		
		frame.setLayout(layout);
		frame.addWindowListener(new ChatFrameWindowActionLisntener());
		frame.setSize(500, 300);
		frame.setResizable(true);
		frame.setVisible(true);
	}
}

class MenuActionListener implements ActionListener{//�����˵�����Ϊ
	public void actionPerformed(ActionEvent event) {
		switch(event.getActionCommand()){
		case"˽���б�":
			PrivateChat.showpanel();
			break;
		case"����˽��":
			String toname = (String)JOptionPane.showInputDialog(LoginFrame.frame,
			        "����Է�����", "����˽��",
			        JOptionPane.QUESTION_MESSAGE,null, null, "");
			if(toname==null){
				JOptionPane.showMessageDialog(ChatFrame.frame,"���Ʋ���Ϊ��!" ,"Warning",JOptionPane.PLAIN_MESSAGE);
				break;
			}
			if(toname.equals("")){
				JOptionPane.showMessageDialog(ChatFrame.frame,"���Ʋ���Ϊ��!" ,"Warning",JOptionPane.PLAIN_MESSAGE);
				break;
			}
			MessageManager.newprivatechat(toname);
			break;
		case"�ر�˽����ʾ":
			MessageManager.ignore=true;
			break;
		case"����lycChat":
			 JOptionPane.showMessageDialog(ChatFrame.frame,"����:lyc8503"+"\n"+"Email:lyc8503@gmail.com"+"\n"+"lycChat�汾:Beta 1.0" ,"lycChat",JOptionPane.PLAIN_MESSAGE);
			 break;
		case"��������¼":
			MessageManager.clearPublicMessage();
			break;
		case"��������":
			String font = (String)JOptionPane.showInputDialog(LoginFrame.frame,
			        "������������", "��������",
			        JOptionPane.QUESTION_MESSAGE,null, null, "");
			int size = Integer.valueOf((String)JOptionPane.showInputDialog(LoginFrame.frame,
			        "�����ֺ�", "��������",
			        JOptionPane.QUESTION_MESSAGE,null, null, ""));
			ChatFrame.showmessagearea.setFont(new Font(font,0,size));
			ClientStart.jsonObject.put("font", font);
			ClientStart.jsonObject.put("size",size);
			FileWriter fWriter;//���浽�����ļ�
			try {
				fWriter = new FileWriter("lycChat.json");
				ClientStart.jsonObject.write(fWriter);
				fWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case"���������¼":
			MessageManager.savemessages();
			break;
		case"�˳���½":
			try {
				ClientMain.outputstream.writeUTF("lycChat");
				ClientMain.outputstream.writeUTF("exit");
				Runtime.getRuntime().exec("java -jar "+System.getProperty("java.class.path"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.exit(0);
			break;
		}
	}
}

class SendButtonActionListener implements ActionListener{//������ť��Ϊ
	public void actionPerformed(ActionEvent event) {
		String message;
		message=ChatFrame.messagefield.getText();
		if(!message.equals("")){
			System.out.println("Send:"+message);//������Ϣ
			try {
				ClientMain.outputstream.writeUTF("lycChat");
				ClientMain.outputstream.writeUTF("message");
				ClientMain.outputstream.writeUTF(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
			ChatFrame.messagefield.setText("");
		}
	}
}

class ChatFrameWindowActionLisntener implements WindowListener{//�����¼�����
	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowClosing(WindowEvent arg0) {
		System.out.println("Windows Closing...");
		ClientStart.stop();//�����ڹر�ʱֹͣ
	}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
}