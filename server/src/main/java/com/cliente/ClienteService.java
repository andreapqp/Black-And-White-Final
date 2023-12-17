package com.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente addCliente(Cliente cliente) {
        return repository.save(cliente);
    }

    public Cliente getClienteById(String id) {
        return repository.findById(id).orElse(null);
    }

    public boolean deleteCliente(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<Cliente> getAllClienti() {
        return repository.findAll();
    }
}
