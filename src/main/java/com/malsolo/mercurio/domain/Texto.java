/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */

package com.malsolo.mercurio.domain;

import java.util.List;

/**
 *
 * @author Javier Beneito Barquero <jbbarquero@gmail.com>
 */
public abstract class Texto {
    
    private Long id;
    private Tipo tipo;
    private String valor;
    private List<Grupo> grupos;
    
    
}
