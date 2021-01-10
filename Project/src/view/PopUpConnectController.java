package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PopUpConnectController{
	
	
	ViewModel vm;
	@FXML
	TextField portTextField;
	@FXML
	TextField ipTextField;
	
	
	public void setViewModel(ViewModel vm) {
		this.vm = vm;
		vm.port.bind(portTextField.textProperty());
		vm.ip.bind(ipTextField.textProperty());
		//If its the oposite way -> like I want a result  that will change when i got the result from the model i use it like this
		//resultLabel.textPropery().bind(vm.result.asString());
	}
	
	public void ConnectSimulator() {
		vm.Connect();
	}
	

}
