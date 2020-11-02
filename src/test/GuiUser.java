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
		
		JPanel RegisterPage = new JPanel();//���� �г�, ���ȭ��
		RegisterPage.setBounds(0, 0, 500, 833);
		//frame.getContentPane().add(RegisterPage);
		frame.getLayeredPane().add(RegisterPage, new Integer(0));
		RegisterPage.setLayout(null);
		
		JPanel MessagePage = new JPanel();//�޼��� �г�, ���ȭ��
		MessagePage.setBounds(0, 0, 200, 200);
		frame.getContentPane().add(MessagePage);
		MessagePage.setLayout(null);
		
		JButton Registerbackbutton = new JButton();//���ȭ��-�ڷι�ư
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
		
		JButton Registeruserbutton = new JButton();//���ȭ��-�߰���ư
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
				if(userlist.contains(inputStr[0])) {//�̹� ���� �̸��� �����ϴ� ���
					nameField.setText("�̹� ���� �̸��� ����ڰ� �����մϴ�");
				}else if(inputStr[0].equals("")){
					nameField.setText("�̸��� �Է��Ͻÿ�");
				}else {// ���ο� �̸� �ֱ�
					userlist.add(inputStr[0]);//��� ����Ʈ���� �̸� �ֱ�
					try {
						System.out.println(inputStr[0]);
						User.useradd(inputStr[0]);//���� ���
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					model.addRow(inputStr);//���̺��� �̸� �ֱ�
					nameField.setText("");//�Է�ĭ ����
				}
			}
		});
		
		JButton Registerdeletebutton = new JButton();//���ȭ��-������ư
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
				if(table.getSelectedRow() == -1) {//�����׸� �������
					return;
				}else {
					User.delUser(userlist.get(table.getSelectedRow()));
					//�� ���� �����
					userlist.remove(table.getSelectedRow());
					//�� ��̸���Ʈ���� �̸� �����
					model.removeRow(table.getSelectedRow());
					//�� ���̺��� �̸������
					
					
				}
			}
		});

		JLabel Registerwallpaper = new JLabel();
		Registerwallpaper.setBounds(0, 0, 500, 833);
		RegisterPage.add(Registerwallpaper);
		Registerwallpaper.setIcon(new ImageIcon(Gui2.class.getResource("/image_resource/registerpage_wp.png")));		

		
		
		
		
		
		
		//�ʱ�ȭ
		String header[] = {"����ڸ��"};
		model = new DefaultTableModel(null,header);
		table = new JTable(model);
		JScrollPane scrollpane = new JScrollPane(table);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		//JTextField nameField = new JTextField(5);
		panel.add(nameField);//�Է�ĭ �ֱ�
		
		//�ʱ⸮��Ʈ
		userlist = User.getUserList();//��ϵ� �̸� ��������
		for(int i = 0; i < userlist.size(); i++) {
			String[] input = new String[1];
			input[0] = userlist.get(i);
			model.addRow(input);//table�� ����� ��� �ϳ��� �ֱ�
		}

		panel.setBounds(100, 600, 300, 30);
		frame.getLayeredPane().add(panel, new Integer(1));
		
		scrollpane.setBounds(100, 200, 300, 350);
		frame.getLayeredPane().add(scrollpane, new Integer(1));
	}

}
