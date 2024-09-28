package com.mycompany.carsharing.controller;

import com.mycompany.carsharing.model.DatabaseManager;
import com.mycompany.carsharing.model.Reserva;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ProcesamientoPagosController {

    @FXML
    private TableView<Reserva> tablaReservas;

    @FXML
    private ComboBox<String> metodoPagoComboBox;

    @FXML
    private Label totalLabel;

    @FXML
    private Button procesarPagoButton;

    @FXML
    private TextArea facturaTextArea;

    // Simulación de base de datos
    private DatabaseManager dbManager = new DatabaseManager();

    public void initialize() {
        cargarDatos();
        procesarPagoButton.setOnAction(e -> procesarPagoTotal());
    }

    public void cargarDatos() {
        List<Reserva> reservas = dbManager.getReservasCompletadas(); // Obtener reservas desde la base de datos
        ObservableList<Reserva> observableReservas = FXCollections.observableArrayList(reservas);
        tablaReservas.setItems(observableReservas);
        actualizarTotal();
    }

   private void actualizarTotal() {
    double total = tablaReservas.getItems().stream()
            .mapToDouble(reserva -> {
                // Eliminar el símbolo de moneda antes de convertir a double
                double price = reserva.getPrice();  // Elimina el primer carácter (el símbolo de moneda)
                return price;
            })
            .sum();
    
    // Actualizar el totalLabel con el formato adecuado
    totalLabel.setText(String.format("Total a Pagar: $%.2f", total));
}

    private void procesarPagoTotal() {
        boolean pagoExitoso = dbManager.procesarPagoTotal(tablaReservas.getItems());
        if (pagoExitoso) {
            generarFacturaTotal();
            tablaReservas.getItems().clear();
            actualizarTotal();
        } else {
            mostrarAlerta("Error en el pago", "No se pudo procesar el pago total.");
        }
    }

    private void generarFacturaTotal() {
        StringBuilder factura = new StringBuilder("Factura Total\n");
        factura.append("Fecha: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        factura.append("Método de Pago: ").append(metodoPagoComboBox.getValue()).append("\n\n");

        for (Reserva reserva : tablaReservas.getItems()) {
            factura.append(String.format("Usuario ID: %d, Vehículo ID: %d, Monto: %s\n",
                    reserva.getUserId(), reserva.getVehicleId(), reserva.getPrice()));
        }

        factura.append("\nTotal: ").append(totalLabel.getText());
        facturaTextArea.setText(factura.toString());
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
