package Interpeter;

import java.util.Observable;
import java.util.Observer;

public class SymbolTabelObject extends Observable implements Observer {
	
	double value;
	String sim;

	@Override
	public void update(Observable o, Object arg) {
		Double num=new Double(0);
		if(arg.getClass()==(num.getClass()))
			if(this.value!=(double)arg) {
				this.setV((double) arg);
				this.setChanged();
				this.notifyObservers(arg+"");
			}


	}


// Constractor
	
	public SymbolTabelObject(double v) {
		this.value=v;
		this.sim=null;
	}
	public SymbolTabelObject() {

	}
	public SymbolTabelObject(String SIM) {
		super();
		sim = SIM;
	}

	public double getV() {
		return value;
	}

	public void setV(double v) {
		if(this.value!=v) {
			this.value = v;
			setChanged();
			notifyObservers(v);
		}


	}
	public String getSIM() {
		return sim;
	}
	public void setSIM(String SIM) {
		sim = SIM;
	}
}