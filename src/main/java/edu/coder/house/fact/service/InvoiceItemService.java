package edu.coder.house.fact.service;

import edu.coder.house.fact.entity.InvoiceItem;
import edu.coder.house.fact.repository.InvoiceItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceItemService {

    @Autowired
    private InvoiceItemRepository repository;

    public InvoiceItemService(InvoiceItemRepository repository) {
        this.repository = repository;
    }

    public InvoiceItem save(InvoiceItem invoiceItem) {
        return repository.save(invoiceItem);
    }

    public List<InvoiceItem> getInvoiceItems() {
        return repository.findAll();
    }

    public Optional<InvoiceItem> getById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        Optional<InvoiceItem> invoiceItem = repository.findById(id);
        if (invoiceItem.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Detalle de factura no encontrado con ID: " + id);
        }
    }
}
