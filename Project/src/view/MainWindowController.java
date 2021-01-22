package view;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javax.crypto.spec.GCMParameterSpec;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import Commands.Utilities;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
public class MainWindowController implements Observer {

	@FXML
	Canvas plane;
	@FXML
	Canvas markX;
	@FXML
	RadioButton autoPilotRadio, ManualRadio;
	@FXML
	Slider throttleSlider, rudderSlider;
	@FXML
	Circle JoystickIn, JoystickOut;
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
	@FXML
	Button connectSub;
	@FXML
	Button calcSub;
	@FXML
	Text CurrentPressure,CurrentAltitude,CurrentSpeed;

	GraphicsContext gcDrawPlane;
	GraphicsContext gcMark;
	Utilities ut;
	ViewModel vm;
	public int mapData[][];
	public DoubleProperty StartingPositionX;
	public DoubleProperty StartingPositionY;
	public DoubleProperty planeX;
	public DoubleProperty planeY;
	public double prevX;
	public double prevY;
	public DoubleProperty sizeOfElement;
	public DoubleProperty XDest, YDest;
	public DoubleProperty aileron, elevator;
	public DoubleProperty rudder, throttle;
	public DoubleProperty heading;
	ArrayList<String[]> rowElementsList = new ArrayList<>();
	//	private String[] solution;
	double height, width, WidthCanvas, HeightCanvas;
	private Image mark, planepic;
	private String[] path;
	private Image[] planeArr;
	
    public void setViewModel(ViewModel vm,Utilities ut) {
    	this.vm = vm;
    	this.ut = ut;
//    	vm.portPath.bind(portCalcTextField.textProperty());
//		vm.ipPath.bind(ipCalcTextField.textProperty());
		StartingPositionX = new SimpleDoubleProperty();
		StartingPositionY = new SimpleDoubleProperty();
		planeX = new SimpleDoubleProperty();
		planeY = new SimpleDoubleProperty();
		planeX.bindBidirectional(vm.planeX);
		planeY.bindBidirectional(vm.planeY);
		aileron = new SimpleDoubleProperty();
		elevator = new SimpleDoubleProperty();
		sizeOfElement = new SimpleDoubleProperty();
		rudder = new SimpleDoubleProperty();
		throttle = new SimpleDoubleProperty();
		heading = new SimpleDoubleProperty();
		XDest = new SimpleDoubleProperty();
		YDest = new SimpleDoubleProperty();
		planeArr = new Image[8];
		try {
			planeArr[0]=new Image(new FileInputStream("./Project/resources/plane0.png"));
			planeArr[1]=new Image(new FileInputStream("./Project/resources/plane45.png"));
			planeArr[2]=new Image(new FileInputStream("./Project/resources/plane90.png"));
			planeArr[3]=new Image(new FileInputStream("./Project/resources/plane135.png"));
			planeArr[4]=new Image(new FileInputStream("./Project/resources/plane180.png"));
			planeArr[5]=new Image(new FileInputStream("./Project/resources/plane225.png"));
			planeArr[6]=new Image(new FileInputStream("./Project/resources/plane270.png"));
			planeArr[7]=new Image(new FileInputStream("./Project/resources/plane315.png"));
			mark = new Image(new FileInputStream("./Project/Resources/mark.png"));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
		fileChooser.setCurrentDirectory(new File("./Project/resources"));

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
					String[] args = line.split(FileDelimiter);
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
				System.out.println("-----------------------------------------: "+rowElementsList.size());
				System.out.println("-----------------------------------------: "+rowElementsList.get(1).length);
				this.vm.mapData = mapDisplayerData;

				mapDisplayerData.setMapData(mapData);
				markX.setOnMouseClicked(ClickOnMap);

				//Binding An NoN FXML Property
				planepic = new Image(new FileInputStream("./Project/resources/plane.png"));
				gcDrawPlane = plane.getGraphicsContext2D();

				double planertX = StartingPositionX.getValue(); //-158.021
				planertX = planertX - planeX.getValue(); //-158.021 + 157.943
				planertX = (int)(planertX/sizeOfElement.getValue() * -1); //-0.078 / 0.01652
				System.out.print("i is:" + planertX);
				double planertY = StartingPositionY.getValue();
				planertY -= planeY.getValue();
				planertY = (int)(planertY/sizeOfElement.getValue());
				System.out.print("j is:" + planertY);
				gcDrawPlane.drawImage(planepic,  planertX,  planertY, 25, 25);
				vm.sizeElement.bind(sizeOfElement);
				



			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	public void drawAirplane(){
		if(planeX.getValue()!=null&&planeY.getValue()!=null)
		{
			double h = mapDisplayerData.height;
			double w = mapDisplayerData.width;
		//	prevX=planeX.getValue();
			//prevY=planeY.getValue();
			gcDrawPlane = plane.getGraphicsContext2D();
			gcDrawPlane.clearRect(0,0,plane.getWidth(),plane.getHeight());
			double planertX2 = StartingPositionX.getValue(); //-158.021
			planertX2 = planertX2 - planeX.getValue(); //-158.021 + 157.943
			planertX2 = (int)(planertX2/sizeOfElement.getValue() * -1); //-0.078 / 0.01652
			double planertY2 = StartingPositionY.getValue();
			planertY2 -= planeY.getValue();
			planertY2 = (int)(planertY2/sizeOfElement.getValue());
			if(heading.getValue()>=0&&heading.getValue()<39)
				gcDrawPlane.drawImage(planeArr[0], planertX2, planertY2, 25, 25);
			if(heading.getValue()>=39&&heading.getValue()<80)
				gcDrawPlane.drawImage(planeArr[1], planertX2, planertY2, 25, 25);
			if(heading.getValue()>=80&&heading.getValue()<129)
				gcDrawPlane.drawImage(planeArr[2], planertX2, planertY2, 25, 25);
			if(heading.getValue()>=129&&heading.getValue()<170)
				gcDrawPlane.drawImage(planeArr[3], planertX2, planertY2, 25, 25);
			if(heading.getValue()>=170&&heading.getValue()<219)
				gcDrawPlane.drawImage(planeArr[4], planertX2, planertY2, 25, 25);
			if(heading.getValue()>=219&&heading.getValue()<260)
				gcDrawPlane.drawImage(planeArr[5], planertX2, planertY2, 25, 25);
			if(heading.getValue()>=260&&heading.getValue()<309)
				gcDrawPlane.drawImage(planeArr[6], planertX2, planertY2, 25, 25);
			if(heading.getValue()>=309)
				gcDrawPlane.drawImage(planeArr[7], planertX2, planertY2, 25, 25);
		}

	}

	//Clicking on the Manual radio button
	public void ClickOnManualRadio() {
		// ailron and elevator settings from the Joystick GUI
		JoystickIn.setOnMouseDragged(JoystickDragAndTakeValues);
		JoystickIn.setOnMouseReleased(JoystickOnRelease2);
		rudderSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub
				if (ManualRadio.isSelected()) {
					rudder.setValue(arg2.doubleValue());
					vm.setRudder();
				}
			}
		});
		throttleSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub
				if (ManualRadio.isSelected()) {
					throttle.setValue(arg2.doubleValue());
					vm.setThrottle();
				}
			}
		});

		vm.throttle.bind(throttle);
		vm.rudder.bind(rudder);
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

	public void ConnectSubmit() {
		vm.Connect();
		Stage stage = (Stage) connectSub.getScene().getWindow();
		stage.close();

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

		System.out.println("--------------" + this.vm.ipPath.getValue());

		this.vm.ConnectCalcPathServer(height, width);
		Stage stage = (Stage) calcSub.getScene().getWindow();
		stage.close();


	}


	// Loading The Auto Pilot Text
	public void LoadAutoPilotText() {

		TextAreaAutoPilot.clear();
		// Opening the TXT File
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("TXT Files", "txt"));
		fileChooser.setCurrentDirectory(new File("./Project/resources"));

		String line = "";
		BufferedReader br = null;

		// Start reading from the TXT File
		int isOK = fileChooser.showOpenDialog(null);
		if (isOK == JFileChooser.APPROVE_OPTION) {

			try {
				br = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
				// Reading all the rest of the TXT file
				while ((line = br.readLine()) != null) {
					TextAreaAutoPilot.appendText(line + "\n");
				}

				this.vm.autoPilotTxt.bindBidirectional(TextAreaAutoPilot.textProperty());
				this.vm.parserAuto();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}


	public void executeAutoPilot() {//need to shutdown first the manual radio
//		this.vm.parserAuto();
	}


	@FXML
	public void initialize() {

		//---------------Joystick-----------------------------------------------
		JoystickIn.setOnMouseDragged(JoystickDragged);
		JoystickIn.setOnMouseReleased(JoystickOnRelease);

		//--------------init Radio-----------------
		ToggleGroup group = new ToggleGroup();
		autoPilotRadio.setToggleGroup(group);
		ManualRadio.setToggleGroup(group);


	}

	//Clicking on the map and showing the X Destination
	EventHandler<MouseEvent> ClickOnMap = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent arg0) {
			System.out.println("The X on the matrix is : " + arg0.getX());
			System.out.println("The Y on the matrix is : " + arg0.getY());

			XDest.setValue(arg0.getX());
			YDest.setValue(arg0.getY());
			//Binding An NoN FXML Property
			vm.XDest.bind(XDest);
			vm.YDest.bind(YDest);
			this.drawMark();

		}

		private void drawMark() {
			double H = markX.getHeight();
			double W = markX.getWidth();
			double h = H/mapDisplayerData.height;
			double w = W/mapDisplayerData.width;
			w = 1.0121;
			h = 1.644;
			gcMark = markX.getGraphicsContext2D();
			gcMark.clearRect(0,0,W,H);
			gcMark.drawImage(mark, XDest.getValue()  ,YDest.getValue()  , 15, 15);

		}

	};

	//Dragging the Joystick
	EventHandler<MouseEvent> JoystickDragged = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent mouseEvent) {
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
		}

	};

	//Dragging the Joystick and taking the ailron and the elevator on manual flight
	EventHandler<MouseEvent> JoystickDragAndTakeValues = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent mouseEvent) {
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

			//-------Taking Values from the Joystick------
			double centerX = 760.0, centerY = 177.0;
			double mouseX = mouseEvent.getSceneX();
			double mouseY = mouseEvent.getSceneY();
			double tmpX = (mouseX - centerX) / 90;
			double tmpY = (mouseY - centerY) / 90;

			if (ManualRadio.isSelected()) {
				//- X -
				if (mouseX > centerX) { //Right of the centerX
					if (tmpX > 1)
						aileron.setValue(1);
					else
						aileron.setValue(tmpX);
				}
				if (mouseX < centerX) { //Left of the centerX
					if (tmpX < -1)
						aileron.setValue(-1);
					else
						aileron.setValue(tmpX);
				}

				//- Y - 
				if (mouseY < centerY) { //Up of the centerY
					if (tmpY < -1)
						elevator.setValue(1);
					else
						elevator.setValue(tmpY / -1);
				}
				if (mouseY > centerY) { //Down of the centerY
					if (tmpY > 1)
						elevator.setValue(-1);
					else
						elevator.setValue(tmpY / -1);
				}

				//Binding An NoN FXML Property
				vm.aileron.bind(aileron);
				vm.elevator.bind(elevator);
				vm.movePlain();
			}


		}

	};

	public void drawLine() {
		double H = markX.getHeight();
		double W = markX.getWidth();
		double h = H/mapDisplayerData.height;
		double w = W/mapDisplayerData.width;
		w = 1.0121;
		h = 1.644;

		String move = path[1];
		double planertX3 = StartingPositionX.getValue(); //-158.021
		planertX3 = planertX3 - planeX.getValue(); //-158.021 + 157.943
		planertX3 = (int) (planertX3 / sizeOfElement.getValue() * -1); //-0.078 / 0.01652
		double planertY3 = StartingPositionY.getValue();
		planertY3 -= planeY.getValue();
		planertY3 = (int) (planertY3 / sizeOfElement.getValue());
		double x = planertX3;
		double y = planertY3;
		//double x = 0;
		//double y = 0;
		for (int i = 2; i < path.length; i++) {
			switch (move) {
				case "Right":
					gcMark.setStroke(Color.BLACK.darker());
					gcMark.strokeLine(x, y, (x + w) , y);
					x = x+w;
					break;
				case "Left":
					gcMark.setStroke(Color.BLACK.darker());
					gcMark.strokeLine(x, y, (x - w), y );
					x = x-w;
					break;
				case "Up":
					gcMark.setStroke(Color.BLACK.darker());
					gcMark.strokeLine(x , y , x, (y - h));
					y = y-h;
					break;
				case "Down":
					gcMark.setStroke(Color.BLACK.darker());
					gcMark.strokeLine(x , y , x , (y + h) );
					y = y+h;
			}

			move = path[i];
		}
	}

		//Joystick on release
		EventHandler<MouseEvent> JoystickOnRelease = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
				JoystickIn.setLayoutX(760);
				JoystickIn.setLayoutY(177);
			}

		};

		//Joystick on release 2
		EventHandler<MouseEvent> JoystickOnRelease2 = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
				JoystickIn.setLayoutX(760);
				JoystickIn.setLayoutY(177);
				aileron.setValue(0);
				elevator.setValue(0);
				vm.movePlain();
			}

		};



	@Override
	public void update(Observable o, Object arg) {
		if(o==vm) {
			//Taking the realTime Vars from the server
			if(ut.isSymbolExist(arg.toString())) {
				if(ut.symbolTable.get(arg.toString()).getSIM().equals("Pressure")) {
					CurrentPressure.setText(Double.toString(ut.symbolTable.get(arg.toString()).getV()));
//					this.vm.CurrentAltitude.bindBidirectional(CurrentAltitude.textProperty());
				}
				if(ut.symbolTable.get(arg.toString()).getSIM().equals("GPS-Altitude")) {
					CurrentAltitude.setText(Double.toString(ut.symbolTable.get(arg.toString()).getV()));
//					this.vm.CurrentAltitude.bindBidirectional(CurrentAltitude.textProperty());
				}
				if(ut.symbolTable.get(arg.toString()).getSIM().equals("airspeed")) {
					CurrentSpeed.setText(Double.toString(ut.symbolTable.get(arg.toString()).getV()));
//					this.vm.CurrentAltitude.bindBidirectional(CurrentAltitude.textProperty());
				}
				if(ut.symbolTable.get(arg.toString()).getSIM().equals("Longtitude")) {
					planeX.setValue(ut.symbolTable.get(arg.toString()).getV());
					System.out.println("PlaneX is : "+planeX.getValue());
					this.drawAirplane();
				}
				if(ut.symbolTable.get(arg.toString()).getSIM().equals("Latitude")) {
					planeY.setValue(ut.symbolTable.get(arg.toString()).getV());
					System.out.println("PlaneY is : "+planeY.getValue());
					this.drawAirplane();
				}
				if(ut.symbolTable.get(arg.toString()).getSIM().equals("heading")) {
					heading.setValue(ut.symbolTable.get(arg.toString()).getV());
					System.out.println("Heading is : "+heading.getValue());
					this.drawAirplane();
				}

			}
			else { //Getting The solution!
//				solution = (String[])arg;
//				System.out.println("Solution is: "+solution.toString());
				if(arg!=null) {
					path = (String[]) arg;
					this.drawLine();

				}
			}
		}

	}
	

}
