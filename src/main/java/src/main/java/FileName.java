package src.main.java;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;  

public class FileName {
	
	private String name;
	private DateTimeFormatter dtf;
	private LocalDateTime now;
	private File fileWAV;
	
	public FileName() {
		   dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmmss");  
		   now = LocalDateTime.now(); 
		   name = "Recordings/" + dtf.format(now) + ".WAV";
		   fileWAV = new File(name);
		   try {
			boolean result = fileWAV.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getFileName() {
		return name;
	}
	
	public File getFile() {
		return fileWAV;
	}
	

}
