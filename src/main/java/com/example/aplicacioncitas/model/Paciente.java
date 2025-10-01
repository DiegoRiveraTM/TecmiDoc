package com.example.aplicacioncitas.model;

public class Paciente
{
    private int idPaciente;
    private String nombre;
    private String apellidoPat;
    private String apellidoMat;
    private int edad;
    private float peso;
    private float altura;
    private String sexo;
    private String telefono;
    private String email;
    private String antecedentes;
    private String contrasena;

    //Constructor
    public Paciente()
    {

    }

    //Constructor con todos los atributos
    public Paciente(int idPaciente, String nombre, String apellidoPat, String apellidoMat, int edad, float peso, float altura, String sexo, String telefono, String email, String antecedentes,  String contrasena)
    {
        this.idPaciente = idPaciente;
        this.nombre = nombre;
        this.apellidoPat = apellidoPat;
        this.apellidoMat = apellidoMat;
        this.edad = edad;
        this.peso = peso;
        this.altura = altura;
        this.sexo = sexo;
        this.telefono = telefono;
        this.email = email;
        this.antecedentes = antecedentes;
        this.contrasena = contrasena;
    }

    //Get y Set para todos los atributos
    public int getIdPaciente(){return idPaciente;}
    public void setIdPaciente(int idPaciente){this.idPaciente = idPaciente;}

    public String getNombre(){return nombre;}
    public void setNombre(String nombre){this.nombre = nombre;}

    public String getApellidoPat(){return apellidoPat;}
    public void setApellidoPat(String apellidoPat){this.apellidoPat = apellidoPat;}

    public String getApellidoMat(){return apellidoMat;}
    public void setApellidoMat(String apellidoMat){this.apellidoMat = apellidoMat;}

    public int getEdad(){return edad;}
    public void setEdad(int edad){this.edad = edad;}

    public float getPeso(){return peso;}
    public void setPeso(float peso){this.peso = peso;}

    public float getAltura(){return altura;}
    public void setAltura(float altura){this.altura = altura;}

    public String getSexo(){return sexo;}
    public void setSexo(String sexo){this.sexo = sexo;}

    public String getTelefono(){return telefono;}
    public void setTelefono(String telefono){this.telefono = telefono;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}

    public String getAntecedentes(){return antecedentes;}
    public void setAntecedentes(String antecedentes){this.antecedentes = antecedentes;}

    public String getContrasena(){return contrasena;}
    public void setContrasena(String contrasena){this.contrasena = contrasena;}
}

