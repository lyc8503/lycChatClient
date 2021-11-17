package start;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gui.LoginFrame;
import net.sf.json.JSONObject;
import socket.ClientMain;

public class ClientStart {
	public static LoginFrame loginframe;
	public static JSONObject jsonObject;
	public static void main(String args[]){//程序开始
		System.out.println("本软件为LycChatClient Beta,与LycChatServer配合使用");
		System.out.println("警告:本软件连接没有加密,请确保在私人网络中使用");
		System.out.println("Starting...");
		
		File configfile=new File("lycChat.json");//读取设置文件
		System.out.println("Reading Config...");
		if(!configfile.exists()){
			System.out.println("FileNotFound!");
			try {
				configfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				FileWriter fileWriter=new FileWriter("lycChat.json");
				fileWriter.write("{}");
				fileWriter.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(configfile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		char buffer[]=new char[6400];
		try {
			fileReader.read(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String json=String.valueOf(buffer);
		jsonObject=JSONObject.fromObject(json);
		
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			    if ("Nimbus".equals(info.getName())) {
			        UIManager.setLookAndFeel(info.getClassName());
			        break;
			    }
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		loginframe=new LoginFrame();//创建登陆窗口
		JOptionPane.showMessageDialog(LoginFrame.frame, "本软件为LycChatClient\n与LycChatServer配合使用\nBUG反馈:lyc8503@gmail.com","Hint", JOptionPane.PLAIN_MESSAGE);
	}
	public static void stop(){
		System.out.println("Exiting...");
		ClientMain.stop();
		System.out.println("Stopped!");
		System.exit(0);//结束程序
	}
}
