import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class registerPage {
	private JFrame frame;;
	private Connection connection;
	private Font font1 = new Font("Arial", Font.BOLD, 15);
	private Font font2 = new Font("Arial", Font.BOLD, 19);
	private Font font3 = new Font("Arial", Font.BOLD, 13);
	public registerPage() {
		frame = new JFrame("Đăng ký");
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(600, 500);
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
		label.setBounds(269, 20, 50, 50);
		frame.add(label);
		
		JLabel label1 = new JLabel("Quản lý nhà cho thuê");
		label1.setFont(font2);
		label1.setBounds(195, 60, 212, 50);
		frame.add(label1);
		
		JLabel label2 = new JLabel("Tên người dùng*");
		label2.setBounds(100, 120, 212, 50);
		label2.setFont(font3);
		JTextField userNameField = new JTextField();
		userNameField.setBounds(100, 160, 190, 40);
		frame.add(label2);
		frame.add(userNameField);
		
		JLabel label3 = new JLabel("Số điện thoại*");
		label3.setBounds(300, 120, 212, 50);
		label3.setFont(font3);
		JTextField phoneNumField = new JTextField();
		phoneNumField.setBounds(300, 160, 190, 40);
		frame.add(label3);
		frame.add(phoneNumField);
		
		JLabel label4 = new JLabel("Mật khẩu*");
		label4.setBounds(100, 190, 212, 50);
		label4.setFont(font3);
		JPasswordField passField = new JPasswordField();
		passField.setBounds(100, 230, 190, 40);
		frame.add(label4);
		frame.add(passField);
		
		JLabel label5 = new JLabel("Nhập lại mật khẩu*");
		label5.setBounds(300, 190, 212, 50);
		label5.setFont(font3);
		JPasswordField rePassField = new JPasswordField();
		rePassField.setBounds(300, 230, 190, 40);
		frame.add(label5);
		frame.add(rePassField);
		
		JButton registerButton = new colorGUI("Đăng ký", null, new Color(124,252,0), new Color(34,139,34));
		registerButton.setBounds(120, 310, 350, 40);
		registerButton.setFont(font3);
		frame.add(registerButton);
		
		JButton loginButton = new colorGUI("Đã có tài khoản? Đăng nhập", null, new Color(124,252,0), new Color(34,139,34));
		loginButton.setBounds(120, 360, 350, 40);
		loginButton.setFont(font3);
		loginButton.setBackground(Color.green);
		frame.add(loginButton);
		
		frame.setVisible(true);
		
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String userName = userNameField.getText().trim();
				String phoneNum = phoneNumField.getText().trim();
				String password = new String(passField.getPassword());
				String rePassword = new String(rePassField.getPassword());
				
				if (userName.isEmpty() || phoneNum.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Vui lòng điền đầy đủ thông tin", "Thông báo", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (!password.equals(rePassword)) {
					JOptionPane.showMessageDialog(frame, "Mật khẩu không khớp", "Thông báo", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (!phoneNum.matches("\\d{10}")) {
					JOptionPane.showMessageDialog(frame, "Số điện thoại không hợp lệ", "Thông báo", JOptionPane.WARNING_MESSAGE);
					return;
				}
				try {
					String sql = "INSERT INTO Users (UserName, PhoneNum, Password) VALUES (?, ?, ?)";
					PreparedStatement statement = connection.prepareStatement(sql);
					statement.setString(1, userName);
					statement.setString(2, phoneNum);
					statement.setString(3, password);
					
					int rowsInserted = statement.executeUpdate();
					if (rowsInserted > 0) {
						JOptionPane.showMessageDialog(frame, "Đăng ký thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
						frame.dispose();
						new homePage();
					}
					else {
						JOptionPane.showMessageDialog(frame, "Lỗi, vui lòng thử lại", "Thông báo", JOptionPane.WARNING_MESSAGE);
					}
				} catch (SQLException ex){
					ex.printStackTrace();
					JOptionPane.showMessageDialog(frame, "Lỗi kết nối cơ sở dữ liệu", "Thông báo", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new loginPage();
			}
		});
	}
}
