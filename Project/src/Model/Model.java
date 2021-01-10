package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;

import test.MyInterpreter;

public class Model extends Observable{
	
	MyInterpreter interperter;
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
	
	public Model() {
		interperter = new MyInterpreter();
		simulatorClient = new SimulatorClient();
	}
	
	
	//Connecting the simulator server as a client
	public void Connect(String ip,int port) {
		simulatorClient.Connect(ip, port);
	}
	
	//Conncetin to MyServer for calculating the path
    public void connectMyServer(String ip,int port){
        try {
            socketMyServer=new Socket(ip,port);
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
                    }
                    outMyServer.println(data[i][j]);
                }
                outMyServer.println("end");
                outMyServer.println(planeX+","+planeY);
                outMyServer.println(markX+","+markY);
                outMyServer.flush();
                String solution = null;
                try {
                	solution = in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("\tSolution received");
                System.out.println(solution);
                
                String[] solTmp=solution.split(",");
                String[] notfiySolution=new String[solTmp.length+1];
                notfiySolution[0]="path";
                for(i=0;i<solTmp.length;i++)
                	notfiySolution[i+1]=solTmp[i];
                this.setChanged();
                this.notifyObservers(notfiySolution);

                

        }).start();
    }
	
}
