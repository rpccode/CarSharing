package com.mycompany.carsharing.model;

public class Vehiculo {
    private final int id;
    private String licensePlate;
    private String brand;
    private String model;
    private String status;
    private double latitude;
    private double longitude;
    private String tiempoEstimadoRetorno;

    public Vehiculo(int id, String licensePlate, String brand, String model, String status, double latitude, double longitude) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        
    }
     public Vehiculo(int id, String status, String tiempoEstimadoRetorno, double latitud, double longitud) {
        this.id = id;
        this.status = status;
        this.tiempoEstimadoRetorno = tiempoEstimadoRetorno;
        this.latitude = latitud;
        this.longitude = longitud;
    }
     
       public Vehiculo(int id, String status,String brand, String model, String tiempoEstimadoRetorno) {
        this.id = id;
        this.status = status;
          this.brand = brand;
        this.model = model;
        this.tiempoEstimadoRetorno = tiempoEstimadoRetorno;
    }

        @Override
    public String toString() {
        return id + " - " + brand + " - " + model ; // Personalización de cómo se muestra el vehículo
    }

    // Getters and setters
    public int getId() { return id; }
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    // Properties for JavaFX binding
    public javafx.beans.property.StringProperty licensePlateProperty() {
        return new javafx.beans.property.SimpleStringProperty(licensePlate);
    }
    public javafx.beans.property.StringProperty tiempoEstimadoRetornoProperty() {
        return new javafx.beans.property.SimpleStringProperty(tiempoEstimadoRetorno);
    }
    public javafx.beans.property.IntegerProperty idProperty() {
        return new javafx.beans.property.SimpleIntegerProperty(id);
    } 

    public javafx.beans.property.StringProperty brandProperty() {
        return new javafx.beans.property.SimpleStringProperty(brand);
    }

    public javafx.beans.property.StringProperty modelProperty() {
        return new javafx.beans.property.SimpleStringProperty(model);
    }

    public javafx.beans.property.StringProperty statusProperty() {
        return new javafx.beans.property.SimpleStringProperty(status);
    }
}
