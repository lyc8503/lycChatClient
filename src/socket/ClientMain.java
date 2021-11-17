package socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

import gui.ChatFrame;
import gui.LoginFrame;
import gui.RegisterFrame;

public class ClientMain {
	@SuppressWarnings("deprecation")
	public static void flushstream(Socket socket) throws IOException{//更换服务器
		if(!(outputstream==null) | !(inputstream==null)){
			datareader.t.stop();
			datareader=null;
			datareader=new ReadData();
		}
		inputstream=new DataInputStream(socket.getInputStream());
		outputstream=new DataOutputStream(socket.getOutputStream());
	}
	@SuppressWarnings("deprecation")
	public static void stop(){//关闭连接
		try {
			outputstream.writeUTF("lycChat");
			outputstream.writeUTF("exit");
			datareader.t.stop();
			outputstream.close();
			inputstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	static{
		datareader=new ReadData();//读取来自服务器的数据
	}
	public static ReadData datareader;
	public static String data;
	public static DataOutputStream outputstream;//输入流与输出流
	public static DataInputStream inputstream;
	public static void changepassword(String Address,String username,String oldpassword,String newpassword){//更改密码
		Socket socket=getConnection.getSocket(Address);
		try {
			flushstream(socket);//更新服务器
			outputstream.writeUTF("lycChat");
			outputstream.writeUTF("changepassword");
			outputstream.writeUTF(username);
			outputstream.writeUTF(oldpassword);
			outputstream.writeUTF(newpassword);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void forgetpassword(String username,String Address){//忘记密码
		Socket socket=getConnection.getSocket(Address);
		try {
			flushstream(socket);//更新服务器
			outputstream.writeUTF("lycChat");
			outputstream.writeUTF("forgetpassword");
			outputstream.writeUTF(username);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void register(String username,String email,String password,String Address){//用于注册的方法
		Socket socket=getConnection.getSocket(Address);
		try {
			if(username.equals("") | email.equals("") | password.equals("")){
				JOptionPane.showMessageDialog(LoginFrame.frame,"用户名,email,密码为必填项","Error!",JOptionPane.DEFAULT_OPTION);
			}else{
				flushstream(socket);//更新服务器
				outputstream.writeUTF("lycChat");
				outputstream.writeUTF("reg");
				outputstream.writeUTF(username);
				outputstream.writeUTF(email);
				outputstream.writeUTF(password);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(LoginFrame.frame,"服务器IO错误,请检查网络连接或联系管理员","Error!",JOptionPane.DEFAULT_OPTION);
		}
	}
	public static void login(String username,String password,String Address){//用于登陆的方法
		if(password.length()<6){
			JOptionPane.showMessageDialog(LoginFrame.frame,"密码长度至少为6位","Hint",JOptionPane.DEFAULT_OPTION);
		}else{
			if(username=="" | password==""){
				JOptionPane.showMessageDialog(LoginFrame.frame,"用户名,密码为必填项","Error!",JOptionPane.DEFAULT_OPTION);
			}else{
				Socket socket=getConnection.getSocket(Address);
				try {
					flushstream(socket);//更新服务器
					outputstream.writeUTF("lycChat");
					outputstream.writeUTF("login");
					outputstream.writeUTF(username);
					outputstream.writeUTF(password);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(LoginFrame.frame,"服务器IO错误,请检查网络连接或联系管理员","Error!",JOptionPane.DEFAULT_OPTION);
				}
			}
		}
	}
}

class ReadData implements Runnable{
	Thread t;
	ReadData(){
		t=new Thread(this);
		t.start();
	}
	public void run() {
		System.out.println("Reading Data From Server...");
		while(true){
			try {
				String data=ClientMain.inputstream.readUTF();
//				System.out.println("Receive data"+data);
				switch(data){
				default:
					System.out.println("Invaild Packet!");
					break;
				case"privateMessage":
					String byuser1=ClientMain.inputstream.readUTF();
					String message1=ClientMain.inputstream.readUTF();
					MessageManager.receiveprivatemessage(byuser1,message1);
					break;
				case"publicMessage"://公共消息
//					System.out.println("publicmessgae");
					String byuser=ClientMain.inputstream.readUTF();
					String message=ClientMain.inputstream.readUTF();
					String date=ClientMain.inputstream.readUTF();	
					String email=ClientMain.inputstream.readUTF();
					MessageManager.addPublicMessage(byuser, message,date,email);
					MessageManager.flushpublicoutput();
					break;
				case"lycChatMessage"://显示提示
//					System.out.println("hint");
					String data1=ClientMain.inputstream.readUTF();
					JOptionPane.showMessageDialog(LoginFrame.frame,data1);
					break;
				case"newpricatechat":
					String privatechatname=ClientMain.inputstream.readUTF();
					MessageManager.receiveprivatechat(privatechatname);
					break;
				case"lycChatSystemInfo"://系统信息
//					System.out.println("info");
					String data2=ClientMain.inputstream.readUTF();
					switch(data2){
					case"privatechatsuccess":
						String data3=ClientMain.inputstream.readUTF();
						MessageManager.privatechatsuccess(data3);
						break;
					case"successlogin"://登陆成功
						try{
							LoginFrame.frame.setVisible(false);
							RegisterFrame.frame.setVisible(false);
						}catch(Exception e){
							//Ignore
						}
						new ChatFrame();
						break;
					default:
						System.out.println("Invaild Packet!");
						break;
					}
					break;
				}
			}catch (NullPointerException e) {
				//Ignore
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("ReadData Error!");
				JOptionPane.showMessageDialog(LoginFrame.frame,"服务器异常,请检查网络连接或联系服务器管理员");
				try {
					Runtime.getRuntime().exec("java -jar "+System.getProperty("java.class.path"));
					ClientMain.outputstream.writeUTF("lycChat");
					ClientMain.outputstream.writeUTF("exit");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		}
	}
}