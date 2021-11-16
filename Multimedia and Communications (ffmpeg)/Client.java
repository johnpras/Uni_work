package polimesa;

import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.lang.ProcessBuilder;
import java.util.Scanner;

public class Client {

	private static int port = 5000;
	public static String textfromgui="";
	
	
	public static void main(String[] args) throws Exception{  
		
		
		
		
		JFrame frame = new JFrame();
        frame.setSize(400,400);//400 width and 500 height  
		frame.setVisible(true);//making the frame visible  
		frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
        frame.setTitle("Client");
        
        Label l1 = new Label();
        l1.setBounds(50,50, 100, 30);
        
        JTextArea tarea = new JTextArea(10, 30);
        tarea.setLineWrap(true);
        JScrollPane sp = new JScrollPane(tarea);
        sp.setBounds(100, 100, 100, 100);
        
      
        JTextField tfield = new JTextField("write your text here ");

        JPanel panel = new JPanel();     
        JButton btn = new JButton("send ");
          
        panel.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));
		frame.add(panel);
		panel.add(tarea, BorderLayout.CENTER);	
		panel.add(l1);
		panel.add(tfield,BorderLayout.WEST);
		panel.add(btn);
		panel.add(sp);
	
		
		Socket s=new Socket("localhost",port);  
		DataInputStream din=new DataInputStream(s.getInputStream());  
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
		
		String strS=""; 
		String toSend="";
		Scanner sc = new Scanner(System.in);
		
		
			strS=din.readUTF();  
			System.out.println("Server says: "+strS);  
			tarea.setText("Server says: "+strS);
			
			toSend = sc.nextLine();		
			try {				
				dout.writeUTF(toSend);
				dout.flush(); 					
			} catch (IOException e) {
				e.printStackTrace();
			}
			
					
			strS=din.readUTF();  
			System.out.println("Server says: "+strS);  
			tarea.setText("Server says: "+strS);
			
			
			toSend = sc.nextLine();		
			try {
				dout.writeUTF(toSend);
				dout.flush(); 					
			} catch (IOException e) {
				e.printStackTrace();
			}
			
				
			strS=din.readUTF();  
			System.out.println("Server says: "+strS);  
			tarea.setText("Server says: "+strS);
					
			toSend = sc.nextLine();		
			try {
				dout.writeUTF(toSend);
				dout.flush(); 					
			} catch (IOException e) {
				e.printStackTrace();
			}
						

			strS=din.readUTF();  
			System.out.println("Server says: "+strS);  
			tarea.setText("Server says: "+strS);
			
			if(strS.contains("streaming")) {
				System.out.println("Server is streaming...Im going to watch now");
				if(toSend.contains("tcp")) {
					Process process2 = new ProcessBuilder("cmd.exe", "/k", "cd E://ffmpeg//bin// && ffplay tcp://127.0.0.1:1234").start();
					//χωρίς system exit δεν τρέχει το ffmplay(???)				
		     		System.exit(0);
				}else if(toSend.contains("udp")) {
					Process process2 = new ProcessBuilder("cmd.exe", "/k", "cd E://ffmpeg//bin// && ffplay udp://127.0.0.1:1234").start();
					//χωρίς system exit δεν τρέχει το ffmplay(???)				
		     		System.exit(0);
				} else {
					Process process2 = new ProcessBuilder("cmd.exe", "/k", "cd E://ffmpeg//bin// && ffplay -protocol_whitelist file,rtp,udp -i video.sdp").start();
					//to sdp video βρίσκεται στο φάκελο που είναι εγκατεστημένο το ffmpeg
					//χωρίς system exit δεν τρέχει το ffmplay(???)				
		     		System.exit(0);
				}
	     	}
		//dout.close();  
		s.close();
	}
}		
