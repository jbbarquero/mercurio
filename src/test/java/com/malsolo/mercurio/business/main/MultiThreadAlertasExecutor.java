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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
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

    public MultiThreadAlertasExecutor(GestorAlertas gestorAlertas, TipoService tipoService, EventosGenerator eventosGenerator) {
        this.eventosGenerator = eventosGenerator;
        this.gestorAlertas = gestorAlertas;
        this.tipoService = tipoService;
    }

    @Override
    public void doStuff(int numeroEventos) throws EventoInvalidoException {

        try {
            BlockingQueue<Evento> eventosQueue = new ArrayBlockingQueue(QUEUE_CAPACITY);
            BlockingQueue<Alerta> alertasQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);

            int processors = Runtime.getRuntime().availableProcessors();
            logger.info("Available processors: {}", processors);
            ExecutorService executorService
                    = Executors.newFixedThreadPool(processors);

            Set<Callable<Long>> callables = new HashSet<>();
            callables.add(new EventosReceiver(numeroEventos, eventosQueue, eventosGenerator));
            callables.add(new EventosListener(numeroEventos, eventosQueue, alertasQueue, gestorAlertas, tipoService));
            callables.add(new AlertasListener(numeroEventos, alertasQueue, gestorAlertas));

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
                long seconds = TimeUnit.NANOSECONDS.toSeconds(l);
                long millis = TimeUnit.NANOSECONDS.toMillis(l)
                        - TimeUnit.SECONDS.toMillis(seconds);
                logger.info("Tiempo {}: {},{} s ({} us)", callableName, seconds, millis, l);
                totalTime += l;
                i++;
            }
            logger.info("Tiempo total: {} con {} procesadores", totalTime, processors);

            executorService.shutdown();

        } catch (InterruptedException | ExecutionException ex) {
            logger.error(ex.toString());
        }
    }

}

class EventosReceiver implements Callable<Long> {

    private final AtomicInteger numeroEventos;
    private final BlockingQueue<Evento> eventosQueue;
    private final EventosGenerator eventosGenerator;
    //private CountDownLatch latch;

    public EventosReceiver(int numeroEventos, BlockingQueue<Evento> eventosQueue, EventosGenerator eventosGenerator) {
        this.numeroEventos = new AtomicInteger(numeroEventos);
        this.eventosQueue = eventosQueue;
        this.eventosGenerator = eventosGenerator;
    }

    @Override
    public Long call() throws Exception {
        long start = System.nanoTime();
        while (numeroEventos.getAndDecrement() > 0) {
            this.eventosQueue.put(this.eventosGenerator.generarEvento());
        }
        long time = System.nanoTime() - start;
        //latch.countDown();
        return time;
    }
}

class EventosListener implements Callable<Long> {

    private final AtomicInteger numeroEventos;
    private final BlockingQueue<Evento> eventosQueue;
    private final BlockingQueue<Alerta> alertasQueue;
    private final GestorAlertas gestorAlertas;
    private final TipoService tipoService;

    //private CountDownLatch latch;
    public EventosListener(int numeroEventos, BlockingQueue<Evento> eventosQueue, BlockingQueue<Alerta> alertasQueue, GestorAlertas gestorAlertas, TipoService tipoService) {
        this.numeroEventos = new AtomicInteger(numeroEventos);
        this.eventosQueue = eventosQueue;
        this.alertasQueue = alertasQueue;
        this.gestorAlertas = gestorAlertas;
        this.tipoService = tipoService;
    }

    @Override
    public Long call() throws Exception {
        long start = System.nanoTime();
        while (numeroEventos.getAndDecrement() > 0) {
            Evento evento = this.eventosQueue.take();
            alertasQueue.put(gestorAlertas.generarAlerta(evento, tipoService.findById(evento.getIdTipo())));
        }
        long time = System.nanoTime() - start;
        //latch.countDown();
        return time;
    }
}

class AlertasListener implements Callable<Long> {

    private final AtomicInteger numeroEventos;
    private final BlockingQueue<Alerta> alertasQueue;
    private final GestorAlertas gestorAlertas;

    //private CountDownLatch latch;
    public AlertasListener(int numeroEventos, BlockingQueue<Alerta> alertasQueue, GestorAlertas gestorAlertas) {
        this.numeroEventos = new AtomicInteger(numeroEventos);
        this.alertasQueue = alertasQueue;
        this.gestorAlertas = gestorAlertas;
    }

    @Override
    public Long call() throws Exception {
        long start = System.nanoTime();
        List<Mensaje> mensajes = new ArrayList<>();
        while (numeroEventos.getAndDecrement() > 0) {
            Alerta alerta = this.alertasQueue.take();
            mensajes.addAll(gestorAlertas.enviarAlerta(alerta));
        }

        for (Mensaje mensaje : mensajes) {
            System.out.println(mensaje);
        }
        long time = System.nanoTime() - start;
        //latch.countDown();

        return time;
    }

}
