package com.project.fashion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

import com.project.fashion.dto.request.AddOrderLineDTO;
import com.project.fashion.dto.request.AddPaymentDTO;
import com.project.fashion.dto.request.ListCartCreateBillDTO;
import com.project.fashion.dto.response.CategoryResDTO;
import com.project.fashion.dto.response.CustomerDetailResponse;

import com.project.fashion.model.Cart;
import com.project.fashion.model.Customer;

import com.project.fashion.model.OrderLine;
import com.project.fashion.model.Payment;

import com.project.fashion.service.implement.CartServiceImplement;
import com.project.fashion.service.implement.CategoryServiceImplement;
import com.project.fashion.service.implement.CustomerServiceImplement;
import com.project.fashion.service.implement.OrderItemServiceImplement;
import com.project.fashion.service.implement.OrderLineServiceImplement;
import com.project.fashion.service.implement.PaymentServiceImplement;
import com.project.fashion.service.implement.ProductServiceImplement;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/user/cart")
public class CartController {

    @Autowired
    private CartServiceImplement cartServiceImplement;
    @Autowired
    private CategoryServiceImplement categoryServiceImplement;
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
    public String renderShowCart(Model model, HttpSession session) {
        try {
            List<Cart> productOfUser = cartServiceImplement.getCartByCustomerId(authen.authen().getCustomerId());
            model.addAttribute("pOU", productOfUser);
            Long price = calculateTotal(productOfUser);
            if (productOfUser.isEmpty())
                model.addAttribute("messInfo", "Không có sản phẩm");
            model.addAttribute("price", price);
            log.info("\n\n\n" + session.getAttribute("username") + "\n\n\n");
            model.addAttribute("createBill", new ListCartCreateBillDTO());
            List<CategoryResDTO> fashions = categoryServiceImplement.showCategory();
            model.addAttribute("fashions", fashions);
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

    @GetMapping("/bill/off")
    @Transactional
    public String createBill(RedirectAttributes redirectAttributes, HttpSession session) {

        Customer customer = customerServiceImplement.getCustomerById(authen.authen().getCustomerId());
        Payment payment = new Payment();
        try {
            payment = paymentServiceImplement.getPaymentByMethodOfCustomer(customer.getCustomerId(), "OFFLINE");
            log.error("\n da co loi", payment);
        } catch (Exception e) {
            AddPaymentDTO pay = new AddPaymentDTO();
            pay.setCustomerId(customer.getCustomerId());
            pay.setMethod("OFFLINE");
            payment = paymentServiceImplement.addPayment(pay);
            log.error("\n chua co ", payment);
        }
        try {
            // yêu cầu bill
            AddOrderLineDTO bill = new AddOrderLineDTO();
            bill.setPaymentId(payment.getPaymentId());
            bill.setStatus("NOT_YET_PAID");
            bill.setTranCode("");
            // lưu bill vào csdl
            log.info("\n0");

            OrderLine order = orderLineServiceImplement.addOrderLine(bill);
            log.info("\n1");
            List<Cart> carts = customer.getCarts();
            // lưu sản phầm vào orderitem
            log.info("\n1.1");
            orderItemServiceImplement.addOrderItemByOrderLine(order.getOrderLineId(), carts);
            log.info("\n2");
            // cập nhập số lượng còn
            productServiceImplement.subtractionStock(customer);
            // // loại bỏ các sản phẩm đã mua
            // log.info("\n3");
            // cartServiceImplement.deleteCartByCustomerId(customer.getCustomerId());
            // Integer countOfProducts =
            // cartServiceImplement.getCountProductsInCustomerCart(customer.getCustomerId());
            // session.setAttribute("countProductsInCart", countOfProducts);
            for (Cart cart : carts) {
                log.info("\n\n\n" + cart.getCartId() + "\n\n");
                cartServiceImplement.removeProductFromCart(cart.getCartId());
            }
        } catch (Exception e) {
            log.info("\n ERROR: " + e.getMessage());
            return "redirect:/user/cart";
        }

        return "redirect:/user";
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
            redirectAttributes.addFlashAttribute("message", "Xóa thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Đã xảy ra lỗi trong khi đang thực hiện");
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
        return "redirect:/user/cart";
    }
}
