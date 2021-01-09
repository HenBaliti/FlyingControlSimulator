package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MainWindowController{

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

	public int mapData[][];
	public double StartingPositionX;
	public double StartingPositionY;
	public double sizeOfElement;
	ArrayList<String[]> rowElementsList = new ArrayList<>();

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
				StartingPositionX = Double.parseDouble(startPositionLine[0]);
				StartingPositionY = Double.parseDouble(startPositionLine[1]);

				// Reading the Size of the Element
				String[] sizeLine = br.readLine().split(FileDelimiter);
				sizeOfElement = Double.parseDouble(sizeLine[0]);

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
				mapDisplayerData.setMapData(mapData);

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
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();

			stage.setTitle("Connect To Server");
			stage.setScene(new Scene(root));
			if (!stage.isShowing()) {
				stage.show();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

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

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	@FXML
    public void initialize() {
        DraggableNode node = new DraggableNode(JoystickIn);
//        node.setOnMouseDragExited(new EventHandler<MouseEvent>() {
//
//			@Override
//			public void handle(MouseEvent arg0) {
//				
//				node.getView().setLayoutX(200.0);
//				node.getView().setLayoutY(250.0);
//				node.setLayoutX(200.0);
//				node.setLayoutY(250.0);
//				
//			}
//		});

        
//        node.setOnDragExited(new EventHandler<Event>() {
//
//			@Override
//			public void handle(Event arg0) {
//				
//				node.getView().setLayoutX(200.0);
//				node.getView().setLayoutY(250.0);
//				node.setLayoutX(200.0);
//				node.setLayoutY(250.0);
//				
//			}
//        });
        anchorPane.getChildren().add(node);
    }

}
