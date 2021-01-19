package test;

import Commands.Utilities;
import Interpeter.Parser;

public class MyInterpreter {
	public Utilities ut;

	public MyInterpreter(Utilities ut) {
		this.ut = ut;
		  String[] start= {
				  "openDataServer 5400 10"
		  };
//	                "var breaks = bind /controls/flight/speedbrake",
//	                "var throttle = bind /controls/engines/current-engine/throttle",
//	                "var heading = bind /instrumentation/heading-indicator/indicated-heading-deg",
//	                "var airspeed = bind /instrumentation/airspeed-indicator/indicated-speed-kt",
//	                "var roll = bind /instrumentation/attitude-indicator/indicated-roll-deg",
//	                "var pitch = bind /instrumentation/attitude-indicator/internal-pitch-deg",
//	                "var rudder = bind /controls/flight/rudder",
//	                "var aileron = bind /controls/flight/aileron",
//	                "var elevator = bind /controls/flight/elevator",
//	                "var alt = bind /instrumentation/altimeter/indicated-altitude-ft",
//	                "var rpm = bind /engines/engine/rpm",
//	                "var hroute = 0",
//	                "var goal = 0"
//	                };
		  interpret(start);
	}
	public int interpret(String[] lines){
		
		Parser ps = new Parser();
		ps.ut = this.ut;
		ps.parse(lines); // Parsing all the Object and Vars
		
		
		return ps.returnedValue;
	}
}
