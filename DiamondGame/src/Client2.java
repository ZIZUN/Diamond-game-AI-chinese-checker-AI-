import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
 
import javax.swing.*;

import java.awt.event.*;
import java.awt.*;



public class Client2 extends JFrame { // 1�ο� ����
	Socket cliesocket;
	public Client2(Socket sc) {    //�⺻ ������
		setTitle("���̾Ƹ�� ���� ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		c.add(new GamePanel(sc));
		
		
		cliesocket=sc;
		
		setSize(500,500);
		setLocation(580,200);
		setVisible(true);
		
	}
	
	public class GamePanel extends JPanel{  //�г�
		JLabel timerLabel = new JLabel();
		TimerThread th = new TimerThread(timerLabel);
		
		ImageIcon null_image = new ImageIcon("images/null.png");
		ImageIcon player1_image = new ImageIcon("images/player1.png");
		ImageIcon player2_image = new ImageIcon("images/player2.png");
		ImageIcon player3_image = new ImageIcon("images/player3.png");
		ImageIcon player1_null = new ImageIcon("images/player1_null.png");
		ImageIcon player1_null_p = new ImageIcon("images/player1_null_p.png");
		ImageIcon player2_null = new ImageIcon("images/player2_null.png");
		ImageIcon player3_null = new ImageIcon("images/player3_null.gif");
		
		
		
		ImageIcon abstain = new ImageIcon("images/����ϱ�.png");
		ImageIcon gameout = new ImageIcon("images/������.png");
		ImageIcon BackGround = new ImageIcon("images/��1.png");
		Image im =BackGround.getImage();
		
		JLabel TurnStatus = new JLabel();
		
		JLabel Abstain = new JLabel();
		JLabel GameOut = new JLabel();
		
		JLabel []Gamestatus = new JLabel[4];
		
		SpringLayout GameLayout; //������ ���̾ƿ� ����
		
		JLabel []stone = new JLabel[37]; //
		
		int player_turn=1;
		int timer_set=20;
		String TurnInformation="";
		String StoneChangeInfor ="";
		boolean Next = false;
		
		int player1_Combo=0;
		int player2_Combo=0;
		int player3_Combo=0;
		int Combo_temp=0;
		
		int totalplayer=0;
		
		void mapping() { //���濡 �̹��� �ҽ������� �ִ� �̹��� �ֱ�
			for(int i = 0 ; i<37; i++)
			{
				stone[i] = new JLabel(null_image);
			}
			
			stone[0] = new JLabel(player1_image);
			stone[1] = new JLabel(player1_image);
			stone[2] = new JLabel(player1_image);
			stone[5] = new JLabel(player1_image);
			stone[6] = new JLabel(player1_image);
			stone[7] = new JLabel(player1_image);
			
			stone[16] = new JLabel(player2_image);
			stone[21] = new JLabel(player2_image);
			stone[22] = new JLabel(player2_image);
			stone[27] = new JLabel(player2_image);
			stone[28] = new JLabel(player2_image);
			stone[29] = new JLabel(player2_image);

		}
		public void paintComponent(Graphics g){ //�� �׷��ֱ�
            super.paintComponent(g);
            g.drawImage(im,0,0,getWidth(),getHeight(),this);
        }
		public void stonedispose() // ���� �ʿ� ��� 
		{
			int bottom=18;
			int left=-31;
			int width=28;
			GameLayout.putConstraint(SpringLayout.NORTH, stone[0], 9, SpringLayout.WEST, this);
			GameLayout.putConstraint(SpringLayout.WEST, stone[0], 214, SpringLayout.WEST, this);
						
			GameLayout.putConstraint(SpringLayout.NORTH, stone[1], bottom, SpringLayout.SOUTH, stone[0]);//�������̶� ��°�� ���� �Ÿ� ����
			GameLayout.putConstraint(SpringLayout.NORTH, stone[2], bottom, SpringLayout.SOUTH, stone[0]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[1], left, SpringLayout.WEST, stone[0]);//������ ���ʰ� �ι�°�� �ǿ��ʵ� �� ������ġ ����ũ��
			GameLayout.putConstraint(SpringLayout.WEST, stone[2], width, SpringLayout.EAST, stone[1]);
			
			for(int i=3; i<=9; i++)//�ι�°�� �̶� ����°�� ���� �Ÿ� ����
			{
				GameLayout.putConstraint(SpringLayout.NORTH, stone[i], bottom, SpringLayout.SOUTH, stone[1]);
			}
			
			GameLayout.putConstraint(SpringLayout.WEST, stone[5], left, SpringLayout.WEST, stone[1]);//���� ���ʰ� ������ �ǿ��ʵ� �� ������ġ ����ũ��
			
			
			GameLayout.putConstraint(SpringLayout.EAST, stone[4], -width, SpringLayout.WEST, stone[5]);
			GameLayout.putConstraint(SpringLayout.EAST, stone[3], -width, SpringLayout.WEST, stone[4]);

			GameLayout.putConstraint(SpringLayout.WEST, stone[6], width, SpringLayout.EAST, stone[5]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[7], width, SpringLayout.EAST, stone[6]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[8], width, SpringLayout.EAST, stone[7]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[9], width, SpringLayout.EAST, stone[8]);
			
			for(int i=10; i<=15; i++)//�ι�°�� �̶� ����°�� ���� �Ÿ� ����
			{
				GameLayout.putConstraint(SpringLayout.NORTH, stone[i], bottom, SpringLayout.SOUTH, stone[5]);
			}
			GameLayout.putConstraint(SpringLayout.WEST, stone[11], left, SpringLayout.WEST, stone[5]);//���� ���ʰ� ������ �ǿ��ʵ� �� ������ġ ����ũ��
			
			GameLayout.putConstraint(SpringLayout.EAST, stone[10],-width, SpringLayout.WEST, stone[11]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[12], width, SpringLayout.EAST, stone[11]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[13], width, SpringLayout.EAST, stone[12]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[14], width, SpringLayout.EAST, stone[13]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[15], width, SpringLayout.EAST, stone[14]);
			for(int i=16; i<=21; i++)//�ι�°�� �̶� ����°�� ���� �Ÿ� ����
			{
				GameLayout.putConstraint(SpringLayout.NORTH, stone[i], bottom, SpringLayout.SOUTH, stone[11]);
			}
			GameLayout.putConstraint(SpringLayout.WEST, stone[16], left, SpringLayout.WEST, stone[11]);//���� ���ʰ� ������ �ǿ��ʵ� �� ������ġ ����ũ��
			
			
			GameLayout.putConstraint(SpringLayout.WEST, stone[17], width, SpringLayout.EAST, stone[16]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[18], width, SpringLayout.EAST, stone[17]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[19], width, SpringLayout.EAST, stone[18]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[20], width, SpringLayout.EAST, stone[19]);
			for(int i=21; i<=27; i++)//�ι�°�� �̶� ����°�� ���� �Ÿ� ����
			{
				GameLayout.putConstraint(SpringLayout.NORTH, stone[i], bottom, SpringLayout.SOUTH, stone[16]);
			}
			GameLayout.putConstraint(SpringLayout.WEST, stone[21], left, SpringLayout.WEST, stone[16]);//���� ���ʰ� ������ �ǿ��ʵ� �� ������ġ ����ũ��
			
			GameLayout.putConstraint(SpringLayout.WEST, stone[22], width, SpringLayout.EAST, stone[21]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[23], width, SpringLayout.EAST, stone[22]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[24], width, SpringLayout.EAST, stone[23]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[25], width, SpringLayout.EAST, stone[24]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[26], width, SpringLayout.EAST, stone[25]);
			
			for(int i=27; i<=33; i++)//�ι�°�� �̶� ����°�� ���� �Ÿ� ����
			{
				GameLayout.putConstraint(SpringLayout.NORTH, stone[i], bottom, SpringLayout.SOUTH, stone[21]);
			}
			GameLayout.putConstraint(SpringLayout.WEST, stone[27], left, SpringLayout.WEST, stone[21]);//���� ���ʰ� ������ �ǿ��ʵ� �� ������ġ ����ũ��
			
			GameLayout.putConstraint(SpringLayout.WEST, stone[28], width, SpringLayout.EAST, stone[27]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[29], width, SpringLayout.EAST, stone[28]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[30], width, SpringLayout.EAST, stone[29]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[31], width, SpringLayout.EAST, stone[30]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[32], width, SpringLayout.EAST, stone[31]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[33], width, SpringLayout.EAST, stone[32]);
			
			GameLayout.putConstraint(SpringLayout.WEST, stone[34], left, SpringLayout.WEST, stone[30]);//���� ���ʰ� ������ �ǿ��ʵ� �� ������ġ ����ũ��
			for(int i=34; i<=35; i++)//�ι�°�� �̶� ����°�� ���� �Ÿ� ����
			{
				GameLayout.putConstraint(SpringLayout.NORTH, stone[i], bottom, SpringLayout.SOUTH, stone[30]);
			}
			GameLayout.putConstraint(SpringLayout.WEST, stone[35], width, SpringLayout.EAST, stone[34]);
			
			GameLayout.putConstraint(SpringLayout.WEST, stone[36], left, SpringLayout.WEST, stone[35]);//���� ���ʰ� ������ �ǿ��ʵ� �� ������ġ ����ũ��
			GameLayout.putConstraint(SpringLayout.NORTH, stone[36], bottom, SpringLayout.SOUTH, stone[35]);
			
		}
		
		void Scan(JLabel click) { // ���� Ŭ�������� ��ü �� ��ĵ�ؼ� �̵��Ҽ� �ִºκ� �� _null�̹����� ǥ�����ִ¿���
			
			for(int i = 0; i<37; i++)
			{
				int x = click.getX();
				int y = click.getY();
				int xlength = 0;
				int ylength =0;
				
				
					if(x>stone[i].getX())
					{
						xlength = x - stone[i].getX(); 
					}
					else if((x<stone[i].getX())) {
						xlength = stone[i].getX() - x;
					}
					if(y>stone[i].getY())
					{
						ylength = y - stone[i].getY();
					}
					else if(y<stone[i].getY()) {
						ylength = stone[i].getY() - y;
					}
			
					
				int sum=xlength + ylength;
				if((sum==62 || sum==83) && stone[i].getIcon()==null_image && jumpafter==0)
				{
					stone[i].setIcon(player1_null);
				}
				else if((sum==62 || sum==83) && stone[i].getIcon()!=null_image) //�����Ǵ°� �ִ� �κ���
				{
					if(x-stone[i].getX()>0 && y-stone[i].getY()==0) //����
					{
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==null_image && stone[i].getX()-stone[j].getX()==62 && stone[i].getY()-stone[j].getY()==0)
							{
								stone[j].setIcon(player1_null); //���������� �κп� �̵����� ǥ�� ����
							}
						}
					}
					else if(x-stone[i].getX()<0 && y-stone[i].getY()==0)//������
					{
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==null_image && stone[i].getX()-stone[j].getX()==-62 && stone[i].getY()-stone[j].getY()==0)
							{
								stone[j].setIcon(player1_null); //���������� �κп� �̵����� ǥ�� ����
							}
						}
					}
					else if(x-stone[i].getX()>0 && y-stone[i].getY()>0)// ������
					{
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==null_image && stone[i].getX()-stone[j].getX()==31 && stone[i].getY()-stone[j].getY()==52)
							{
								stone[j].setIcon(player1_null); //���������� �κп� �̵����� ǥ�� ����
							}
						}
					}
					else if(x-stone[i].getX()>0 && y-stone[i].getY()<0)// ���ʾƷ�
					{
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==null_image && stone[i].getX()-stone[j].getX()==31 && stone[i].getY()-stone[j].getY()==-52)
							{
								stone[j].setIcon(player1_null); //���������� �κп� �̵����� ǥ�� ����
							}
						}
					}
					else if(x-stone[i].getX()<0 && y-stone[i].getY()>0)// ��������
					{
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==null_image && stone[i].getX()-stone[j].getX()==-31 && stone[i].getY()-stone[j].getY()==52)
							{
								stone[j].setIcon(player1_null); //���������� �κп� �̵����� ǥ�� ����
							}
						}
					}
					else if(x-stone[i].getX()<0 && y-stone[i].getY()<0)// �����ʾƷ�
					{
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==null_image && stone[i].getX()-stone[j].getX()==-31 && stone[i].getY()-stone[j].getY()==-52)
							{
								stone[j].setIcon(player1_null); //���������� �κп� �̵����� ǥ�� ����
							}
						}
					}
				}
				
				
			}	
		}
		
		
		
		int NextJumpScan(JLabel button) { // �������� �ִ��� Ȯ��
			
			for(int i = 0; i<37; i++)
			{
				int x = button.getX();
				int y = button.getY();
				int xlength = 0;
				int ylength =0;
				
					if(x>stone[i].getX())
					{
						xlength = x - stone[i].getX(); 
					}
					else if((x<stone[i].getX())) {
						xlength = stone[i].getX() - x;
					}
					if(y>stone[i].getY())
					{
						ylength = y - stone[i].getY();
					}
					else if(y<stone[i].getY()) {
						ylength = stone[i].getY() - y;
					}
					
				int sum=xlength + ylength;
				
				if((sum==62 || sum==83) && stone[i].getIcon()!=null_image) //�����Ǵ°� �ִ� �κ���
				{
					if(x-stone[i].getX()>0 && y-stone[i].getY()==0) //����
					{
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==null_image && stone[i].getX()-stone[j].getX()==62 && stone[i].getY()-stone[j].getY()==0)
							{
								return 1;
							}
						}
					}
					else if(x-stone[i].getX()<0 && y-stone[i].getY()==0)//������
					{
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==null_image && stone[i].getX()-stone[j].getX()==-62 && stone[i].getY()-stone[j].getY()==0)
							{
								return 1;//���������Ҷ� ������ ������
							}
						}
					}
					else if(x-stone[i].getX()>0 && y-stone[i].getY()>0)// ������
					{
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==null_image && stone[i].getX()-stone[j].getX()==31 && stone[i].getY()-stone[j].getY()==52)
							{
								return 1;//���������Ҷ� ������ ������
							}
						}
					}
					else if(x-stone[i].getX()>0 && y-stone[i].getY()<0)// ���ʾƷ�
					{
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==null_image && stone[i].getX()-stone[j].getX()==31 && stone[i].getY()-stone[j].getY()==-52)
							{
								return 1;//���������Ҷ� ������ ������
							}
						}
					}
					else if(x-stone[i].getX()<0 && y-stone[i].getY()>0)// ��������
					{
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==null_image && stone[i].getX()-stone[j].getX()==-31 && stone[i].getY()-stone[j].getY()==52)
							{
								return 1;//���������Ҷ� ������ ������
							}
						}
					}
					else if(x-stone[i].getX()<0 && y-stone[i].getY()<0)// �����ʾƷ�
					{
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==null_image && stone[i].getX()-stone[j].getX()==-31 && stone[i].getY()-stone[j].getY()==-52)
							{
								return 1;//���������Ҷ� ������ ������
							}
						}
					}
					
				}
				
				
			}	
			return 0;
		}
		
		void clear() // �� ������ �̹��� ������� Ŭ����
		{
			for(int i = 0; i<37; i++)
			{
				if(stone[i].getIcon()!=player1_image && stone[i].getIcon()!=player3_image && stone[i].getIcon()!=player2_image)
					stone[i].setIcon(null_image);
			}
		}
		int GameStatus_i=0;
		int player1_status=1;//player1�� ������ �������� .. 1�Ͻ� ����������
		int player2_status=1;
		int player3_status=1;
		
		int player1_Score=0;
		int player2_Score=0;
		int player3_Score=0;
		
		String player1_Location="TOP";			
		String player2_Location="LEFT";		
		String player3_Location="RIGHT";
		void GameScore() {
			player1_Score=0;
			player2_Score=0;
			player3_Score=0;
			
			int Top_num[] = new int[6];
			Top_num[0]=29; Top_num[1]=30; Top_num[2]=31; Top_num[3]=34; Top_num[4]=35; Top_num[5]=36;
			int LEFT_num[] = new int[6];
			LEFT_num[0]=7; LEFT_num[1]=8; LEFT_num[2]=9; LEFT_num[3]=14; LEFT_num[4]=15; LEFT_num[5]=20;
			int RIGHT_num[] = new int[6];
			RIGHT_num[0]=3; RIGHT_num[1]=4; RIGHT_num[2]=5; RIGHT_num[3]=10; RIGHT_num[4]=11; RIGHT_num[5]=16;
			
			
			int ply1_arr[] = new int[6];
			int ply2_arr[] = new int[6];
			int ply3_arr[] = new int[6];
			if(player1_Location=="TOP" && player2_Location=="LEFT" && player3_Location=="RIGHT" )
			{
				ply1_arr=Top_num; ply2_arr=LEFT_num; ply3_arr=RIGHT_num;
			}else if(player1_Location=="TOP" && player2_Location=="RIGHT" && player3_Location=="LEFT" )
			{
				ply1_arr=Top_num; ply2_arr=RIGHT_num; ply3_arr=LEFT_num;
			}else if(player1_Location=="LEFT"  && player2_Location=="TOP" && player3_Location=="RIGHT" )
			{
				ply1_arr=LEFT_num; ply2_arr=Top_num; ply3_arr=RIGHT_num;
			}else if(player1_Location=="LEFT" &&  player2_Location=="RIGHT" &&  player3_Location=="TOP" )
			{
				ply1_arr=LEFT_num; ply2_arr=RIGHT_num; ply3_arr=Top_num;
			}else if(player1_Location=="RIGHT"  &&  player2_Location=="TOP" && player3_Location=="LEFT" )
			{
				ply1_arr=RIGHT_num; ply2_arr=Top_num; ply3_arr=LEFT_num;
			}else if(player1_Location=="RIGHT"  &&  player2_Location=="LEFT"  && player3_Location=="TOP" )
			{
				ply1_arr=RIGHT_num; ply2_arr=LEFT_num; ply3_arr=Top_num;
			}
			
				
			
			
			if(stone[ply1_arr[0]].getIcon()==player1_image) player1_Score++;
			if(stone[ply1_arr[1]].getIcon()==player1_image) player1_Score++;
			if(stone[ply1_arr[2]].getIcon()==player1_image) player1_Score++;
			if(stone[ply1_arr[3]].getIcon()==player1_image) player1_Score++;
			if(stone[ply1_arr[4]].getIcon()==player1_image) player1_Score++;
			if(stone[ply1_arr[5]].getIcon()==player1_image) player1_Score++;
			
			if(stone[ply2_arr[0]].getIcon()==player2_image) player2_Score++;
			if(stone[ply2_arr[1]].getIcon()==player2_image) player2_Score++;
			if(stone[ply2_arr[2]].getIcon()==player2_image) player2_Score++;
			if(stone[ply2_arr[3]].getIcon()==player2_image) player2_Score++;
			if(stone[ply2_arr[4]].getIcon()==player2_image) player2_Score++;
			if(stone[ply2_arr[5]].getIcon()==player2_image) player2_Score++;
			
			if(stone[ply3_arr[0]].getIcon()==player3_image) player3_Score++;
			if(stone[ply3_arr[1]].getIcon()==player3_image) player3_Score++;
			if(stone[ply3_arr[2]].getIcon()==player3_image) player3_Score++;
			if(stone[ply3_arr[3]].getIcon()==player3_image) player3_Score++;
			if(stone[ply3_arr[4]].getIcon()==player3_image) player3_Score++;
			if(stone[ply3_arr[5]].getIcon()==player3_image) player3_Score++;
		}
		int abstain_player=0;
		int gameover=0;
		void GameStatus() { // �̸�, ���� , �޺� ������������ ���
			GameScore();
			if(totalplayer==3)
			{
				if(abstain_num==1)
				{
					if(abstain_player==1)
					{
						if(player2_Score > player3_Score)
						{
							Gamestatus[0].setText(player2_Nickname+" "+player2_Score+"comb: "+player2_Combo);
							Gamestatus[1].setText(player3_Nickname+" "+player3_Score+"comb: "+player3_Combo);
						}
						else
						{
							Gamestatus[0].setText(player3_Nickname+" "+player3_Score+"comb: "+player3_Combo);
							Gamestatus[1].setText(player2_Nickname+" "+player2_Score+"comb: "+player2_Combo);
						}
						Gamestatus[2].setText(player1_Nickname+" "+player1_Score+"comb: "+player1_Combo);
					}
					else if(abstain_player==2)
					{
						if(player1_Score > player3_Score)
						{
							Gamestatus[0].setText(player1_Nickname+" "+player1_Score+"comb: "+player1_Combo);
							Gamestatus[1].setText(player3_Nickname+" "+player3_Score+"comb: "+player3_Combo);
						}
						else
						{
							Gamestatus[0].setText(player3_Nickname+" "+player3_Score+"comb: "+player3_Combo);
							Gamestatus[1].setText(player1_Nickname+" "+player1_Score+"comb: "+player1_Combo);
						}
						Gamestatus[2].setText(player2_Nickname+" "+player2_Score+"comb: "+player2_Combo);
					}else if(abstain_player ==3)
					{
						if(player1_Score > player2_Score)
						{
							Gamestatus[0].setText(player1_Nickname+" "+player1_Score+"comb: "+player1_Combo);
							Gamestatus[1].setText(player2_Nickname+" "+player2_Score+"comb: "+player2_Combo);
						}
						else
						{
							Gamestatus[0].setText(player2_Nickname+" "+player2_Score+"comb: "+player2_Combo);
							Gamestatus[1].setText(player1_Nickname+" "+player1_Score+"comb: "+player1_Combo);
						}
						Gamestatus[2].setText(player3_Nickname+" "+player3_Score+"comb: "+player3_Combo);
					}
					abstain_num--;
				}
				
				
				
				
				
				if(player1_Score==6 && player1_status==1)
				{
					if(GameStatus_i==0)
					{
						Gamestatus[GameStatus_i].setText(player1_Nickname+" "+player1_Score+"comb: "+player1_Combo);
						if(player2_Score > player3_Score)
						{
							Gamestatus[1].setText(player2_Nickname+" "+player2_Score+"comb: "+player2_Combo);
							Gamestatus[2].setText(player3_Nickname+" "+player3_Score+"comb: "+player3_Combo);
						}
						else
						{
							Gamestatus[1].setText(player3_Nickname+" "+player3_Score+"comb: "+player3_Combo);
							Gamestatus[2].setText(player2_Nickname+" "+player2_Score+"comb: "+player2_Combo);
						}
						GameStatus_i++;
					}
					else if(GameStatus_i==1)
					{
						if(player2_Score==6)
						{
							Gamestatus[1].setText(player1_Nickname+" "+player1_Score+"comb: "+player1_Combo);
							Gamestatus[2].setText(player3_Nickname+" "+player3_Score+"comb: "+player3_Combo);
						}
						else
						{
							Gamestatus[1].setText(player1_Nickname+" "+player1_Score+"comb: "+player1_Combo);
							Gamestatus[2].setText(player2_Nickname+" "+player2_Score+"comb: "+player2_Combo);
						}
						GameStatus_i++;
					}
					else
					{
						Gamestatus[2].setText(player1_Nickname+" "+player1_Score+"comb: "+player1_Combo);
					}

					player1_status=0;
				}
				else if(player2_Score==6 && player2_status==1)
				{
					if(GameStatus_i==0)
					{
						Gamestatus[GameStatus_i].setText(player2_Nickname+" "+player2_Score+"comb: "+player2_Combo);
						if(player1_Score > player3_Score)
						{
							Gamestatus[1].setText(player1_Nickname+" "+player1_Score+"comb: "+player1_Combo);
							Gamestatus[2].setText(player3_Nickname+" "+player3_Score+"comb: "+player3_Combo);
						}
						else
						{
							Gamestatus[1].setText(player3_Nickname+" "+player3_Score+"comb: "+player3_Combo);
							Gamestatus[2].setText(player1_Nickname+" "+player1_Score+"comb: "+player1_Combo);
						}
						GameStatus_i++;
					}
					else if(GameStatus_i==1)
					{
						if(player1_Score==6)
						{
							Gamestatus[1].setText(player2_Nickname+" "+player2_Score+"comb: "+player2_Combo);
							Gamestatus[2].setText(player3_Nickname+" "+player3_Score+"comb: "+player3_Combo);
						}
						else
						{
							Gamestatus[1].setText(player2_Nickname+" "+player2_Score+"comb: "+player2_Combo);
							Gamestatus[2].setText(player1_Nickname+" "+player1_Score+"comb: "+player1_Combo);
						}
						GameStatus_i++;
					}
					else
					{
						Gamestatus[2].setText(player2_Nickname+" "+player2_Score+"comb: "+player2_Combo);
					}
					
					player2_status=0;
				}
				else if(player3_Score==6 && player3_status==1)
				{
					if(GameStatus_i==0)
					{
						Gamestatus[GameStatus_i].setText(player3_Nickname+" "+player3_Score+"comb: "+player3_Combo);
						if(player1_Score > player2_Score)
						{
							Gamestatus[1].setText(player1_Nickname+" "+player1_Score+"comb: "+player1_Combo);
							Gamestatus[2].setText(player2_Nickname+" "+player2_Score+"comb: "+player2_Combo);
						}
						else
						{
							Gamestatus[1].setText(player2_Nickname+" "+player2_Score+"comb: "+player2_Combo);
							Gamestatus[2].setText(player1_Nickname+" "+player1_Score+"comb: "+player1_Combo);
						}
						GameStatus_i++;
					}
					else if(GameStatus_i==1)
					{
						if(player1_Score==6)
						{
							Gamestatus[1].setText(player3_Nickname+" "+player3_Score+"comb: "+player3_Combo);
							Gamestatus[2].setText(player2_Nickname+" "+player2_Score+"comb: "+player2_Combo);
						}
						else
						{
							Gamestatus[1].setText(player3_Nickname+" "+player3_Score+"comb: "+player3_Combo);
							Gamestatus[2].setText(player1_Nickname+" "+player1_Score+"comb: "+player1_Combo);
						}
						GameStatus_i++;
					}
					else
					{
						Gamestatus[2].setText(player3_Nickname+" "+player3_Score+"comb: "+player3_Combo);
					}
					player3_status=0;
				}
			}
			else if(totalplayer==2)
			{
				if(player1_status==0 && player2_status==1)
				{
					Gamestatus[0].setText(player2_Nickname+" "+player2_Score+"comb: "+player2_Combo);
					Gamestatus[1].setText(player1_Nickname+" "+0+"comb: "+player1_Combo);
					th.stop();
					Abstain.setVisible(false);
					GameOut.setVisible(true);
				}
				else if (player1_status==1 && player2_status==0)
				{
					Gamestatus[0].setText(player1_Nickname+" "+player1_Score+"comb: "+player1_Combo);
					Gamestatus[1].setText(player2_Nickname+" "+0+"comb: "+player2_Combo);
					th.stop();
					Abstain.setVisible(false);
					GameOut.setVisible(true);
					
				}
				
				if(player1_Score==6 && player1_status==1)
				{
						Gamestatus[0].setText(player1_Nickname+" "+player1_Score+"comb: "+player1_Combo);
						Gamestatus[1].setText(player2_Nickname+" "+player2_Score+"comb: "+player2_Combo);

						th.stop();
						Abstain.setVisible(false);
						GameOut.setVisible(true);
				}
				else if(player2_Score==6 && player2_status==1)
				{
						Gamestatus[0].setText(player2_Nickname+" "+player2_Score+"comb: "+player2_Combo);
						Gamestatus[1].setText(player1_Nickname+" "+player1_Score+"comb: "+player1_Combo);
						th.stop();
						Abstain.setVisible(false);
						GameOut.setVisible(true);
				}
				
			}
		}
		
		
		String player1_Nickname="player1";
		String player2_Nickname="player2";
		String player3_Nickname="player3";
		
		void My_Nickname_set()
		{
			if(playerinfor==1)
			{
				player1_Nickname=setup.NickName;
			}
			else if(playerinfor==2)
			{
				player2_Nickname=setup.NickName;
			}
			else if(playerinfor==3)
			{
				player3_Nickname=setup.NickName;
			}
		}
		
		void NextTurn() { // ������ �û�� ���ϴ��Լ�
			Combo_temp=0;
			if(player_turn==1 && player2_status==1) // �÷��̾�1�� ���϶�
			{
				player_turn++;
				TurnStatus.setText(player2_Nickname+" Turn");
				TurnStatus.setForeground(Color.BLUE);
				GameStatus();
			
				TurnInformation="TURN_2";
				n=timer_set-2;
				
			}
			else if(player_turn==1 && player2_status==0 && player3_status==1 )
			{
				player_turn=3;
				TurnStatus.setText(player3_Nickname+" Turn");
				TurnStatus.setForeground(Color.GREEN);
				GameStatus();
			
				TurnInformation="TURN_3";
				n=timer_set-2;
			}
			else if(player_turn==1 && player2_status==0 && player3_status==0 )
			{
				GameStatus();
				n=timer_set-2;
			}
			
			else if(player_turn==2 && player3_status==1) // �÷��̾� 2�� ���϶�
			{
				player_turn++;
				TurnStatus.setText(player3_Nickname+" Turn");
				TurnStatus.setForeground(Color.GREEN);
				GameStatus();
			
				TurnInformation="TURN_3";
				n=timer_set-2;
			}else if(player_turn==2 && player3_status==0 && player1_status==1)
			{
				player_turn=1;
				TurnStatus.setText(player1_Nickname+" Turn");
				TurnStatus.setForeground(Color.BLACK);
				GameStatus();
			
				TurnInformation="TURN_1";
				n=timer_set-2;
			}else if(player_turn==2 && player3_status==0 && player1_status==0)
			{
				GameStatus();
			
				n=timer_set-2;
			}
			
			else if(player_turn==3 && player1_status==1) // �÷��̾�3�� ���϶�
			{
				player_turn=1;
				TurnStatus.setText(player1_Nickname+" Turn");
				TurnStatus.setForeground(Color.BLACK);
				GameStatus();
			
				TurnInformation="TURN_1";
				n=timer_set-2;
			}else if(player_turn==3 && player1_status==0 && player2_status==1)
			{
				player_turn=2;
				TurnStatus.setText(player2_Nickname+" Turn");
				TurnStatus.setForeground(Color.BLUE);
				GameStatus();
			
				TurnInformation="TURN_2";
				n=timer_set-2;
			}else if(player_turn==3 && player1_status==0 && player2_status==0)
			{
				GameStatus();
				n=timer_set-2;
				
			}
		}
		
		boolean timeset=false;
		int n = timer_set-2;
		class TimerThread extends Thread{  // Ÿ�̸� �κ� ������
			private JLabel timerLabel;
			public TimerThread(JLabel timerLabel) {
				this.timerLabel = timerLabel;
			}
			
			public void run() {
				
				while(true)
				{
					timerLabel.setText(Integer.toString(n));
					n--;
					if(timeset==true)//�������� �ð��ʱ�ȭ �϶���ϸ�
					{
						n=timer_set-2;
						timerLabel.setText(Integer.toString(n));
						timeset=false;
					}
					
					
					if((player1_status==0 && player2_status==0)||(player2_status==0 && player3_status==0)||(player1_status==0 && player3_status==0))
					{
						GameStatus();
						Abstain.setVisible(false);
						GameOut.setVisible(true);
						StoneChangeInfor = "GAMEOVER_";
						interrupt();
					}
					
					
					if(player1_status==0 && playerinfor==1)
					{
						Abstain.setVisible(false);
						GameOut.setVisible(true);
					}
					else if(player2_status==0 && playerinfor==2)
					{
						Abstain.setVisible(false);
						GameOut.setVisible(true);
					}else if(player3_status==0 && playerinfor==3)
					{
						Abstain.setVisible(false);
						GameOut.setVisible(true);
					}
					
					
					
					try {
						Thread.sleep(1000);
					}
					catch(InterruptedException e)
					{
						return ;
					}
				}
			}
		}
		
		int playerinfor;
		class ClientReader extends Thread {  // �޴ºκ� ������
		    Socket clientSocket;
		 
		    ClientReader(Socket clientSocket) {
		        this.clientSocket = clientSocket;
		    }
		 
		    @Override
		    public  void run() {
		        try {
		        	
		        		
		            while (true) {
		            	InputStream inputStream = clientSocket.getInputStream();
			        	byte[] byteArray = new byte[256];

		                int size = inputStream.read(byteArray);
		                String readMessage = new String(byteArray, 0, size, "UTF-8");   //read message�� �޴� ��Ʈ���� -----------�߿�
		                //JOptionPane.showMessageDialog(null, readMessage);
		                String s[] = readMessage.split("_");
		                if(s[0].equals("GO"))
		                {
		                	stone[Integer.parseInt(s[2])].setIcon(stone[Integer.parseInt(s[1])].getIcon());
		                	stone[Integer.parseInt(s[1])].setIcon(null_image);
		                	NextTurn();
		                	Sounds.Sound();
		                }
		                
		                else if(s[0].equals("JUMP"))
		                {
		                	stone[Integer.parseInt(s[2])].setIcon(stone[Integer.parseInt(s[1])].getIcon());
		                	stone[Integer.parseInt(s[1])].setIcon(null_image);
		                	Sounds.Sound();
		                }

		                else if(s[0].equals("DONTMOVE"))
				                {
				                	NextTurn();
				                	Sounds.Sound();
				                }
		                else if(s[0].equals("TIMESET"))
		                {
		                	timeset =true; 
		                	NextTurn();
		                	
		                }
		                else if(s[0].equals("GAMESTART"))
		                {
		                	th.start();
		                	AfterStart=true;
		                	Abstain.setVisible(true);
		                	
		                	try {
		        				String send="PLAYER"+"_"+playerinfor+"_nickname_"+setup.NickName+"_";
		        				byte[] byteArray3 = send.getBytes("UTF-8");  
		        	            OutputStream outputStream = clientSocket.getOutputStream();
		        	            outputStream.write(byteArray3);// byteArray �� ������ ����( �����¸޽��� ) -=---------�߿�
		        	            StoneChangeInfor="";
		        			} catch (IOException e1) {

		        			}
		                	TurnStatus.setText(player1_Nickname+" Turn");
		                	
		                }
		                else if(s[0].equals("PLAYER2")&& s[1].equals("in"))
		                {
		                	stone[16].setIcon(player2_image);
		        			stone[21].setIcon(player2_image);
		        			stone[22].setIcon(player2_image);
		        			stone[27].setIcon(player2_image);
		        			stone[28].setIcon(player2_image);
		        			stone[29].setIcon(player2_image);
		        			player3_status=0;
		                }
		                else if(s[0].equals("PLAYER3")&& s[1].equals("in"))
		                {
		        			stone[20].setIcon(player3_image);
		        			stone[25].setIcon(player3_image);
		        			stone[26].setIcon(player3_image);
		        			stone[31].setIcon(player3_image);
		        			stone[32].setIcon(player3_image);
		        			stone[33].setIcon(player3_image);
		        			player3_status=1;
		                }
		                else if(s[0].equals("YOU")&& s[1].equals("PLAYER1"))
		                {
		        			playerinfor=1;
		        			Playerinfor_set();
		        			My_Nickname_set();
		                }
		                else if(s[0].equals("YOU")&& s[1].equals("PLAYER2"))
		                {
		        			playerinfor=2;
		        			Playerinfor_set();
		        			My_Nickname_set();
		                }
		                else if(s[0].equals("YOU")&& s[1].equals("PLAYER3"))
		                {
		        			stone[20].setIcon(player3_image);
		        			stone[25].setIcon(player3_image);
		        			stone[26].setIcon(player3_image);
		        			stone[31].setIcon(player3_image);
		        			stone[32].setIcon(player3_image);
		        			stone[33].setIcon(player3_image);
		        			playerinfor=3;
		        			Playerinfor_set();
		        			My_Nickname_set();
		                }
		                else if(s[0].equals("ABSTAIN")&& s[1].equals("PLAYER"))
		                {
		                	abstain_num++;
		                	
		                	int t= Integer.parseInt(s[2]);
		                	abstain_player=t;
		                	if(t==1)
		                	{
		                		for(int i = 0; i<37; i++) // ��ư �Ű����°� ���� �����ϴºκ�
		    					{
		    						if(stone[i].getIcon()==player1_image) //�Ű������� ��ư��ġ
		    						{
		    							stone[i].setIcon(null_image);
		    						}
		    					}
		                		player1_status=0;
		                		player1_Score=0;
		                		if(player_turn==1)
		                			NextTurn();
		                	}
		                	else if(t==2)
		                	{
		                		for(int i = 0; i<37; i++) // ��ư �Ű����°� ���� �����ϴºκ�
		    					{
		    						if(stone[i].getIcon()==player2_image) //�Ű������� ��ư��ġ
		    						{
		    							stone[i].setIcon(null_image);
		    						}
		    					}
		                		player2_status=0;
		                		player2_Score=0;
		                		if(player_turn==2)
		                			NextTurn();
		                	}
		                	else if(t==3)
		                	{

		                		for(int i = 0; i<37; i++) // ��ư �Ű����°� ���� �����ϴºκ�
		    					{
		    						if(stone[i].getIcon()==player3_image) //�Ű������� ��ư��ġ
		    						{
		    							stone[i].setIcon(null_image);
		    						}
		    					}
		                		player3_status=0;
		                		player3_Score=0;
		                		if(player_turn==3)
		                			NextTurn();
		                	}
		                	GameStatus();
		                }
		                else if(s[0].equals("TOTALPLAYER"))
		                {
		                	totalplayer= Integer.parseInt(s[1]);
		                }
		                else if(s[0].equals("PLAYER")&&s[2].equals("nickname"))
		                {
		                	int k = Integer.parseInt(s[1]);
		                	
		                	if(k==1)
		                	{
		                		player1_Nickname=s[3];
		                	}
		                	else if(k==2)
		                	{
		                		player2_Nickname=s[3];
		                	}
		                	else if(k==3)
		                	{
		                		player3_Nickname=s[3];
		                	}
		                	
		                	
		                }
		                else if(s[0].equals("TIMEcorrecT"))
		                {
		                	timer_set= Integer.parseInt(s[1]);
		                	n=timer_set-2;
		                }
		                else if(s[0].equals("GAMEOVER"))
		                {
		                	th.stop();
		                	gameover=1;
		                }
		                else if(s[0].equals("COMBO"))
		                {
		                	if(s[1].equals("1"))
		                	{
		                		player1_Combo= Integer.parseInt(s[2]);
		                	}
		                	else if(s[1].equals("2"))
		                	{
		                		player2_Combo= Integer.parseInt(s[2]);
		                	}
		                	else if(s[1].equals("3"))
		                	{
		                		player3_Combo= Integer.parseInt(s[2]);
		                	}
		                }
		            }
		        } catch (Exception e) {}
		    }
		}
		

		
		class ConnectThread extends Thread { //������ �κ� ������
		    Socket clientSocket = null;
		    
		    ConnectThread(Socket clientSocket) {
		        this.clientSocket = clientSocket;
		    }
		 
		    @Override
		    public  void run() {
		    	
		        try {
		        	
		        	while (true) {	

		                if(StoneChangeInfor.length()>2)
		                {
		                	 byte[] byteArray = StoneChangeInfor.getBytes("UTF-8");  
				                OutputStream outputStream = clientSocket.getOutputStream();
				                outputStream.write(byteArray);// byteArray �� ������ ����( �����¸޽��� ) -=---------�߿�
				                StoneChangeInfor="";
		                }

		            	sleep(500);
		            	
		            }
		        	
		        } catch (Exception e) {}
		    }
		}//
		int abstain_num=0;
		public GamePanel(Socket sc){

			 Socket clientSocket = sc;
	        try {
	         

	            
	            ConnectThread connectThread = new ConnectThread(clientSocket);
	            connectThread.start();

	            
	        } catch (Exception e) {
	        }

			GameLayout = new SpringLayout();
	        setLayout(GameLayout);
	        mapping();
			stonedispose();
			
			ClientReader clientReader = new ClientReader(clientSocket);
        	clientReader.start();//�޴ºκ� ������ ����  --��Ƽ������
			
			TurnStatus.setFont(new Font("Tahoma", Font.BOLD, 30));
			
			TurnStatus.setForeground(Color.BLACK);

			timerLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
			
			Abstain= new JLabel(abstain);
			Abstain.addMouseListener(new MouseAdapter() { // ����ϱ� ��������
				public void mousePressed(MouseEvent e) {
					abstain_num++;
					GameOut.setVisible(true);
					if(playerinfor==1)
                	{
						abstain_player=1;
                		for(int i = 0; i<37; i++) // ��ư �Ű����°� ���� �����ϴºκ�
    					{
    						if(stone[i].getIcon()==player1_image) //�Ű������� ��ư��ġ
    						{
    							stone[i].setIcon(null_image);
    						}
    					}
                		player1_status=0;
                		player1_Score=0;
                		GameStatus();
                		if(player_turn==1)
                			NextTurn();

                	}
                	else if(playerinfor==2)
                	{
                		abstain_player=2;
                		for(int i = 0; i<37; i++) // ��ư �Ű����°� ���� �����ϴºκ�
    					{
    						if(stone[i].getIcon()==player2_image) //�Ű������� ��ư��ġ
    						{
    							stone[i].setIcon(null_image);
    						}
    					}
                		player2_status=0;
                		player2_Score=0;
                		GameStatus();
                		if(player_turn==2)
                			NextTurn();
                	}
                	else if(playerinfor==3)
                	{
                		abstain_player=3;
                		for(int i = 0; i<37; i++) // ��ư �Ű����°� ���� �����ϴºκ�
    					{
    						if(stone[i].getIcon()==player3_image) //�Ű������� ��ư��ġ
    						{
    							stone[i].setIcon(null_image);
    						}
    					}
                		player3_status=0;
                		player3_Score=0;
                		GameStatus();
                		if(player_turn==3)
                			NextTurn();

                	}
					try {
						String s = "ABSTAIN_PLAYER_"+playerinfor+"_";
						byte[] byteArray = s.getBytes("UTF-8");  
		                OutputStream outputStream = clientSocket.getOutputStream();
		                outputStream.write(byteArray);// byteArray �� ������ ����( �����¸޽��� ) -=---------�߿�
		                
		                Abstain.setVisible(false);
					} catch (Exception de) {
			        }
					
				}
			});
			
			
			GameOut= new JLabel(gameout);
			GameOut.addMouseListener(new MouseAdapter() { // ������ ��������
				public void mousePressed(MouseEvent e) {
					try {
						String s = "EXIT_PLAYER_"+playerinfor+"_";
						byte[] byteArray = s.getBytes("UTF-8");  
		                OutputStream outputStream = clientSocket.getOutputStream();
		                outputStream.write(byteArray);// byteArray �� ������ ����( �����¸޽��� ) -=---------�߿�

					} catch (Exception de) {
			        }
					System.exit(0);
					}
			});
			
			GameLayout.putConstraint(SpringLayout.NORTH, timerLabel, 0, SpringLayout.WEST, this);
			GameLayout.putConstraint(SpringLayout.EAST, timerLabel, 0, SpringLayout.EAST, this);
			add(timerLabel);
			
			GameLayout.putConstraint(SpringLayout.NORTH, Abstain, 380, SpringLayout.SOUTH, TurnStatus);
			GameLayout.putConstraint(SpringLayout.WEST, Abstain, 0, SpringLayout.WEST, TurnStatus);
			
			GameLayout.putConstraint(SpringLayout.NORTH, GameOut, 380, SpringLayout.SOUTH, TurnStatus);
			GameLayout.putConstraint(SpringLayout.WEST, GameOut, 0, SpringLayout.WEST, TurnStatus);
			
			add(Abstain);
			add(GameOut);
			Abstain.setVisible(false);
			GameOut.setVisible(false);
			add(TurnStatus);
			for(int i=0; i<4; i++) {
				Gamestatus[i] = new JLabel();
				Gamestatus[i].setFont(new Font("Tahoma", Font.BOLD, 17));
				Gamestatus[i].setText("");
				Gamestatus[i].setForeground(Color.BLACK);
				add(Gamestatus[i]);
			}

			
			GameLayout.putConstraint(SpringLayout.NORTH, Gamestatus[0], 371, SpringLayout.WEST, this);
			GameLayout.putConstraint(SpringLayout.WEST, Gamestatus[0], 280, SpringLayout.WEST, this);
			
			GameLayout.putConstraint(SpringLayout.NORTH, Gamestatus[1], 0, SpringLayout.SOUTH, Gamestatus[0]);
			GameLayout.putConstraint(SpringLayout.WEST, Gamestatus[1], 0, SpringLayout.WEST, Gamestatus[0]);
			
			GameLayout.putConstraint(SpringLayout.NORTH, Gamestatus[2], 0, SpringLayout.SOUTH, Gamestatus[1]);
			GameLayout.putConstraint(SpringLayout.WEST, Gamestatus[2], 0, SpringLayout.WEST, Gamestatus[1]);
			for(int i = 0; i<37; i++)
			{
				add(stone[i]);
			}			
			
			mymouselistener my = new mymouselistener();
			
			for(int i = 0; i<37; i++)
			{
				stone[i].addMouseListener(my);
			}			
			
	


		}
		
		
		
		
		JLabel infor_button = new JLabel();
		
		void Playerinfor_set()
		{
			if(playerinfor==1)
			{
				infor_button.setIcon(player1_image);
			}
			else if(playerinfor==2)
			{
				infor_button.setIcon(player2_image);
			}
			else if(playerinfor==3)
			{
				infor_button.setIcon(player3_image);
			}
		}
		
		
		
		int jumpafter=0;
		boolean AfterStart = false;
		class mymouselistener implements MouseListener{ // stone ���콺������
			JLabel released_button = new JLabel();//��ư �����ٰ� ��������
			JLabel now_button = new JLabel(); //���� �ö��ִ¹�ư
			JLabel pressed_button = new JLabel();
			JLabel exited_button = new JLabel(); //���������� �����ö� (�� ������ �̹��� �������)
			JLabel clicked_button = new JLabel(); //�׳� Ŭ���ߴٰ� ������
			
			JLabel temp = new JLabel(); 
			
			public void mousePressed(MouseEvent e) { //���콺�� ���������� ����������
				pressed_button = (JLabel)e.getSource();
				if(pressed_button.getIcon()==infor_button.getIcon() && AfterStart==true && gameover==0)
				{
					if(pressed_button!=temp && jumpafter==1)//�����ѵڿ� ������ ������ ���游 ����ϱ� ����
					{
						
					}
					else if(player_turn == 1 && pressed_button.getIcon()==player1_image && player1_status==1)
					{
						Scan(pressed_button);
					}
					else if(player_turn == 2 && pressed_button.getIcon()==player2_image && player2_status==1)
					{
						Scan(pressed_button);
					}
					else if(player_turn == 3 && pressed_button.getIcon()==player3_image && player3_status==1)
					{
						Scan(pressed_button);
					}
					
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				clicked_button = (JLabel)e.getSource();
				
				if(AfterStart==true && clicked_button.getIcon() == infor_button.getIcon() && gameover==0)
				{
					if(player_turn == 1 && player1_image == clicked_button.getIcon() && clicked_button.getIcon()==now_button.getIcon())
					{
						Sounds.Sound();
						StoneChangeInfor= "DONTMOVE_";
						NextTurn();
						
						
					}
					if(player_turn == 2 && player2_image == clicked_button.getIcon() && clicked_button.getIcon()==now_button.getIcon())
					{
						Sounds.Sound();
						StoneChangeInfor= "DONTMOVE_";
						NextTurn();
						
						
					}
					if(player_turn == 3 && player3_image == clicked_button.getIcon() && clicked_button.getIcon()==now_button.getIcon())
					{
						Sounds.Sound();
						StoneChangeInfor= "DONTMOVE_";
						NextTurn();
						
						
					}
					jumpafter=0;
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) { // ���콺�� �������� �ö�����
				now_button = (JLabel)e.getSource();
				if(now_button.getIcon()==player1_null && AfterStart==true)
				{
					now_button.setIcon(player1_null_p);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) { // ���콺�� ���������� �������.
				exited_button = (JLabel)e.getSource();
				
				if(exited_button.getIcon()==player1_null_p && AfterStart==true)
				{
					exited_button.setIcon(player1_null);
				}
				
			}

			@Override
			public void mouseReleased(MouseEvent e) { // ���콺�� ��������
				released_button = (JLabel)e.getSource();

				if(now_button.getIcon()==player1_null_p && AfterStart==true)
				{
					int nextx = now_button.getX();
					int nexty = now_button.getY();
					int befx = released_button.getX();
					int befy = released_button.getY();
					int xlength=0;
					int ylength=0;
					if(nextx>befx)
					{
						xlength = nextx - befx; 
					}
					else if(nextx<befx) {
						xlength = befx - nextx;
					}
					if(nexty>befy)
					{
						ylength = nexty - befy; 
					}
					else if(nexty<befy) {
						ylength = befy - nexty;
					}
					
					String tem="";
					int beforbutton=0;
					for(int i = 0; i<37; i++) // ��ư �Ű����°� ���� �����ϴºκ�
					{
						if(stone[i]==released_button) //�Ű������� ��ư��ġ
						{
							beforbutton=i;
						}
					}
					for(int i = 0; i<37; i++) // ��ư �Ű����°� ���� �����ϴºκ�
					{
						if((ylength+xlength)>110 && stone[i]==now_button)
						{
							tem= "JUMP_"+ beforbutton +"_"+ i+"_";
							
						}
						else if(stone[i]==now_button)// �Ű��� ���� ��ư��ġ
						{
							StoneChangeInfor= "GO_"+ beforbutton +"_"+ i+"_";
							Sounds.Sound();
						}
					}
					
					now_button.setIcon(released_button.getIcon());
					released_button.setIcon(null_image);
					
					
					if((ylength+xlength)>110 && NextJumpScan(now_button)==1)
					{
						temp=now_button;
						jumpafter=1; // ������ ������ �˷��� after=1 �̸� ������ �Ķ�, ������ ���̸� �����δ� �̵����ϰ� �����ؼ��� �̵������ϰ� ���ִ� ������.
						Combo_temp++;//�޺�������
						
						clear();
						Scan(now_button);//������ ���� ��
						int i=0;
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==player1_null)
							{
								if(stone[j]!=released_button)
								{
									i++;
								}
							}
						}
						
						if(i==0)
						{
							jumpafter=0;
							for(int v = 0; v<37; v++) 
							{
								if(stone[v]==now_button)
								{
									StoneChangeInfor= "GO_"+ beforbutton +"_"+ v+"_";  // �����Ұ��� �ٽõ��ư��°� �ۿ��������� JUMP���� GO�� ó���ؼ� �ٽ� ���� ���ϰ�����
									Sounds.Sound();
								}
							}
							NextTurn();
						}
						else
						{
							StoneChangeInfor=tem;
							Sounds.Sound();
						}
						
						
						if(Combo_temp>player1_Combo && now_button.getIcon()==player1_image) //�� �޺����� �޺�Ƚ���� ������ 
						{
							player1_Combo = Combo_temp; // �����޺�Ƚ���� �־���
							try {
								String send="COMBO"+"_"+1+"_"+player1_Combo+"_";
								byte[] byteArray = send.getBytes("UTF-8");  
					            OutputStream outputStream = cliesocket.getOutputStream();
					            outputStream.write(byteArray);// byteArray �� ������ ����( �����¸޽��� ) -=---------�߿�
					            
							} catch (IOException e1) {
							}
							
						}else if(Combo_temp>player2_Combo && now_button.getIcon()==player2_image) //�� �޺����� �޺�Ƚ���� ������ 
						{
							player2_Combo = Combo_temp; // �����޺�Ƚ���� �־���
							try {
								String send="COMBO"+"_"+2+"_"+player2_Combo+"_";
								byte[] byteArray = send.getBytes("UTF-8");  
					            OutputStream outputStream = cliesocket.getOutputStream();
					            outputStream.write(byteArray);// byteArray �� ������ ����( �����¸޽��� ) -=---------�߿�
					            
							} catch (IOException e1) {
							}
						}else if(Combo_temp>player3_Combo && now_button.getIcon()==player3_image) //�� �޺����� �޺�Ƚ���� ������ 
						{
							player3_Combo = Combo_temp; // �����޺�Ƚ���� �־���
							try {
								String send="COMBO"+"_"+3+"_"+player3_Combo+"_";
								byte[] byteArray = send.getBytes("UTF-8");  
					            OutputStream outputStream = cliesocket.getOutputStream();
					            outputStream.write(byteArray);// byteArray �� ������ ����( �����¸޽��� ) -=---------�߿�
					            
							} catch (IOException e1) {
							}
						}
					}
					else
					{
						NextTurn(); // ������ �û�� ���ϴ��Լ�
						
						
					}
						
				}
				clear();
			}
		}
	}
}


 
