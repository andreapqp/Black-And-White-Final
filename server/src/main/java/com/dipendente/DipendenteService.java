package com.dipendente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DipendenteService {

    @Autowired
    private DipendenteRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Dipendente addDipendente(Dipendente dipendente) {
        String encryptedPassword = passwordEncoder.encode(dipendente.getPassword());
        dipendente.setPassword(encryptedPassword);
        return repository.save(dipendente);
    }

    public Dipendente getDipendenteById(String id) {
        return repository.findById(id).orElse(null);
    }

    public boolean deleteDipendente(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<Dipendente> getAllDipendenti() {
        return repository.findAll();
    }
}
