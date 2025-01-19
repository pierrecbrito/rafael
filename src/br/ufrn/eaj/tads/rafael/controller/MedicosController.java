package br.ufrn.eaj.tads.rafael.controller;

import br.ufrn.eaj.tads.rafael.dao.MedicoDAO;
import br.ufrn.eaj.tads.rafael.model.Cliente;
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

import br.ufrn.eaj.tads.rafael.dao.EspecialidadeDAO;

public class MedicosController {

    @FXML
	private TreeTableView<Medico> tableMedicos;
	@FXML
    private TreeTableColumn<Medico, String> colNome;
    @FXML
    private TreeTableColumn<Medico, String> colEspecialidade;
    @FXML
    private TreeTableColumn<Medico, String> colCRM;
    @FXML
    private TreeTableColumn<Medico, String> colEmail;
    @FXML
    private TextField inputNome;
    @FXML
    private TextField inputCRM;
    @FXML
    private ChoiceBox<Especialidade> inputEspecialidade;
    
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
		
		MenuItem editItem = new MenuItem("Editar Médico");
		editItem.setOnAction(event -> handleEditCliente());
		contextMenu.getItems().add(editItem);
		
		MenuItem deleteItem = new MenuItem("Excluir Médico");
		deleteItem.setOnAction(event -> handleDeleteCliente());
		contextMenu.getItems().add(deleteItem);
		
		tableMedicos.setContextMenu(contextMenu);
    	
    }
    
    private void handleEditCliente() {
    	TreeItem selectedItem = tableMedicos.getSelectionModel().getSelectedItem();
    	
		if (selectedItem != null && selectedItem.getValue() != null) {
			Medico medico = (Medico) selectedItem.getValue();
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/br/ufrn/eaj/tads/rafael/view/EditMedico.fxml"));
				Stage dialogStage = new Stage();
				dialogStage.setTitle("Editar Cliente");
				dialogStage.initModality(Modality.WINDOW_MODAL);
				dialogStage.setResizable(false);
				dialogStage.initStyle(StageStyle.UNDECORATED);
				dialogStage.initOwner(tableMedicos.getScene().getWindow());
				Scene scene = new Scene(loader.load());
				dialogStage.setScene(scene);

				EditMedicoController controller = loader.getController();
				controller.setDialogStage(dialogStage);
				controller.setMedico(medico);
				controller.setMedicosController(this);

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
    

	private void handleDeleteCliente() {
		TreeItem selectedItem = tableMedicos.getSelectionModel().getSelectedItem(); 
		if (selectedItem != null && selectedItem.getValue() != null) { 
			Medico medico = (Medico) selectedItem.getValue();
			MedicoDAO medicoDAO = new MedicoDAO();
			medicoDAO.deleteMedico(medico);
			updateTable();
		} 
	}

	public void updateTable() {
		MedicoDAO medicDAO = new MedicoDAO();
		ObservableList<Medico> medicos = FXCollections.observableArrayList(medicDAO.getAllMedicos());
       
        TreeItem<Medico> root = new TreeItem<>(new Medico());
        root.setExpanded(true);

        for (Medico medico : medicos) {
            TreeItem<Medico> item = new TreeItem<>(medico);
            root.getChildren().add(item);
        }
        

        colNome.setCellValueFactory(new TreeItemPropertyValueFactory<>("nome"));
        colEspecialidade.setCellValueFactory(new TreeItemPropertyValueFactory<>("especialidade"));
        colCRM.setCellValueFactory(new TreeItemPropertyValueFactory<>("crm"));
        colEmail.setCellValueFactory(new TreeItemPropertyValueFactory<>("email"));

        tableMedicos.setRoot(root);
        tableMedicos.setShowRoot(false);
	}
    
    @FXML
	private void handlePesquisar() {
    	
		if (inputNome.getText().isEmpty() && inputCRM.getText().isEmpty() && inputEspecialidade.getValue() == null) { 
            updateTable(); 
            return;
        }
		
		String nome = inputNome.getText().trim(); 
		String crm = inputCRM.getText().trim(); 
		Especialidade especialidade = inputEspecialidade.getValue();
		
		MedicoDAO medicoDAO = new MedicoDAO();
		ObservableList<Medico> medicos = FXCollections.observableArrayList(medicoDAO.getAllMedicos());
		
		ObservableList<Medico> filteredMedicos = FXCollections.observableArrayList();
		
		for (Medico medico : medicos) {
			boolean matches = true;
			
			if (!nome.isEmpty() && !medico.getNome().contains(nome)) {
				matches = false;
			}
			if (!crm.isEmpty() && !medico.getCrm().contains(crm)) {
				matches = false;
			}
			if (especialidade != null && !medico.getEspecialidades().contains(especialidade)) {
				matches = false;
			}
			if (matches) {
				filteredMedicos.add(medico);
			}
		}
		
		TreeItem<Medico> root = new TreeItem<>(new Medico());
		root.setExpanded(true);
		
		for (Medico medico : filteredMedicos) {
			TreeItem<Medico> item = new TreeItem<>(medico);
			root.getChildren().add(item);
		}
		
		tableMedicos.setRoot(root);
		tableMedicos.setShowRoot(false);

	}
    
    @FXML
	private void handleResetar() {
		inputNome.clear();
		inputCRM.clear();
		inputEspecialidade.setValue(null);
		updateTable();
	}

    
    @FXML
    private void handleNewMedico() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/br/ufrn/eaj/tads/rafael/view/AddMedico.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Adicionar Médico");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.initOwner(tableMedicos.getScene().getWindow());
            Scene scene = new Scene(loader.load());
            dialogStage.setScene(scene);
            
            AddMedicoController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMedicosController(this);

            dialogStage.showAndWait();

            if (controller.isOkClicked()) {
                // Refresh the table or add the new client to the table
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
}
