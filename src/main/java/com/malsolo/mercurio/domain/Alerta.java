/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */

package com.malsolo.mercurio.domain;

import java.util.Date;
import java.util.List;

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
}
