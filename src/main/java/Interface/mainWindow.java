package Interface;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class mainWindow extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainWindow.fxml"));
		mainWindowController controller = new mainWindowController(primaryStage);
		loader.setController(controller);
		Pane mainPane = (Pane) loader.load();
		Scene scene = new Scene(mainPane);
		scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
		primaryStage.setTitle("Agente de Trazas");
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.UNDECORATED);
        File file = new File("/images/ToolIcon.png");
        Image ToolIcon = new Image(file.toURI().toString());
        primaryStage.getIcons().add(ToolIcon);
		primaryStage.show();
		mainPane.requestFocus();
	}
	
}
