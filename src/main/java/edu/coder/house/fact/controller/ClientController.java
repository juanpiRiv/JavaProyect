package edu.coder.house.fact.controller;


import edu.coder.house.fact.entity.Client;
import edu.coder.house.fact.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }


    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<Client>> getAll() {
        Iterable<Client> clients = service.getClient();
        return ResponseEntity.ok(clients);
    }


    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Optional<Client>> getById(@PathVariable UUID id) {
        Optional<Client> client = service.getById(id);
        if (client.isPresent()) {
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Client> create(@RequestBody Client client) {
        try {
            Client newClient = service.save(client);
            return ResponseEntity.ok(newClient);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Client> update(@PathVariable UUID id, @RequestBody Client updatedClient) {
        Optional<Client> existingClient = service.getById(id);
        if (existingClient.isPresent()) {
            updatedClient.setId(id);
            Client client = service.save(updatedClient);
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        try {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
