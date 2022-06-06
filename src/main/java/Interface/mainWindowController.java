package Interface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

//import org.jsoup.Connection;
//import org.jsoup.Jsoup;

//import AnalyticAHP.Analityc;
//import AnalyticAHP.Smartphone;
//import admArchivos.admArchivo;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
//import webScrap.JsoupRun;

public class mainWindowController extends Application implements Initializable {


    @FXML private ImageView btnClose;
    @FXML private ImageView btnMinimize;
    @FXML private ImageView btnUpload;
    @FXML private ImageView btnClearOutPut;
    @FXML private AnchorPane topWindow;
    @FXML private Label errorLabel;
    @FXML private Label fileLabel;
    @FXML private Label btnEjecutar;
    @FXML private TextArea txtOutput;
    
    
    //private Analityc analityc = new Analityc();
    private ObservableList<String> listaDispositivos = FXCollections.observableArrayList(); 
      
    private FileChooser fileChooser = new FileChooser();
    //private Desktop desktop = Desktop.getDesktop();
    
    private Stage pStage; // Stage de mainWindow
    
    private double xOffset = 0; // xOffset e yOffset permiten almacenar la posicion de la ventana al ser desplazada por el usuario
    private double yOffset = 0; 
    private boolean styled = false; // Permite verificar si ya fue removido el marco de la ventana. 
    private Stage sStage = new Stage(); // Stage de la segunda ventana: 2. Ingrese el valor de la inversion deseada
    
    //private JsoupRun imageSmartPhone = new JsoupRun();
    //private Task getImageWorker; 
	//private JsoupRun testConexion = new JsoupRun();
    
    public mainWindowController(Stage primaryStage) {
    	pStage = primaryStage; 
	}
    
    
    
    
	@Override
	public void start(Stage secondStage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("investmentWindow.fxml"));
		//investmentWindowController c = new investmentWindowController(pStage, secondStage, analityc); 
		//loader.setController(c);
		Pane mainPane = (Pane) loader.load();
		Scene scene = new Scene(mainPane);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		secondStage.setScene(scene);
		secondStage.setX(xOffset);
		secondStage.setY(yOffset);
		if (!styled) {
			secondStage.initStyle(StageStyle.UNDECORATED);
			styled = true;
		}
		secondStage.setWidth(pStage.getWidth());
		secondStage.setHeight(pStage.getHeight());
		secondStage.show();
		mainPane.requestFocus();
	}
	
	// SIGUIENTE VENTANA investmentWindow mediante btnNext
	public void nextInvestmentWindow() {
		System.out.println("Metodo por afuera");		
	}
	
	private void executeJar() {

		StringBuffer sb = new StringBuffer();
		try {
			//java -javaagent:JavaAgent-1.0-SNAPSHOT-jar-with-dependencies.jar -jar Ejecutable.jar
	        ProcessBuilder builder = new ProcessBuilder(
	        		"java", 
	        		"-javaagent:C:\\Users\\Joaco\\Desktop\\Tesis\\Agente-Tesis\\target\\JavaAgent-1.0-SNAPSHOT-jar-with-dependencies.jar", 
	        		"-jar", 
	        		"C:\\Users\\Joaco\\Desktop\\Tesis\\Agente-Tesis\\Ejecutable.jar");
	        builder.redirectErrorStream(true);
	        Process p = builder.start();
	        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        String line;
	        
	        while (true) {
	            line = r.readLine();
	            if (line == null) { break; }
	            sb.append(line + System.lineSeparator());
	        }
			}
		catch(Exception ex) {
			
		}
		
		txtOutput.setEditable(false);
		txtOutput.setText(sb.toString());
		
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {

        	    	
    	// MOVIMIENTO DE VENTANA DESDE TOP WINDOWS
		topWindow.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });	
		
		topWindow.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pStage.setX(event.getScreenX() - xOffset);
                pStage.setY(event.getScreenY() - yOffset);
            }
        });
		
		// CERRAR VENTANA MEDIANTE BOTON CLOSE
		btnClose.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

		     @Override
		     public void handle(MouseEvent event) {
		         pStage.close();		         
		         event.consume();
		     }
		});
		
		// MINIMIZAR VENTANA MEDIANTE BOTON MINIMIZE
		btnMinimize.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

		     @Override
		     public void handle(MouseEvent event) {
		         pStage.setIconified(true);	         
		         event.consume();
		     }
		});
		
		btnUpload.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

		     @Override
		     public void handle(MouseEvent event) {
		    	 
		    	 File file = fileChooser.showOpenDialog(pStage);
                 if (file != null) {
                	 fileLabel.setText(file.toString());                 
                 }               
		     }
		});
		
		btnEjecutar.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

		     @Override
		     public void handle(MouseEvent event) {
		    	 
		    	 if (!fileLabel.getText().isEmpty()) {
		    		 executeJar();
		    	 }
		    	 
                             
		     }
		});
		
		btnClearOutPut.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

		     @Override
		     public void handle(MouseEvent event) {
		    	 
		    	 txtOutput.setText("");   	 
                            
		     }
		});
		
	}

    
}





