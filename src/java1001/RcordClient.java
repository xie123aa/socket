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
		init();//最开始的面板
		southPanel();//上面的面板
		centerPanel();//上面的面板
		value1();//为一些私有变量赋值
		event();//事件
		
	}

	private void value1(){
		ip = tf.getText();					//获取ip地址;
		ip = ip.trim().length() == 0 ? "255.255.255.255" : ip;
		port=12345;
		try{
		socket = new Socket(ip, port); //程序一开始就建立socket通讯；
		br= new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
		ps=new PrintStream(socket.getOutputStream());
		}catch(Exception e ){
			viewText.append("服务器没有建立"+"\r\n\r\n");
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
		Panel south = new Panel();					//创建南边的Panel
		tf = new TextField(15);
		tf.setText("127.0.0.1");//修改你的IP地址
		send = new Button("发 送");
		log = new Button("清屏");
		one = new Button("一条");
		five = new Button("五条");
		south.add(tf);
		south.add(send);
		south.add(log);
		south.add(one);
		south.add(five);
		this.add(south,BorderLayout.SOUTH);			//将Panel放在Frame的南边
	}
	public void centerPanel() {
		Panel center = new Panel();					//创建中间的Panel
		viewText = new TextArea();
		sendText = new TextArea(5,1);
		center.setLayout(new BorderLayout());		//设置为边界布局管理器
		center.add(sendText,BorderLayout.SOUTH);	//发送的文本区域放在南边
		center.add(viewText,BorderLayout.CENTER);	//显示区域放在中间
		viewText.setEditable(false);				//设置不可以编辑
		viewText.setBackground(Color.WHITE);		//设置背景颜色
		sendText.setFont(new Font("xxx", Font.PLAIN, 15));
		viewText.setFont(new Font("xxx", Font.PLAIN, 15));
		this.add(center,BorderLayout.CENTER);
	}
	
	
	
	private void send() throws IOException {
		String time = getCurrentTime();				//获取当前时间
		String message = time+":"+sendText.getText();		//获取发送区域的内容
		
		
		send(message.getBytes(),ip);
		
	
		String str = time + (ip.equals("255.255.255.255") ? "所有人" : ip) + "说\r\n" + message + "\r\n\r\n";	//alt + shift + l 抽取局部变量
		viewText.append(str);						//将信息添加到显示区域中
		sendText.setText("");
		
		
		
	}
	
	
	private void send(byte[] arr, String ip) throws IOException {
                 //获取输出流
		System.out.println();
        ps.println(new String(arr));	
        
		
	}
	
	private String getCurrentTime() {
		Date d = new Date();						//创建当前日期对象
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		return sdf.format(d);						//将时间格式化
	}
	
 public static void main(String[] args) throws UnknownHostException, IOException {
	new RcordClient();
 }
}
