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
		
		
		
		
//σχετικά με gui
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
        
//jtextfield αποτυχημένο μεν γιατί υπήρχε bug όταν έπαιρνα το text        
        JTextField tfield = new JTextField("write your text here ");

        JPanel panel = new JPanel();
//επίσης αποτυχημένο button για send αφού υπήρχε θέμα γενικά στο textfield  ( έχει υποθεί στο doc )     
        JButton btn = new JButton("send ");
        
//για να μη επεκτείνεται το textarea ανάλογα με τον όγκο των δεδομένων    
        panel.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));
		frame.add(panel);
		panel.add(tarea, BorderLayout.CENTER);	
		panel.add(l1);
		panel.add(tfield,BorderLayout.WEST);
		panel.add(btn);
		panel.add(sp);
	

//ξεκινάμε με sockets		
		Socket s=new Socket("localhost",port);  
		DataInputStream din=new DataInputStream(s.getInputStream());  
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
		
//strS αυτό που στέλνει ο server
		String strS=""; 
//τι στέλνω εγώ κλασικά διαβάζοντας γραμμή γιατί δεν δούλεψε με το gui
		String toSend="";
		Scanner sc = new Scanner(System.in);
		

//Υπήρχε θέμα με το while οπότε το έκανα 1-1 ερώτηση απάντηση server client		
//στέλνει ο server το κατεβατό με τα αρχεία που έχει και ρωτάει επίσης να δώσεις bitrate+format
			strS=din.readUTF();  
			System.out.println("Server says: "+strS);  
			tarea.setText("Server says: "+strS);
			
//του στέλνω bitrate και format	σε μορφή XMbps,_type
			toSend = sc.nextLine();		
			try {				
				dout.writeUTF(toSend);
				dout.flush(); 					
			} catch (IOException e) {
				e.printStackTrace();
			}
			
					
//μου στέλνει ονόματα αρχείων που ταιριάζουν σε αυτά που θέλω
			strS=din.readUTF();  
			System.out.println("Server says: "+strS);  
			tarea.setText("Server says: "+strS);
			
			
//του στέλνω πιο αρχείο θέλω να δω
			toSend = sc.nextLine();		
			try {
				dout.writeUTF(toSend);
				dout.flush(); 					
			} catch (IOException e) {
				e.printStackTrace();
			}
			
				
//με ρωτάει για πρωτόκολλο
			strS=din.readUTF();  
			System.out.println("Server says: "+strS);  
			tarea.setText("Server says: "+strS);
					
//του απαντάω πρωτόκολλο
			toSend = sc.nextLine();		
			try {
				dout.writeUTF(toSend);
				dout.flush(); 					
			} catch (IOException e) {
				e.printStackTrace();
			}
						
//όλα οκ μέχρι εδώ, δεν έχω άλλα πάρε δώσε και πλέον μπορώ να δω το steam	
//στέλνει ο server την λέξη streaming( αν κάνει stream είμαστε οκ γιατί το ελέγχουμε, αν δεν κάνει stream απλά δεν θα εκτελεστεί ο ffmplay )
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