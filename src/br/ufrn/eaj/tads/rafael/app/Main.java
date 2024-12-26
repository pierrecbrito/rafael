package br.ufrn.eaj.tads.rafael.app;
	
import br.ufrn.eaj.tads.rafael.util.AlertUtil;
import br.ufrn.eaj.tads.rafael.util.DatabaseUtil;
import br.ufrn.eaj.tads.rafael.util.ScreenController;
import br.ufrn.eaj.tads.rafael.util.Session;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	
	@Override
	public void start(Stage primaryStage) {
		try {
			ScreenController.setStage(primaryStage);
			ScreenController.activate(ScreenController.LOGIN_SCREEN);
	        DatabaseUtil.getConnection();       
		} catch(Exception e) {
			e.printStackTrace();
			AlertUtil.showAlert("Erro ao carregar a aplicação: " + e.getMessage());
		}
	}
	
	@Override
	public void stop() throws Exception {
		DatabaseUtil.closeConnection();
		Session.getInstance().invalidate();
		super.stop();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
