package egovframework.msa.order.service;

import egovframework.msa.order.api.OrderRequest;
import egovframework.msa.order.domain.PurchaseOrder;
import egovframework.msa.order.repository.PurchaseOrderRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class OrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    public OrderService(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Transactional(readOnly = true)
    public List<PurchaseOrder> getAll(Long userId) {
        if (userId == null) {
            return purchaseOrderRepository.findAll();
        }
        return purchaseOrderRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public PurchaseOrder getById(Long id) {
        return purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    public PurchaseOrder create(OrderRequest request) {
        return purchaseOrderRepository.save(
                new PurchaseOrder(request.userId(), request.productName(), request.quantity()));
    }

    public PurchaseOrder update(Long id, OrderRequest request) {
        PurchaseOrder order = getById(id);
        order.update(request.userId(), request.productName(), request.quantity());
        return order;
    }

    public void delete(Long id) {
        PurchaseOrder order = getById(id);
        purchaseOrderRepository.delete(order);
    }
}
