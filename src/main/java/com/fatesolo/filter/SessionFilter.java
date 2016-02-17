package com.fatesolo.filter;

import com.fatesolo.util.Response;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (uri.contains("admin") && !uri.contains("admin/login") && request.getSession().getAttribute("user") == null) {
            response.sendRedirect(Response.PATH + "login.html");
        } else {
            filterChain.doFilter(request, response);
        }
    }

}
