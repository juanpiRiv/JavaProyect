package edu.coder.house.fact.controller;

import edu.coder.house.fact.entity.Invoice;
import edu.coder.house.fact.entity.InvoiceItem;
import edu.coder.house.fact.service.InvoiceItemService;
import edu.coder.house.fact.service.InvoiceService;
import edu.coder.house.fact.service.ProductService;

import edu.coder.house.fact.entity.Product;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ProductService productService;

    @Operation(summary = "Create Invoice")
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
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
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Iterable<Invoice>> getAll() {
        Iterable<Invoice> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }

    @Operation(summary = "Get by id invoice")
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
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
    public ResponseEntity<?> addItemsToInvoice(
            @PathVariable UUID invoiceId,
            @RequestBody List<InvoiceItem> request) {

        Optional<Invoice> invoiceOpt = invoiceService.getById(invoiceId);

        if (!invoiceOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invoice not found");
        }

        Invoice invoice = invoiceOpt.get();

        invoice.clearItems();
        List<InvoiceItem> newItems = new ArrayList<>();

        try {

            for (InvoiceItem item : request) {
                if (item.getProduct() == null || item.getProduct().getId() == null) {
                    throw new RuntimeException("Product not found with ID");
                }

                Optional<Product> productOpt = productService.getById(item.getProduct().getId());

                if (!productOpt.isPresent()) {
                    throw new RuntimeException("Product not found with ID: " + item.getProduct().getId());
                }

                item.setProduct(productOpt.get());
                item.setInvoice(invoice);
                item.setSubtotal();

                newItems.add(item);
            }

            invoice.getItems().addAll(newItems);

            invoice.calculateTotal();

            Invoice updatedInvoice = invoiceService.save(invoice);

            return ResponseEntity.status(HttpStatus.CREATED).body(updatedInvoice);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding items");
        }
    }

}

