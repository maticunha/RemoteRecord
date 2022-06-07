package Application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;



public class Preferences extends Application {
	
	@FXML
	private Button GoogleDriveLogin;
	
	@FXML
	private TextField GoogleDriveLink;
	
	@FXML
	private Button OneDriveLogin;
	
	@FXML
	private TextField OneDriveLink;
	
	@FXML
	private Button Save;
	
	@FXML 
	Button Cancel;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = (Pane)FXMLLoader.load(getClass().getResource("Preferences.fxml"));
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
