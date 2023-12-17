package com.evento;

import java.text.SimpleDateFormat;
import java.util.List;
import java.text.ParseException;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api/v1/eventi")
@CrossOrigin(origins = "*")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping("/addevento")
    public ResponseEntity<Evento> creaEvento(@RequestBody Evento evento) {
        Evento nuovoEvento = eventoService.addEvento(evento);
        return new ResponseEntity<>(nuovoEvento, HttpStatus.CREATED);
    }

    @PutMapping("/updateevento/{id}")
    public ResponseEntity<Evento> aggiornaEvento(@PathVariable long id, @RequestBody Evento evento) {
        Evento eventoAggiornato = eventoService.aggiornaEvento(id, evento);
        if (eventoAggiornato != null) {
            return new ResponseEntity<>(eventoAggiornato, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/geteventibydate/{data}")
public ResponseEntity<List<Evento>> getEventiByDate(@PathVariable("data") String dataString) {
    try {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date data = dateFormat.parse(dataString);
        
        // Chiamata al servizio per ottenere gli eventi in base alla data
        List<Evento> eventiByDate = eventoService.getEventiByDate(data);
        
        if (eventiByDate != null && !eventiByDate.isEmpty()) {
            return new ResponseEntity<>(eventiByDate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } catch (ParseException e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}


    @DeleteMapping("/deleteevento/{id}")
    public ResponseEntity<Void> eliminaEvento(@PathVariable long id) {
        boolean eliminato = eventoService.eliminaEvento(id);
        if (eliminato) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
