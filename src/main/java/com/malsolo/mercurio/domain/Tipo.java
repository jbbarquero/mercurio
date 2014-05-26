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
public class Tipo {
    
    public static final int POLITICA_ALERTAS_UNA_POR_EVENTO = 1;
    
    private Long id;
    private String codigo;
    private String descripcion;
    private boolean activo;
    private int politica;
    private List<Etiqueta> etiquetas;

    public Tipo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getPolitica() {
        return politica;
    }

    public void setPolitica(int politica) {
        this.politica = politica;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.codigo, this.descripcion, this.activo, this.politica, this.etiquetas);
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
        final Tipo other = (Tipo) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.codigo, other.codigo)
                && Objects.equals(this.descripcion, other.descripcion)
                && Objects.equals(this.activo, other.activo)
                && Objects.equals(this.politica, other.politica)
                && Objects.equals(this.etiquetas, other.etiquetas);
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
                .addValue(this.id)
                .addValue(this.codigo)
                .addValue(this.descripcion)
                .addValue(this.activo)
                .addValue(this.politica)
                .addValue(this.etiquetas)
                .toString();
    }
    
}
