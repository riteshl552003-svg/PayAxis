package com.payroll.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*", "/employee/*"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        String path = request.getRequestURI();

        if (role == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Admin trying to access employee pages
        if (path.contains("/employee/") && !"EMPLOYEE".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
            return;
        }

        // Employee trying to access admin pages
        if (path.contains("/admin/") && !"ADMIN".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/employee/dashboard.jsp");
            return;
        }

        chain.doFilter(req, res);
    }
}