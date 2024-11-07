package com.project.fashion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

import com.project.fashion.dto.request.AddOrderLineDTO;
import com.project.fashion.dto.request.AddPaymentDTO;
import com.project.fashion.dto.request.ListCartCreateBillDTO;
import com.project.fashion.dto.response.CustomerDetailResponse;
import com.project.fashion.model.Cart;
import com.project.fashion.model.Customer;

import com.project.fashion.model.OrderLine;
import com.project.fashion.model.Payment;
import com.project.fashion.service.implement.CartServiceImplement;
import com.project.fashion.service.implement.CustomerServiceImplement;
import com.project.fashion.service.implement.OrderItemServiceImplement;
import com.project.fashion.service.implement.OrderLineServiceImplement;
import com.project.fashion.service.implement.PaymentServiceImplement;
import com.project.fashion.service.implement.ProductServiceImplement;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private PaymentServiceImplement paymentServiceImplement;
    @Autowired
    private ProductServiceImplement productServiceImplement;

    @Autowired
    private Authen authen;

    private Long calculateTotal(List<Cart> bill) {
        Long total = Long.valueOf(0);
        for (Cart cart : bill) {
            total += cart.getProduct().getPrice() * cart.getQuantity();
        }
        return total;
    }

    @GetMapping
    public String renderShowCart(Model model) {
        try {
            List<Cart> productOfUser = cartServiceImplement.getCartByCustomerId(authen.authen().getCustomerId());
            model.addAttribute("pOU", productOfUser);
            Long price = calculateTotal(productOfUser);
            if (productOfUser.isEmpty())
                model.addAttribute("messInfo", "No products");
            model.addAttribute("price", price);
            model.addAttribute("createBill", new ListCartCreateBillDTO());
        } catch (Exception e) {
            model.addAttribute("message", "Not Product");
        }
        return "cart";
    }

    // Get bill
    @GetMapping("/bill")
    public String showBill(Model model) {
        try {
            Customer customer = customerServiceImplement.getCustomerByUsername(authen.authen().getUsername());
            // show info customer like phone, address, fullname,... and options payment list
            // to customer choose and list cart by customer.getCarts()
            model.addAttribute("customer", customer);
            List<Cart> payProducts = cartServiceImplement.getCartByCustomerId(customer.getCustomerId());
            // total money
            Long total = calculateTotal(payProducts);
            model.addAttribute("payProducts", payProducts);
            model.addAttribute("total", total);
            return "bill";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "redirect:/user/cart";
        }
    }

    @PostMapping("/bill")
    public String createBill(Model model, @RequestParam("paymentMethod") String method) {
        // thông tin user
        Customer customer = customerServiceImplement.getCustomerById(authen.authen().getCustomerId());
        AddPaymentDTO addPaymentDTO = new AddPaymentDTO();

        try {
            // kiểm tra người dùng đã có method thanh toán đó chưa nếu chư có sẽ ném ra
            // exception
            Payment pay = paymentServiceImplement.getPaymentByMethodOfCustomer(customer.getCustomerId(), method);
            addPaymentDTO.setCustomerId(pay.getCustomer().getCustomerId());
            addPaymentDTO.setMethod(pay.getPaymentMethod());
            addPaymentDTO.setPaymentId(pay.getPaymentId());
        } catch (Exception e) {
            // chưa có thì ta sét method đó để lưu
            addPaymentDTO.setCustomerId(customer.getCustomerId());
            addPaymentDTO.setMethod(method);
            paymentServiceImplement.addPayment(addPaymentDTO);
        }
        try {

            // yêu cầu hóa đơn tạo hóa đơn
            AddOrderLineDTO addOrderLineDTO = new AddOrderLineDTO();
            addOrderLineDTO.setPaymentId(addOrderLineDTO.getPaymentId());
            // trạng thái chưa thanh toán
            addOrderLineDTO.setStatus("NOT_YET_PAID");

            // tạo hóa đơn
            OrderLine orderLine = orderLineServiceImplement.addOrderLine(addOrderLineDTO);

            // lưu danh sách mặt hàng của hóa đơn
            orderItemServiceImplement.addOrderItemByOrderLine(orderLine.getOrderLineId(), customer.getCarts());

            // xóa những sản phẩm đã mua khỏi giỏ hàng
            productServiceImplement.subtractionStock(customer);
            cartServiceImplement.deleteCartByCustomerId(customer.getCustomerId());
            model.addAttribute("messageCreateBillSuccess", "Order Successfully");

            // trả về trang home với trạng thái thanh toán
            if (method == "ONLINE") {
                Long total = calculateTotal(cartServiceImplement.getCartByCustomerId(customer.getCustomerId()));
                return "redirect:/payment?amount=" + total;
            } else {
                return "redirect:/user/home";
            }
        } catch (Exception e) {
            model.addAttribute("messageErrorCreateBill", e.getMessage());
            return "redirect:/user/cart";
        }
    }

    // delete product
    @DeleteMapping
    public String deleteProduct(@RequestParam("cartId") Long cartId,
            RedirectAttributes redirectAttributes, HttpSession session) {
        try {
            CustomerDetailResponse user = authen.authen();
            Integer countOfProducts = cartServiceImplement.getCountProductsInCustomerCart(user.getCustomerId());
            session.setAttribute("countProductsInCart", countOfProducts);
            cartServiceImplement.removeProductFromCart(cartId);
            int cartAfterDel = (int) session.getAttribute("countProductsInCart") - 1;
            session.setAttribute("countProductsInCart", cartAfterDel);
            redirectAttributes.addFlashAttribute("message", "Delete Successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error while trying to remove product");
        }
        return "redirect:/user/cart";
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
