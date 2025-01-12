package br.ufrn.eaj.tads.rafael.controller;

import br.ufrn.eaj.tads.rafael.dao.ClienteDAO;
import br.ufrn.eaj.tads.rafael.model.Cliente;
import br.ufrn.eaj.tads.rafael.model.Pessoa;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddClienteController {

    @FXML
    private TextField nomeField;
    @FXML
    private TextField cpfField;
    @FXML
    private DatePicker dataNascimentoPicker;
    @FXML
    private TextField telefoneField;

    private Stage dialogStage;
    private boolean okClicked = false;
    
    private ClientesController clientesController;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }
    
	public void setClientesController(ClientesController clientesController) {
		this.clientesController = clientesController;
	}

    @FXML
    private void handleAddCliente() {
		if (isInputValid()) {
	        Cliente cliente = new Cliente();
	        Pessoa pessoa = new Pessoa();
	
	        pessoa.setNome(nomeField.getText());
	        pessoa.setDataNascimento(dataNascimentoPicker.getValue());
	        pessoa.setTelefone(telefoneField.getText());
	
	        cliente.setPessoa(pessoa);
	        cliente.setCpf(cpfField.getText());
	
	        ClienteDAO clienteDAO = new ClienteDAO();
	        Cliente novoCliente = clienteDAO.addCliente(cliente);
	        
			if (novoCliente == null) {
				 Alert alert = new Alert(AlertType.ERROR);
		         alert.initOwner(dialogStage);
		         alert.setTitle("Campos Inválidos");
		         alert.setHeaderText("Por favor, corrija os campos inválidos");
		         alert.setContentText("Erro ao adicionar cliente!");
		         alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(dialogStage);
				alert.setTitle("Sucesso");
				alert.setHeaderText("Cliente adicionado com sucesso!");
				alert.showAndWait();
				this.clientesController.updateTable();
			}
	
	        okClicked = true;
	        dialogStage.close();
		}
    }
    
    private boolean isInputValid() { String errorMessage = "";

	    if (nomeField.getText() == null || nomeField.getText().isEmpty()) {
	        errorMessage += "Nome inválido!\n";
	    }
	    if (cpfField.getText() == null || cpfField.getText().isEmpty()) {
	        errorMessage += "CPF inválido!\n";
	    }
	    if (dataNascimentoPicker.getValue() == null) {
	        errorMessage += "Data de nascimento inválida!\n";
	    }
	    if (telefoneField.getText() == null || telefoneField.getText().isEmpty()) {
	        errorMessage += "Telefone inválido!\n";
	    }

	    if (errorMessage.isEmpty()) {
	        return true;
	    } else {
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.initOwner(dialogStage);
	        alert.setTitle("Campos Inválidos");
	        alert.setHeaderText("Por favor, corrija os campos inválidos");
	        alert.setContentText(errorMessage);
	        alert.showAndWait();
	        return false;
	    }
    }
    
    @FXML
	private void handleCancel() {
		dialogStage.close();
	}
}