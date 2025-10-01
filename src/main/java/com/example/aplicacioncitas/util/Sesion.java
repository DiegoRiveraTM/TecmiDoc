package com.example.aplicacioncitas.util;


//Este archivo es para el citaController
public class Sesion
{
    private static int idPacienteLoggeado;

    public static int getIdPacienteLoggeado()
    {
        return idPacienteLoggeado;
    }

    public static void setIdPacienteLoggeado(int idPaciente)
    {
        idPacienteLoggeado = idPaciente;
    }
}
