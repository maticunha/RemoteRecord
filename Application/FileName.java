package Application;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;  

public class FileName {
	
	private String name;
	private DateTimeFormatter dtf;
	private LocalDateTime now;
	
	public FileName() {
		   dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		   now = LocalDateTime.now(); 
		   name = dtf.format(now) + ".WAV";
	}
	
	public String getFileName() {
		return name;
	}
	

}
