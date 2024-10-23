package edu.coder.house.fact.service;

import edu.coder.house.fact.entity.Invoice;
import edu.coder.house.fact.entity.InvoiceItem;
import edu.coder.house.fact.entity.Product;
import edu.coder.house.fact.repository.InvoiceItemRepository;
import edu.coder.house.fact.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvoiceItemService {

    @Autowired
    private InvoiceItemRepository repository;

    @Autowired
    private ProductRepository productRepository;

    public InvoiceItem save(InvoiceItem item) {
        return repository.save(item);
    }

    public List<InvoiceItem> getAllItems() {
        return repository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<InvoiceItem> findByInvoiceId(UUID invoiceId) {
        return repository.findByInvoiceId(invoiceId);
    }

    public void deleteById(UUID id) {
        Optional<InvoiceItem> item = repository.findById(id);
        if (item.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Item de factura no encontrado con ID: " + id);
        }
    }
}
