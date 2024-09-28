/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carsharing.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.ObjectProperty;

import java.time.LocalDateTime;
import javafx.beans.property.SimpleDoubleProperty;

public class Reserva {

    private final IntegerProperty id;
    private final IntegerProperty userId;
    private final IntegerProperty vehicleId;
        private final SimpleDoubleProperty price;
    private final ObjectProperty<LocalDateTime> startTime;
    private final ObjectProperty<LocalDateTime> endTime;
    private final StringProperty status;

    // Constructor
    public Reserva(int id, int userId, int vehicleId, double price, LocalDateTime startTime, LocalDateTime endTime, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.userId = new SimpleIntegerProperty(userId);
        this.vehicleId = new SimpleIntegerProperty(vehicleId);
        
        this.startTime = new SimpleObjectProperty<>(startTime);
        this.endTime = new SimpleObjectProperty<>(endTime);
        this.status = new SimpleStringProperty(status);
        this.price = new SimpleDoubleProperty(price);
    }

    // Getters for properties (used by JavaFX)
    public IntegerProperty idProperty() {
        return id;
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public IntegerProperty vehicleIdProperty() {
        return vehicleId;
    }

    public ObjectProperty<LocalDateTime> startTimeProperty() {
        return startTime;
    }

    public ObjectProperty<LocalDateTime> endTimeProperty() {
        return endTime;
    }

    public StringProperty statusProperty() {
        return status;
    }

    // Standard getters
    public int getId() {
        return id.get();
    }

    public int getUserId() {
        return userId.get();
    }

    public int getVehicleId() {
        return vehicleId.get();
    }

    public LocalDateTime getStartTime() {
        return startTime.get();
    }

    public LocalDateTime getEndTime() {
        return endTime.get();
    }

    public String getStatus() {
        return status.get();
    }
    
       public double getPrice() {
        return price.get();
    }

    public void setMonto(double price) {
        this.price.set(price);
    }

    // Setters
    public void setId(int id) {
        this.id.set(id);
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId.set(vehicleId);
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime.set(startTime);
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime.set(endTime);
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
