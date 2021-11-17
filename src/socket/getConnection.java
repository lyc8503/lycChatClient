package socket;

import java.net.Socket;
import javax.swing.JOptionPane;

import gui.LoginFrame;

public class getConnection {
	public static Socket getSocket(String address){
		StringBuffer str=new StringBuffer(address);//����������ݷָ�ΪIP�Ͷ˿�
		String add="";
		String port="";
		for(int i=0;i<address.length();i++){
			if(str.charAt(i)==':'){
				add=str.substring(0,i);
				port=str.substring(i+1,address.length());
			}
		}
		System.out.println("Address:"+add);
		System.out.println("port:"+port);
		Socket socket;
		try {
			socket = new Socket(add,Integer.parseInt(port));//���ӷ�����
			return socket;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(LoginFrame.frame,"���ӷ�����ʱ���ִ���,��������ȷ�ķ�������ַ","Error!",JOptionPane.DEFAULT_OPTION);
			return null;
		}
	}
}
