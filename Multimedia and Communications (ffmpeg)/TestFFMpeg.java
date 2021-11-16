package polimesa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;

import java.awt.BorderLayout;
import java.io.File; 

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

public class TestFFMpeg {

//initialize some arguments that we will use and want every listener on some buttons to see	
public static String input_name="skoupidia";	
public static String input_choice;	
public static String input_rate;	


	public static void main(String[] args) {
		
		
		//about gui	( creating an instanse of jframe, sizing it and putting it in the middle of the screen )
		JFrame f=new JFrame();   
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(400,400);   
		f.setVisible(true);   
		f.setLocationRelativeTo(null); 
        f.setTitle("Streaming Director");

		
		//when we press the button to choose file( implemented later on the code ), it will open in a specific directory		
		JFileChooser j = new JFileChooser("E:\\eclipse-workspace\\polimesa\\raw_videos\\");
		//create the button to choose file			
		JButton btn = new JButton("choose file");
		btn.setSize(100,100);
		btn.setVisible(true);
		//add a listener to the button so when we click on it it will get the name of the file 
		btn.addActionListener(e ->{
	        j.showSaveDialog(null); 
	        input_name = j.getSelectedFile().getName();
		});
	
		//dropdown options to choose the format	
		JLabel ltype = new JLabel("select type");
		String choices[]= {
				"avi",
				"mp4",
				"mkv"
		};
		JComboBox cb = new JComboBox(choices);
		cb.setSize(100,100);
		cb.setVisible(true);
		
		//dropdown options to choose the Mbps
		JLabel lmbps = new JLabel("select type");
		String choices2[]= {
				"0.2Mbps",
				"0.5Mbps",
				"1Mbps",
				"3Mbps"
		};
		JComboBox cb2 = new JComboBox(choices2);
		cb.setSize(100,100);
		cb.setVisible(true);
		
		//button to click and convert the original file to the liking of the user depended by his options ( implemented later on the code )		
		JButton btn2 = new JButton("convert");
		btn2.setSize(100,100);
		btn2.setVisible(true);
		
		
		//we put all this(buttons and dropdown lists) to the frame		
		JPanel panel = new JPanel();
	    panel.setBorder(BorderFactory.createEmptyBorder(100, 10, 10, 10));
		f.add(panel);
		panel.add(btn);		
		panel.add(cb);
		panel.add(cb2);
		panel.add(btn2);
		

		//listener for the "on click" of the CONVERT button ( btn2 )
		//we get the parameters ( format, bitrate ) the user selected and we apply them to the ffmpegbuilder
		btn2.addActionListener(e -> {
		
			input_choice = String.valueOf(cb.getSelectedItem());
			input_rate = String.valueOf((cb2).getSelectedItem());
			
			//if no file is selected, an alert windows shows up
	         if(input_name.equals("skoupidia")) {        	 
	        	 JOptionPane.showMessageDialog(null, "Error...\n Select a file.");    	 
	         }

	        //setting up the directories for the input and output of ffmpeg etc. 
	        String inputDir = "E:\\eclipse-workspace\\polimesa\\raw_videos\\";
	     	String outDir = "E:\\eclipse-workspace\\polimesa\\videos\\";
	     	
	     	FFmpeg ffmpeg = null;
	     	FFprobe ffprobe = null;
	     	
	     	try {
	     		ffmpeg = new FFmpeg("E:\\ffmpeg\\bin\\ffmpeg.exe");
	     		ffprobe = new FFprobe("E:\\ffmpeg\\bin\\ffprobe.exe");

	     	} catch (IOException ex) {
	     		ex.printStackTrace();
	     	}
	     	
	     	
	     	// as mentioned in the .doc file
	     	//for the avi format we change the resolution to match the bitrate
	     	//tested on the ffmpeg program in cmd first
	     	//quaranted the desired bitrate ( +- 10% deviation )
	     	
	     	//if the user wants 200Kbps and .avi format. really simple using ffmpegbuilder
	     	//we also change the output name to match the user options 
	     	if(input_rate.equals("0.2Mbps") && (input_choice.equals("avi")) )	{
	     		
	     		FFmpegBuilder builder = new FFmpegBuilder()
	     		     	.setInput(inputDir + input_name)
	     		     	.overrideOutputFiles(true)
	     		     	.addOutput(outDir + input_name.replaceAll("\\..+", "")+ input_rate+ "."+ input_choice)
	     		     	//.setVideoBitRate(200000)
	     		     	.setVideoResolution(400, 200)
	     		     	.done();
	     		     	FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
	     		     	executor.createJob(builder).run();
	     		        
	     	}
	     	

	     	//as before we follow the same steps
	     	if(input_rate.equals("0.5Mbps") && (input_choice.equals("avi")) )	{
	     		
	     		FFmpegBuilder builder = new FFmpegBuilder()
	     		     	.setInput(inputDir + input_name)
	     		     	.overrideOutputFiles(true)
	     		     	.addOutput(outDir + input_name.replaceAll("\\..+", "")+ input_rate+ "."+ input_choice)
	     		     	.setVideoResolution(720, 500)
	     		     	.done();
	     		     	FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
	     		     	executor.createJob(builder).run();     		         
	     		        
	     	}
	     	   	
	     	if(input_rate.equals("1Mbps") && (input_choice.equals("avi")) )	{
	     		
	     		FFmpegBuilder builder = new FFmpegBuilder()
	     		     	.setInput(inputDir + input_name)
	     		     	.overrideOutputFiles(true)
	     		     	.addOutput(outDir + input_name.replaceAll("\\..+", "")+ input_rate+ "."+ input_choice)
	     		     	.setVideoResolution(1024, 768)
	     		     	.done();
	     		     	FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
	     		     	executor.createJob(builder).run();	     		         
	     		        
	     	}
	     	
	     	if(input_rate.equals("3Mbps") && (input_choice.equals("avi")) )	{	     		
	     		
	     		FFmpegBuilder builder = new FFmpegBuilder()
	     		     	.setInput(inputDir + input_name)
	     		     	.overrideOutputFiles(true)
	     		     	.addOutput(outDir + input_name.replaceAll("\\..+", "")+ input_rate+ "."+ input_choice)
	     		     	.setVideoResolution(1920, 1080)
	     		     	//.setVideoBitRate(bit_rate)
	     		     	.done();
	     		     	FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
	     		     	executor.createJob(builder).run();
	     		              		        
	     	}
	     	
	     	// as mentioned in the .doc file
	     	//for the .mp4 and .mkv 
	     	//we change the bitrate using the .setVideoBitRate not using the .setVideoResolution as we used before 
	     	//same procedure. we take the users options and convert the files
	     	
	     	if(input_rate.equals("0.2Mbps") && ( (input_choice.equals("mp4") || (input_choice.equals("mkv")) ) )	) {	     		
	     		
	     		FFmpegBuilder builder = new FFmpegBuilder()
	     		     	.setInput(inputDir + input_name)
	     		     	.overrideOutputFiles(true)
	     		     	.addOutput(outDir + input_name.replaceAll("\\..+", "")+ input_rate+ "."+ input_choice)
	     		     	.setVideoBitRate(200000)
	     		     	.done();
	     		     	FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
	     		     	executor.createJob(builder).run();	     		         
	     		        
	     	}

	     	if(input_rate.equals("0.5Mbps") && ( (input_choice.equals("mp4") || (input_choice.equals("mkv")) ) )	) {
	     		
	     		FFmpegBuilder builder = new FFmpegBuilder()
	     		     	.setInput(inputDir + input_name)
	     		     	.overrideOutputFiles(true)
	     		     	.addOutput(outDir + input_name.replaceAll("\\..+", "")+ input_rate+ "."+ input_choice)
	     		     	.setVideoBitRate(500000)
	     		     	.done();
	     		     	FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
	     		     	executor.createJob(builder).run();     		         
	     		        
	     	}
	     	   	
	     	if(input_rate.equals("1Mbps") && ( (input_choice.equals("mp4") || (input_choice.equals("mkv")) ) )	) {     		
	     		
	     		FFmpegBuilder builder = new FFmpegBuilder()
	     		     	.setInput(inputDir + input_name)
	     		     	.overrideOutputFiles(true)
	     		     	.addOutput(outDir + input_name.replaceAll("\\..+", "")+ input_rate+ "."+ input_choice)
	     		     	.setVideoBitRate(1000000)
	     		     	.done();
	     		     	FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
	     		     	executor.createJob(builder).run();
	     		              		        
	     	}
	     	
	     	if(input_rate.equals("3Mbps") && ( (input_choice.equals("mp4") || (input_choice.equals("mkv")) ) )	) {
	     		
	     		
	     		FFmpegBuilder builder = new FFmpegBuilder()
	     		     	.setInput(inputDir + input_name)
	     		     	.overrideOutputFiles(true)
	     		     	.addOutput(outDir + input_name.replaceAll("\\..+", "")+ input_rate+ "."+ input_choice)
	     		     	.setVideoBitRate(3000000)
	     		     	.done();
	     		     	FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
	     		     	executor.createJob(builder).run();     		         
	     		        
	     	}

	        });
	

		//last button to close the application after deleting the video we used from raw_videos
		JButton btn3 = new JButton("end");
		btn3.setSize(100,100);
		btn3.setVisible(true);
		panel.add(btn3);
		
		btn3.addActionListener(exx ->{
			
			Path path = Paths.get("E:\\eclipse-workspace\\polimesa\\raw_videos\\"+input_name);
	     	try {
				Files.delete(path);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	     	
	     	System.exit(0);
		});
		
	}
	
}