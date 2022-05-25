package Interface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
    @FXML private AnchorPane topWindow;
    @FXML private Label errorLabel;
    @FXML private Label fileLabel;
    @FXML private Label btnEjecutar;
    
    
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
	
	private void openFile(File file) {
        /*try {
            //desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                FileChooser.class.getName()).log(
                    Level.SEVERE, null, ex
                );
        }*/
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {

        	
    	// INICIALIZACION PROGRESS INDICATOR IMAGE
    	//progressImg.setProgress(-1.0f);
    	//progressImg.setVisible(false);
    	
    	// SETEO LISTVIEW LISTSMARTPHONES
    	
    	try {
			//analityc.levantarBasedeDatos(listaDispositivos);
    		int x = 3;
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		}
		//listSmartphones.setItems(listaDispositivos);
		
		//TEST CONEXION PARA INICIAR LA BUSQUEDA DE PRECIOS DE LOS SMARTPHONES
		/*if (testConexion.hayConexion()) {
			analityc.iniciarWebScrapNewPrice();
		} else {
			offlineFlag.setVisible(true);
		}*/

    	// BUSQUEDA POR NOMBRE MEDIANTE SEARCHINPUT
		FilteredList<String> filteredData = new FilteredList<>(listaDispositivos, e -> true);
		
		
		
		
		// SELECCION DISPOSITIVO DE LA LISTA
		
		
		
    	
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
                     System.out.println(file.toString()); 
                 }               
		     }
		});
		
	}

    
    /*public Task createWorker() {
    	return new Task() {

			@Override
			protected Object call() throws Exception {
				if (imageSmartPhone.getImageURL(analityc.getHashSmartphones().get(listSmartphones.getSelectionModel().getSelectedItem())) != null) {
					updateProgress(25, 100);
					URL url = new URL(imageSmartPhone.getImageURL(analityc.getHashSmartphones().get(listSmartphones.getSelectionModel().getSelectedItem())));
	            	updateProgress(50, 100);
	        		Image image = new Image(url.toString());
	        		updateProgress(75, 100);
	                Image img = imgSearchSelect.getImage();
	                if (img != null) {
	                    double w = 0;
	                    double h = 0;

	                    double ratioX = imgSearchSelect.getFitWidth() / img.getWidth();
	                    double ratioY = imgSearchSelect.getFitHeight() / img.getHeight();
	                    
	                    double reducCoeff = 0;
	                    if(ratioX >= ratioY) {
	                        reducCoeff = ratioY;
	                    } else {
	                        reducCoeff = ratioX;
	                    }

	                    w = img.getWidth() * reducCoeff;
	                    h = img.getHeight() * reducCoeff;

	                    imgSearchSelect.setX((imgSearchSelect.getFitWidth() - w) / 2);
	                    imgSearchSelect.setY((imgSearchSelect.getFitHeight() - h) / 2);
	                }
	            	updateProgress(100, 100);
	            	progressImg.setVisible(false);
	            	imgSearchSelect.setImage(image);
	            	imgSearchSelect.setVisible(true);
					return true;
				}
				return true;

			}
    		
    	};
    }*/
    
}





