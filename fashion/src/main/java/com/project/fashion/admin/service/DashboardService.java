package com.project.fashion.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.fashion.model.Category;
import com.project.fashion.model.OrderItem;
import com.project.fashion.model.OrderLine;
import com.project.fashion.model.Product;
import com.project.fashion.repository.OrderLineReponsitory;
import com.project.fashion.service.implement.CategoryServiceImplement;
import com.project.fashion.service.implement.OrderItemServiceImplement;

public class DashboardService {
    @Autowired
    private OrderLineReponsitory orderLineRepository;
    @Autowired
    private OrderItemServiceImplement orderItemServiceImplement;

    @Autowired
    private CategoryServiceImplement categoryServiceImplement;

    @Autowired

    // tổng doanh thu
    public Long totalRevenue() {
        List<OrderItem> items = orderItemServiceImplement.getAllOrderItem();
        Long money = Long.valueOf(0);
        for (OrderItem item : items) {
            money += item.getQuantity() * item.getProduct().getPrice();
        }
        return money;
    }

    // tông số đơn hàng theo tháng
    public List<Object[]> getOrderLineCountByMonth(int year) {
        return orderLineRepository.countOrderLineByMonth(year);
    }

    // danh sách đơn hàng theo tháng
    public List<OrderLine> getOrderLineByMonth(int year, int month) {
        return orderLineRepository.findOrderLineByMonth(year, month);
    }

    // số sản phẩm của từng danh mục
    public List<Object[]> getAmountByCategory() {
        List<Category> categories = categoryServiceImplement.getAllCategories();
        List<Object[]> categoryCount = new ArrayList<>();
        for (Category category : categories) {
            Object[] ob = new Object[2];
            ob[0] = category.getName();
            ob[1] = category.getProducts().size();
            categoryCount.add(ob);
        }
        return categoryCount;
    }

    // số sản phẩm đã bán của từng danh mục
    public List<Object[]> getCategorySold() {
        List<Category> categories = categoryServiceImplement.getAllCategories();
        List<Object[]> categoryCount = new ArrayList<>();
        for (Category category : categories) {
            Object[] ob = new Object[2];
            ob[0] = category.getName();
            Long totalProduct = Long.valueOf(0);
            for (Product product : category.getProducts()) {
                totalProduct += product.getOrderItems().size();
            }
            ob[1] = totalProduct;
            categoryCount.add(ob);
        }
        return categoryCount;
    }

    // doanh thu mỗi tháng của năm
    public List<Object[]> getRevenueByMonth(int year) {
        List<Object[]> ls = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            Object[] ob = new Object[2];
            ob[0] = i;
            Long totalMonth = Long.valueOf(0);
            for (OrderLine orderLine : getOrderLineByMonth(year, i)) {
                for (OrderItem orderItem : orderLine.getOrderItems()) {
                    totalMonth += orderItem.getQuantity() * orderItem.getProduct().getPrice();
                }
            }
            ob[1] = totalMonth;
            ls.add(ob);
        }
        return ls;
    }
}
