/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.carsharing.controller;


import com.mycompany.carsharing.model.DatabaseManager;
import com.mycompany.carsharing.model.Vehiculo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.util.Callback;
/**
 * FXML Controller class
 *
 * @author rperez
 */
public class Estado_vehiculoController  {
 @FXML
    private TableView<Vehiculo> tablaVehiculos;
    @FXML
    private TableColumn<Vehiculo, String> vehiculoCol;
    @FXML
    private TableColumn<Vehiculo, String> estadoCol;
    @FXML
    private TableColumn<Vehiculo, String> devolucionCol;
    @FXML
    private Button actualizarButton;
    @FXML
    private Label statusLabel;

    private DatabaseManager dbManager = new DatabaseManager();

    @FXML
    public void initialize() {
        // Configurar las columnas
        vehiculoCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        estadoCol.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());
        estadoCol.setCellFactory(createComboBoxCellFactory());
        devolucionCol.setCellValueFactory(cellData -> cellData.getValue().tiempoEstimadoRetornoProperty());

        // Cargar datos
        cargarDatos();
    }

    private void cargarDatos() {
        List<Vehiculo> vehiculos = dbManager.getVehiculos();
        ObservableList<Vehiculo> observableVehiculos = FXCollections.observableArrayList(vehiculos);
        tablaVehiculos.setItems(observableVehiculos);
    }

    @FXML
    private void actualizarEstado() {
        for (Vehiculo vehiculo : tablaVehiculos.getItems()) {
            dbManager.actualizarEstadoVehiculo(vehiculo);
        }
        statusLabel.setText("Estado actualizado: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    private Callback<TableColumn<Vehiculo, String>, TableCell<Vehiculo, String>> createComboBoxCellFactory() {
        return new Callback<TableColumn<Vehiculo, String>, TableCell<Vehiculo, String>>() {
            @Override
            public TableCell<Vehiculo, String> call(TableColumn<Vehiculo, String> param) {
                return new TableCell<Vehiculo, String>() {
                    private ComboBox<String> comboBox;

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            if (comboBox == null) {
                                comboBox = new ComboBox<>();
                                comboBox.getItems().addAll("Disponible", "Reservado", "En uso");
                                comboBox.setOnAction(event -> {
                                    Vehiculo vehiculo = getTableView().getItems().get(getIndex());
                                    vehiculo.setEstado(comboBox.getValue());
                                });
                            }
                            comboBox.setValue(item);
                            setGraphic(comboBox);
                        }
                    }
                };
            }
        };
    }
    
}
