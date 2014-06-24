/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */

package com.malsolo.mercurio.business.main.business;

import com.malsolo.mercurio.business.EventosValidador;
import com.malsolo.mercurio.domain.Evento;
import com.malsolo.mercurio.exceptions.EventoInvalidoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jbeneito
 */
public class EventosValidadorImpl implements EventosValidador {

    private final static Logger logger = LoggerFactory.getLogger(EventosValidadorImpl.class);

    @Override
    public Evento valida(Evento evento) throws EventoInvalidoException {
        logger.debug("Evento válido {} ", evento);
        return evento;
    }
    
}
