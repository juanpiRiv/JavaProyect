package edu.coder.house.fact.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(nullable = false)
    private LocalDateTime date;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceItem> items = new ArrayList<>();; // Inicializar la lista

    @Column(nullable = false)
    private Double totalAmount;

    public Invoice(Client client, LocalDateTime date, Double totalAmount) {
        this.client = client;
        this.date = date;
        this.totalAmount = totalAmount;
        this.items = new ArrayList<>();
    }
    public void addItem(InvoiceItem item) {
        items.add(item);
        item.setInvoice(this);
        totalAmount += item.getSubtotal();
    }


    public void removeItem(InvoiceItem item) {
        items.remove(item);
        item.setInvoice(null);
        totalAmount -= item.getSubtotal();
    }


    public void calculateTotal() {
        totalAmount = items.stream()
                .mapToDouble(InvoiceItem::getSubtotal)
                .sum();
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public Client getClient() {
        return client;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}