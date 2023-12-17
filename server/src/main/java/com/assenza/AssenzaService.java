package com.assenza;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class AssenzaService {

    @Autowired
    private AssenzaRepository assenzaRepository;

    public List<Assenza> getAllAssenze() {
        return assenzaRepository.findAll();
    }

    public Assenza getAssenzaByData(Date data) {
        return assenzaRepository.findByData(data).orElse(null);
    }

    public boolean deleteAssenzaByData(Date data) {
        Assenza assenza = assenzaRepository.findByData(data).orElse(null);
        if (assenza != null) {
            assenzaRepository.delete(assenza);
            return true;
        } else {
            return false;
        }
    }

    public Assenza addDipendenteToAssenza(Date data, String dipendenteId) {
        Assenza assenza = assenzaRepository.findByData(data).orElse(null);
        if (assenza != null) {
            assenza.getDipendenti().add(dipendenteId);
            return assenzaRepository.save(assenza);
        } else {
            return null;
        }
    }

    public Assenza removeDipendenteFromAssenza(Date data, String dipendenteId) {
        Assenza assenza = assenzaRepository.findByData(data).orElse(null);
        if (assenza != null) {
            assenza.getDipendenti().remove(dipendenteId);
            return assenzaRepository.save(assenza);
        } else {
            return null;
        }
    }

    public List<Assenza> getAssenzeByDipendente(String dipendenteId) {
        List<Assenza> assenze = assenzaRepository.findByDipendentiContaining(dipendenteId);
        if (assenze != null) {
            return assenze;
        } else {
            return Collections.emptyList();
        }
    }

    public Assenza addAssenza(Date data, String dipendenteId) {
        Assenza assenza = new Assenza();
        assenza.setData(data);
        assenza.setDipendenti(new ArrayList<>());
        assenza.getDipendenti().add(dipendenteId);
        return assenzaRepository.save(assenza);
    }
    

    public Assenza addDipendenteToAssenza(String assenzaId, String dipendenteId) {
        Assenza assenza = assenzaRepository.findById(assenzaId).orElse(null);

        if (assenza != null) {
            assenza.getDipendenti().add(dipendenteId);
            return assenzaRepository.save(assenza);
        }

        return null;
    }

    public List<Assenza> getAssenzeByDipendenteAndData(String dipendenteId, Date data) {
        return assenzaRepository.findByDipendentiContainingAndData(dipendenteId, data);
    }
}
