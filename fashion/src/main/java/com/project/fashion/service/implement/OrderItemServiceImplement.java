/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.fashion.service.implement;

import com.project.fashion.exception.ResourceNotFoundException;
import com.project.fashion.model.Cart;
import com.project.fashion.model.OrderItem;
import com.project.fashion.model.OrderLine;
import com.project.fashion.model.Product;
import com.project.fashion.repository.OrderItemReponsitory;
import com.project.fashion.repository.OrderLineReponsitory;
import com.project.fashion.repository.ProductRepository;
import com.project.fashion.service.OrderItemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Vu
 */
@Service
public class OrderItemServiceImplement implements OrderItemService {

    @Autowired
    private OrderItemReponsitory orderItemReponsitory;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderLineReponsitory orderLineRepository;

    @Autowired
    private OrderLineServiceImplement orderLineServiceImplement;
    // create

    @Override
    public List<OrderItem> addOrderItemByOrderLine(Long orderLineId, List<Cart> carts) {
        for (Cart cart : carts) {
            OrderItem orderItem = OrderItem.builder()
                    .orderLine(orderLineServiceImplement.getOrderLineById(orderLineId))
                    .product(cart.getProduct())
                    .quantity(cart.getQuantity())
                    .build();
            orderItemReponsitory.save(orderItem);
        }
        return getOrderItemByOrderLine(orderLineId);
    }

    // read
    @Override
    public List<OrderItem> getOrderItemByOrderLine(Long orderLineId) {
        List<OrderItem> orderItems = orderItemReponsitory.findAllByOrderLine_OrderLineId(orderLineId);
        if (orderItems.isEmpty()) {
            throw new ResourceNotFoundException("Not OrderItem");
        }
        return orderItems;
    }

    @Override
    public List<OrderItem> getOrderItemByProduct(Long productId) {
        Product pro = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Not Product"));
        return pro.getOrderItems();
    }

    @Override
    public List<OrderItem> getAllOrderItem() {
        return orderItemReponsitory.findAll();
    }

    // update
    @Override
    public OrderItem updateOrderItem(Long orderLineId, Product product, Integer quantity) {
        OrderLine orderLine = orderLineRepository.findById(orderLineId)
                .orElseThrow(() -> new ResourceNotFoundException("Not OrderLine"));
        OrderItem items = new OrderItem();
        for (OrderItem item : orderLine.getOrderItems()) {
            if (item.getProduct().getProductId() == product.getProductId() && quantity > 0) {
                item.setQuantity(quantity);
                orderItemReponsitory.save(item);
                items = item;
                break;
            }
        }
        return items;
    }

    // delete
    @Override
    public void deleteOrderItem(Long orderItemId) {
        orderItemReponsitory.deleteById(orderItemId);
    }

    public void deleteOrderListItem(List<OrderItem> orderItems) {
        orderItemReponsitory.deleteAll(orderItems);
    }
}
