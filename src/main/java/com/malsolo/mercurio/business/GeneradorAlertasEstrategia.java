/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */

package com.malsolo.mercurio.business;

import com.malsolo.mercurio.domain.Alerta;
import com.malsolo.mercurio.domain.Evento;
import com.malsolo.mercurio.domain.Tipo;

/**
 *
 * @author Javier Beneito Barquero <jbbarquero@gmail.com>
 */
public interface GeneradorAlertasEstrategia {
    
    public Alerta generaAlerta(Evento evento, Tipo tipo);
    
}
