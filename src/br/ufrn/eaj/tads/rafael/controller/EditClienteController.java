package br.ufrn.eaj.tads.rafael.controller;

import br.ufrn.eaj.tads.rafael.dao.ClienteDAO; import br.ufrn.eaj.tads.rafael.model.Cliente; import br.ufrn.eaj.tads.rafael.model.Pessoa; import javafx.fxml.FXML; import javafx.scene.control.Alert; import javafx.scene.control.Alert.AlertType; import javafx.scene.control.DatePicker; import javafx.scene.control.TextField; import javafx.stage.Stage;

public class EditClienteController {

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
private Cliente cliente;
private ClientesController clientesController;

public void setDialogStage(Stage dialogStage) {
    this.dialogStage = dialogStage;
}

public boolean isOkClicked() {
    return okClicked;
}

public void setCliente(Cliente cliente) {
    this.cliente = cliente;

    if (cliente != null) {
        Pessoa pessoa = cliente.getPessoa();
        nomeField.setText(pessoa.getNome());
        cpfField.setText(cliente.getCpf());
        dataNascimentoPicker.setValue(pessoa.getDataNascimento());
        telefoneField.setText(pessoa.getTelefone());
    }
}

public void setClientesController(ClientesController clientesController) {
    this.clientesController = clientesController;
}

@FXML
private void handleOk() {
    if (isInputValid()) {
        Pessoa pessoa = cliente.getPessoa();
        pessoa.setNome(nomeField.getText());
        pessoa.setDataNascimento(dataNascimentoPicker.getValue());
        pessoa.setTelefone(telefoneField.getText());

        cliente.setCpf(cpfField.getText());

        ClienteDAO clienteDAO = new ClienteDAO();
        boolean success = clienteDAO.updateCliente(cliente);

        if (!success) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao atualizar cliente");
            alert.setContentText("Não foi possível atualizar o cliente.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.initOwner(dialogStage);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Cliente atualizado com sucesso!");
            alert.showAndWait();
            this.clientesController.updateTable();
        }

        okClicked = true;
        dialogStage.close();
    }
}

@FXML
private void handleCancel() {
    dialogStage.close();
}

private boolean isInputValid() {
    String errorMessage = "";

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
}