package br.ufrn.eaj.tads.rafael.controller;

import br.ufrn.eaj.tads.rafael.util.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {
	@FXML
	private Label labelUsername;
	
	@FXML
    private void initialize() {
       this.labelUsername.setText("Oi, " + Session.getInstance().getUsuario().getNome());
    }
}
