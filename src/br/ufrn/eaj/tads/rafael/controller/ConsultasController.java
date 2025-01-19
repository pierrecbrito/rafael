package br.ufrn.eaj.tads.rafael.controller;

import br.ufrn.eaj.tads.rafael.model.Consulta;
import br.ufrn.eaj.tads.rafael.model.Especialidade;
import br.ufrn.eaj.tads.rafael.model.Medico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;

import br.ufrn.eaj.tads.rafael.dao.ConsultaDAO;
import br.ufrn.eaj.tads.rafael.dao.EspecialidadeDAO;

public class ConsultasController {

    @FXML
	private TreeTableView<Consulta> tableConsultas;
	@FXML
    private TreeTableColumn<Consulta, String> colNome;
	@FXML
	private TreeTableColumn<Consulta, String> colMedico;
    @FXML
    private TreeTableColumn<Consulta, String> colEspecialidade;
    @FXML
    private TreeTableColumn<Consulta, String> colDiaHora;
   
    @FXML
    private TextField inputPaciente;
    @FXML
    private TextField inputMedico;
    @FXML
    private ChoiceBox<Especialidade> inputEspecialidade;
    @FXML
    private DatePicker inputDia;
    
    @FXML
    private void initialize() {
    	updateTable();
    	
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
	    
	    ContextMenu contextMenu = new ContextMenu();
		
		MenuItem editItem = new MenuItem("Editar Consulta");
		editItem.setOnAction(event -> handleEditConsulta());
		contextMenu.getItems().add(editItem);
		
		MenuItem deleteItem = new MenuItem("Excluir Consulta");
		deleteItem.setOnAction(event -> handleDeleteConsulta());
		contextMenu.getItems().add(deleteItem);
		
		tableConsultas.setContextMenu(contextMenu);
    	
    }
    
    private void handleEditConsulta() {
    	TreeItem selectedItem = tableConsultas.getSelectionModel().getSelectedItem();
    	
		if (selectedItem != null && selectedItem.getValue() != null) {
			Consulta consulta = (Consulta) selectedItem.getValue();
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/br/ufrn/eaj/tads/rafael/view/EditConsulta.fxml"));
				Stage dialogStage = new Stage();
				dialogStage.setTitle("Editar Consulta");
				dialogStage.initModality(Modality.WINDOW_MODAL);
				dialogStage.setResizable(false);
				dialogStage.initStyle(StageStyle.UNDECORATED);
				dialogStage.initOwner(tableConsultas.getScene().getWindow());
				Scene scene = new Scene(loader.load());
				dialogStage.setScene(scene);

				EditConsultaController controller = loader.getController();
				controller.setDialogStage(dialogStage);
				controller.setConsulta(consulta);
				controller.setConsultasController(this);

				dialogStage.showAndWait();

				if (controller.isOkClicked()) {
					// Refresh the table or add the new client to the table
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else { 
				Alert alert = new Alert(Alert.AlertType.WARNING); 
				alert.setTitle("Nenhuma seleção");
				alert.setHeaderText("Nenhum Médico Selecionado"); 
				alert.setContentText("Por favor, selecione um médico na tabela."); 
				alert.showAndWait(); 
		} 

	}
    

	private void handleDeleteConsulta() {
		TreeItem selectedItem = tableConsultas.getSelectionModel().getSelectedItem(); 
		
		if (selectedItem != null && selectedItem.getValue() != null) { 
			Consulta consulta = (Consulta) selectedItem.getValue();
			ConsultaDAO consultaDAO = new ConsultaDAO();
			consultaDAO.deleteConsulta(consulta.getId());
			updateTable();
		} 
	}

	public void updateTable() {
		ConsultaDAO consultaDAO = new ConsultaDAO();
		ObservableList<Consulta> consultas = FXCollections.observableArrayList(consultaDAO.listAllConsultas());
		
		TreeItem<Consulta> root = new TreeItem<>(new Consulta());
		root.setExpanded(true);
		
		for (Consulta consulta : consultas) {
			TreeItem<Consulta> item = new TreeItem<>(consulta);
            root.getChildren().add(item);
		}
		
		colNome.setCellValueFactory(new TreeItemPropertyValueFactory<>("nomePaciente"));
		colMedico.setCellValueFactory(new TreeItemPropertyValueFactory<>("nomeMedico"));
		colEspecialidade.setCellValueFactory(new TreeItemPropertyValueFactory<>("especialidade"));
		colDiaHora.setCellValueFactory(new TreeItemPropertyValueFactory<>("diaHora"));

        tableConsultas.setRoot(root);
        tableConsultas.setShowRoot(false);
	}
    
    @FXML
	private void handlePesquisar() {
    	
		if (inputPaciente.getText().isEmpty() && inputMedico.getText().isEmpty() && inputEspecialidade.getValue() == null && inputDia.getValue() == null) { 
            updateTable(); 
            return;
        }
		
		String nome = inputPaciente.getText().trim(); 
		String medico = inputMedico.getText().trim(); 
		Especialidade especialidade = inputEspecialidade.getValue();
		LocalDate dia = inputDia.getValue();
		
		ConsultaDAO consultaDAO = new ConsultaDAO();
		ObservableList<Consulta> consultas = FXCollections.observableArrayList(consultaDAO.listAllConsultas());
		
		ObservableList<Consulta> filteredConsultas = FXCollections.observableArrayList();
		
		for (Consulta consulta : consultas) {
			boolean matches = true;

			if (!nome.isEmpty() && !consulta.getNomePaciente().contains(nome)) {
				matches = false;
			}
			if (!medico.isEmpty() && !consulta.getNomeMedico().contains(medico)) {
				matches = false;
			}
			if (especialidade != null && !consulta.getMedico().getEspecialidades().getFirst().equals(especialidade)) {
				matches = false;
			}
			if (dia != null && !consulta.getDiaHora().contains(dia.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")))) {
				matches = false;
			}
			if (matches) {
				filteredConsultas.add(consulta);
			}
		}
		
		TreeItem<Consulta> root = new TreeItem<>(new Consulta());
		root.setExpanded(true);
		
		for (Consulta consulta : filteredConsultas) {
			TreeItem<Consulta> item = new TreeItem<>(consulta);
			root.getChildren().add(item);
		}
		
		tableConsultas.setRoot(root);
		tableConsultas.setShowRoot(false);
	}
    
    @FXML
	private void handleResetar() {
		inputPaciente.clear();
		inputMedico.clear();
		inputEspecialidade.setValue(null);
		inputDia.setValue(null);
		updateTable();
	}

    
    @FXML
    private void handleNewConsulta() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/br/ufrn/eaj/tads/rafael/view/AddConsulta.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Marcar Consulta");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.initOwner(tableConsultas.getScene().getWindow());
            Scene scene = new Scene(loader.load());
            dialogStage.setScene(scene);
            
            AddConsultaController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setConsultasController(this);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
}
