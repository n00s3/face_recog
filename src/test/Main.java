package test;

/*
 * 설명설명
 * 
 * Main에서는 트레이담당
 * 		트레이를 통해Gui.openManager()함수실행
 * 
 * Gui에서는 선택지담당
 * 		Lock.stopLock();또는 Lock.startLock()함수 실행할수있음
 * 		또는 GuiSetting.openSetting();을 실행할수있음
 * 
 * GuiSetting에서는 유저관리 담당
 * 		User의 함수들을 호출함
 * 		User에서 사용자리스트, 사용자 추가, 사용자제거 등이 있음
 * 
 * Lock에서는 잠금 기능을 담당
 * 		파이썬 파일과 소켓 통신을 통해 잠금및 잠금해제를 함
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
		
		//사용자유무 체크
		ArrayList<String> list = User.getUserList();
		if(list.size() == 0) {
			///Gui.openManager();
			///GuiSetting.openSetting();
			///GuiSetting.nameField.setText("사용자를 추가해주세요");
			///Gui2 window = new Gui2();
			Gui2.window.frame.setVisible(true);
			GuiUser.window.frame.setVisible(true);
			GuiUser.nameField.setText("사용자를 등록해주세요");
		}else {
			//스크립트 실행
			Lock.startLock();
		}
		
		
		
		//tray  등록
		TrayIcon trayicon=null;

		if(SystemTray.isSupported()) {
			SystemTray tray=SystemTray.getSystemTray();
			Image trayImage=Toolkit.getDefaultToolkit().getImage("C:\\Project\\guiImages\\window_icon.png");
		  
			ActionListener listener=new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(e.toString().contains("열기")) {
						System.out.println("설정창 열기");
						
						///Gui.openManager();
						Gui2.window.frame.setVisible(true);
						
					}
					else if(e.toString().contains("종료")) {
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
						System.out.println("종료");
						System.exit(0);
					}
				}
			};
		   
			PopupMenu popup=new PopupMenu();
		   
			MenuItem defaultItem=new MenuItem("열기");
			defaultItem.addActionListener(listener);
			 
			MenuItem exit=new MenuItem("종료");
			exit.addActionListener(listener);
		   
			popup.add(defaultItem);
			popup.add(exit);
			trayicon=new TrayIcon(trayImage,"프로그램", popup);
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
		
		//log파일 출력

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
