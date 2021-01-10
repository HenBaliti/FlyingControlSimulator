package view;

import java.util.Observable;
import java.util.Observer;

import Model.Model;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;

public class ViewModel extends Observable implements Observer {

	Model model;
	public DoubleProperty throttle;
    public DoubleProperty rudder;
    public DoubleProperty aileron;
    public DoubleProperty elevator;
    public StringProperty ip;
    public StringProperty port;
    public StringProperty ipPath;
    public StringProperty portPath;
    public DoubleProperty airplaneX;
    public DoubleProperty airplaneY;
    public DoubleProperty startX;
    public DoubleProperty startY;
    public BooleanProperty IsPath;
    public BooleanProperty isFirstCalc;
    public DoubleProperty XDest, YDest;
    public DoubleProperty realtiveH, relativeW;
    MapDisplayer mapData;
    Canvas Destiny;
	
	
    public ViewModel() {
    	throttle=new SimpleDoubleProperty();
        rudder=new SimpleDoubleProperty();
        aileron=new SimpleDoubleProperty();
        elevator=new SimpleDoubleProperty();
        ip=new SimpleStringProperty();
        port=new SimpleStringProperty();
        ipPath=new SimpleStringProperty();
        portPath=new SimpleStringProperty();
        airplaneX=new SimpleDoubleProperty();
        airplaneY=new SimpleDoubleProperty();
        startX=new SimpleDoubleProperty();
        startY=new SimpleDoubleProperty();
        XDest=new SimpleDoubleProperty();
        YDest=new SimpleDoubleProperty();
        IsPath = new SimpleBooleanProperty();
        isFirstCalc = new SimpleBooleanProperty();
        
    }
    
    public void setModel(Model model) {
    	this.model=model;
    }
    
//    public void setMapData(MapDisplayer map) {
//    	this.mapData=map;
//        setChanged();
//        notifyObservers(this.mapData);
//    }
    
    //Connecting to the Simulator
    public void Connect() {
    	model.Connect(ip.getValue(),Integer.parseInt(port.getValue()));
    	
    }
    
    //Connecting to MyServer For calculating the path
    public void ConnectCalcPathServer() {
    	System.out.println(ipPath.getValue());
    	model.connectMyServer(ipPath.getValue(),Integer.parseInt(portPath.getValue()));
    	
    	//if it is the first time we calculate
    	
        model.findPath((int) (airplaneY.getValue()/-1), (int) (airplaneX.getValue() +15),Math.abs( (int) (YDest.getValue() / h)) ,
                Math.abs((int) (XDest.getValue() / w)), mapData.mapData );
    }
    
    
    
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg0==model)
        {
            String[] tmpArr=(String[])arg1;
            
            if(tmpArr[0].equals("path")){
                setChanged();
                notifyObservers(tmpArr);
            }
        }
	}
	
	
	

}
