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
		setTitle("���� â");
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
		JButton button = new JButton("����");
		
		class connectThread extends Thread{ 
			public void run() {
				
				while(true)
				{
					//����(Ŭ��)�κ�//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					button.setEnabled(false);
					print.append(setup.NickName+" ���� �غ� ��...\n");
					
			        try {
			        	int i=0;
			        	sleep(1000);
			            Socket clientSocket = null;

			            clientSocket = new Socket();
			            print.append(setup.NickName+" ���� �õ� ��...\n");
			            sleep(1000);
			            
			            print.append(input.getText()+"���� �����մϴ�..\n");
			            clientSocket.connect(new InetSocketAddress(input.getText(), 8544));
			            
			            print.append(setup.NickName+" ���� ����!\n");
			            sleep(300);
			            print.append(setup.NickName+" ���� ���� �õ�\n");
			            sleep(300);
			           
			            
			          ///////////////////���� ��////////////////////
			            new Client2(clientSocket);
			            return;
			            
			            

			            
			        } catch (Exception ee) {
			        }
			        print.append("���� �߻�.. �ٽ� �������ּ���.\n");
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
			print.append("�����Ϸ��� �������ּҸ� ���� ���ӹ�ư�� �����ÿ�..\n");
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