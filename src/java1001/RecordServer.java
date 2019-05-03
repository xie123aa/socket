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
			
			final Socket socket = server.accept();						//���ܿͻ��˵�����
			new Thread() {
				public void run() {
					try {
						BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));	//���ֽ�����װ�����ַ���
						PrintStream ps = new PrintStream(socket.getOutputStream());	
						while(true)//PrintStream����д�����еķ���
						{
							String a=br.readLine();
							System.out.println("������:"+a);
							
							if (a.equals("quit0")){//�˳����߳�
								break;						
							}else if (a.equals("one0")){//��ȡ���һ����¼
								ArrayList<String> arrayList = new ArrayList<>();
								FileReader fr = new FileReader("log.txt");
								BufferedReader bf = new BufferedReader(fr);
								String str;
								// ���ж�ȡ�ַ���
								while ((str = bf.readLine()) != null) {
									arrayList.add(str);
								}
								bf.close();
								fr.close();
								ps.println(arrayList.get(arrayList.size()-1));
								}
							else if (a.equals("five0")) {//��ȡ�������
								ArrayList<String> arrayList = new ArrayList<>();
								FileReader fr = new FileReader("log.txt");
								BufferedReader bf = new BufferedReader(fr);
								String str;
								// ���ж�ȡ�ַ���
								while ((str = bf.readLine()) != null) {
									arrayList.add(str);
								}
								bf.close();
								fr.close();
								for(int i =0;i<5;i++){
									ps.println(arrayList.get(arrayList.size()-1-4+i));
								}
								
								
							}
							else if (a.equals("all0")){//��ȡȫ��
								
							}
							else{
								FileWriter fw= new FileWriter("log.txt",true);
								PrintWriter pw = new PrintWriter(fw);
								Date d = new Date();						//������ǰ���ڶ���
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");
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
