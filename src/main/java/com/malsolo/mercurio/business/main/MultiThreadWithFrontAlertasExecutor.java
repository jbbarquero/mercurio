/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */
package com.malsolo.mercurio.business.main;

import com.malsolo.mercurio.business.GestorAlertas;
import com.malsolo.mercurio.domain.Alerta;
import com.malsolo.mercurio.domain.Evento;
import com.malsolo.mercurio.domain.Mensaje;
import com.malsolo.mercurio.exceptions.EventoInvalidoException;
import com.malsolo.mercurio.front.Dispatcher;
import com.malsolo.mercurio.front.Receiver;
import com.malsolo.mercurio.service.TipoService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jbeneito
 */
public class MultiThreadWithFrontAlertasExecutor implements AlertasExecutor {

    private final static Logger logger
            = LoggerFactory.getLogger(MultiThreadWithFrontAlertasExecutor.class);

    private final static int QUEUE_CAPACITY = 1024;
    private final static int N_THREADS = 2;

    private final EventosGenerator eventosGenerator;
    private final GestorAlertas gestorAlertas;
    private final TipoService tipoService;
    private final BlockingQueue<Evento> eventosQueue;
    private final BlockingQueue<Alerta> alertasQueue;
    private final Dispatcher dispatcher;
    private final Receiver receiver;

    public MultiThreadWithFrontAlertasExecutor(EventosGenerator eventosGenerator, GestorAlertas gestorAlertas, TipoService tipoService) {
        this.receiver = new ReceiverImpl();
        this.dispatcher = new DispatcherImpl();
        this.alertasQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
        this.eventosQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
        this.eventosGenerator = eventosGenerator;
        this.gestorAlertas = gestorAlertas;
        this.tipoService = tipoService;
    }

    @Override
    public void doStuff(final int numeroEventos) throws EventoInvalidoException {

        int processors = Runtime.getRuntime().availableProcessors();
        logger.info("Available processors: {}", processors);

        ExecutorService executorServiceEventos
                = Executors.newCachedThreadPool();
        List<Future<Long>> tiemposGenerarEventos = new ArrayList<>();
        for (int i = 0; i < N_THREADS; i++) {
            tiemposGenerarEventos.add(
                    executorServiceEventos.submit(new Callable<Long>() {
                        @Override
                        public Long call() {
                            long start = System.nanoTime();
                            for (int j = 0; j < (numeroEventos / N_THREADS); j++) {
                                receiver.recibirEvento(eventosGenerator.generarEvento());
                            }
                            long time = System.nanoTime() - start;
                            return time;
                        }
                    }));
        }

        EventosListener eventosListener = new EventosListener(numeroEventos);
        ExecutorService executorServiceEventosListener
                = Executors.newCachedThreadPool();
        Future<Long> tiempoRecogerEventos
                = executorServiceEventosListener.submit(eventosListener);

        AlertasListener alertasListener = new AlertasListener(numeroEventos);
        ExecutorService executorServiceAlertas
                = Executors.newCachedThreadPool();
        Future<Long> tiempoEnviarAlertas
                = executorServiceAlertas.submit(alertasListener);

        try {
            Long tiempoGenerarEventos = 0L;
            for (Future<Long> future : tiemposGenerarEventos) {
                tiempoGenerarEventos += future.get();
            }
            Long tEventos = tiempoRecogerEventos.get(); //Para esperar a que termine todo antes de escribir el log
            Long tAlertas = tiempoEnviarAlertas.get(); //Para esperar a que termine todo antes de escribir el log
            Main.tiempo(">>>>> TIEMPO DE GENERAR EVENTOS", tiempoGenerarEventos);
            Main.tiempo(">>>>> TIEMPO DE RECOGER EVENTOS", tEventos);
            Main.tiempo(">>>>> TIEMPO DE ENVIAR ALERTAS", tAlertas);
        } catch (InterruptedException | ExecutionException ex) {
            logger.error(ex.toString());
        }

        executorServiceEventos.shutdown();
        executorServiceEventosListener.shutdown();
        executorServiceAlertas.shutdown();
    }

    class ReceiverImpl extends Receiver {

        @Override
        protected void procesarEvento(Evento evento) {
            try {
                logger.debug("Recibir evento: {}", evento);
                eventosQueue.put(evento);
                logger.info("EVENTO RECIBIDO: {}", evento);
            } catch (InterruptedException ex) {
                logger.error("Error al generar eventos: {}", ex.toString());
            }
        }
    }

    class EventosListener implements Callable<Long> {

        private final int numeroEventos;

        public EventosListener(int numeroEventos) {
            this.numeroEventos = numeroEventos;
        }

        @Override
        public Long call() {
            long start = System.nanoTime();
            for (int i = 0; i < numeroEventos; i++) {
                Evento evento;
                try {
                    logger.debug("Generar alerta de un evento");
                    evento = eventosQueue.take();
                    Alerta alerta = gestorAlertas.generarAlerta(evento, tipoService.findById(evento.getIdTipo()));
                    alertasQueue.put(alerta);
                    logger.info("ALERTA GENERADA CON ID {} A PARTIR DEL EVENTO CON ID {}", alerta.getId(), evento.getId());
                } catch (InterruptedException ex) {
                    logger.error("Error al escuchar eventos: {}", ex.toString());
                }
            }
            long time = System.nanoTime() - start;
            return time;
        }
    }

    class DispatcherImpl extends Dispatcher {

        @Override
        protected List<Alerta> seleccionarAlertas() {
            List<Alerta> alertas = new ArrayList<>();
            try {
                logger.debug("Seleccionar alerta");
                alertas.add(alertasQueue.take());
                logger.info("ALERTAS SELECCIONADAS: {}", alertas.size());
            } catch (InterruptedException ex) {
                logger.error("Error al enviar alertas: {}", ex.toString());
            }
            return alertas;
        }

        @Override
        protected void enviarAlerta(Alerta alerta) {
            logger.debug("Enviar alerta {}", alerta);
            List<Mensaje> mensajes = gestorAlertas.enviarAlerta(alerta);
            logger.info("ALERTA ENVIADA EN {} MENSAJES", mensajes.size());
        }

    }

    class AlertasListener implements Callable<Long> {

        private final int numeroEventos;

        public AlertasListener(int numeroEventos) {
            this.numeroEventos = numeroEventos;
        }

        @Override
        public Long call() {
            long start = System.nanoTime();
            for (int i = 0; i < numeroEventos; i++) {
                dispatcher.enviarAlertas();
            }
            long time = System.nanoTime() - start;
            return time;
        }

    }
}
