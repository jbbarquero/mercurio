/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */

package com.malsolo.mercurio.business;

import com.malsolo.mercurio.domain.Alerta;
import com.malsolo.mercurio.domain.Evento;
import com.malsolo.mercurio.domain.Mensaje;
import com.malsolo.mercurio.service.EventoService;
import com.malsolo.mercurio.service.TipoService;
import java.util.List;

/**
 *
 * @author Javier Beneito Barquero <jbbarquero@gmail.com>
 */
public class Main {
    
    private static GestorAlertas gestorAlertas;
    private static TipoService tipoService;

    public Main() {
    }
    
    public Evento generarEvento() {
        return new Evento();
    }
    
    public static void main(String... args) {
        
        Main main = new Main();
        Evento evento = Main.gestorAlertas.recibirEvento(main.generarEvento());
        List<Mensaje> mensajes = Main.gestorAlertas.enviarAlerta(gestorAlertas.generarAlerta(evento, Main.tipoService.findById(evento.getIdTipo())));
        for (Mensaje mensaje : mensajes) {
            System.out.println(mensaje);
        }
        
    }
    
}
