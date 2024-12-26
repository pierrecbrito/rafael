package br.ufrn.eaj.tads.rafael.util;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ScreenController {
  	private static Stage stage;
    private static HashMap<String, String> screens = new HashMap<>();
	private static double xOffset = 0;
	private static double yOffset = 0;
    public static final String LOGIN_SCREEN = "loginScreen";
    public static final String DASH_SCREEN = "dashScreen";

    static {
    	screens.put(LOGIN_SCREEN, "/br/ufrn/eaj/tads/rafael/view/inicial.fxml");
        screens.put(DASH_SCREEN, "/br/ufrn/eaj/tads/rafael/view/dashboard.fxml");
    }
    
	public static void setStage(Stage stage) {
		ScreenController.stage = stage;
		stage.setTitle("Rafael - Gerenciamento de Hospital");
        stage.getIcons().add(new Image(ScreenController.class.getResourceAsStream("/br/ufrn/eaj/tads/rafael/view/favicon-wtbg.png")));
        stage.setResizable(false);
        stage.initStyle(javafx.stage.StageStyle.UNDECORATED);
        stage.centerOnScreen();
        stage.initStyle(StageStyle.TRANSPARENT);
	}

    public static void activate(String name) {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenController.class.getResource(screens.get(name)));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            System.setProperty("file.encoding", "UTF-8");
            scene.setFill(Color.TRANSPARENT);
            
            root.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });
            
            stage.show();
        } catch (IOException e) {
        	 e.printStackTrace();
            AlertUtil.showAlert("Erro ao tentar carregar a tela: " + e.getMessage());
        }
    }
}
