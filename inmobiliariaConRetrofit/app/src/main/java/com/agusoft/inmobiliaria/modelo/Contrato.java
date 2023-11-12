package com.agusoft.inmobiliaria.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Contrato implements Serializable {

    private int idContrato;
    private int idInquilino;
    private int idInmueble;
    private Date fechaDesde;
    private Date fechaHasta;
    private double montoAlquiler;
    private Inquilino vive;
    private Inmueble lugar;

    public Contrato() {}
    public Contrato(int idContrato, Date fechaInicio, Date fechaFin, double montoAlquiler, Inquilino inquilino, Inmueble inmueble) {
        this.idContrato = idContrato;
        this.fechaDesde = fechaInicio;
        this.fechaHasta = fechaFin;
        this.montoAlquiler = montoAlquiler;
        this.vive = inquilino;
        this.lugar = inmueble;
    }

    public Contrato(int idContrato, int idInquilino, int idInmueble, Date fechaDesde, Date fechaHasta, double montoAlquiler) {
        this.idContrato = idContrato;
        this.idInquilino = idInquilino;
        this.idInmueble = idInmueble;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.montoAlquiler = montoAlquiler;
    }

    public Contrato(int idContrato, int idInquilino, int idInmueble, Date fechaDesde, Date fechaHasta, double montoAlquiler, Inquilino vive, Inmueble lugar) {
        this.idContrato = idContrato;
        this.idInquilino = idInquilino;
        this.idInmueble = idInmueble;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.montoAlquiler = montoAlquiler;
        this.vive = vive;
        this.lugar = lugar;
    }

    public int getIdInquilino() {
        return idInquilino;
    }

    public void setIdInquilino(int idInquilino) {
        this.idInquilino = idInquilino;
    }

    public int getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(int idInmueble) {
        this.idInmueble = idInmueble;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaInicio) {
        this.fechaDesde = fechaInicio;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaFin) {
        this.fechaHasta = fechaFin;
    }

    public double getMontoAlquiler() {
        return montoAlquiler;
    }

    public void setMontoAlquiler(double montoAlquiler) {
        this.montoAlquiler = montoAlquiler;
    }


    public Inquilino getVive() {
        return vive;
    }

    public void setVive(Inquilino inquilino) {
        this.vive = inquilino;
    }

    public Inmueble getLugar() {
        return lugar;
    }

    public void setLugar(Inmueble inmueble) {
        this.lugar = inmueble;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contrato contrato = (Contrato) o;
        return idContrato == contrato.idContrato;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idContrato);
    }
}
