package java1001;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RcordClient extends Frame{
	private TextField tf;
	private Button send;
	private Button log;
	private Button one;
	private Button five;
	private TextArea viewText;
	private TextArea sendText;
	private OutputStream os; 
	private InputStream is;
	private Socket socket;
	private String ip;
	private int port;
	private PrintStream ps;
	private BufferedReader br ;

	

	public RcordClient() throws UnknownHostException, IOException {
		init();//�ʼ�����
		southPanel();//��������
		centerPanel();//��������
		value1();//ΪһЩ˽�б�����ֵ
		event();//�¼�
		
	}

	private void value1(){
		ip = tf.getText();					//��ȡip��ַ;
		ip = ip.trim().length() == 0 ? "255.255.255.255" : ip;
		port=12345;
		try{
		socket = new Socket(ip, port); //����һ��ʼ�ͽ���socketͨѶ��
		br= new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
		ps=new PrintStream(socket.getOutputStream());
		}catch(Exception e ){
			viewText.append("������û�н���"+"\r\n\r\n");
		}
	}

	private void event() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(socket!=null){
				try {
					ps.println("quit0");
					ps.close();
					br.close();
					socket.close();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}

				System.exit(0);
				
			}
		});
		
		
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					send();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			}

		});
		
		one.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {	
			        ps.println("one0");	
					viewText.append(br.readLine()+"\r\n\r\n");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			

			}

		});
		
		
		five.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {	
			        ps.println("five0");	
					for(int i=0;i<5;i++){
						viewText.append(br.readLine()+"\r\n\r\n");
					}
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			

			}

		});
		
		
		log.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				viewText.setText("");
			}
		});
	}

	private void init() {
		this.setLocation(500, 50);
		this.setSize(800, 600);
		this.setVisible(true);
		
	}
	
	public void southPanel() {
		Panel south = new Panel();					//�����ϱߵ�Panel
		tf = new TextField(15);
		tf.setText("127.0.0.1");//�޸����IP��ַ
		send = new Button("�� ��");
		log = new Button("����");
		one = new Button("һ��");
		five = new Button("����");
		south.add(tf);
		south.add(send);
		south.add(log);
		south.add(one);
		south.add(five);
		this.add(south,BorderLayout.SOUTH);			//��Panel����Frame���ϱ�
	}
	public void centerPanel() {
		Panel center = new Panel();					//�����м��Panel
		viewText = new TextArea();
		sendText = new TextArea(5,1);
		center.setLayout(new BorderLayout());		//����Ϊ�߽粼�ֹ�����
		center.add(sendText,BorderLayout.SOUTH);	//���͵��ı���������ϱ�
		center.add(viewText,BorderLayout.CENTER);	//��ʾ��������м�
		viewText.setEditable(false);				//���ò����Ա༭
		viewText.setBackground(Color.WHITE);		//���ñ�����ɫ
		sendText.setFont(new Font("xxx", Font.PLAIN, 15));
		viewText.setFont(new Font("xxx", Font.PLAIN, 15));
		this.add(center,BorderLayout.CENTER);
	}
	
	
	
	private void send() throws IOException {
		String time = getCurrentTime();				//��ȡ��ǰʱ��
		String message = time+":"+sendText.getText();		//��ȡ�������������
		
		
		send(message.getBytes(),ip);
		
	
		String str = time + (ip.equals("255.255.255.255") ? "������" : ip) + "˵\r\n" + message + "\r\n\r\n";	//alt + shift + l ��ȡ�ֲ�����
		viewText.append(str);						//����Ϣ��ӵ���ʾ������
		sendText.setText("");
		
		
		
	}
	
	
	private void send(byte[] arr, String ip) throws IOException {
                 //��ȡ�����
		System.out.println();
        ps.println(new String(arr));	
        
		
	}
	
	private String getCurrentTime() {
		Date d = new Date();						//������ǰ���ڶ���
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");
		return sdf.format(d);						//��ʱ���ʽ��
	}
	
 public static void main(String[] args) throws UnknownHostException, IOException {
	new RcordClient();
 }
}
