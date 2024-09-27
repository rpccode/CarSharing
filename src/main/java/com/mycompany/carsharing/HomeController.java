package com.mycompany.carsharing;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author rperez
 */
public class HomeController implements Initializable {
    @FXML
    private StackPane contentArea;

    @FXML
    private Button btnReservar, btnMonitorear, btnEstado, btnPagos, btnNotificaciones, btnHistorial;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Setup button actions
        btnReservar.setOnAction(event -> showView("ReservationForm.fxml"));
        btnMonitorear.setOnAction(event -> showView("MonitoreoVehiculo.fxml"));
        btnEstado.setOnAction(event -> showView("estado_vehiculo.fxml"));
        btnPagos.setOnAction(event -> showView("estado_vehiculo.fxml"));
        btnNotificaciones.setOnAction(event -> showView("estado_vehiculo.fxml"));
        btnHistorial.setOnAction(event -> showView("estado_vehiculo.fxml"));
    }
      private void showView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/carsharing/fxml/"+fxmlFile));
            Parent newView = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(newView);
        } catch (IOException e) {
            e.printStackTrace();
            // Aquí puedes manejar el error, como mostrar una alerta
        }
    }

}
