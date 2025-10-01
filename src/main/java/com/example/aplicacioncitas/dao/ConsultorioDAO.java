package com.example.aplicacioncitas.dao;

import com.example.aplicacioncitas.model.Consultorio;
import com.example.aplicacioncitas.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultorioDAO
{

    public boolean registrarConsultorio(Consultorio consultorio)
    {
        boolean exitoso = false;

        String sql = "INSERT INTO Consultorio (Nombre_consultorio, Calle, Num_interior, Num_exterior, Colonia, Ciudad, Estado, Codigo_postal, telefono_fijo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, consultorio.getNombreConsultorio());
            stmt.setString(2, consultorio.getCalle());
            stmt.setString(3, consultorio.getNumInterior());
            stmt.setString(4, consultorio.getNumExterior());
            stmt.setString(5, consultorio.getColonia());
            stmt.setString(6, consultorio.getCiudad());
            stmt.setString(7, consultorio.getEstado());
            stmt.setString(8, consultorio.getCodigoPostal());
            stmt.setString(9, consultorio.getTelefonoFijo());

            int rowsInserted = stmt.executeUpdate();
            exitoso = rowsInserted > 0;

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return exitoso;
    }
    //Traer todos los nombres de consultorios
    public static List<String> obtenerNombresConsultorios()
    {
        List<String> consultorios = new ArrayList<>();
        String sql = "SELECT id_consultorio, Nombre_consultorio FROM Consultorio";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {

            while (rs.next())
            {
                consultorios.add(rs.getString("Nombre_consultorio"));
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return consultorios;
    }

    //Obtener id del consultorio por nombre
    public static int obtenerIdPorNombre(String nombre)
    {
        String sql = "SELECT id_consultorio FROM consultorio WHERE Nombre_consultorio = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {

            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next())
            {
                return rs.getInt("id_consultorio");
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return -1; //si no encuentra
    }
}
