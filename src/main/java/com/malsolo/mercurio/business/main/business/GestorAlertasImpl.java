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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jbeneito
 */
public class GestorAlertasImpl extends GestorAlertas {

    private final static Logger logger = LoggerFactory.getLogger(GestorAlertasImpl.class);

    public GestorAlertasImpl(EventosValidador eventosValidador, GeneradorAlertasEstrategia alertasEstrategia, EventoService eventoService, AlertaService alertaService) {
        super(eventosValidador, alertasEstrategia, eventoService, alertaService);
    }

    @Override
    public List<Mensaje> realizarEnvioAlerta(Alerta alerta) {
        logger.warn("Realizar envío de la alerta {}", alerta);
        return new ArrayList<>();
    }
    
}
