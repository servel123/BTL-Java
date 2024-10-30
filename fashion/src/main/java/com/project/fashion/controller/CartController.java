package com.project.fashion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

import com.project.fashion.dto.request.AddOrderLineDTO;
import com.project.fashion.dto.request.ListCartCreateBillDTO;
import com.project.fashion.model.Cart;
import com.project.fashion.model.Customer;
import com.project.fashion.model.OrderItem;
import com.project.fashion.model.OrderLine;
import com.project.fashion.service.implement.CartServiceImplement;
import com.project.fashion.service.implement.CustomerServiceImplement;
import com.project.fashion.service.implement.OrderItemServiceImplement;
import com.project.fashion.service.implement.OrderLineServiceImplement;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/cart")
public class CartController {

    @Autowired
    private CartServiceImplement cartServiceImplement;
    @Autowired
    private CustomerServiceImplement customerServiceImplement;
    @Autowired
    private OrderLineServiceImplement orderLineServiceImplement;
    @Autowired
    private OrderItemServiceImplement orderItemServiceImplement;

    @Autowired
    private Authen authen;

    private Long calculateTotal(ListCartCreateBillDTO bill) {
        List<Cart> carts = cartServiceImplement.getCartByListId(bill.getCarts());
        Long total = Long.valueOf(0);
        for (Cart cart : carts) {
            total += cart.getTotalPrice();
        }
        return total;
    }

    @GetMapping
    public String renderShowCart(Model model) {
        try {
            List<Cart> productOfUser = cartServiceImplement.getCartByCustomerId(authen.authen().getCustomerId());
            model.addAttribute("pOU", productOfUser);
            model.addAttribute("createBill", new ListCartCreateBillDTO());
        } catch (Exception e) {
            model.addAttribute("message", "Not Product");
        }
        return "cart";
    }

    // Get bill
    @GetMapping("/bill")
    public String showBill(@Valid @ModelAttribute("createBill") ListCartCreateBillDTO bills,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "redirect:/user/cart";
        }
        try {
            Customer customer = customerServiceImplement.getCustomerByUsername(authen.authen().getUsername());
            // show info customer like phone, address, fullname,... and options payment list
            // to customer choose
            model.addAttribute("customer", customer);
            // show items
            model.addAttribute("bills", cartServiceImplement.getCartByListId(bills.getCarts()));
            // money
            Long total = calculateTotal(bills);
            model.addAttribute("total", total);
            model.addAttribute("create", new ListCartCreateBillDTO());
            model.addAttribute("payment", new AddOrderLineDTO());
            return "bill";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "redirect:/user/cart";
        }
    }

    @PostMapping("/bill")
    public String createBill(@Valid @ModelAttribute("create") ListCartCreateBillDTO bills,
            @ModelAttribute("payment") AddOrderLineDTO payment,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "redirect:/user/cart";
        }
        try {
            List<Cart> carts = cartServiceImplement.getCartByListId(bills.getCarts());
            payment.setStatus("NOT_YET_PAID");
            OrderLine orderLine = orderLineServiceImplement.addOrderLine(payment);
            List<OrderItem> orderItems = orderItemServiceImplement.addOrderItemByOrderLine(orderLine.getOrderLineId(),
                    carts);
            orderItemServiceImplement.deleteOrderListItem(orderItems);
            model.addAttribute("messageCreateBillSuccess", "Order Successfully");

            return "redirect:/user/cart";
        } catch (Exception e) {
            model.addAttribute("messageErrorCreateBill", e.getMessage());
            return "redirect:/user/cart";
        }
    }

    // delete product
    @DeleteMapping
    public String deleteProduct(@RequestParam("cartId") Long cartId,
            Model model) {
        try {
            cartServiceImplement.removeProductFromCart(cartId);
            model.addAttribute("message", "Delete Successfully");
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "cart";
    }

    // partial update
    @PatchMapping
    public String updateProduct(@RequestParam("cartId") Long cartId,
            @RequestParam("quantity") Integer quantity,
            Model model) {
        try {
            Cart cart = cartServiceImplement.updateProductQuantity(cartId, quantity);
            model.addAttribute("product", cart);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "cart";
    }
}
