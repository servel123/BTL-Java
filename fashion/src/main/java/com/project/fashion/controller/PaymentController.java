/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.fashion.controller;

import com.project.fashion.config.PaymentConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.project.fashion.dto.request.AddOrderLineDTO;
import com.project.fashion.dto.request.AddPaymentDTO;
import com.project.fashion.dto.response.PaymentResDTO;

import com.project.fashion.model.Cart;
import com.project.fashion.model.Customer;
import com.project.fashion.model.OrderLine;
import com.project.fashion.model.Payment;

import com.project.fashion.service.implement.CustomerServiceImplement;
import com.project.fashion.service.implement.OrderItemServiceImplement;
import com.project.fashion.service.implement.OrderLineServiceImplement;
import com.project.fashion.service.implement.PaymentServiceImplement;
import com.project.fashion.service.implement.ProductServiceImplement;

/**
 *
 * @author Vu
 */
@Slf4j
@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private CustomerServiceImplement customerServiceImplement;
    @Autowired
    private PaymentServiceImplement paymentServiceImplement;
    @Autowired
    private OrderLineServiceImplement orderLineServiceImplement;
    @Autowired
    private OrderItemServiceImplement orderItemServiceImplement;
    @Autowired
    private ProductServiceImplement productServiceImplement;

    @GetMapping
    public String createPayment(HttpServletRequest req, HttpServletResponse resp,
            @RequestParam("amount") long price)
            throws UnsupportedEncodingException, IOException {

        long amount = price * 100;

        String bankCode = req.getParameter("bankCode");
        String vnp_TxnRef = PaymentConfig.getRandomNumber(8);
        String vnp_IpAddr = PaymentConfig.getIpAddress(req);

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", PaymentConfig.vnp_Version);
        vnp_Params.put("vnp_Command", PaymentConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", PaymentConfig.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", PaymentConfig.orderType);

        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", PaymentConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = PaymentConfig.vnp_PayUrl + "?" + queryUrl;
        JsonObject job = new JsonObject();
        job.addProperty("code", "00");
        job.addProperty("message", "success");
        job.addProperty("data", paymentUrl);
        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(job));

        PaymentResDTO paymentResDTO = new PaymentResDTO();
        paymentResDTO.setStatus(HttpStatus.OK.value());
        paymentResDTO.setMessage(HttpStatus.OK.getReasonPhrase());
        paymentResDTO.setUrl(paymentUrl);

        log.info("\n\n\n" + paymentUrl + "\n\n\n");
        return "redirect:" + paymentUrl;
    }

    @GetMapping("/result")
    @Transactional
    public String resultPayment(@RequestParam("vnp_Amount") Long amount,
            @RequestParam("vnp_ResponseCode") String code,
            @RequestParam("vnp_TxnRef") String trancode,
            HttpSession session,
            Model model) {
        Customer customer = customerServiceImplement.getCustomerByUsername(session.getAttribute("username").toString());
        log.info("\n\n\n ohhhh: " + session.getAttribute("username") + "\n\n\n");
        Payment payment = new Payment();
        try {

            payment = paymentServiceImplement.getPaymentByMethodOfCustomer(customer.getCustomerId(),
                    "ONLINE");
        } catch (Exception e) {
            AddPaymentDTO pay = new AddPaymentDTO();
            pay.setCustomerId(customer.getCustomerId());
            pay.setMethod("ONLINE");
            payment = paymentServiceImplement.addPayment(pay);
        }
        if (code.equals("00")) {
            try {
                // yêu cầu bill
                AddOrderLineDTO bill = new AddOrderLineDTO();
                bill.setPaymentId(payment.getPaymentId());
                bill.setStatus("PAID");
                bill.setTranCode(trancode);
                // lưu bill vào csdl
                OrderLine order = orderLineServiceImplement.addOrderLine(bill);

                List<Cart> carts = customer.getCarts();
                // lưu sản phầm vào orderitem
                orderItemServiceImplement.addOrderItemByOrderLine(order.getOrderLineId(),
                        carts);
                // cập nhập số lượng còn
                productServiceImplement.subtractionStock(customer);
                // loại bỏ các sản phẩm đã mua
                // cartServiceImplement.deleteCartByCustomerId(customer.getCustomerId());

            } catch (Exception e) {

            }
        }
        model.addAttribute("trancode", trancode);
        model.addAttribute("code", code);
        model.addAttribute("amount", amount);

        return "vpn_return";
    }

}
