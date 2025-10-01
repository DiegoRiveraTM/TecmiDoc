package com.example.aplicacioncitas.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnection
{
    public static Connection getConnection()
    {
        //Declaramos la variable conexion como null
        Connection connection = null;
        //Intentamos cargar los datos de la base de datos
        try(InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("config.properties"))
        {
            //Creamos un objeto de Propiedades para guardar ahi los datos
            Properties prop = new Properties();
            prop.load(input);
            //Guardamos la URL del config.properties en la variable URL
            String url = prop.getProperty("db.url");
            //Guardamos el usuario de config.properties en la variable user
            String user = prop.getProperty("db.user");
            //Guardamos la contraseña
            String password = prop.getProperty("db.password");

            //Usamos la URL, usuario y contraseña para crear la conexión con DriverManager
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexion Exitosa");
        } catch (Exception ex)
        {
            ex.printStackTrace();
            System.out.println("Error en la conexion a la base de datos");
        }
        return connection;
    }
}

