import java.io.File;
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

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.*;

import java.awt.event.*;
import java.awt.*;



public class Client2_Server extends JFrame { // 1인용 게임
	Socket cliesocket;
	public Client2_Server(Socket sc) {    //기본 프레임
		setTitle("다이아몬드 게임 ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		c.add(new GamePanel(sc));
		
		cliesocket=sc;

		
		setSize(500,500);
		setLocation(580,200);
		setVisible(true);
		
	}
	
	public class GamePanel extends JPanel{  //패널
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
		
		ImageIcon BackGround = new ImageIcon("images/맵1.png");
		ImageIcon gamestart = new ImageIcon("images/게임시작.png");
		ImageIcon abstain = new ImageIcon("images/기권하기.png");
		ImageIcon gameout = new ImageIcon("images/나가기.png");
		Image im =BackGround.getImage();
		

		
		JLabel TurnStatus = new JLabel();
		
		JLabel []Gamestatus = new JLabel[4];
		
		JLabel GameStart = new JLabel(); // 게임시작 버튼
		
		JLabel Abstain = new JLabel();
		JLabel GameOut = new JLabel();
		
		SpringLayout GameLayout; //스프링 레이아웃 정의
		
		JLabel []stone = new JLabel[37]; //
		
		int player_turn=1;
		String TurnInformation="";
		String StoneChangeInfor ="";
		boolean Next = false;

		int player1_Combo=0;
		int player2_Combo=0;
		int player3_Combo=0;
		int Combo_temp=0;
		
		int totalplayer = 0;
		
		void mapping() { //스톤에 이미지 소스폴더에 있는 이미지 넣기
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
			
			//stone[16] = new JLabel(player2_image);
			//stone[21] = new JLabel(player2_image);
			//stone[22] = new JLabel(player2_image);
		//	stone[27] = new JLabel(player2_image);
			//stone[28] = new JLabel(player2_image);
		//	stone[29] = new JLabel(player2_image);
			
		//	stone[20] = new JLabel(player3_image);
		//	stone[25] = new JLabel(player3_image);
		//	stone[26] = new JLabel(player3_image);
		//	stone[31] = new JLabel(player3_image);
		//	stone[32] = new JLabel(player3_image);
		//	stone[33] = new JLabel(player3_image);
		}
		public void paintComponent(Graphics g){ //맵 그려주기
            super.paintComponent(g);
            g.drawImage(im,0,0,getWidth(),getHeight(),this);
        }
		public void stonedispose() // 스톤 맵에 깔기 
		{
			int bottom=18;
			int left=-31;
			int width=28;
			GameLayout.putConstraint(SpringLayout.NORTH, stone[0], 9, SpringLayout.WEST, this);
			GameLayout.putConstraint(SpringLayout.WEST, stone[0], 214, SpringLayout.WEST, this);
						
			GameLayout.putConstraint(SpringLayout.NORTH, stone[1], bottom, SpringLayout.SOUTH, stone[0]);//맨윗줄이랑 둘째줄 사이 거리 설정
			GameLayout.putConstraint(SpringLayout.NORTH, stone[2], bottom, SpringLayout.SOUTH, stone[0]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[1], left, SpringLayout.WEST, stone[0]);//맨윗돌 왼쪽과 두번째줄 맨왼쪽돌 의 왼쪽위치 사이크기
			GameLayout.putConstraint(SpringLayout.WEST, stone[2], width, SpringLayout.EAST, stone[1]);
			
			for(int i=3; i<=9; i++)//두번째줄 이랑 세번째줄 사이 거리 설정
			{
				GameLayout.putConstraint(SpringLayout.NORTH, stone[i], bottom, SpringLayout.SOUTH, stone[1]);
			}
			
			GameLayout.putConstraint(SpringLayout.WEST, stone[5], left, SpringLayout.WEST, stone[1]);//윗돌 왼쪽과 다음줄 맨왼쪽돌 의 왼쪽위치 사이크기
			
			
			GameLayout.putConstraint(SpringLayout.EAST, stone[4], -width, SpringLayout.WEST, stone[5]);
			GameLayout.putConstraint(SpringLayout.EAST, stone[3], -width, SpringLayout.WEST, stone[4]);

			GameLayout.putConstraint(SpringLayout.WEST, stone[6], width, SpringLayout.EAST, stone[5]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[7], width, SpringLayout.EAST, stone[6]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[8], width, SpringLayout.EAST, stone[7]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[9], width, SpringLayout.EAST, stone[8]);
			
			for(int i=10; i<=15; i++)//두번째줄 이랑 세번째줄 사이 거리 설정
			{
				GameLayout.putConstraint(SpringLayout.NORTH, stone[i], bottom, SpringLayout.SOUTH, stone[5]);
			}
			GameLayout.putConstraint(SpringLayout.WEST, stone[11], left, SpringLayout.WEST, stone[5]);//윗돌 왼쪽과 다음줄 맨왼쪽돌 의 왼쪽위치 사이크기
			
			GameLayout.putConstraint(SpringLayout.EAST, stone[10],-width, SpringLayout.WEST, stone[11]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[12], width, SpringLayout.EAST, stone[11]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[13], width, SpringLayout.EAST, stone[12]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[14], width, SpringLayout.EAST, stone[13]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[15], width, SpringLayout.EAST, stone[14]);
			for(int i=16; i<=21; i++)//두번째줄 이랑 세번째줄 사이 거리 설정
			{
				GameLayout.putConstraint(SpringLayout.NORTH, stone[i], bottom, SpringLayout.SOUTH, stone[11]);
			}
			GameLayout.putConstraint(SpringLayout.WEST, stone[16], left, SpringLayout.WEST, stone[11]);//윗돌 왼쪽과 다음줄 맨왼쪽돌 의 왼쪽위치 사이크기
			
			
			GameLayout.putConstraint(SpringLayout.WEST, stone[17], width, SpringLayout.EAST, stone[16]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[18], width, SpringLayout.EAST, stone[17]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[19], width, SpringLayout.EAST, stone[18]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[20], width, SpringLayout.EAST, stone[19]);
			for(int i=21; i<=27; i++)//두번째줄 이랑 세번째줄 사이 거리 설정
			{
				GameLayout.putConstraint(SpringLayout.NORTH, stone[i], bottom, SpringLayout.SOUTH, stone[16]);
			}
			GameLayout.putConstraint(SpringLayout.WEST, stone[21], left, SpringLayout.WEST, stone[16]);//윗돌 왼쪽과 다음줄 맨왼쪽돌 의 왼쪽위치 사이크기
			
			GameLayout.putConstraint(SpringLayout.WEST, stone[22], width, SpringLayout.EAST, stone[21]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[23], width, SpringLayout.EAST, stone[22]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[24], width, SpringLayout.EAST, stone[23]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[25], width, SpringLayout.EAST, stone[24]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[26], width, SpringLayout.EAST, stone[25]);
			
			for(int i=27; i<=33; i++)//두번째줄 이랑 세번째줄 사이 거리 설정
			{
				GameLayout.putConstraint(SpringLayout.NORTH, stone[i], bottom, SpringLayout.SOUTH, stone[21]);
			}
			GameLayout.putConstraint(SpringLayout.WEST, stone[27], left, SpringLayout.WEST, stone[21]);//윗돌 왼쪽과 다음줄 맨왼쪽돌 의 왼쪽위치 사이크기
			
			GameLayout.putConstraint(SpringLayout.WEST, stone[28], width, SpringLayout.EAST, stone[27]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[29], width, SpringLayout.EAST, stone[28]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[30], width, SpringLayout.EAST, stone[29]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[31], width, SpringLayout.EAST, stone[30]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[32], width, SpringLayout.EAST, stone[31]);
			GameLayout.putConstraint(SpringLayout.WEST, stone[33], width, SpringLayout.EAST, stone[32]);
			
			GameLayout.putConstraint(SpringLayout.WEST, stone[34], left, SpringLayout.WEST, stone[30]);//윗돌 왼쪽과 다음줄 맨왼쪽돌 의 왼쪽위치 사이크기
			for(int i=34; i<=35; i++)//두번째줄 이랑 세번째줄 사이 거리 설정
			{
				GameLayout.putConstraint(SpringLayout.NORTH, stone[i], bottom, SpringLayout.SOUTH, stone[30]);
			}
			GameLayout.putConstraint(SpringLayout.WEST, stone[35], width, SpringLayout.EAST, stone[34]);
			
			GameLayout.putConstraint(SpringLayout.WEST, stone[36], left, SpringLayout.WEST, stone[35]);//윗돌 왼쪽과 다음줄 맨왼쪽돌 의 왼쪽위치 사이크기
			GameLayout.putConstraint(SpringLayout.NORTH, stone[36], bottom, SpringLayout.SOUTH, stone[35]);
			
		}
		
		void Scan(JLabel click) { // 스톤 클릭했을때 전체 맵 스캔해서 이동할수 있는부분 널 _null이미지로 표시해주는역할
			
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
				else if((sum==62 || sum==83) && stone[i].getIcon()!=null_image) //점프되는거 넣는 부분임
				{
					if(x-stone[i].getX()>0 && y-stone[i].getY()==0) //왼쪽
					{
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==null_image && stone[i].getX()-stone[j].getX()==62 && stone[i].getY()-stone[j].getY()==0)
							{
								stone[j].setIcon(player1_null); //점프가능한 부분에 이동가능 표시 남김
							}
						}
					}
					else if(x-stone[i].getX()<0 && y-stone[i].getY()==0)//오른쪽
					{
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==null_image && stone[i].getX()-stone[j].getX()==-62 && stone[i].getY()-stone[j].getY()==0)
							{
								stone[j].setIcon(player1_null); //점프가능한 부분에 이동가능 표시 남김
							}
						}
					}
					else if(x-stone[i].getX()>0 && y-stone[i].getY()>0)// 왼쪽위
					{
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==null_image && stone[i].getX()-stone[j].getX()==31 && stone[i].getY()-stone[j].getY()==52)
							{
								stone[j].setIcon(player1_null); //점프가능한 부분에 이동가능 표시 남김
							}
						}
					}
					else if(x-stone[i].getX()>0 && y-stone[i].getY()<0)// 왼쪽아래
					{
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==null_image && stone[i].getX()-stone[j].getX()==31 && stone[i].getY()-stone[j].getY()==-52)
							{
								stone[j].setIcon(player1_null); //점프가능한 부분에 이동가능 표시 남김
							}
						}
					}
					else if(x-stone[i].getX()<0 && y-stone[i].getY()>0)// 오른쪽위
					{
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==null_image && stone[i].getX()-stone[j].getX()==-31 && stone[i].getY()-stone[j].getY()==52)
							{
								stone[j].setIcon(player1_null); //점프가능한 부분에 이동가능 표시 남김
							}
						}
					}
					else if(x-stone[i].getX()<0 && y-stone[i].getY()<0)// 오른쪽아래
					{
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==null_image && stone[i].getX()-stone[j].getX()==-31 && stone[i].getY()-stone[j].getY()==-52)
							{
								stone[j].setIcon(player1_null); //점프가능한 부분에 이동가능 표시 남김
							}
						}
					}
				}
				
				
			}	
		}
		
		
		void Timeout_move() { //타이머 0이되면 랜덤으로 돌 하나 골라서 랜덤으로 이동(점프가능)
			int temp2;
			int temp;
			int i = 0;
			if(player_turn==1&& jumpafter==0)
			{
				//player1 돌중 하나 랜덤선택해서 움직일수있으면 움직여주기 점프도 가능
				while(true)
				{
					i=0;
					temp = (int)(36*Math.random());
					if(stone[temp].getIcon()==player1_image )
					{
						while(true)////////////////////////////////////
						{
							Scan(stone[temp]);
							temp2 = (int)(36*Math.random());
							if(stone[temp2].getIcon()==player1_null)
							{
								stone[temp2].setIcon(player1_image);
								stone[temp].setIcon(null_image);
								clear();
								StoneChangeInfor= "JUMP_"+ temp +"_"+ temp2+"_";
								temp2 = -100;
								break;
							}
							else 
							{
								i++;
								clear();
							}
							
							if(i==570)
							{
								break;
							}
						
						}///////////////////////////////////////////
						if(temp2==-100)
							break;
					}
				}
			}
			else if(player_turn==2&& jumpafter==0)
			{
				while(true)
				{
					i=0;
					temp = (int)(36*Math.random());
					if(stone[temp].getIcon()==player2_image )
					{
						
						
						while(true)
						{
							Scan(stone[temp]);
							temp2 = (int)(36*Math.random());
							if(stone[temp2].getIcon()==player1_null)
							{
								stone[temp2].setIcon(player2_image);
								stone[temp].setIcon(null_image);
								clear();
								StoneChangeInfor= "JUMP_"+ temp +"_"+ temp2+"_";
								temp2 = -100;
								break;
							}
							else 
							{
								i++;
								clear();
							}
							
							if(i==570)
							{
								break;
							}
						
						}///////////////////////////////////////////
						if(temp2==-100)
							break;
					}
				}
			}
			else if(player_turn==3&& jumpafter==0)
			{
				while(true)
				{
					i=0;
					temp = (int)(36*Math.random());
					if(stone[temp].getIcon()==player3_image )
					{
						
						while(true)
						{
							Scan(stone[temp]);
							temp2 = (int)(36*Math.random());
							if(stone[temp2].getIcon()==player1_null)
							{
								stone[temp2].setIcon(player3_image);
								stone[temp].setIcon(null_image);
								clear();
								StoneChangeInfor= "JUMP_"+ temp +"_"+ temp2+"_";
								temp2 = -100;
								break;
							}
							else 
							{
								i++;
								clear();
							}
							
							if(i==570)
							{
								break;
							}
						
						}///////////////////////////////////////////
						if(temp2==-100)
							break;
					}
				}
			}
			else if(jumpafter==1)
			{
				clear();
				StoneChangeInfor= "DONTMOVE_";
				NextTurn();
			}
		}
		
		int NextJumpScan(JLabel button) { // 다음점프 있는지 확인
			
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
				
				if((sum==62 || sum==83) && stone[i].getIcon()!=null_image) //점프되는거 넣는 부분임
				{
					if(x-stone[i].getX()>0 && y-stone[i].getY()==0) //왼쪽
					{
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==null_image && stone[i].getX()-stone[j].getX()==62 && stone[i].getY()-stone[j].getY()==0)
							{
								return 1;
							}
						}
					}
					else if(x-stone[i].getX()<0 && y-stone[i].getY()==0)//오른쪽
					{
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==null_image && stone[i].getX()-stone[j].getX()==-62 && stone[i].getY()-stone[j].getY()==0)
							{
								return 1;//점프가능할때 내턴을 유지함
							}
						}
					}
					else if(x-stone[i].getX()>0 && y-stone[i].getY()>0)// 왼쪽위
					{
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==null_image && stone[i].getX()-stone[j].getX()==31 && stone[i].getY()-stone[j].getY()==52)
							{
								return 1;//점프가능할때 내턴을 유지함
							}
						}
					}
					else if(x-stone[i].getX()>0 && y-stone[i].getY()<0)// 왼쪽아래
					{
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==null_image && stone[i].getX()-stone[j].getX()==31 && stone[i].getY()-stone[j].getY()==-52)
							{
								return 1;//점프가능할때 내턴을 유지함
							}
						}
					}
					else if(x-stone[i].getX()<0 && y-stone[i].getY()>0)// 오른쪽위
					{
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==null_image && stone[i].getX()-stone[j].getX()==-31 && stone[i].getY()-stone[j].getY()==52)
							{
								return 1;//점프가능할때 내턴을 유지함
							}
						}
					}
					else if(x-stone[i].getX()<0 && y-stone[i].getY()<0)// 오른쪽아래
					{
						for(int j = 0; j<37; j++)
						{
							if(stone[j].getIcon()==null_image && stone[i].getX()-stone[j].getX()==-31 && stone[i].getY()-stone[j].getY()==-52)
							{
								return 1;//점프가능할때 내턴을 유지함
							}
						}
					}
					
				}
				
				
			}	
			return 0;
		}
		
		void clear() // 널 포인터 이미지 원래대로 클리어
		{
			for(int i = 0; i<37; i++)
			{
				if(stone[i].getIcon()!=player1_image && stone[i].getIcon()!=player3_image && stone[i].getIcon()!=player2_image)
					stone[i].setIcon(null_image);
				
			}
		}
		int GameStatus_i=0;
		int player1_status=1;//player1의 게임이 끝났는지 .. 1일시 게임진행중
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
		int gameover=0;
		void GameStatus() { // 이름, 점수 , 콤보 내림차순으로 출력
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
		
		
		void NextTurn() { // 다음턴 올사람 구하는함수
			Combo_temp=0;
			if(player_turn==1 && player2_status==1) // 플레이어1이 턴일때
			{
				player_turn++;
				TurnStatus.setText(player2_Nickname+" Turn");
				TurnStatus.setForeground(Color.BLUE);
				GameStatus();
			
				TurnInformation="TURN_2";
				n=setup.Time-2;
				
				
			}
			else if(player_turn==1 && player2_status==0 && player3_status==1 )
			{
				player_turn=3;
				TurnStatus.setText(player3_Nickname+" Turn");
				TurnStatus.setForeground(Color.GREEN);
				GameStatus();
			
				TurnInformation="TURN_3";
				n=setup.Time-2;
			}
			else if(player_turn==1 && player2_status==0 && player3_status==0 )
			{
				GameStatus();
				n=setup.Time-2;
			}
			
			else if(player_turn==2 && player3_status==1) // 플레이어 2가 턴일때
			{
				player_turn++;
				TurnStatus.setText(player3_Nickname+" Turn");
				TurnStatus.setForeground(Color.GREEN);
				GameStatus();
			
				TurnInformation="TURN_3";
				n=setup.Time-2;
			}else if(player_turn==2 && player3_status==0 && player1_status==1)
			{
				player_turn=1;
				TurnStatus.setText(player1_Nickname+" Turn");
				TurnStatus.setForeground(Color.BLACK);
				GameStatus();
			
				TurnInformation="TURN_1";
				n=setup.Time-2;
			}else if(player_turn==2 && player3_status==0 && player1_status==0)
			{
				GameStatus();
			
				n=setup.Time-2;
			}
			
			else if(player_turn==3 && player1_status==1) // 플레이어3이 턴일때
			{
				player_turn=1;
				TurnStatus.setText(player1_Nickname+" Turn");
				TurnStatus.setForeground(Color.BLACK);
				GameStatus();
			
				TurnInformation="TURN_1";
				n=setup.Time-2;
			}else if(player_turn==3 && player1_status==0 && player2_status==1)
			{
				player_turn=2;
				TurnStatus.setText(player2_Nickname+" Turn");
				TurnStatus.setForeground(Color.BLUE);
				GameStatus();
			
				TurnInformation="TURN_2";
				n=setup.Time-2;
			}else if(player_turn==3 && player1_status==0 && player2_status==0)
			{
				GameStatus();
				n=setup.Time-2;
				
			}
		}
		
		boolean timeset=false;
		int n = setup.Time-2;
		class TimerThread extends Thread{  // 타이머 부분 스레드
			private JLabel timerLabel;
			public TimerThread(JLabel timerLabel) {
				this.timerLabel = timerLabel;
			}
			
			public void run() {
				
				while(true)
				{
					timerLabel.setText(Integer.toString(n));
					n--;
					if(timeset==true)//서버에서 시간초기화 하라고하면
					{
						
						n=setup.Time-2;
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
		int abstain_num=0;
		int abstain_player=0;
		class ClientReader extends Thread {  // 받는부분 스레드
		    Socket clientSocket;
		 
		    ClientReader(Socket clientSocket) {
		        this.clientSocket = clientSocket;
		    }
		 
		    @Override
		    public synchronized void run() {
		        try {
		        	
		        		
		            while (true) {
		            	InputStream inputStream = clientSocket.getInputStream();
			        	byte[] byteArray = new byte[256];
			        	
		                int size = inputStream.read(byteArray);
		                String readMessage = new String(byteArray, 0, size, "UTF-8");   //read message가 받는 스트링임 -----------중요
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
		                			Sounds.Sound();
				                	NextTurn();
				                }
		                else if(s[0].equals("TIMESET"))
		                {
		                	timeset =true; 
		                	Timeout_move();
		                	NextTurn();
		                	
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
		                else if(s[0].equals("ABSTAIN")&& s[1].equals("PLAYER"))
		                {		      
		                	abstain_num++;
		                	
		                	
		                	int t= Integer.parseInt(s[2]);
		                	abstain_player=t;
		                	if(t==1)
		                	{
		                		for(int i = 0; i<37; i++) // 버튼 옮겨지는거 정보 제공하는부분
		    					{
		    						if(stone[i].getIcon()==player1_image) //옮겨지기전 버튼위치
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
		                		for(int i = 0; i<37; i++) // 버튼 옮겨지는거 정보 제공하는부분
		    					{
		    						if(stone[i].getIcon()==player2_image) //옮겨지기전 버튼위치
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

		                		for(int i = 0; i<37; i++) // 버튼 옮겨지는거 정보 제공하는부분
		    					{
		    						if(stone[i].getIcon()==player3_image) //옮겨지기전 버튼위치
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
		                		player1_Nickname=s[3];
		                	else if(k==2)
		                		player2_Nickname=s[3];
		                	else if(k==3)
		                		player3_Nickname=s[3];
		                }
		                else if(s[0].equals("GAMEOVER"))
		                {
		                	gameover=1;
		                	th.stop();
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
		
		class ConnectThread extends Thread { //보내는 부분 스레드
		    Socket clientSocket = null;
		    
		    ConnectThread(Socket clientSocket) {
		        this.clientSocket = clientSocket;
		    }
		 
		    @Override
		    public synchronized void run() {
		    	
		        try {
		        	
		        	while (true) {	
		        		
		            	
		                if(StoneChangeInfor.length()>2)
		                {
		                	 byte[] byteArray = StoneChangeInfor.getBytes("UTF-8");  
				                OutputStream outputStream = clientSocket.getOutputStream();
				                outputStream.write(byteArray);// byteArray 를 서버로 전송( 보내는메시지 ) -=---------중요
				                StoneChangeInfor="";
		                }
		                
		           
		            	sleep(500);
		            	
		            }
		        	
		        } catch (Exception e) {}
		    }
		}//
		int playerinfor=1;
		
		String player1_name;
		String player2_name;
		String player3_name;
		
		public GamePanel(Socket sc){
			My_Nickname_set();

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
        	clientReader.start();//받는부분 스레드 시작  --멀티스레드
			
			
			TurnStatus.setFont(new Font("Tahoma", Font.BOLD, 30));
			
			TurnStatus.setForeground(Color.BLACK);

			timerLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
			
			
			Abstain= new JLabel(abstain);
			Abstain.addMouseListener(new MouseAdapter() { // 기권하기 눌렀을때
				public void mousePressed(MouseEvent e) {
				
					abstain_num++;
					GameOut.setVisible(true);
					
					if(playerinfor==1)
                	{
						abstain_player=1;
                		for(int i = 0; i<37; i++) // 버튼 옮겨지는거 정보 제공하는부분
    					{
    						if(stone[i].getIcon()==player1_image) //옮겨지기전 버튼위치
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
                		for(int i = 0; i<37; i++) // 버튼 옮겨지는거 정보 제공하는부분
    					{
    						if(stone[i].getIcon()==player2_image) //옮겨지기전 버튼위치
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
                		for(int i = 0; i<37; i++) // 버튼 옮겨지는거 정보 제공하는부분
    					{
    						if(stone[i].getIcon()==player3_image) //옮겨지기전 버튼위치
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
		                outputStream.write(byteArray);// byteArray 를 서버로 전송( 보내는메시지 ) -=---------중요
		                
		                Abstain.setVisible(false);
					} catch (Exception de) {
			        }
					
				}
			});
			
			
			
			class GAMESTARTThread extends Thread { //보내는 부분 스레드
				boolean k = true;
			    public void run() {
			    	
			        try {
			        	
			        	while (k) {	
			        		
			            	
			        		try {
								String send="PLAYER"+"_"+playerinfor+"_nickname_"+setup.NickName+"_";
								byte[] byteArray = send.getBytes("UTF-8");  
					            OutputStream outputStream = clientSocket.getOutputStream();
					            outputStream.write(byteArray);// byteArray 를 서버로 전송( 보내는메시지 ) -=---------중요
					            
							} catch (IOException e1) {
								sleep(500);
							}
							try {
								String s = "TIMEcorrecT_"+setup.Time+"_";
								byte[] byteArray = s.getBytes("UTF-8");  
				                OutputStream outputStream = clientSocket.getOutputStream();
				                outputStream.write(byteArray);// byteArray 를 서버로 전송( 보내는메시지 ) -=---------중요
				                
							} catch (Exception de) {
					        }
							sleep(500);
							try {
								String s = "GAMESTART_";
								byte[] byteArray = s.getBytes("UTF-8");  
				                OutputStream outputStream = clientSocket.getOutputStream();
				                outputStream.write(byteArray);// byteArray 를 서버로 전송( 보내는메시지 ) -=---------중요
				                th.start();
				                GameStart.setVisible(false);
				                AfterStart=true;
				                Abstain.setVisible(true);
							} catch (Exception de) {
					        }
							this.stop();
			            }
			        	
			        } catch (Exception e) {}
			    }
			}//
			

		
			GameStart= new JLabel(gamestart);
			GameStart.addMouseListener(new MouseAdapter() { // 게임시작 눌렀을때
				public void mousePressed(MouseEvent e) {
					
					GAMESTARTThread d = new GAMESTARTThread();
					d.start();
					
					TurnStatus.setText(player1_Nickname+" Turn");
				}
			});
			
			GameOut= new JLabel(gameout);
			GameOut.addMouseListener(new MouseAdapter() { // 나가기 눌렀을때
				public void mousePressed(MouseEvent e) {
						try {
							String s = "EXIT_PLAYER_"+playerinfor+"_";
							byte[] byteArray = s.getBytes("UTF-8");  
			                OutputStream outputStream = clientSocket.getOutputStream();
			                outputStream.write(byteArray);// byteArray 를 서버로 전송( 보내는메시지 ) -=---------중요

						} catch (Exception de) {
				        }
					dispose();
					}
			});
			
			
			GameLayout.putConstraint(SpringLayout.NORTH, timerLabel, 0, SpringLayout.WEST, this);
			GameLayout.putConstraint(SpringLayout.EAST, timerLabel, 0, SpringLayout.EAST, this);
			
			
			add(timerLabel);
			
			GameLayout.putConstraint(SpringLayout.NORTH, Abstain, 380, SpringLayout.SOUTH, TurnStatus);
			GameLayout.putConstraint(SpringLayout.WEST, Abstain, 0, SpringLayout.WEST, TurnStatus);
			
			add(Abstain);
			Abstain.setVisible(false);
			
			
			GameLayout.putConstraint(SpringLayout.NORTH, GameStart, 380, SpringLayout.SOUTH, TurnStatus);
			GameLayout.putConstraint(SpringLayout.WEST, GameStart, 0, SpringLayout.WEST, TurnStatus);
			
			GameLayout.putConstraint(SpringLayout.NORTH, GameOut, 380, SpringLayout.SOUTH, TurnStatus);
			GameLayout.putConstraint(SpringLayout.WEST, GameOut, 0, SpringLayout.WEST, TurnStatus);
			
			add(GameOut);
			add(GameStart);
			add(TurnStatus);
			GameOut.setVisible(false);
			
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
		
		
		JLabel infor_button = new JLabel(player1_image);
		
		
		
		int jumpafter=0;
		boolean AfterStart=false;
		class mymouselistener implements MouseListener{ // stone 마우스리스너
			JLabel released_button = new JLabel();//버튼 눌럿다가 때어질때
			JLabel now_button = new JLabel(); //현재 올라가있는버튼
			JLabel pressed_button = new JLabel();
			JLabel exited_button = new JLabel(); //스톤위에서 내려올때 (널 포인터 이미지 원래대로)
			JLabel clicked_button = new JLabel(); //그냥 클릭했다가 땟을때
			
			JLabel temp = new JLabel(); 
			
			public void mousePressed(MouseEvent e) { //마우스가 스톤위에서 눌러졌을때
				pressed_button = (JLabel)e.getSource();
				if(pressed_button.getIcon()==infor_button.getIcon() &&  AfterStart==true && gameover==0)
				{
					if(pressed_button!=temp && jumpafter==1)//점프한뒤에 무조건 눌럿던 스톤만 사용하기 위함
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
				
				if( AfterStart==true && clicked_button.getIcon()==infor_button.getIcon() && gameover==0) {
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
			public void mouseEntered(MouseEvent e) { // 마우스가 스톤위에 올라갔을때
				now_button = (JLabel)e.getSource();
				if(now_button.getIcon()==player1_null &&  AfterStart==true)
				{
					now_button.setIcon(player1_null_p);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) { // 마우스가 스톤위에서 벗어낫을때.
				exited_button = (JLabel)e.getSource();
				
				if(exited_button.getIcon()==player1_null_p &&  AfterStart==true)
				{
					exited_button.setIcon(player1_null);
				}
				
			}
			
			
			
			@Override
			public void mouseReleased(MouseEvent e) { // 마우스가 때어질때
				released_button = (JLabel)e.getSource();

				if(now_button.getIcon()==player1_null_p &&  AfterStart==true)
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
					for(int i = 0; i<37; i++) // 버튼 옮겨지는거 정보 제공하는부분
					{
						if(stone[i]==released_button) //옮겨지기전 버튼위치
						{
							beforbutton=i;
						}
					}
					for(int i = 0; i<37; i++) // 버튼 옮겨지는거 정보 제공하는부분
					{
						if((ylength+xlength)>110 && stone[i]==now_button)
						{
							tem= "JUMP_"+ beforbutton +"_"+ i+"_";
							
						}
						else if(stone[i]==now_button)// 옮겨진 후의 버튼위치
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
						jumpafter=1; // 점프한 후인지 알려줌 after=1 이면 점프한 후라서, 점프한 후이면 옆으로는 이동못하고 점프해서만 이동가능하게 해주는 변수임.
						Combo_temp++;//콤보수증가
						
						clear();
						Scan(now_button);//점프한 후의 돌
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
							for(int v = 0; v<37; v++) // 버튼 옮겨지는거 정보 제공하는부분
							{
								if(stone[v]==now_button)// 옮겨진 후의 버튼위치
								{
									StoneChangeInfor= "GO_"+ beforbutton +"_"+ v+"_";
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
						
						
						if(Combo_temp>player1_Combo && now_button.getIcon()==player1_image) //전 콤보보다 콤보횟수가 많을시 
						{
							player1_Combo = Combo_temp; // 현재콤보횟수를 넣어줌
							try {
								String send="COMBO"+"_"+1+"_"+player1_Combo+"_";
								byte[] byteArray = send.getBytes("UTF-8");  
					            OutputStream outputStream = cliesocket.getOutputStream();
					            outputStream.write(byteArray);// byteArray 를 서버로 전송( 보내는메시지 ) -=---------중요
					            
							} catch (IOException e1) {
							}
							
						}else if(Combo_temp>player2_Combo && now_button.getIcon()==player2_image) //전 콤보보다 콤보횟수가 많을시 
						{
							player2_Combo = Combo_temp; // 현재콤보횟수를 넣어줌
							try {
								String send="COMBO"+"_"+2+"_"+player2_Combo+"_";
								byte[] byteArray = send.getBytes("UTF-8");  
					            OutputStream outputStream = cliesocket.getOutputStream();
					            outputStream.write(byteArray);// byteArray 를 서버로 전송( 보내는메시지 ) -=---------중요
					            
							} catch (IOException e1) {
							}
						}else if(Combo_temp>player3_Combo && now_button.getIcon()==player3_image) //전 콤보보다 콤보횟수가 많을시 
						{
							player3_Combo = Combo_temp; // 현재콤보횟수를 넣어줌
							try {
								String send="COMBO"+"_"+3+"_"+player3_Combo+"_";
								byte[] byteArray = send.getBytes("UTF-8");  
					            OutputStream outputStream = cliesocket.getOutputStream();
					            outputStream.write(byteArray);// byteArray 를 서버로 전송( 보내는메시지 ) -=---------중요
					            
							} catch (IOException e1) {
							}
						}
					}
					else
					{
						NextTurn(); // 다음턴 올사람 구하는함수
						
						
					}
						
				}
				clear();
			}
		}
	}
}


 
