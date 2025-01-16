import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.Statement;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.category.DefaultCategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class managementPage {
	private JFrame frame;
	private JTable table;
	private Dialog dialog;
	JComboBox<String> tenantsBox, floorBox, tenantsField, waterBill, elecBill, garbageBill, wifiBill, status;
	private Connection connection;
	private JTextField searchField, priceField;
	JCheckBox statusFilter1, statusFilter2;
	private DefaultTableModel model;
	private Font font1 = new Font("Arial", Font.BOLD, 13);
	private Font font2 = new Font("Arial", Font.BOLD, 19);
	private Color color1 = new Color(20,220,107), color2 = new Color(60,169,113),color3 = new Color(0, 128, 0), colorBackGround = new Color(175, 238, 238);
	public managementPage() {
		frame = new JFrame("Quản lý nhà");
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(800, 700);
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);
		
		try {
			connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=rentalManagement;encrypt=true;trustServercertificate=true",
			"sa",
			"haonguyen123"
			);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "Lỗi kết nối cơ sở dữ liệu", "Thông báo", JOptionPane.ERROR_MESSAGE);
		}
		
		JPanel panelBackGround = new JPanel();
		panelBackGround.setBackground(color2);
		
		panelBackGround.setLayout(null);
		panelBackGround.setBounds(0, 0, 800, 90);
		frame.add(panelBackGround, BorderLayout.NORTH);
		
		JPanel panelWest = new JPanel();
		ImageIcon icon = new ImageIcon("Hjava.jpg");
		JLabel labeWest = new JLabel("Quản lý nhà cho thuê", icon, JLabel.CENTER);
		labeWest.setHorizontalAlignment(JLabel.RIGHT);
		labeWest.setFont(font1);
		labeWest.setForeground(Color.white);
		panelWest.add(labeWest);
		panelWest.setBounds(10, 14, 190, 60);
		panelWest.setBackground(color3);
		panelBackGround.add(panelWest);
		
		
		JButton logoutButton = new JButton("Đăng xuất", new ImageIcon("out.jfif"));
		logoutButton.setFont(font1);
		logoutButton.setForeground(Color.white);
		logoutButton.setBackground(color1);
		logoutButton.setBounds(650, 14, 130, 60);
		panelBackGround.add(logoutButton);
		
		JButton accountButton = new JButton("Tài khoản", new ImageIcon("account.png"));
		accountButton.setFont(font1);
		accountButton.setBackground(color1);
		accountButton.setForeground(Color.white);
		accountButton.setBounds(520, 14, 128, 60);
		panelBackGround.add(accountButton);
		
		JButton managerButton = new JButton("Quản lý nhà", new ImageIcon("nha.jfif"));
		managerButton.setFont(font1);
		managerButton.setBackground(color3);
		managerButton.setForeground(Color.white);
		managerButton.setBounds(377, 14, 142, 60);
		panelBackGround.add(managerButton);
		
		accountButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				accountButton.setBackground(color3);
				managerButton.setBackground(color1);
				logoutButton.setBackground(color1);
			}
		});
		logoutButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				logoutButton.setBackground(color3);
				managerButton.setBackground(color1);
				accountButton.setBackground(color1);
			}
		});
		managerButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				managerButton.setBackground(color3);
				accountButton.setBackground(color1);
				logoutButton.setBackground(color1);
			}
		});
		logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new homePage();
			}
		});
		accountButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog accountDialog = new JDialog(frame ,"Thông tin tài khoản", true);
				accountDialog.setSize(500, 400);
				accountDialog.setLayout(null);
				accountDialog.setLocationRelativeTo(null);
				
				JPanel panelMain = new JPanel();
				panelMain.setLayout(null);
				panelMain.setBounds(0, 0, 480, 380);
				
				JLabel iconLabel = new JLabel(new ImageIcon("avatar.png"));
				iconLabel.setBounds(210, 50, 80, 80);
				panelMain.add(iconLabel);
				
				try {
					String sqlAccount = "SELECT * FROM Users WHERE UserID = ?";
					PreparedStatement statement = connection.prepareStatement(sqlAccount);
					int userID = loginPage.currentUserID;
					statement.setInt(1, userID);
					ResultSet resultSet = statement.executeQuery();
					
					if (resultSet.next()) {
						JLabel nameLabel = new JLabel("Tên người dùng: " + resultSet.getString("UserName"));
						nameLabel.setFont(font1);
						nameLabel.setBounds(180, 150, 200, 30);
						panelMain.add(nameLabel);
						
						JLabel phoneLabel = new JLabel("Số điện thoại: " + resultSet.getString("phoneNum"));
						phoneLabel.setFont(font1);
						phoneLabel.setBounds(180, 190, 200, 30);
						panelMain.add(phoneLabel);
					} 
					
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(accountDialog, "Lỗi kết nối cơ sở dữ liệu!", "Thông báo", JOptionPane.ERROR_MESSAGE);
				}
				
				accountDialog.add(panelMain);
				
				accountDialog.setVisible(true);
				try {
					String sql = "SELECT * FROM Users";
					java.sql.Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery(sql);
					
				} catch (SQLException e2) {
					
				}
			}
		});
		JPanel panelBackgroupButton = new JPanel();
		panelBackgroupButton.setLayout(null);
		panelBackgroupButton.setBounds(0, 91, 800, 100);
		panelBackgroupButton.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JPanel panelButton = new JPanel();
		panelButton.setLayout(new GridLayout(2, 2, 3, 3));
		panelButton.setBounds(590, 0, 180, 100);
		
		ImageIcon icon4 = new ImageIcon("gach.png");
		JLabel labelButton = new JLabel("Quản lý danh sách phòng", icon4, JLabel.CENTER);
		labelButton.setBounds(0, 30, 300, 40);
		labelButton.setFont(font2);;
		labelButton.setHorizontalTextPosition(JLabel.RIGHT);
		
		JButton chartButton = new colorGUI("Thống kê", new ImageIcon("thongke.png"), new Color(255, 69, 0), new Color(135, 206, 250));
		chartButton.setForeground(Color.white);
		chartButton.setBounds(350, 10, 200, 80);
		
		JButton loadButton = new colorGUI("Hiển thị", null, new Color(0,255,0), new Color(30,144,255));
		loadButton.setForeground(Color.white);
		loadButton.setFont(font1);
		
		JButton addButton = new colorGUI("Thêm", null, new Color(0,255,0), new Color(30,144,255));
		addButton.setForeground(Color.white);
		addButton.setFont(font1);
		
		JButton editButton = new colorGUI("Sửa", null, new Color(0,255,0), new Color(30,144,255));
		editButton.setForeground(Color.white);
		editButton.setFont(font1);       
		
		JButton deleteButton = new colorGUI("Xoá", null, new Color(0,255,0), new Color(30,144,255));
		deleteButton.setForeground(Color.white);
		deleteButton.setFont(font1);
		
		panelButton.add(addButton);
		panelButton.add(deleteButton);
		panelButton.add(loadButton);
		panelButton.add(editButton);
		
		panelBackgroupButton.add(chartButton);
		panelBackgroupButton.add(panelButton);
		panelBackgroupButton.add(labelButton);
		
		chartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				showChart();
			}
		});
		
		frame.add(panelBackgroupButton);
		
		JPanel panelSearch = new JPanel();
		panelSearch.setLayout(null);
		panelSearch.setBounds(0, 193, 800, 60);
		panelSearch.setBorder(BorderFactory.createLineBorder(Color.black));
		
		ImageIcon icon5 = new ImageIcon("loc.png");
		JLabel labelSearch = new JLabel(icon5);
		labelSearch.setBounds(10, 10, 40, 40);

//		statusFilter1 = new JCheckBox("Đang ở");
//		statusFilter1.setBounds(60, 12, 70, 40);
//		
//		statusFilter2 = new JCheckBox("Còn trống");
//		statusFilter2.setBounds(140, 12, 90, 40);
//		
//		String[] tenanstFilter = {"Khách thuê", "1 người ở", "2 người ở", "3-4 người ở", "Không giới hạn"};
//		tenantsBox = new JComboBox<>(tenanstFilter);
//		
//		tenantsBox.setBounds(240, 12, 110, 40);
//		String[] floorFilter = new String[51];
//		floorFilter[0] = "Tầng";
//		for (int i = 1; i <= 50; i++) {
//			floorFilter[i] = String.valueOf(i);
//		}
//		floorBox = new JComboBox<>(floorFilter);
//		floorBox.setBounds(370, 12, 60, 40);
		
		JButton filterButton = new JButton("Lọc");
		filterButton.setBounds(500, 12, 100, 40);
		JButton deleteFilter = new JButton("Thiết lập lại"); 
		deleteFilter.setBounds(620, 12, 100, 40);
		
		filterButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				filterTable();
			}
		});
		
		deleteFilter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				statusFilter1.setSelected(false);
		        statusFilter2.setSelected(false);
		        tenantsBox.setSelectedIndex(0);
		        floorBox.setSelectedIndex(0);
		        priceField.setText("Giá");
		        priceField.setForeground(Color.GRAY);

		        loadTableData();
			}
		});
		
		priceField = new JTextField("Giá");
		priceField.setForeground(Color.gray);
		priceField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if (priceField.getText().equals("Giá")) {
					priceField.setText("");
					priceField.setForeground(Color.black);
				}
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if (priceField.getText().trim().isEmpty()) {
					priceField.setText("Giá");
					priceField.setForeground(Color.gray);
				}
			}
		});
		priceField.setFont(font1);
		priceField.setBounds(60, 12, 100, 40);
		
		panelSearch.add(deleteFilter);
		panelSearch.add(filterButton);
		panelSearch.add(priceField);
//		panelSearch.add(floorBox);
//		panelSearch.add(tenantsBox);
		panelSearch.add(labelSearch);
//		panelSearch.add(statusFilter2);
//		panelSearch.add(statusFilter1);
		
		frame.add(panelSearch);
		
		JPanel panelTable = new JPanel();
		panelTable.setLayout(new BorderLayout());
		panelTable.setBounds(0, 255, 800, 400);
		
		String[] dataField = {"ID", "Địa chỉ", "Phòng", "Tầng", "Khách thuê", "Giá", "Tình trạng", "Diện tích", "Tiền điện", "Tiền nước", "Wifi", "Rác"};
		model = new DefaultTableModel(dataField, 0);
		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		panelTable.add(scrollPane, BorderLayout.CENTER);
		
		frame.add(panelTable);
		
		editButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					String[] rowData = new String[table.getColumnCount()];
					for (int i = 0; i < rowData.length; i++) {
						rowData[i] = table.getValueAt(selectedRow, i).toString();
					}
					acctionPage("Sửa", rowData);
				}
				else {
					JOptionPane.showMessageDialog(frame, "Vui lòng chọn một hàng để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				acctionPage("Thêm", null);
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					String roomId = model.getValueAt(selectedRow, 0).toString();
					try {
						String deleteQuery = "DELETE FROM Rooms WHERE ID = ?";
						PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
						deleteStatement.setString(1, roomId);
						int rowsDelete = deleteStatement.executeUpdate();
						if (rowsDelete > 0) {
							model.removeRow(selectedRow);
							JOptionPane.showMessageDialog(frame, "Xoá thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (SQLException ex1) {
						ex1.printStackTrace();
						JOptionPane.showMessageDialog(frame, "Lỗi kết nối cơ sở dữ liệu", "Thông báo", JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(frame, "Vui lòng chọn một hàng để xoá!", "Thông báo", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		loadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				loadTableData();
			}
		});
     
		frame.setVisible(true);
		
	}
	private void acctionPage(String action, String[] rowData) {
		dialog = new JDialog(frame, action, true);
		dialog.setSize(500, 700);
		dialog.setLayout(null);
		dialog.setLocationRelativeTo(null);
		
		ImageIcon icon = new ImageIcon("gachcam.png");
		JLabel labelInfo = new JLabel("Thông tin: ", icon, JLabel.CENTER);
		labelInfo.setFont(font1);
		labelInfo.setHorizontalTextPosition(JLabel.RIGHT);
		labelInfo.setBounds(0, 0, 105, 30);
		
		JTextField idField = new JTextField(rowData != null ? rowData[2] : "ID phòng*");
		idField.setFont(font1);
		idField.setForeground(rowData != null ? Color.BLACK : Color.GRAY);
		idField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if (idField.getText().equals("ID phòng*")) {
					idField.setText("");
					idField.setForeground(Color.BLACK);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if (idField.getText().trim().isEmpty()) {
					idField.setText("ID phòng*");
					idField.setForeground(Color.gray);	
				}
			}
		});
		idField.setBounds(30, 40, 170, 40);
	
		JTextField floorField = new JTextField(rowData != null ? rowData[3] : "Tầng*");
		floorField.setFont(font1);
		floorField.setForeground(rowData != null ? Color.BLACK : Color.GRAY);
		floorField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if (floorField.getText().equals("Tầng*")) {
					floorField.setText("");
					floorField.setForeground(Color.BLACK);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if (floorField.getText().trim().isEmpty()) {
					floorField.setText("Tầng*");
					floorField.setForeground(Color.gray);	
				}
			}
		});
		floorField.setBounds(280, 40, 170, 40);
		
		JLabel labelAddress = new JLabel("Địa chỉ:", icon, JLabel.CENTER);
		labelAddress.setFont(font1);
		labelAddress.setHorizontalTextPosition(JLabel.RIGHT);
		labelAddress.setBounds(0, 90, 80, 30);
		
		JTextField addressField = new JTextField(rowData != null ? rowData[1] : "Địa chỉ*");
		addressField.setFont(font1);
		addressField.setForeground(rowData != null ? Color.BLACK : Color.GRAY);
		addressField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if (addressField.getText().equals("Địa chỉ*")) {
					addressField.setText("");
					addressField.setForeground(Color.BLACK);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if (addressField.getText().trim().isEmpty()) {
					addressField.setText("Địa chỉ*");
					addressField.setForeground(Color.gray);	
				}
			}
		});
		addressField.setBounds(30, 130, 420, 40);
		
		JLabel labelInfoBase = new JLabel("Thông tin cơ bản: ", icon, JLabel.CENTER);
		labelInfoBase.setFont(font1);
		labelInfoBase.setHorizontalTextPosition(JLabel.RIGHT);
		labelInfoBase.setBounds(0, 180, 150, 30);
		
		String[] tenantsOptions = {"Khách thuê","1 người ở", "2 người ở", "3-4 người ở", "Không giới hạn"};
		tenantsField = new JComboBox<>(tenantsOptions);
		tenantsField.setFont(font1);
		tenantsField.setEditable(true);
		if (rowData != null) {
		    tenantsField.setSelectedItem(rowData[4]);
		}
		tenantsField.setBounds(30, 220, 170, 40);
		
		JTextField priceField = new JTextField(rowData != null ? rowData[5] : "Giá*");
		priceField.setFont(font1);
		priceField.setForeground(rowData != null ? Color.black : Color.gray);
		priceField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if (priceField.getText().trim().isEmpty()) {
					priceField.setText("Giá*");
					priceField.setForeground(Color.gray);
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if (priceField.getText().equals("Giá*")) {
					priceField.setText("");
					priceField.setForeground(Color.black);
				}
			}
		}
		);
		priceField.setBounds(280, 220, 170, 40);
		
		JTextField areaField = new JTextField(rowData != null ? rowData[7] : "Diện tích*");
		areaField.setFont(font1);
		areaField.setForeground(rowData != null ? Color.black : Color.gray);
		areaField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if (areaField.getText().trim().isEmpty()) {
					areaField.setText("Diện tích*");
					areaField.setForeground(Color.gray);
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if (areaField.getText().equals("Diện tích*")) {
					areaField.setText("");
					areaField.setForeground(Color.black);
				}
			}
		});
		areaField.setBounds(30, 270, 170, 40);
		
		ImageIcon icon3 = new ImageIcon("gachcam.png");
		JLabel labelServiceSet = new JLabel("Cài đặt dịch vụ:", icon3, JLabel.CENTER);
		labelServiceSet.setFont(font1);
		labelServiceSet.setHorizontalTextPosition(JLabel.RIGHT);
		labelServiceSet.setBounds(0, 320, 135, 30);
		
		String[] elec = {"Dịch vụ điện", "3.500 VNĐ/kWh", "4.500 VNĐ/kWh", "5.000 VNĐ/người"};
		elecBill = new JComboBox<>(elec);
		elecBill.setEditable(true);
		if (rowData != null) {
		    elecBill.setSelectedItem(rowData[8]);
		}
		elecBill.setBounds(30, 360, 170, 40);
		
		String[] bill = {"Dịch vụ nước", "30.000 VNĐ/người", "50.000 VNĐ/người", "70.000 VNĐ/người", "100.000 VNĐ/người", "Miễn phí/Không sử dụng"};
		waterBill = new JComboBox<>(bill);
		waterBill.setEditable(true);
		if (rowData != null) {
		    waterBill.setSelectedItem(rowData[9]);
		}
		waterBill.setBounds(280, 360, 170, 40);
		
		String[] garbage = {"Dịch vụ rác", "10.000 VNĐ/người", "15.000 VNĐ/người", "20.000 VNĐ/người", "25.000 VNĐ/người","MIễn phí"};
		garbageBill = new JComboBox<String>(garbage);
		garbageBill.setEditable(true);
		if (rowData != null) {
		    garbageBill.setSelectedItem(rowData[10]);
		}
		garbageBill.setBounds(30, 410, 170, 40);
		
		String[] wifi = {"Dịch vụ wifi/internet", "15.000 VNĐ/người", "20.000 VNĐ/người", "30.000 VNĐ/người", "50.000 VNĐ/người","MIễn phí"};
		wifiBill = new JComboBox<String>(wifi);
		wifiBill.setEditable(true);
		if (rowData != null) {
		    wifiBill.setSelectedItem(rowData[11]);
		}
		wifiBill.setBounds(280, 410, 170, 40);
		
		JLabel labelStatus = new JLabel("Tình trạng", icon, JLabel.CENTER);
		labelStatus.setHorizontalTextPosition(JLabel.RIGHT);
		labelStatus.setBounds(0, 460, 100, 40);
		
		String[] check = {"Tình trạng", "Đã thuê", "Còn trống", "Đang sửa chữa"};
		status = new JComboBox<String>(check);
		status.setEditable(true);
		if (rowData != null) {
		    status.setSelectedItem(rowData[6]);
		}
		status.setBounds(30, 500, 170, 40);
		
		JButton saveButton = new JButton("Lưu");
		saveButton.setFont(font2);
		saveButton.setBounds(20, 600, 190, 50);
		saveButton.setBackground(Color.green);
		
		JButton caccelButton = new JButton("Huỷ");
		caccelButton.setFont(font2);
		caccelButton.setBounds(280, 600, 190, 50);
		caccelButton.setBackground(Color.gray);
		
		dialog.add(addressField);
		dialog.add(labelAddress);
		dialog.add(labelInfo);
		dialog.add(idField);
		dialog.add(floorField);
		dialog.add(labelInfoBase);
		dialog.add(tenantsField);
		dialog.add(priceField);
		dialog.add(labelServiceSet);
		dialog.add(areaField);
		dialog.add(elecBill);
		dialog.add(waterBill);
		dialog.add(garbageBill);
		dialog.add(wifiBill);
		dialog.add(labelStatus);
		dialog.add(status);
		dialog.add(saveButton);
		dialog.add(caccelButton);
		
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String address = addressField.getText().trim();
				String id = idField.getText().trim();
				String floor = floorField.getText().trim();
				String tenants = tenantsField.getSelectedItem().toString().trim();
				String price = priceField.getText().trim();
				String statusText = status.getSelectedItem().toString().trim();
				String area = areaField.getText().trim();
				String elec = elecBill.getSelectedItem().toString().trim();
				String water = waterBill.getSelectedItem().toString().trim();
				String garbage = garbageBill.getSelectedItem().toString().trim();
				String wifi = wifiBill.getSelectedItem().toString().trim();
				
				if (address.isEmpty() || id.isEmpty() || floor.isEmpty() || tenants.isEmpty() || tenants.equals("Khách thê") || price.isEmpty() || statusText.equals("Tình trạng") || statusText.isEmpty() || area.isEmpty() || elec.isEmpty() || elec.equals("Dịch vụ điện") || water.isEmpty() || water.equals("Dịch vụ nước") || garbage.isEmpty() || garbage.equals("Dịch vụ rác") || wifi.isEmpty() || wifi.equals("Dịch vụ wifi/internet")) {
					JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
					return;
				}
				try {
					String query;
					if (action.equals("Sửa")) {
						query = "UPDATE Rooms SET Address = ?,RoomID = ?, Floor = ?, Tenants = ?, Price = ?, Status = ?, Area = ?, Elec = ?, Water = ?, Wifi = ?, Garbage = ? WHERE ID = ?";
					}
					else {
						query = "INSERT INTO Rooms(Address, RoomID, Floor, Tenants, Price, Status, Area, Elec, Water, Wifi, Garbage) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					}
					PreparedStatement statement = connection.prepareStatement(query);
					statement.setString(1, address);
					statement.setString(2, id);
					try {
						statement.setInt(3, Integer.parseInt(floor));
					} catch (NumberFormatException floorException) {
						JOptionPane.showMessageDialog(dialog, "Tầng phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
						return;				}
					statement.setString(4, tenants);
					try {
						statement.setBigDecimal(5, new BigDecimal(price));
					} catch (NumberFormatException priceException) {
						JOptionPane.showMessageDialog(dialog, "Giá phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
						return;
					}
					statement.setString(6, statusText);
					try {
						statement.setBigDecimal(7, new BigDecimal(area));
					} catch (NumberFormatException priceException) {
						JOptionPane.showMessageDialog(dialog, "Giá phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
						return;
					}
					statement.setString(8, elec);
					statement.setString(9, water);
					statement.setString(10, wifi);
					statement.setString(11, garbage);
					
					if (action.equals("Sửa")) {
						statement.setInt(12, Integer.parseInt(rowData[0]));
					}
					
						int rowInserted = statement.executeUpdate();
						if (rowInserted > 0) {
							JOptionPane.showMessageDialog(dialog, action + " thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
							dialog.dispose();
							loadTableData();
						}
						else {
							JOptionPane.showMessageDialog(frame, "Lỗi, vui lòng thử lại", "Thông báo", JOptionPane.WARNING_MESSAGE);
							dialog.dispose();
						}
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(dialog, "Lỗi kết nối cơ sở dữ liệu", "Thông báo", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		caccelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dialog.dispose();
			}
		});
		
		dialog.setVisible(true);
	}
	
	private void loadTableData() {
		model.setRowCount(0);
		try {
			String query = "SELECT * FROM Rooms";
			java.sql.Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				model.addRow(new Object[] {
						resultSet.getString("ID"),
						resultSet.getString("Address"),
						resultSet.getString("RoomID"),
						resultSet.getString("Floor"),
						resultSet.getString("Tenants"),
						resultSet.getString("Price"),
						resultSet.getString("Status"),
						resultSet.getString("Area"),
						resultSet.getString("Elec"),
						resultSet.getString("Water"),
						resultSet.getString("Wifi"),
						resultSet.getString("Garbage")
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(dialog, "Lỗi kết nối cơ sở dữ liệu!", "Thông báo", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void showChart() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		try {
			String query = "SELECT Status, COUNT(*) AS Count FROM Rooms GROUP BY Status";
			java.sql.Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			while (resultSet.next()) {
				String status = resultSet.getString("Status");
				int count = resultSet.getInt("Count");
				dataset.addValue(count, "Số lượng" , status);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(frame, "Lỗi kết nối cơ sở dữ liệu!", "Thông báo", JOptionPane.ERROR_MESSAGE);
			return;
		}
		JFreeChart barChart = ChartFactory.createBarChart("Thống kê số lượng phòng theo tình trạng", "Tình trạng", "Số lượng", dataset);
		ChartPanel chartPanel = new ChartPanel(barChart);
		JDialog chartDialog = new JDialog(frame, "Biểu đồ thống kê", true);
		chartDialog.setSize(600, 500);
		chartDialog.setLocationRelativeTo(frame);
		chartDialog.add(chartPanel);
		chartDialog.setVisible(true);
	}
	private void filterTable() {
	    String query = "SELECT * FROM Rooms WHERE 1=1";
	    String priceFilter = priceField.getText().trim();
	    if (!priceFilter.equals("Giá") && !priceFilter.isEmpty()) {
	        try {
	            BigDecimal price = new BigDecimal(priceFilter);
	            query += " AND Price >= ?";
	        } catch (NumberFormatException num) {
	            JOptionPane.showMessageDialog(dialog, "Giá phải là số hợp lệ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
	            return;
	        }
	    }

	    model.setRowCount(0);

	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	        int index = 1;
	        if (!priceFilter.equals("Giá") && !priceFilter.isEmpty()) {
	            BigDecimal price = new BigDecimal(priceFilter);
	            statement.setBigDecimal(index++, price);
	        }

	        ResultSet resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            model.addRow(new Object[] {
	                resultSet.getString("ID"),
	                resultSet.getString("Address"),
	                resultSet.getString("RoomID"),
	                resultSet.getString("Floor"),
	                resultSet.getString("Tenants"),
	                resultSet.getString("Price"),
	                resultSet.getString("Status"),
	                resultSet.getString("Area"),
	                resultSet.getString("Elec"),
	                resultSet.getString("Water"),
	                resultSet.getString("Wifi"),
	                resultSet.getString("Garbage")
	            });
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(dialog, "Lỗi kết nối cơ sở dữ liệu", "Thông báo", JOptionPane.ERROR_MESSAGE);
	    }
	}

}