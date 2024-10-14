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
    private InvoiceRepository invoiceRepository;


    public Invoice save(Invoice invoice) {
        invoice.calcularTotal();
        return invoiceRepository.save(invoice);
    }


    public List<Invoice> getInvoices() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> getById(UUID id) {
        return invoiceRepository.findById(id);
    }


    public void deleteById(UUID id) {
        invoiceRepository.deleteById(id);
    }
}
