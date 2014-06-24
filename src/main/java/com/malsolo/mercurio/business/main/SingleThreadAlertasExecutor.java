/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */
package com.malsolo.mercurio.business.main;

import com.malsolo.mercurio.business.GestorAlertas;
import com.malsolo.mercurio.domain.Evento;
import com.malsolo.mercurio.domain.Mensaje;
import com.malsolo.mercurio.exceptions.EventoInvalidoException;
import com.malsolo.mercurio.service.TipoService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jbeneito
 */
public class SingleThreadAlertasExecutor implements AlertasExecutor {
    
    private final static Logger logger = LoggerFactory.getLogger(SingleThreadAlertasExecutor.class);

    private final GestorAlertas gestorAlertas;
    private final TipoService tipoService;
    private final EventosGenerator eventosGenerator;

    public SingleThreadAlertasExecutor(GestorAlertas gestorAlertas
            , TipoService tipoService
            , EventosGenerator eventosGenerator) {
        this.gestorAlertas = gestorAlertas;
        this.tipoService = tipoService;
        this.eventosGenerator= eventosGenerator;
    }

    @Override
    public void doStuff(int numeroEventos) throws EventoInvalidoException {

        for (int i = 0; i < numeroEventos; i++) {
            Evento evento = eventosGenerator.generarEvento();
            if (evento != null) {
                List<Mensaje> mensajes = gestorAlertas.enviarAlerta(
                        gestorAlertas.generarAlerta(
                                gestorAlertas.recibirEvento(evento),
                                tipoService.findById(evento.getIdTipo())));

                for (Mensaje mensaje : mensajes) {
                    System.out.println(mensaje);
                }

            }
            else {
                logger.error("Generado evento null en la iteración {} de {} "
                        , i, numeroEventos);
            }
        }
    }

}
