package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import socket.MessageManager;
import socket.PublicMessage;
import start.ClientStart;

public class PrivateChat {
	public static ControlDialog dialog;
	public static Vector<PrivateChatFrame> privateChatFramevector=new Vector<PrivateChatFrame>();
	public static GUIRefresher guiRefresher=new GUIRefresher();
	public static Vector<String> privatechatvector=new Vector<String>();
	public static void flushoutput(PrivateChatFrame frame){
		StringBuilder allMessage=new StringBuilder("");
		for(int i=0;frame.messagevector.size()>i;i++){
			allMessage.append(frame.messagevector.get(i).getByuser()+"\n"+frame.messagevector.get(i).getMessage()+"\n");
		}
		frame.showmessagearea.setText(allMessage.toString());
		frame.scrollpane.getVerticalScrollBar().setValue(frame.scrollpane.getVerticalScrollBar().getMaximum()+5);
	}
	public static void privatechatmessage(String byuser,String message){
		System.out.println("Private Message From "+byuser+" : "+message);
		PrivateChatFrame privateChatFrame=null;
		for(PrivateChatFrame privateChatFrame1:privateChatFramevector){
			if(privateChatFrame1.name.equals(byuser)){
				privateChatFrame=privateChatFrame1;
			}
		}
		PublicMessage message2=new PublicMessage();
		message2.setByuser(byuser);
		message2.setMessage(message);
		try {
			privateChatFrame.messagevector.add(message2);
			PrivateChat.flushoutput(privateChatFrame);
		} catch (Exception e) {
			//Ignore
		}
	}
	public static void showprivatechat(String name){
//		System.out.println("TEST");
		PrivateChatFrame privatechatframe=null;
		for(PrivateChatFrame privateChatFrame1:privateChatFramevector){
			if(privateChatFrame1.name.equals(name)){
				privatechatframe=privateChatFrame1;
			}
		}
		if(privatechatframe!=null){
//			System.out.println("TEST1");
			privatechatframe.frame.setLocation(0, 0);
			privatechatframe.frame.setVisible(true);
			privatechatframe.frame.setSize(500, 300);
		}
	}
	public static void showpanel(){
		System.out.println("Show Private Chat Panel");
		if(dialog==null){
			dialog=new ControlDialog();
		}
		ControlDialog.frame.setVisible(true);
		ControlDialog.frame.setSize(300, 200);
		ControlDialog.frame.setLocation(0,0);
	}
	public static void newframe(String name){
		boolean duplicated=false;
		for(String s:PrivateChat.privatechatvector){
			if(s.equals(name)){
				duplicated=true;
			}
		}
		if(!duplicated){
			PrivateChat.privatechatvector.addElement(name);
			privateChatFramevector.addElement(new PrivateChatFrame(name));
		}
	}
}

class ControlDialog{//私聊列表
	public static JFrame frame;
	public static GridBagLayout layout;
	public static GridBagConstraints constraints;
	public static DefaultListModel<String> listModel=new DefaultListModel<String>();
	public static JList<String> list=new JList<String>(listModel);
	public static JButton openbutton;//打开选中的私聊窗口
	public ControlDialog() {
		frame=new JFrame("Private Chat");
		layout=new GridBagLayout();
		constraints=new GridBagConstraints();
		frame.setLayout(layout);
		constraints.ipadx=300;
		constraints.ipady=50;
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.weighty=0.9;
		constraints.fill=GridBagConstraints.BOTH;
		JScrollPane scrollPane=new JScrollPane(list);
		layout.setConstraints(scrollPane, constraints);
		frame.getContentPane().add(scrollPane);
		openbutton=new JButton("Open");
		openbutton.addActionListener(new PrivateChatActionListener());
		constraints.gridx=0;
		constraints.gridy=1;
		constraints.ipadx=0;
		constraints.ipady=0;
		constraints.weighty=0.1;
		layout.setConstraints(openbutton, constraints);
		frame.getContentPane().add(openbutton);
	}
}
class PrivateChatActionListener implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case"Open":
			String name=ControlDialog.list.getModel().getElementAt(ControlDialog.list.getSelectedIndex());
//			System.out.println("TEST "+name);
			PrivateChat.showprivatechat(name);
			break;
		}
	}
}

class GUIRefresher implements Runnable{
	public GUIRefresher() {
		Thread thread=new Thread(this);
		thread.start();
	}
	public void run() {
		while(true){
			try {
				ControlDialog.listModel.removeAllElements();
				Vector<String> privatechatvector = PrivateChat.privatechatvector;
				for (int i = 0; i < privatechatvector.size(); i++) {
					String message = privatechatvector.get(i);
					ControlDialog.listModel.addElement(message);
//					System.out.println("TEST");
				}
			} catch (Exception e) {
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class PrivateChatFrame{//私聊窗口
	public String name;
	public Vector<PublicMessage> messagevector;
	public JFrame frame;
	public JMenuBar menubar;
	public JMenu helpmenu;
	public JMenuItem aboutmenuitem;
	public JTextField messagefield;
	public JButton sendbutton;
	public JTextArea showmessagearea;
	public JScrollPane scrollpane;
	public GridBagLayout layout;
	public GridBagConstraints constraints;
	public PrivateChatFrame(String name) {
		PrivateMessageListener listener=new PrivateMessageListener(this);
		messagevector=new Vector<PublicMessage>();
		this.name=name;
		frame=new JFrame("PrivateChat To "+name);
		menubar=new JMenuBar();//菜单项
		helpmenu=new JMenu("帮助");
		aboutmenuitem=new JMenuItem("关于lycChat");
		menubar.add(helpmenu);
		helpmenu.add(aboutmenuitem);
		aboutmenuitem.addActionListener(new MenuActionListener());
		frame.setJMenuBar(menubar);
		
		messagefield=new JTextField("Type message here!");//添加组件
		sendbutton=new JButton("Send");
		showmessagearea=new JTextArea("Welcome to use lycChat!");
		showmessagearea.setEditable(false);
		layout=new GridBagLayout();
		constraints=new GridBagConstraints();
		
		constraints.fill=GridBagConstraints.BOTH;
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridheight=1;
		constraints.gridwidth=2;
		constraints.weightx=1;
		constraints.weighty=0.9;
		//读取配置文件
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
		messagefield.addActionListener(listener);
		
		constraints.gridx=1;
		constraints.gridy=1;
		constraints.weightx=0.2;
		constraints.weighty=0.1;
		constraints.gridheight=1;
		constraints.gridwidth=1;
		layout.setConstraints(sendbutton, constraints);
		frame.getContentPane().add(sendbutton);
		sendbutton.addActionListener(listener);
		
		frame.setLayout(layout);
	}
}

class PrivateMessageListener implements ActionListener{
	PrivateChatFrame frame;
	public PrivateMessageListener(PrivateChatFrame frame) {
		this.frame=frame;
	}
	public void actionPerformed(ActionEvent e) {
		String message=frame.messagefield.getText();
		if(!message.equals("")){
			MessageManager.sendprivatemessage(frame.name, message);
			PublicMessage message2=new PublicMessage();
			message2.setByuser("MySelf");
			message2.setMessage(message);
			frame.messagevector.addElement(message2);
			PrivateChat.flushoutput(frame);
			frame.messagefield.setText("");
		}	
	}
}