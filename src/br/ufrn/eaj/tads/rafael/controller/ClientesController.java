package br.ufrn.eaj.tads.rafael.controller;

import java.io.IOException;
import java.time.LocalDate;
import br.ufrn.eaj.tads.rafael.dao.ClienteDAO;
import br.ufrn.eaj.tads.rafael.model.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

public class ClientesController {
	@FXML
	private TreeTableView<Cliente> tableClientes;
	@FXML
    private TreeTableColumn<Cliente, String> colNome;
    @FXML
    private TreeTableColumn<Cliente, String> colCPF;
    @FXML
    private TreeTableColumn<Cliente, LocalDate> colNascimento;
    @FXML
    private TreeTableColumn<Cliente, String> colTelefone;
    @FXML
    private TextField inputNome;
    @FXML
    private TextField inputCPF;
    @FXML
    private TextField inputTelefone;
    
	
	@FXML
    private void initialize() {
		ContextMenu contextMenu = new ContextMenu();
		
		MenuItem editItem = new MenuItem("Editar Cliente");
		editItem.setOnAction(event -> handleEditCliente());
		contextMenu.getItems().add(editItem);
		
		MenuItem deleteItem = new MenuItem("Excluir Cliente");
		deleteItem.setOnAction(event -> handleDeleteCliente());
		contextMenu.getItems().add(deleteItem);
		
		// Set context menu on the table
		tableClientes.setContextMenu(contextMenu);
		
		updateTable();
    }
	
	public void updateTable() {
		ClienteDAO clienteDAO = new ClienteDAO();

        ObservableList<Cliente> clientes = FXCollections.observableArrayList(
            clienteDAO.getAllClientesWithUsuarios()
        );

        TreeItem<Cliente> root = new TreeItem<>(new Cliente());
        root.setExpanded(true);

        for (Cliente cliente : clientes) {
            TreeItem<Cliente> item = new TreeItem<>(cliente);
            root.getChildren().add(item);
        }

        colNome.setCellValueFactory(new TreeItemPropertyValueFactory<>("nome"));
        colCPF.setCellValueFactory(new TreeItemPropertyValueFactory<>("cpf"));
        colNascimento.setCellValueFactory(new TreeItemPropertyValueFactory<>("dataNascimento"));
        colTelefone.setCellValueFactory(new TreeItemPropertyValueFactory<>("telefone"));

        tableClientes.setRoot(root);
        tableClientes.setShowRoot(false);
	}
	
	@FXML
    private void handleNewCliente() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/br/ufrn/eaj/tads/rafael/view/AddCliente.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Adicionar Cliente");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.initOwner(tableClientes.getScene().getWindow());
            Scene scene = new Scene(loader.load());
            dialogStage.setScene(scene);
            
            AddClienteController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setClientesController(this);

            dialogStage.showAndWait();

            if (controller.isOkClicked()) {
                // Refresh the table or add the new client to the table
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	

	private void handleDeleteCliente() { 
		TreeItem selectedItem = tableClientes.getSelectionModel().getSelectedItem(); 
		if (selectedItem != null && selectedItem.getValue() != null) { 
			Cliente cliente = (Cliente) selectedItem.getValue(); 
			ClienteDAO clienteDAO = new ClienteDAO(); 
			clienteDAO.deleteCliente(cliente); 
			updateTable(); 
		} else { // Show an alert if no client is selected Alert alert = new Alert(Alert.AlertType.WARNING); alert.setTitle("Nenhuma seleção"); alert.setHeaderText("Nenhum Cliente Selecionado"); alert.setContentText("Por favor, selecione um cliente na tabela."); alert.showAndWait(); } }
		}
	}
	
	public void handleEditCliente( ) {
		TreeItem selectedItem = tableClientes.getSelectionModel().getSelectedItem();
		if (selectedItem != null && selectedItem.getValue() != null) {
			Cliente cliente = (Cliente) selectedItem.getValue();
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/br/ufrn/eaj/tads/rafael/view/EditCliente.fxml"));
				Stage dialogStage = new Stage();
				dialogStage.setTitle("Editar Cliente");
				dialogStage.initModality(Modality.WINDOW_MODAL);
				dialogStage.setResizable(false);
				dialogStage.initStyle(StageStyle.UNDECORATED);
				dialogStage.initOwner(tableClientes.getScene().getWindow());
				Scene scene = new Scene(loader.load());
				dialogStage.setScene(scene);

				EditClienteController controller = loader.getController();
				controller.setDialogStage(dialogStage);
				controller.setCliente(cliente);
				controller.setClientesController(this);

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
				alert.setHeaderText("Nenhum Cliente Selecionado"); 
				alert.setContentText("Por favor, selecione um cliente na tabela."); 
				alert.showAndWait(); 
			} 
	}
	
	
	@FXML 
	private void handlePesquisar() { 
		
		if (inputNome.getText().isEmpty() && inputCPF.getText().isEmpty() && inputTelefone.getText().isEmpty()) { 
            updateTable(); 
            return;
        }
		
		String nome = inputNome.getText().trim(); 
		String cpf = inputCPF.getText().trim(); 
		String telefone = inputTelefone.getText().trim();

		ClienteDAO clienteDAO = new ClienteDAO();
		ObservableList<Cliente> clientes = FXCollections.observableArrayList(
		    clienteDAO.getAllClientesWithUsuarios()
		);

		ObservableList<Cliente> filteredClientes = FXCollections.observableArrayList();

		for (Cliente cliente : clientes) {
		    boolean matches = true;
		    if (!nome.isEmpty() && !cliente.getNome().contains(nome)) {
		        matches = false;
		    }
		    if (!cpf.isEmpty() && !cliente.getCpf().contains(cpf)) {
		        matches = false;
		    }
		    if (!telefone.isEmpty() && !cliente.getTelefone().contains(telefone)) { // Replace getOtherField() with the actual method to get the field value
		        matches = false;
		    }
		    if (matches) {
		        filteredClientes.add(cliente);
		    }
		}

		TreeItem<Cliente> root = new TreeItem<>(new Cliente());
		root.setExpanded(true);

		for (Cliente cliente : filteredClientes) {
		    TreeItem<Cliente> item = new TreeItem<>(cliente);
		    root.getChildren().add(item);
		}

		tableClientes.setRoot(root);
		tableClientes.setShowRoot(false);
	}
	
	@FXML
	public void handleResetar() {
		inputNome.clear();
		inputCPF.clear();
		inputTelefone.clear();
		updateTable();
	}
}
