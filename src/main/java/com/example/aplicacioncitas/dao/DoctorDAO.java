package com.example.aplicacioncitas.dao;

import com.example.aplicacioncitas.model.Doctor;
import com.example.aplicacioncitas.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO
{

    public boolean registrarDoctor(Doctor doctor)
    {
        boolean exitoso = false;

        String sql = "INSERT INTO Doctor (Nombres, Apellido_paterno, Apellido_materno, Telefono_movil, Especialidad, Rating, id_consultorio) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {

            stmt.setString(1, doctor.getNombres());
            stmt.setString(2, doctor.getApellidoPat());
            stmt.setString(3, doctor.getApellidoMNat());
            stmt.setString(4, doctor.getTelefonoMovil());
            stmt.setString(5, doctor.getEspecialidad());
            stmt.setFloat(6, doctor.getRating());
            stmt.setInt(7, doctor.getIdConsultorio());

            int rowsInserted = stmt.executeUpdate();
            exitoso = rowsInserted > 0;

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return exitoso;
    }

    //Trae los nombres de todos los doctores
    public static List<String> obtenerNombresDoctores()
    {
        List<String> doctores = new ArrayList<>();
        String sql = "SELECT CONCAT(Nombres, ' ', Apellido_paterno, ' ', Apellido_materno) AS nombreCompleto FROM Doctor";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {

            while (rs.next())
            {
                doctores.add(rs.getString("nombreCompleto"));
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return doctores;
    }

    //Devuelve el id de un doctor según su nombre completo
    public static int obtenerIdPorNombre(String nombreCompleto)
    {
        String sql = "SELECT id_doctor, CONCAT(Nombres, ' ', Apellido_paterno, ' ', Apellido_materno) AS nombreCompleto " +
                "FROM doctor WHERE CONCAT(Nombres, ' ', Apellido_paterno, ' ', Apellido_materno) = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {

            pstmt.setString(1, nombreCompleto);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next())
            {
                return rs.getInt("id_doctor");
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return -1; // -1 indica que no encontró el doctor
    }
}
