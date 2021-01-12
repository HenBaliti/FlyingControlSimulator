package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.crypto.spec.GCMParameterSpec;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MainWindowController implements Observer{

	@FXML
	RadioButton autoPilotRadio,ManualRadio;
	@FXML
	Slider throttleSlider,rudderSlider;
	@FXML
	Circle JoystickIn,JoystickOut;
	@FXML
	MapDisplayer mapDisplayerData;
	@FXML
    TextArea TextAreaAutoPilot;
	@FXML
	AnchorPane anchorPane;
	@FXML
	TextField portCalcTextField;
	@FXML
	TextField ipCalcTextField;
	@FXML
	TextField portTextField;
	@FXML
	TextField ipTextField;


	ViewModel vm;
	public int mapData[][];
	public DoubleProperty  StartingPositionX;
	public DoubleProperty  StartingPositionY;
	public DoubleProperty  sizeOfElement;
	public DoubleProperty XDest, YDest;
	ArrayList<String[]> rowElementsList = new ArrayList<>();
//	private String[] solution;
	double height,width,WidthCanvas,HeightCanvas; 
	
	
    public void setViewModel(ViewModel vm) {
    	this.vm = vm;

//    	vm.portPath.bind(portCalcTextField.textProperty());
//		vm.ipPath.bind(ipCalcTextField.textProperty());
		StartingPositionX=new SimpleDoubleProperty();
		StartingPositionY=new SimpleDoubleProperty();
		sizeOfElement=new SimpleDoubleProperty();
    	XDest=new SimpleDoubleProperty();
    	YDest=new SimpleDoubleProperty();
    }
	
    
    public void ConnectingPopUpServer() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PopUpCalculateConnect.fxml"));
			fxmlLoader.setController(this);
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();

			stage.setTitle("Calculate A Path");
			stage.setScene(new Scene(root));
	    	this.vm.portPath.bindBidirectional(portCalcTextField.textProperty());
	    	this.vm.ipPath.bindBidirectional(ipCalcTextField.textProperty());
			if (!stage.isShowing()) {
				stage.show();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	public void LoadData() {
		// Opening the CSV File
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
		fileChooser.setCurrentDirectory(new File("./resources"));

		String FileDelimiter = ",";
		String line = "";
		BufferedReader br = null;

		// Start reading from the CSV File
		int isOK = fileChooser.showOpenDialog(null);
		if (isOK == JFileChooser.APPROVE_OPTION) {

			try {
				// Reading the Starting element from the first line
				br = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
				String[] startPositionLine = br.readLine().split(FileDelimiter);
				StartingPositionX.setValue(Double.parseDouble(startPositionLine[0]));
				StartingPositionY.setValue(Double.parseDouble(startPositionLine[1]));

				// Reading the Size of the Element
				String[] sizeLine = br.readLine().split(FileDelimiter);
				sizeOfElement.setValue(Double.parseDouble(sizeLine[0]));

				// Reading all the rest of the CSV file
				while ((line = br.readLine()) != null) {
					String[] args = br.readLine().split(FileDelimiter);
					rowElementsList.add(args);
				}

				// Putting all the data in the mapData
				mapData = new int[rowElementsList.size()][];

				for (int i = 0; i < rowElementsList.size(); i++) {
					mapData[i] = new int[rowElementsList.get(i).length];

					for (int j = 0; j < rowElementsList.get(i).length; j++) {
						String tmp = rowElementsList.get(i)[j];
						mapData[i][j] = Integer.parseInt(tmp);
					}
				}
				this.vm.mapData = mapDisplayerData;
				mapDisplayerData.setMapData(mapData);
		        mapDisplayerData.setOnMouseClicked(ClickOnMap);
		      //Binding An NoN FXML Property
		        mapDisplayerData.gc.strokeText("A",(-1)*StartingPositionX.get(), StartingPositionY.get());
		        vm.startX.bind(StartingPositionX);
		        vm.startY.bind(StartingPositionY);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	// Opens the a popup window on clicking on -Connect-
	public void Connect() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PopUpConnect.fxml"));
			fxmlLoader.setController(this);
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();

			stage.setTitle("Connect To Server");
			stage.setScene(new Scene(root));
			vm.port.bind(portTextField.textProperty());
			vm.ip.bind(ipTextField.textProperty());
			if (!stage.isShowing()) {
				stage.show();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	public void ConnectSimulator() {
		this.vm.Connect();
	}
	
	
	
	//Connect to server to calc path
	public void ConnectMyServer() {
		

			//Width and Height
			WidthCanvas = mapDisplayerData.getWidth();
			HeightCanvas = mapDisplayerData.getHeight();
			
			width = WidthCanvas / mapData[0].length;
			height = HeightCanvas / mapData.length;
			
			System.out.println("--------------"+this.vm.ipPath.getValue());
			
			this.vm.ConnectCalcPathServer(height,width);
		
		
	}
	

	// Loading The Auto Pilot Text
	public void LoadAutoPilotText() {
		
		TextAreaAutoPilot.clear();
		// Opening the TXT File
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("TXT Files", "txt"));
		fileChooser.setCurrentDirectory(new File("./resources"));

		String line = "";
		BufferedReader br = null;

		// Start reading from the TXT File
		int isOK = fileChooser.showOpenDialog(null);
		if (isOK == JFileChooser.APPROVE_OPTION) {

			try {
				br = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
				// Reading all the rest of the TXT file
				while ((line = br.readLine()) != null) {
					TextAreaAutoPilot.appendText(line +"\n");
				}
				
		this.vm.autoPilotTxt.bindBidirectional(TextAreaAutoPilot.textProperty());

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	
	public void SendToInterperter() {
		this.vm.AutoPilotParser();
	}
	

	@FXML
    public void initialize() {

		//---------------Joystick-----------------------------------------------
		JoystickIn.setOnMouseDragged(mouseEvent -> {
	            Point2D centerPoint = new Point2D(760.0, 177.0);
	            Point2D mouse = new Point2D(mouseEvent.getSceneX(), mouseEvent.getSceneY());
	            double dis = centerPoint.distance(mouse);
	            if (dis > 90) { // if joystick get out of bounds
	                double angle = Math.atan2(mouse.getY() - centerPoint.getY(), mouse.getX() - centerPoint.getX()); // cal angle between 2 points
	                // force joystick to stay on his bounds
	                JoystickIn.setLayoutX(centerPoint.getX() + 90 * Math.cos(angle));
	                JoystickIn.setLayoutY(centerPoint.getY() + 90 * Math.sin(angle));
	            } else { // in bounds
	            	JoystickIn.setLayoutX(mouseEvent.getSceneX());
	            	JoystickIn.setLayoutY(mouseEvent.getSceneY());
	            }
	        });

			
			JoystickIn.setOnMouseReleased(mouseDragEvent -> {
				JoystickIn.setLayoutX(760);
				JoystickIn.setLayoutY(177);
		    });

		//--------------init Radio-----------------
		ToggleGroup group = new ToggleGroup();
		autoPilotRadio.setToggleGroup(group);
		ManualRadio.setToggleGroup(group);
			
			
    }
	
	//Clicking on the map and showing the X Destination
	EventHandler<MouseEvent> ClickOnMap = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent arg0) {
			System.out.println("The X on the matrix is : "+arg0.getX());
			System.out.println("The Y on the matrix is : "+arg0.getY());

			XDest.setValue(arg0.getX());
            YDest.setValue(arg0.getY());
            //Binding An NoN FXML Property
            vm.XDest.bind(XDest);
            vm.YDest.bind(YDest);
            mapDisplayerData.gc.strokeText("X",arg0.getX(), arg0.getY());
		}
		
	};
	



	@Override
	public void update(Observable o, Object arg) {
		if(o==vm) {
//			solution = (String[])arg;
//			System.out.println("Solution is: "+solution.toString());
		}
		else {

		}
		
	}
	

}
