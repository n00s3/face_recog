package test;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Crypto3{

	public JFileChooser jfc = new JFileChooser();
	private JButton jbt_chk = new JButton("확인");
	private JPanel panel2 = new JPanel();
	private JTextField pwField = new JTextField(20);
	public static Crypto3 window = new Crypto3();
	public JFrame frame = new JFrame();

	public Crypto3() {
		this.init();
		this.start();
	}

	public void init() {
		panel2.add(pwField);
		panel2.add(jbt_chk);
		// add(panel2, BorderLayout.SOUTH);
	}

	public void start() {
		jfc.setFileFilter(null);// new FileNameExtensionFilter("txt", "txt"));
		// 파일 필터
		jfc.setMultiSelectionEnabled(true);// 다중 선택
	}

	public void createPwTxt() {
		String pw = pwField.getText();
		if (pw.equals(""))
			return;
		FileWriter fw;
		try {
			fw = new FileWriter("C:\\Project\\crypto\\pw.txt");
			fw.write(pw);// 비밀번호 텍스트 생성
			fw.close();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

	public void pwFrame(int v) {
		GuiPw.window.v = v;
		GuiPw.window.frame.setVisible(true);
		
		/*
		JFrame frame = new JFrame("비번을 입력하시오");
		frame.add(panel2);
		frame.setSize(300, 200);
		frame.setVisible(true);
		jbt_chk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				if(v == 1) {
					String[] temp = null;
					for (File f : jfc.getSelectedFiles()) {// 선택된 파일을 옮긴다
						// System.out.println(f.toString());
						temp = f.toString().split("\\\\");
						String fileName = temp[temp.length - 1];
						f.renameTo(new File("C:\\Project\\crypto\\_plain\\" + fileName));
					}
					createPwTxt();
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
					for (File f : jfc.getSelectedFiles()) {// 선택된 파일을 옮긴다
						System.out.println(f.toString());
						temp = f.toString().split("\\\\");
						String fileName = temp[temp.length - 1];
						f.renameTo(new File("C:\\Project\\crypto\\_encoded\\" + fileName));
					}
					createPwTxt();
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
		*/
	}
}
