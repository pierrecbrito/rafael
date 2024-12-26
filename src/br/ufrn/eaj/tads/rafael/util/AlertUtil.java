package br.ufrn.eaj.tads.rafael.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertUtil {
	 public static void showAlert(String message) {
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Erro");
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	 }
}
