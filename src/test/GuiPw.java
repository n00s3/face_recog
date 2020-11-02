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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class GuiPw {

	JFrame frame;
	JTextField pwField = new JTextField(50);
	int v;
	public static GuiPw window = new GuiPw();//
	

	/**
	 * Launch the application.
	 */
	
	private static Point point = new Point();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiPw window = new GuiPw();
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
	public GuiPw() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("deprecation")
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Gui2.class.getResource("/image_resource/window_icon.png")));
		//frame.setBounds(0, 0, 500, 833);
		frame.setBounds(0, 0, 600, 333);
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
		//RegisterPage.setBounds(0, 0, 500, 833);
		RegisterPage.setBounds(0, 0, 600, 333);
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
		Registerbackbutton.setBounds(221, 230, 73, 74);
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
		Registeruserbutton.setBounds(294, 230, 73, 74);
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

						frame.setVisible(false);
						if(v == 1) {
							String[] temp = null;
							for (File f : Crypto3.window.jfc.getSelectedFiles()) {// 선택된 파일을 옮긴다
								// System.out.println(f.toString());
								temp = f.toString().split("\\\\");
								String fileName = temp[temp.length - 1];
								f.renameTo(new File("C:\\Project\\crypto\\_plain\\" + fileName));
							}
							Crypto3.window.createPwTxt();
							try {// 암호화
								Process py = Runtime.getRuntime().exec("python C:\\Project\\crypto\\myAES_enc.py");
								py.waitFor();
							} catch (IOException | InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							String location = "";// 원래 있던 위치
							for (int i = 0; i < temp.length - 1; i++) {
								location += temp[i];
								location += "\\";
							}
							// System.out.println(location);

							String encPath = "C:\\Project\\crypto\\_encoded";
							File folder = new File(encPath);
							for (File f : folder.listFiles()) {
								f.renameTo(new File(location + f.getName()));

							}
						} else if(v == 2) {
							String[] temp = null;
							for (File f : Crypto3.window.jfc.getSelectedFiles()) {// 선택된 파일을 옮긴다
								System.out.println(f.toString());
								temp = f.toString().split("\\\\");
								String fileName = temp[temp.length - 1];
								f.renameTo(new File("C:\\Project\\crypto\\_encoded\\" + fileName));
							}
							Crypto3.window.createPwTxt();
							try {// 암호화
								Process py = Runtime.getRuntime().exec("python C:\\Project\\crypto\\myAES_dec2.py");
								py.waitFor();
							} catch (IOException | InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							String location = "";// 원래 있던 위치
							for (int i = 0; i < temp.length - 1; i++) {
								location += temp[i];
								location += "\\";
							}
							// System.out.println(location);

							String encPath = "C:\\Project\\crypto\\_encoded";
							String decPath = "C:\\Project\\crypto\\_decoded";
							File folder = new File(decPath);
							for (File f : folder.listFiles()) {
								f.renameTo(new File(location + f.getName()));
							}
							File folder2 = new File(encPath);
							for (File f : folder2.listFiles()) {
								f.renameTo(new File(location + f.getName()));

							}
						}
						pwField.setText("");
					}
				
			
		});
		
			

		JLabel Registerwallpaper = new JLabel();
		//Registerwallpaper.setBounds(0, 0, 500, 833);
		Registerwallpaper.setBounds(0, 0, 600, 333);
		RegisterPage.add(Registerwallpaper);
		Registerwallpaper.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/pw_wallpaper.png")));		

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(pwField);//입력칸 넣기
		
		panel.setBounds(150, 150, 300, 30);
		frame.getLayeredPane().add(panel, new Integer(1));
	}

}
