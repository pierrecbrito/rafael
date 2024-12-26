package br.ufrn.eaj.tads.rafael.controller;


import br.ufrn.eaj.tads.rafael.dao.UsuarioDAO;
import br.ufrn.eaj.tads.rafael.model.Usuario;
import br.ufrn.eaj.tads.rafael.util.ScreenController;
import br.ufrn.eaj.tads.rafael.util.Session;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class InicialController {
	@FXML
	private TextField inputEmail;
	@FXML
	private PasswordField inputSenha;
	@FXML
	private Button btnEntrar;
	@FXML
	private Label labelErro;
	private UsuarioDAO usuarioDAO;
	
	@FXML
    private void initialize() {
       this.usuarioDAO = new UsuarioDAO();
    }
	

    @FXML
    private void entrar() {
        String email = inputEmail.getText();
        String senha = inputSenha.getText();
        
        Usuario usuario = this.usuarioDAO.logar(email, senha);
        
        if(usuario != null) {
        	Session.getInstance().setUsuario(usuario);
        	ScreenController.activate(ScreenController.DASH_SCREEN);
        } else {
        	labelErro.setText("Credenciais inválidas!");
        	labelErro.setVisible(true);
        	
            PauseTransition pause = new PauseTransition(Duration.seconds(4));
            pause.setOnFinished(event -> labelErro.setVisible(false));
            pause.play();
        }
    }
}
