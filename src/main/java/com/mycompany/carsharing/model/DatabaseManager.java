package com.mycompany.carsharing.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/carsharing"; // URL de tu base de datos
    private static final String USER = "Rudy"; // Usuario de la BD
    private static final String PASS = "0923"; // Contraseña de la BD

    // Método para obtener la lista de vehículos desde la base de datos
    public List<Vehiculo> getVehiculos() {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String query = "SELECT id,brand, model, status , tiempo_estimado_retorno FROM vehicles";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Vehiculo vehiculo = new Vehiculo(
                    rs.getInt("id"),
                    rs.getString("status"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getString("tiempo_estimado_retorno")  // Placeholder
                );
                vehiculos.add(vehiculo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehiculos;
    }

    // Método para actualizar el estado de un vehículo en la base de datos
    public void actualizarEstadoVehiculo(Vehiculo vehiculo) {
        String query = "UPDATE vehicles SET status = ? WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, vehiculo.getStatus());
            pstmt.setInt(2, vehiculo.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener los vehículos que están "En uso"
    public List<Vehiculo> getVehiculosEnUso() {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String query = "SELECT id, status, '' , tiempo_estimado_retorno FROM vehicles WHERE status = 'En uso'";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Vehiculo vehiculo = new Vehiculo(
                    rs.getInt("id"),
                    rs.getString("status"),
                    rs.getString("tiempo_estimado_retorno"),
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude")// Placeholder
                );
                vehiculos.add(vehiculo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehiculos;
    }

    // Ejemplo para procesar un pago (asociado a una reserva)
    public boolean procesarPago(Reserva reserva) {
        String query = "UPDATE payments SET status = 'Pagado' WHERE reservation_id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, reserva.getId());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener reservas completadas (ejemplo)
    public List<Reserva> getReservasCompletadas() {
        List<Reserva> reservas = new ArrayList<>();
        String query = "SELECT id, user_id, vehicle_id, start_time, end_time, status FROM reservations WHERE status = 'completada'";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Reserva reserva = new Reserva(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("vehicle_id"),
                    rs.getDouble("price"),
                    rs.getTimestamp("start_time").toLocalDateTime(),
                    rs.getTimestamp("end_time").toLocalDateTime(),
                    rs.getString("status")
                );
                reservas.add(reserva);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }

// Agregar en DatabaseManager.java
public boolean procesarPagoTotal(List<Reserva> reservas) {
    String query = "UPDATE payments SET status = 'Pagado' WHERE reservation_id = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        for (Reserva reserva : reservas) {
            pstmt.setInt(1, reserva.getId());
            pstmt.addBatch();
        }

        int[] affectedRows = pstmt.executeBatch();
        return affectedRows.length == reservas.size();  // Verifica si todas las filas fueron actualizadas

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

 public boolean isVehicleAvailable(Vehiculo vehicle, String startDate, String endDate) throws SQLException {
        String query = "SELECT COUNT(*) FROM reservations WHERE vehicle_id = ? AND (start_date <= ? AND end_date >= ?)";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, vehicle.getId()); // Setting vehicle ID
        stmt.setString(2, endDate); // End date in query
        stmt.setString(3, startDate); // Start date in query

        ResultSet rs = stmt.executeQuery(); // Executing the query
        
        if (rs.next()) {
            int count = rs.getInt(1); // Getting the result count
            return count == 0; // Return true if no conflicts found
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Handle SQL exception
    }
    return false; // Return false if unavailable or query fails
    }
 
 public boolean reserveVehicle(Vehiculo vehicle, String startDate, String endDate, double price) {
        String query = "INSERT INTO reservations (vehicle_id, user_id, start_time, end_time, price, status) "
                     + "VALUES (?, ?, ?, ?, ?, 'Reservado')";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, vehicle.getId()); // Assuming vehicle has a getId() method
            stmt.setInt(2, 1); // Placeholder for user ID, replace with the actual user ID
            stmt.setString(3, startDate);
            stmt.setString(4, endDate);
            stmt.setDouble(5, price); // Set the price

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0; // Return true if the reservation was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
