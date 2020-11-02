package test;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class Lock {
	static Socket connection;
	static final String PY_COMMAND = "python C:\\Project\\bin\\faceRecog.py";
	static final String HOOK_PATH = "C:\\Project\\bin\\keyhook.exe";
	static int c_pid;
	static String kill_cmd = "taskkill /F /PID ";

	static JFrame fr = new JFrame("Frame"); // 잠금프레임 생성
	// static ImageIcon ic = new
	// ImageIcon("C:\\Project\\src\\background\\background.jpg");
	// static JLabel Image1 = new JLabel(ic);
	static Toolkit tk = Toolkit.getDefaultToolkit();
	static Cursor invisCursor = tk.createCustomCursor(tk.createImage(""), new Point(), null);

	static JFrame frame = new JFrame("hello");

	// static Thread mtp = new MultiThread_python();
	// static Thread mtc = new MultiThread_c();
	// static Thread r = new FlagCheck(fr);

	static boolean isLock = false;
	static boolean isInit = false;

	static Process py;
	static int py_pid;

	static public void init() {
		// fr.add(Image1);

		fr.getContentPane().setBackground(Color.BLACK);
		// fr.setExtendedState(JFrame.NORMAL); //전체 화면
		fr.setExtendedState(JFrame.MAXIMIZED_BOTH); // 전체 화면
		fr.setUndecorated(true); // 타이틀바 삭제
		fr.setCursor(invisCursor);
		fr.setAlwaysOnTop(true);
		// ////////////////////////////////////////////////////////////////////////
		// 최상단 위치

		frame.getContentPane().setBackground(Color.BLACK);
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setCursor(invisCursor);
		frame.setSize(640, 480);
		frame.setUndecorated(true);
		frame.setAlwaysOnTop(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(false);

		isInit = true;
	}

	static public void startLock() {

		System.out.println("잠금실행");
		if (isInit == false)
			init();
		MultiThread_python.flag = 1;
		Thread mtp = new MultiThread_python();
		Thread mtc = new MultiThread_c();
		Thread r = new FlagCheck(fr, frame);

		Lock.isLock = true;

		fr.setVisible(true);
		frame.setVisible(true);

		// 통신 스레드
		mtp.start();
		// 통신 스레드
		mtc.start();
		// 플래그 체크
		r.start();

		try {
			// Process c = Runtime.getRuntime().exec(HOOK_PATH);
			// c_pid = (int) c.pid();
			py = Runtime.getRuntime().exec(PY_COMMAND);
			py_pid = (int) py.pid();
			System.out.println("py실행");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 얼굴 인식 스크립트 호출
		/*
		 * try { String command = "python C:\\Project\\bin\\faceRecog.py";
		 * 
		 * Process p = Runtime.getRuntime().exec(command); System.out.println("실행함"); }
		 * catch (IOException e) { e.printStackTrace();
		 * 
		 * }
		 */
		// key,mouse hook //밑에잇음
		/*
		 * try { Process p1 =
		 * Runtime.getRuntime().exec("C:\\Project\\bin\\keyhook.exe"); } catch
		 * (Exception e) { e.printStackTrace(); }
		 */

	}

	static public void stopLock() {

		System.out.println("해제실행");
		fr.setVisible(false);
		frame.setVisible(false);
		Lock.isLock = false;
		FlagCheck.subFlag = 0;
		MultiThread_python.flag = 0;
		// mtp.interrupt();
		// r.interrupt();
		try {
			Thread.sleep(1500);
			Process kill = Runtime.getRuntime().exec(Lock.kill_cmd + Lock.py_pid);
			System.out.println("pykill실행");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

//플레그 체크 스레드
class FlagCheck extends Thread {
	static int subFlag = 0;

	JFrame temp_fr1;
	JFrame temp_fr2;
	String cmd1 = "reg add \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System\" /v \"DisableTaskMgr\" /t REG_DWORD /d 1 /f";
	String cmd2 = "reg add \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System\" /v \"DisableTaskMgr\" /t REG_DWORD /d 0 /f";

	FlagCheck(JFrame fr1, JFrame fr2) {
		temp_fr1 = fr1;
		temp_fr2 = fr2;
	}

	public void run() {
		while (Lock.isLock) {
			try {
				sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (MultiThread_python.flag == 1 && subFlag != 1) {
				subFlag = 1;
				System.out.println("키훅실행, 잠금화면ON");
				temp_fr1.setVisible(true && Lock.isLock);
				temp_fr2.setVisible(true && Lock.isLock);
				try {
					Process c = Runtime.getRuntime().exec(Lock.HOOK_PATH);// 키훅
					Lock.c_pid = (int) c.pid();
					Process p = Runtime.getRuntime().exec(cmd1); // 레지스트리
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // 테스트용으로 주석 처리
			} else if (MultiThread_python.flag == 2 && subFlag != 2) {
				subFlag = 2;
				System.out.println("키훅종료, 잠금화면OFF");
				temp_fr1.setVisible(false);
				temp_fr2.setVisible(false);
				try {
					Process kill = Runtime.getRuntime().exec(Lock.kill_cmd + Lock.c_pid);
					Process p = Runtime.getRuntime().exec(cmd2); // 레지스트리
				} catch (IOException e1) {
					e1.printStackTrace();
				} // 테스트용으로 주석 처리
			}
		}
	}
}

class MultiThread_python extends Thread {
	static int flag = 0;

	public void run() {
		try (ServerSocket server = new ServerSocket(8077)) {
			// while(Lock.isLock) {
			try {
				Socket connection = server.accept();
				Thread task = new Check(connection);
				/// Thread task2 = new SendT(connection);
				task.start();
				// task2.start();
			} catch (IOException e) {

			} catch (Exception e) {

			}
			// }

			// server.close();
		} catch (IOException e) {
			System.err.println("연결 에러.");
		}
	}

	/*
	 * private static class SendT extends Thread { private Socket connection;
	 * SendT(Socket connection){ this.connection = connection; } public void run() {
	 * int i = 1; while (true) { try { if(Lock.isLock == false) { BufferedWriter out
	 * = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
	 * out.write("T");// out.flush(); System.out.println("보냄 T 파이썬종료");
	 * Thread.sleep(1000); connection.close(); break; } else { BufferedWriter out =
	 * new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
	 * if(i == 1) { i = 0; out.write("S");// } else { i = 1; out.write("F");// }
	 * out.flush(); //System.out.println("보냄 F 파이썬작동중"); } Thread.sleep(1000);
	 * }catch (Exception e) { e.printStackTrace(); } } } }
	 */
	private static class Check extends Thread {
		private Socket connection;

		Check(Socket connection) {
			this.connection = connection;
		}

		public void run() {

			try {
				// Lock.connection = connection;
				while (Lock.isLock) {

					// BufferedWriter out = new BufferedWriter(new
					// OutputStreamWriter(connection.getOutputStream()));
					// out.write("F");//
					// out.flush();
					// System.out.println("보냄 F 파이썬작동중");

					// PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					System.out.println("기다리는중");
					String rev = in.readLine();
					System.out.print("받음 " + rev);

					// out.println(rev);
					// System.out.println("Send : "+rev);

					int i = Integer.parseInt(rev);
					if (i == 10) { // lock on
						flag = 1;
						System.out.println("  등록된사용자없음");
					} else if (i == 20) { // lock off
						flag = 2;
						System.out.println(" 등록된사용자인식");
					}

					// in.close();
					// out.close();

					// sleep(300);
				}
				// if (!Lock.isLock) {
				// BufferedWriter out = new BufferedWriter(new
				// OutputStreamWriter(connection.getOutputStream()));
				// out.write("T");// 파이썬으로 잠금풀렸다고 보낸다
				// out.flush();
				// System.out.println("보냄 T 파이썬종료");
				// }
				// connection.close();
			} catch (IOException e) {
				System.out.println("IOEx_Mul_py_Ch");
				e.printStackTrace();
			} // catch (InterruptedException e) {
				// e.printStackTrace();
				// }
		}
	}
}

class MultiThread_c extends Thread {
	public void run() {
		try (ServerSocket server = new ServerSocket(8078)) {
			while (Lock.isLock) {
				try {
					Socket connection = server.accept();
					Thread task = new Check(connection);
					task.start();///////////////////////////////////
				} catch (IOException e) {
				}
			}
			server.close();
		} catch (IOException e) {
			System.err.println("연결 에러2.");
		}
	}

	private static class Check extends Thread {
		private Socket connection;
		String send;

		Check(Socket connection) {
			this.connection = connection;
		}

		public void run() {
			try {
				while (Lock.isLock) {
					Thread.sleep(1000);
					PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
					if (MultiThread_python.flag == 2)
						send = "20";
					else if (MultiThread_python.flag == 1)
						send = "10";
					out.println(send);
					// out.close();
				}
				connection.close();
			} catch (IOException e) {
				System.out.println("IOEx_Mul_c_Ch");
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class ImagePanel extends JPanel {

	private Image img;

	public ImagePanel(Image img) {

		this.img = img;

		// setSize(new Dimension(img.getWidth(null), img.getHeight(null)));

		// setSize(new Dimension(680, 480));

		// setLayout(null);

		// setBounds(280, 240, 680, 480);

		setBounds(0, 0, 680, 480);

	}

	public void paintComponent(Graphics g) {

		g.drawImage(img, 0, 0, null);

	}

}
