/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */

package com.malsolo.mercurio.business.main.business;

import com.malsolo.mercurio.business.EventosValidador;
import com.malsolo.mercurio.domain.Evento;
import com.malsolo.mercurio.exceptions.EventoInvalidoException;

/**
 *
 * @author jbeneito
 */
public class EventosValidadorImpl implements EventosValidador {

    @Override
    public Evento valida(Evento evento) throws EventoInvalidoException {
        System.out.println("Evento válido: " + evento);
        return evento;
    }
    
}
