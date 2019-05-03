package java1001;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecordServer {
	public static void main(String[] args) throws IOException {
		
	ServerSocket server = new ServerSocket(12345);
		
		while(true) {
			
			final Socket socket = server.accept();						//接受客户端的请求
			new Thread() {
				public void run() {
					try {
						BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));	//将字节流包装成了字符流
						PrintStream ps = new PrintStream(socket.getOutputStream());	
						while(true)//PrintStream中有写出换行的方法
						{
							String a=br.readLine();
							System.out.println("服务器:"+a);
							
							if (a.equals("quit0")){//退出该线程
								break;						
							}else if (a.equals("one0")){//读取最近一条记录
								ArrayList<String> arrayList = new ArrayList<>();
								FileReader fr = new FileReader("log.txt");
								BufferedReader bf = new BufferedReader(fr);
								String str;
								// 按行读取字符串
								while ((str = bf.readLine()) != null) {
									arrayList.add(str);
								}
								bf.close();
								fr.close();
								ps.println(arrayList.get(arrayList.size()-1));
								}
							else if (a.equals("five0")) {//读取最近五条
								ArrayList<String> arrayList = new ArrayList<>();
								FileReader fr = new FileReader("log.txt");
								BufferedReader bf = new BufferedReader(fr);
								String str;
								// 按行读取字符串
								while ((str = bf.readLine()) != null) {
									arrayList.add(str);
								}
								bf.close();
								fr.close();
								for(int i =0;i<5;i++){
									ps.println(arrayList.get(arrayList.size()-1-4+i));
								}
								
								
							}
							else if (a.equals("all0")){//读取全部
								
							}
							else{
								FileWriter fw= new FileWriter("log.txt",true);
								PrintWriter pw = new PrintWriter(fw);
								Date d = new Date();						//创建当前日期对象
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
								pw.println(sdf.format(d)+":"+a);
								pw.close();
							}
							System.out.println(this.getName());
						}
						socket.close();

					} catch (IOException e) {
						
						e.printStackTrace();
					}
				}
			}.start();
		}
	}

	
}
