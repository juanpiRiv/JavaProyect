package edu.coder.house.fact.controller;


import edu.coder.house.fact.entity.Client;
import edu.coder.house.fact.entity.Invoice;
import edu.coder.house.fact.entity.InvoiceItem;
import edu.coder.house.fact.service.ClientService;
import edu.coder.house.fact.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/invoices", produces = MediaType.APPLICATION_JSON_VALUE)
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ClientService clientService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createInvoice(@RequestBody Invoice invoice) {
        try {
            // Verificar si el cliente existe utilizando el ID desde el objeto cliente
            if (invoice.getClient() == null || invoice.getClient().getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client information is missing.");
            }

            // Busca al cliente utilizando su ID
            Optional<Client> clientOptional = clientService.findById(invoice.getClient().getId());

            if (clientOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
            }

            // Establecer el cliente en la factura usando el objeto Client directamente
            invoice.setClient(clientOptional.get());

            for (InvoiceItem item : invoice.getItems()) {
                item.setInvoice(invoice); // Establecer la relación entre el item y la factura
            }

            invoice.calcularTotal(); // Asegúrate de que este método calcule el total correctamente
            Invoice newInvoice = invoiceService.save(invoice); // Guarda la nueva factura
            return new ResponseEntity<>(newInvoice, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request: " + e.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getInvoices();
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable UUID id) {
        Optional<Invoice> invoice = invoiceService.getById(id);
        return invoice.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable UUID id, @RequestBody Invoice invoice) {
        Optional<Invoice> existingInvoice = invoiceService.getById(id);
        if (existingInvoice.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        invoice.setId(id);
        invoice.setClient(existingInvoice.get().getClient());
        Invoice updatedInvoice = invoiceService.save(invoice);
        return ResponseEntity.ok(updatedInvoice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoiceById(@PathVariable UUID id) {
        try {
            invoiceService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

