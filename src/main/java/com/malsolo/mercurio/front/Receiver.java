/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */

package com.malsolo.mercurio.front;

import com.malsolo.mercurio.domain.Evento;

/**
 *
 * @author jbeneito
 */
public abstract class Receiver {
    
    public void recibirEvento(Evento evento) {
        procesarEvento(evento);
    }

    protected abstract void procesarEvento(Evento evento);
    
}
