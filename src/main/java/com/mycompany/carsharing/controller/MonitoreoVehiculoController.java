package com.mycompany.carsharing.controller;

import com.mycompany.carsharing.model.DatabaseManager;
import com.mycompany.carsharing.model.Vehiculo;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 */
public class MonitoreoVehiculoController {

    @FXML
    private WebView mapView;
    @FXML
    private ListView<Vehiculo> vehiculoListView;
    @FXML
    private Button actualizarButton;
    @FXML
    private Label statusLabel;

    // Simulación de base de datos
    private DatabaseManager dbManager = new DatabaseManager();

    @FXML
    public void initialize() {
        mapView.getEngine().loadContent(getGoogleMapsHTML());
        actualizarMonitoreo(); // Inicializar datos
    }

    @FXML
    public void actualizarMonitoreo() {
        try {
            List<Vehiculo> vehiculos = dbManager.getVehiculosEnUso();
            ObservableList<Vehiculo> observableVehiculos = FXCollections.observableArrayList(vehiculos);
            vehiculoListView.setItems(observableVehiculos);

            // Update map
            StringBuilder script = new StringBuilder("var markers = [");
            for (Vehiculo v : vehiculos) {
                script.append(String.format("{lat: %f, lng: %f},", v.getLatitud(), v.getLongitud()));
            }
            script.append("];");
            script.append("updateMarkers(markers);");
            
            // Asegúrate de que el WebEngine esté listo antes de ejecutar el script
            WebEngine engine = mapView.getEngine();
            engine.executeScript(script.toString());
            
            statusLabel.setText("Monitoreo actualizado: " + java.time.LocalDateTime.now());
        } catch (Exception e) {
            statusLabel.setText("Error al actualizar el monitoreo: " + e.getMessage());
            e.printStackTrace(); // Log the exception
        }
    }

    private String getGoogleMapsHTML() {
        return "<!DOCTYPE html>" +
               "<html>" +
               "<head>" +
               "    <style>html, body, #map { height: 100%; margin: 0; padding: 0; }</style>" +
               "    <script src=\"https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY\"></script>" +
               "    <script>" +
               "        var map;" +
               "        var markers = [];" +
               "        function initMap() {" +
               "            map = new google.maps.Map(document.getElementById('map'), {" +
               "                center: {lat: 0, lng: 0}," +
               "                zoom: 2" +
               "            });" +
               "        }" +
               "        function updateMarkers(newMarkers) {" +
               "            for (var i = 0; i < markers.length; i++) {" +
               "                markers[i].setMap(null);" +
               "            }" +
               "            markers = [];" +
               "            for (var i = 0; i < newMarkers.length; i++) {" +
               "                var marker = new google.maps.Marker({" +
               "                    position: newMarkers[i]," +
               "                    map: map" +
               "                });" +
               "                markers.push(marker);" +
               "            }" +
               "            if (markers.length > 0) {" +
               "                map.setCenter(markers[0].getPosition());" +
               "                map.setZoom(12);" +
               "            }" +
               "        }" +
               "    </script>" +
               "</head>" +
               "<body onload=\"initMap()\">" +
               "    <div id=\"map\"></div>" +
               "</body>" +
               "</html>";
    }
}
