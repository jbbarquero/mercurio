/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */
package com.malsolo.mercurio.business.main;

import com.malsolo.mercurio.business.GestorAlertas;
import com.malsolo.mercurio.domain.Alerta;
import com.malsolo.mercurio.domain.Evento;
import com.malsolo.mercurio.exceptions.EventoInvalidoException;
import com.malsolo.mercurio.front.Dispatcher;
import com.malsolo.mercurio.front.Receiver;
import com.malsolo.mercurio.service.TipoService;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jbeneito
 */
public class SingleThreadWithFrontAlertasExecutor implements AlertasExecutor {

    private final static Logger logger = LoggerFactory.getLogger(SingleThreadWithFrontAlertasExecutor.class);

    private final GestorAlertas gestorAlertas;
    private final TipoService tipoService;
    private final EventosGenerator eventosGenerator;
    private final Dispatcher dispatcher;
    private final Receiver receiver;
    private final ArrayDeque<Alerta> pilaAlertas;

    public SingleThreadWithFrontAlertasExecutor(GestorAlertas gestorAlertas, TipoService tipoService, EventosGenerator eventosGenerator) {
        this.eventosGenerator = eventosGenerator;
        this.gestorAlertas = gestorAlertas;
        this.tipoService = tipoService;
        this.dispatcher = new DispatcherImpl();
        this.pilaAlertas = new ArrayDeque<>(1);
        this.receiver = new ReceiverImpl();
    }

    @Override
    public void doStuff(int numeroEventos) throws EventoInvalidoException {
        long tiempoRecibirEventos = 0L;
        long tiempoEnviarAlertas = 0L;
        long t;
        for (int i = 0; i < numeroEventos; i++) {
            Evento evento = eventosGenerator.generarEvento();
            if (evento != null) {

                t = System.nanoTime();
                this.receiver.recibirEvento(evento);
                tiempoRecibirEventos += System.nanoTime() - t;

                t = System.nanoTime();
                this.dispatcher.enviarAlertas();
                tiempoEnviarAlertas += System.nanoTime() - t;

            } else {
                logger.error("Generado evento null en la iteración {} de {} ", i, numeroEventos);
            }
        }
        Main.tiempo("Tiempo de recibir eventos ", tiempoRecibirEventos);
        Main.tiempo("Tiempo de enviar alertas ", tiempoEnviarAlertas);
    }

    class ReceiverImpl extends Receiver {

        @Override
        protected void procesarEvento(Evento evento) {
            try {
                pilaAlertas.push(
                        gestorAlertas.generarAlerta(
                                gestorAlertas.recibirEvento(evento)
                                , tipoService.findById(evento.getIdTipo())));
            } catch (EventoInvalidoException ex) {
                logger.error("Error al procesar evento {} ", evento);
            }
        }
    }

    class DispatcherImpl extends Dispatcher {

        @Override
        protected List<Alerta> seleccionarAlertas() {
            List<Alerta> alertas = new ArrayList<>();
            alertas.add(pilaAlertas.pop());
            return alertas;
        }

        @Override
        protected void enviarAlerta(Alerta alerta) {
            /* List<Mensaje> mensajes = */ gestorAlertas.enviarAlerta(alerta);
        }
    }

}
