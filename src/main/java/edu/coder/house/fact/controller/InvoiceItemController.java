package edu.coder.house.fact.controller;

import edu.coder.house.fact.entity.InvoiceItem;
import edu.coder.house.fact.service.InvoiceItemService;
import edu.coder.house.fact.service.InvoiceService;
import edu.coder.house.fact.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @GetMapping(value = "/{invoiceId}/items", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<InvoiceItem>> getItemsByInvoiceId(@PathVariable UUID invoiceId) {
        List<InvoiceItem> items = invoiceItemService.findByInvoiceId(invoiceId);
        return ResponseEntity.ok(items);
    }
}