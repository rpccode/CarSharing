/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carsharing.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author rperez
 */
public class DatabaseManager {
     public List<Vehiculo> getVehiculos() {
        // Simulación: retorna una lista de vehículos
        return Arrays.asList(
            new Vehiculo("V001", "Disponible", "-"),
            new Vehiculo("V002", "En uso", "30 min"),
            new Vehiculo("V003", "Reservado", "2 horas")
        );
    }

    public void actualizarEstadoVehiculo(Vehiculo vehiculo) {
        // Simulación: actualiza el estado del vehículo en la base de datos
        System.out.println("Actualizando estado de " + vehiculo.getId() + " a " + vehiculo.getEstado());
    }

 public List<Vehiculo> getVehiculosEnUso() {
    // Filter the list to only return vehicles that are "En uso"
    return getVehiculos().stream()
            .filter(vehiculo -> "En uso".equals(vehiculo.getEstado()))
            .collect(Collectors.toList());
}


  
}
