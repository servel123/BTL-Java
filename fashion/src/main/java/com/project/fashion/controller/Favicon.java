package com.project.fashion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Favicon {

    @RequestMapping("favicon.ico")
    @ResponseBody
    void handleFavicon() {
        // Không làm gì, chỉ trả về response 200 OK
    }
}
