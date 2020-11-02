package test;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.AbstractAction;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class Gui2 {

	public JFrame frame;//
	public static Gui2 window = new Gui2();//

	/**
	 * Launch the application.
	 */
	
	private static Point point = new Point();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					///Gui2 window = new Gui2();
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
	public Gui2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Gui2.class.getResource("/image_resource/window_icon.png")));
		frame.setBounds(0, 0, 1000, 563);
		frame.getContentPane().setLayout(null);
		frame.setUndecorated(true);//������Ʋ ���ֱ�
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
	});//â �巡�װ���
		
		JButton Mainexit = new JButton();//����-�����ư
		Mainexit.setBounds(931, 8, 47, 47);
		frame.getContentPane().add(Mainexit);
		Mainexit.setBorderPainted(false);//�̹��� ����
		Mainexit.setContentAreaFilled(false);
		Mainexit.setFocusPainted(false);
		Mainexit.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/mainpage_before_dispose.png")));
		Mainexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				JButton c = (JButton)e.getSource();
				c.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/mainpage_after_dispose.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				JButton c = (JButton)e.getSource();
				c.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/mainpage_before_dispose.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();//����
			}
		});
		
		
		JPanel MainPage = new JPanel();//���� �г�, ����ȭ��
		MainPage.setBounds(0, 0, 1000, 563);
		frame.getContentPane().add(MainPage);
		MainPage.setLayout(null);

		
		JButton MainEncodeButton = new JButton();//����ȭ��-��ȣȭ��ư
		MainEncodeButton.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/encode_before.png")));
		MainEncodeButton.setBorderPainted(false);//�̹��� ����
		MainEncodeButton.setContentAreaFilled(false);
		MainEncodeButton.setFocusPainted(false);
		MainEncodeButton.setBounds(261, 173, 217, 217);//���ʹ�ư
		MainPage.add(MainEncodeButton);
		MainEncodeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				JButton c = (JButton)e.getSource();
				c.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/encode_after.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				JButton c = (JButton)e.getSource();
				c.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/encode_before.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (Crypto3.window.jfc.showOpenDialog(Crypto3.window.frame) == JFileChooser.APPROVE_OPTION) {
					// showopendialog ���� â�� ���� Ȯ�� ��ư�� �������� Ȯ��
					Crypto3.window.pwFrame(1);
				}
			}
		});
		
		JButton MainDecodeButton = new JButton();//����ȭ��-��ȣȭ��ư
		MainDecodeButton.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/decode_before.png")));
		MainDecodeButton.setBorderPainted(false);//�̹��� ����
		MainDecodeButton.setContentAreaFilled(false);
		MainDecodeButton.setFocusPainted(false);
		MainDecodeButton.setBounds(524, 173, 217, 217);//�����ʹ�ư
		MainPage.add(MainDecodeButton);
		MainDecodeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				JButton c = (JButton)e.getSource();
				c.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/decode_after.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				JButton c = (JButton)e.getSource();
				c.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/decode_before.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (Crypto3.window.jfc.showOpenDialog(Crypto3.window.frame) == JFileChooser.APPROVE_OPTION) {
					// showopendialog ���� â�� ���� Ȯ�� ��ư�� �������� Ȯ��
					Crypto3.window.pwFrame(2);
				}
			}
		});
		
		JButton Mainlockbutton = new JButton();//����ȭ��-��ݹ�ư
		Mainlockbutton.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/mainpage_before_lock.png")));
		Mainlockbutton.setBorderPainted(false);
		Mainlockbutton.setContentAreaFilled(false);
		Mainlockbutton.setFocusPainted(false);
		//Mainlockbutton.setBounds(524, 173, 217, 217);//�����ʹ�ư
		Mainlockbutton.setBounds(393, 307, 217, 217);//�Ʒ��ʹ�ư
		MainPage.add(Mainlockbutton);
		Mainlockbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				JButton c = (JButton)e.getSource();
				c.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/mainpage_after_lock.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				JButton c = (JButton)e.getSource();
				c.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/mainpage_before_lock.png")));
			}
			@Override//���콺 ��ư�� Ŭ�� �Ǿ��� ��
			public void mouseClicked(MouseEvent e) {
				ArrayList<String> list = User.getUserList();
				if(list.size() == 0) {
					GuiUser.window.frame.setVisible(true);
					GuiUser.nameField.setText("����ڸ� ������ּ���");
				}
				else {
					if(!Lock.isLock) Lock.startLock();
					else System.out.println("�̹̽�����");
				}
			}
		});
		
		JButton Mainregisterbutton = new JButton();//����ȭ��-��Ϲ�ư
		Mainregisterbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		Mainregisterbutton.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/mainpage_before_register.png")));
		Mainregisterbutton.setBorderPainted(false);
		Mainregisterbutton.setContentAreaFilled(false);
		//Mainregisterbutton.setBounds(261, 173, 217, 217);//���ʹ�ư
		Mainregisterbutton.setBounds(393, 39, 217, 217);//��ܹ�ư
		MainPage.add(Mainregisterbutton);
		Mainregisterbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				JButton c = (JButton)e.getSource();
				c.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/mainpage_after_register.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				JButton c = (JButton)e.getSource();
				c.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/mainpage_before_register.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				//JButton c = (JButton)e.getSource();
				//GuiUser window = new GuiUser();//���â����
				GuiUser.window.frame.setVisible(true);
			}
		});
		
		JLabel Mainwallpaper = new JLabel();
		Mainwallpaper.setBounds(0, 0, 1000, 563);
		MainPage.add(Mainwallpaper);
		Mainwallpaper.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/mainpage_wp.png")));
		
	}
}



