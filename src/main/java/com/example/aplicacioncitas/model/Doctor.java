package com.example.aplicacioncitas.model;

public class Doctor
{
    private int idDoctor;
    private String nombres;
    private String apellidoPat;
    private String apellidoMat;
    private String telefonoMovil;
    private String especialidad;
    private float rating;
    private int idConsultorio;

    public Doctor()
    {

    }

    //Constructor con todos los atributos
    public Doctor(int idDoctor, String nombres, String apellidoPat, String apellidoMat,  String telefonoMovil, String especialidad, float rating, int idConsultorio)
    {
        this.idDoctor = idDoctor;
        this.nombres = nombres;
        this.apellidoPat = apellidoPat;
        this.apellidoMat = apellidoMat;
        this.telefonoMovil = telefonoMovil;
        this.especialidad = especialidad;
        this.rating = rating;
        this.idConsultorio = idConsultorio;
    }

    //Getters y Setters
    public int getIdDoctor(){return idDoctor;}
    public void setIdDoctor(int idDoctor){this.idDoctor = idDoctor;}

    public String getNombres(){return nombres;}
    public void setNombres(String nombres){this.nombres = nombres;}

    public String getApellidoPat(){return apellidoPat;}
    public void setApellidoPat(String apellidoMat){this.apellidoPat = apellidoMat;}

    public String getApellidoMNat(){return apellidoMat;}
    public void setApellidoMNat(String apellidoMat){this.apellidoMat = apellidoMat;}

    public String getTelefonoMovil(){return telefonoMovil;}
    public void setTelefonoMovil(String telefonoMat){this.telefonoMovil = telefonoMat;}

    public String getEspecialidad(){return especialidad;}
    public void setEspecialidad(String especialidad){this.especialidad = especialidad;}

    public float getRating(){return rating;}
    public void setRating(float rating){this.rating = rating;}

    public int getIdConsultorio(){return idConsultorio;}
    public void setIdConsultorio(int idConsultorio){this.idConsultorio = idConsultorio;}
}

