/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */

package com.malsolo.mercurio.domain;

import java.util.Objects;

/**
 *
 * @author Javier Beneito Barquero <jbbarquero@gmail.com>
 */
public class Etiqueta {
    
    private Long    id;
    private String  codigo;
    private String  tipo;
    private Integer longitud;

    public Etiqueta() {
    }
    
    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Integer getLongitud() {
        return longitud;
    }

    public String getTipo() {
        return tipo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setLongitud(Integer longitud) {
        this.longitud = longitud;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.codigo, this.longitud, this.tipo);
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
        final Etiqueta other = (Etiqueta) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.codigo, other.codigo)
                && Objects.equals(this.longitud, other.longitud)
                && Objects.equals(this.tipo, other.tipo);
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
                .addValue(this.id)
                .addValue(this.codigo)
                .addValue(this.longitud)
                .addValue(this.tipo)
                .toString();
    }

}
