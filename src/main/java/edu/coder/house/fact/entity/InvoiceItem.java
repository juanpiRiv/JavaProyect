package edu.coder.house.fact.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "invoice_item")
@Getter
@Setter
@NoArgsConstructor // Constructor sin argumentos para JPA
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private double subtotal;

    public void calcularSubtotal() {
        this.subtotal = this.quantity * this.price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calcularSubtotal();
    }

    public void setPrice(double price) {
        this.price = price;
        calcularSubtotal();
    }


    public double getSubtotal() {
        return this.subtotal;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

}
