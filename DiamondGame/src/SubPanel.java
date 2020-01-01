import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class SubPanel extends JFrame{
	public SubPanel() {
		setTitle("DiamondGame by Sungmin");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		c.add(new MenuPanel());
		
		
		
		setSize(600,460);
		setLocation(580,200);
		setVisible(true);
	}
	
	public class MenuPanel extends JPanel{  //패널
		private ImageIcon BackGround = new ImageIcon("images/첫화면_배경.png");
		private ImageIcon menu1 = new ImageIcon("images/첫화면_메뉴1.png");
		private ImageIcon menu2 = new ImageIcon("images/첫화면_메뉴2.png");
		private ImageIcon menu3 = new ImageIcon("images/첫화면_메뉴3.png");
		private ImageIcon menu4 = new ImageIcon("images/첫화면_메뉴4.png");
		private ImageIcon menu5 = new ImageIcon("images/첫화면_메뉴5.png");
		private ImageIcon menu6 = new ImageIcon("images/첫화면_메뉴6.png");
		
		private Image BG =BackGround.getImage();
		private JLabel menu_1 = new JLabel(menu1);
		private JLabel menu_2 = new JLabel(menu2);
		private JLabel menu_3 = new JLabel(menu3);
		private JLabel menu_4 = new JLabel(menu4);
		private JLabel menu_5 = new JLabel(menu5);
		private JLabel menu_6 = new JLabel(menu6);
		
		public void paintComponent(Graphics g){ //맵 그려주기
            super.paintComponent(g);
            g.drawImage(BG,0,0,getWidth(),getHeight(),this);
        }
		
		public MenuPanel(){
			setResizable(false);
			
			add(menu_1);
			add(menu_2);
			add(menu_3);
			add(menu_4);
			add(menu_5);
			add(menu_6);
			
			play_2or3_normal_listener normal_2or3 = new play_2or3_normal_listener();
			play_2or3_client_listener client_2or3 = new play_2or3_client_listener();	
			play_2or3_server_listener server_2or3 = new play_2or3_server_listener();			
			player_3_Game player3game = new player_3_Game();		
			AI_Game aigame = new AI_Game();
			EXIT exit = new EXIT();
			Setup setup = new Setup();
			
			menu_1.addMouseListener(aigame);
			menu_2.addMouseListener(normal_2or3);
			menu_3.addMouseListener(client_2or3); 
			menu_4.addMouseListener(server_2or3);
			menu_5.addMouseListener(setup);
			menu_6.addMouseListener(exit);

		}
	
	
	}
	
	class play_2or3_normal_listener  extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			new selec_2_3();
			dispose();
		}

	}
	class play_2or3_server_listener extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			new Server2();
			dispose();
		}

	}
	class play_2or3_client_listener  extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			new Client_wait();
			dispose();
		}

	}
	class player_3_Game  extends MouseAdapter{ 
		public void mousePressed(MouseEvent e) {

			new player3_game();
			dispose();
		}

	}
	class AI_Game  extends MouseAdapter{ 
		public void mousePressed(MouseEvent e) {
			new AI_game();
			dispose();
		}
	}
	class Setup  extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			new setup();
			
		}

	}
	class EXIT  extends MouseAdapter{ 
		public void mousePressed(MouseEvent e) {
			new EXIT();
			dispose();
		}

	}
	public static void main(String[] args) {
		new SubPanel();
		
	}
}
	
