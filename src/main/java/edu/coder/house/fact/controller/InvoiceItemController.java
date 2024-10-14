package edu.coder.house.fact.controller;

import edu.coder.house.fact.entity.InvoiceItem;
import edu.coder.house.fact.service.InvoiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/invoice-items")
public class InvoiceItemController {

    @Autowired
    private InvoiceItemService invoiceItemService;

    public InvoiceItemController(InvoiceItemService invoiceItemService) {
        this.invoiceItemService = invoiceItemService;
    }

    @PostMapping
    public ResponseEntity<InvoiceItem> createInvoiceItem(@RequestBody InvoiceItem invoiceItem) {
        InvoiceItem savedInvoiceItem = invoiceItemService.save(invoiceItem);
        return ResponseEntity.ok(savedInvoiceItem);
    }

    @GetMapping
    public ResponseEntity<List<InvoiceItem>> getAllInvoiceItems() {
        List<InvoiceItem> invoiceItems = invoiceItemService.getInvoiceItems();
        return ResponseEntity.ok(invoiceItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceItem> getInvoiceItemById(@PathVariable Long id) {
        Optional<InvoiceItem> invoiceItem = invoiceItemService.getById(id);
        if (invoiceItem.isPresent()) {
            return ResponseEntity.ok(invoiceItem.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoiceItem(@PathVariable Long id) {
        try {
            invoiceItemService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
