package br.ufrn.eaj.tads.rafael.controller;


import br.ufrn.eaj.tads.rafael.dao.EspecialidadeDAO;
import br.ufrn.eaj.tads.rafael.dao.MedicoDAO;
import br.ufrn.eaj.tads.rafael.model.Especialidade;
import br.ufrn.eaj.tads.rafael.model.Medico;
import br.ufrn.eaj.tads.rafael.model.Pessoa;
import br.ufrn.eaj.tads.rafael.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class AddMedicoController {

    @FXML
    private TextField inputNome;
    @FXML
    private TextField inputCRM;
    @FXML
    private DatePicker inputDataNascimento;
    @FXML
    private TextField inputTelefone;
    @FXML
    private TextField inputEmail;
    @FXML
    private PasswordField inputSenha;
    @FXML
    private ChoiceBox<Especialidade> inputEspecialidade;


    private Stage dialogStage;
    private boolean okClicked = false;
    private MedicosController medicosController;
    
    @FXML
    private void initialize() {
    	
    	 inputEspecialidade.setConverter(new StringConverter<Especialidade>() {
	        @Override
	        public String toString(Especialidade especialidade) {
	            return especialidade != null ? especialidade.getNome() : "";
	        }

	        @Override
	        public Especialidade fromString(String string) {
	            return inputEspecialidade.getItems().stream()
	                .filter(especialidade -> especialidade != null && especialidade.getNome().equals(string))
	                .findFirst()
	                .orElse(null);
	        }
	    });

	    new EspecialidadeDAO().listAll().forEach(especialidade -> {
	        if (especialidade != null) {
	            inputEspecialidade.getItems().add(especialidade);
	        }
	    });
	    
    	
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }
    
    @FXML
    private void handleAddMedico() {
		if (isInputValid()) {
			
			Medico medico = new Medico();
			Pessoa pessoa = new Pessoa();
			Usuario usuario = new Usuario();
			            
			pessoa.setNome(inputNome.getText());
			pessoa.setDataNascimento(inputDataNascimento.getValue());
			pessoa.setTelefone(inputTelefone.getText());
			
			usuario.setEmail(inputEmail.getText());
			usuario.setSenha(inputSenha.getText());	
			
			medico.setCrm(inputCRM.getText());
			medico.addEspecialidade(inputEspecialidade.getValue());
			
			medico.setPessoa(pessoa);
			medico.setUsuario(usuario);
			
			
			MedicoDAO daoMedico = new MedicoDAO();
			Medico novoMedico = daoMedico.addMedico(medico);
	        
	        
			if (novoMedico == null) {
				 Alert alert = new Alert(AlertType.ERROR);
		         alert.initOwner(dialogStage);
		         alert.setTitle("Campos Inválidos");
		         alert.setHeaderText("Por favor, corrija os campos inválidos");
		         alert.setContentText("Erro ao adicionar médico!");
		         alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(dialogStage);
				alert.setTitle("Sucesso");
				alert.setHeaderText("Médico adicionado com sucesso!");
				alert.showAndWait();
				this.medicosController.updateTable();
			}
	
	        okClicked = true;
	        dialogStage.close();
		}
    }
    
    private boolean isInputValid() { 
    	String errorMessage = "";

		if (inputNome.getText() == null || inputNome.getText().length() == 0) {
			errorMessage += "Nome inválido!\n";
		}
		
		if (inputCRM.getText() == null || inputCRM.getText().length() == 0) {
			errorMessage += "CRM inválido!\n";
		}
		
		if (inputDataNascimento.getValue() == null) {
			errorMessage += "Data de Nascimento inválida!\n";
		}
		
		if (inputTelefone.getText() == null || inputTelefone.getText().length() == 0) {
			errorMessage += "Telefone inválido!\n";
        }
		
		if (inputEmail.getText() == null || inputEmail.getText().length() == 0) {
			errorMessage += "Email inválido!\n";
        }
		
		if (inputSenha.getText() == null || inputSenha.getText().length() == 0) {
			errorMessage += "Senha inválida!\n";
		}
		
		if (inputEspecialidade.getValue() == null) {
			errorMessage += "Especialidade inválida!\n";
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

    public void setMedicosController(MedicosController medicosController) {
        this.medicosController = medicosController;
    }
    
    @FXML
   	private void handleCancel() {
   		dialogStage.close();
   	}

}
