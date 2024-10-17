package edu.coder.house.fact.service;

import edu.coder.house.fact.entity.Invoice;
import edu.coder.house.fact.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository repository;

    public Invoice save(Invoice invoice) {
        return repository.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return repository.findAll();
    }

    public Optional<Invoice> getById(UUID id) {
        return repository.findById(id);
    }


    public void deleteById(UUID id) {
        Optional<Invoice> invoice = repository.findById(id);
        if (invoice.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Factura no encontrada con ID: " + id);
        }
    }
}
