package Application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;

public class RecordTest{
	
	public static void main(String[] args) {
		
		try
		{
			AudioFormat audioFormat = new AudioFormat (AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
			
			DataLine.Info dataInfo= new DataLine.Info((TargetDataLine.class), audioFormat);
					if(!AudioSystem.isLineSupported(dataInfo)) 
					{
						System.out.println("Not Supported");
					}
			
			TargetDataLine targetLine = (TargetDataLine)AudioSystem.getLine(dataInfo);
			targetLine.open();
			
			JOptionPane.showMessageDialog(null, "Hit ok to start recording");
			targetLine.start();
		
		
			Thread audioRecorderThread = new Thread() {
			
			@Override public void run()
			{
				AudioInputStream recordingStream = new AudioInputStream(targetLine);
				File outputFile = new File ("C:/Test/TEST.wav");
				try
				{
					AudioSystem.write(recordingStream, AudioFileFormat.Type.WAVE, outputFile);
					}
				catch (IOException ex)
				{
					System.out.println(ex);
				}
				
				System.out.println("Stopped Recording");
			
			}
		
		
			
			
			
		};
		
			audioRecorderThread.start();
			JOptionPane.showMessageDialog(null,"Hit ok to stop recording");
			targetLine.stop();
			targetLine.close();
			
	}
		
		catch(Exception e)
		{
			System.out.println("Not Supported");
		}
		
		
	

}
}
