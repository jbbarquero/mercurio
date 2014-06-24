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
import com.malsolo.mercurio.service.TipoService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class MultiThreadAlertasExecutor implements AlertasExecutor {

    private final static Logger logger
            = LoggerFactory.getLogger(MultiThreadAlertasExecutor.class);

    private final static int QUEUE_CAPACITY = 1024;

    private final EventosGenerator eventosGenerator;
    private final GestorAlertas gestorAlertas;
    private final TipoService tipoService;
    private final BlockingQueue<Evento> eventosQueue;
    private final BlockingQueue<Alerta> alertasQueue;

    public MultiThreadAlertasExecutor(GestorAlertas gestorAlertas, TipoService tipoService, EventosGenerator eventosGenerator) {
        this.alertasQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
        this.eventosQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
        this.eventosGenerator = eventosGenerator;
        this.gestorAlertas = gestorAlertas;
        this.tipoService = tipoService;
    }

    @Override
    public void doStuff(int numeroEventos) throws EventoInvalidoException {

        try {
            int processors = Runtime.getRuntime().availableProcessors();
            logger.info("Available processors: {}", processors);
            ExecutorService executorService
//                    = Executors.newFixedThreadPool(processors + 1);
                    = Executors.newCachedThreadPool();
            
            Set<Callable<Long>> callables = new HashSet<>();
//            for (int i = 0; i < 10; i++) {
                callables.add(new EventosReceiver(numeroEventos /*/ 10*/));
//            }
            callables.add(new EventosListener(numeroEventos));
            callables.add(new AlertasListener(numeroEventos));

            //CountDownLatch latch = new CountDownLatch(3);
            List<Future<Long>> times = executorService.invokeAll(callables);
            //(new Thread(new EventosListener(), "Thread-Eventos")).start();
            //(new Thread(new AlertasListener(), "Thread-Alertas")).start();

            //latch.await();
            long totalTime = 0L;
            int i = 0;
            String callableName = null;
            for (Future<Long> time : times) {
                switch (i) {
                    case 0:
                        callableName = "Generar eventos";
                        break;
                    case 1:
                        callableName = "Generar alertas";
                        break;
                    case 2:
                        callableName = "Enviar alertas";
                        break;
                    default:
                        callableName = "No name";
                        break;
                }
                long l = time.get();
                Main.tiempo(callableName, l);
                totalTime += l;
                i++;
            }
            logger.info("Tiempo total: {} con {} procesadores", totalTime, processors);

            /*
            Future<Long> fer = executorService.submit(new EventosReceiver(numeroEventos));
            Future<Long> fel = executorService.submit(new EventosListener(numeroEventos));
            Future<Long> fal = executorService.submit(new AlertasListener(numeroEventos));
            
            executorService.shutdown();
            executorService.awaitTermination(60, TimeUnit.SECONDS);
            
            Main.tiempo("Tiempo de generar eventos ", fer.get());
            Main.tiempo("Tiempo de recibir eventos ", fel.get());
            Main.tiempo("Tiempo de enviar alertas ", fal.get());
            */
            
            executorService.shutdown();
            
        } catch (InterruptedException | ExecutionException ex) {
            logger.error(ex.toString());
        }
    }

    class EventosReceiver implements Callable<Long> {

        private final int numeroEventos;

        public EventosReceiver(int numeroEventos) {
            this.numeroEventos = numeroEventos;
        }

        @Override
        public Long call() throws Exception {
            long start = System.nanoTime();
            for (int i = 0; i < numeroEventos; i++) {
                eventosQueue.put(eventosGenerator.generarEvento());
            }
            long time = System.nanoTime() - start;
            return time;
        }
    }

    class EventosListener implements Callable<Long> {

        private final int numeroEventos;

        public EventosListener(int numeroEventos) {
            this.numeroEventos = numeroEventos;
        }

        @Override
        public Long call() throws Exception {
            long start = System.nanoTime();
            for (int i = 0; i < numeroEventos; i++) {
                Evento evento = eventosQueue.take();
                alertasQueue.put(gestorAlertas.generarAlerta(evento, tipoService.findById(evento.getIdTipo())));
            }
            long time = System.nanoTime() - start;
            return time;
        }
    }

    class AlertasListener implements Callable<Long> {

        private final int numeroEventos;

        public AlertasListener(int numeroEventos) {
            this.numeroEventos = numeroEventos;
        }

        @Override
        public Long call() throws Exception {
            long start = System.nanoTime();
            List<Mensaje> mensajes = new ArrayList<>();
            for (int i = 0; i < numeroEventos; i++) {
                Alerta alerta = alertasQueue.take();
                mensajes.addAll(gestorAlertas.enviarAlerta(alerta));
            }

            for (Mensaje mensaje : mensajes) {
                System.out.println(mensaje);
            }
            long time = System.nanoTime() - start;

            return time;
        }

    }

}
