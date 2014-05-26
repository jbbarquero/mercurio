/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */

package com.malsolo.mercurio.business;

import com.malsolo.mercurio.domain.Alerta;
import com.malsolo.mercurio.domain.Evento;
import com.malsolo.mercurio.domain.Mensaje;
import com.malsolo.mercurio.domain.Tipo;
import java.util.List;

/**
 *
 * @author Javier Beneito Barquero <jbbarquero@gmail.com>
 */
public interface GestorAlertas {
    
    public Evento recibirEvento(Evento evento);
    
    public Alerta generarAlerta(Evento evento, Tipo tipo);
    
    public List<Mensaje> enviarAlerta(Alerta alerta);
    
}
