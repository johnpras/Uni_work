package polimesa;

import java.awt.BorderLayout;
import java.awt.Label;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.lang.ProcessBuilder;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.io.FileUtils;

import java.nio.file.StandardCopyOption.*;


public class Server {

	private static ServerSocket ss;
	private static Socket s;
	private static int port = 5000;
	private static DataInputStream din;
	private static DataOutputStream dout;
	private static String filetodel="";
	
	public static void main(String[] args) throws Exception{
		
		File ff = new File("E:\\eclipse-workspace\\polimesa\\videos\\");
	    String[] filenames;
	    String[] datafromclient;
	    filenames = ff.list(); 	//παίρνω τα ονόματα των αρχείων που βρίσκονται στο φάκελο videos.   
	    List<String> list1 = new ArrayList<String>(); 
	     
	    //περί gui
        JFrame frame = new JFrame();
        frame.setSize(400,400);//400 width and 500 height  
		frame.setVisible(true);//making the frame visible  
		frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
        frame.setTitle("Server");
        
        Label l1 = new Label();
        l1.setBounds(50,50, 100, 30);
        
        JTextArea tarea = new JTextArea(10, 30);
                
        JPanel panel = new JPanel();        
        panel.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));
		frame.add(panel);
		panel.add(tarea, BorderLayout.CENTER);	
		panel.add(l1);		
		
		//περί connection socket κτλπ
		ServerSocket ss=new ServerSocket(port);  
		Socket s=ss.accept();  
		DataInputStream din=new DataInputStream(s.getInputStream());  
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in)); 
		// όπου strC αυτό που παίρνει απο client, strS στέλνει ο Server
		String strC="",strS="";  
		
//filenamestoprint λίστα με ονόματα αρχείων στο videos πιο κομψό, onomata είναι το τι θα στείλει στον client μετά από τις επιλογές που θα κάνει
String onomata="";
String filenamestoprint = Arrays.toString(filenames).replace(",", "\n").replace("[", "").replace("]", "").trim();

//εν τέλη μόνο το πρώτο χρησιμοποιώ, αλλά έτσι είχα σκεφτεί να το πάω
String[] commands= {"Dwse bitrate kai format *hint: XMbps,_format or 1Mbps, avi\nExw ta parakatw arxeia \n"+filenamestoprint, "dialexe onoma \n"+onomata, "dialexe protocol", "streamarw" };
	
//δεν μας νοιάζει τόσο, μια φορά θα χρησιμοποιηθεί
	int j=0;	
			
//(ρωτάει ο server)δώσε bitrate και format και του δείχνει τα αρχεία(την κατεβατή λίστα με όλα τα ονόματα των βίντεο)
			strS = commands[j];
			dout.writeUTF(strS); 
			dout.flush();
			j++;
			
//διαβάζει ο server bitrate και format		
			strC=din.readUTF();  
			tarea.setText("client says: "+strC);
			System.out.println("client says: "+tarea.getText());			
			
//παίρνει ο server τα δεδομένα του client και μπαίνουν σε ένα string[] για να τα χειριστώ αργότερα	
			datafromclient = strC.split(", ");	
			
//ώστε αν πχ είναι avi το format:			
			if(datafromclient[1].contains("avi")) {
//μετά κοιτάω το bitrate				
				if(datafromclient[0].equals("0.2Mbps")) {	
					for(int i=0; i<filenames.length; i++){
						 if(filenames[i].contains("0.2Mbps.avi")) {
							 	list1.add(filenames[i]);		//προσθέτει σε μια λίστα τα ονόματα των αρχείων που κάνουν match με αυτά που θέλει
						 }
					 }			
				}
				if(datafromclient[0].contains("0.5Mbps")) {				
					for(int i=0; i<filenames.length; i++){
						 if(filenames[i].contains(("0.5Mbps.avi")) || filenames[i].contains("0.2Mbps.avi")) {
							 	list1.add(filenames[i]);
						 }
					 }
				}
				if(datafromclient[0].contains("1Mbps")) {			
					for(int i=0; i<filenames.length; i++){
						 if(filenames[i].contains(("0.5Mbps.avi")) || filenames[i].contains("0.2Mbps.avi") || filenames[i].contains("1Mbps.avi")) {
							 	list1.add(filenames[i]);
						 }
					 }
				}
				if(datafromclient[0].contains("3Mbps")) {			
					for(int i=0; i<filenames.length; i++){
						 if(filenames[i].contains(("0.5Mbps.avi")) || filenames[i].contains("0.2Mbps.avi") || filenames[i].contains("1Mbps.avi") || filenames[i].contains("3Mbps.avi")) {
							 	list1.add(filenames[i]);
						 }
					 }	
				}	
//(ρωτάει ο server) να διαλέξει όνομα ο client	από τη νέα λίστα με ονόματα αρχείων	
				onomata=String.join(", ", list1);
				strS= "dialexe onoma \n"+onomata;
				dout.writeUTF(strS); 
				dout.flush();
				j++;
				
//διαβάζω την απάντηση του client και την τοποθετώ στο "nameofchosenvideo"
				strC=din.readUTF();  
				tarea.setText("client says: "+strC);
				System.out.println("client says: "+tarea.getText());
				String nameofchosenvideo = strC;
//αν αυτό που έδωσε ο client βρίσκεται στην λίστα, δηλαδή δε διάλεξε κάτι από τη φαντασία του ή δεν έκανε τυπογραφικό (δεν ελέγχω σε περίπτωση λάθους )				
				if(list1.contains(nameofchosenvideo)){
//ρωτάει τον client να δώσει πρωτόκολλο					
					strS = "Dwse prwtokollo";
					dout.writeUTF(strS); 
					dout.flush(); 
//αποθηκεύει το πρωτόκολλο σε μια μεταβλητή		
					strC=din.readUTF();  
					tarea.setText("client says: "+strC);
					System.out.println("client says: "+tarea.getText());
					String nameofchosenprotocol =strC;
//στέλνει στον client ότι κάνει streaming
					strS = "streaming";
					dout.writeUTF(strS); 
					dout.flush(); 
//αν διάλεξε tcp					
					if(nameofchosenprotocol.contains("tcp")) {	
/* κανει stream*/		Process process = new ProcessBuilder("cmd.exe", "/k", "cd E:\\ffmpeg\\bin\\ && ffmpeg -re -i "+" E:\\eclipse-workspace\\polimesa\\videos\\"+nameofchosenvideo+" -f avi tcp://127.0.0.1:1234?listen").start();
/* η udp κ.ο.κ*/	} else if (nameofchosenprotocol.contains("udp")) {
			     		Process process = new ProcessBuilder("cmd.exe", "/k", "cd E:\\ffmpeg\\bin\\ && ffmpeg -re -i "+" E:\\eclipse-workspace\\polimesa\\videos\\"+nameofchosenvideo+" -f avi udp://127.0.0.1:1234").start();
					} else if (nameofchosenprotocol.contains("rtp")) {
			     		Process process = new ProcessBuilder("cmd.exe", "/k", "cd E:\\ffmpeg\\bin\\ && ffmpeg -re -i "+" E:\\eclipse-workspace\\polimesa\\videos\\"+nameofchosenvideo+" -f rtp -sdp_file video.sdp \"rtp://127.0.0.1:1234\"").start();
					} //to sdp video βρίσκεται στο φάκελο που είναι εγκατεστημένο το ffmpeg
				}
			}
//ακριβώς ίδια διαδικασία για mp4 ...		
			else if(datafromclient[1].contains("mp4")) {
				
				if(datafromclient[0].equals("0.2Mbps")) {	
					for(int i=0; i<filenames.length; i++){
						 if(filenames[i].contains("0.2Mbps.mp4")) {
							 	list1.add(filenames[i]);
						 }
					 }			
				}
				if(datafromclient[0].contains("0.5Mbps")) {				
					for(int i=0; i<filenames.length; i++){
						 if(filenames[i].contains(("0.5Mbps.mp4")) || filenames[i].contains("0.2Mbps.mp4")) {
							 	list1.add(filenames[i]);
						 }
					 }
					
				}
				if(datafromclient[0].contains("1Mbps")) {			
					for(int i=0; i<filenames.length; i++){
						 if(filenames[i].contains(("0.5Mbps.mp4")) || filenames[i].contains("0.2Mbps.mp4") || filenames[i].contains("1Mbps.mp4")) {
							 	list1.add(filenames[i]);
						 }
					 }
				}
				if(datafromclient[0].contains("3Mbps")) {			
					for(int i=0; i<filenames.length; i++){
						 if(filenames[i].contains(("0.5Mbps.mp4")) || filenames[i].contains("0.2Mbps.mp4") || filenames[i].contains("1Mbps.mp4") || filenames[i].contains("3Mbps.mp4")) {
							 	list1.add(filenames[i]);
						 }
					 }	
				}
	
				onomata=String.join(", ", list1);
				strS= "dialexe onoma \n"+onomata;
				dout.writeUTF(strS); 
				dout.flush();
				j++;
				
				strC=din.readUTF();  
				tarea.setText("client says: "+strC);
				System.out.println("client says: "+tarea.getText());
				String nameofchosenvideo = strC;
				
				if(list1.contains(nameofchosenvideo)){
					
					strS = "Dwse prwtokollo";
					dout.writeUTF(strS); 
					dout.flush(); 
									
					strC=din.readUTF();  
					tarea.setText("client says: "+strC);
					System.out.println("client says: "+tarea.getText());
					String nameofchosenprotocol =strC;
					
					strS = "streaming";
					dout.writeUTF(strS); 
					dout.flush(); 
					
					if(nameofchosenprotocol.contains("tcp")) {
			     		Process process = new ProcessBuilder("cmd.exe", "/k", "cd E:\\ffmpeg\\bin\\ && ffmpeg -re -i "+" E:\\eclipse-workspace\\polimesa\\videos\\"+nameofchosenvideo+" -f avi tcp://127.0.0.1:1234?listen").start();
					} else if (nameofchosenprotocol.contains("udp")) {
			     		Process process = new ProcessBuilder("cmd.exe", "/k", "cd E:\\ffmpeg\\bin\\ && ffmpeg -re -i "+" E:\\eclipse-workspace\\polimesa\\videos\\"+nameofchosenvideo+" -f avi udp://127.0.0.1:1234").start();
					} else if (nameofchosenprotocol.contains("rtp")) {
			     		Process process = new ProcessBuilder("cmd.exe", "/k", "cd E:\\ffmpeg\\bin\\ && ffmpeg -re -i "+" E:\\eclipse-workspace\\polimesa\\videos\\"+nameofchosenvideo+" -f rtp -sdp_file video.sdp \"rtp://127.0.0.1:1234\"").start();
					}
				}
			}
//και για mkv			
			else if(datafromclient[1].contains("mkv")) {
				
				if(datafromclient[0].equals("0.2Mbps")) {	
					for(int i=0; i<filenames.length; i++){
						 if(filenames[i].contains("0.2Mbps.mkv")) {
							 	list1.add(filenames[i]);
						 }
					 }			
				}
				if(datafromclient[0].contains("0.5Mbps")) {				
					for(int i=0; i<filenames.length; i++){
						 if(filenames[i].contains(("0.5Mbps.mkv")) || filenames[i].contains("0.2Mbps.mkv")) {
							 	list1.add(filenames[i]);
						 }
					 }				
				}
				if(datafromclient[0].contains("1Mbps")) {			
					for(int i=0; i<filenames.length; i++){
						 if(filenames[i].contains(("0.5Mbps.mkv")) || filenames[i].contains("0.2Mbps.mkv") || filenames[i].contains("1Mbps.mkv")) {
							 	list1.add(filenames[i]);
						 }
					 }
				}
				if(datafromclient[0].contains("3Mbps")) {			
					for(int i=0; i<filenames.length; i++){
						 if(filenames[i].contains(("0.5Mbps.mkv")) || filenames[i].contains("0.2Mbps.mkv") || filenames[i].contains("1Mbps.mkv") || filenames[i].contains("3Mbps.mkv")) {
							 	list1.add(filenames[i]);
						 }
					 }	
				}			
		
				onomata=String.join(", ", list1);
				strS= "dialexe onoma \n"+onomata;
				dout.writeUTF(strS); 
				dout.flush();
				j++;
				
				strC=din.readUTF();  
				tarea.setText("client says: "+strC);
				System.out.println("client says: "+tarea.getText());
				String nameofchosenvideo = strC;
				
				if(list1.contains(nameofchosenvideo)){
					
					strS = "Dwse prwtokollo";
					dout.writeUTF(strS); 
					dout.flush(); 
											
					strC=din.readUTF();  
					tarea.setText("client says: "+strC);
					System.out.println("client says: "+tarea.getText());
					String nameofchosenprotocol =strC;
					
					strS = "streaming";
					dout.writeUTF(strS); 
					dout.flush(); 
					
					if(nameofchosenprotocol.contains("tcp")) {
			     		Process process = new ProcessBuilder("cmd.exe", "/k", "cd E:\\ffmpeg\\bin\\ && ffmpeg -re -i "+" E:\\eclipse-workspace\\polimesa\\videos\\"+nameofchosenvideo+" -f avi tcp://127.0.0.1:1234?listen").start();
					} else if (nameofchosenprotocol.contains("udp")) {
			     		Process process = new ProcessBuilder("cmd.exe", "/k", "cd E:\\ffmpeg\\bin\\ && ffmpeg -re -i "+" E:\\eclipse-workspace\\polimesa\\videos\\"+nameofchosenvideo+" -f avi udp://127.0.0.1:1234").start();
					} else if (nameofchosenprotocol.contains("rtp")) {
			     		Process process = new ProcessBuilder("cmd.exe", "/k", "cd E:\\ffmpeg\\bin\\ && ffmpeg -re -i "+" E:\\eclipse-workspace\\polimesa\\videos\\"+nameofchosenvideo+" -f rtp -sdp_file video.sdp \"rtp://127.0.0.1:1234\"").start();
					} 
				}
			}					
		//dout.close();  
		s.close();  
		ss.close();  	
	}
}

