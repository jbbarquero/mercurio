/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */

package com.malsolo.mercurio.business.main.service;

import com.malsolo.mercurio.domain.Alerta;
import com.malsolo.mercurio.service.AlertaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jbeneito
 */
public class AlertaServiceImpl implements AlertaService {
    
    private final static Logger logger = LoggerFactory.getLogger(AlertaServiceImpl.class);

    @Override
    public Alerta save(Alerta alerta) {
        logger.debug("Alerta guardada {} ", alerta);
        return alerta;
    }
    
}
