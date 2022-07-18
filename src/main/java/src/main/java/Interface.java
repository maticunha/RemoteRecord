package src.main.java;
	
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;


public class Interface extends Application implements Initializable{
	
	@FXML
	private BorderPane Border;
	
	@FXML
	private Pane Pane;
	
	@FXML
	private Pane Header;
	
	@FXML
    private Button Record;
	
	@FXML
	private Button Stop;
	
	@FXML
	private MenuItem Preferences;
	
	@FXML
	private Button Play;
	
	@FXML
	private MenuItem About;
	
	@FXML
	private Text RIP;
	
	@FXML
	private Text Label;
	
	@FXML
	private Button back;
	
	Record r1 = new Record();
  
  @FXML
  private AnchorPane Control_Panel;
	
	@FXML
	private Box box1;
	
	@FXML
	private Box box2;
	
	@FXML
	private Box box3;
	
	@FXML
	private Box box4;
	
	@FXML
	private Box box5;
	
	@FXML
	private Button Hidden;
	
	@FXML
	private ListView<String> driveFolders = new ListView<String>();
	
	@FXML
	private Label currentFolder = new Label(); 
	
	@FXML
	public Button uploadButton;
	
	@FXML
	public Button localUpload;
	
	FileChooser fileChooser = new FileChooser();
	
	private String[] driveFileNames;
	private String[] driveFileId;
	
	private double xOffset = 0;
	private double yOffset = 0;
	

	private static String driveFolderID = "root";
	private static String rootFolderID = null;
	private static String lastID = "root";
	private static String backToRoot = "Back to MyDrive...";
	private static String driveFolderName = "My Drive";


	
	public void start(Stage primaryStage) {
		try {
			Pane root = (Pane)FXMLLoader.load(getClass().getResource("Design.fxml"));
		
			Scene scene = new Scene(root,600,450);
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
	
	public void upload(ActionEvent e) {
		String name = "test";
		File recording = new File("/RemoteRecord/Recordings/2022-06-06 171904.WAV");
		List<String> parents = new ArrayList<>(1);
		parents.add(driveFolderID);
		try {
			GoogleDriveAPI.uploadFile(name, parents, recording);
		} catch (IOException | GeneralSecurityException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	public void localUpload(ActionEvent e) {
		
		Stage primaryStage = new Stage();
		
		String[] fileName = r1.getFileName().split("/");
		
		fileChooser.setTitle("Save");
		fileChooser.setInitialFileName(fileName[1]);
	    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("WAV", ".wav"));
		File file = fileChooser.showSaveDialog(primaryStage);
		
		Path temp;
		try {
			temp = Files.move
			        (Paths.get(r1.getFile().toURI()),
			        Paths.get(file.toURI()));
			 if(temp != null)
		        {
		            System.out.println("File renamed and moved successfully");
		        }
		        else
		        {
		            System.out.println("Failed to move the file");
		        }
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		       
		
		
				
	}
	
	public static void main(String[] args) throws IOException, GeneralSecurityException {
		    launch(args);
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			
			driveFileNames = GoogleDriveAPI.getFileNames(driveFolderID);
			driveFileId = GoogleDriveAPI.getFileID(driveFolderID);
			driveFolders.getItems().addAll(driveFileNames);
			rootFolderID = GoogleDriveAPI.getParentOf(driveFileId[0]);
			currentFolder.setText("Current Folder: MyDrive");
			System.out.println(rootFolderID);
			
		} catch (IOException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		driveFolders.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				int index = driveFolders.getSelectionModel().getSelectedIndex();
				

				
				System.out.println("Now changing list...");
				
				
				 if (driveFolders.getSelectionModel().getSelectedItem().contains("Back to")) { //if back is hit/if there is an empty directory 
					 String tid;
					try {
						tid = GoogleDriveAPI.getParentOf(driveFolderID);
						driveFolderID = tid; 
					} catch (IOException | GeneralSecurityException e) {
						e.printStackTrace();
					}
					 
				
				 } else { //if navigating to the next folder
					driveFolderID = driveFileId[index];
					System.out.println(driveFolderID);
				 }
	
				driveFolders.getItems().clear();
			
				try {
					
					
					
					driveFileNames = GoogleDriveAPI.getFileNames(driveFolderID);
					driveFileId = GoogleDriveAPI.getFileID(driveFolderID);
	
				
				
					if (driveFileId == null) { //add to empty directory 
						driveFolders.getItems().add("Back to: " + GoogleDriveAPI.getNameOf(GoogleDriveAPI.getParentOf(driveFolderID)));
						currentFolder.setText("Current Folder: " + newValue);
						
						
					
					} else if (driveFolderID.equals(rootFolderID)) { //if going back would bring you to the root directory 
						driveFolders.getItems().addAll(driveFileNames);
						System.out.println(GoogleDriveAPI.getParentOf(driveFolderID));
						currentFolder.setText("Current Folder: MyDrive");
								
					} else {
						driveFolders.getItems().addAll(driveFileNames);
						driveFolders.getItems().add("Back to " + GoogleDriveAPI.getNameOf(GoogleDriveAPI.getParentOf(driveFolderID)));
						currentFolder.setText("Current Folder: " + newValue);
					
				
					}	
					
					
				


					
					

				} catch (IOException | GeneralSecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				System.out.println(driveFolderID);
				
			}
			
			
		
			});

		
		System.out.println("Ready for next input...");
	}
	
	
	
	
	
}

