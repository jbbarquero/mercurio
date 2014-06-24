/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */

package com.malsolo.mercurio.business.main.business;

import com.malsolo.mercurio.business.GeneradorAlertasEstrategia;
import com.malsolo.mercurio.domain.Alerta;
import com.malsolo.mercurio.domain.Evento;
import com.malsolo.mercurio.domain.Tipo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jbeneito
 */
public class GeneradorAlertasEstrategiaImpl implements GeneradorAlertasEstrategia {
    
    private final static Logger logger = LoggerFactory.getLogger(GeneradorAlertasEstrategiaImpl.class);

    @Override
    public Alerta generaAlerta(Evento evento, Tipo tipo) {
        logger.debug("Alerta generada del evento {} de tipo {} ", evento, tipo);
        return new Alerta();
    }
    
}
