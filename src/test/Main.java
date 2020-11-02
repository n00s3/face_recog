package test;

/*
 * ������
 * 
 * Main������ Ʈ���̴��
 * 		Ʈ���̸� ����Gui.openManager()�Լ�����
 * 
 * Gui������ ���������
 * 		Lock.stopLock();�Ǵ� Lock.startLock()�Լ� �����Ҽ�����
 * 		�Ǵ� GuiSetting.openSetting();�� �����Ҽ�����
 * 
 * GuiSetting������ �������� ���
 * 		User�� �Լ����� ȣ����
 * 		User���� ����ڸ���Ʈ, ����� �߰�, ��������� ���� ����
 * 
 * Lock������ ��� ����� ���
 * 		���̽� ���ϰ� ���� ����� ���� ��ݹ� ��������� ��
 */




import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class Main {
	//static final String PY_COMMAND = "python C:\\Project\\bin\\face_recog.py";
	//static final String HOOK_PATH = "C:\\Project\\bin\\keyhook.exe";
	//static int c_pid;
	//static String kill_cmd = "taskkill /F /PID ";
	
	public static void main(String[] args) throws IOException, InterruptedException, AWTException {
		
		//��������� üũ
		ArrayList<String> list = User.getUserList();
		if(list.size() == 0) {
			///Gui.openManager();
			///GuiSetting.openSetting();
			///GuiSetting.nameField.setText("����ڸ� �߰����ּ���");
			///Gui2 window = new Gui2();
			Gui2.window.frame.setVisible(true);
			GuiUser.window.frame.setVisible(true);
			GuiUser.nameField.setText("����ڸ� ������ּ���");
		}else {
			//��ũ��Ʈ ����
			Lock.startLock();
		}
		
		
		
		//tray  ���
		TrayIcon trayicon=null;

		if(SystemTray.isSupported()) {
			SystemTray tray=SystemTray.getSystemTray();
			Image trayImage=Toolkit.getDefaultToolkit().getImage("C:\\Project\\guiImages\\window_icon.png");
		  
			ActionListener listener=new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(e.toString().contains("����")) {
						System.out.println("����â ����");
						
						///Gui.openManager();
						Gui2.window.frame.setVisible(true);
						
					}
					else if(e.toString().contains("����")) {
						/*
						try { 
							Process kill = Runtime.getRuntime().exec(kill_cmd+Lock.c_pid);
						}	
						catch (IOException e1) { 
							e1.printStackTrace(); 
						}
						*/
						Lock.stopLock();
						Lock.isInit = false;
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						System.out.println("����");
						System.exit(0);
					}
				}
			};
		   
			PopupMenu popup=new PopupMenu();
		   
			MenuItem defaultItem=new MenuItem("����");
			defaultItem.addActionListener(listener);
			 
			MenuItem exit=new MenuItem("����");
			exit.addActionListener(listener);
		   
			popup.add(defaultItem);
			popup.add(exit);
			trayicon=new TrayIcon(trayImage,"���α׷�", popup);
			trayicon.addActionListener(listener);
		  
		   
			try {
				tray.add(trayicon);
			} catch(AWTException e) {
				e.printStackTrace();
				}
		}
		else {
			
		}
		//tray
		
		//log���� ���

		while (true) {

			BufferedImage img1;

			try {

				img1 = ImageIO.read(new File("C:\\Project\\log\\log.jpg"));

			}

			catch (IOException e){

				continue;

			}

			ImagePanel panel = new ImagePanel(img1);

			Lock.frame.add(panel);

			//panel.repaint();

			panel.revalidate();

			panel.repaint();

			Lock.frame.revalidate();

			Lock.frame.repaint();

			

			Thread.sleep(300);

			Lock.frame.remove(panel);

		}
	}
}
