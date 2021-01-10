package view;

import java.util.Observable;
import java.util.Observer;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class PopUpCalculateController  implements Observer{

	public DoubleProperty XDest, YDest;
	Canvas Destiny;

	ViewModel vm;
//	@FXML
//	TextField portCalcTextField;
//	@FXML
//	TextField ipCalcTextField;
	
	private BooleanProperty IsTherePath; //path
	private String[] solution;
	double height,width,WidthCanvas,HeightCanvas; 
	GraphicsContext gc;
	MapDisplayer mapData;
	
	
	public void setViewModel(ViewModel vm) {
		this.vm = vm;
//		vm.portPath.bind(portCalcTextField.textProperty());
//		vm.ipPath.bind(ipCalcTextField.textProperty());
    	XDest=new SimpleDoubleProperty();
    	YDest=new SimpleDoubleProperty();
    	YDest.bindBidirectional(vm.YDest);
        XDest.bindBidirectional(vm.XDest);
		IsTherePath.booleanProperty(vm.IsPath);
		//If its the oposite way -> like I want a result  that will change when i got the result from the model i use it like this
		//resultLabel.textPropery().bind(vm.result.asString());
	}
	
	public void ConnectMyServer() {
		
		//Width and Height
		WidthCanvas = Destiny.getWidth();
		HeightCanvas = Destiny.getHeight();
		
		width = WidthCanvas / mapData.mapData[0].length;
		height = HeightCanvas / mapData.mapData.length;
		
		vm.ConnectCalcPathServer(height,width);
		
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o==vm) {
			if(o.getClass().getName().equals("MapDisplayer")) {//////////
				this.mapData = (MapDisplayer)arg;
			}
			else {
				this.Destiny = (Canvas)arg;
				gc = Destiny.getGraphicsContext2D();
//				Destiny.setOnMouseClicked(ClickOnMap);
			}
		}
		else {
			solution = (String[])arg;
			System.out.println("Solution is: "+solution.toString());
		}
		
	}
	

	@FXML
    public void initialize() {
	}
	
	

	
}
