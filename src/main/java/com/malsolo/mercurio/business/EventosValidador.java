/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */

package com.malsolo.mercurio.business;

import com.malsolo.mercurio.domain.Evento;
import com.malsolo.mercurio.exceptions.EventoInvalidoException;

/**
 *
 * @author Javier Beneito Barquero <jbbarquero@gmail.com>
 */
public interface EventosValidador {
    
    public Evento valida(Evento evento) throws EventoInvalidoException;
    
}
