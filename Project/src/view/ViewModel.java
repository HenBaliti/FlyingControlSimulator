package view;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import Commands.Utilities;
import Model.Model;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import test.MyInterpreter;

public class ViewModel extends Observable implements Observer {

	Utilities ut;
    MapDisplayer mapData;
	Model model;
    public boolean isFirstCalc = true;
    public DoubleProperty XDest, YDest;
    public StringProperty ip;
    public StringProperty port;
    public DoubleProperty planeX;
    public DoubleProperty planeY;
    public StringProperty ipPath;//+
    public StringProperty portPath;//+
    public DoubleProperty startX;//+
    public DoubleProperty startY;//+
    public StringProperty autoPilotTxt;//+
	public DoubleProperty throttle;//+
    public DoubleProperty rudder;//+
    public DoubleProperty aileron;//+
    public DoubleProperty elevator;//+
    public DoubleProperty sizeElement;//+
//    public BooleanProperty IsPath;
//    public DoubleProperty realtiveH, relativeW;
//    public StringProperty CurrentAltitude;//+++
	
    String[] GettingsNamesOrder = {"/instrumentation/airspeed-indicator/indicated-speed-kt", "/instrumentation/altimeter/pressure-alt-ft", "/instrumentation/attitude-indicator/indicated-pitch-deg", "/instrumentation/attitude-indicator/indicated-roll-deg", "/instrumentation/attitude-indicator/internal-pitch-deg",
    	      "/instrumentation/attitude-indicator/internal-roll-deg", "/instrumentation/encoder/indicated-altitude-ft", "/instrumentation/encoder/pressure-alt-ft", "/instrumentation/gps/indicated-altitude-ft", "/instrumentation/gps/indicated-ground-speed-kt",
    	      "/instrumentation/gps/indicated-vertical-speed", "/instrumentation/magnetic-compass/indicated-heading-deg", "/instrumentation/slip-skid-ball/indicated-slip-skid", "/instrumentation/turn-indicator/indicated-turn-rate", "/instrumentation/vertical-speed-indicator/indicated-speed-fpm", "/controls/flight/flaps", "/engines/engine/rpm",
    	    "/position/longitude-deg","/position/latitude-deg","/instrumentation/heading-indicator/indicated-heading-deg"
    	};
	
    public ViewModel() {
    	throttle=new SimpleDoubleProperty();
        rudder=new SimpleDoubleProperty();
        aileron=new SimpleDoubleProperty();
        elevator=new SimpleDoubleProperty();
        ip=new SimpleStringProperty();
        port=new SimpleStringProperty();
        ipPath=new SimpleStringProperty();
        portPath=new SimpleStringProperty();
        planeX=new SimpleDoubleProperty();
        planeY=new SimpleDoubleProperty();
        startX=new SimpleDoubleProperty();
        startY=new SimpleDoubleProperty();
        XDest=new SimpleDoubleProperty();
        YDest=new SimpleDoubleProperty();
        autoPilotTxt = new SimpleStringProperty();
        sizeElement = new SimpleDoubleProperty();
//        CurrentAltitude = new SimpleStringProperty();
//        isFirstCalc = new SimpleBooleanProperty();
        
    }
    
    public void parserAuto() {
    	
    	ArrayList<String> arrayLst = new ArrayList<String>();
    	Scanner myObj = new Scanner(autoPilotTxt.getValue());  // Create a Scanner object
    	while(myObj.hasNextLine()) {
    		String line = myObj.nextLine();
    		arrayLst.add(line);
    	}
    	
    	String[] arr = new String[arrayLst.size()];
    	
    	int i = 0;
    	for(String str:arrayLst) {
    		arr[i] = str;
    		i++;
    	}
    	
    	this.model.ParseAutoPilot(arr);
    }
    
    
    public void execute(){
        model.execute();
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
    	System.out.println("The Starting airplane is : "+planeX.getValue()+" y: "+planeY .getValue());
    	System.out.println("The Destiny airplane is : "+XDest.getValue()/3.33+" y: "+YDest.getValue());
    	System.out.println("height: "+height);
    	System.out.println("width: "+width);

//    	
//		double planertX = StartingPositionX.getValue(); //-158.021
//		planertX = planertX - planeX.getValue(); //-158.021 + 157.943
//		planertX = (int)(planertX/sizeOfElement.getValue() * -1); //-0.078 / 0.01652
//		System.out.print("i is:" + planertX);
//		double planertY = StartingPositionY.getValue();
//		planertY -= planeY.getValue();
//		planertY = (int)(planertY/sizeOfElement.getValue());
//		System.out.print("j is:" + planertY);
//		gcDrawPlane.drawImage(planepic, mapDisplayerData.width * planertX, mapDisplayerData.height * planertY, 25, 25);
//    	
//        model.findPath((int) (airplaneY.getValue()/-1), (int) (airplaneX.getValue() +15),Math.abs( (int) (markSceneY.getValue() / h)) ,
//                Math.abs((int) (markSceneX.getValue() / w)), data );
        
    	if(isFirstCalc) {
    		isFirstCalc = false;
    		model.connectMyServer(ipPath.getValue(),Integer.parseInt(portPath.getValue()));
    		System.out.println("init state: [0,0] = "+ (int) (planeX.getValue() +15)+" "+(int) (planeY.getValue()/-1)+"\n");
    		System.out.println("Destiny is : [0,0] = "+(int)((XDest.getValue()/3.33))+" "+(int)(YDest.getValue()*1)+"\n");
    		
    		model.findPath(4, 0, (int)(YDest.getValue()*1) ,(int)((XDest.getValue()/3.33)), mapData.mapData );
    	}

    }
    
    public void movePlain() {
        model.movePlain(aileron.getValue(), elevator.getValue());
    }

    public void setThrottle() {
    	model.setThrottle(throttle.getValue());
    }

    public void setRudder() {
    	model.setRudder(rudder.getValue());
    }
    
    
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg0==model)
        {

			//Sending the vars from a realtime via Server
			Boolean isExistIn = false;
			for(String str:GettingsNamesOrder) {
				if(arg1.equals(str))
					isExistIn = true;					
			}
			if(isExistIn) {
				setChanged();
				notifyObservers(arg1);
			}
			
	    }
			

	}
	
	
	

}
