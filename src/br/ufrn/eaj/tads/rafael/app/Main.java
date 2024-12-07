package br.ufrn.eaj.tads.rafael.app;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			  	Parent root = FXMLLoader.load(getClass().getResource("/br/ufrn/eaj/tads/rafael/view/inicial.fxml"));
	            Scene scene = new Scene(root);
	            primaryStage.setScene(scene);
	            primaryStage.setTitle("Rafael - Gerenciamento de Hospital");
	            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/br/ufrn/eaj/tads/rafael/view/Logo-Rafael-removebg.png")));
	            primaryStage.setResizable(false);
	            primaryStage.initStyle(javafx.stage.StageStyle.UNDECORATED);
	            primaryStage.centerOnScreen();
	            primaryStage.show();
	            
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
