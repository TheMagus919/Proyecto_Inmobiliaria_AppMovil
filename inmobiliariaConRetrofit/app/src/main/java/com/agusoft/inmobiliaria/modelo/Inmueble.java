package com.agusoft.inmobiliaria.modelo;

import java.io.Serializable;
import java.util.Objects;

public class Inmueble implements Serializable {

    private int idInmueble;
    private int cantidadDeAmbientes;
    private String direccion;
    private boolean disponible;
    private int idPropietario;
    private double precio;
    private String uso;
    private String tipo;
    private String imagen;

    public Inmueble(int idInmueble, int cantidadDeAmbientes, String direccion, boolean disponible, int idPropietario, double precio, String uso, String tipo, String imagen) {
        this.idInmueble = idInmueble;
        this.cantidadDeAmbientes = cantidadDeAmbientes;
        this.direccion = direccion;
        this.disponible = disponible;
        this.idPropietario = idPropietario;
        this.precio = precio;
        this.uso = uso;
        this.tipo = tipo;
        this.imagen = imagen;
    }

    public Inmueble(int idInmueble, int cantidadDeAmbientes, String direccion, boolean disponible, int idPropietario, double precio, String uso, String tipo) {
        this.idInmueble = idInmueble;
        this.cantidadDeAmbientes = cantidadDeAmbientes;
        this.direccion = direccion;
        this.disponible = disponible;
        this.idPropietario = idPropietario;
        this.precio = precio;
        this.uso = uso;
        this.tipo = tipo;
    }

    public Inmueble() {
    }

    public int getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(int idInmueble) {
        this.idInmueble = idInmueble;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidadDeAmbientes() {
        return cantidadDeAmbientes;
    }

    public void setCantidadDeAmbientes(int ambientes) {
        this.cantidadDeAmbientes = ambientes;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int propietario) {
        this.idPropietario = propietario;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean estado) {
        this.disponible = estado;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inmueble inmueble = (Inmueble) o;
        return idInmueble == inmueble.idInmueble;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInmueble);
    }
}
