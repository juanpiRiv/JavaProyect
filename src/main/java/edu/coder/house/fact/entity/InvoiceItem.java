package edu.coder.house.fact.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoice_item")
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    @JsonBackReference
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull
    @Column(nullable = false)
    private Integer quantity;

    @NotNull
    @Column(nullable = false)
    private Double subtotal;

    // methods

    public InvoiceItem(Invoice invoice, Product product, Integer quantity) {
        this.invoice = invoice;
        this.product = product;
        this.quantity = quantity;
        this.subtotal = calculateSubtotal();
    }

    public Double calculateSubtotal() {
        return this.product.getPrice() * this.quantity;
    }


    public void setSubtotal(){
        this.subtotal = this.calculateSubtotal();
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        this.subtotal = calculateSubtotal();
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public double getSubtotal() {
        return this.subtotal;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "id=" + id +
                ", invoiceId=" + (invoice != null ? invoice.getId() : "null") +
                ", productId=" + (product != null ? product.getId() : "null") +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                '}';
    }

}