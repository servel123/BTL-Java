package com.project.fashion.service;

import com.project.fashion.model.Cart;
import com.project.fashion.model.OrderItem;
import com.project.fashion.model.Product;
import java.util.List;

public interface OrderItemService {
    // create
    List<OrderItem> addOrderItemByOrderLine(Long orderLineId, List<Cart> carts);

    // read
    List<OrderItem> getAllOrderItem();

    List<OrderItem> getOrderItemByOrderLine(Long orderLineId);

    List<OrderItem> getOrderItemByProduct(Long productId);

    // update
    OrderItem updateOrderItem(Long orderLineId, Product product, Integer quantity);

    // delete
    void deleteOrderItem(Long orderItemId);

    void deleteOrderListItem(List<OrderItem> orderItems);

}
