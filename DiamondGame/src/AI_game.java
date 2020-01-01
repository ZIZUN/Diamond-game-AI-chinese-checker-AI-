import javax.swing.*;
import java.awt.event.*;
import java.io.OutputStream;
import java.util.Vector;
import java.awt.*;



public class AI_game extends JFrame { // 인공지능 게임
	public AI_game() {    //기본 프레임
		setTitle("다이아몬드 게임 ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		c.add(new GamePanel());
		

		
		setSize(500,500);
		setLocation(580,200);
		setVisible(true);
		
	}
	
	
	
	public class GamePanel extends JPanel{  //패널
		JLabel timerLabel = new JLabel();
		TimerThread th = new TimerThread(timerLabel);

		ImageIcon null_image = new ImageIcon("images/null.png");
		ImageIcon Black_stone = new ImageIcon("images/player1.png");
		ImageIcon Blue_stone = new ImageIcon("images/player2.png");
		ImageIcon Green_stone = new ImageIcon("images/player3.png");
		
		ImageIcon player1_null = new ImageIcon("images/player1_null.png");
		ImageIcon player1_null_p = new ImageIcon("images/player1_null_p.png");
		ImageIcon player2_null = new ImageIcon("images/player2_null.png");
		ImageIcon player3_null = new ImageIcon("images/player3_null.gif");
		
		ImageIcon BackGround = new ImageIcon("images/맵1.png");
		ImageIcon abstain = new ImageIcon("images/기권하기.png");
		ImageIcon gameout = new ImageIcon("images/나가기.png");
		Image im =BackGround.getImage();
		
		JLabel TurnStatus = new JLabel();
		JLabel Abstain = new JLabel();
		JLabel GameOut = new JLabel();
		
		JLabel []Gamestatus = new JLabel[4];
		
		SpringLayout GameLayout; //스프링 레이아웃 정의
		
		JLabel []stone = new JLabel[37]; //
		
		int player_turn=1;
		 
		int player1_Combo=0;
		int player2_Combo=0;
		int player3_Combo=0;
		int Combo_temp=0;
		
		class TimerThread extends Thread{
			private JLabel timerLabel;
			int n = setup.Time-2;
			public TimerThread(JLabel timerLabel) {
				this.timerLabel = timerLabel;
			}

			public void run() {
				
				while(true)
				{
					if(n==0)
					{
						Timeout_move();
						jumpafter=0;
						n=setup.Time-2;
						
					}
					
					if(player1_status==0 || player2_status==0)
					{
						GameStatus();
						Abstain.setVisible(false);
						GameOut.setVisible(true);
						interrupt();
						d.stop();
						
					}
					timerLabel.setText(Integer.toString(n));
					n--;
					
					
					
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
		
	
		int AI_Destination_x;
		int AI_Destination_y;
		int xlength;
		int ylength;
		int Destination_xlength_i;
		int Destination_ylength_i ;
		

		int Destination_sumlength_i ;
		
		int Destination_xlength_j ;
		int Destination_ylength_j ;
		
		
		int Destination_sumlength_j;
		
		int xlen;
		int ylen;
		int percent;
		int sum;
		
		
		void func_variable(int a, int b)// 인공지능쓰레드에 필요한 변수 설정해주는 함수
		{
			int i  = a;
			int j = b;
			xlength= stone[i].getX() - stone[j].getX();
			ylength= stone[i].getY() - stone[j].getY();
			
			xlen=0; 
			ylen=0;
			
			if(xlength<0)
				xlen = (-xlength);
			else
				xlen = xlength;
			
			if(ylength<0)
				ylen = (-ylength);
			else
				ylen = ylength;
			
			sum = xlen+ylen;

			
			
			Destination_xlength_i = AI_Destination_x- stone[i].getX(); //stone[i]와 목적지의 x길이
			Destination_ylength_i = AI_Destination_y- stone[i].getY();//stone[i]와 목적지의 y길이
			
			if(Destination_xlength_i<0)
				Destination_xlength_i= -Destination_xlength_i;
			if(Destination_ylength_i<0)
				Destination_ylength_i= -Destination_ylength_i;
			
			Destination_sumlength_i = Destination_xlength_i+Destination_ylength_i;
			
			Destination_xlength_j = AI_Destination_x- stone[j].getX(); //stone[j]와 목적지의 x길이
			Destination_ylength_j = AI_Destination_y- stone[j].getY(); //stone[j]와 목적지의 y길이
			
			if(Destination_xlength_j<0)
				Destination_xlength_j= -Destination_xlength_j;
			if(Destination_ylength_j<0)
				Destination_ylength_j= -Destination_ylength_j;
			
			Destination_sumlength_j = Destination_xlength_j+Destination_ylength_j;			
		}
		
		class AI_Control extends Thread{ //간단한 인공지능 쓰레드
			public void run() {
				
				while(true)
				{
					
					if(player1_status==0 || player2_status==0 || player_turn==1)
					{
						this.stop();
					}
					try {
						sleep(800);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(player_turn==2)
					{
						
						try {    //일단 0.1초정도 텀을 둠
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(player2_Location=="TOP")
						{
							AI_Destination_x= 214;
							AI_Destination_y= 425;
						}
						else if(player2_Location=="LEFT")
						{
							AI_Destination_x= 400;
							AI_Destination_y= 113;
						}
						else if(player2_Location=="RIGHT")
						{
							AI_Destination_x= 28;
							AI_Destination_y= 113;
						}
				
						int tem=0;
						
						while(true)
						{
							int i = (int)(37*Math.random());
							
							if(stone[i].getIcon()==player2_image)
							{
								///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
								if(player1_status==0 || player2_status==0 || player_turn==1)
								{
									this.stop();
								}
								
								//최대 점프경로 탐색
								//while(true)
								Scan(stone[i]);
								Vector<Vector<Integer>> vect = new Vector<Vector<Integer>>() ;
								
								for(int p = 0 ; p<=36; p++)
								{
									Vector<Integer> v = new Vector<Integer>();
									v.add(i);
									if(stone[p].getIcon()==player1_null)
									{
										func_variable(i, p);
										
										if(sum==124 || sum==166)//점프이면
										{
											v.add(p);
											clear();
											Scan(stone[p]);
									
											
											for(int q = 0 ; q<=36; q++)
											{
												if(stone[q].getIcon()==player1_null)
												{
													func_variable(p, q);
													
													if(i==q)
														continue;
													
													if(sum==124 || sum==166)//점프이면
													{
														v.add(q);
														clear();
														Scan(stone[q]);
													
														for(int r = 0 ; r<=36; r++)
														{
															if(stone[r].getIcon()==player1_null)
															{
																func_variable(q, r);
																
																if(p==r)
																	continue;
																
																if(sum==124 || sum==166)//점프이면
																{
																	v.add(r);
																	clear();
																	Scan(stone[r]);
																
																	for(int s = 0 ; s<=36; s++)
																	{
																		if(stone[s].getIcon()==player1_null)
																		{
																			func_variable(r, s);
																			
																			if(q==s)
																				continue;
																			
																			if(sum==124 || sum==166)//점프이면 
																			{
																				v.add(s);
																				clear();
																				Scan(stone[s]);
																			
																				for(int t = 0 ; t<=36; t++)
																				{
																					if(stone[t].getIcon()==player1_null)
																					{
																						func_variable(s, t);
																						
																						if(r==t)
																							continue;
																						
																						if(sum==124 || sum==166)//점프이면 -- 5번점프까지가능하게 만들엇음 점프 더하게 할수도 있음
																						{
																							v.add(t);
																							clear();
																					
																						}
																						else 
																							continue;
																					}
																					

																				}
																			}
																			else 
																				continue;
																		}
																		

																	}
																}
																else 
																	continue;
															}
															

														}
														{
															vect.add(v); //점프 1~5번 가능한 것들을 vector에 모두저장
														}
													}
													else 
														continue;
												}
												

											}
											
										}
										else
											continue;
										
										
									}
								}
								Vector<Vector<Integer>> vect2 = new Vector<Vector<Integer>>() ;

								for(int x=0; x < vect.size() ; x++)
								{
									
									int first_stone = vect.get(x).get(0);// 첫째돌
									int last_stone = vect.get(x).lastElement();// 마지막돌
									
									func_variable(first_stone, last_stone);
									if(player2_Location=="TOP")          // 목적지의 큰방향성과 맞지않는다면 확률 낮춘다.
									{
										if(stone[last_stone].getX()<=90 || stone[last_stone].getX()>=338  
												|| (stone[last_stone].getX()==28 && stone[last_stone].getY()==113)	//왼쪽위
												|| (stone[last_stone].getX()==400 && stone[last_stone].getY()==321)//오른쪽밑
												|| (stone[last_stone].getX()==28 && stone[last_stone].getY()==321)//왼쪽밑
												|| (stone[last_stone].getX()==400 && stone[last_stone].getY()==113)//오른쪽위
												)// 가는 길목에서 멀리 벗어나는것(width)을 잡아줌.
										{
											continue;
										}
										if(stone[last_stone].getY()-stone[first_stone].getY()<0)//뒤로가는 확률 낮춤
										{
											continue;
										}
										else if(stone[last_stone].getY()-stone[first_stone].getY()==0)//그줄에 있는 확률 낮춤.
										{
											continue;
										}
										if(ylength>0)
											continue;
									}
									else if(player2_Location=="LEFT")
									{
										if(stone[last_stone].getY()==61 || stone[last_stone].getY()==373  || (stone[last_stone].getX()==369 && stone[last_stone].getY()==269) 
												|| (stone[last_stone].getX()==338 && stone[last_stone].getY()==321)|| xlen==31 || xlen==62 
												|| (stone[last_stone].getX()==214 && stone[last_stone].getY()==425)//밑
												|| (stone[last_stone].getX()==28 && stone[last_stone].getY()==113)	//왼쪽위
												|| (stone[last_stone].getX()==214 && stone[last_stone].getY()==9)//위
												|| (stone[last_stone].getX()==400 && stone[last_stone].getY()==321))//오른쪽밑
											// 가는 길목에서 멀리 벗어나는것(width)을 잡아줌.
										{
											continue;
										}
										if(stone[last_stone].getX()-stone[first_stone].getX()<0)//뒤로가는 확률 낮춤
										{	
											continue;
										}
										else if(stone[last_stone].getX()-stone[first_stone].getX()==0)//그줄에 있는 확률 낮춤.
										{
											continue;
										}
										if(xlength>0)
											continue;
									}
									else if(player2_Location=="RIGHT")
									{
										if(stone[last_stone].getY()==61 || stone[last_stone].getY()==373  ||  (stone[last_stone].getX()==59 && stone[last_stone].getY()==269) 
												|| (stone[last_stone].getX()==90 && stone[last_stone].getY()==321)|| xlen==31 || xlen==62
												|| (stone[last_stone].getX()==214 && stone[last_stone].getY()==425)//밑
												|| (stone[last_stone].getX()==28 && stone[last_stone].getY()==321)//왼쪽밑
												|| (stone[last_stone].getX()==400 && stone[last_stone].getY()==113)//오른쪽위
												|| (stone[last_stone].getX()==214 && stone[last_stone].getY()==9))//위
											
											// 가는 길목에서 멀리 벗어나는것(width)을 잡아줌.
										{
											continue;
										}
										if(stone[last_stone].getX()-stone[first_stone].getX()>0)//뒤로가는 확률 낮춤
										{
											continue;
										}
										else if(stone[last_stone].getX()-stone[first_stone].getX()==0)//그줄에 있는 확률 낮춤.
										{
											continue;
										}
										if(xlength<0)
											continue;
									}
									vect2.add(vect.get(x));
									
								}
								Vector<Vector<Integer>> vect3 = new Vector<Vector<Integer>>() ;
								if(vect2.size()>1)
								{
									for(int y=0; y<vect2.size(); y++)
									{
										func_variable(vect2.get(y).get(0), vect2.get(y).lastElement());
										
										if(Destination_sumlength_j>Destination_sumlength_i) // 목적지와 거리가 더 멀면 이동할 확률 낮춘다.
										{
											break;
										}
										
										
										if(Destination_sumlength_i<=84 || Destination_sumlength_i==145 || Destination_sumlength_i==104)  // stone[i]-현재 스톤   과 목적지의 거리가 일정수준 이하이면 움직이지않음
										{
											break;
										}
										vect3.add(vect2.get(y));
									}
								}
								else if(vect2.size()==1)
								{
									int siz = vect2.get(0).size();
									int combo=0;
									for(int z = 0 ; z<siz-1; z++)
									{
										clear();
										stone[vect2.get(0).get(z)].setIcon(null_image);
										stone[vect2.get(0).get(z+1)].setIcon(player2_image);
										Sounds.Sound();
										
										combo++;
										if(combo>player2_Combo)
											player2_Combo=combo;
										
										try {
											sleep(800);
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									
									NextTurn();
									break;
								}
								
								if(vect3.size()!=0)
								{
									int siz = vect3.get(1).size();
									int combo=0;
									for(int z = 0 ; z<siz-1; z++)
									{
										clear();
										stone[vect3.get(1).get(z)].setIcon(null_image);
										stone[vect3.get(1).get(z+1)].setIcon(player2_image);
										Sounds.Sound();
										
										
										combo++;
										if(combo>player2_Combo)
											player2_Combo=combo;
										
								try {
											sleep(1200);
									} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								
									NextTurn();
									clear();
									break;
								}
								/////////////////////////////////////////////////////////////////////////////////////////////////////////////
								
							
								while(true)//선택한 돌을 어디로 옮길수있는지 알아보고 괜찮으면 옮기는 부분( 한칸또는 점프 또는 이단점프부분) 앞 최대점프 구하는거에서 실패했을
								{
									
									clear();
									
									int j = (int)(37*Math.random());
									if(player1_status==0 || player2_status==0 || player_turn==1)
									{
										this.stop();
									}
									Scan(stone[i]);
									
									if(stone[j].getIcon()==player1_null)
									{
										
										func_variable(i, j);
										
										percent=100; //확률
										
										if(sum!=124 || sum!=166) // 점프하는 것이 아니라면 확률 낮춘다.
											percent= percent-10;
										
										if(player2_Location=="TOP")          // 목적지의 큰방향성과 맞지않는다면 확률 낮춘다.
										{
											if(stone[j].getX()<=90 || stone[j].getX()>=338	|| (stone[j].getX()==28 && stone[j].getY()==113)	//왼쪽위
													|| (stone[j].getX()==400 && stone[j].getY()==321)//오른쪽밑
													|| (stone[j].getX()==28 && stone[j].getY()==321)//왼쪽밑
													|| (stone[j].getX()==400 && stone[j].getY()==113)//오른쪽위
													)// 가는 길목에서 멀리 벗어나는것(width)을 잡아줌.
											{
												percent=100;
												clear();
												break;
											}
											if(stone[j].getY()-stone[i].getY()<0)//뒤로가는 확률 낮춤
											{
													percent= percent-20;
											}
											else if(stone[j].getY()-stone[i].getY()==0)//그줄에 있는 확률 낮춤.
											{
												percent= percent-27;
											}
											if(ylength>0)
											percent=percent-30;
										}
										else if(player2_Location=="LEFT")
										{
											if(stone[j].getY()==61 || stone[j].getY()==373  || (stone[j].getX()==369 && stone[j].getY()==269) || (stone[j].getX()==338 && stone[j].getY()==321)	|| (stone[j].getX()==214 && stone[j].getY()==425)//밑
													|| (stone[j].getX()==28 && stone[j].getY()==113)	//왼쪽위
													|| (stone[j].getX()==214 && stone[j].getY()==9)//위
													|| (stone[j].getX()==400 && stone[j].getY()==321))//오른쪽밑
												// 가는 길목에서 멀리 벗어나는것(width)을 잡아줌.
											{
												percent=100;
												clear();
												break;
											}
											if(stone[j].getX()-stone[i].getX()<0)//뒤로가는 확률 낮춤
											{	
													percent=percent-20;
											}
											else if(stone[j].getX()-stone[i].getX()==0)//그줄에 있는 확률 낮춤.
											{
												percent=percent-27;
											}
											if(xlength>0)
											percent=percent-30;
										}
										else if(player2_Location=="RIGHT")
										{
											if(stone[j].getY()==61 || stone[j].getY()==373  ||  (stone[j].getX()==59 && stone[j].getY()==269) || (stone[j].getX()==90 && stone[j].getY()==321)||
													(stone[j].getX()==214 && stone[j].getY()==425)//밑
													|| (stone[j].getX()==28 && stone[j].getY()==321)//왼쪽밑
													|| (stone[j].getX()==400 && stone[j].getY()==113)//오른쪽위
													|| (stone[j].getX()==214 && stone[j].getY()==9))//위)// 가는 길목에서 멀리 벗어나는것(width)을 잡아줌.
											{
												percent=100;
												clear();
												break;
											}
											if(stone[j].getX()-stone[i].getX()>0)//뒤로가는 확률 낮춤
											{
													percent= percent-20;
											}
											else if(stone[j].getX()-stone[i].getX()==0)//그줄에 있는 확률 낮춤.
											{
												percent=percent-27;
											}
											if(xlength<0)
											percent=percent-30;
										}
										
										if(Destination_sumlength_j>Destination_sumlength_i) // 목적지와 거리가 더 멀면 이동할 확률 낮춘다.
										{
											percent=percent-30;
										}
										
										
										if(Destination_sumlength_i<=84 || Destination_sumlength_i==145 || Destination_sumlength_i==104)  // stone[i]-현재 스톤   과 목적지의 거리가 일정수준 이하이면 움직이지않음
										{
											percent=100;
											clear();
											
											break;
										}
										
										
										
										if(percent<40+(int)(Math.random()*60)) // 확률 적절히 설정
										{
											percent=100;
											clear();
											
											break;
										}
										//////////////////////////////////////////////////////////////////////////////////////////i , j 
										
										/////////////////////////////////이단점프
										
										if(sum==124 || sum==166) // 점프일경우 점프 한번더하는거 결정하는부분 (더블점프 구현)  넣어야댈거: 함수로 구현해보기, 특정범위 안벗어나게, 더블점프 한 후를 분석해서 좋은쪽이면 이동하게끔
										{
											//Gamestatus[1].setText(Integer.toString(sum));
											stone[i].setIcon(null_image);
											stone[j].setIcon(player2_image);
											Sounds.Sound();
											if(1>player2_Combo)
												player2_Combo=1;
											
											clear();
											try {
												sleep(300);
											} catch (InterruptedException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											
											Scan(stone[j]);
											int vv=0;
											while(true)
											{
												int t = (int)(37*Math.random());
												if(i==t)//이동했던 경로로 다시 못가게 하기위함.
												{
													continue;
												}
											
												if(stone[t].getIcon()==player1_null)
												{
													xlength= stone[j].getX() - stone[t].getX();
													ylength= stone[j].getY() - stone[t].getY();
													xlen=0; ylen=0;
													if(xlength<0)
														xlen=-xlength;
													else
														xlen= xlength;
													if(ylength<0)
														ylen=-ylength;
													else 
														ylen = ylength;
													
													sum=xlen+ylen;
													if(sum==124 || sum==166)
													{
														if(player2_Location=="TOP" && (stone[t].getY()-stone[j].getY()>0))          // 목적지의 큰방향성과 맞지않는다면 확률 낮춘다.
														{
															if(stone[t].getX()<=90 || stone[t].getX()>=338  
																	|| (stone[t].getX()==28 && stone[t].getY()==113)	//왼쪽위
																	|| (stone[t].getX()==400 && stone[t].getY()==321)//오른쪽밑
																	|| (stone[t].getX()==28 && stone[t].getY()==321)//왼쪽밑
																	|| (stone[t].getX()==400 && stone[t].getY()==113)//오른쪽위
																	)// 가는 길목에서 멀리 벗어나는것(width)을 잡아줌.
															{
																percent=100;
																clear();
																break;
															}
															stone[j].setIcon(null_image);
															stone[t].setIcon(player2_image);
															Sounds.Sound();
															
															if(2>player2_Combo)
																player2_Combo=2;
															clear();
															tem=10;
															NextTurn();
															break;
														}
														else if(player2_Location=="LEFT" && (stone[t].getX()-stone[j].getX()>0))
														{
															if(stone[t].getY()==61 || stone[t].getY()==373  || (stone[t].getX()==369 && stone[t].getY()==269) 
																	|| (stone[t].getX()==338 && stone[t].getY()==321)|| xlen==31 || xlen==62 
																	|| (stone[t].getX()==214 && stone[t].getY()==425)//밑
																	|| (stone[t].getX()==28 && stone[t].getY()==113)	//왼쪽위
																	|| (stone[t].getX()==214 && stone[t].getY()==9)//위
																	|| (stone[t].getX()==400 && stone[t].getY()==321))//오른쪽밑
																// 가는 길목에서 멀리 벗어나는것(width)을 잡아줌.
															{
																percent=100;
																clear();
																break;
															}
															stone[j].setIcon(null_image);
															stone[t].setIcon(player2_image);
															Sounds.Sound();
															
															if(2>player2_Combo)
																player2_Combo=2;
															clear();
															tem=10;
															NextTurn();
															break;
														}
														else if(player2_Location=="RIGHT" && stone[t].getX()-stone[j].getX()<0)
														{
															if(stone[t].getY()==61 || stone[t].getY()==373  ||  (stone[t].getX()==59 && stone[t].getY()==269) 
																	|| (stone[t].getX()==90 && stone[t].getY()==321)|| xlen==31 || xlen==62
																	|| (stone[t].getX()==214 && stone[t].getY()==425)//밑
																	|| (stone[t].getX()==28 && stone[t].getY()==321)//왼쪽밑
																	|| (stone[t].getX()==400 && stone[t].getY()==113)//오른쪽위
																	|| (stone[t].getX()==214 && stone[t].getY()==9))//위
																
																// 가는 길목에서 멀리 벗어나는것(width)을 잡아줌.
															{
																percent=100;
																clear();
																break;
															}
															stone[j].setIcon(null_image);
															stone[t].setIcon(player2_image);
															Sounds.Sound();
															
															if(2>player2_Combo)
																player2_Combo=2;
															clear();
															tem=10;
															NextTurn();
															break;
														}
														
													}
													vv++;
													if(vv==1000)
													{
														tem=10;
														clear();
														NextTurn();
														break;
													}
												}
											}
											if(tem==10)
											{
												break;
											}
										}
										/////////////////////////////////////////////////////////////////////////////////////////////////////이단점프
										//Gamestatus[0].setText("3");
										stone[i].setIcon(null_image);
										stone[j].setIcon(player2_image);
										Sounds.Sound();
										clear();
										NextTurn();
										tem = 10;
									
										break;
									}
									else
									{
										clear();
										break;
									}
								}
								if(tem==10)
								{
									tem=0;
									break;
								}
							}
						}
					}
					else
					{
						try {
							sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		ImageIcon player1_image;
		ImageIcon player2_image;

		Color player1_Color;
		Color player2_Color;

		String player1_Location;
		String player2_Location;

		void mapping() { //스톤에 이미지넣기
			for(int i = 0 ; i<37; i++)
			{
				stone[i] = new JLabel(null_image);
			}
			
			double temp = Math.random()*6;  //player마다 색깔 랜덤부여
			
			if(temp<3)
			{	player1_image=Black_stone; player2_image=Blue_stone; 
				player1_Color=Color.BLACK; player2_Color=Color.BLUE;
			}

			else if(temp<6)
			{	player1_image=Blue_stone; player2_image=Black_stone; 
				player1_Color=Color.BLUE; player2_Color=Color.BLACK; 
			}

			temp = Math.random()*6;

			if(temp<1) // 위치 랜덤배정
			{
				player1_Location="TOP";				player2_Location="LEFT";		
				stone[0] = new JLabel(player1_image); 				stone[1] = new JLabel(player1_image);				stone[2] = new JLabel(player1_image);
				stone[5] = new JLabel(player1_image);				stone[6] = new JLabel(player1_image);				stone[7] = new JLabel(player1_image);
				
				stone[16] = new JLabel(player2_image);				stone[21] = new JLabel(player2_image);				stone[22] = new JLabel(player2_image);
				stone[27] = new JLabel(player2_image);				stone[28] = new JLabel(player2_image);				stone[29] = new JLabel(player2_image);
				
			}else if(temp<2) // 위치 랜덤배정
			{
				player1_Location="TOP";				player2_Location="RIGHT";		
				stone[0] = new JLabel(player1_image); 				stone[1] = new JLabel(player1_image);				stone[2] = new JLabel(player1_image);
				stone[5] = new JLabel(player1_image);				stone[6] = new JLabel(player1_image);				stone[7] = new JLabel(player1_image);
				
				stone[20] = new JLabel(player2_image);				stone[25] = new JLabel(player2_image);				stone[26] = new JLabel(player2_image);
				stone[31] = new JLabel(player2_image);				stone[32] = new JLabel(player2_image);				stone[33] = new JLabel(player2_image);
				
			}

			else if(temp<3)
			{
				player1_Location="LEFT";				player2_Location="TOP";	
				stone[0] = new JLabel(player2_image); 				stone[1] = new JLabel(player2_image);				stone[2] = new JLabel(player2_image);
				stone[5] = new JLabel(player2_image);				stone[6] = new JLabel(player2_image);				stone[7] = new JLabel(player2_image);
				
				stone[16] = new JLabel(player1_image);				stone[21] = new JLabel(player1_image);				stone[22] = new JLabel(player1_image);
				stone[27] = new JLabel(player1_image);				stone[28] = new JLabel(player1_image);				stone[29] = new JLabel(player1_image);

			}	else if(temp<4)
			{
				player1_Location="LEFT";				player2_Location="RIGHT";	
				stone[20] = new JLabel(player2_image);				stone[25] = new JLabel(player2_image);				stone[26] = new JLabel(player2_image);
				stone[31] = new JLabel(player2_image);				stone[32] = new JLabel(player2_image);				stone[33] = new JLabel(player2_image);
				
				stone[16] = new JLabel(player1_image);				stone[21] = new JLabel(player1_image);				stone[22] = new JLabel(player1_image);
				stone[27] = new JLabel(player1_image);				stone[28] = new JLabel(player1_image);				stone[29] = new JLabel(player1_image);

			}else if(temp<5) // 위치 랜덤배정
			{
				player1_Location="RIGHT";				player2_Location="LEFT";		
				stone[20] = new JLabel(player1_image);				stone[25] = new JLabel(player1_image);				stone[26] = new JLabel(player1_image);
				stone[31] = new JLabel(player1_image);				stone[32] = new JLabel(player1_image);				stone[33] = new JLabel(player1_image);
				
				stone[16] = new JLabel(player2_image);				stone[21] = new JLabel(player2_image);				stone[22] = new JLabel(player2_image);
				stone[27] = new JLabel(player2_image);				stone[28] = new JLabel(player2_image);				stone[29] = new JLabel(player2_image);
				
			}else if(temp<6) // 위치 랜덤배정
			{
				player1_Location="RIGHT";				player2_Location="TOP";		
				stone[0] = new JLabel(player2_image); 				stone[1] = new JLabel(player2_image);				stone[2] = new JLabel(player2_image);
				stone[5] = new JLabel(player2_image);				stone[6] = new JLabel(player2_image);				stone[7] = new JLabel(player2_image);
				
				stone[20] = new JLabel(player1_image);				stone[25] = new JLabel(player1_image);				stone[26] = new JLabel(player1_image);
				stone[31] = new JLabel(player1_image);				stone[32] = new JLabel(player1_image);				stone[33] = new JLabel(player1_image);
				
			}

			
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
								Sounds.Sound();
								clear();
								NextTurn();
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
								Sounds.Sound();
								clear();
								NextTurn();
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
				if(stone[i].getIcon()!=player1_image && stone[i].getIcon()!=player2_image)
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
			if(player1_Location=="TOP" && player2_Location=="LEFT" )
			{
				ply1_arr=Top_num; ply2_arr=LEFT_num; ply3_arr=RIGHT_num;
			}else if(player1_Location=="TOP" && player2_Location=="RIGHT")
			{
				ply1_arr=Top_num; ply2_arr=RIGHT_num; ply3_arr=LEFT_num;
			}else if(player1_Location=="LEFT"  && player2_Location=="TOP")
			{
				ply1_arr=LEFT_num; ply2_arr=Top_num; ply3_arr=RIGHT_num;
			}else if(player1_Location=="LEFT" &&  player2_Location=="RIGHT")
			{
				ply1_arr=LEFT_num; ply2_arr=RIGHT_num; ply3_arr=Top_num;
			}else if(player1_Location=="RIGHT"  &&  player2_Location=="TOP"  )
			{
				ply1_arr=RIGHT_num; ply2_arr=Top_num; ply3_arr=LEFT_num;
			}else if(player1_Location=="RIGHT"  &&  player2_Location=="LEFT" )
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
			

		}
		
		void GameStatus() { // 이름, 점수 , 콤보 내림차순으로 출력
			GameScore();
			if(player1_Score==6 && player1_status==1)
			{
				if(GameStatus_i==0)
				{
					Gamestatus[GameStatus_i].setText(setup.NickName+" "+player1_Score+" comb: "+player1_Combo);
					Gamestatus[GameStatus_i+1].setText("AI "+player2_Score+" comb: "+player2_Combo);
					GameStatus_i++;
				}
				else if(GameStatus_i==1)
				{
					Gamestatus[1].setText(setup.NickName+" "+player1_Score+" comb: "+player1_Combo);
				}
				
				player1_status=0;
			}
			else if(player2_Score==6 && player2_status==1)
			{
				if(GameStatus_i==0)
				{
					Gamestatus[GameStatus_i].setText("AI "+player2_Score+" comb: "+player2_Combo);
					Gamestatus[GameStatus_i+1].setText(setup.NickName+" "+player1_Score+" comb: "+player1_Combo);

					GameStatus_i++;
				}
				else if(GameStatus_i==1)
				{
					Gamestatus[1].setText("AI "+player2_Score+" comb: "+player2_Combo);
					
				}

				player2_status=0;				
				
			}
			
		}
		
		void NextTurn() { // 다음턴 올사람 구하는함수
			Combo_temp=0;
			if(player_turn==1 && player2_status==1) // 플레이어1이 턴일때
			{
				player_turn++;
				d = new AI_Control();
				d.start();
				TurnStatus.setText("AI Turn");
				TurnStatus.setForeground(player2_Color);
				GameStatus();
				th.n=setup.Time-2;
				
			}
			else if(player_turn==1 && player2_status==0)
			{
				GameStatus();
				th.n=setup.Time-2;
			}
			
			else if(player_turn==2 && player1_status==1) // 플레이어 2가 턴일때
			{
				player_turn=1;
				TurnStatus.setText(setup.NickName+" Turn");
				TurnStatus.setForeground(player1_Color);
				GameStatus();
				th.n=setup.Time-2;
			}
			else if(player_turn==2 && player1_status==0)
			{
				GameStatus();
				th.n=setup.Time-2;
			}
		}
		AI_Control d = new AI_Control();
		public GamePanel(){
			
			
			GameLayout = new SpringLayout();
	        setLayout(GameLayout);
	        mapping();
			stonedispose();
			
				
			
			
			TurnStatus.setFont(new Font("Tahoma", Font.BOLD, 30));
			TurnStatus.setText(setup.NickName+" Turn");
			TurnStatus.setForeground(player1_Color);
			timerLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
			
			Abstain= new JLabel(abstain);
			GameOut= new JLabel(gameout);
			th.start();
			
			
			GameLayout.putConstraint(SpringLayout.NORTH, timerLabel, 0, SpringLayout.WEST, this);
			GameLayout.putConstraint(SpringLayout.EAST, timerLabel, 0, SpringLayout.EAST, this);
			add(timerLabel);
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
			
			GameLayout.putConstraint(SpringLayout.NORTH, Abstain, 380, SpringLayout.SOUTH, TurnStatus);
			GameLayout.putConstraint(SpringLayout.WEST, Abstain, 0, SpringLayout.WEST, TurnStatus);
			
			GameLayout.putConstraint(SpringLayout.NORTH, GameOut, 380, SpringLayout.SOUTH, TurnStatus);
			GameLayout.putConstraint(SpringLayout.WEST, GameOut, 0, SpringLayout.WEST, TurnStatus);
			
			for(int i = 0; i<37; i++)
			{
				add(stone[i]);
			}			
			
			mymouselistener my = new mymouselistener();
			
			for(int i = 0; i<37; i++)
			{
				stone[i].addMouseListener(my);
			}
			
			
			add(Abstain);
			
			add(GameOut);
			GameOut.setVisible(false);
			
			Abstain.addMouseListener(new MouseAdapter() { // 기권하기 눌렀을때
				public void mousePressed(MouseEvent e) {
					d.stop();
					th.stop();
					GameScore();

							
							Gamestatus[0].setText("AI "+player2_Score+" comb: "+player2_Combo);
							Gamestatus[1].setText(setup.NickName+" "+player1_Score+" comb: "+player1_Combo);
				
                		for(int i = 0; i<37; i++) // 버튼 옮겨지는거 정보 제공하는부분
    					{
    						if(stone[i].getIcon()==player1_image) //옮겨지기전 버튼위치
    						{
    							stone[i].setIcon(null_image);
    						}
    					}
                		player1_status=0;
                		if(player_turn==1)
                			NextTurn();
                		
            			Abstain.setVisible(false);
            			GameOut.setVisible(true);
					}
			});
			GameOut.addMouseListener(new MouseAdapter() { // 나가기 눌렀을때
				public void mousePressed(MouseEvent e) {
					
					System.exit(0);
					}
			});

		}
		
		int jumpafter=0;
		
		class mymouselistener implements MouseListener{ // stone 마우스리스너
			JLabel released_button = new JLabel();//버튼 눌럿다가 때어질때
			JLabel now_button = new JLabel(); //현재 올라가있는버튼
			JLabel pressed_button = new JLabel();
			JLabel exited_button = new JLabel(); //스톤위에서 내려올때 (널 포인터 이미지 원래대로)
			JLabel clicked_button = new JLabel(); //그냥 클릭했다가 땟을때
			
			JLabel temp = new JLabel(); 
			
			
			public void mousePressed(MouseEvent e) { //마우스가 스톤위에서 눌러졌을때
				pressed_button = (JLabel)e.getSource();
				if(pressed_button.getIcon()!=null_image && pressed_button.getIcon()!=player2_image && !(player1_status==0 || player2_status==0) )
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
					
					
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				clicked_button = (JLabel)e.getSource();
				
				
				if(player_turn == 1 && player1_image == clicked_button.getIcon() && clicked_button.getIcon()==now_button.getIcon())
				{
					
					NextTurn();
					
				}

				
				//Gamestatus[0].setText(clicked_button.getLocation().toString());
				jumpafter=0;
			}

			@Override
			public void mouseEntered(MouseEvent e) { // 마우스가 스톤위에 올라갔을때
				now_button = (JLabel)e.getSource();
				if(now_button.getIcon()==player1_null && player_turn==1)
				{
					now_button.setIcon(player1_null_p);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) { // 마우스가 스톤위에서 벗어낫을때.
				exited_button = (JLabel)e.getSource();
				
				if(exited_button.getIcon()==player1_null_p)
				{
					exited_button.setIcon(player1_null);
				}
				
			}

			@Override
			public void mouseReleased(MouseEvent e) { // 마우스가 때어질때
				released_button = (JLabel)e.getSource();
				
				if(now_button.getIcon()==player1_null_p)
				{
					Sounds.Sound();
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
							NextTurn();
						}
						
						
						if(Combo_temp>player1_Combo && now_button.getIcon()==player1_image) //전 콤보보다 콤보횟수가 많을시 
						{
							player1_Combo = Combo_temp; // 현재콤보횟수를 넣어줌
						}else if(Combo_temp>player2_Combo && now_button.getIcon()==player2_image) //전 콤보보다 콤보횟수가 많을시 
						{
							player2_Combo = Combo_temp; // 현재콤보횟수를 넣어줌
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
