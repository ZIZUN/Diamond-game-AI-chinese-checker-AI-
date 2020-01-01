import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;



public class Client_wait extends JFrame{
	String nickname = ""; 

	TextField tf = new TextField(); 
	DataInputStream in; 
	DataOutputStream out; 
	public Client_wait() 
	{ 
		setTitle("접속 창");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		TextArea ta = new TextArea(); 
		 
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		c.add(new Panel());
		
		setSize(330,225);
		setLocation(580,255);
		setVisible(true);
	}
	class Panel extends JPanel
	{
		
		
		JTextField input = new JTextField(20);
		TextArea print = new TextArea(9,42);
		JButton button = new JButton("접속");
		
		class connectThread extends Thread{ 
			public void run() {
				
				while(true)
				{
					//소켓(클라)부분//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					button.setEnabled(false);
					print.append(setup.NickName+" 접속 준비 중...\n");
					
			        try {
			        	int i=0;
			        	sleep(1000);
			            Socket clientSocket = null;

			            clientSocket = new Socket();
			            print.append(setup.NickName+" 접속 시도 중...\n");
			            sleep(1000);
			            
			            print.append(input.getText()+"으로 접속합니다..\n");
			            clientSocket.connect(new InetSocketAddress(input.getText(), 8544));
			            
			            print.append(setup.NickName+" 접속 성공!\n");
			            sleep(300);
			            print.append(setup.NickName+" 게임 참여 시도\n");
			            sleep(300);
			           
			            
			          ///////////////////연결 후////////////////////
			            new Client2(clientSocket);
			            return;
			            
			            

			            
			        } catch (Exception ee) {
			        }
			        print.append("에러 발생.. 다시 접속해주세요.\n");
			        button.setEnabled(true);
			        return;
			        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				}
			}
		}
		
		
		public Panel()
		{
			setResizable(false);
			add(input);
			add(button);
			add(print);
			
			print.setEditable(false);
			setResizable(false); 
			print.append("접속하려면 아이피주소를 적고 접속버튼을 누르시오..\n");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					connectThread a = new connectThread();
					a.start();
					
				}
			});
			

			
		}
	}
}