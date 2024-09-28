package com.mycompany.carsharing.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import com.mycompany.carsharing.model.DatabaseManager; // Suposición de dónde está la clase que maneja la BD
import com.mycompany.carsharing.model.Vehiculo; // Suposición de la clase Vehicle que representa a los vehículos
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author rperez
 */
public class ReservationFormController implements Initializable {

    @FXML
    private ComboBox<Vehiculo> vehicleComboBox;
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

    private DatabaseManager databaseManager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar el DatabaseManager (conexión a la base de datos)
        databaseManager = new DatabaseManager();
        
        List<Vehiculo> vehicles = databaseManager.getVehiculos(); // Método que obtiene los vehículos
        vehicleComboBox.setItems(FXCollections.observableArrayList(vehicles));
        // Utilizar un StringConverter para personalizar la visualización del Vehiculo en el ComboBox
vehicleComboBox.setConverter(new StringConverter<Vehiculo>() {
    @Override
    public String toString(Vehiculo vehicle) {
        // Aquí especificamos cómo queremos que se muestre cada Vehiculo
        return vehicle.getId() + " - " + vehicle.getBrand();
    }

    @Override
    public Vehiculo fromString(String string) {
        // No es necesario implementar esta parte a menos que necesites convertir de vuelta el string a un objeto Vehiculo
        return null;
    }
});

// Seleccionar el primer elemento del ComboBox (opcional)
if (!vehicles.isEmpty()) {
    vehicleComboBox.getSelectionModel().selectFirst();
}
        // Deshabilitar el botón de reserva hasta que se verifique la disponibilidad
        reserveButton.setDisable(true);
    }

    @FXML
    private void checkAvailability() {
        statusLabel.setText("Verificando disponibilidad...");
        reserveButton.setDisable(true);

        new Thread(() -> {
            try {
                // Simulación de verificación con retraso
                Thread.sleep(1000); // Simular delay de red

                // Aquí puedes implementar la lógica para verificar la disponibilidad en la base de datos
                boolean isAvailable = checkVehicleAvailability();

                javafx.application.Platform.runLater(() -> {
                    if (isAvailable) {
                        statusLabel.setText("Vehículo disponible para las fechas seleccionadas.");
                        reserveButton.setDisable(false);
                    } else {
                        statusLabel.setText("Vehículo no disponible para las fechas seleccionadas.");
                    }
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
                // Simulación de procesamiento con retraso
                Thread.sleep(1000); // Simular delay de red

                // Lógica para hacer la reserva en la base de datos
                boolean reservationSuccess = makeVehicleReservation();

                javafx.application.Platform.runLater(() -> {
                    if (reservationSuccess) {
                        statusLabel.setText("Reserva completada con éxito.");
                        reserveButton.setDisable(true);
                    } else {
                        statusLabel.setText("Error al completar la reserva.");
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Método para verificar la disponibilidad del vehículo en la base de datos
    private boolean checkVehicleAvailability() {
        try {
            Vehiculo selectedVehicle = vehicleComboBox.getValue();
            String startDate = startDatePicker.getValue().toString() + " " + startTimePicker.getText();
            String endDate = endDatePicker.getValue().toString() + " " + endTimePicker.getText();

            // Llamada a la base de datos para verificar disponibilidad
            return databaseManager.isVehicleAvailable(selectedVehicle, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para realizar la reserva en la base de datos
    private boolean makeVehicleReservation() {
        try {
            Vehiculo selectedVehicle = vehicleComboBox.getValue();
            String startDate = startDatePicker.getValue().toString() + " " + startTimePicker.getText();
            String endDate = endDatePicker.getValue().toString() + " " + endTimePicker.getText();

            // Llamada a la base de datos para hacer la reserva
            double price = 100.50;
            return databaseManager.reserveVehicle(selectedVehicle, startDate, endDate, price);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
