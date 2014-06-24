/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */

package com.malsolo.mercurio.business;

import com.malsolo.mercurio.domain.Alerta;
import com.malsolo.mercurio.domain.Evento;
import com.malsolo.mercurio.domain.Mensaje;
import com.malsolo.mercurio.domain.Tipo;
import com.malsolo.mercurio.exceptions.EventoInvalidoException;
import com.malsolo.mercurio.service.AlertaService;
import com.malsolo.mercurio.service.EventoService;
import java.util.List;

/**
 *
 * @author Javier Beneito Barquero <jbbarquero@gmail.com>
 */
public abstract class GestorAlertas {
    
    private final EventosValidador eventosValidador;
    private final GeneradorAlertasEstrategia alertasEstrategia;
    private final EventoService eventoService;
    private final AlertaService alertaService;

    public GestorAlertas(EventosValidador eventosValidador, 
            GeneradorAlertasEstrategia alertasEstrategia, 
            EventoService eventoService, AlertaService alertaService) {
        //TODO: validar con Java 7+ ó GUAVA
        this.eventosValidador = eventosValidador;
        this.alertasEstrategia = alertasEstrategia;
        this.eventoService = eventoService;
        this.alertaService = alertaService;
    }
    
    public Evento recibirEvento(Evento evento) throws EventoInvalidoException {
        return this.eventoService.save(this.eventosValidador.valida(evento));
    }
    
    public Alerta generarAlerta(Evento evento, Tipo tipo) {
        return this.alertaService
                .save(this.alertasEstrategia.generaAlerta(evento, tipo));
    }
    
    public List<Mensaje> enviarAlerta(Alerta alerta) {
        return realizarEnvioAlerta(alerta);
    }
    
    protected abstract List<Mensaje> realizarEnvioAlerta(Alerta alerta);
    
}
