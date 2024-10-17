package edu.coder.house.fact.controller;

import edu.coder.house.fact.entity.Invoice;
import edu.coder.house.fact.entity.InvoiceItem;
import edu.coder.house.fact.entity.Product;
import edu.coder.house.fact.service.InvoiceItemService;
import edu.coder.house.fact.service.InvoiceService;
import edu.coder.house.fact.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceItemController {

    @Autowired
    private InvoiceItemService invoiceItemService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/{invoiceId}/items", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<InvoiceItem>> getItemsByInvoiceId(@PathVariable UUID invoiceId) {
        List<InvoiceItem> items = invoiceItemService.findByInvoiceId(invoiceId);
        return ResponseEntity.ok(items);
    }

    @PostMapping(value = "/{invoiceId}/items", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<InvoiceItem> addInvoiceItemToInvoice(
            @PathVariable UUID invoiceId,
            @RequestBody InvoiceItem item) {
        Optional<Invoice> invoice = invoiceService.getById(invoiceId);

        if (invoice.isPresent()) {
            Optional<Product> product = productService.getById(item.getProduct().getId());
            if (product.isPresent()) {
                item.setInvoice(invoice.get());
                item.setProduct(product.get());
                item.setPrice(product.get().getPrice());
                item.setSubtotal(item.getPrice() * item.getQuantity());
                InvoiceItem savedItem = invoiceItemService.save(item);
                return ResponseEntity.ok(savedItem);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

