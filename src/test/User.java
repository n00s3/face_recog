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
			System.out.println("이미 같은 이름의 사용자가 존재합니다.");
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
				System.out.println("캡처실행");
				Process p = Runtime.getRuntime().exec(commandArg); 
				p.waitFor();
			}	
			catch (IOException e) { 
				e.printStackTrace(); 
				return -1;	//등록 에러
			}
    	}
		else {
			return -1;
		}
		return 0;	//정상 등록
	}
	
	static public void setUser(String name) {
		User.name = name;
		User.commandArg = command.concat(" "+name);
	}
	
	
	public static ArrayList<String> getUserList() {
		File file = new File(path);
		File[] listFiles = file.listFiles();
		
		ArrayList<String> names = new ArrayList<String>();//여기에 이름을 담는다
		String name;
		
		//System.out.println("###사용자명단###");
		for(File f : listFiles) {
			if(f.isFile()) {
				String fileName = f.getName();
				if(fileName.contains(".png") || fileName.contains(".jpg")) {//이미지만 
					name = fileName.split("\\.")[0];
					names.add(name);//리스트에 이름 추가
					
					
					//System.out.println(name);
					//이미지파일의 이름만 출력   즉, 사용자출력
				}
			}
		}
		return names;//리스트 출력
	}
	
	
	public static int delUser(String name) {
		File file = new File(path);
		File[] listFiles = file.listFiles();
		
		for(File f : listFiles) {
			
			if(f.isFile()) {//폴더가 아니고 파일인가
				String fileName = f.getName();
				
				if(fileName.contains(".png") || fileName.contains(".jpg")) {//이미지인가 
					
					if(fileName.indexOf(name) != -1) {//xxx.jpg 에 name이 있으면 
						f.delete();
						
						//System.out.println(name + "사용자가 삭제되었습니다.");
						return 0;
					}
				}
			}
		}
		
		//System.out.println("파일이 없거나 삭제되지 않았습니다.");
		return -1;
	}
	
}