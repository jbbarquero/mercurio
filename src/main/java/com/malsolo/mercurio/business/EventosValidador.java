/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */

package com.malsolo.mercurio.business;

import com.malsolo.mercurio.domain.Evento;
import com.malsolo.mercurio.exceptions.EventoInvalidoException;
import com.malsolo.mercurio.exceptions.TipoInvalidoException;

/**
 *
 * @author Javier Beneito Barquero <jbbarquero@gmail.com>
 */
public interface EventosValidador {
    
    public void valida(Evento evento) throws EventoInvalidoException, TipoInvalidoException;
    
}
