package com.example.aplicacioncitas.model;

import java.time.LocalDateTime;

public class Cita
{
    private int idCita;
    private LocalDateTime fechaCita;
    private float monto;
    private String motivo;
    private int idConsultorio;
    private int idDoctor;
    private int idPaciente;
    private String nombreDoctor;
    private String nombrePaciente;
    private String nombreConsultorio;

    //Constructor Vacio
    public Cita()
    {

    }

    //Constructor con todos los atributos
    public Cita(int idCita, LocalDateTime fechaCita, float monto, String motivo, int idConsultorio, int idDoctor, int idPaciente, String nombreDoctor, String nombrePaciente, String nombreConsultorio)
    {
        this.idCita = idCita;
        this.fechaCita = fechaCita;
        this.monto = monto;
        this.motivo = motivo;
        this.idConsultorio = idConsultorio;
        this.idDoctor = idDoctor;
        this.idPaciente = idPaciente;
        this.nombreDoctor = nombreDoctor;
        this.nombrePaciente = nombrePaciente;
        this.nombreConsultorio = nombreConsultorio;
    }

    //Getters y setters
    public int getIdCita(){return idCita;}
    public void setIdCita(int idCita){this.idCita = idCita;}

    public LocalDateTime getFechaCita(){return fechaCita;}
    public void setFechaCita(LocalDateTime fechaCita){this.fechaCita = fechaCita;}

    public float getMonto(){return monto;}
    public void setMonto(float monto){this.monto = monto;}

    public String getMotivo(){return motivo;}
    public void setMotivo(String motivo){this.motivo = motivo;}

    public int getIdConsultorio(){return idConsultorio;}
    public void setIdConsultorio(int idConsultorio){this.idConsultorio = idConsultorio;}

    public int getIdDoctor(){return idDoctor;}
    public void setIdDoctor(int idDoctor){this.idDoctor = idDoctor;}

    public int getIdPaciente(){return idPaciente;}
    public void setIdPaciente(int idPaciente){this.idPaciente = idPaciente;}

    public String getNombreDoctor(){return nombreDoctor;}
    public void setNombreDoctor(String nombreDoctor){this.nombreDoctor = nombreDoctor;}

    public String getNombrePaciente(){return nombrePaciente;}
    public void setNombrePaciente(String nombrePaciente){this.nombrePaciente = nombrePaciente;}

    public String getNombreConsultorio(){return nombreConsultorio;}
    public void setNombreConsultorio(String nombreConsultorio){this.nombreConsultorio = nombreConsultorio;}
}
