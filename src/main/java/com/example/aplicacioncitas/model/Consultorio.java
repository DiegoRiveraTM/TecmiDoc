package com.example.aplicacioncitas.model;

public class Consultorio
{
    private int idConsultorio;
    private String nombreConsultorio;
    private String calle;
    private String numInterior;
    private String numExterior;
    private String colonia;
    private String ciudad;
    private String estado;
    private String codigoPostal;
    private String telefonoFijo;

    public Consultorio()
    {

    }

    //Constructor con todos los atributos
    public Consultorio(int idConsultorio, String nombreConsultorio, String calle, String numInterior, String numExterior, String colonia, String ciudad, String estado, String codigoPostal, String telefonoFijo)
    {
        this.idConsultorio = idConsultorio;
        this.nombreConsultorio = nombreConsultorio;
        this.calle = calle;
        this.numInterior = numInterior;
        this.numExterior = numExterior;
        this.colonia = colonia;
        this.ciudad = ciudad;
        this.estado = estado;
        this.codigoPostal = codigoPostal;
        this.telefonoFijo = telefonoFijo;
    }

    //Getters y Sets para
    public int getIdConsultorio(){return idConsultorio;}
    public void setIdConsultorio(int idConsultorio){this.idConsultorio = idConsultorio;}

    public String getNombreConsultorio(){return nombreConsultorio;}
    public void setNombreConsultorio(String nombreConsultorio){this.nombreConsultorio = nombreConsultorio;}

    public String getCalle(){return calle;}
    public void setCalle(String calle){this.calle = calle;}

    public String getNumInterior(){return numInterior;}
    public void setNumInterior(String numInterior){this.numInterior = numInterior;}

    public String getNumExterior(){return numExterior;}
    public void setNumExterior(String numExterior){this.numExterior = numExterior;}

    public String getColonia(){return colonia;}
    public void setColonia(String colonia){this.colonia = colonia;}

    public String getCiudad(){return ciudad;}
    public void setCiudad(String ciudad){this.ciudad = ciudad;}

    public String getEstado(){return estado;}
    public void setEstado(String estado){this.estado = estado;}

    public String getCodigoPostal(){return codigoPostal;}
    public void setCodigoPostal(String codigoPostal){this.codigoPostal = codigoPostal;}

    public String getTelefonoFijo(){return telefonoFijo;}
    public void setTelefonoFijo(String TelefonoFijo){this.telefonoFijo = TelefonoFijo;}
}
