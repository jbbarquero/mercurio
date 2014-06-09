/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */

package com.malsolo.mercurio.business.main;

import com.malsolo.mercurio.exceptions.EventoInvalidoException;

/**
 *
 * @author jbeneito
 */
public interface AlertasExecutor {
    
    public void doStuff(int numeroEventos) throws EventoInvalidoException;
    
}
