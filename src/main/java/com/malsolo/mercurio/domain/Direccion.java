/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */

package com.malsolo.mercurio.domain;

/**
 *
 * @author Javier Beneito Barquero <jbbarquero@gmail.com>
 */
public abstract class Direccion {
    
    private Long id;

    public Direccion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
