package br.ufrn.eaj.tads.rafael.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class InicialController {
	@FXML
	private TextField inputEmail;
	@FXML
	private PasswordField inputSenha;
	@FXML
	private Button btnEntrar;
	@FXML
	private Label labelErro;
	
	@FXML
    private void initialize() {
        // Inicialização, se necessário
    }
	

    @FXML
    private void entrar() {
        String email = inputEmail.getText();
        String senha = inputSenha.getText();
        
        if(email.equals("admin") && senha.equals("admin")) {
        	System.out.println("Login realizado com sucesso!");
        } else {
        	labelErro.setText("Credenciais inválidas!");
        	labelErro.setVisible(true);
        }
    }
}
