/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */

package com.malsolo.mercurio.business.main.service;

import com.malsolo.mercurio.domain.Tipo;
import com.malsolo.mercurio.service.TipoService;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jbeneito
 */
public class TipoServiceImpl implements TipoService {
    
    private final static Logger logger = LoggerFactory.getLogger(TipoServiceImpl.class);

    @Override
    public Tipo findById(Long id) {
        Tipo tipo = Mockito.mock(Tipo.class);
        logger.debug("Tipo encontrado con ID {} ", tipo.getId());
        return tipo;
    }
    
}
