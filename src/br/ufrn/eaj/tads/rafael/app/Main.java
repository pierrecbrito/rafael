package br.ufrn.eaj.tads.rafael.app;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Main extends Application {
	private double xOffset = 0;
	private double yOffset = 0;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			  	Parent root = FXMLLoader.load(getClass().getResource("/br/ufrn/eaj/tads/rafael/view/inicial.fxml"));
	            Scene scene = new Scene(root);
	            primaryStage.setScene(scene);
	            primaryStage.setTitle("Rafael - Gerenciamento de Hospital");
	            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/br/ufrn/eaj/tads/rafael/view/favicon-wtbg.png")));
	            primaryStage.setResizable(false);
	            primaryStage.initStyle(javafx.stage.StageStyle.UNDECORATED);
	            primaryStage.centerOnScreen();
	            scene.setFill(Color.TRANSPARENT);
	            
	            primaryStage.initStyle(StageStyle.TRANSPARENT);
	            primaryStage.setScene(scene);
	         // Adicionar eventos de mouse para arrastar o Stage
	            
	            root.setOnMousePressed(event -> {
	                xOffset = event.getSceneX();
	                yOffset = event.getSceneY();
	            });

	            root.setOnMouseDragged(event -> {
	                primaryStage.setX(event.getScreenX() - xOffset);
	                primaryStage.setY(event.getScreenY() - yOffset);
	            });
	            
	            primaryStage.show();
	            
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
