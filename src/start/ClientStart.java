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
	public static void main(String args[]){//����ʼ
		System.out.println("�����ΪLycChatClient Beta,��LycChatServer���ʹ��");
		System.out.println("����:���������û�м���,��ȷ����˽��������ʹ��");
		System.out.println("Starting...");
		
		File configfile=new File("lycChat.json");//��ȡ�����ļ�
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
		
		loginframe=new LoginFrame();//������½����
		JOptionPane.showMessageDialog(LoginFrame.frame, "�����ΪLycChatClient\n��LycChatServer���ʹ��\nBUG����:lyc8503@gmail.com","Hint", JOptionPane.PLAIN_MESSAGE);
	}
	public static void stop(){
		System.out.println("Exiting...");
		ClientMain.stop();
		System.out.println("Stopped!");
		System.exit(0);//��������
	}
}
