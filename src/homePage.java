import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class homePage {
	private JFrame frame;
	private Font font1 = new Font("Arial", Font.BOLD, 15);
	private Font font2 = new Font("Arial", Font.BOLD, 19);
	private Font font3 = new Font("Arial", Font.BOLD, 13);
	public homePage() {
		frame = new JFrame("Trang chủ");
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(600, 500);
		frame.setLayout(new BorderLayout());
		frame.setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		ImageIcon icon1 = new ImageIcon("Hjava.jpg");
		JLabel label = new JLabel("Quản lý nhà cho thuê", icon1, JLabel.CENTER);
		label.setForeground(Color.GREEN);
		label.setFont(new Font("a", Font.BOLD, 18));
		label.setHorizontalTextPosition(JLabel.RIGHT);
		
		panel.add(label, BorderLayout.WEST);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		JButton loginButton = new colorGUI("Đăng nhập", null, new Color(124,252,0), new Color(34,139,34));
		loginButton.setFont(font1);
		buttonPanel.add(loginButton);
		JButton registerButton = new colorGUI("Đăng ký", null, new Color(124,252,0), new Color(34,139,34));
		registerButton.setFont(font1);
		buttonPanel.add(registerButton);
		
		panel.add(buttonPanel, BorderLayout.EAST);
		
		ImageIcon icon2 = new ImageIcon("Doan.png");
		JLabel label2 = new JLabel(icon2);
		
		frame.add(panel, BorderLayout.NORTH);
		frame.add(label2, BorderLayout.CENTER);
		frame.setVisible(true);
		
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				 new loginPage();
			}
		});
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new registerPage();
			}
		});
	}
}
