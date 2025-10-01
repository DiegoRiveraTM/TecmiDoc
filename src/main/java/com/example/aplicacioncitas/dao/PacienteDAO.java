package com.example.aplicacioncitas.dao;

import com.example.aplicacioncitas.model.Paciente;
import com.example.aplicacioncitas.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PacienteDAO
{

    public boolean registrarPaciente(Paciente paciente)
    {
        boolean exitoso = false;

        String sql = "INSERT INTO Paciente (Nombres, Apellido_paterno, Apellido_materno, edad, peso, altura, sexo, telefono, correo_electronico, contrasena, antecedentes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {

            stmt.setString(1, paciente.getNombre());
            stmt.setString(2, paciente.getApellidoPat());
            stmt.setString(3, paciente.getApellidoMat());
            stmt.setInt(4, paciente.getEdad());
            stmt.setFloat(5, paciente.getPeso());
            stmt.setFloat(6, paciente.getAltura());
            stmt.setString(7, paciente.getSexo());
            stmt.setString(8, paciente.getTelefono());
            stmt.setString(9, paciente.getEmail());
            stmt.setString(10, paciente.getContrasena());
            stmt.setString(11, paciente.getAntecedentes());

            int rowsInserted = stmt.executeUpdate();
            exitoso = rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return exitoso;
    }

    public Paciente login(String email, String contrasena)
    {
        //Empezamos con una instancia de usuario vacio
        Paciente Paciente = null;
        //Hacemos la consulta para ver si existe un usuario y contrasena con esos datos
        String sql = "SELECT * FROM PACIENTE WHERE correo_electronico = ? AND contrasena = ?";

        //Intentamos conectarnos con Excepcion por si la conexion falla
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            //Reemplazamos los atributos de la consulta con los que se recibieron de los txt
            stmt.setString(1, email);
            stmt.setString(2, contrasena);

            //Ejecutamos la consulta y procesamos el resultado
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    //Si hay coincidencia, creamos el objeto usuario y lo llenamos con los datos de la base de datos
                    Paciente = new Paciente();
                    Paciente.setIdPaciente(resultSet.getInt("id_paciente"));
                    Paciente.setNombre(resultSet.getString("Nombres"));
                    Paciente.setApellidoPat(resultSet.getString("Apellido_paterno"));
                    Paciente.setApellidoMat(resultSet.getString("Apellido_materno"));
                    Paciente.setEdad(resultSet.getInt("edad"));
                    Paciente.setPeso(resultSet.getFloat("peso"));
                    Paciente.setAltura(resultSet.getFloat("altura"));
                    Paciente.setSexo(resultSet.getString("sexo"));
                    Paciente.setTelefono(resultSet.getString("telefono"));
                    Paciente.setEmail(resultSet.getString("correo_electronico"));
                    Paciente.setContrasena(resultSet.getString("contrasena"));
                    Paciente.setAntecedentes(resultSet.getString("antecedentes"));
                }
            }
            //Excepcion por si falla la conexion con sql mostramos el mensaje
        } catch(Exception e) {
            e.printStackTrace();
        }
        //Retornamos el usuario
        return Paciente;
    }
}
