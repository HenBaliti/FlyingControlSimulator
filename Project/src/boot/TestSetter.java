package boot;

import server_side.ClientHandler;

// change this to match your code

import server_side.MySerialServer;
import server_side.Server;
import server_side.AdapterSearcherSolver;
import server_side.BFS;
import server_side.FileCacheManager;
import server_side.MyClientHandler;

public class TestSetter {
	

	static Server s; 
	
	public static void runServer(int port) {
		// put the code here that runs your server
		// for example:
		s=new MySerialServer(); 
		ClientHandler clientHandler = new MyClientHandler(new AdapterSearcherSolver<String,String>(new BFS()),new FileCacheManager<String,String>());
		s.open(port, clientHandler);
	}

	public static void stopServer() {
		// put the code here that stops your server
		// for example:
		s.stop();
	}
	

}