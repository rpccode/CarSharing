package com.mycompany.carsharing.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;

/**
 * FXML Controller class
 *
 * @author rperez
 */
public class ReservationFormController implements Initializable {

    @FXML
    private ComboBox<String> vehicleComboBox;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private TextField startTimePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField endTimePicker;
    @FXML
    private Button checkAvailabilityButton;
    @FXML
    private Button reserveButton;
    @FXML
    private Label statusLabel;

    @FXML
    private void checkAvailability() {
        statusLabel.setText("Verificando disponibilidad...");
        new Thread(() -> {
            try {
                Thread.sleep(1000); // Simular delay de red
                javafx.application.Platform.runLater(() -> {
                    statusLabel.setText("Vehículo disponible para las fechas seleccionadas.");
                    reserveButton.setDisable(false);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void makeReservation() {
        statusLabel.setText("Procesando reserva...");
        new Thread(() -> {
            try {
                Thread.sleep(1000); // Simular delay de red
                javafx.application.Platform.runLater(() -> {
                    statusLabel.setText("Reserva completada con éxito.");
                    reserveButton.setDisable(true);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vehicleComboBox.setItems(FXCollections.observableArrayList("Vehículo 1", "Vehículo 2", "Vehículo 3"));
    }
}
