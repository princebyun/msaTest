package egovframework.msa.order.api;

import egovframework.msa.order.domain.PurchaseOrder;

public record OrderResponse(Long id, Long userId, String productName, Integer quantity) {

    public static OrderResponse from(PurchaseOrder order) {
        return new OrderResponse(order.getId(), order.getUserId(), order.getProductName(), order.getQuantity());
    }
}
