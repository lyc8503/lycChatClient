package socket;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;

import gui.ChatFrame;
import gui.PrivateChat;

public class MessageManager {
	public static boolean ignore=false;
	public static Vector<PublicMessage> publicmessageVector;
	public static void receiveprivatemessage(String byuser,String message){//˽����Ϣ
		PrivateChat.privatechatmessage(byuser, message);
	}
	public static void sendprivatemessage(String toname,String message){
		System.out.println("PrivateMessage to "+toname+" : "+message);
		try {
			ClientMain.outputstream.writeUTF("lycChat");
			ClientMain.outputstream.writeUTF("privatemessage");
			ClientMain.outputstream.writeUTF(toname);
			ClientMain.outputstream.writeUTF(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void receiveprivatechat(String name){//����˽��
		System.out.println("NewPrivateChat From "+name);
		PrivateChat.newframe(name);
		if(!ignore){
			JOptionPane.showMessageDialog(ChatFrame.frame,"���� "+name+" ��˽������!"+"\n�������˽���б��д�");
		}
	}
	public static void privatechatsuccess(String name){
		JOptionPane.showMessageDialog(ChatFrame.frame,"���� "+name+" ������������!"+"\n�������˽���б��д�" ,"Hint",JOptionPane.PLAIN_MESSAGE);
		PrivateChat.newframe(name);
	}
	public static void newprivatechat(String toname){//�µ�˽��
		try {
			System.out.println("NewPrivateChat to "+toname);
			ClientMain.outputstream.writeUTF("lycChat");
			ClientMain.outputstream.writeUTF("newprivatemessage");
			ClientMain.outputstream.writeUTF(toname);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	static{
		publicmessageVector =new Vector<PublicMessage>();
	}
	public static void addPublicMessage(String byuser,String message,String date,String email){
		PublicMessage publicmessage=new PublicMessage();
		System.out.println("Receive:"+message);
		publicmessage.setByuser(byuser);
		publicmessage.setMessage(message);
		publicmessage.setDate(date);
		publicmessage.setEmail(email);
		publicmessageVector.addElement(publicmessage);
	}
	public static void flushpublicoutput(){
		StringBuilder allMessage=new StringBuilder("");
		for(int i=0;publicmessageVector.size()>i;i++){
			allMessage.append(publicmessageVector.get(i).getByuser()+" ("+publicmessageVector.get(i).getEmail()+") "+publicmessageVector.get(i).getDate()+"\n"+publicmessageVector.get(i).getMessage()+"\n");
		}
		ChatFrame.showmessagearea.setText(allMessage.toString());
		ChatFrame.scrollpane.getVerticalScrollBar().setValue(ChatFrame.scrollpane.getVerticalScrollBar().getMaximum()+5);
	}
	public static void clearPublicMessage(){
		publicmessageVector.removeAllElements();
		flushpublicoutput();
		ChatFrame.showmessagearea.setText("Messages Cleared!");
	}
	public static void savemessages(){
		System.out.println("Saving Messages...");
		if(publicmessageVector.size()==0){
			JOptionPane.showMessageDialog(ChatFrame.frame,"���������¼!" ,"Hint",JOptionPane.PLAIN_MESSAGE);
			return;
		}
		String allMessage="";
		for(int i=0;publicmessageVector.size()>i;i++){
			allMessage+=(publicmessageVector.get(i).getByuser()+" ("+publicmessageVector.get(i).getEmail()+") "+publicmessageVector.get(i).getDate()+"\r\n"+publicmessageVector.get(i).getMessage()+"\r\n");
		}
		File file=new File("lycChatMessages.txt");
		if(file.exists()){
			int n = JOptionPane.showConfirmDialog(null, "lycChatMessages.txt�Ѿ�����,�Ƿ��滻?", "Warning", JOptionPane.YES_NO_OPTION);
			if (n==JOptionPane.YES_OPTION) {
				file.delete();
			}else{
				return;
			}
		}
		try {
			FileWriter fw=new FileWriter(file);
			fw.write(allMessage);
			fw.close();
			JOptionPane.showMessageDialog(ChatFrame.frame,"�����¼�ѱ�����lycChatMessages.txt!" ,"Hint",JOptionPane.PLAIN_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}