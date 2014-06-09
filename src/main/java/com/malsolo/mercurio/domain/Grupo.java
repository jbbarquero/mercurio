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
public class Grupo {
    
    private Long id;
    private String nombre;
    private List<Destinatario> destinatarios;

    public Grupo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Destinatario> getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(List<Destinatario> destinatarios) {
        this.destinatarios = destinatarios;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.nombre, this.destinatarios);
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
        final Grupo other = (Grupo) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.nombre, other.nombre)
                && Objects.equals(this.destinatarios, other.destinatarios);
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
                .addValue(this.id)
                .addValue(this.nombre)
                .addValue(this.destinatarios)
                .toString();
    }
    
    
}
