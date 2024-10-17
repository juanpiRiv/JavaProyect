package edu.coder.house.fact.controller;

import edu.coder.house.fact.entity.Invoice;
import edu.coder.house.fact.entity.InvoiceItem;
import edu.coder.house.fact.entity.Product;
import edu.coder.house.fact.service.InvoiceItemService;
import edu.coder.house.fact.service.InvoiceService;
import edu.coder.house.fact.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Get items by invoice id")
    @GetMapping(value = "/{invoiceId}/items", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<InvoiceItem>> getItemsByInvoiceId(@PathVariable UUID invoiceId) {
        List<InvoiceItem> items = invoiceItemService.findByInvoiceId(invoiceId);
        return ResponseEntity.ok(items);
    }

    @Operation(summary = "Add item to invoice")
    @PostMapping(value = "/{invoiceId}/items", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<InvoiceItem> addInvoiceItemToInvoice(
            @PathVariable UUID invoiceId,
            @RequestBody InvoiceItem item) {

        System.out.println("InvoiceItem recibido: " + item);


        Optional<Invoice> invoiceOptional = invoiceService.getById(invoiceId);
        if (!invoiceOptional.isPresent()) {
            System.out.println("Factura no encontrada con ID: " + invoiceId);
            return ResponseEntity.notFound().build();
        }

        Optional<Product> productOptional = productService.getById(item.getProduct().getId());
        if (!productOptional.isPresent()) {
            System.out.println("Producto no encontrado con ID: " + item.getProduct().getId());
            return ResponseEntity.badRequest().body(null);
        }

        Product product = productOptional.get();
        item.setInvoice(invoiceOptional.get());
        item.setProduct(product);
        item.setPrice(product.getPrice());

        if (item.getPrice() == null) {
            System.out.println("El precio es nulo.");
            return ResponseEntity.badRequest().body(null);
        }

        item.setSubtotal(item.getPrice() * item.getQuantity());

        InvoiceItem savedItem = invoiceItemService.save(item);
        return ResponseEntity.ok(savedItem);
    }


}

