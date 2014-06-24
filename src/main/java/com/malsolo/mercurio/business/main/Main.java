/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */
package com.malsolo.mercurio.business.main;

import com.malsolo.mercurio.business.EventosValidador;
import com.malsolo.mercurio.business.GeneradorAlertasEstrategia;
import com.malsolo.mercurio.business.GestorAlertas;
import com.malsolo.mercurio.business.main.business.EventosValidadorImpl;
import com.malsolo.mercurio.business.main.business.GeneradorAlertasEstrategiaImpl;
import com.malsolo.mercurio.business.main.business.GestorAlertasImpl;
import com.malsolo.mercurio.business.main.service.AlertaServiceImpl;
import com.malsolo.mercurio.business.main.service.EventoServiceImpl;
import com.malsolo.mercurio.business.main.service.TipoServiceImpl;
import com.malsolo.mercurio.domain.Evento;
import com.malsolo.mercurio.exceptions.EventoInvalidoException;
import com.malsolo.mercurio.service.AlertaService;
import com.malsolo.mercurio.service.EventoService;
import com.malsolo.mercurio.service.TipoService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Javier Beneito Barquero <jbbarquero@gmail.com>
 */
public class Main {

    private final static Logger logger = LoggerFactory.getLogger(Main.class);

    private final static int NUMERO_EVENTOS = 100000;

    private final AlertasExecutor alertasExecutor;

    public static void main(String... args) throws EventoInvalidoException, InterruptedException {

        logger.info("MAIN Mercurio");
        
        Thread.sleep(5000);
        
        logger.info("Wake up, Mercurio!");

        long start = System.nanoTime();

        new Main().getAlertasExecutor().doStuff(NUMERO_EVENTOS);

        long time = System.nanoTime() - start;

        long seconds = TimeUnit.NANOSECONDS.toSeconds(time);
        long millis = TimeUnit.NANOSECONDS.toMillis(time)
                - TimeUnit.SECONDS.toMillis(seconds);

        tiempo("END MAIN Mercurio. It took ", time);
        logger.info("END MAIN Mercurio. Sending {} Events took {},{} s ({} us)"
                , NUMERO_EVENTOS, seconds, millis, time);

    }
    
    public static void tiempo(String message, long time) {
        long seconds = TimeUnit.NANOSECONDS.toSeconds(time);
        long millis = TimeUnit.NANOSECONDS.toMillis(time)
                - TimeUnit.SECONDS.toMillis(seconds);
        logger.info("{} {},{} s ({} us)", message, seconds, millis, time);
    }

    public Main() {

        EventosValidador eventosValidador = new EventosValidadorImpl();
        GeneradorAlertasEstrategia alertasEstrategia = new GeneradorAlertasEstrategiaImpl();
        EventoService eventoService = new EventoServiceImpl();
        AlertaService alertaService = new AlertaServiceImpl();
        TipoService tipoService = new TipoServiceImpl();

        GestorAlertas gestorAlertas = new GestorAlertasImpl(eventosValidador, alertasEstrategia, eventoService, alertaService);

        EventosGenerator eventosGenerator = new EventosGenerator() {
            
            AtomicLong counter = new AtomicLong(1);
            
            @Override
            public Evento generarEvento() {
                Evento evento = new Evento();
                evento.setId(counter.getAndIncrement());
                evento.setIdTipo(1L);
                evento.setDatos("datos");
                return evento;
            }
        };

       /*
        * NUMERO_EVENTOS = 100000;
        * [main] INFO  c.m.mercurio.business.main.Main - END MAIN Mercurio. It took 0,192 s (192862486 us)
        * Total time: 1.168s
        *
        * NUMERO_EVENTOS = 100000;
        * [main] INFO  c.m.mercurio.business.main.Main - END MAIN Mercurio. It took 41,729 s (41729141122 us)
        * Total time: 43.036s
        *
        * NUMERO_EVENTOS = 1000000;
        * [main] INFO  c.m.mercurio.business.main.Main - END MAIN Mercurio. It took 421,113 s (421113503448 us)
        * Total time: 7:02.503s
        */
//        this.alertasExecutor = 
//                new SingleThreadAlertasExecutor(gestorAlertas, tipoService, eventosGenerator);

       /*
        * NUMERO_EVENTOS = 100000;
        * [main] INFO  c.m.m.b.m.MultiThreadAlertasExecutor - Tiempo Generar eventos: 0,1 s (1213632 us)
        * [main] INFO  c.m.m.b.m.MultiThreadAlertasExecutor - Tiempo Generar alertas: 0,182 s (182384754 us)
        * [main] INFO  c.m.m.b.m.MultiThreadAlertasExecutor - Tiempo Enviar alertas: 0,182 s (182346715 us)
        * [main] INFO  c.m.m.b.m.MultiThreadAlertasExecutor - Tiempo total: 365945101 con 4 procesadores
        * [main] INFO  c.m.mercurio.business.main.Main - END MAIN Mercurio. It took 0,198 s (198878799 us)
        * Total time: 1.069s
        *
        * NUMERO_EVENTOS = 100000;
        * [main] INFO  c.m.m.b.m.MultiThreadAlertasExecutor - Tiempo Generar eventos: 27,19 s (27019778050 us)
        * [main] INFO  c.m.m.b.m.MultiThreadAlertasExecutor - Tiempo Generar alertas: 27,181 s (27181071187 us)
        * [main] INFO  c.m.m.b.m.MultiThreadAlertasExecutor - Tiempo Enviar alertas: 27,180 s (27180837743 us)
        * [main] INFO  c.m.m.b.m.MultiThreadAlertasExecutor - Tiempo total: 81381686980 con 4 procesadores
        * [main] INFO  c.m.mercurio.business.main.Main - END MAIN Mercurio. It took 27,287 s (27287706526 us)
        * Total time: 28.254s
        *
        * NUMERO_EVENTOS = 1000000;
        * [main] INFO  c.m.m.b.m.MultiThreadAlertasExecutor - Tiempo Generar eventos: 255,906 s (255906854252 us)
        * [main] INFO  c.m.m.b.m.MultiThreadAlertasExecutor - Tiempo Generar alertas: 256,194 s (256194003380 us)
        * [main] INFO  c.m.m.b.m.MultiThreadAlertasExecutor - Tiempo Enviar alertas: 256,193 s (256193274834 us)
        * [main] INFO  c.m.m.b.m.MultiThreadAlertasExecutor - Tiempo total: 768294132466 con 4 procesadores
        * [main] INFO  c.m.mercurio.business.main.Main - END MAIN Mercurio. It took 256,253 s (256253457874 us)
        * Total time: 4:17.670s
        */
        this.alertasExecutor = 
                new MultiThreadWithFrontAlertasExecutor(eventosGenerator, gestorAlertas, tipoService);
//                new MultiThreadAlertasExecutor(gestorAlertas, tipoService, eventosGenerator);

//        this.alertasExecutor = 
//                new SingleThreadWithFrontAlertasExecutor(gestorAlertas, tipoService, eventosGenerator);

    }

    AlertasExecutor getAlertasExecutor() {
        return this.alertasExecutor;
    }

    public Evento generarEvento() {
        return Mockito.mock(Evento.class);
    }

}
