package com.agusoft.inmobiliaria.modelo;

import java.util.Objects;

public class Propietario {

    private int idPropietario;
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String clave;
    private String telefono;
    private String avatar;

    public Propietario(){}
    public Propietario(int idPropietario, String dni, String nombre, String apellido, String email, String clave, String telefono, String avatar) {
        this.idPropietario = idPropietario;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.clave = clave;
        this.telefono = telefono;
        this.avatar=avatar;
    }

    public Propietario(int idPropietario, String dni, String nombre, String apellido, String email, String clave, String telefono) {
        this.idPropietario = idPropietario;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.clave = clave;
        this.telefono = telefono;
    }

    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int id) {
        this.idPropietario = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public String getClave() {
        return clave;
    }

    public void setClave(String contraseña) {
        this.clave = contraseña;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Propietario that = (Propietario) o;
        return idPropietario == that.idPropietario;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPropietario);
    }
}
