// package com.project.fashion.util;

// import jakarta.servlet.Filter;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.ServletRequest;
// import jakarta.servlet.ServletResponse;
// import jakarta.servlet.http.HttpServletRequest;
// import org.springframework.stereotype.Component;

// import java.io.IOException;

// @Component
// public class FaviconFilter implements Filter {
// @Override
// public void doFilter(ServletRequest request, ServletResponse response,
// FilterChain chain)
// throws IOException, ServletException {
// HttpServletRequest req = (HttpServletRequest) request;
// if ("/favicon.ico".equals(req.getRequestURI())) {
// return; // Bỏ qua yêu cầu favicon.ico
// }
// chain.doFilter(request, response);
// }
// }
