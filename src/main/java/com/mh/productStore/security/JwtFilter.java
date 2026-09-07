package com.mh.productStore.security;

import com.mh.productStore.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter //وظیفه دارد برای هر درخواست ورودی توکن را استخراج اعتبارسنجی و هویت کاربر را در سیستم ثبت کند
{
    private final JwtService jwtService;
    private final UserService userService;

    public JwtFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("JWT FILTER RUNNING for: " + request.getRequestURI());
        //بررسی هدر Authorization
        String authHeader = request.getHeader("Authorization");//بررسی میکند هدر مجوز دارد یا نه
        //اگر هدر وجود نداشت یا Bearer نبود، بدون پردازش JWT ادامه بده
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("No valid Authorization header found, skipping JWT filter");
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(7);//استخراج توکن
        System.out.println("[JWT] Token extracted (first 20): " + token.substring(0, Math.min(20, token.length())) + "...");

        String username = jwtService.extractUsername(token);//خواندن نام کاربری
        System.out.println("[JWT] Username from token: " + username);

        var userDetails = userService.loadUserByUsername(username);//دریافت کاربر
        System.out.println("[JWT] User loaded - authorities: " + userDetails.getAuthorities());

        boolean isValid = jwtService.validateToken(token, userDetails.getUsername());//اعتبارسنجی توکن
        System.out.println("[JWT] Token valid: " + isValid);

        if (isValid) {
            var authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
            System.out.println("[JWT] SecurityContextHolder set OK!");
        }
        filterChain.doFilter(request, response);//درخواست و پاسخ به فیلتر بعدی در زنجیره پاس داده میشود

    }

}
