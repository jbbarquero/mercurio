/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */

package com.malsolo.mercurio.domain;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Javier Beneito Barquero <jbbarquero@gmail.com>
 */
public class Alerta {

    private Long id;
    private Tipo tipo;
    private String datos;
    private List<Evento> eventos;
    private Date fechaTratamiento;
    private Date fechaEnvio;

    public Alerta() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public Date getFechaTratamiento() {
        return fechaTratamiento;
    }

    public void setFechaTratamiento(Date fechaTratamiento) {
        this.fechaTratamiento = fechaTratamiento;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.tipo, this.datos, this.eventos,
                this.fechaTratamiento, this.fechaEnvio);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Alerta other = (Alerta) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.tipo, other.tipo)
                && Objects.equals(this.fechaTratamiento, other.fechaTratamiento)
                && Objects.equals(this.fechaEnvio, other.fechaEnvio)
                && Objects.equals(this.datos, other.datos);
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
                .addValue(this.id)
                .addValue(this.tipo)
                .addValue(this.datos)
                .addValue(this.fechaTratamiento)
                .addValue(this.fechaEnvio)
                .toString();
    }
    
}
