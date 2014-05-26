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
public class Mensaje {
    
    private Long id;
    private Alerta alerta;
    private Texto texto;
    private List<Direccion> direcciones;
    private String valor;
    
}
