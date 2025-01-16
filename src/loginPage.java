import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class loginPage {
	private JFrame frame;
	public static int currentUserID;
	private Connection connection;
	private Font font1 = new Font("Arial", Font.BOLD, 15);
	private Font font2 = new Font("Arial", Font.BOLD, 19);
	private Font font3 = new Font("Arial", Font.BOLD, 13);
	public loginPage() {
		frame = new JFrame("Đăng nhập");
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(500, 400);
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);
		
		try {
			connection = DriverManager.getConnection(
				    "jdbc:sqlserver://localhost:1433;databaseName=rentalManagement;encrypt=true;trustServerCertificate=true", 
				    "sa", 
				    "haonguyen123"
				);
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(frame, "Lỗi kết nối cơ sở dữ liệu", "Thông báo", JOptionPane.ERROR_MESSAGE);
		}
		
		ImageIcon icon = new ImageIcon("Hjava1.jpg");
		JLabel label = new JLabel(icon);
		label.setBounds(220, 20, 50, 50);
		frame.add(label);
		
		JLabel label1 = new JLabel("Quản lý nhà cho thuê");
		label1.setFont(font2);
		label1.setBounds(145, 60, 212, 50);
		frame.add(label1);
		
		JLabel label2 = new JLabel("Đăng nhập tài khoản");
		label2.setFont(font1);
		label2.setBounds(173, 100, 190, 50);
		frame.add(label2);
		
		JLabel phoneNumJLabel = new JLabel("Số điện thoại*");
		phoneNumJLabel.setBounds(60, 135, 100, 50);
		phoneNumJLabel.setFont(font3);
		JTextField phoneNumField = new JTextField();
		phoneNumField.setBounds(160, 140, 180, 40);
		frame.add(phoneNumField);
		frame.add(phoneNumJLabel);
		
		JLabel passJLabel = new JLabel("Nhập mật khẩu*");
		passJLabel.setBounds(60, 185, 100, 50);
		passJLabel.setFont(font3);
		JPasswordField passField = new JPasswordField();
		passField.setBounds(160, 190, 180, 40);
		frame.add(passJLabel);
		frame.add(passField);
		
		JButton loginButton = new colorGUI("Đăng nhập", null, new Color(124,252,0), new Color(34,139,34));
		loginButton.setBounds(180, 270, 140, 30);
		JButton registerButton = new colorGUI("Tạo tài khoản", null, new Color(124,252,0), new Color(34,139,34));
		registerButton.setBounds(180, 305, 140, 30);
		
		frame.add(loginButton);
		frame.add(registerButton);
		
		frame.setVisible(true);
		
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String phoneNum = phoneNumField.getText().trim();
				String password = new String(passField.getPassword());
				if (phoneNum.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Vui lòng điền đầy đủ thông tin", "Thông báo", JOptionPane.WARNING_MESSAGE);
					return;
				}
				try {
					String sql = "SELECT * FROM Users WHERE phoneNum = ? AND password = ?";
					PreparedStatement statement = connection.prepareStatement(sql);
					statement.setString(1, phoneNum);
					statement.setString(2, password);
					ResultSet resultSet = statement.executeQuery();
					if (resultSet.next()) {
						currentUserID = resultSet.getInt("UserID");
						JOptionPane.showMessageDialog(frame, "Đăng nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
						frame.dispose();
						new managementPage();
					}
					else {
						JOptionPane.showMessageDialog(frame, "Tên người dùng hoặc mật khẩu không đúng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, "Lỗi kết nối cơ sở dữ liệu", "Thông báo", JOptionPane.ERROR_MESSAGE);
				}
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
