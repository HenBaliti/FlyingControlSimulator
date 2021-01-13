package view;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

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
    public StringProperty autoPilotTxt;//+
	public DoubleProperty throttle;//+
    public DoubleProperty rudder;//+
    public DoubleProperty aileron;//+
    public DoubleProperty elevator;//+
//    public BooleanProperty IsPath;
//    public DoubleProperty realtiveH, relativeW;
	
	
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
        autoPilotTxt = new SimpleStringProperty();
//        isFirstCalc = new SimpleBooleanProperty();
        
    }
    
    public void AutoPilotParser() {
    	
    	ArrayList<String> arrayLst = new ArrayList<String>();
    	Scanner myObj = new Scanner(autoPilotTxt.getValue());  // Create a Scanner object
    	while(myObj.hasNextLine()) {
    		String line = myObj.nextLine();
    		arrayLst.add(line);
    	}
    	
    	String[] arr = new String[arrayLst.size()];
    	
    	int i = 0;
    	for(String str:arr) {
    		arr[i] = str;
    		i++;
    	}
    	
    	this.model.ParseAutoPilot(arr);
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
    	System.out.println("rudder: "+rudder.getValue());
    	System.out.println("throttle: "+throttle.getValue());
    	System.out.println("ailron: "+aileron.getValue());
    	System.out.println("elevator: "+elevator.getValue());
    	
    	if(isFirstCalc) {
    		isFirstCalc = false;
    		airplaneX.setValue(startX.getValue());
    		airplaneY.setValue(startY.getValue());
    		model.connectMyServer(ipPath.getValue(),Integer.parseInt(portPath.getValue()));
    		System.out.println("init state: [0,0] = "+ (int) (airplaneX.getValue() +15)+" "+(int) (airplaneY.getValue()/-1)+"\n");
    		System.out.println("Destiny is : [0,0] = "+Math.abs((int) (XDest.getValue() / width))+" "+Math.abs((int) (YDest.getValue() / height))+"\n");
    		
    		model.findPath((int)(airplaneY.getValue()*1), (int) ((airplaneX.getValue())*-1),Math.abs( (int) (YDest.getValue() / height)) ,
                  Math.abs((int) (XDest.getValue() / width)), mapData.mapData );
    	}

    }
    
    
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg0==model)
        {
			System.out.println("Got heree1 ---------------------------");
            String[] tmpArr=(String[])arg1;
            int i=0,j=0;
            if(tmpArr[0].equals("path")){
            	System.out.println("Got heree2 ---------------------------");
            	//Drawing the path
            	for(int t=1;t<tmpArr.length;t++) {
            		switch(tmpArr[t]) {
                    case "Right":
                        j++;
                    	mapData.gc.setFill(Color.WHITE);
                    	mapData.gc.fillRect((j * mapData.width), (i * mapData.height), mapData.width, mapData.height);
//                        mapData.gc.setStroke(Color.BLACK.darker(),arg0.getX(), arg0.getY());
                        break;
                    case "Down":
//                    	mapData.gc.setStroke(Color.BLACK.darker());
                        i++;
                    	mapData.gc.setFill(Color.WHITE);
                    	mapData.gc.fillRect((j * mapData.width), (i * mapData.height), mapData.width, mapData.height);
                        break;
                    case "Left":
//                    	mapData.gc.setStroke(Color.BLACK.darker());
                        j--;
                    	mapData.gc.setFill(Color.WHITE);
                    	mapData.gc.fillRect((j * mapData.width), (i * mapData.height), mapData.width, mapData.height);
                        break;
                    case "Up":
//                    	mapData.gc.setStroke(Color.BLACK.darker());
                        i--;
                    	mapData.gc.setFill(Color.WHITE);
                    	mapData.gc.fillRect((j * mapData.width), (i * mapData.height), mapData.width, mapData.height);
                        break;
            		}
            	}
            }
        }
		else {
			System.out.println("Oooooopsss ---------------------------");
		}
	}
	
	
	

}
