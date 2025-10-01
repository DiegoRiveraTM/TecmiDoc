package com.example.aplicacioncitas.dao;

import com.example.aplicacioncitas.model.Cita;
import com.example.aplicacioncitas.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO
{

    //Obtener todas las citas con nombres de doctor, paciente y consultorio
    public static List<Cita> obtenerCitas()
    {
        //Creamos una lista para guardar las citas
        List<Cita> citas = new ArrayList<>();

        //Traemos todos los atributos de SQL que necesitamos
        String sql = "SELECT c.id_cita, c.fecha_cita, c.monto, c.motivo,\n" +
                "       c.id_doctor, c.id_paciente, c.id_consultorio,\n" +
                "       CONCAT(d.Nombres, ' ', d.Apellido_paterno, ' ', d.Apellido_materno) AS nombre_doctor,\n" +
                "       CONCAT(p.Nombres, ' ', p.Apellido_paterno, ' ', p.Apellido_materno) AS nombre_paciente,\n" +
                "       cons.Nombre_consultorio AS nombre_consultorio\n" +
                "FROM cita c\n" +
                "JOIN doctor d ON c.id_doctor = d.id_doctor\n" +
                "JOIN paciente p ON c.id_paciente = p.id_paciente\n" +
                "JOIN consultorio cons ON c.id_consultorio = cons.id_consultorio;";


        //Intentamos conectarnos a la base de datos para obtener los datos de la cita
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cita cita = new Cita();
                cita.setIdCita(rs.getInt("id_cita"));
                cita.setFechaCita(rs.getTimestamp("fecha_cita").toLocalDateTime());
                cita.setMonto(rs.getFloat("monto"));
                cita.setMotivo(rs.getString("motivo"));
                cita.setNombreDoctor(rs.getString("nombre_doctor"));
                cita.setNombrePaciente(rs.getString("nombre_paciente"));
                cita.setNombreConsultorio(rs.getString("nombre_consultorio"));
                cita.setIdDoctor(rs.getInt("id_doctor"));
                cita.setIdPaciente(rs.getInt("id_paciente"));
                cita.setIdConsultorio(rs.getInt("id_consultorio"));


                citas.add(cita);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Regresamos la cita si se lograron sacar todos los datos de la cita
        return citas;
    }

    //Insertar nueva cita
    public static void insertarCita(Cita cita)
    {
        String sql = "INSERT INTO CITA (fecha_cita, Monto, Motivo, id_consultorio, id_doctor, id_paciente) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {

            pstmt.setTimestamp(1, Timestamp.valueOf(cita.getFechaCita()));
            pstmt.setFloat(2, cita.getMonto());
            pstmt.setString(3, cita.getMotivo());
            pstmt.setInt(4, cita.getIdConsultorio());
            pstmt.setInt(5, cita.getIdDoctor());
            pstmt.setInt(6, cita.getIdPaciente());

            pstmt.executeUpdate();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //Eliminar cita
    public static void eliminarCita(int idCita)
    {
        String sql = "DELETE FROM cita WHERE id_cita=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {

            pstmt.setInt(1, idCita);
            pstmt.executeUpdate();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //Actualizar una cita existente
    public static void actualizarCita(Cita cita)
    {
        String sql = "UPDATE cita SET fecha_cita = ?, monto = ?, motivo = ?, " +
                "id_consultorio = ?, id_doctor = ?, id_paciente = ? " +
                "WHERE id_cita = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {

            pstmt.setTimestamp(1, Timestamp.valueOf(cita.getFechaCita()));
            pstmt.setFloat(2, cita.getMonto());
            pstmt.setString(3, cita.getMotivo());
            pstmt.setInt(4, cita.getIdConsultorio());
            pstmt.setInt(5, cita.getIdDoctor());
            pstmt.setInt(6, cita.getIdPaciente());
            pstmt.setInt(7, cita.getIdCita());

            pstmt.executeUpdate();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    //Funcion para ver si el doctor esta disponible
    public static boolean doctorDisponible(int idDoctor, LocalDateTime fechaCita, Integer idCitaActual)
    {
        String sql = "SELECT COUNT(*) FROM cita WHERE id_doctor = ? AND fecha_cita = ?";

        //Si estamos editando una cita, excluimos la misma cita de la validación
        if (idCitaActual != null)
        {
            sql += " AND id_cita <> ?";
        }

        //Intentamos conectarnos con sql
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {

            //Definimos que atributos necesitaremos de Doctor
            pstmt.setInt(1, idDoctor);
            pstmt.setTimestamp(2, Timestamp.valueOf(fechaCita));

            //Si estamos editando, agregamos el id de la cita actual como parámetro para excluirla
            if (idCitaActual != null) {
                pstmt.setInt(3, idCitaActual);
            }

            //Ejecutamos la consulta y obtenemos el resultado
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
            {
                return rs.getInt(1) == 0; //true si está libre
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }



}
