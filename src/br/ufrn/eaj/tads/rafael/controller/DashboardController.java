package br.ufrn.eaj.tads.rafael.controller;

import java.io.IOException;

import br.ufrn.eaj.tads.rafael.util.Session;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class DashboardController {
	@FXML
	private Label labelUsername;
	@FXML
	private BorderPane borderPanelMain;
	
	@FXML
    private void initialize() {
       this.labelUsername.setText("Oi, " + Session.getInstance().getUsuario().getPessoa().getNome());
       
       try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufrn/eaj/tads/rafael/view/inicio.fxml"));
	        Parent vboxClientes = loader.load();
	        borderPanelMain.setCenter(vboxClientes);
	   } catch (IOException e) {
	        e.printStackTrace();
	   }
    }
	
	@FXML
    private void sair(MouseEvent event) {
		 Platform.exit();
    }
	
	@FXML
    private void selectClientes(MouseEvent event) {
		try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufrn/eaj/tads/rafael/view/clientes.fxml"));
	        Parent vboxClientes = loader.load();
	        borderPanelMain.setCenter(vboxClientes);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
	
	@FXML
    private void selectInicio(MouseEvent event) {
		try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufrn/eaj/tads/rafael/view/inicio.fxml"));
	        Parent vboxClientes = loader.load();
	        borderPanelMain.setCenter(vboxClientes);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
	
	@FXML
    private void selectMedicos(MouseEvent event) {
		try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufrn/eaj/tads/rafael/view/medicos.fxml"));
	        Parent vboxClientes = loader.load();
	        borderPanelMain.setCenter(vboxClientes);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }

}
