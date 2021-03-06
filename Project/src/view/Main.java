package view;
	
import java.util.Observer;

import Commands.MyServerGet;
import Commands.Utilities;
import Model.Model;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
			Parent root =loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("FlightGear Controller");
			primaryStage.show();
			MainWindowController mc = loader.getController();
			Utilities ut = new Utilities();
			
//			//Starting the Server
//
//			MyServerGet ms = new MyServerGet();
//			
//			new Thread(()->{
//			try {
//				ms.runServer();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}).start();
			
			ViewModel vm = new ViewModel();
			vm.ut = ut;
			Model model = new Model(ut);
			ut.addObserver(model);
			vm.setModel(model);
			model.addObserver(vm);
			vm.addObserver(mc);
			mc.setViewModel(vm,ut);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		
		
	}
}
