package Interpeter;

import java.util.Observable;
import java.util.Observer;

import Model.SimulatorClient;

public class SymbolTabelObject extends Observable implements Observer {

	double value;
	String sim;

	@Override
	public void update(Observable o, Object arg) {

			Double d = new Double(0);
	        if (arg.getClass() == (d.getClass())) {
	            if (this.value != (double) arg) {
	                this.setV((double) arg);
//	                System.out.println("There was update");
	                // if the current VarObject bound to VarObject
	                if (!this.getSIM().isEmpty()) { // check if the Sim param in VarObject is empty
	                    String msg = "set " + ((SymbolTabelObject)o).getSIM() + " " + this.getV(); // prepare msg "set ...... 5"
	                    String[] commands = {
	                            "set "+((SymbolTabelObject)o).getSIM()+" " + this.getV()
	                    };
	                    SimulatorClient.Send(commands);
	                    

//	                    SimulatorClient.out.println(msg); // send to server the message to change the parameter
//	                    System.out.println("My Client sendin to the simulator now : "+msg);
	                }
	            }
	        }
	}

	@Override
	public String toString() {
		return this.sim;
	}

	public SymbolTabelObject(double v) {
		this.value = v;
		this.sim = null;
	}

	public SymbolTabelObject(double v,String SIM) {
		this.value = v;
		this.sim = SIM;
	}

	public SymbolTabelObject(String SIM) {
		super();
		sim = SIM;
	}

	public double getV() {
		return value;
	}

	public void setV(double v) {
		if (this.value != v) {/////////////////////////////////////////////////////////////
			this.value = v;
			setChanged();
			notifyObservers(v);
		}

	}
	
	public void setVal(double v) {
		if (this.value != v) {
			this.value = v;

		}

	}

	public String getSIM() {
		return sim;
	}

	public void setSIM(String SIM) {
		sim = SIM;
	}

	public int calculate() {
		// TODO Auto-generated method stub
		return 0;
	}
}