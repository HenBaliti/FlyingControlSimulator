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

    MapDisplayer mapData;
	Model model;
    public boolean isFirstCalc = true;
    public DoubleProperty XDest, YDest;
    public StringProperty ip;
    public StringProperty port;
    public DoubleProperty airplaneX;
    public DoubleProperty airplaneY;
    public StringProperty ipPath;//+
    public StringProperty portPath;//+
    public DoubleProperty startX;//+
    public DoubleProperty startY;//+
//	public DoubleProperty throttle;
//    public DoubleProperty rudder;
//    public DoubleProperty aileron;
//    public DoubleProperty elevator;
//    public BooleanProperty IsPath;
//    public DoubleProperty realtiveH, relativeW;
	
	
    public ViewModel() {
//    	throttle=new SimpleDoubleProperty();
//        rudder=new SimpleDoubleProperty();
//        aileron=new SimpleDoubleProperty();
//        elevator=new SimpleDoubleProperty();
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
//        IsPath = new SimpleBooleanProperty();
//        isFirstCalc = new SimpleBooleanProperty();
        
    }
    
    public void setModel(Model model) {
    	this.model=model;
    }

    
    //Connecting to the Simulator
    public void Connect() {
    	System.out.println("ip is : "+ip.getValue());
    	model.Connect(ip.getValue(),Integer.parseInt(port.getValue()));
    	
    }
    
    //Connecting to MyServer For calculating the path
    public void ConnectCalcPathServer(double height,double width) {
    	System.out.println("The Starting airplane is : "+startX.getValue()+" y: "+startY.getValue());
    	System.out.println("IP Path is: "+ipPath.getValue());
    	System.out.println("The Destiny airplane is : "+XDest.getValue()+" y: "+YDest.getValue());
    	System.out.println("height: "+height);
    	System.out.println("width: "+width);
    	
    	if(isFirstCalc) {
    		isFirstCalc = false;
    		airplaneX.setValue(startX.getValue());
    		airplaneY.setValue(startY.getValue());
    		model.connectMyServer(ipPath.getValue(),Integer.parseInt(portPath.getValue()));
    		model.findPath((int) (airplaneY.getValue()/-1), (int) (airplaneX.getValue() +15),Math.abs( (int) (YDest.getValue() / height)) ,
                  Math.abs((int) (XDest.getValue() / width)), mapData.mapData );
    	}

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
