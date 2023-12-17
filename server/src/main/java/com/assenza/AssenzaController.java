package com.assenza;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/assenze")
@CrossOrigin(origins = "*")
public class AssenzaController {

    @Autowired
    private AssenzaService assenzaService;

    @GetMapping("/getassenzabydata/{data}")
    public ResponseEntity<Assenza> getAssenzaByData(@PathVariable("data") String dataString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date data = dateFormat.parse(dataString);
            Assenza assenza = assenzaService.getAssenzaByData(data);
            if (assenza != null) {
                return new ResponseEntity<>(assenza, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (ParseException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/adddipendente/{data}/{dipendenteId}")
    @PreAuthorize("hasAuthority('PROPRIETARIO') || hasAuthority('REFERENTE')|| hasAuthority('DIPENDENTE')")
    public ResponseEntity<Assenza> addDipendenteToAssenza(
            @PathVariable("data") @DateTimeFormat(pattern = "yyyy-MM-dd") Date data,
            @PathVariable("dipendenteId") String dipendenteId) {

        Assenza assenza = assenzaService.getAssenzaByData(data);

        if (assenza != null) {
            assenza = assenzaService.addDipendenteToAssenza(assenza.getId(), dipendenteId);
        } else {
            assenza = assenzaService.addAssenza(data, dipendenteId);
        }
        return ResponseEntity.ok(assenza);

    }

    @DeleteMapping("/removedipendente/{data}/{dipendenteId}")
    @PreAuthorize("hasAuthority('PROPRIETARIO') || hasAuthority('REFERENTE')|| hasAuthority('DIPENDENTE')")
    public ResponseEntity<Assenza> removeDipendenteFromAssenza(
            @PathVariable("data") String dataString,
            @PathVariable("dipendenteId") String dipendenteId) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date data = dateFormat.parse(dataString);
            Assenza updatedAssenza = assenzaService.removeDipendenteFromAssenza(data, dipendenteId);

            if (updatedAssenza.getDipendenti().isEmpty()) {
                boolean deleted = assenzaService.deleteAssenzaByData(data);
                if (deleted) {
                    return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>(updatedAssenza, HttpStatus.OK);
            }

        } catch (ParseException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasAuthority('PROPRIETARIO') || hasAuthority('REFERENTE')|| hasAuthority('DIPENDENTE')")
    @GetMapping("/getassenzedipendente/{dipendenteId}")
    public ResponseEntity<List<Assenza>> getAssenzeByDipendente(@PathVariable("dipendenteId") String dipendenteId) {
        List<Assenza> assenze = assenzaService.getAssenzeByDipendente(dipendenteId);
        return new ResponseEntity<>(assenze, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('PROPRIETARIO') || hasAuthority('REFERENTE')|| hasAuthority('DIPENDENTE')")
    @GetMapping("/getassenzedipendente/{dipendenteId}/bydata")
    public ResponseEntity<List<Assenza>> getAssenzeByDipendenteAndData(
            @PathVariable("dipendenteId") String dipendenteId,
            @RequestParam(name = "data", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date data) {
        List<Assenza> assenze;

        if (data != null) {
            assenze = assenzaService.getAssenzeByDipendenteAndData(dipendenteId, data);
        } else {
            assenze = assenzaService.getAssenzeByDipendente(dipendenteId);
        }

        return new ResponseEntity<>(assenze, HttpStatus.OK);
    }
}
