package Application;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public class Record {
	
	private AudioFormat audioFormat;
	private DataLine.Info dataInfo;
	private TargetDataLine dataLine; 
	private Thread audioInputThread;
	private AudioInputStream recording; 
	private FileName fileName = new FileName();
	
	
	/*
	 * Constructor to create elements needed to record
	 * @param audioFormat dictates formating for recording
	 * @param dataInfo creates a usable list of info for class TargetDataLine
	 * @param dataLine is the data stream to recording
	 * @param audioInputThread allows us to do something else in the program while the recording is runing
	 * @param recording writes the recording to a file using dataLine
	 */
	
	public Record () {
		try {
			audioFormat = new AudioFormat (AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
			
			dataInfo = new DataLine.Info((TargetDataLine.class), audioFormat);
				
			if (!AudioSystem.isLineSupported(dataInfo)) {
				
					System.out.println("Not Supported");
				}
			dataLine = (TargetDataLine)AudioSystem.getLine(dataInfo);
			
			audioInputThread = new Thread() {
				
				@Override public void run () {
				recording = new AudioInputStream(dataLine);
				File outputFile = new File (fileName.getFileName());
				try
				{
					AudioSystem.write(recording, AudioFileFormat.Type.WAVE, outputFile);
					}
				catch (IOException ex)
				{
					System.out.println(ex);
				}
			}
			};
			
		}
		catch(Exception e)
		{
			System.out.println("Not Supported");
		};
	}
	
	//starts the recording
	public void start () {
		try {
		dataLine.open();
		dataLine.start();
		audioInputThread.start();
		}
		catch (Exception e) {
			System.out.print(e);
		}
		
	}
	
	//stops the recording
	public void stop() {
		try {
			dataLine.stop();
			dataLine.close();
		}
		catch(Exception e) {
			System.out.print(e);
		}
	}
	
	public String getFileName() {
		return fileName.getFileName(); 
	}
	
	
	
	

}
