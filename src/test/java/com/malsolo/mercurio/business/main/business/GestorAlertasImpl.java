/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */

package com.malsolo.mercurio.business.main.business;

import com.malsolo.mercurio.business.EventosValidador;
import com.malsolo.mercurio.business.GeneradorAlertasEstrategia;
import com.malsolo.mercurio.business.GestorAlertas;
import com.malsolo.mercurio.domain.Alerta;
import com.malsolo.mercurio.domain.Mensaje;
import com.malsolo.mercurio.service.AlertaService;
import com.malsolo.mercurio.service.EventoService;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jbeneito
 */
public class GestorAlertasImpl extends GestorAlertas {

    public GestorAlertasImpl(EventosValidador eventosValidador, GeneradorAlertasEstrategia alertasEstrategia, EventoService eventoService, AlertaService alertaService) {
        super(eventosValidador, alertasEstrategia, eventoService, alertaService);
    }

    @Override
    public List<Mensaje> realizarEnvioAlerta(Alerta alerta) {
        return new ArrayList<>();
    }
    
}
