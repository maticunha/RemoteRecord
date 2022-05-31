package Application;
	
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.io.*;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JOptionPane;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.SourceDataLine; 
import javax.sound.sampled.UnsupportedAudioFileException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


public class Interface extends Application{
	
	@FXML
    private Button Record;
	
	@FXML
	private Button Stop;
	
	@FXML
	private Button Preferences;
	
	@FXML
	private Button Play;
	
	@FXML
	private Button About;
	
	@FXML
	private Text RIP;
	
	@FXML
	private Text Label;
	
	Record r1 = new Record();
	
	public void start(Stage primaryStage) {
		try {
			Pane root = (Pane)FXMLLoader.load(getClass().getResource("Design.fxml"));
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void open_preferences(ActionEvent actionEVent) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("Preferences.fxml"));
		
		Scene scene = new Scene(root);
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.show();
	}
	
	public void open_about(ActionEvent actionEVent) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("About.fxml"));
		
		Scene scene = new Scene(root);
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.show();
	}


	public void recording(ActionEvent e) {
		RIP.setFill(Color.RED);
		r1.start();
	}
	
	public void stop(ActionEvent e) {
		RIP.setFill(Color.WHITE);
		RIP.setStyle("-fx-text-fill: #fff2f2");
		r1.stop();
	}
	
	public void play(ActionEvent e) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(r1.getFileName()).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			} catch(Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
			}
	}
}
