/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */

package com.malsolo.mercurio.domain;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author Javier Beneito Barquero <jbbarquero@gmail.com>
 */
public class Mensaje {
    
    private Long id;
    private Alerta alerta;
    private Texto texto;
    private List<Direccion> direcciones;
    private String valor;

    public Mensaje() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Alerta getAlerta() {
        return alerta;
    }

    public void setAlerta(Alerta alerta) {
        this.alerta = alerta;
    }

    public Texto getTexto() {
        return texto;
    }

    public void setTexto(Texto texto) {
        this.texto = texto;
    }

    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.alerta, this.texto, this.direcciones
                , this.valor);
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
        final Mensaje other = (Mensaje) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.alerta, other.alerta)
                && Objects.equals(this.texto, other.texto)
                && Objects.equals(this.direcciones, other.direcciones)
                && Objects.equals(this.valor, other.valor);
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
                .addValue(this.id)
                .addValue(this.alerta)
                .addValue(this.texto)
                .addValue(this.direcciones)
                .addValue(this.valor)
                .toString();
    }
    
}
