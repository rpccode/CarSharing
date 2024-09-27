/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carsharing.model;

/**
 *
 * @author rperez
 */
public class Vehiculo {
    private final String id;
    private String estado;
    private final String tiempoEstimadoRetorno;
    private double latitud;
    private double longitud;

    public Vehiculo(String id, String estado, String tiempoEstimadoRetorno) {
        this.id = id;
        this.estado = estado;
        this.tiempoEstimadoRetorno = tiempoEstimadoRetorno;
    }

    public javafx.beans.property.StringProperty idProperty() {
        return new javafx.beans.property.SimpleStringProperty(id);
    }

    public javafx.beans.property.StringProperty estadoProperty() {
        return new javafx.beans.property.SimpleStringProperty(estado);
    }

    public javafx.beans.property.StringProperty tiempoEstimadoRetornoProperty() {
        return new javafx.beans.property.SimpleStringProperty(tiempoEstimadoRetorno);
    }
     public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public String getId() { return id; }
    public String getEstado() { return estado; }
    public String getTiempoEstimadoRetorno() { return tiempoEstimadoRetorno; }

    public void setEstado(String estado) { this.estado = estado; }
}
