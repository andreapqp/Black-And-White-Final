package com.dipendente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dipendenti")
@CrossOrigin(origins = "*")
public class DipendenteController {

    @Autowired
    private DipendenteService service;

    @PostMapping("/adddipendente")
    @PreAuthorize("hasAuthority('PROPRIETARIO') || hasAuthority('REFERENTE')")
    public ResponseEntity<Dipendente> addDipendente(@RequestBody Dipendente dipendente) {
        Dipendente savedDipendente = service.addDipendente(dipendente);
        return new ResponseEntity<>(savedDipendente, HttpStatus.CREATED);
    }

    @GetMapping("/getalldipendenti")
    @PreAuthorize("hasAuthority('PROPRIETARIO') || hasAuthority('REFERENTE')")
    public ResponseEntity<List<Dipendente>> getAllDipendenti() {
        final List<Dipendente> dipendenti = service.getAllDipendenti();
        return new ResponseEntity<>(dipendenti, HttpStatus.OK);
    }

    @GetMapping("/getdipendente/{id}")
    @PreAuthorize("hasAuthority('PROPRIETARIO') || hasAuthority('REFERENTE')")
    public ResponseEntity<Dipendente> getDipendenteById(@PathVariable String id) {
    Dipendente dipendente = service.getDipendenteById(id);
    if (dipendente != null) {
        return new ResponseEntity<>(dipendente, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

    @DeleteMapping("/deletedipendente/{id}")
    @PreAuthorize("hasAuthority('PROPRIETARIO') || hasAuthority('REFERENTE')")
    public ResponseEntity<String> deleteDipendente(@PathVariable String id) {
        boolean deleted = service.deleteDipendente(id);
        if (deleted) {
            return new ResponseEntity<>("Dipendente eliminato correttamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Impossibile trovare il Dipendente con l'ID specificato", HttpStatus.NOT_FOUND);
        }
    }

}
