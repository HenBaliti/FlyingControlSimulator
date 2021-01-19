package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;

import Commands.Utilities;
import test.MyInterpreter;

public class Model extends Observable{
	
	public Utilities ut;
	public MyInterpreter interperter;
	SimulatorClient simulatorClient;
    private static Socket socketMyServer;
    private  static PrintWriter outMyServer;
    private  static BufferedReader in;
    double startX;
    double startY;
    double planeX;
    double planeY;
    double markX;
    double markY;
    int[][] data;
	
	public Model(Utilities ut) {
		this.ut = ut;
		interperter = new MyInterpreter(ut);
		simulatorClient = new SimulatorClient();
	}
	
	//Controlling the Plane-------------------Start------------------------------
    public void movePlain(double aileron, double elevator) {
        String[] commands = {
                "set /controls/flight/aileron " + aileron,
                "set /controls/flight/elevator " + elevator,
        };
        simulatorClient.Send(commands);
    }

    public void setThrottle(double throttle) {
        String[] commands = {
                "set /controls/engines/current-engine/throttle " + throttle
        };
        simulatorClient.Send(commands);
    }

    public void setRudder(double rudder) {
        String[] commands = {
                "set /controls/flight/rudder " + rudder
        };
        simulatorClient.Send(commands);
    }
	
  //Controlling the Plane-----------------------End--------------------------
    
    
	//Parsing the AutoPilot Text
	public void ParseAutoPilot(String[] arr) {
		new Thread(()->interperter.interpret(arr)).start();
		
	}
	
	//Executing the interpeter
    public void execute()
    {
        //interpreter.execute();
    }
	

	
	//Connecting the simulator server as a client
	public void Connect(String ip,int port) {
		simulatorClient.Connect(ip, port);
	}
	
	//Conncetin to MyServer for calculating the path
    public void connectMyServer(String ip,int port){
        try {
            socketMyServer=new Socket(ip,port);
            System.out.println("Connected To Calc Server Succesfully");
            outMyServer=new PrintWriter(socketMyServer.getOutputStream());
            in=new BufferedReader(new InputStreamReader(socketMyServer.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public void findPath(int planeX,int planeY,int markX,int markY,int[][] data)
    {
        this.planeX=planeX;
        this.planeY=planeY;
        this.markX=markX;
        this.markY=markY;
        this.data=data;
        new Thread(()->{

                int j,i;
                System.out.println("\tSending problem to the server...");
                for (i = 0; i < data.length; i++) {
                    System.out.print("\t");
                    for (j = 0; j < data[i].length - 1; j++) {
                    	outMyServer.print(data[i][j] + ",");
                    	System.out.println(data[i][j] + ",");
                    }
                    outMyServer.println(data[i][j]);
                }
                outMyServer.println("end");

                outMyServer.println(planeX+","+planeY);

                outMyServer.println(markX+","+markY);

                outMyServer.flush();
    			System.out.println("\tend\n");
    			System.out.println("\tproblem sent, waiting for solution...");

                String solution = null;
                try {
                	solution = in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("\tSolution received");
                System.out.println((String)solution);
                
                String[] solTmp=solution.split(",");
                String[] notfiySolution=new String[solTmp.length+1];
                notfiySolution[0]="path";
                for(i=0;i<solTmp.length;i++)
                	notfiySolution[i+1]=solTmp[i];
                setChanged();
                notifyObservers(notfiySolution);

                

        }).start();
    }

	
}
