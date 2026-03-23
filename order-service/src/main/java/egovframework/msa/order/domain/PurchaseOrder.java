package egovframework.msa.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 120)
    private String productName;

    @Column(nullable = false)
    private Integer quantity;

    protected PurchaseOrder() {
    }

    public PurchaseOrder(Long userId, String productName, Integer quantity) {
        this.userId = userId;
        this.productName = productName;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void update(Long userId, String productName, Integer quantity) {
        this.userId = userId;
        this.productName = productName;
        this.quantity = quantity;
    }
}
