/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */

package com.malsolo.mercurio.business.main.service;

import com.malsolo.mercurio.domain.Evento;
import com.malsolo.mercurio.service.EventoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jbeneito
 */
public class EventoServiceImpl implements EventoService {
    
    private final static Logger logger = LoggerFactory.getLogger(EventoServiceImpl.class);

    @Override
    public Evento save(Evento evento) {
        logger.debug("Evento guardado {} ", evento.toString());
        return evento;
    }
    
}
