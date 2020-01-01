import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Server2 extends JFrame {
	String nickname = ""; 
	TextArea ta = new TextArea(); 
	TextField tf = new TextField(); 
	DataInputStream in; 
	DataOutputStream out; 
	public Server2() 
	{ 
		setTitle("Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		Container c = getContentPane();
		c.setLayout(null);
		c.add(new Panel());

		setSize(230,380);
		setLocation(580,200);
		setVisible(true);
		
		
			
		
	} 
	public class Panel extends JPanel{
		
		public Panel(){
			//String nickname = ""; 
			TextArea taa = ta; 

			DataInputStream inn= in; 
			DataOutputStream outt = out; 
			int n;
			
			Container d = getContentPane();
			
			d.setLayout(null);
			taa.setSize(222, 350);
			d.add(taa);
			

			taa.setEditable(false);
			setResizable(false); 
			setVisible(true);

			serverstart(); 
		}
	}
	

	
	
	void serverstart() {
		ta.append("SERVER START\n");       
		
        try {

            ServerSocket mainServerSocket = null;
            mainServerSocket = new ServerSocket();
            mainServerSocket.bind(new InetSocketAddress("127.0.0.1", 8544));
            
            
            
            ConnectThread connectThread = new ConnectThread(mainServerSocket);
            connectThread.start();
            
            Socket clientSocket = new Socket();
            clientSocket.connect(new InetSocketAddress("127.0.0.1", 8544));
            new Client2_Server(clientSocket);
            
            
           TimeSendThread timersendthread = new TimeSendThread();
            timersendthread.start();
            
        } catch (Exception e) {}
        //ta.append("-SERVER 종료\n");
	}
	
	class UserInfo {
	    Socket serverSocket;
	 
	    UserInfo(Socket serverSocket) {
	        this.serverSocket = serverSocket;
	    }
	}
	
	
	int n = setup.Time;
	class TimerThread extends Thread{  // 타이머 부분 스레드
		public TimerThread() {
		}
		
		public void run() {
			
			while(true)
			{
				try {
					timeset();
					Thread.sleep(1000);
				}
				catch(InterruptedException e)
				{
					return ;
				}
			}
		}
	}
	
	synchronized void timeset()
	{
		if(n==0) {
			try {
				ta.append("TIME SET\n");
				wait();
			}catch(InterruptedException e) {return ;}
			
		}
		n--;
		
	}
	

	class TimeSendThread extends Thread { //타이머지시에 따라 보내는 스레드
	    public synchronized void run() {
	    	
	        try {
	        	
	        	while (true) {	
	        		if(n==0)
	            	{
	            		for (int i = 0; i < li.size(); i++) {
	            			OutputStream outputStream = li.get(i).serverSocket.getOutputStream(); 
	            			byte[] byteArray = new byte[256];
	            			String a = "TIMESET_";
	            			
	            			byteArray =  a.getBytes("UTF-8");  
	                        outputStream.write(byteArray);
		            	}
	            		ta.append("TIMESET_\n");
	            		n=setup.Time;
	            		notify();
	            	}
	            	sleep(500);
	            	
	            }
	        	
	        } catch (Exception e) {}
	    }
	}//
	TimerThread timerthread = new TimerThread();
	class UserThread extends Thread {     // 클라한테서 정보받고 그 정보 뿌리는 스레드 (클라이언트 각각)
	    Socket serverSocket;
	 
	    UserThread(Socket serverSocket) {
	        this.serverSocket = serverSocket;
	    }
	 
	    @Override
	    public void run() {
	        try {
	            while (true) {
	            	
	            	
	                InputStream inputStream = serverSocket.getInputStream(); //입력스트림  serversocket 각 클라에 대응되는 클라이언트 소켓임
	                byte[] byteArray = new byte[256];
	                int size = inputStream.read(byteArray);
	                
	                if (size == -1) {
	                    break;
	                }
	                
	                for (int i = 0; i < li.size(); i++) {
	                	if (li.get(i).serverSocket != serverSocket) { //자기자신이 아니라면
	                        OutputStream outputStream = li.get(i).serverSocket.getOutputStream(); 
	                        outputStream.write(byteArray);// byteArray를 각 유저들에게 모두 전송 
	                        	
	                    }
	                }
	                
	                String sendMessage = new String(byteArray, 0, size, "UTF-8");
	                ta.append(sendMessage+"\n");//서버에 먼저 출력
	                
	                String s[] = sendMessage.split("_");
                    
	                if(s[0].equals("GO") || s[0].equals("DONTMOVE"))//go or donmove 받으면 타이머 20으로 초기화
	                {
	                	n=setup.Time;
	                }
	                else if(s[0].equals("GAMESTART"))
	                {
	                	if(li.size()==1)
	                		ta.append("혼자서 게임불가..\n다시 시작하십시오\n");
	                	else
	                	{
	                		n=setup.Time;
		                	
		                    timerthread.start();
		                    
		                    byte[] byteArray2 = new byte[256];
	            			String dd = "TOTALPLAYER_"+li.size()+"_\n";
	            			byteArray2 =  dd.getBytes("UTF-8");
	            			ta.append(dd);
		                    for (int i = 0; i < li.size(); i++) {
			                        OutputStream outputStream = li.get(i).serverSocket.getOutputStream(); 
			                        outputStream.write(byteArray2);// byteArray를 각 유저들에게 모두 전송 
			                }
	                	}
	                }
	                else if(s[0].equals("EXIT") && s[1].equals("PLAYER"))
	                {
	                	li.remove(Integer.parseInt(s[2])-1);
	                }
	                else if(s[0].equals("TIMEcorrecT"))
	                {
	                	n= Integer.parseInt(s[1]);
	                }
	                else if(s[0].equals("GAMEOVER"))
	                {
	                	timerthread.stop();
	                }
	                	                //
	                
	            }
	        } catch (Exception e) {

	        }
	    }
	}//
	 int playerinfo=1;
	List<UserInfo> li = new ArrayList<UserInfo>();
	
	
	class ConnectThread extends Thread { //연결스레드
	    ServerSocket mainServerSocket = null;
	    
	    
	    ConnectThread(ServerSocket mainServerSocket) {
	        this.mainServerSocket = mainServerSocket;
	    }
	 
	    @Override
	    public void run() {
	        try {
	            while (true) {
	            	 
	                Socket serverSocket = mainServerSocket.accept(); // 클라이언트 한명 접속할때까지 대기..
	                
	                String in = "PLAYER"+playerinfo+"_in_\n" ;
	                ta.append(in);
	                
	                sleep(1000);
	               
	                OutputStream outputuser = serverSocket.getOutputStream(); 
        			byte[] byteArr = new byte[256];
        			String d = "YOU_PLAYER"+playerinfo+"_\n";
        			ta.append(d);
        			byteArr =  d.getBytes("UTF-8");
        			outputuser.write(byteArr);
        			playerinfo++;
        			
        			
        			
	                li.add(new UserInfo(serverSocket));  //지금 들어온 클라 리스트에 추가
	                UserThread userThread = new UserThread(serverSocket);
	                userThread.start(); // 지금 들어온 클라에 대한 스레드 시작
	                try {
                		for (int j = 0; j < li.size(); j++) {
	            			OutputStream outputStream = li.get(j).serverSocket.getOutputStream(); 
	            			byte[] byteArray = new byte[256];
	            			
	            			byteArray =  in.getBytes("UTF-8");  
	                        outputStream.write(byteArray);
		            	}
        				
        			}catch(Exception ce) {
        			}
	 
	            }
	        } catch (Exception e) {}
	    }
	}//
	
    public static void main(String[] args) {        
       new Server2();
    }
}
