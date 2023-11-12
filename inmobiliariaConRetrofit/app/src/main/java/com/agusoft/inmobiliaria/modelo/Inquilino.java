package com.agusoft.inmobiliaria.modelo;

import java.io.Serializable;

public class Inquilino implements Serializable {

    private int idInquilino;
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String garante;
    private String telefonoGarante;

    public Inquilino() {}

    public Inquilino(int idInquilino, String DNI, String nombre, String apellido, String email, String telefono, String nombreGarante, String telefonoGarante) {
        this.idInquilino = idInquilino;
        this.dni = DNI;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.garante = nombreGarante;
        this.telefonoGarante = telefonoGarante;
    }

    public int getIdInquilino() {
        return idInquilino;
    }

    public void setIdInquilino(int idInquilino) {
        this.idInquilino = idInquilino;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String DNI) {
        this.dni = DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getGarante() {
        return garante;
    }

    public void setGarante(String nombreGarante) {
        this.garante = nombreGarante;
    }

    public String getTelefonoGarante() {
        return telefonoGarante;
    }

    public void setTelefonoGarante(String telefonoGarante) {
        this.telefonoGarante = telefonoGarante;
    }
}
