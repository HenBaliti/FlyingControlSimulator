package boot;

import java.util.Random;

public class MainTrain {

	public static void main(String[] args) {
		//----------- ex1 --------------
//		DesignTest dt=new DesignTest();
//		TestSetter.setClasses(dt);
//		dt.testDesign();
		
		//----------- ex2 --------------
		// execution test (40 points)
		int port= 1234;
		TestSetter.runServer(port);

	}

}

