package edu.coder.house.fact.controller;

import edu.coder.house.fact.entity.Invoice;
import edu.coder.house.fact.entity.InvoiceItem;
import edu.coder.house.fact.service.InvoiceItemService;
import edu.coder.house.fact.service.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceItemService invoiceItemService;
    @Operation(summary = "Create Invoice")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Invoice> create(@RequestBody Invoice invoice) {
        try {
            Invoice newInvoice = invoiceService.save(invoice);
            return ResponseEntity.status(HttpStatus.CREATED).body(newInvoice);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    @Operation(summary = "Get all invoices")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<Invoice>> getAll() {
        Iterable<Invoice> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }
    @Operation(summary = "Get by id invoice")
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Optional<Invoice>> getById(@PathVariable UUID id) {
        Optional<Invoice> invoice = invoiceService.getById(id);
        if (invoice.isPresent()) {
            return ResponseEntity.ok(invoice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(summary = "Add items to invoice")
    @PostMapping("/{invoiceId}/items")
    public ResponseEntity<?> addItemsToInvoice(@PathVariable UUID invoiceId, @RequestBody List<InvoiceItem> items) {
        Optional<Invoice> invoice = invoiceService.getById(invoiceId);
        if (!invoice.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invoice not found");
        }

        for (InvoiceItem item : items) {
            item.setInvoice(invoice.get());
            invoiceItemService.save(item);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Items added successfully");
    }
}

