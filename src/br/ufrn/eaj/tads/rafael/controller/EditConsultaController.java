package br.ufrn.eaj.tads.rafael.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.eaj.tads.rafael.dao.ClienteDAO;
import br.ufrn.eaj.tads.rafael.dao.ConsultaDAO;
import br.ufrn.eaj.tads.rafael.dao.MedicoDAO;
import br.ufrn.eaj.tads.rafael.model.Cliente;
import br.ufrn.eaj.tads.rafael.model.Consulta;
import br.ufrn.eaj.tads.rafael.model.Especialidade;
import br.ufrn.eaj.tads.rafael.model.Medico;
import br.ufrn.eaj.tads.rafael.model.Pessoa;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class EditConsultaController {

    @FXML
    private ChoiceBox<Cliente> inputPaciente;
    @FXML
    private ChoiceBox<Medico> inputMedico;
    @FXML
    private DatePicker inputDataConsulta;
    @FXML
    private ChoiceBox<String> inputHoraConsulta;
    
    private Consulta consulta;
    private Stage dialogStage;
    private boolean okClicked = false;
    private ConsultasController consultasController;
    
    public void setConsulta(Consulta consulta) {
    	this.consulta = consulta;
    	inputPaciente.setValue(consulta.getCliente());
    	inputMedico.setValue(consulta.getMedico());
    	inputDataConsulta.setValue(consulta.getDataConsulta().toLocalDate());
    	inputHoraConsulta.setValue(consulta.getDataConsulta().toLocalTime().toString());
    }
    
    public Consulta getConsulta() {
    	return consulta;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }
    
	public void setConsultasController(ConsultasController consultasController) {
		this.consultasController = consultasController;
	}
	
	@FXML
    private void initialize() {
		populateChoiceBoxWithTimes();
		
		List<Cliente> clientes = new ClienteDAO().getAllClientesWithUsuarios();
		inputPaciente.getItems().addAll(clientes);
		
		List<Medico> medicos = new MedicoDAO().getAllMedicos();
		inputMedico.getItems().addAll(medicos);
		
		inputPaciente.setConverter(new StringConverter<Cliente>() {
			@Override
			public String toString(Cliente object) {
				return object != null ? object.getNome() + " (" + object.getCpf() + ")" : "";
			}

			@Override
			public Cliente fromString(String string) {
				String nome = string.substring(0, string.indexOf("(")).trim();
				String cpf = string.substring(string.indexOf("(") + 1, string.indexOf(")"));
				for (Cliente cliente : clientes) {
					if (cliente.getNome().equals(nome) && cliente.getCpf().equals(cpf)) {
						return cliente;
					}
				}
				return null;
			}
		});
		
		inputMedico.setConverter(new StringConverter<Medico>() {
            @Override
            public String toString(Medico object) {
                return object != null ? object.getNome() + " (" + object.getEspecialidade() + ")" : "";
            }

            @Override
            public Medico fromString(String string) {
                String nome = string.substring(0, string.indexOf("(")).trim();
                String especialidade = string.substring(string.indexOf("(") + 1, string.indexOf(")"));
                for (Medico medico : medicos) {
                    if (medico.getNome().equals(nome) && medico.getEspecialidade().equals(especialidade)) {
                        return medico;
                    }
                }
                return null;
            }
		});
			
	}

    @FXML
    private void handleEditConsulta() {
		if (isInputValid()) {
		
			consulta.setCliente(inputPaciente.getValue());
			consulta.setMedico(inputMedico.getValue());
			LocalTime time = LocalTime.parse(inputHoraConsulta.getValue());
		    LocalDateTime dateTime = inputDataConsulta.getValue().atTime(time);
			consulta.setDataConsulta(dateTime);
			
			if (!new ConsultaDAO().updateConsulta(consulta)) {
				 Alert alert = new Alert(AlertType.ERROR);
		         alert.initOwner(dialogStage);
		         alert.setTitle("Campos Inválidos");
		         alert.setHeaderText("Por favor, corrija os campos inválidos");
		         alert.setContentText("Erro ao marcar consulta!");
		         alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(dialogStage);
				alert.setTitle("Sucesso");
				alert.setHeaderText("Consulta editada com sucesso!");
				alert.showAndWait();
				this.consultasController.updateTable();
			}
	
	        okClicked = true;
	        dialogStage.close();
		}
    }
    
    private boolean isInputValid() { 
    	String errorMessage = "";

		if (inputPaciente.getValue() == null) {
			errorMessage += "Paciente inválido!\n";
		}
		
		if (inputMedico.getValue() == null) {
			errorMessage += "Médico inválido!\n";
		}
		
		if (inputDataConsulta.getValue() == null) {
			errorMessage += "Data inválida!\n";
		}
		
		if (inputHoraConsulta.getValue() == null) {
			errorMessage += "Hora inválida!\n";
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
    
    private void populateChoiceBoxWithTimes() {
         List<String> horas = new ArrayList<>();
         horas.add("08:00");
         horas.add("08:30");
         horas.add("09:00");
         horas.add("09:30");
         horas.add("10:00");
         horas.add("10:30");
         horas.add("11:00");
         horas.add("11:30");
         horas.add("12:00");
         horas.add("12:30");
         horas.add("13:00");
         horas.add("13:30");
         horas.add("14:00");
         horas.add("14:30");
         horas.add("15:00");
         horas.add("15:30");
         horas.add("16:00");
         horas.add("16:30");
         horas.add("17:00");
         horas.add("17:30");
         horas.add("18:00");
         horas.add("18:30");
         horas.add("19:00");
         horas.add("19:30");
         horas.add("20:00");
         
         inputHoraConsulta.getItems().addAll(horas);
    }
    
    @FXML
	private void handleCancel() {
		dialogStage.close();
	}
}