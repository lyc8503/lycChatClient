package socket;

import java.net.Socket;
import javax.swing.JOptionPane;

import gui.LoginFrame;

public class getConnection {
	public static Socket getSocket(String address){
		StringBuffer str=new StringBuffer(address);//将输入的数据分割为IP和端口
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
			socket = new Socket(add,Integer.parseInt(port));//连接服务器
			return socket;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(LoginFrame.frame,"连接服务器时出现错误,请输入正确的服务器地址","Error!",JOptionPane.DEFAULT_OPTION);
			return null;
		}
	}
}
