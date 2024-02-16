package com.example.application.services;

import com.example.application.entities.Client;
import com.example.application.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// CRUD
@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // Create
    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    // Read
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // Search
    public List<Client> searchClientsByName(String name) {
        return clientRepository.findByFullnameContainingIgnoreCase(name);
    }

    // Delete
    public void deleteClient(Integer clientId) {
        clientRepository.deleteById(clientId);
    }
}