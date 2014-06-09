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
public abstract class Texto {
    
    private Long id;
    private Tipo tipo;
    private String valor;
    private List<Grupo> grupos;

    public Texto() {
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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.tipo, this.valor, this.grupos);
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
        final Texto other = (Texto) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.tipo, other.tipo)
                && Objects.equals(this.valor, other.valor)
                && Objects.equals(this.grupos, other.grupos);
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
                .addValue(this.id)
                .addValue(this.tipo)
                .addValue(this.valor)
                .addValue(this.grupos)
                .toString();
    }
    
}
