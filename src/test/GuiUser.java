package test;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComponent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class GuiUser {

	JFrame frame;
	static JTextField nameField = new JTextField(5);
	public static GuiUser window = new GuiUser();//
	private static ArrayList<String> userlist;
	private static DefaultTableModel model;
	private static JTable table;

	/**
	 * Launch the application.
	 */
	
	private static Point point = new Point();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiUser window = new GuiUser();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GuiUser() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("deprecation")
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Gui2.class.getResource("/image_resource/window_icon.png")));
		frame.setBounds(0, 0, 500, 833);
		frame.getContentPane().setLayout(null);
		frame.setUndecorated(true);//프레임틀 없애기
		frame.setVisible(false);
		frame.setResizable(false);
		frame.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				point.x=e.getX();
				point.y=e.getY();
			}
		});
		frame.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point p = frame.getLocation();
				frame.setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
			}
		});//창 드래그가능
		
		JPanel RegisterPage = new JPanel();//메인 패널, 등록화면
		RegisterPage.setBounds(0, 0, 500, 833);
		//frame.getContentPane().add(RegisterPage);
		frame.getLayeredPane().add(RegisterPage, new Integer(0));
		RegisterPage.setLayout(null);
		
		JPanel MessagePage = new JPanel();//메세지 패널, 등록화면
		MessagePage.setBounds(0, 0, 200, 200);
		frame.getContentPane().add(MessagePage);
		MessagePage.setLayout(null);
		
		JButton Registerbackbutton = new JButton();//등록화면-뒤로버튼
		Registerbackbutton.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/registerpage_before_back.png")));
		Registerbackbutton.setBorderPainted(false);
		Registerbackbutton.setContentAreaFilled(false);
		Registerbackbutton.setFocusPainted(false);
		Registerbackbutton.setBounds(31, 710, 73, 74);
		RegisterPage.add(Registerbackbutton);
		Registerbackbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				JButton c = (JButton)e.getSource();
				c.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/registerpage_after_back.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				JButton c = (JButton)e.getSource();
				c.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/registerpage_before_back.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		
		JButton Registeruserbutton = new JButton();//등록화면-추가버튼
		Registeruserbutton.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/registerpage_before_register.png")));
		Registeruserbutton.setBorderPainted(false);
		Registeruserbutton.setContentAreaFilled(false);
		Registeruserbutton.setFocusPainted(false);
		Registeruserbutton.setBounds(104, 710, 73, 74);
		RegisterPage.add(Registeruserbutton);
		Registeruserbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				JButton c = (JButton)e.getSource();
				c.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/registerpage_after_register.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				JButton c = (JButton)e.getSource();
				c.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/registerpage_before_register.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(Lock.isLock) {
					Lock.stopLock();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				String inputStr[] = new String[1];
				inputStr[0] = nameField.getText();
				if(userlist.contains(inputStr[0])) {//이미 같은 이름이 존재하는 경우
					nameField.setText("이미 같은 이름의 사용자가 존재합니다");
				}else if(inputStr[0].equals("")){
					nameField.setText("이름을 입력하시오");
				}else {// 새로운 이름 넣기
					userlist.add(inputStr[0]);//어레이 리스트에도 이름 넣기
					try {
						System.out.println(inputStr[0]);
						User.useradd(inputStr[0]);//사진 찍기
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					model.addRow(inputStr);//테이블에도 이름 넣기
					nameField.setText("");//입력칸 비우기
				}
			}
		});
		
		JButton Registerdeletebutton = new JButton();//등록화면-삭제버튼
		Registerdeletebutton.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/registerpage_before_delete.png")));
		Registerdeletebutton.setBorderPainted(false);
		Registerdeletebutton.setContentAreaFilled(false);
		Registerdeletebutton.setFocusPainted(false);
		Registerdeletebutton.setBounds(179, 710, 73, 74);
		RegisterPage.add(Registerdeletebutton);
		Registerdeletebutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				JButton c = (JButton)e.getSource();
				c.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/registerpage_after_delete.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				JButton c = (JButton)e.getSource();
				c.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/registerpage_before_delete.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(Lock.isLock) {
					Lock.stopLock();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				// TODO Auto-generated method stub
				if(table.getSelectedRow() == -1) {//선택항목 없을경우
					return;
				}else {
					User.delUser(userlist.get(table.getSelectedRow()));
					//ㄴ 사진 지우기
					userlist.remove(table.getSelectedRow());
					//ㄴ 어레이리스트에도 이름 지우기
					model.removeRow(table.getSelectedRow());
					//ㄴ 테이블에도 이름지우기
					
					
				}
			}
		});

		JLabel Registerwallpaper = new JLabel();
		Registerwallpaper.setBounds(0, 0, 500, 833);
		RegisterPage.add(Registerwallpaper);
		Registerwallpaper.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/registerpage_wp.png")));		

		
		
		
		
		
		
		//초기화
		String header[] = {"사용자목록"};
		model = new DefaultTableModel(null,header);
		table = new JTable(model);
		JScrollPane scrollpane = new JScrollPane(table);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		//JTextField nameField = new JTextField(5);
		panel.add(nameField);//입력칸 넣기
		
		//초기리스트
		userlist = User.getUserList();//등록된 이름 가져오기
		for(int i = 0; i < userlist.size(); i++) {
			String[] input = new String[1];
			input[0] = userlist.get(i);
			model.addRow(input);//table에 사용자 목록 하나씩 넣기
		}

		panel.setBounds(100, 600, 300, 30);
		frame.getLayeredPane().add(panel, new Integer(1));
		
		scrollpane.setBounds(100, 200, 300, 350);
		frame.getLayeredPane().add(scrollpane, new Integer(1));
	}

}
