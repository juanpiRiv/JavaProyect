package edu.coder.house.fact.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceItem> items = new ArrayList<>();

    @Column
    private String date;

    @Column
    private String status;

    @Column
    private double total;

    public Invoice() {
        // Inicializa la lista de invoiceItems para evitar NullPointerException
        this.items = new ArrayList<>();
    }

    public void calcularTotal() {
        this.total = this.items.stream().mapToDouble(InvoiceItem::getSubtotal).sum();
    }

    public List<InvoiceItem> getInvoiceItems() {
        return items;
    }

    public void addInvoiceItem(InvoiceItem invoiceItem) {
        items.add(invoiceItem);
        invoiceItem.setInvoice(this);  // Asegúrate de que InvoiceItem tiene este método
        calcularTotal();
    }

    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        this.items = invoiceItems;
        calcularTotal();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {  // Asegúrate de que el tipo está especificado
        this.client = client;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void addItem(InvoiceItem item) {
        items.add(item);
        item.setInvoice(this);
    }

    public void removeItem(InvoiceItem item) {
        items.remove(item);
        item.setInvoice(null);
    }

    public UUID getClientId() {
        return client != null ? client.getId() : null;
    }
}