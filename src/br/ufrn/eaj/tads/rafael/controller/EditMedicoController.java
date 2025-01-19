package br.ufrn.eaj.tads.rafael.controller;


import br.ufrn.eaj.tads.rafael.dao.EspecialidadeDAO;
import br.ufrn.eaj.tads.rafael.dao.MedicoDAO;
import br.ufrn.eaj.tads.rafael.model.Especialidade;
import br.ufrn.eaj.tads.rafael.model.Medico;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class EditMedicoController {

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
    private ChoiceBox<Especialidade> inputEspecialidade;
    private Medico medico;


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
    
    public void setMedico(Medico medico) {
        this.medico = medico;

        if (medico != null) {
            inputNome.setText(medico.getPessoa().getNome());
            inputCRM.setText(medico.getCrm());
            inputDataNascimento.setValue(medico.getPessoa().getDataNascimento());
            inputTelefone.setText(medico.getPessoa().getTelefone());
            inputEmail.setText(medico.getUsuario().getEmail());
            inputEspecialidade.setValue(medico.getEspecialidades().get(0));
        }
    }
    
    @FXML
    private void handleEditMedico() {
    	
		if (isInputValid()) {		            
			this.medico.getPessoa().setNome(inputNome.getText());
			this.medico.getPessoa().setDataNascimento(inputDataNascimento.getValue());
			this.medico.getPessoa().setTelefone(inputTelefone.getText());
			
			this.medico.getUsuario().setEmail(inputEmail.getText());
	
			this.medico.setCrm(inputCRM.getText());
			
			if(inputEspecialidade.getValue() != this.medico.getEspecialidades().getFirst()) {
				this.medico.getEspecialidades().remove(0);
				medico.addEspecialidade(inputEspecialidade.getValue());
			}
			
			MedicoDAO daoMedico = new MedicoDAO();
			boolean resultado = daoMedico.updateMedico(medico);
	        
			if (!resultado) {
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
				alert.setHeaderText("Médico editado com sucesso!");
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
