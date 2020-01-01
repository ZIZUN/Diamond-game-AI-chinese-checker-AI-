import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class selec_2_3 extends JFrame{
	public selec_2_3() {
		setTitle("2/3인 게임");
				
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		setResizable(false);
		
		JButton game2 = new JButton("2인 게임");
		JButton game3 = new JButton("3인 게임");
		c.add(game2);
		c.add(game3);
		
		game2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				JButton a = (JButton)e.getSource();
				new player2_game();
				dispose();
			}
		});
		game3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				JButton a = (JButton)e.getSource();
				new player3_game();
				
				dispose();
			}
		});
		
		setSize(300,80);
		setLocation(680,270);
		setVisible(true);
	}
	
}
