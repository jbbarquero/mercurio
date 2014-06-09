/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */

package com.malsolo.mercurio.front;

import com.malsolo.mercurio.domain.Alerta;
import java.util.List;

/**
 *
 * @author jbeneito
 */
public abstract class Dispatcher {
    
    public void enviarAlertas() {
        
        List<Alerta> alertas = seleccionarAlertas();
        for (Alerta alerta : alertas) {
            enviarAlerta(alerta);
        }
    
    }

    protected abstract List<Alerta> seleccionarAlertas();

    protected abstract void enviarAlerta(Alerta alerta);
    
}
