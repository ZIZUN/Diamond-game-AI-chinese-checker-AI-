import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class setup extends JFrame{
	public static int Time=20;
	public static String NickName="Anonymous";
	public static String NickName2="Player2";
	public static String NickName3="Player3";
	public static int Volume=2;
	public setup() 
	{ 
		setTitle("환경 설정");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		TextArea ta = new TextArea(); 
		 
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		c.add(new Panel());
		
		setSize(330,205);
		setLocation(580,255);
		setVisible(true);
	}
	class Panel extends JPanel
	{
		
		JLabel nickname_Label = new JLabel("player1(MY)");
		JTextField nickname_Text = new JTextField(20);
		
		JLabel nickname2_Label = new JLabel("   player2     ");
		JTextField nickname2_Text = new JTextField(20);
		
		JLabel nickname3_Label = new JLabel("   player3     ");
		JTextField nickname3_Text = new JTextField(20);
		
		JLabel Time_Label = new JLabel("타이머설정");
		JTextField Time_Text = new JTextField(20);
		
		JLabel Sound_Label = new JLabel("소리설정 (-40 ~ 6)  ");
		JTextField Sound_Text = new JTextField(16);
		
		
		JButton Set_Button = new JButton("설정");

		public Panel()
		{
			Time_Text.setText(Integer.toString((Time-2)));		
			nickname_Text.setText(NickName);
			nickname2_Text.setText(NickName2);
			nickname3_Text.setText(NickName3);
			Sound_Text.setText(Integer.toString(Volume));
			add(nickname_Label);
			add(nickname_Text);
			
			add(nickname2_Label);
			add(nickname2_Text);
			
			add(nickname3_Label);
			add(nickname3_Text);
			
			add(Time_Label);
			add(Time_Text);
			
			add(Sound_Label);
			add(Sound_Text);
			
			add(Set_Button);
			setResizable(false);
	
          
			Set_Button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					setup.Time = Integer.parseInt(Time_Text.getText())+2;		
					setup.NickName = nickname_Text.getText();	
					setup.NickName2 = nickname2_Text.getText();
					setup.NickName3 = nickname3_Text.getText();
					setup.Volume = Integer.parseInt(Sound_Text.getText());
					dispose();
				}
			});
		}
	}
}