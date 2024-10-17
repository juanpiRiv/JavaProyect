package edu.coder.house.fact.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

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
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull
    @Column(nullable = false)
    private Integer quantity;

    @NotNull
    @Column(nullable = false)
    private Double price;

    @NotNull
    @Column(nullable = false)
    private Double subtotal;


    public InvoiceItem(Invoice invoice, Product product, Integer quantity, Double price) {
        this.invoice = invoice;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = calculateSubtotal();
    }

    public Double calculateSubtotal() {
        return this.price * this.quantity;
    }

    public void setPrice(Double price) {
        this.price = price;
        this.subtotal = calculateSubtotal();
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        this.subtotal = calculateSubtotal();
    }


    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Double getPrice() {
        return price;
    }

    public double getSubtotal() {
        return this.subtotal;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setSubtotal(double i) {
        this.subtotal= (double) i;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

