package test;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

class User {
	static private String name;
	static private String path = "C:\\Project\\images\\";	//picture path
	static private String command = "python C:\\Project\\bin\\capture.py";	
	static private String commandArg = "python C:\\Project\\bin\\capture.py";	
	
	static public int check_file() {
		String temp_path = path+name+".jpg";
		File file = new File(temp_path);
		
		if(file.exists()) {
			System.out.println("�̹� ���� �̸��� ����ڰ� �����մϴ�.");
			return -1;
		}
		return 0;
	}
	
	static public int useradd(String name) throws IOException, InterruptedException {
		
		
		User.setUser(name);
		
		if (check_file() == 0) {
			try { 
				Lock.stopLock();
				Thread.sleep(1500);
				System.out.println("ĸó����");
				Process p = Runtime.getRuntime().exec(commandArg); 
				p.waitFor();
			}	
			catch (IOException e) { 
				e.printStackTrace(); 
				return -1;	//��� ����
			}
    	}
		else {
			return -1;
		}
		return 0;	//���� ���
	}
	
	static public void setUser(String name) {
		User.name = name;
		User.commandArg = command.concat(" "+name);
	}
	
	
	public static ArrayList<String> getUserList() {
		File file = new File(path);
		File[] listFiles = file.listFiles();
		
		ArrayList<String> names = new ArrayList<String>();//���⿡ �̸��� ��´�
		String name;
		
		//System.out.println("###����ڸ��###");
		for(File f : listFiles) {
			if(f.isFile()) {
				String fileName = f.getName();
				if(fileName.contains(".png") || fileName.contains(".jpg")) {//�̹����� 
					name = fileName.split("\\.")[0];
					names.add(name);//����Ʈ�� �̸� �߰�
					
					
					//System.out.println(name);
					//�̹��������� �̸��� ���   ��, ��������
				}
			}
		}
		return names;//����Ʈ ���
	}
	
	
	public static int delUser(String name) {
		File file = new File(path);
		File[] listFiles = file.listFiles();
		
		for(File f : listFiles) {
			
			if(f.isFile()) {//������ �ƴϰ� �����ΰ�
				String fileName = f.getName();
				
				if(fileName.contains(".png") || fileName.contains(".jpg")) {//�̹����ΰ� 
					
					if(fileName.indexOf(name) != -1) {//xxx.jpg �� name�� ������ 
						f.delete();
						
						//System.out.println(name + "����ڰ� �����Ǿ����ϴ�.");
						return 0;
					}
				}
			}
		}
		
		//System.out.println("������ ���ų� �������� �ʾҽ��ϴ�.");
		return -1;
	}
	
}